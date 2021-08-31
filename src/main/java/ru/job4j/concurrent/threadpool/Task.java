package ru.job4j.concurrent.threadpool;

public class Task implements Runnable {
    private final String taskName;

    public Task(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void run() {
        try {
            for (int index = 0; index < 5; index++) {
                System.out.println(Thread.currentThread().getName() + " executing task: " + taskName);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(taskName + " ends");
    }

    @Override
    public String toString() {
        return "Task{" + "taskName='" + taskName + '\'' + '}';
    }
}
