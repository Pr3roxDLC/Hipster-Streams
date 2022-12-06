package me.pr3;

import me.pr3.streams.api.streams.ISingleStream;

import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        ISingleStream.of("aaa", "bbb", "ccc").map(String::toUpperCase).forEach(System.out::println);
    }

}