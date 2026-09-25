package ru.nsu.asemenychev.task112;

import java.util.ArrayList;
import java.util.List;

/**
 * Рука игрока — набор карт с подсчётом очков по правилам блэкджека.
 * Тузы считаются как 11, но если итоговая сумма превышает 21, их
 * значение понижается до 1 (по одному на каждый туз, пока сумма
 * не опустится до 21 или ниже).
 */
public class Hand {
    private final List<Card> cards = new ArrayList<>();

    /**
     * Добавляет карту в руку.
     *
     * @param card добавляемая карта.
     */
    public void add(Card card) {
        cards.add(card);
    }

    /**
     * Удаляет все карты из руки.
     */
    public void clear() {
        cards.clear();
    }

    /**
     * @return выдает список карт в руке.
     */
    public List<Card> getCards() {
        return cards;
    }

    /**
     * Считает сумму очков с учётом тузов, заниженных в стоимости до 1.
     * Сначала все тузы считаются как 11. Затем, пока сумма > 21 и есть
     * тузы, каждый туз понижается на 10.
     *
     * @return сумма очков руки.
     */
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

    /**
     * @return true если в руке ровно 2 карты и сумма очков равна 21.
     */
    public boolean isBlackjack() {
        return cards.size() == 2 && total() == 21;
    }

    /**
     * @return true если сумма очков превышает 21.
     */
    public boolean isBust() {
        return total() > 21;
    }

    /**
     * Возвращает строковое представление руки с учётом понижения тузов.
     *
     * @return строка вида "[карта1 (номинал1), карта2 (номинал2), ...] => сумма"
     */
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