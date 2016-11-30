package com.metropolis.util;

import java.io.Serializable;
import java.util.*;

public class MultiMapDecorator<K, V, C extends Collection<V>> implements MultiMap<K, V, C>, Serializable {
	private static final long serialVersionUID = 1L;
	private final Map<K, C> map;
	private final Class<? extends C> nestedType;

	@SuppressWarnings("unchecked")
	public MultiMapDecorator(Map<K, C> delegate, Class<? extends C> nestedType) {
		if (delegate == null)
			throw new NullPointerException();
		if (nestedType == null)
			throw new NullPointerException();
		map = delegate;
		this.nestedType = (Class<? extends C>) nestedType.asSubclass(Collection.class);
	}
	protected C createValues() {
		try {
			return nestedType.newInstance();
		} catch (ReflectiveOperationException e) {
			throw new IllegalArgumentException(e);
		}
	}
	protected C getOrCreateBucket(K key) {
		C result = map.get(key);
		if (result == null)
			map.put(key, result = createValues());
		return result;
	}
	@Override
	public int size() {
		return map.values().parallelStream().mapToInt(b -> b == null || b.isEmpty() ? 1 : b.size()).sum();
	}
	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}
	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}
	@Override
	public boolean containsValue(Object value) {
		return map.values().parallelStream().filter(Objects::nonNull).anyMatch(b -> b.contains(value));
	}
	@Override
	public V get(Object key) {
		C bucket = getAll(key);
		return bucket == null || bucket.isEmpty() ? null : bucket.iterator().next();
	}
	@Override
	public V put(K key, V value) {
		return add(key, value) ? null : value;
	}
	@Override
	public V remove(Object key) {
		C bucket = map.remove(key);
		if (bucket == null || bucket.isEmpty())
			return null;
		return bucket.iterator().next();
	}
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		addAll(m);
	}
	@Override
	public void clear() {
		map.clear();
	}
	@Override
	public Set<K> keySet() {
		return map.keySet();
	}
	@Override
	public Collection<V> values() {
		return values == null ? (values = new Values()) : values;
	}
	@Override
	public Set<Entry<K, V>> entrySet() {
		return entrySet == null ? (entrySet = new EntrySet()) : entrySet;
	}
	@Override
	public C getAll(Object key) {
		return map.get(key);
	}
	@Override
	public boolean add(K key, V value) {
		return getOrCreateBucket(key).add(value);
	}
	@Override
	public boolean add(K key, Collection<? extends V> bucket) {
		C values = map.get(key);
		if (values == null) {
			map.put(key, values = createValues());
			values.addAll(bucket);
			return true;
		} else
			return values.addAll(bucket);
	}
	@Override
	public boolean addAll(Map<? extends K, ? extends V> map) {
		return map.entrySet().parallelStream().map(e -> add(e.getKey(), e.getValue())).reduce(false, Boolean::logicalOr);
	}
	@Override
	public boolean mergeAll(Map<? extends K, ? extends Collection<? extends V>> buckets) {
		return buckets.entrySet().parallelStream().map(e -> add(e.getKey(), e.getValue())).reduce(false, Boolean::logicalOr);
	}

	@Override
	public Map<K, C> asBucketMap() {
		return map;
	}

	public MultiMapDecorator<K, V, C> wipeNullMarkers() {
		asBucketMap().entrySet().stream().forEach(e -> {
			C bucket = e.getValue();
			if (bucket.size() == 1 && bucket.iterator().next() == null)
				e.setValue(null);
		});
		return this;
	}

	private transient EntrySet entrySet;
	private transient Values values;

	private class EntrySet extends AbstractSet<Map.Entry<K, V>> {
		@Override
		public Iterator<Entry<K, V>> iterator() {
			return new EntryIterator();
		}
		@Override
		public int size() {
			return MultiMapDecorator.this.size();
		}
		@Override
		public boolean isEmpty() {
			return MultiMapDecorator.this.isEmpty();
		}
		@Override
		public boolean contains(Object o) {
			if (!(o instanceof Entry))
				return false;
			Entry<?, ?> e = (Entry<?, ?>) o;
			if (map.containsKey(e.getKey())) {
				C bucket = map.get(e.getKey());
				Object value = e.getValue();
				if (value == null)
					return bucket == null || bucket.isEmpty();
				else
					return bucket != null && bucket.contains(value);
			}
			return false;
		}
		@Override
		public boolean remove(Object o) {
			if (!(o instanceof Entry))
				return false;
			Entry<?, ?> e = (Entry<?, ?>) o;
			if (map.containsKey(e.getKey())) {
				C bucket = map.get(e.getKey());
				Object value = e.getValue();
				boolean result = false;
				if (value == null) {
					if (bucket != null && !bucket.isEmpty()) {
						if (!bucket.remove(value))
							return false;
					}
				} else if (bucket == null || !(result = bucket.remove(value)))
					return false;
				if (bucket == null || bucket.isEmpty()) {
					map.remove(e.getKey());
					return true;
				}
				return result;
			}
			return false;
		}
		@Override
		public void clear() {
			MultiMapDecorator.this.clear();
		}
	}

	private class EntryIterator implements Iterator<Entry<K, V>> {
		private final Iterator<Entry<K, C>> master;
		private K key;
		private Iterator<V> current;

		public EntryIterator() {
			master = map.entrySet().iterator();
		}
		@Override
		public boolean hasNext() {
			return master.hasNext() || current != null && current.hasNext();
		}
		@Override
		public Entry<K, V> next() {
			V value = null;
			if (current == null || !current.hasNext()) {
				Entry<K, C> entry = master.next();
				key = entry.getKey();
				C bucket = entry.getValue();
				if (bucket == null)
					current = null;
				else {
					current = bucket.iterator();
					if (current.hasNext())
						value = current.next();
					if (value == null && !current.hasNext())
						current = null;
				}
			} else
				value = current.next();
			return new AbstractMap.SimpleImmutableEntry<>(key, value);
		}
		@Override
		public void remove() {
			if (current == null)
				master.remove();
			else
				current.remove();
		}
	}

	private class Values extends AbstractCollection<V> {
		@Override
		public Iterator<V> iterator() {
			return new ValuesIterator(entrySet().iterator());
		}
		@Override
		public int size() {
			return MultiMapDecorator.this.size();
		}
		@Override
		public boolean isEmpty() {
			return MultiMapDecorator.this.isEmpty();
		}
		@Override
		public void clear() {
			MultiMapDecorator.this.clear();
		}
	}

	private class ValuesIterator implements Iterator<V> {
		private final Iterator<Entry<K, V>> entryIterator;
		public ValuesIterator(Iterator<Entry<K, V>> entryIterator) {
			this.entryIterator = entryIterator;
		}
		@Override
		public boolean hasNext() {
			return entryIterator.hasNext();
		}
		@Override
		public V next() {
			return entryIterator.next().getValue();
		}
		@Override
		public void remove() {
			entryIterator.remove();
		}
	}
	public static <K, V, C extends Collection<V>> MultiMapDecorator<K, V, C> newInstance(Map<K, C> delegate, Class<? extends C> cClass) {
		return new MultiMapDecorator<K, V, C>(delegate, cClass);
	}
}
