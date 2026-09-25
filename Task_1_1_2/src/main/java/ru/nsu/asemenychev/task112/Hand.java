package ru.nsu.asemenychev.task112;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private final List<Card> cards = new ArrayList<>();

    public void add(Card card) {
        cards.add(card);
    }

    public void clear() {
        cards.clear();
    }

    public List<Card> getCards() {
        return cards;
    }

    public int total() {
        int total = 0;
        int aces = 0;

        for (Card card : cards) {
            total += card.baseValue();
            if (card.getRank() == Card.Rank.ACE) {
                aces++;
            }
        }

        while (total > 21 && aces > 0) {
            total -= 10;
            aces--;
        }

        return total;
    }

    public boolean isBlackjack() {
        return cards.size() == 2 && total() == 21;
    }

    public boolean isBust() {
        return total() > 21;
    }

    @Override
    public String toString() {
        int total = total();

        int aces = 0;
        for (Card card : cards) {
            if (card.getRank() == Card.Rank.ACE) {
                aces++;
            }
        }

        int baseTotal = 0;
        for (Card card : cards) {
            baseTotal += card.baseValue();
        }

        int acesToReduce = 0;
        while (baseTotal > 21 && acesToReduce < aces) {
            baseTotal -= 10;
            acesToReduce++;
        }

        int acesAsOneLeft = acesToReduce;
        StringBuilder sb = new StringBuilder("[");

        for (int i = 0; i < cards.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }

            Card card = cards.get(i);
            int value = card.baseValue();

            if (card.getRank() == Card.Rank.ACE && acesAsOneLeft > 0) {
                value = 1;
                acesAsOneLeft--;
            }

            sb.append(card.display(value));
        }

        sb.append("] => ").append(total);
        return sb.toString();
    }
}