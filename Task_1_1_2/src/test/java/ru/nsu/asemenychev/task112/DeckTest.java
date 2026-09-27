package ru.nsu.asemenychev.task112;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * Тесты для класса {@link Deck}.
 */
class DeckTest {

    @Test
    void newDeckHas52Cards() {
        assertEquals(52, new Deck().size());
    }

    @Test
    void drawCardReducesSize() {
        Deck deck = new Deck();
        Card card = deck.drawCard();
        assertNotNull(card);
        assertEquals(51, deck.size());
    }

    @Test
    void deckRefillsAutomaticallyWhenEmpty() {
        Deck deck = new Deck();
        for (int i = 0; i < 52; i++) {
            deck.drawCard();
        }
        assertEquals(0, deck.size());

        Card extra = deck.drawCard();
        assertNotNull(extra);
        assertEquals(51, deck.size());
    }

    @Test
    void shuffleKeepsAllCards() {
        Deck deck = new Deck();
        deck.shuffle();
        assertEquals(52, deck.size());
    }

    @Test
    void drawManyCardsDoesNotFail() {
        Deck deck = new Deck();
        for (int i = 0; i < 100; i++) {
            assertNotNull(deck.drawCard());
        }
    }
}