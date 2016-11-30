package com.metropolis.util;

import java.util.Collection;
import java.util.Map;

public interface MultiMap<K, V, C extends Collection<V>> extends Map<K, V> {
	@Override
	@Deprecated
	V get(Object key);
	@Override
	@Deprecated
	V put(K key, V value);
	@Override
	@Deprecated
	V remove(Object key);
	@Override
	@Deprecated
	void putAll(Map<? extends K, ? extends V> m);
	C getAll(Object key);
	boolean add(K key, V value);
	boolean add(K key, Collection<? extends V> bucket);
	boolean addAll(Map<? extends K, ? extends V> map);
	boolean mergeAll(Map<? extends K, ? extends Collection<? extends V>> buckets);
	Map<K, C> asBucketMap();
}
