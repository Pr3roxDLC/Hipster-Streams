package me.pr3;

import me.pr3.streams.api.streams.ISingleStream;

import java.util.ArrayList;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {

     ISingleStream.of("aaaaaaa", "bbbb", "ccccc")
                .map(String::toUpperCase)
                .mapToPair(Function.identity(), String::length)
                .max((t1, t2, u1, u2) -> t2.length() < t1.length() ? -1 : 1)
                .ifBothArePresent((s, integer) -> {
                    System.out.println(s + " " + integer);
                });
    }

}