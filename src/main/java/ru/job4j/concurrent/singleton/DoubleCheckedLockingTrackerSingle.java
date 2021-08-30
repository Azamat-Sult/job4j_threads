package ru.job4j.concurrent.singleton;

public class DoubleCheckedLockingTrackerSingle {
    private static volatile DoubleCheckedLockingTrackerSingle instance;

    private DoubleCheckedLockingTrackerSingle() {
    }

    public static DoubleCheckedLockingTrackerSingle getInstance() {
        if (instance == null) {
            synchronized (EnumTrackerSingle.class) {
                if (instance == null) {
                    instance = new DoubleCheckedLockingTrackerSingle();
                }
            }
        }
        return instance;
    }

    public Item add(Item model) {
        return model;
    }

    public static void main(String[] args) {
        DoubleCheckedLockingTrackerSingle tracker = DoubleCheckedLockingTrackerSingle.getInstance();
    }
}
