package me.pr3.streams.impl.tupels;

import me.pr3.streams.api.functions.tri.TriConsumer;
import me.pr3.streams.api.functions.tri.TriFunction;
import me.pr3.streams.api.functions.tri.TriPredicate;
import org.jetbrains.annotations.NotNull;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class OptionalTriplet <T,U,V>{

    private final T value1;
    private final U value2;
    private final V value3;
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
    @SuppressWarnings("unchecked")
    public static <T, U ,V> OptionalTriplet<T, U ,V> ofNullable(T value1, U value2, V value3) {
        return value1 == null || value2 == null || value3 == null ? (OptionalTriplet<T, U, V>) EMPTY
                : new OptionalTriplet<>(value1, value2, value3);
    }

    public Triplet<T,U,V> get() {
        if (value1 == null || value2 == null || value3 == null) {
            throw new NoSuchElementException("No value present");
        }
        return new Triplet<>(value1, value2, value3);
    }

    public boolean isPresent() {
        return value1 != null && value2 != null && value3 != null;
    }

    public boolean isEmpty() {
        return !isPresent();
    }

    public void ifPresentOrElse(TriConsumer<? super T, ? super U, ? super V> action, Runnable emptyAction) {
        if (value1 != null && value2 != null && value3 != null) {
            action.accept(value1, value2, value3);
        } else {
            emptyAction.run();
        }
    }

    public OptionalTriplet<T, U ,V> filter(TriPredicate<? super T, ? super U, ? super V> predicate) {
        Objects.requireNonNull(predicate);
        if (!isPresent()) {
            return this;
        } else {
            return predicate.test(value1, value2, value3) ? this : empty();
        }
    }

    public <A, B , C> OptionalTriplet<A, B, C> mapSeparate(Function<? super T, ? extends A> mapper1, Function<? super U, ? extends B> mapper2, Function<? super V, ? extends C> mapper3) {
        Objects.requireNonNull(mapper1);
        Objects.requireNonNull(mapper2);
        Objects.requireNonNull(mapper3);
        if (!isPresent()) {
            return empty();
        } else {
            return OptionalTriplet.ofNullable(mapper1.apply(value1), mapper2.apply(value2), mapper3.apply(value3));
        }
    }

    public <A, B , C> OptionalTriplet<A, B, C> mapTogether(TriFunction<? super T, ? super U, ? super V, OptionalTriplet<A,B,C>> mapper1) {
        Objects.requireNonNull(mapper1);
        if (!isPresent()) {
            return empty();
        } else {
            return mapper1.apply(value1, value2, value3);
        }
    }

    public OptionalTriplet<T, U ,V> or(Supplier<? extends T> supplier1, Supplier<? extends U> supplier2, Supplier<? extends V> supplier3) {
        Objects.requireNonNull(supplier1);
        Objects.requireNonNull(supplier2);
        Objects.requireNonNull(supplier3);
        if (isPresent()) {
            return this;
        } else {
            return OptionalTriplet.ofNullable(value1, value2, value3);
        }
    }

    //TODO implement this
//    public Stream<T> stream() {
//        if (!isPresent()) {
//            return Stream.empty();
//        } else {
//            return Stream.of(value);
//        }
//    }

    public Triplet<T, U, V> orElse(T otherT, U otherU, V otherV) {
        return value1 != null && value2 != null && value3 != null ? get(): new Triplet<>(otherT, otherU, otherV);
    }

    public Triplet<T, U, V> orElseGet(Supplier<? extends T> supplier1, Supplier<? extends U> supplier2, Supplier<? extends V> supplier3) {
        return value1 != null && value2 != null && value3 != null ? get() : new Triplet<>(supplier1.get(), supplier2.get(), supplier3.get());
    }

    public Triplet<T, U ,V> orElseThrow() {
        if (value1 == null || value2 == null ||  value3 == null) {
            throw new NoSuchElementException("No value present");
        }
        return get();
    }

    public <X extends Throwable> Triplet<T, U, V> orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (value1 != null && value2 != null && value3 != null ) {
            return get();
        } else {
            throw exceptionSupplier.get();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OptionalTriplet<?, ?, ?> that = (OptionalTriplet<?, ?, ?>) o;
        return Objects.equals(value1, that.value1) && Objects.equals(value2, that.value2) && Objects.equals(value3, that.value3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value1, value2, value3);
    }

    @Override
    public String toString() {
        return "OptionalTriplet["+value1+","+value2+","+value3+"]";
    }
}
