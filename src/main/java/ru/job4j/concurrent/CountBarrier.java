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
            while (count >= total) {
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
        Thread counter1 = new Thread(
                () -> {
                    while (true) {
                        countBarrier.count();
                        System.out.println(Thread.currentThread().getName()
                                + " counted to "
                                + countBarrier.getCount());
                        countBarrier.await();
                    }
                },
                "Counter1"
        );
        Thread counter2 = new Thread(
                () -> {
                    while (true) {
                        countBarrier.count();
                        System.out.println(Thread.currentThread().getName()
                                + " counted to "
                                + countBarrier.getCount());
                        countBarrier.await();
                    }
                },
                "Counter2"
        );
        counter1.start();
        counter2.start();

        Thread.State counter1State = counter1.getState();
        Thread.State counter2State = counter2.getState();

        while (counter1State.equals(Thread.State.RUNNABLE)
                || counter2State.equals(Thread.State.RUNNABLE)) {
            counter1State = counter1.getState();
            counter2State = counter2.getState();
        }
        System.out.println("Counter1 is in " + counter1State + " state");
        System.out.println("Counter2 is in " + counter2State + " state");
    }
}
