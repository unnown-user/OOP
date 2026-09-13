package ru.nsu.asemenychev.Task_1_1_1;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


class HeapSortComplexity {

    @Test
    void time() {
        int[] sizes = {10000, 100000, 1000000, 10000000};

        System.out.println();
        System.out.println("n\t\t\ttime (ms)");
        for (int n : sizes) {
            int[] data = randomArray(n);
            int[] expected_data = data.clone();
            Arrays.sort(expected_data);

            long t0 = System.nanoTime();
            HeapSort.sort(data);
            long dt = System.nanoTime() - t0;

            assertArrayEquals(data, expected_data);

            System.out.printf("%-11d %.3f%n", n, dt / 1000000.0);
        }

        System.out.printf("time(10*n) ~ 13*time(n) за счет асимптотики nlogn%n");
    }

    static int[] randomArray(int n) {
        Random r = new Random(100);
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = r.nextInt();
        }

        return a;
    }
}