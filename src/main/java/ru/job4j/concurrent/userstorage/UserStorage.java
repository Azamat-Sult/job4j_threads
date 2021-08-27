package ru.job4j.concurrent.userstorage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final Map<Integer, User> storage = new HashMap<>();

    public synchronized boolean add(User user) {
        boolean rsl = false;
        if (!storage.containsKey(user.getId())) {
            storage.put(user.getId(), user);
            rsl = true;
        }
        return rsl;
    }

    public synchronized boolean update(User user) {
        boolean rsl = false;
        if (storage.containsKey(user.getId())) {
            storage.put(user.getId(), user);
            rsl = true;
        }
        return rsl;
    }

    public synchronized boolean delete(User user) {
        boolean rsl = false;
        if (storage.containsKey(user.getId())) {
            storage.remove(user.getId());
            rsl = true;
        }
        return rsl;
    }

    public synchronized User get(int index) {
        return storage.get(index);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        if (storage.containsKey(fromId) && storage.containsKey(toId)) {
            int fromAmount = storage.get(fromId).getAmount();
            if (fromAmount >= amount) {
                int toAmount = storage.get(toId).getAmount();
                storage.get(fromId).setAmount(fromAmount - amount);
                storage.get(toId).setAmount(toAmount + amount);
                rsl = true;
            }
        }
        return rsl;
    }
}
