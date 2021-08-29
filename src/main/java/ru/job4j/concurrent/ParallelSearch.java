package ru.job4j.concurrent;

public class ParallelSearch {
    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        final Thread consumer = new Thread(
                () -> {
                    while (!Thread.interrupted()) {
                        try {
                            queue.poll();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    System.out.println("Consumer stopped");
                },
                "Consumer"
        );
        consumer.start();

        new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        try {
                            queue.offer(index);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Producer stopped");
                    consumer.interrupt();
                },
                "Producer"

        ).start();
    }
}
