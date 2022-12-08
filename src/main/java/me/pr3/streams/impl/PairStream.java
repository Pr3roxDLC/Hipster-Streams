package me.pr3.streams.impl;

import me.pr3.streams.api.functions.bi.BiComparator;
import me.pr3.streams.api.functions.bi.BiToDoubleFunction;
import me.pr3.streams.api.functions.bi.BiToIntFunction;
import me.pr3.streams.api.functions.bi.BiToLongFunction;
import me.pr3.streams.api.streams.IPairStream;
import me.pr3.streams.api.streams.ISingleStream;
import me.pr3.streams.impl.tupels.OptionalPair;
import me.pr3.streams.impl.tupels.Pair;
import org.apache.commons.collections4.ListUtils;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

@SuppressWarnings("unused")
public class PairStream<T, U> implements IPairStream<T, U> {
    private final Stream<Pair<T, U>> pairStream;


    @Override
    public IPairStream<T, U> filter(BiPredicate<? super T, ? super U> predicate) {
        return new PairStream<>(pairStream.filter(p -> predicate.test(p.left, p.right)));
    }

    @Override
    public <A, B> IPairStream<A, B> mapSeparate(Function<? super T, ? extends A> mapperA, Function<? super U, ? extends B> mapperB) {
        List<A> aList = new ArrayList<>();
        List<B> bList = new ArrayList<>();
        pairStream.forEach(p -> {
            aList.add(mapperA.apply(p.left));
            bList.add(mapperB.apply(p.right));
        });
        return new PairStream<>(aList, bList);
    }

    @Override
    public <A, B> IPairStream<A, B> mapTogether(BiFunction<? super T, ? super U, A> aBiFunction, BiFunction<? super T, ? super U, B> bBiFunction) {
        List<A> aList = new ArrayList<>();
        List<B> bList = new ArrayList<>();
        pairStream.forEach(p -> {
            aList.add(aBiFunction.apply(p.left, p.right));
            bList.add(bBiFunction.apply(p.left, p.right));
        });
        return new PairStream<>(aList, bList);
    }

    @Override
    public <A> ISingleStream<A> mapToSingle(BiFunction<T, U, A> biFunction) {
        return new SingleStream<>(pairStream.map(p -> biFunction.apply(p.left, p.right)).collect(Collectors.toList()));
    }

    @Override
    public IntStream mapToInt(BiToIntFunction<? super T, ? super U> mapper) {
        return pairStream.mapToInt(p -> mapper.applyAsInt(p.left, p.right));
    }

    @Override
    public LongStream mapToLong(BiToLongFunction<? super T, ? super U> mapper) {
        return pairStream.mapToLong(p -> mapper.applyAsLong(p.left, p.right));
    }

    @Override
    public DoubleStream mapToDouble(BiToDoubleFunction<? super T, ? super U> mapper) {
        return pairStream.mapToDouble(p -> mapper.applyAsDouble(p.left, p.right));
    }

    @Override
    public <A, B> IPairStream<A, B> flatMap(BiFunction<? super T, ? super U, ? extends IPairStream<? extends A, ? extends B>> mapper) {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public IntStream flatMapToInt(BiFunction<? super T, ? super U, ? extends IntStream> mapper) {
        return pairStream.flatMapToInt(p -> mapper.apply(p.left, p.right));
    }

    @Override
    public LongStream flatMapToLong(BiFunction<? super T, ? super U, ? extends LongStream> mapper) {
        return pairStream.flatMapToLong(p -> mapper.apply(p.left, p.right));
    }

    @Override
    public DoubleStream flatMapToDouble(BiFunction<? super T, ? super U, ? extends DoubleStream> mapper) {
        return pairStream.flatMapToDouble(p -> mapper.apply(p.left, p.right));
    }

    @Override
    public IPairStream<T, U> distinct() {
        return new PairStream<>(pairStream.distinct());
    }

    @Override
    public IPairStream<T, U> sorted() {
        return new PairStream<>(pairStream.sorted());
    }

    @Override
    public IPairStream<T, U> peek(BiConsumer<? super T, ? super U> action) {
        return new PairStream<>(pairStream.peek(p -> action.accept(p.left, p.right)));
    }

    @Override
    public IPairStream<T, U> limit(long maxSize) {
        return new PairStream<>(pairStream.limit(maxSize));
    }

    @Override
    public IPairStream<T, U> skip(long n) {
        return new PairStream<>(pairStream.skip(n));
    }

    @Override
    public void forEach(BiConsumer<? super T, ? super U> action) {
        pairStream.forEach(p -> action.accept(p.left, p.right));
    }

    @Override
    public void forEachOrdered(BiConsumer<? super T, ? super U> action) {
        pairStream.forEachOrdered(p -> action.accept(p.left, p.right));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Pair<T, U>[] toArray() {
       return pairStream.toList().toArray(new Pair[0]);
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return pairStream.toArray(generator);
    }

    @Override
    public Pair<T, U> reduce(T identity1, U identity2, BiFunction<T, T, T> tAccumulator, BiFunction<U, U, U> uAccumulator) {
        T tResult = identity1;
        U uResult = identity2;
        for (Pair<T, U> pair : pairStream.toList()) {
            tResult = tAccumulator.apply(tResult, pair.left);
            uResult = uAccumulator.apply(uResult, pair.right);
        }
        return new Pair<>(tResult, uResult);
    }

    @Override
    public OptionalPair<T, U> reduce(BiFunction<T, T, T> tAccumulator, BiFunction<U, U, U> uAccumulator) {
        boolean foundAny = false;
        T tResult = null;
        U uResult = null;
        for (Pair<T, U> element : pairStream.toList()) {
            if (!foundAny) {
                foundAny = true;
                tResult = element.left;
                uResult = element.right;
            } else {
                tResult = tAccumulator.apply(tResult, element.left);
                uResult = uAccumulator.apply(uResult, element.right);
            }

        }
        return foundAny ? OptionalPair.of(tResult, uResult) : OptionalPair.empty();
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> aAccumulator, BiConsumer<R, ? super U> uAccumulator) {
        R container = supplier.get();
        for (Pair<T, U> pair : pairStream.toList()) {
            aAccumulator.accept(container, pair.left);
            uAccumulator.accept(container, pair.right);
        }
        return container;
    }

    //TODO implement this
    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        throw new RuntimeException("Not Implemented Yet");
    }

    @Override
    public OptionalPair<T, U> min(BiComparator<? super T, ? super U> comparator) {
        List<Pair<T,U>> pairs = pairStream.toList();
        if(pairs.isEmpty())return OptionalPair.empty();
        Pair<T,U> min = pairs.get(0);
        for (Pair<T, U> p : pairs) {
            if (comparator.compare(min.left, p.left, min.right, p.right) < 0) {
                min = p;
            }
        }
        return OptionalPair.of(min.left, min.right);
    }

    @Override
    public OptionalPair<T, U> max(BiComparator<? super T, ? super U> comparator) {
        List<Pair<T,U>> pairs = pairStream.toList();
        if(pairs.isEmpty())return OptionalPair.empty();
        Pair<T,U> max = pairs.get(0);
        for (Pair<T, U> p : pairs) {
            if (comparator.compare(max.left, p.left, max.right, p.right) > 0) {
                max = p;
            }
        }
        return OptionalPair.of(max.left, max.right);
    }

    @Override
    public long count() {
        return pairStream.count();
    }

    @Override
    public boolean anyMatch(BiPredicate<? super T, ? super U> predicate) {
        return pairStream.anyMatch(p -> predicate.test(p.left, p.right));
    }

    @Override
    public boolean allMatch(BiPredicate<? super T, ? super U> predicate) {
        return pairStream.allMatch(p -> predicate.test(p.left, p.right));
    }

    @Override
    public boolean noneMatch(BiPredicate<? super T, ? super U> predicate) {
        return pairStream.noneMatch(p -> predicate.test(p.left, p.right));
    }

    @Override
    public OptionalPair<T, U> findFirst() {
        return pairStream.findFirst().map(tuPair -> new OptionalPair<>(tuPair.left, tuPair.right)).orElseGet(OptionalPair::empty);
    }

    @Override
    public OptionalPair<T, U> findAny() {
        return pairStream.findAny().map(tuPair -> new OptionalPair<>(tuPair.left, tuPair.right)).orElseGet(OptionalPair::empty);
    }

    public static <T,U> PairStream<T,U> empty(){
        return new PairStream<>(Stream.empty());
    }

    public static <T,U> PairStream<T,U> of(List<T> tList, List<U> uList){
        return new PairStream<>(tList, uList);
    }

    @Override
    public IPairStream<List<T>, List<U>> partition(int size) {
        List<T> tList = new ArrayList<>();
        List<U> uList = new ArrayList<>();
        pairStream.forEach(p -> {
            tList.add(p.left);
            uList.add(p.right);
        });
        List<List<T>> tLists = ListUtils.partition(tList, size);
        List<List<U>> uLists = ListUtils.partition(uList, size);
        return new PairStream<>(tLists, uLists);
    }

    @SuppressWarnings("unchecked")
    public PairStream(List<T> tList, List<U> uList) {
        Pair<T, U>[] pairs = new Pair[Math.max(tList.size(), uList.size())];
        for (int i = 0; i < Math.max(tList.size(), uList.size()); i++) {
            pairs[i] = new Pair<>(tList.get(i), uList.get(i));
        }
        this.pairStream = Arrays.stream(pairs);
    }

    private PairStream(Stream<Pair<T, U>> pairStream) {
        this.pairStream = pairStream;
    }


}
