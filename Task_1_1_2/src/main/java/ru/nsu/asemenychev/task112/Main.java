package ru.nsu.asemenychev.task112;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        new BlackjackGame(scanner).play();
    }
}
