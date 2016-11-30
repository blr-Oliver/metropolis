package com.metropolis.hibernate;

import java.lang.reflect.Constructor;
import java.util.*;

import org.hibernate.HibernateException;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.persister.collection.CollectionPersister;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserCollectionType;

import com.metropolis.util.MultiMap;
import com.metropolis.util.MultiMapDecorator;

@SuppressWarnings("rawtypes")
public class MultiMapType implements UserCollectionType, ParameterizedType {
	public static final String BASE_TYPE_PARAM = "baseType";
	public static final String USE_INT_CONSTRUCTOR_PARAM = "useIntConstructor";
	public static final String NESTED_TYPE_PARAM = "nestedType";

	private Class<? extends Map> baseType = HashMap.class;
	private boolean useIntConstructor = true;
	private Class<? extends Collection> nestedType = HashSet.class;

	@SuppressWarnings("unchecked")
	@Override
	public Object instantiate(int anticipatedSize) {
		Map delegate = null;
		if (useIntConstructor && anticipatedSize >= 0) {
			try {
				Constructor<? extends Map> intConstructor = baseType.getConstructor(int.class);
				delegate = intConstructor.newInstance(anticipatedSize);
			} catch (ReflectiveOperationException e) {
				useIntConstructor = false;
			} catch (SecurityException e) {
				useIntConstructor = false;
			}
		}
		if (delegate == null)
			try {
				delegate = baseType.newInstance();
			} catch (ReflectiveOperationException e) {
				throw new IllegalArgumentException(e);
			}
		return new MultiMapDecorator(delegate, nestedType);
	}

	@Override
	public PersistentCollection instantiate(SessionImplementor session, CollectionPersister persister) throws HibernateException {
		return new PersistentMultiMap(session);
	}

	@Override
	public PersistentCollection wrap(SessionImplementor session, Object collection) {
		return new PersistentMultiMap(session, (MultiMap) collection);
	}

	@Override
	public Iterator getElementsIterator(Object collection) {
		return ((MultiMap) collection).values().iterator();
	}

	@Override
	public boolean contains(Object collection, Object entity) {
		return ((MultiMap) collection).values().contains(entity);
	}

	@Override
	public Object indexOf(Object collection, Object entity) {
		for (Object o : ((MultiMap) collection).asBucketMap().entrySet()) {
			final Map.Entry entry = (Map.Entry) o;
			Collection value = (Collection) entry.getValue();
			if (value.contains(entity))
				return entry.getKey();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object replaceElements(Object original, Object target, CollectionPersister persister, Object owner, Map copyCache, SessionImplementor session)
			throws HibernateException {
		MultiMap result = (MultiMap) target;
		result.clear();

		for (Object o : ((MultiMap) original).asBucketMap().entrySet()) {
			final Map.Entry entry = (Map.Entry) o;
			Object key = persister.getIndexType().replace(entry.getKey(), null, session, owner, copyCache);
			Collection values = (Collection) entry.getValue();
			for (Object element : values)
				result.add(key, persister.getElementType().replace(element, null, session, owner, copyCache));
		}
		return result;
	}

	@Override
	public void setParameterValues(Properties parameters) {
		String baseTypeName = parameters.getProperty(BASE_TYPE_PARAM);
		if (baseTypeName != null) {
			Class<?> baseType;
			try {
				baseType = Class.forName(baseTypeName);
			} catch (ClassNotFoundException e) {
				throw new IllegalArgumentException(e);
			}
			this.baseType = baseType.asSubclass(Map.class);
		}
		String useIntConstructorString = parameters.getProperty(USE_INT_CONSTRUCTOR_PARAM);
		if (useIntConstructorString != null)
			useIntConstructor = Boolean.parseBoolean(useIntConstructorString);
		String nestedTypeName = parameters.getProperty(NESTED_TYPE_PARAM);
		if (nestedTypeName != null) {
			Class<?> nestedType;
			try {
				nestedType = Class.forName(nestedTypeName);
			} catch (ClassNotFoundException e) {
				throw new IllegalArgumentException(e);
			}
			this.nestedType = nestedType.asSubclass(Collection.class);
		}
	}
}
