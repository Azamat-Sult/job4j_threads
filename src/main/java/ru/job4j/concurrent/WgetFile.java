package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class WgetFile implements Runnable {
    private final String url;
    private final String fileNameToSave;
    private final int speed;

    public WgetFile(String url, String fileNameToSave, int speed) {
        this.url = url;
        this.fileNameToSave = fileNameToSave;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileNameToSave)) {
            byte[] dataBuffer = new byte[speed];
            int bytesRead;
            long readTime;
            long stop;
            long start = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, speed)) != -1) {
                stop = System.currentTimeMillis();
                readTime = stop - start;
                if (readTime < 1000) {
                    Thread.sleep(1000 - readTime);
                }
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                start = System.currentTimeMillis();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 3) {
            throw new IllegalArgumentException("\n Correct usage: \n"
                    + "java -jar WgetFile.jar "
                    + "SOURCE_FILE_URL "
                    + "DEST_FILE.EXTENSION "
                    + "DOWNLOAD_SPEED(bytes per second)");
        }
        String url = args[0];
        String fileNameToSave = args[1];
        int speed = Integer.parseInt(args[2]);

        System.out.println(url);
        System.out.println(fileNameToSave);
        System.out.println(speed);

        //url = "https://raw.githubusercontent.com/Azamat-Sult/job4j_tracker/master/GC_Parallel_MemTraker.log";
        //fileNameToSave = "GC_Parallel_MemTraker.log";
        //speed = 2048;

        Thread wget = new Thread(new WgetFile(url, fileNameToSave, speed));
        wget.start();
        wget.join();
    }
}