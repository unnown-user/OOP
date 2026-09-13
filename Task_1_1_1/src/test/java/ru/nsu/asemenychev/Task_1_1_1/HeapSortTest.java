package ru.nsu.asemenychev.Task_1_1_1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class HeapSortTest {

    @Test
    void sort0() {
        int[] array = {};
        HeapSort.sort(array);
        assertArrayEquals(new int[]{}, array);
    }

    @Test
    void sort1() {
        int[] array = {1, 2, 2, 3, 5, 7};
        HeapSort.sort(array);
        assertArrayEquals(new int[]{1, 2, 2, 3, 5, 7}, array);
    }

    @Test
    void sort2() {
        int[] array = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        HeapSort.sort(array);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, array);
    }
}