package ru.job4j.concurrent;

public final class Node<T> {
    private final Node<T> next;
    private final T value;

    public Node(Node<T> next, T value) {
        this.next = next;
        this.value = value;
    }

    public Node<T> getNext() {
        return next;
    }

    public Node<T> setNext(Node<T> next) {
        return new Node<>(next, value);
    }

    public T getValue() {
        return value;
    }

    public Node<T> setValue(T value) {
        return new Node<>(next, value);
    }
}
