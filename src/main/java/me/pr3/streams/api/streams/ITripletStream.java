package me.pr3.streams.api.streams;

import me.pr3.streams.api.functions.tri.*;
import me.pr3.streams.impl.tupels.OptionalTriplet;
import me.pr3.streams.impl.tupels.Triplet;

import java.util.List;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public interface ITripletStream <T, U, V> {
    ITripletStream<T, U, V> filter(TriPredicate<? super T, ? super U, ? super V> predicate);

    <A, B, C> ITripletStream<A, B, C> mapSeparate(
            Function<? super T, ? extends A> mapperA,
            Function<? super U, ? extends B> mapperB,
            Function<? super V, ? extends C> mapperC);

    <A, B, C> ITripletStream<A, B, C> mapTogether(
            TriFunction<? super T, ? super U, ? super V, A> aTriFunction,
            TriFunction<? super T, ? super U, ? super V, B> bTriFunction,
            TriFunction<? super T, ? super U, ? super V, C> cTriFunction);

    <A> ISingleStream<A> mapToSingle(TriFunction<T, U, V, A> triFunction);

    IntStream mapToInt(TriToIntFunction<? super T, ? super U, ? super V> mapper);

    LongStream mapToLong(TriToLongFunction<? super T, ? super U, ? super V> mapper);

    DoubleStream mapToDouble(TriToDoubleFunction<? super T, ? super U, ? super V> mapper);

    <A, B, C> ITripletStream<A, B, C> flatMap(
            TriFunction<? super T, ? super U, ? super V, ? extends ITripletStream<A,B,C>> mapper);

    IntStream flatMapToInt(TriFunction<? super T, ? super U, ? super V, ? extends IntStream> mapper);

    LongStream flatMapToLong(TriFunction<? super T, ? super U, ? super V, ? extends LongStream> mapper);

    DoubleStream flatMapToDouble(TriFunction<? super T, ? super U, ? super V, ? extends DoubleStream> mapper);

    ITripletStream<T, U, V> distinct();

    ITripletStream<T, U, V> sorted();

    //TODO write API for everything Comparator related, is a n-Comparator Needed?
    //IPairStream<T, U> sorted(Comparator<? super T> comparator);

    ITripletStream<T, U, V> peek(TriConsumer<? super T, ? super U, ? super V> action);

    ITripletStream<T, U, V> limit(long maxSize);

    ITripletStream<T, U, V> skip(long n);

    void forEach(TriConsumer<? super T, ? super U, ? super V> action);

    void forEachOrdered(TriConsumer<? super T, ? super U, ? super V> action);

    Triplet<T, U, V>[] toArray();

    <A> A[] toArray(IntFunction<A[]> generator);

    //TODO rethink this, there should be a better way to handle this?
    Triplet<T, U, V> reduce(
            T identity1,
            U identity2,
            V identity3,
            BiFunction<T, T, T> tAccumulator,
            BiFunction<U, U, U> uAccumulator,
            BiFunction<V, V, V> vAccumulator);

    OptionalTriplet<T, U ,V> reduce(
            BiFunction<T, T, T> tAccumulator,
            BiFunction<U, U, U> uAccumulator,
            BiFunction<V, V, V> vAccumulator);

    <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> aAccumulator, BiConsumer<R, ? super U> uAccumulator, BiConsumer<R, ? super V> vAccumulator);

    <R, A> R collect(Collector<? super T, A, R> collector);

    default List<Triplet<T, U, V>> toList() {
        return List.of(this.toArray());
    }

    OptionalTriplet<T, U, V> min(TriComparator<? super T, ? super U, ? super V> comparator);

    OptionalTriplet<T, U, V> max(TriComparator<? super T, ? super U, ? super V> comparator);

    long count();

    boolean anyMatch(TriPredicate<? super T, ? super U, ? super V> predicate);

    boolean allMatch(TriPredicate<? super T, ? super U, ? super V> predicate);

    boolean noneMatch(TriPredicate<? super T, ? super U, ? super V> predicate);

    OptionalTriplet<T, U, V> findFirst();

    OptionalTriplet<T, U, V> findAny();

    ITripletStream<List<T>, List<U>, List<V>> partition(int size);


}
