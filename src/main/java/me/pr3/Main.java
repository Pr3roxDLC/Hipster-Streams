package me.pr3;

import me.pr3.streams.api.streams.ISingleStream;

import java.util.function.Function;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        ISingleStream.of("aaa", "bbb", "ccc")
                .map(String::toUpperCase)
                .mapToPair(Function.identity(), String::length)
                .mapSeparate(
                        a -> a.concat("Hello World"),
                        b -> b + 10
                )
                .mapTogether(
                        (a,b) -> a.concat(b.toString()),
                        (a,b) -> b * a.length()
                )
                .forEach((a,b) -> System.out.println(a + b));
    }

}