package ru.job4j.concurrent;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static class Sums {
        private final int rowSum;
        private final int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }
    }

    public static Sums[] sum(int[][] matrix) {
        int rowSum;
        int columnSum;
        Sums[] rsl = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            rowSum = 0;
            columnSum = 0;
            for (int j = 0; j < matrix.length; j++) {
                rowSum += matrix[i][j];
                columnSum += matrix[j][i];
            }
            rsl[i] = new Sums(rowSum, columnSum);
        }
        return rsl;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] rsl = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            rsl[i] = calcSum(matrix, i).get();
        }
        return rsl;
    }

    public static CompletableFuture<Sums> calcSum(int[][] data, int index) {
        return CompletableFuture.supplyAsync(() -> {
            int rowSum = 0;
            int columnSum = 0;
            for (int i = 0; i < data.length; i++) {
                rowSum += data[index][i];
                columnSum += data[i][index];
            }
            return new Sums(rowSum, columnSum);
        });
    }
}
