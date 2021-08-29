package ru.job4j.concurrent.nbcache;

public class OptimisticException extends RuntimeException {

    public OptimisticException(String message) {
        super(message);
    }
}
