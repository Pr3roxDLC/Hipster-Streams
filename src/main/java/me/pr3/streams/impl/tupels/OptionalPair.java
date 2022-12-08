package me.pr3.streams.impl.tupels;

import me.pr3.streams.impl.PairStream;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class OptionalPair<T, U> {
    private static final OptionalPair<?, ?> EMPTY = new OptionalPair<>(null, null);

    private final T value1;
    private final U value2;

    public OptionalPair(T t, U u) {
        this.value1 = t;
        this.value2 = u;
    }

    public static <T, U> OptionalPair<T, U> empty() {
        @SuppressWarnings("unchecked")
        OptionalPair<T, U> t = (OptionalPair<T, U>) EMPTY;
        return t;
    }

    @Contract("_, _ -> new")
    public static <T, U> @NotNull OptionalPair<T, U> of(T value1, U value2) {
        return new OptionalPair<>(value1, value2);
    }

    @SuppressWarnings("unchecked")
    public static <T, U> OptionalPair<T, U> ofNullable(T value1, U value2) {
        return value1 == null || value2 == null ? (OptionalPair<T, U>) EMPTY
                : new OptionalPair<>(value1, value2);
    }

    public Pair<T, U> get() {
        if (value1 == null || value2 == null) {
            throw new NoSuchElementException("No value present");
        }
        return new Pair<>(value1, value2);
    }


    public boolean isPresent(){
        return value1!=null||value2!=null;
    }

    public boolean isEmpty(){
        return !isPresent();
    }

    public void ifPresent(BiConsumer<? super T, ? super U> action) {
        if (value1 != null && value2 != null) {
            action.accept(value1, value2);
        }
    }

    public void ifPresentOrElse(BiConsumer<? super T, ? super U> action, Runnable emptyAction) {
        if (value1 != null && value2 != null) {
            action.accept(value1, value2);
        } else {
            emptyAction.run();
        }
    }

    public OptionalPair<T, U> filter(BiPredicate<? super T, ? super U> predicate) {
        Objects.requireNonNull(predicate);
        if (!isPresent()) {
            return this;
        } else {
            return predicate.test(value1, value2) ? this : empty();
        }
    }

    public <R, S> OptionalPair<R, S> map(Function<? super T, ? extends R> mapper1, Function<? super U, ? extends S> mapper2) {
        Objects.requireNonNull(mapper1);
        Objects.requireNonNull(mapper2);
        if (!isPresent()) {
            return empty();
        } else {
            return OptionalPair.ofNullable(mapper1.apply(value1), mapper2.apply(value2));
        }
    }

    public OptionalPair<T, U> or(Supplier<? extends T> supplier1, Supplier<? extends U> supplier2) {
        Objects.requireNonNull(supplier1);
        Objects.requireNonNull(supplier2);
        if (isPresent()) {
            return this;
        } else {
            return new OptionalPair<>(Objects.requireNonNull(supplier1.get()), Objects.requireNonNull(supplier2.get()));
        }
    }

    public PairStream<T, U> stream() {
        if (!isPresent()) {
            return PairStream.empty();
        } else {
            return PairStream.of(List.of(value1), List.of(value2));
        }
    }

    public Pair<T,U> orElse(T otherT, U otherU) {
        return value1 != null && value2 != null ? new Pair<>(value1, value2) : new Pair<>(otherT, otherU);
    }

    public Pair<T,U> orElseGet(Supplier<? extends T> supplier1, Supplier<? extends U> supplier2) {
        return value1 != null && value2 != null ? new Pair<>(value1, value2) : new Pair<>(supplier1.get(), supplier2.get());
    }

    public Pair<T,U> orElseThrow() {
        if (value1 == null || value2 == null) {
            throw new NoSuchElementException("No value present");
        }
        return new Pair<>(value1, value2);
    }

    public <X extends Throwable> Pair<T,U> orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (value1 != null && value2 != null) {
            return new Pair<>(value1, value2);
        } else {
            throw exceptionSupplier.get();
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj instanceof OptionalPair<?, ?> other
                && Objects.equals(value1, other.value1) && Objects.equals(value2, other.value2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value1, value2);
    }

    @Override
    public String toString() {
        if (value1 == null && value2 == null) return "OptionalPair.empty";
        if (value1 != null && value2 == null) return String.format("OptionalPair[%s,]", value1);
        if (value1 == null) return String.format("OptionalPair[,%s]", value2);
        return String.format("OptionalPair[%s,%s]", value1, value2);

    }

}
