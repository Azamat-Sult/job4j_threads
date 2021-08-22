package ru.job4j.concurrent;

public class Wget {
    public static void main(String[] args) {
        Thread loading = new Thread(
                () -> {
                    for (int index = 0; index <= 100; index++) {
                        System.out.print("\rLoading : " + index  + "%");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        loading.start();
    }
}
