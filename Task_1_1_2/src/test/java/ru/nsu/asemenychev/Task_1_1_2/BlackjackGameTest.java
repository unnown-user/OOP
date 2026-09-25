package ru.nsu.asemenychev.Task_1_1_2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

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
    void deckHas52CardsPerDeck() {
        Deck deck = new Deck(1);
        assertEquals(52, deck.size());

        Deck twoDecks = new Deck(2);
        assertEquals(104, twoDecks.size());
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
    void handToStringShowsAceAsOneWhenNeeded() {
        Hand hand = new Hand();
        hand.add(new Card(Card.Rank.ACE, Card.Suit.CLUBS));
        hand.add(new Card(Card.Rank.THREE, Card.Suit.CLUBS));
        hand.add(new Card(Card.Rank.TEN, Card.Suit.SPADES));

        assertEquals(
                "[Туз Трефы (1), Тройка Трефы (3), Десятка Пики (10)] > 14",
                hand.toString()
        );
    }
}