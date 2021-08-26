package ru.job4j.concurrent.userstorage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final List<User> storage = new ArrayList<>();

    public synchronized boolean add(User user) {
        boolean rsl = false;
        if (getIndexById(user.getId()) == -1) {
            storage.add(user);
            rsl = true;
        }
        return rsl;
    }

    public synchronized boolean update(User user) {
        boolean rsl = false;
        int indexToUpd = getIndexById(user.getId());
        if (indexToUpd != -1) {
            storage.set(indexToUpd, user);
            rsl = true;
        }
        return rsl;
    }

    public synchronized boolean delete(User user) {
        boolean rsl = false;
        int indexToDel = getIndexById(user.getId());
        if (indexToDel != -1) {
            storage.remove(indexToDel);
            rsl = true;
        }
        return rsl;
    }

    public synchronized User getUserById(int id) {
        User rsl = null;
        int indexToReturn = getIndexById(id);
        if (indexToReturn != -1) {
            rsl = storage.get(indexToReturn);
        }
        return rsl;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        int indexFrom = getIndexById(fromId);
        int fromAmount = storage.get(indexFrom).getAmount();
        int indexTo = getIndexById(toId);
        int toAmount = storage.get(indexTo).getAmount();
        if (indexFrom != -1 && indexTo != -1 && fromAmount >= amount) {
            storage.get(indexFrom).setAmount(fromAmount - amount);
            storage.get(indexTo).setAmount(toAmount + amount);
            rsl = true;
        }
        return rsl;
    }

    private synchronized int getIndexById(int id) {
        int rsl = -1;
        for (int index = 0; index < storage.size(); index++) {
            if (storage.get(index).getId() == id) {
                rsl = index;
                break;
            }
        }
        return rsl;
    }
}
