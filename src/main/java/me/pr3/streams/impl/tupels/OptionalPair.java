package me.pr3.streams.impl.tupels;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

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

    public Pair<T, U> get() {
        if (value1 == null || value2 == null) {
            throw new NoSuchElementException("No value present");
        }
        return new Pair<>(value1, value2);
    }

    public boolean areBothPresent() {
        return value1 != null && value2 != null;
    }

    public boolean isAtLeastOnePresent() {
        return value1 != null || value2 != null;
    }

    public boolean isNonePresent() {
        return value1 == null && value2 == null;
    }

    public boolean isExactlyOnePresent() {
        return value1 == null ^ value2 == null;
    }

    public void ifBothArePresent(BiConsumer<? super T, ? super U> action) {
        if (value1 != null && value2 != null) {
            action.accept(value1, value2);
        }
    }

    public void ifNoneArePresent(Runnable runnable) {
        if (value1 == null && value2 == null) {
            runnable.run();
        }
    }

    public OptionalPair<T, U> orIfNoneArePresent(Supplier<? extends OptionalPair<? extends T, ? extends U>> supplier) {
        Objects.requireNonNull(supplier);
        if (areBothPresent()) {
            return this;
        } else {
            @SuppressWarnings("unchecked")
            OptionalPair<T, U> r = (OptionalPair<T, U>) supplier.get();
            return Objects.requireNonNull(r);
        }
    }

    public OptionalPair<T, U> orIfExactlyOneIsPresent(Supplier<? extends OptionalPair<? extends T, ? extends U>> supplier) {
        Objects.requireNonNull(supplier);
        if (isExactlyOnePresent()) {
            return this;
        } else {
            @SuppressWarnings("unchecked")
            OptionalPair<T, U> r = (OptionalPair<T, U>) supplier.get();
            return Objects.requireNonNull(r);
        }
    }

    public OptionalPair<T, U> orIfAtLeastOneIsPresent(Supplier<? extends OptionalPair<? extends T, ? extends U>> supplier) {
        Objects.requireNonNull(supplier);
        if (isAtLeastOnePresent()) {
            return this;
        } else {
            @SuppressWarnings("unchecked")
            OptionalPair<T, U> r = (OptionalPair<T, U>) supplier.get();
            return Objects.requireNonNull(r);
        }
    }

    public Pair<T, U> orElseThrowIfBothAreNull() {
        if (value1 == null && value2 == null) {
            throw new NoSuchElementException("No value present");
        }
        return new Pair<>(value1, value2);
    }

    public Pair<T, U> orElseThrowIfExactlyOneIsNull() {
        if (value1 == null ^ value2 == null) {
            throw new NoSuchElementException("No value present");
        }
        return new Pair<>(value1, value2);
    }

    public Pair<T, U> orElseThrowIfAtLeastOneIsNull() {
        if (value1 == null || value2 == null) {
            throw new NoSuchElementException("No value present");
        }
        return new Pair<>(value1, value2);
    }

    public <X extends Throwable> Pair<T, U> orElseThrowIfBothAreNull(Supplier<? extends X> exceptionSupplier) throws X {
        if (value1 != null || value2 != null) {
            return new Pair<>(value1, value2);
        } else {
            throw exceptionSupplier.get();
        }
    }

    public <X extends Throwable> Pair<T, U> orElseThrowIfExactlyOneIsNull(Supplier<? extends X> exceptionSupplier) throws X {
        if ((value1 == null) == (value2 == null)) {
            return new Pair<>(value1, value2);
        } else {
            throw exceptionSupplier.get();
        }
    }

    public <X extends Throwable> Pair<T, U> orElseThrowIfAtLeastOneIsNull(Supplier<? extends X> exceptionSupplier) throws X {
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
