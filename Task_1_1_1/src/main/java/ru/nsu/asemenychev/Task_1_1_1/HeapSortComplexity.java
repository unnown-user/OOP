package ru.nsu.asemenychev.Task_1_1_1;

import java.util.Random;


public class HeapSortComplexity {
    void time() {
        int[] sizes = {10000, 100000, 1000000, 10000000};

        System.out.println();
        System.out.println("%-14d time (ms)");
        for (int n : sizes) {
            int[] data = randomArray(n);
            long t0 = System.nanoTime();
            HeapSort.sort(data);
            long dt = System.nanoTime() - t0;
            System.out.printf("%-14d %.3f%n", n, dt / 1000000.0);
        }
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
