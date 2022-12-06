package me.pr3.streams.api.functions.bi;

public interface BiToLongFunction <T, U>{
    long applyAsLong(T t, U u);
}
