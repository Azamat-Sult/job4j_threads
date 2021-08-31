package ru.job4j.concurrent.threadpool;

import ru.job4j.concurrent.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks;

    public ThreadPool(int queueMaxSize) {
        tasks = new SimpleBlockingQueue<>(queueMaxSize);
        int size = Runtime.getRuntime().availableProcessors();
        for (int count = 0; count < size; count++) {
            Thread thread = new PoolThread(tasks);
            thread.start();
            threads.add(thread);
        }
    }

    public void work(Runnable job) {
        try {
            tasks.offer(job);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        System.out.println("ThreadPool shutdown...");
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool(12);
        threadPool.work(new Task("Task 1"));
        threadPool.work(new Task("Task 2"));
        threadPool.work(new Task("Task 3"));
        threadPool.work(new Task("Task 4"));
        threadPool.work(new Task("Task 5"));
        threadPool.work(new Task("Task 6"));
        threadPool.work(new Task("Task 7"));
        threadPool.work(new Task("Task 8"));
        threadPool.work(new Task("Task 9"));
        threadPool.work(new Task("Task 10"));
        threadPool.work(new Task("Task 11"));
        threadPool.work(new Task("Task 12"));
        threadPool.work(new Task("Task 13"));
        threadPool.work(new Task("Task 14"));
        Thread.sleep(15000);
        threadPool.shutdown();
    }
}
