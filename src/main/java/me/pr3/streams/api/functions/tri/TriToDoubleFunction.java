package me.pr3.streams.api.functions.tri;

public interface TriToDoubleFunction <T, U, V>{
    double applyAsDouble(T t, U u, V v);
}
