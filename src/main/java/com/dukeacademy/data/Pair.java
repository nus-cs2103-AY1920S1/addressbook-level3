package com.dukeacademy.data;

/**
 * Implementation of a pair data structure. Each pair is a tuple of 2 elements. In this implementation, the first
 * element is referred to as the head and the second element is referred to as the tail.
 * @param <T> the type parameter of the head of the pair
 * @param <U> the type parameter of the tail of the pair
 */
public class Pair<T, U> {
    private final T head;
    private final U tail;

    public Pair(T head, U tail) {
        this.head = head;
        this.tail = tail;
    }

    public T getHead() {
        return this.head;
    }

    public U getTail() {
        return this.tail;
    }
}
