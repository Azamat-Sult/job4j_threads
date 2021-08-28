package ru.job4j.concurrent;

import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void whenQueueFullThenProducerWait() throws InterruptedException {
        SimpleBlockingQueue<Integer> sbQueue = new SimpleBlockingQueue<>(5);

        Thread producer = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " start");
                    for (int index = 0; index < 10; index++) {
                        sbQueue.offer(index);
                    }
                    System.out.println(Thread.currentThread().getName() + " works done");
                },
                "Producer"
        );

        producer.start();
        Thread.sleep(1000);

        assertEquals(producer.getState(), Thread.State.WAITING);
    }

    @Test
    public void whenQueueEmptyThenConsumerWait() throws InterruptedException {
        SimpleBlockingQueue<Integer> sbQueue = new SimpleBlockingQueue<>(5);

        Thread consumer = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " start");
                    sbQueue.poll();
                    System.out.println(Thread.currentThread().getName() + " works done");
                },
                "Consumer"
        );

        consumer.start();
        Thread.sleep(1000);

        assertEquals(consumer.getState(), Thread.State.WAITING);
    }

    @Test
    public void when2Producer2ConsumerThenEmptyQueue() throws InterruptedException {
        SimpleBlockingQueue<Integer> sbQueue = new SimpleBlockingQueue<>(5);

        Thread producer1 = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " start");
                    for (int index = 0; index < 4; index++) {
                        sbQueue.offer(index);
                    }
                    System.out.println(Thread.currentThread().getName() + " works done");
                },
                "Producer 1"
        );

        Thread producer2 = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " start");
                    for (int index = 0; index < 4; index++) {
                        sbQueue.offer(index);
                    }
                    System.out.println(Thread.currentThread().getName() + " works done");
                },
                "Producer 2"
        );

        Thread consumer1 = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " start");
                    for (int index = 0; index < 4; index++) {
                        sbQueue.poll();
                    }
                    System.out.println(Thread.currentThread().getName() + " works done");
                },
                "Consumer 1"
        );

        Thread consumer2 = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " start");
                    for (int index = 0; index < 4; index++) {
                        sbQueue.poll();
                    }
                    System.out.println(Thread.currentThread().getName() + " works done");
                },
                "Consumer 2"
        );

        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();

        producer1.join();
        consumer1.join();
        producer2.join();
        consumer2.join();

        assertEquals(sbQueue.getCurrentSize(), 0);
    }
}