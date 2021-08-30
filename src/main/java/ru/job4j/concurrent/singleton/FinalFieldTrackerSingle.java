package ru.job4j.concurrent.singleton;

public class FinalFieldTrackerSingle {
    private static final FinalFieldTrackerSingle INSTANCE = new FinalFieldTrackerSingle();

    private FinalFieldTrackerSingle() {
    }

    public static FinalFieldTrackerSingle getInstance() {
        return INSTANCE;
    }

    public Item add(Item model) {
        return model;
    }

    public static void main(String[] args) {
        FinalFieldTrackerSingle tracker = FinalFieldTrackerSingle.getInstance();
    }
}
