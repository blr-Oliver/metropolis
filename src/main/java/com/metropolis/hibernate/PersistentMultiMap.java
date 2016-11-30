package com.metropolis.hibernate;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.hibernate.HibernateException;
import org.hibernate.collection.internal.PersistentMap;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.loader.CollectionAliases;
import org.hibernate.persister.collection.CollectionPersister;
import org.hibernate.type.Type;

import com.metropolis.util.MultiMap;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class PersistentMultiMap extends PersistentMap implements MultiMap {
	private static final long serialVersionUID = 1L;

	public PersistentMultiMap(SessionImplementor session, MultiMap map) {
		super(session, map);
	}

	public PersistentMultiMap(SessionImplementor session) {
		super(session);
	}

	protected final MultiMap getMultiMap() {
		return (MultiMap) map;
	}
	protected final boolean marksNull(Collection c) {
		return c == null || c.isEmpty() || c.size() == 1 && c.iterator().next() == null;
	}

	@Override
	protected Object readElementByIndex(Object index) {
		// override to always ignore extra-laziness
		// since trying to load single element for index produces 'not a single row' error
		// TODO this should really be handled by specialized persister
		read();
		return UNKNOWN;
	}
	@Override
	public Serializable getSnapshot(CollectionPersister persister) throws HibernateException {
		final MultiMap clonedMap = (MultiMap) persister.getCollectionType().instantiate(getMultiMap().asBucketMap().size());
		final Type elementType = persister.getElementType();
		final SessionFactoryImplementor factory = persister.getFactory();
		for (Object o : getMultiMap().asBucketMap().entrySet()) {
			Object key = ((Entry) o).getKey();
			Collection values = (Collection) ((Entry) o).getValue();
			if (values == null || values.isEmpty())
				clonedMap.add(key, null);
			else
				for (Object element : values)
					clonedMap.add(key, elementType.deepCopy(element, factory));
		}
		return (Serializable) clonedMap;
	}
	@Override
	public Collection getOrphans(Serializable snapshot, String entityName) throws HibernateException {
		return getOrphans(((MultiMap) snapshot).values(), getMultiMap().values(), entityName, getSession());
	}
	@Override
	public boolean equalsSnapshot(CollectionPersister persister) throws HibernateException {
		final Type elementType = persister.getElementType();
		final MultiMap snapshot = (MultiMap) getSnapshot();
		final MultiMap map = getMultiMap();
		if (snapshot.asBucketMap().size() != map.asBucketMap().size())
			return false;
		for (Object o : map.asBucketMap().entrySet()) {
			final Entry entry = (Entry) o;
			Collection currentValues = (Collection) entry.getValue();
			Collection oldValues = snapshot.getAll(entry.getKey());
			if (marksNull(currentValues)) {
				if (!marksNull(oldValues))
					return false;
			} else {
				if (marksNull(oldValues))
					return false;
				for (Iterator currentItr = currentValues.iterator(), oldItr = oldValues.iterator(); currentItr.hasNext();) {
					if (elementType.isDirty(currentItr.next(), oldItr.next(), getSession()))
						return false;
				}
			}
		}
		return true;
	}
	@Override
	public Object put(Object key, Object value) {
		initialize(true);
		if (getMultiMap().add(key, value)) {
			dirty();
			return null;
		} else
			return value;
	}

	@Override
	public void putAll(Map puts) {
		initialize(true);
		if (getMultiMap().addAll(puts))
			dirty();
	}

	private transient List<Object[]> loadingEntries;

	@Override
	public Object readFrom(ResultSet rs, CollectionPersister persister, CollectionAliases descriptor, Object owner) throws HibernateException, SQLException {
		final Object index = persister.readIndex(rs, descriptor.getSuffixedIndexAliases(), getSession());
		final Object element = persister.readElement(rs, owner, descriptor.getSuffixedElementAliases(), getSession());

		if (loadingEntries == null)
			loadingEntries = new ArrayList<Object[]>();

		loadingEntries.add(new Object[] { index, element });
		return element;
	}

	@Override
	public boolean endRead() {
		if (loadingEntries != null) {
			MultiMap map = getMultiMap();
			for (Object[] entry : loadingEntries)
				map.add(entry[0], entry[1]);
			loadingEntries = null;
		}
		return super.endRead();
	}

	@Override
	public Object getSnapshotElement(Object entry, int i) {
		final MultiMap snapshot = (MultiMap) getSnapshot();
		Map.Entry e = (Map.Entry) entry;
		Object value = e.getValue();
		if (value != null) {
			Collection oldState = snapshot.getAll(e.getKey());
			for (Object oldValue : oldState)
				if (value.equals(oldValue))
					return oldValue;
		}
		return null;
	}

	@Override
	public boolean entryExists(Object entry, int i) {
		return true;
	}

	@Override
	public Collection getAll(Object key) {
		read();
		return getMultiMap().getAll(key);
	}
	@Override
	public boolean add(Object key, Object value) {
		write();
		return getMultiMap().add(key, value);
	}
	@Override
	public boolean add(Object key, Collection bucket) {
		write();
		return getMultiMap().add(key, bucket);
	}
	@Override
	public boolean addAll(Map map) {
		write();
		return getMultiMap().addAll(map);
	}
	@Override
	public boolean mergeAll(Map buckets) {
		write();
		return getMultiMap().mergeAll(buckets);
	}
	@Override
	public Map asBucketMap() {
		// TODO add modification support
		read();
		return Collections.unmodifiableMap(getMultiMap().asBucketMap());
	}
}
