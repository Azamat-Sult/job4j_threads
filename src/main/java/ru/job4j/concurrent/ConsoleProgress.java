package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        Character[] sphere = {'\\', '|', '/', '-'};
        int index = 0;
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("\rLoading... " + sphere[index]);
                if (index < sphere.length - 1) {
                    index++;
                } else {
                    index = 0;
                }
                Thread.sleep(300);
            }
        } catch (InterruptedException e) {
            //e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(4000); /* симулируем выполнение параллельной задачи в течение 1 секунды. */
        progress.interrupt();
    }
}
