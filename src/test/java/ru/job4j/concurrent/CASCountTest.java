package ru.job4j.concurrent;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CASCountTest {

    private class ThreadCount extends Thread {
        private final CASCount count;

        private ThreadCount(final CASCount count) {
            this.count = count;
        }

        @Override
        public void run() {
            for (int index = 0; index < 10; index++) {
                count.increment();
            }
        }
    }

    @Test
    public void whenExecute3ThreadThen30() throws InterruptedException {
        CASCount count = new CASCount();

        Thread first = new ThreadCount(count);
        Thread second = new ThreadCount(count);
        Thread third = new ThreadCount(count);

        first.start();
        second.start();
        third.start();

        first.join();
        second.join();
        third.join();

        assertEquals(count.get(), 30);
    }
}