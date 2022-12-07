package me.pr3.streams.impl.tupels;

import java.util.Objects;

public class Pair <T,U>{
    public T left;
    public U right;
    public Pair(T left, U right){
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(left, pair.left) && Objects.equals(right, pair.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override
    public String toString() {
        return "Pair["+ left +
                "," + right +
                ']';
    }
}
