package ru.job4j.concurrent.threadpool;

import ru.job4j.concurrent.SimpleBlockingQueue;

public class PoolThread extends Thread {
    private final SimpleBlockingQueue<Runnable> queue;

    public PoolThread(SimpleBlockingQueue<Runnable> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " started");
        Runnable task = null;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                task = queue.poll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            if (task != null) {
                task.run();
                task = null;
            }
        }
        System.out.println(Thread.currentThread().getName() + " stopped");
    }
}