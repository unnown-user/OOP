package ru.nsu.asemenychev.task112;

import java.util.Scanner;

public class BlackjackGame {
    private final Scanner scanner;
    private final Deck deck;
    private final Player player;
    private final Dealer dealer;

    private int playerScore = 0;
    private int dealerScore = 0;
    private int roundNumber = 1;

    public BlackjackGame(Scanner scanner) {
        this(scanner, 1);
    }

    public BlackjackGame(Scanner scanner, int deckCount) {
        this.scanner = scanner;
        this.deck = new Deck(deckCount);
        this.player = new Player("Игрок");
        this.dealer = new Dealer();
    }

    public void play() {
        System.out.println("Добро пожаловать в Блэкджек!");

        while (true) {
            playRound();
            roundNumber++;
        }
    }

    private void playRound() {
        System.out.println("Раунд " + roundNumber);

        player.clearHand();
        dealer.clearHand();

        System.out.println("Дилер раздал карты");
        dealInitialCards();

        printPlayerHand();
        printDealerHand(true);

        if (player.getHand().isBlackjack() || dealer.getHand().isBlackjack()) {
            resolveBlackjack();
            return;
        }

        if (!playerTurn()) {
            return;
        }

        dealerTurn();
        resolveWinner();
    }

    private void dealInitialCards() {
        player.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());
        player.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());
    }

    private boolean playerTurn() {
        System.out.println("Ваш ход");
        System.out.println("-------");

        while (true) {
            int choice = readChoice();

            if (choice == 0) {
                return true;
            }

            Card card = deck.drawCard();
            player.addCard(card);

            System.out.println("Вы открыли карту " + card);
            printPlayerHand();
            printDealerHand(true);

            if (player.getHand().isBust()) {
                dealerScore++;
                System.out.println("Перебор! Вы проиграли раунд. "
                        + scoreString("в пользу дилера"));
                return false;
            }
        }
    }

    private void dealerTurn() {
        System.out.println("Ход дилера");
        System.out.println("-------");

        Card hole = dealer.getHand().getCards().get(1);
        System.out.println("Дилер открывает закрытую карту " + hole);

        printPlayerHand();
        printDealerHand(false);

        while (dealer.shouldHit()) {
            Card card = deck.drawCard();
            dealer.addCard(card);

            System.out.println("Дилер открывает карту " + card);

            printPlayerHand();
            printDealerHand(false);
        }
    }

    private void resolveWinner() {
        int playerTotal = player.getHand().total();
        int dealerTotal = dealer.getHand().total();

        if (dealer.getHand().isBust()) {
            playerScore++;
            System.out.println("У дилера перебор! Вы выиграли раунд! "
                    + scoreString("в вашу пользу"));
        } else if (playerTotal > dealerTotal) {
            playerScore++;
            System.out.println("Вы выиграли раунд! "
                    + scoreString("в вашу пользу"));
        } else if (playerTotal < dealerTotal) {
            dealerScore++;
            System.out.println("Вы проиграли раунд! "
                    + scoreString("в пользу дилера"));
        } else {
            System.out.println("Ничья! " + scoreString(""));
        }
    }

    private void resolveBlackjack() {
        boolean playerBlackjack = player.getHand().isBlackjack();
        boolean dealerBlackjack = dealer.getHand().isBlackjack();

        if (dealerBlackjack) {
            printDealerHand(false);
        }

        if (playerBlackjack && dealerBlackjack) {
            System.out.println("У обоих блэкджек! Ничья. " + scoreString(""));
        } else if (playerBlackjack) {
            playerScore++;
            System.out.println("У вас блэкджек! Вы выиграли раунд! "
                    + scoreString("в вашу пользу"));
        } else {
            dealerScore++;
            System.out.println("У дилера блэкджек! Вы проиграли раунд. "
                    + scoreString("в пользу дилера"));
        }
    }

    private int readChoice() {
        while (true) {
            System.out.println("Введите “1”, чтобы взять карту, и “0”, чтобы остановиться .");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                if (choice == 0 || choice == 1) {
                    return choice;
                }
            } else {
                scanner.next();
            }

            System.out.println("Некорректный ввод. Введите 1 или 0.");
        }
    }

    private void printPlayerHand() {
        System.out.println("Ваши карты: " + player.getHand());
    }

    private void printDealerHand(boolean hideHole) {
        if (hideHole) {
            Card first = dealer.getHand().getCards().get(0);
            System.out.println("Карты дилера: [" + first + ", <закрытая карта>]");
        } else {
            System.out.println("Карты дилера: " + dealer.getHand());
        }
    }

    private String scoreString(String suffix) {
        String result = "Счет " + playerScore + ":" + dealerScore;

        if (suffix != null && !suffix.isEmpty()) {
            result += " " + suffix;
        }

        return result + ".";
    }
}