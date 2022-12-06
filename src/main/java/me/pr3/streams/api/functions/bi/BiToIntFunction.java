package me.pr3.streams.api.functions.bi;

public interface BiToIntFunction<T, U> {
    int applyAsInt(T value1, U value2);
}
