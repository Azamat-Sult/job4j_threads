package ru.job4j.concurrent;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class RolColSumTest {

    @Test
    public void whenMatrix6x6ThenSum() {
        int[][] source = {
                {1, 2, 3, 4, 5, 6},
                {7, 8, 9, 10, 11, 12},
                {13, 14, 15, 16, 17, 18},
                {19, 20, 21, 22, 23, 24},
                {25, 26, 27, 28, 29, 30},
                {31, 32, 33, 34, 35, 36}
        };
        RolColSum.Sums[] expected = {
                new RolColSum.Sums(21, 96),
                new RolColSum.Sums(57, 102),
                new RolColSum.Sums(93, 108),
                new RolColSum.Sums(129, 114),
                new RolColSum.Sums(165, 120),
                new RolColSum.Sums(201, 126)
        };

        assertArrayEquals(RolColSum.sum(source), expected);
    }

    @Test
    public void whenMatrix6x6ThenAsyncSum() throws ExecutionException, InterruptedException {
        int[][] source = {
                {1, 2, 3, 4, 5, 6},
                {7, 8, 9, 10, 11, 12},
                {13, 14, 15, 16, 17, 18},
                {19, 20, 21, 22, 23, 24},
                {25, 26, 27, 28, 29, 30},
                {31, 32, 33, 34, 35, 36}
        };
        RolColSum.Sums[] expected = {
                new RolColSum.Sums(21, 96),
                new RolColSum.Sums(57, 102),
                new RolColSum.Sums(93, 108),
                new RolColSum.Sums(129, 114),
                new RolColSum.Sums(165, 120),
                new RolColSum.Sums(201, 126)
        };

        assertArrayEquals(RolColSum.asyncSum(source), expected);
    }
}