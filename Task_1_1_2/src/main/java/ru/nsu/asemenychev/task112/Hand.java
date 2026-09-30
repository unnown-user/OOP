package ru.nsu.asemenychev.task112;

import java.util.ArrayList;
import java.util.List;

/**
 * Рука игрока — набор карт с подсчётом очков по правилам блэкджека.
 * Тузы считаются как 11, но если итоговая сумма превышает 21, их
 * значение понижается до 1 (по одному на каждый туз, пока сумма
 * не опустится до 21 или ниже).
 * Сумма очков и количество "старших" тузов хранятся в состоянии
 * объекта и пересчитываются только при изменении руки.
 */
public class Hand {
    private static final int BLACKJACK = 21;
    private static final int ACE_HIGH = 11;
    private static final int ACE_LOW_DIFF = 10;

    private final List<Card> cards = new ArrayList<>();
    private int total;
    private int acesAsEleven;

    /**
     * Добавляет карту в руку.
     *
     * @param card добавляемая карта.
     */
    public void add(Card card) {
        cards.add(card);
        total += card.baseValue();

        if (card.getRank() == Card.Rank.ACE) {
            acesAsEleven++;
        }

        while (total > BLACKJACK && acesAsEleven > 0) {
            total -= ACE_LOW_DIFF;
            acesAsEleven--;
        }
    }

    /**
     * Удаляет все карты из руки и сбрасывает счётчики.
     */
    public void clear() {
        cards.clear();
        total = 0;
        acesAsEleven = 0;
    }

    /**
     * Возвращает карту по указанной позиции.
     *
     * @param index позиция карты в руке (0 — первая).
     * @return карта по указанной позиции.
     */
    public Card getCard(int index) {
        return cards.get(index);
    }

    /**
     * Проверяет, пуста ли рука.
     *
     * @return {@code true}, если в руке нет карт.
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * Возвращает текущую сумму очков руки.
     *
     * @return текущая сумма очков руки.
     */
    public int total() {
        return total;
    }

    /**
     * Проверяет, является ли рука блэкджеком.
     *
     * @return {@code true}, если в руке ровно 2 карты и сумма равна 21.
     */
    public boolean isBlackjack() {
        return cards.size() == 2 && total == BLACKJACK;
    }

    /**
     * Проверяет, перебрала ли рука.
     *
     * @return {@code true}, если сумма очков превышает 21.
     */
    public boolean isBust() {
        return total > BLACKJACK;
    }

    /**
     * Возвращает строковое представление руки с учётом понижения тузов.
     *
     * @return строка вида "[карта1 (значение1), карта2 (значение2), ...] => сумма"
     */
    @Override
    public String toString() {
        int acesAsElevenLeft = acesAsEleven;
        StringBuilder sb = new StringBuilder("[");

        for (int i = 0; i < cards.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            Card card = cards.get(i);
            int value = card.baseValue();

            if (card.getRank() == Card.Rank.ACE) {
                if (acesAsElevenLeft > 0) {
                    value = ACE_HIGH;
                    acesAsElevenLeft--;
                } else {
                    value = 1;
                }
            }

            sb.append(card.display(value));
        }

        sb.append("] => ").append(total);
        return sb.toString();
    }
}
