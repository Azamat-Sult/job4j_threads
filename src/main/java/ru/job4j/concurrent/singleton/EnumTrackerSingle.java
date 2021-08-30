package ru.job4j.concurrent.singleton;

public enum EnumTrackerSingle {
    INSTANCE;

    public Item add(Item model) {
        return model;
    }

    public static void main(String[] args) {
        EnumTrackerSingle tracker = EnumTrackerSingle.INSTANCE;
    }
}
