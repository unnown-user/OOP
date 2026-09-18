package ru.nsu.asemenychev.task_1_1_1;

import java.util.Random;

import org.junit.jupiter.api.Test;

class HeapSortComplexityTest {
    @Test
    void time() {
        int[] sizes = {10000, 100000, 1000000};

        System.out.println();
        System.out.println("%-14d time (ms)");
        for (int n : sizes) {
            long dt = 0;
            for (int i = 0; i < 10; i++) {
                int[] data = randomArray(n);

                long t0 = System.nanoTime();
                HeapSort.sort(data);
                long dti = System.nanoTime() - t0;
                dt += dti;
            }

            System.out.printf("%-14d %.3f%n", n, dt / 1000000.0);
        }

        System.out.printf("time(10*n) ~ 12*time(n) за счет асимптотики nlogn%n");
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