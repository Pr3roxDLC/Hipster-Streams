package me.pr3.streams.impl.tupels;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class OptionalTriplet <T,U,V>{

    private T value1;
    private U value2;
    private V value3;
    private static final OptionalTriplet<?, ?, ?> EMPTY = new OptionalTriplet<>(null, null, null);
    public OptionalTriplet(T value1, U value2, V value3) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }

    public static <T, U, V> OptionalTriplet<T, U, V> empty() {
        @SuppressWarnings("unchecked")
        OptionalTriplet<T, U, V> t = (OptionalTriplet<T, U, V>) EMPTY;
        return t;
    }

    public static <T, U, V> @NotNull OptionalTriplet<T, U, V> of(T value1, U value2, V value3) {
        return new OptionalTriplet<>(value1, value2, value3);
    }

}
