package me.pr3;

import me.pr3.streams.impl.TripletStream;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println(new TripletStream<>(List.of(1, 2, 3), List.of("a", "b", "c"), List.of("", "", "")).toList());
        System.out.println(new TripletStream<>(List.of(1, 2, 3), List.of("a", "b", "c"), List.of("", "", "")).mapSeparate(
                        a -> a,
                        b -> b.toUpperCase(),
                        c -> "test")
                .peek((a,b,c) -> System.out.println(a)).toList()
        );


    }

}