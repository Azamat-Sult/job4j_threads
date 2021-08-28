package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    private final Object monitor = this;
    @GuardedBy("monitor")
    private final Queue<T> queue = new LinkedList<>();
    private final int queueMaxSize;


    public SimpleBlockingQueue(int queueMaxSize) {
        this.queueMaxSize = queueMaxSize;
    }

    public void offer(T value) {
        synchronized (monitor) {
            while (queue.size() == queueMaxSize) {
                try {
                    System.out.println("Queue is full! All producer threads in waiting state");
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            if (queue.size() == 0) {
                System.out.println("Queue is empty! Notify all producer threads");
                monitor.notifyAll();
            }
            queue.add(value);
            System.out.println(Thread.currentThread().getName() + ": add " + value + " done");
        }
    }

    public T poll() {
        synchronized (monitor) {
            while (queue.size() == 0) {
                try {
                    System.out.println("Queue is empty! All consumer threads in waiting state");
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            if (queue.size() == queueMaxSize) {
                System.out.println("Queue is full! Notify all consumer threads");
                monitor.notifyAll();
            }
            T rsl = queue.poll();
            System.out.println(Thread.currentThread().getName() + ": get " + rsl + " done");
            return rsl;
        }
    }

    public int getCurrentSize() {
        synchronized (monitor) {
            return queue.size();
        }
    }
}