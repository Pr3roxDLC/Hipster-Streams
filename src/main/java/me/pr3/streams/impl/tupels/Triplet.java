package me.pr3.streams.impl.tupels;

import java.util.Objects;

public class Triplet<T,U,V>{
    public T left;
    public U middle;
    public V right;


    public Triplet(T left, U middle, V right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triplet<?, ?, ?> triplet = (Triplet<?, ?, ?>) o;
        return Objects.equals(left, triplet.left) && Objects.equals(middle, triplet.middle) && Objects.equals(right, triplet.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, middle, right);
    }

    @Override
    public String toString() {
        return "Triplet[" +
                 left +
                ", " + middle +
                ", " + right +
                ']';
    }
}
