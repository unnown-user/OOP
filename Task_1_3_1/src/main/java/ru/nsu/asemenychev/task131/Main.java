package ru.nsu.asemenychev.task131;

import java.io.IOException;
import java.util.List;

/**
 * Точка входа в программу поиска подстроки в файле.
 */
public class Main {

    /**
     * Запускает поиск подстроки.
     * Ожидает два аргумента командной строки: путь к файлу и искомую подстроку.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Ожидаются аргументы: <файл> <подстрока>");
            return;
        }
        String filePath = args[0];
        String substring = args[1];

        FileSubstringSearcher searcher = new FileSubstringSearcher();
        try {
            List<Long> indices = searcher.find(filePath, substring);
            System.out.println(indices);
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}