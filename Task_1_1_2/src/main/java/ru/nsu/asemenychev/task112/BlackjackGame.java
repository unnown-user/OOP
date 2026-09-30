package ru.nsu.asemenychev.task112;

import java.util.Scanner;

/**
 * Основной класс игры. Управляет раундами, раздачей
 * карт, ходами игрока и дилера, подсчётом очков.
 * Взаимодействует с пользователем через консоль.
 */
public class BlackjackGame {
    private final Scanner scanner;
    private final Deck deck;
    private final Player player;
    private final Dealer dealer;

    private int playerScore = 0;
    private int dealerScore = 0;
    private int roundNumber = 1;

    /**
     * Создаёт игру с одной стандартной колодой (52 карты).
     *
     * @param scanner источник ввода.
     */
    public BlackjackGame(Scanner scanner) {
        this.scanner = scanner;
        this.deck = new Deck();
        this.player = new Player();
        this.dealer = new Dealer();
    }

    /**
     * Запускает бесконечный игровой цикл.
     * Каждая итерация — новый раунд.
     */
    public void play() {
        System.out.println("Добро пожаловать в Блэкджек!");

        while (true) {
            playRound();
            roundNumber++;
        }
    }

    /**
     * Проводит один раунд: раздача, ходы,
     * определение победителя.
     */
    void playRound() {
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

    /**
     * Раздаёт по две карты игроку и дилеру.
     */
    private void dealInitialCards() {
        player.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());
        player.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());
    }

    /**
     * Ход игрока: цикл, пока игрок не остановится или не переберёт.
     *
     * @return {@code true}  если ход завершён корректно (игрок остановился),
     *         {@code false} если игрок перебрал (раунд проигран)
     */
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

    /**
     * Ход дилера: раскрывает закрытую карту и добирает до 17.
     */
    private void dealerTurn() {
        System.out.println("Ход дилера");
        System.out.println("-------");

        Card hole = dealer.getHand().getCard(1);
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

    /**
     * Определяет победителя по очкам и обновляет счёт.
     */
    private void resolveWinner() {
        int playerTotal = player.getHand().total();
        int dealerTotal = dealer.getHand().total();
        boolean dealerBust = dealer.getHand().isBust();

        String result = determineWinner(playerTotal, dealerTotal, dealerBust);

        if ("PLAYER".equals(result)) {
            playerScore++;
            System.out.println("Вы выиграли раунд! " + scoreString("в вашу пользу"));
        } else if ("DEALER".equals(result)) {
            dealerScore++;
            System.out.println("Вы проиграли раунд! " + scoreString("в пользу дилера"));
        } else {
            System.out.println("Ничья! " + scoreString(""));
        }
    }

    /**
     * Разбирает ситуацию, когда у кого-то блэкджек.
     */
    private void resolveBlackjack() {
        boolean playerBlackjack = player.getHand().isBlackjack();
        boolean dealerBlackjack = dealer.getHand().isBlackjack();

        if (dealerBlackjack) {
            printDealerHand(false);
        }

        String outcome = determineBlackjackOutcome(playerBlackjack, dealerBlackjack);

        if ("PLAYER".equals(outcome)) {
            playerScore++;
            System.out.println("У вас блэкджек! Вы выиграли раунд! "
                    + scoreString("в вашу пользу"));
        } else if ("DEALER".equals(outcome)) {
            dealerScore++;
            System.out.println("У дилера блэкджек! Вы проиграли раунд. "
                    + scoreString("в пользу дилера"));
        } else {
            System.out.println("У обоих блэкджек! Ничья. " + scoreString(""));
        }
    }

    /**
     * Считывает с консоли выбор игрока: 1 — взять карту, 0 — остановиться.
     * Повторяет запрос при некорректном вводе.
     *
     * @return 0 или 1.
     */
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

    /**
     * Печатает карты игрока.
     */
    private void printPlayerHand() {
        System.out.println("Ваши карты: " + player.getHand());
    }

    /**
     * Печатает карты дилера.
     *
     * @param hideHole: {@code true} — вторая карта скрыта,
     *                  {@code false} — открыта.
     */
    private void printDealerHand(boolean hideHole) {
        if (hideHole) {
            Card first = dealer.getHand().getCard(0);
            System.out.println("Карты дилера: [" + first + ", <закрытая карта>]");
        } else {
            System.out.println("Карты дилера: " + dealer.getHand());
        }
    }

    /**
     * Формирует строку со счётом игры.
     *
     * @param suffix добавляемый текст (может быть пустым).
     * @return строка вида "Счет A:B в вашу пользу.".
     */
    String scoreString(String suffix) {
        String result = "Счет " + playerScore + ":" + dealerScore;

        if (suffix != null && !suffix.isEmpty()) {
            result += " " + suffix;
        }

        return result + ".";
    }

    /**
     * Определяет победителя по очкам игрока и дилера.
     *
     * @param playerTotal очки игрока
     * @param dealerTotal очки дилера
     * @param dealerBust  перебрал ли дилер
     * @return "PLAYER" / "DEALER" / "TIE"
     */
    String determineWinner(int playerTotal, int dealerTotal, boolean dealerBust) {
        if (dealerBust) {
            return "PLAYER";
        }
        if (playerTotal > dealerTotal) {
            return "PLAYER";
        }
        if (playerTotal < dealerTotal) {
            return "DEALER";
        }
        return "TIE";
    }

    /**
     * Определяет исход при блэкджеках.
     *
     * @param playerBlackjack есть ли блэкджек у игрока
     * @param dealerBlackjack есть ли блэкджек у дилера
     * @return "PLAYER" / "DEALER" / "TIE"
     */
    String determineBlackjackOutcome(boolean playerBlackjack, boolean dealerBlackjack) {
        if (playerBlackjack && dealerBlackjack) {
            return "TIE";
        }
        if (playerBlackjack) {
            return "PLAYER";
        }
        return "DEALER";
    }
}