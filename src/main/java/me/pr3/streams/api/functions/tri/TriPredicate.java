package me.pr3.streams.api.functions.tri;

public interface TriPredicate<T, U ,V> {
    boolean test(T t, U u, V v);
}
