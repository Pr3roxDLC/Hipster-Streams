package me.pr3.streams.impl;

import me.pr3.streams.api.functions.tri.*;
import me.pr3.streams.api.streams.ISingleStream;
import me.pr3.streams.api.streams.ITripletStream;
import me.pr3.streams.impl.tupels.OptionalTriplet;
import me.pr3.streams.impl.tupels.Triplet;
import org.apache.commons.collections4.ListUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.*;
import java.util.stream.*;

public class TripletStream<T, U, V> implements ITripletStream<T, U, V> {

    private final Stream<Triplet<T, U, V>> tripletStream;

    @Override
    public ITripletStream<T, U, V> filter(TriPredicate<? super T, ? super U, ? super V> predicate) {
        return new TripletStream<>(tripletStream.filter(t -> predicate.test(t.left, t.middle, t.right)));
    }

    @Override
    public <A, B, C> ITripletStream<A, B, C> mapSeparate(Function<? super T, ? extends A> mapperA, Function<? super U, ? extends B> mapperB, Function<? super V, ? extends C> mapperC) {
        return new TripletStream<>(tripletStream.map(t -> new Triplet<A, B, C>(mapperA.apply(t.left), mapperB.apply(t.middle), mapperC.apply(t.right))));
    }

    @Override
    public <A, B, C> ITripletStream<A, B, C> mapTogether(TriFunction<? super T, ? super U, ? super V, A> aTriFunction, TriFunction<? super T, ? super U, ? super V, B> bTriFunction, TriFunction<? super T, ? super U, ? super V, C> cTriFunction) {
        return new TripletStream<>(tripletStream.map(t ->
                new Triplet<>(
                        aTriFunction.apply(t.left, t.middle, t.right),
                        bTriFunction.apply(t.left, t.middle, t.right),
                        cTriFunction.apply(t.left, t.middle, t.right))));
    }

    @Override
    public <A> ISingleStream<A> mapToSingle(TriFunction<T, U, V, A> triFunction) {
        return new SingleStream<A>(tripletStream.map(t -> triFunction.apply(t.left, t.middle, t.right)).toList());
    }

    @Override
    public IntStream mapToInt(TriToIntFunction<? super T, ? super U, ? super V> mapper) {
        return tripletStream.mapToInt(t -> mapper.applyAsInt(t.left, t.middle, t.right));
    }

    @Override
    public LongStream mapToLong(TriToLongFunction<? super T, ? super U, ? super V> mapper) {
        return tripletStream.mapToLong(t -> mapper.applyAsLong(t.left, t.middle, t.right));
    }

    @Override
    public DoubleStream mapToDouble(TriToDoubleFunction<? super T, ? super U, ? super V> mapper) {
        return tripletStream.mapToDouble(t -> mapper.applyAsDouble(t.left, t.middle, t.right));

    }

    @Override
    public <A, B, C> ITripletStream<A, B, C> flatMap(TriFunction<? super T, ? super U, ? super V, ? extends ITripletStream<A, B, C>> mapper) {
        return new TripletStream<>(tripletStream.flatMap(t -> mapper.apply(t.left, t.middle, t.right).toList().stream()));
    }

    @Override
    public IntStream flatMapToInt(TriFunction<? super T, ? super U, ? super V, ? extends IntStream> mapper) {
        return tripletStream.flatMapToInt(t -> mapper.apply(t.left, t.middle, t.right));
    }

    @Override
    public LongStream flatMapToLong(TriFunction<? super T, ? super U, ? super V, ? extends LongStream> mapper) {
        return tripletStream.flatMapToLong(t -> mapper.apply(t.left, t.middle, t.right));

    }

    @Override
    public DoubleStream flatMapToDouble(TriFunction<? super T, ? super U, ? super V, ? extends DoubleStream> mapper) {
        return tripletStream.flatMapToDouble(t -> mapper.apply(t.left, t.middle, t.right));

    }

    @Override
    public ITripletStream<T, U, V> distinct() {
        return new TripletStream<>(tripletStream.distinct());
    }

    @Override
    public ITripletStream<T, U, V> sorted() {
        return new TripletStream<>(tripletStream.sorted());
    }

    @Override
    public ITripletStream<T, U, V> peek(TriConsumer<? super T, ? super U, ? super V> action) {
        return new TripletStream<>(tripletStream.peek(t -> action.accept(t.left, t.middle, t.right)));
    }

    @Override
    public ITripletStream<T, U, V> limit(long maxSize) {
        return new TripletStream<>(tripletStream.limit(maxSize));
    }

    @Override
    public ITripletStream<T, U, V> skip(long n) {
        return new TripletStream<>(tripletStream.skip(n));
    }

    @Override
    public void forEach(TriConsumer<? super T, ? super U, ? super V> action) {
        tripletStream.forEach(t -> action.accept(t.left, t.middle, t.right));
    }

    @Override
    public void forEachOrdered(TriConsumer<? super T, ? super U, ? super V> action) {
        tripletStream.forEachOrdered(t -> action.accept(t.left, t.middle, t.right));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Triplet<T, U, V>[] toArray() {
        return tripletStream.toList().toArray(new Triplet[0]);
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return tripletStream.toArray(generator);
    }

    @Override
    public Triplet<T, U, V> reduce(T identity1, U identity2, V identity3, BiFunction<T, T, T> tAccumulator, BiFunction<U, U, U> uAccumulator, BiFunction<V, V, V> vAccumulator) {
        T tResult = identity1;
        U uResult = identity2;
        V vResult = identity3;
        for (Triplet<T, U, V> triplet : tripletStream.toList()) {
            tResult = tAccumulator.apply(tResult, triplet.left);
            uResult = uAccumulator.apply(uResult, triplet.middle);
            vResult = vAccumulator.apply(vResult, triplet.right);
        }
        return new Triplet<>(tResult, uResult, vResult);
    }

    @Override
    public OptionalTriplet<T, U, V> reduce(BiFunction<T, T, T> tAccumulator, BiFunction<U, U, U> uAccumulator, BiFunction<V, V, V> vAccumulator) {
        boolean foundAny = false;
        T tResult = null;
        U uResult = null;
        V vResult = null;
        for (Triplet<T, U, V> triplet : tripletStream.toList()) {
            if (!foundAny) {
                foundAny = true;
                tResult = triplet.left;
                uResult = triplet.middle;
                vResult = triplet.right;
            } else {
                tResult = tAccumulator.apply(tResult, triplet.left);
                uResult = uAccumulator.apply(uResult, triplet.middle);
                vResult = vAccumulator.apply(vResult, triplet.right);
            }

        }
        return foundAny ? OptionalTriplet.of(tResult, uResult, vResult) : OptionalTriplet.empty();
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> aAccumulator, BiConsumer<R, ? super U> uAccumulator, BiConsumer<R, ? super V> vAccumulator) {
        R container = supplier.get();
        for (Triplet<T, U, V> triplet : tripletStream.toList()) {
            aAccumulator.accept(container, triplet.left);
            uAccumulator.accept(container, triplet.middle);
            vAccumulator.accept(container, triplet.right);
        }
        return container;
    }

    //TODO Implement this
    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        return null;
    }

    @Override
    public OptionalTriplet<T, U, V> min(TriComparator<? super T, ? super U, ? super V> comparator) {
        List<Triplet<T, U, V>> triplets = tripletStream.toList();
        if (triplets.isEmpty()) return OptionalTriplet.empty();
        Triplet<T, U, V> min = triplets.get(0);
        for (Triplet<T, U, V> t : triplets) {
            if (Integer.signum(comparator.compare(min.left, t.left, min.middle, t.middle, min.right, t.right)) < 0) {
                min = t;
            }
        }
        return OptionalTriplet.of(min.left, min.middle, min.right);
    }

    @Override
    public OptionalTriplet<T, U, V> max(TriComparator<? super T, ? super U, ? super V> comparator) {
        List<Triplet<T, U, V>> triplets = tripletStream.toList();
        if (triplets.isEmpty()) return OptionalTriplet.empty();
        Triplet<T, U, V> min = triplets.get(0);
        for (Triplet<T, U, V> t : triplets) {
            if (Integer.signum(comparator.compare(min.left, t.left, min.middle, t.middle, min.right, t.right)) > 0) {
                min = t;
            }
        }
        return OptionalTriplet.of(min.left, min.middle, min.right);
    }



    @Override
    public long count() {
        return tripletStream.count();
    }

    @Override
    public boolean anyMatch(TriPredicate<? super T, ? super U, ? super V> predicate) {
        return tripletStream.anyMatch(t -> predicate.test(t.left, t.middle, t.right));
    }

    @Override
    public boolean allMatch(TriPredicate<? super T, ? super U, ? super V> predicate) {
        return tripletStream.allMatch(t -> predicate.test(t.left, t.middle, t.right));
    }

    @Override
    public boolean noneMatch(TriPredicate<? super T, ? super U, ? super V> predicate) {
        return tripletStream.noneMatch(t -> predicate.test(t.left, t.middle, t.right));
    }

    @Override
    public OptionalTriplet<T, U, V> findFirst() {
        Optional<Triplet<T, U, V>> first = tripletStream.findFirst();
        if (first.isPresent()) {
            Triplet<T, U, V> first2 = first.get();
            return new OptionalTriplet<>(first2.left, first2.middle, first2.right);
        }
        return OptionalTriplet.empty();
    }

    @Override
    public OptionalTriplet<T, U, V> findAny() {
        Optional<Triplet<T, U, V>> first = tripletStream.findAny();
        if (first.isPresent()) {
            Triplet<T, U, V> first2 = first.get();
            return new OptionalTriplet<>(first2.left, first2.middle, first2.right);
        }
        return OptionalTriplet.empty();
    }

    @Override
    public ITripletStream<List<T>, List<U>, List<V>> partition(int size) {
        List<T> tList = new ArrayList<>();
        List<U> uList = new ArrayList<>();
        List<V> vList = new ArrayList<>();
        tripletStream.forEach(t -> {
            tList.add(t.left);
            uList.add(t.middle);
            vList.add(t.right);
        });
        List<List<T>> tLists = ListUtils.partition(tList, size);
        List<List<U>> uLists = ListUtils.partition(uList, size);
        List<List<V>> vLists = ListUtils.partition(vList, size);

        return new TripletStream<>(tLists, uLists, vLists);
    }

    public TripletStream(Stream<Triplet<T, U, V>> stream) {
        this.tripletStream = stream;
    }

    public TripletStream(List<T> tList, List<U> uList, List<V> vList) {
        Triplet<T, U, V>[] triplets = new Triplet[IntStream.of(tList.size(), uList.size(), vList.size()).max().orElse(0)];
        for (int i = 0; i < IntStream.of(tList.size(), uList.size(), vList.size()).max().orElse(0); i++) {
            triplets[i] = new Triplet<>(tList.get(i), uList.get(i), vList.get(i));
        }
        this.tripletStream = Arrays.stream(triplets);
    }
}
