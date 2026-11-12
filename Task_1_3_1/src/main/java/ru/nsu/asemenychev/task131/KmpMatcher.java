package ru.nsu.asemenychev.task131;

/**
 * Алгоритм Кнута-Морриса-Пратта (KMP) для поиска подстроки в потоке символов.
 * Позволяет находить вхождения за O(N) времени и O(M) дополнительной памяти
 * (N - длина текста; M - длина искомой подстроки).
 */
public class KmpMatcher {

    // Искомая подстрока, разбитая на символы
    private char[] pattern;
    // Массив префикс-функции
    private int[] prefix;
    // Сколько символов подстроки уже совпали к текущему моменту
    private int matchedLength;

    /**
     * Создает сопоставитель для заданной подстроки.
     *
     * @param patternStr искомая подстрока
     * @throws IllegalArgumentException если подстрока пуста или равна null
     */
    public KmpMatcher(String patternStr) {
        if (patternStr == null || patternStr.isEmpty()) {
            throw new IllegalArgumentException("Подстрока не может быть пустой или null");
        }

        this.pattern = patternStr.toCharArray();
        this.prefix = computePrefixFunction(this.pattern);
        this.matchedLength = 0;
    }

    /**
     * Вычисляет префикс-функцию для алгоритма KMP.
     *
     * @param pattern массив символов подстроки
     * @return массив значений префикс-функции
     */
    private static int[] computePrefixFunction(char[] pattern) {
        int[] pi = new int[pattern.length];
        for (int i = 1, j = 0; i < pattern.length; i++) {
            while (j > 0 && pattern[i] != pattern[j]) {
                j = pi[j - 1];
            }
            if (pattern[i] == pattern[j]) {
                j++;
            }
            pi[i] = j;
        }
        return pi;
    }

    /**
     * Обрабатывает следующий символ из потока.
     *
     * @param c текущий символ
     * @param currentIndex индекс текущего символа в исходном тексте (0-based)
     * @return индекс начала найденного вхождения, либо -1, если вхождение не завершено
     */
    public long feed(char c, long currentIndex) {
        while (matchedLength > 0 && c != pattern[matchedLength]) {
            matchedLength = prefix[matchedLength - 1];
        }
        if (c == pattern[matchedLength]) {
            matchedLength++;
        }
        if (matchedLength == pattern.length) {
            long matchStart = currentIndex - pattern.length + 1;
            matchedLength = prefix[matchedLength - 1];
            return matchStart;
        }

        return -1L;
    }
}
