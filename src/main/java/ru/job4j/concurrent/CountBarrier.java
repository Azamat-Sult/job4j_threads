package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class CountBarrier {
    private final Object monitor = this;

    private final int total;
    @GuardedBy("monitor")
    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public synchronized int getCount() {
        synchronized (monitor) {
            return count;
        }
    }

    public void count() {
        synchronized (monitor) {
            count++;
            monitor.notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (count < total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) {
        CountBarrier countBarrier = new CountBarrier(100);

        Thread waitingThread1 = new Thread(
                () -> {
                    countBarrier.await();
                    System.out.println(Thread.currentThread().getName() + " thread is working");
                },
                "Counter1"
        );
        Thread waitingThread2 = new Thread(
                () -> {
                    countBarrier.await();
                    System.out.println(Thread.currentThread().getName() + " thread is working");
                },
                "Counter2"
        );

        waitingThread1.start();
        waitingThread2.start();

        for (int count = 0; count < countBarrier.total; count++) {
            countBarrier.count();
            System.out.print("\rCount = " + countBarrier.getCount() + " , Total = " + countBarrier.total);
        }
        System.out.println();
    }
}
