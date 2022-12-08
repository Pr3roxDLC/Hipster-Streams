package me.pr3.streams.api.functions.tri;

public interface TriToLongFunction <T, U, V>{
    long applyAsLong(T t, U u, V v);
}
