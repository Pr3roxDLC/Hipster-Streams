package me.pr3.streams.api.streams;

import me.pr3.streams.api.functions.bi.BiComparator;
import me.pr3.streams.api.functions.bi.BiToDoubleFunction;
import me.pr3.streams.api.functions.bi.BiToIntFunction;
import me.pr3.streams.api.functions.bi.BiToLongFunction;
import me.pr3.streams.impl.tupels.OptionalPair;
import me.pr3.streams.impl.tupels.Pair;

import java.util.List;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public interface IPairStream<T, U> {
    IPairStream<T, U> filter(BiPredicate<? super T, ? super U> predicate);

    <A, B> IPairStream<A, B> mapSeparate(Function<? super T, ? extends A> mapperA, Function<? super U, ? extends B> mapperB);

    <A, B> IPairStream<A, B> mapTogether(BiFunction<? super T, ? super U, A> aBiFunction, BiFunction<? super T, ? super U, B> bBiFunction);

    <A> ISingleStream<A> mapToSingle(BiFunction<T, U, A> biFunction);

    IntStream mapToInt(BiToIntFunction<? super T, ? super U> mapper);

    LongStream mapToLong(BiToLongFunction<? super T, ? super U> mapper);

    DoubleStream mapToDouble(BiToDoubleFunction<? super T, ? super U> mapper);

    <A, B> IPairStream<A, B> flatMap(BiFunction<? super T, ? super U, ? extends IPairStream<? extends A, ? extends B>> mapper);

    IntStream flatMapToInt(BiFunction<? super T, ? super U, ? extends IntStream> mapper);

    LongStream flatMapToLong(BiFunction<? super T, ? super U, ? extends LongStream> mapper);

    DoubleStream flatMapToDouble(BiFunction<? super T, ? super U, ? extends DoubleStream> mapper);

    IPairStream<T, U> distinct();

    IPairStream<T, U> sorted();

    //TODO write API for everything Comparator related, is a n-Comparator Needed?
    //IPairStream<T, U> sorted(Comparator<? super T> comparator);

    IPairStream<T, U> peek(BiConsumer<? super T, ? super U> action);

    IPairStream<T, U> limit(long maxSize);

    IPairStream<T, U> skip(long n);

    void forEach(BiConsumer<? super T, ? super U> action);

    void forEachOrdered(BiConsumer<? super T, ? super U> action);

    Pair<T, U>[] toArray();

    <A> A[] toArray(IntFunction<A[]> generator);

    //TODO rethink this, there should be a better way to handle this?
    Pair<T, U> reduce(T identity1, U identity2, BiFunction<T, T, T> tAccumulator, BiFunction<U, U, U> uAccumulator);

    OptionalPair<T, U> reduce(BiFunction<T, T, T> tAccumulator, BiFunction<U, U, U> uAccumulator);

    <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> aAccumulator, BiConsumer<R, ? super U> uAccumulator);

    <R, A> R collect(Collector<? super T, A, R> collector);

    default List<Pair<T, U>> toList() {
        return List.of(this.toArray());
    }

    OptionalPair<T, U> min(BiComparator<? super T, ? super U> comparator);

    OptionalPair<T, U> max(BiComparator<? super T, ? super U> comparator);

    long count();

    boolean anyMatch(BiPredicate<? super T, ? super U> predicate);

    boolean allMatch(BiPredicate<? super T, ? super U> predicate);

    boolean noneMatch(BiPredicate<? super T, ? super U> predicate);

    OptionalPair<T, U> findFirst();

    OptionalPair<T, U> findAny();

    IPairStream<List<T>, List<U>> partition(int size);


}
