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

    public void offer(T value) throws InterruptedException {
        synchronized (monitor) {
            while (queue.size() == queueMaxSize) {
                monitor.wait();
            }
            monitor.notifyAll();
            queue.add(value);
        }
    }

    public T poll() throws InterruptedException {
        synchronized (monitor) {
            while (queue.size() == 0) {
                monitor.wait();
            }
            monitor.notifyAll();
            return queue.poll();
        }
    }

    public int getCurrentSize() {
        synchronized (monitor) {
            return queue.size();
        }
    }
}