package ru.job4j.concurrent.io;

import java.io.*;

public final class SaveFileContent {
    private final File file;

    public SaveFileContent(File file) {
        this.file = file;
    }

    public void saveContent(String content) throws IOException {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < content.length(); i += 1) {
                out.write(content.charAt(i));
            }
        }
    }
}
