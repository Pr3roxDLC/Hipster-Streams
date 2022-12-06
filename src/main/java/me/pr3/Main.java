package me.pr3;

import me.pr3.streams.api.streams.ISingleStream;

import java.util.ArrayList;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {

        System.out.println((Object)ISingleStream.of("aaa", "bbb", "ccc")
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
                .collect(ArrayList::new, ArrayList::add, (list, r) -> list.add(r.toString())));
    }

}