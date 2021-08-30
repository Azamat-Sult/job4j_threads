package ru.job4j.concurrent.singleton;

public class SingleCheckedLockingTrackerSingle {
    private static SingleCheckedLockingTrackerSingle instance;

    private SingleCheckedLockingTrackerSingle() {
    }

    public static synchronized SingleCheckedLockingTrackerSingle getInstance() {
        if (instance == null) {
            instance = new SingleCheckedLockingTrackerSingle();
        }
        return instance;
    }

    public Item add(Item model) {
        return model;
    }

    public static void main(String[] args) {
        SingleCheckedLockingTrackerSingle tracker = SingleCheckedLockingTrackerSingle.getInstance();
    }
}
