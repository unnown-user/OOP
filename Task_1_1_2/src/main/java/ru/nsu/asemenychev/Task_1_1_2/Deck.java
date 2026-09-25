package ru.nsu.asemenychev.Task_1_1_2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final int deckCount;
    private final List<Card> cards = new ArrayList<>();

    public Deck(int deckCount) {
        if (deckCount < 1) {
            throw new IllegalArgumentException("Количество колод должно быть >= 1");
        }
        this.deckCount = deckCount;
        refill();
        shuffle();
    }

    private void refill() {
        cards.clear();
        for (int d = 0; d < deckCount; d++) {
            for (Card.Suit suit : Card.Suit.values()) {
                for (Card.Rank rank : Card.Rank.values()) {
                    cards.add(new Card(rank, suit));
                }
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            refill();
            shuffle();
        }
        return cards.remove(cards.size() - 1);
    }

    public int size() {
        return cards.size();
    }
}