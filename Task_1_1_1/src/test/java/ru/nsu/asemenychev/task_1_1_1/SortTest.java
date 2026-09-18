package ru.nsu.asemenychev.task_1_1_1;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class SortTest {

    @Test
    void sortEmpty() {
        int[] array = {};
        HeapSort.sort(array);
        assertArrayEquals(new int[]{}, array);
    }

    @Test
    void withOnlyOneElement() {
        int[] array = {1};
        HeapSort.sort(array);
        assertArrayEquals(new int[]{1}, array);
    }

    @Test
    void sortWithoutChangesWithoutDuplicates() {
        int[] array = {1, 2, 3, 4, 5, 6};
        HeapSort.sort(array);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6}, array);
    }

    @Test
    void sortWithoutChangesWithDuplicates() {
        int[] array = {1, 2, 2, 3, 5, 7};
        HeapSort.sort(array);
        assertArrayEquals(new int[]{1, 2, 2, 3, 5, 7}, array);
    }

    @Test
    void sortRevert() {
        int[] array = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        HeapSort.sort(array);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, array);
    }

    @Test
    void sort() {
        int[] array = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        HeapSort.sort(array);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, array);
    }
}