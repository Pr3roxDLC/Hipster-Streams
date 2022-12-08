package me.pr3.streams.api.functions.tri;

public interface TriToIntFunction<T, U, V> {
    int applyAsInt(T value1, U value2, V value3);
}
