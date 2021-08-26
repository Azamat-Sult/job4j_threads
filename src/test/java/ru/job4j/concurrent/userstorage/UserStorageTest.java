package ru.job4j.concurrent.userstorage;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UserStorageTest {

    private static class ThreadTransfer extends Thread {
        private final UserStorage userStorage;
        private final int fromId;
        private final int toId;
        private final int amount;

        private ThreadTransfer(UserStorage userStorage, int fromId, int toId, int amount) {
            this.userStorage = userStorage;
            this.fromId = fromId;
            this.toId = toId;
            this.amount = amount;
        }

        @Override
        public void run() {
            this.userStorage.transfer(fromId, toId, amount);
        }
    }

    @Test
    public void whenExecute4ThreadThenUser4Amount1000()  throws InterruptedException {
        User user1 = new User(1, 200);
        User user2 = new User(2, 300);
        User user3 = new User(3, 500);
        User user4 = new User(4, 0);

        UserStorage storage = new UserStorage();

        storage.add(user1);
        storage.add(user2);
        storage.add(user3);
        storage.add(user4);

        Thread first = new ThreadTransfer(storage, 1, 4, 200);
        Thread second = new ThreadTransfer(storage, 2, 4, 300);
        Thread third = new ThreadTransfer(storage, 3, 4, 500);
        Thread last = new ThreadTransfer(storage, 1, 2, 1000);

        first.start();
        second.start();
        third.start();
        last.start();

        first.join();
        second.join();
        third.join();
        last.join();

        assertEquals(storage.getUserById(4).getAmount(), 1000);
        assertEquals(storage.getUserById(1).getAmount(), 0);
        assertEquals(storage.getUserById(2).getAmount(), 0);
    }
}