package me.pr3.streams.api.functions.bi;

public interface BiComparator<T, U> {
    int compare(T t1, T t2, U u1, U u2);
}
