package me.pr3.streams.impl;

import me.pr3.streams.api.streams.IPairStream;
import me.pr3.streams.api.streams.ISingleStream;
import me.pr3.streams.api.streams.ITripletStream;
import org.apache.commons.collections4.ListUtils;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class SingleStream<T> implements ISingleStream<T> {
    private final Stream<T> stream;
    @Override
    public ISingleStream<T> filter(Predicate<? super T> predicate) {
        return new SingleStream<>(stream.filter(predicate));
    }

    @Override
    public <R> ISingleStream<R> map(Function<? super T, ? extends R> mapper) {
        return new SingleStream<>(stream.map(mapper));
    }

    @Override
    public <A, B> IPairStream<A, B> mapToPair(Function<? super T, A> aFunction, Function<? super T, B> bFunction) {
        List<A> aList = new ArrayList<>();
        List<B> bList = new ArrayList<>();
        stream.forEach(t ->{
            aList.add(aFunction.apply(t));
            bList.add(bFunction.apply(t));
        });
        return new PairStream<>(aList, bList);
    }

    @Override
    public <A, B, C> ITripletStream<A, B, C> mapToTriplet(Function<? super T, A> aFunction, Function<? super T, B> bFunction, Function<? super T, C> cFunction) {
        List<A> aList = new ArrayList<>();
        List<B> bList = new ArrayList<>();
        List<C> cList = new ArrayList<>();
        stream.forEach(t ->{
            aList.add(aFunction.apply(t));
            bList.add(bFunction.apply(t));
            cList.add(cFunction.apply(t));
        });
        return new TripletStream<>(aList, bList, cList);
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super T> mapper) {
        return stream.mapToInt(mapper);
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super T> mapper) {
        return stream.mapToLong(mapper);
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        return stream.mapToDouble(mapper);
    }

    @Override
    public <R> ISingleStream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return new SingleStream<>(stream.flatMap(mapper));
    }

    @Override
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        return stream.flatMapToInt(mapper);
    }

    @Override
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        return stream.flatMapToLong(mapper);
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        return stream.flatMapToDouble(mapper);
    }

    @Override
    public ISingleStream<T> distinct() {
        return new SingleStream<>(stream.distinct());
    }

    @Override
    public ISingleStream<T> sorted() {
        return new SingleStream<>(stream.sorted());
    }

    @Override
    public ISingleStream<T> sorted(Comparator<? super T> comparator) {
        return new SingleStream<>(stream.sorted(comparator));
    }

    @Override
    public ISingleStream<T> peek(Consumer<? super T> action) {
        return new SingleStream<>(stream.peek(action));
    }

    @Override
    public ISingleStream<T> limit(long maxSize) {
        return new SingleStream<>(stream.limit(maxSize));
    }

    @Override
    public ISingleStream<T> skip(long n) {
        return new SingleStream<>(stream.skip(n));
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        stream.forEach(action);
    }

    @Override
    public void forEachOrdered(Consumer<? super T> action) {
        stream.forEachOrdered(action);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T[] toArray() {
        return (T[]) stream.toList().toArray(new Object[0]);
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return stream.toArray(generator);
    }

    @Override
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        return stream.reduce(identity, accumulator);
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        return stream.reduce(accumulator);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        return stream.collect(supplier, accumulator, combiner);
    }

    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        return stream.collect(collector);
    }

    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        return stream.min(comparator);
    }

    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        return stream.max(comparator);
    }

    @Override
    public long count() {
        return stream.count();
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        return stream.anyMatch(predicate);
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        return stream.allMatch(predicate);
    }

    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        return stream.noneMatch(predicate);
    }

    @Override
    public Optional<T> findFirst() {
        return stream.findFirst();
    }

    @Override
    public Optional<T> findAny() {
        return stream.findAny();
    }

    @Override
    public ISingleStream<List<T>> partition(int size) {
        List<T> list = stream.toList();
        List<List<T>> collections = ListUtils.partition(list, size);
        return new SingleStream<>(collections);
    }

    public SingleStream(T[] data){
        this.stream = Arrays.stream(data);
    }

    private SingleStream(Stream<T> stream){
        this.stream = stream;
    }

    public SingleStream(List<T> tList){
        this.stream = tList.stream();
    }
}
