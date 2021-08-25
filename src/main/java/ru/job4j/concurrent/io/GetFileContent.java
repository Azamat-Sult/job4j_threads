package ru.job4j.concurrent.io;

import java.io.*;
import java.util.function.Predicate;

public final class GetFileContent {
    private final File file;

    public GetFileContent(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Character> filter) throws IOException {
        StringBuilder rsl = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            int data;
            while ((data = in.read()) != -1) {
                char dataChar = (char) data;
                if (filter.test(dataChar)) {
                    rsl.append(dataChar);
                }
            }
        }
        return rsl.toString();
    }
}
