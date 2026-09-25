package ru.nsu.asemenychev.task112;

import java.util.Scanner;

/**
 * Точка входа в игру «Блэкджек».
 * Создаёт игру и запускает бесконечный игровой цикл.
 */
public class Main {

    /**
     * Запускает консольную игру.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        new BlackjackGame(scanner).play();
    }
}
