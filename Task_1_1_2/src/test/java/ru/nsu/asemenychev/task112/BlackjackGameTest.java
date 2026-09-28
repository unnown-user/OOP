package ru.nsu.asemenychev.task112;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Модульные тесты для классов блэкджек-игры:
 * {@link Card}, {@link Deck}, {@link Hand}, {@link Dealer},
 * {@link BlackjackGame}.
 */
public class BlackjackGameTest {

    @Test
    void aceCountsAsElevenWhenNotBust() {
        Hand hand = new Hand();
        hand.add(new Card(Card.Rank.ACE, Card.Suit.SPADES));
        hand.add(new Card(Card.Rank.SIX, Card.Suit.HEARTS));
        assertEquals(17, hand.total());
    }

    @Test
    void aceCountsAsOneWhenBust() {
        Hand hand = new Hand();
        hand.add(new Card(Card.Rank.ACE, Card.Suit.SPADES));
        hand.add(new Card(Card.Rank.KING, Card.Suit.HEARTS));
        hand.add(new Card(Card.Rank.QUEEN, Card.Suit.CLUBS));
        hand.add(new Card(Card.Rank.FIVE, Card.Suit.DIAMONDS));
        assertEquals(26, hand.total());
    }

    @Test
    void blackjackIsTwoCardsTwentyOne() {
        Hand hand = new Hand();
        hand.add(new Card(Card.Rank.ACE, Card.Suit.SPADES));
        hand.add(new Card(Card.Rank.KING, Card.Suit.HEARTS));
        assertTrue(hand.isBlackjack());
        assertEquals(21, hand.total());
    }

    @Test
    void bustWhenTotalOver21() {
        Hand hand = new Hand();
        hand.add(new Card(Card.Rank.TEN, Card.Suit.SPADES));
        hand.add(new Card(Card.Rank.KING, Card.Suit.HEARTS));
        hand.add(new Card(Card.Rank.TWO, Card.Suit.CLUBS));
        assertTrue(hand.isBust());
    }

    @Test
    void handToStringShowsAceAsOneWhenNeeded() {
        Hand hand = new Hand();
        hand.add(new Card(Card.Rank.ACE, Card.Suit.CLUBS));
        hand.add(new Card(Card.Rank.THREE, Card.Suit.CLUBS));
        hand.add(new Card(Card.Rank.TEN, Card.Suit.SPADES));
        assertEquals(
                "[Туз Трефы (1), Тройка Трефы (3), Десятка Пики (10)] => 14",
                hand.toString()
        );
    }

    @Test
    void handIsEmptyAfterCreation() {
        assertTrue(new Hand().isEmpty());
    }

    @Test
    void handIsNotEmptyAfterAddingCard() {
        Hand hand = new Hand();
        hand.add(new Card(Card.Rank.TWO, Card.Suit.SPADES));
        assertFalse(hand.isEmpty());
    }

    @Test
    void getCardReturnsCorrectPosition() {
        Hand hand = new Hand();
        Card first = new Card(Card.Rank.TWO, Card.Suit.SPADES);
        Card second = new Card(Card.Rank.THREE, Card.Suit.HEARTS);
        hand.add(first);
        hand.add(second);
        assertEquals(first, hand.getCard(0));
        assertEquals(second, hand.getCard(1));
    }

    @Test
    void clearResetsHand() {
        Hand hand = new Hand();
        hand.add(new Card(Card.Rank.TEN, Card.Suit.SPADES));
        hand.clear();
        assertEquals(0, hand.total());
        assertTrue(hand.isEmpty());
    }

    @Test
    void deckHas52Cards() {
        assertEquals(52, new Deck().size());
    }

    @Test
    void dealerHitsUntilAtLeast17() {
        Dealer dealer = new Dealer();
        dealer.addCard(new Card(Card.Rank.TEN, Card.Suit.SPADES));
        dealer.addCard(new Card(Card.Rank.SIX, Card.Suit.HEARTS));
        assertEquals(16, dealer.getHand().total());
        assertTrue(dealer.shouldHit());

        dealer.addCard(new Card(Card.Rank.TWO, Card.Suit.CLUBS));
        assertEquals(18, dealer.getHand().total());
        assertFalse(dealer.shouldHit());
    }

    @Test
    void dealerStopsAtExactly17() {
        Dealer dealer = new Dealer();
        dealer.addCard(new Card(Card.Rank.TEN, Card.Suit.SPADES));
        dealer.addCard(new Card(Card.Rank.SEVEN, Card.Suit.HEARTS));
        assertFalse(dealer.shouldHit());
    }

    private BlackjackGame newGame() {
        return new BlackjackGame(new Scanner(System.in));
    }

    @Test
    void playerWinsWithHigherScore() {
        assertEquals("PLAYER", newGame().determineWinner(20, 18, false));
    }

    @Test
    void dealerWinsWithHigherScore() {
        assertEquals("DEALER", newGame().determineWinner(17, 20, false));
    }

    @Test
    void tieWhenEqualScores() {
        assertEquals("TIE", newGame().determineWinner(19, 19, false));
    }

    @Test
    void playerWinsWhenDealerBust() {
        assertEquals("PLAYER", newGame().determineWinner(15, 25, true));
    }

    @Test
    void playerWinsWhenDealerBustEvenWithLowScore() {
        assertEquals("PLAYER", newGame().determineWinner(5, 22, true));
    }

    @Test
    void bothBlackjackIsTie() {
        assertEquals("TIE", newGame().determineBlackjackOutcome(true, true));
    }

    @Test
    void onlyPlayerBlackjackWins() {
        assertEquals("PLAYER", newGame().determineBlackjackOutcome(true, false));
    }

    @Test
    void onlyDealerBlackjackWins() {
        assertEquals("DEALER", newGame().determineBlackjackOutcome(false, true));
    }

    @Test
    void scoreStringWithSuffix() {
        BlackjackGame game = newGame();
        assertEquals("Счет 0:0 в вашу пользу.", game.scoreString("в вашу пользу"));
    }

    @Test
    void scoreStringWithEmptySuffix() {
        BlackjackGame game = newGame();
        assertEquals("Счет 0:0.", game.scoreString(""));
    }

    @Test
    void scoreStringWithNullSuffix() {
        BlackjackGame game = newGame();
        assertEquals("Счет 0:0.", game.scoreString(null));
    }

    private PrintStream originalOut;

    @BeforeEach
    void saveSystemOut() {
        originalOut = System.out;
    }

    @AfterEach
    void restoreSystemOut() {
        System.setOut(originalOut);
    }

    @Test
    void playRoundPrintsExpectedPhrasesWhenPlayerStops() {
        String input = "0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        BlackjackGame game = new BlackjackGame(new Scanner(System.in));
        game.playRound();

        String output = out.toString();
        assertTrue(output.contains("Раунд 1"));
        assertTrue(output.contains("Дилер раздал карты"));
        assertTrue(output.contains("Ваши карты:"));
        assertTrue(output.contains("Карты дилера:"));
        assertTrue(output.contains("Ваш ход"));
        assertTrue(output.contains("Ход дилера"));
    }

    @Test
    void playRoundWithPlayerTakingOneCard() {
        String input = "1\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        BlackjackGame game = new BlackjackGame(new Scanner(System.in));
        game.playRound();

        String output = out.toString();
        assertTrue(output.contains("Вы открыли карту"));
    }

    @Test
    void playRoundHandlesInvalidInputThenStops() {
        String input = "5\nfoo\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        BlackjackGame game = new BlackjackGame(new Scanner(System.in));
        game.playRound();

        String output = out.toString();
        assertTrue(output.contains("Некорректный ввод"));
    }

    @Test
    void playerBustsOnManyCards() {
        String input = "1\n".repeat(12);
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        BlackjackGame game = new BlackjackGame(new Scanner(System.in));

        assertThrows(NoSuchElementException.class, game::play);

        String output = out.toString();
        assertTrue(output.contains("Перебор"));
        assertTrue(output.contains("в пользу дилера"));
    }
}