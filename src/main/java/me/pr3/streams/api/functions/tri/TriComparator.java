package me.pr3.streams.api.functions.tri;

public interface TriComparator<T, U, V> {
    int compare(T t1, T t2, U u1, U u2, V v1, V v2);
}
