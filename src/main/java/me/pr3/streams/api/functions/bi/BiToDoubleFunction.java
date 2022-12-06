package me.pr3.streams.api.functions.bi;

public interface BiToDoubleFunction <T, U>{
    double applyAsDouble(T t, U u);
}
