package me.pr3.streams.impl;

import me.pr3.streams.api.functions.bi.BiToDoubleFunction;
import me.pr3.streams.api.functions.bi.BiToIntFunction;
import me.pr3.streams.api.functions.bi.BiToLongFunction;
import me.pr3.streams.api.streams.IPairStream;
import me.pr3.streams.api.streams.ISingleStream;
import me.pr3.streams.impl.tupels.OptionalPair;
import me.pr3.streams.impl.tupels.Pair;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.*;
import java.util.stream.*;

import static java.lang.Math.max;

public class PairStream<T, U> implements IPairStream<T, U> {
    private Stream<Pair<T, U>> pairStream;
    @Override
    public IPairStream<T, U> filter(BiPredicate<? super T, ? super U> predicate) {
        return null;
    }

    @Override
    public <A, B> IPairStream<A, B> mapSeparate(Function<? super T, ? extends A> mapperA, Function<? super U, ? extends B> mapperB) {
        return null;
    }

    @Override
    public <A, B> IPairStream<A, B> mapTogether(BiFunction<? super T, ? super U, A> aBiFunction, BiFunction<? super T, ? super U, B> bBiFunction) {
        return null;
    }

    @Override
    public <A> ISingleStream<A> mapToSingle(BiFunction<T, U, A> biFunction) {
        return null;
    }

    @Override
    public IntStream mapToInt(BiToIntFunction<? super T, ? super U> mapper) {
        return null;
    }

    @Override
    public LongStream mapToLong(BiToLongFunction<? super T, ? super U> mapper) {
        return null;
    }

    @Override
    public DoubleStream mapToDouble(BiToDoubleFunction<? super T, ? super U> mapper) {
        return null;
    }

    @Override
    public <A, B> IPairStream<A, B> flatMap(BiFunction<? super T, ? super U, ? extends IPairStream<? extends A, ? extends B>> mapper) {
        return null;
    }

    @Override
    public IntStream flatMapToInt(BiFunction<? super T, ? super U, ? extends IntStream> mapper) {
        return null;
    }

    @Override
    public LongStream flatMapToLong(BiFunction<? super T, ? super U, ? extends LongStream> mapper) {
        return null;
    }

    @Override
    public DoubleStream flatMapToDouble(BiFunction<? super T, ? super U, ? extends DoubleStream> mapper) {
        return null;
    }

    @Override
    public IPairStream<T, U> distinct() {
        return null;
    }

    @Override
    public IPairStream<T, U> sorted() {
        return null;
    }

    @Override
    public IPairStream<T, U> peek(BiConsumer<? super T, ? super U> action) {
        return null;
    }

    @Override
    public IPairStream<T, U> limit(long maxSize) {
        return null;
    }

    @Override
    public IPairStream<T, U> skip(long n) {
        return null;
    }

    @Override
    public void forEach(BiConsumer<? super T, ? super U> action) {

    }

    @Override
    public void forEachOrdered(BiConsumer<? super T, ? super U> action) {

    }

    @Override
    public Pair<T, U>[] toArray() {
        return new Pair[0];
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return null;
    }

    @Override
    public Pair<T, U> reduce(T identity1, U identity2, BiFunction<T, U, Pair<T, U>> accumulator) {
        return null;
    }

    @Override
    public Optional<Pair<T, U>> reduce(BiFunction<T, U, Pair<T, U>> accumulator) {
        return Optional.empty();
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        return null;
    }

    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public boolean anyMatch(BiPredicate<? super T, ? super U> predicate) {
        return false;
    }

    @Override
    public boolean allMatch(BiPredicate<? super T, ? super U> predicate) {
        return false;
    }

    @Override
    public boolean noneMatch(BiPredicate<? super T, ? super U> predicate) {
        return false;
    }

    @Override
    public OptionalPair<T, U> findFirst() {
        return null;
    }

    @Override
    public OptionalPair<T, U> findAny() {
        return null;
    }

    public PairStream(T[] tData, U[] uData){
        Pair<T, U>[] pairs = new Pair[max(tData.length, uData.length)];
        for(int i = 0; i < max(tData.length, uData.length); i++){
            pairs[i] = new Pair<>(tData[i], uData[i]);
        }
        this.pairStream = Arrays.stream(pairs);
    }

    public PairStream(List<T> tList, List<U> uList){
        Pair<T, U>[] pairs = new Pair[max(tList.size(), uList.size())];
        for(int i = 0; i < max(tList.size(), uList.size()); i++){
            pairs[i] = new Pair<>(tList.get(i), uList.get(i));
        }
        this.pairStream = Arrays.stream(pairs);
    }


}
