package me.pr3.streams.api.streams;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public interface ISingleStream<T>{
    ISingleStream<T> filter(Predicate<? super T> predicate);

    <R> ISingleStream<R> map(Function<? super T, ? extends R> mapper);

    <A, B> IPairStream<A,B> mapToPair(Function<? super T, A> aFunction, Function<? super T, B> bFunction);

    IntStream mapToInt(ToIntFunction<? super T> mapper);

    LongStream mapToLong(ToLongFunction<? super T> mapper);

    DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper);

    <R> ISingleStream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);

    IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper);

    LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper);

    DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper);

    ISingleStream<T> distinct();

    ISingleStream<T> sorted();

    ISingleStream<T> sorted(Comparator<? super T> comparator);

    ISingleStream<T> peek(Consumer<? super T> action);

    ISingleStream<T> limit(long maxSize);

    ISingleStream<T> skip(long n);

    void forEach(Consumer<? super T> action);

    void forEachOrdered(Consumer<? super T> action);

    T[] toArray();

    <A> A[] toArray(IntFunction<A[]> generator);

    T reduce(T identity, BinaryOperator<T> accumulator);

    Optional<T> reduce(BinaryOperator<T> accumulator);

    <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner);

    <R, A> R collect(Collector<? super T, A, R> collector);

    default List<T> toList() {
        return List.of(this.toArray());
    }

    Optional<T> min(Comparator<? super T> comparator);

    Optional<T> max(Comparator<? super T> comparator);

    long count();

    boolean anyMatch(Predicate<? super T> predicate);

    boolean allMatch(Predicate<? super T> predicate);

    boolean noneMatch(Predicate<? super T> predicate);

    Optional<T> findFirst();

    Optional<T> findAny();

    @SafeVarargs
    static<T> ISingleStream<T> of(T... values) {
        return new me.pr3.streams.impl.SingleStream<>(values);
    }

    ISingleStream<List<T>> partition(int size);

}
