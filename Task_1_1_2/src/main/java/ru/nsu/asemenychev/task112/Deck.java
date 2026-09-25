package ru.nsu.asemenychev.task112;

import java.util.ArrayList;

import java.util.Collections;

import java.util.List;

/**
 * Игровая колода из 52 карт. Поддерживает перемешивание и выдачу карт.
 * Когда карты заканчиваются, колода автоматически пересоздаётся и
 * перемешивается, что позволяет играть неограниченно долго.
 */
public class Deck {
    private final List<Card> cards = new ArrayList<>();

    /**
     * Создаёт колоду из 52 карт.
     */
    public Deck() {
        refill();
        shuffle();
    }

    /**
     * Заполняет колоду полным набором карт.
     */
    private void refill() {
        cards.clear();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    /**
     * Перемешивает карты в колоде случайным образом.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Выдаёт одну карту из колоды (с верхушки).
     * Если колода пуста — пересоздаёт и перемешивает её.
     *
     * @return выданная карта
     */
    public Card drawCard() {
        if (cards.isEmpty()) {
            refill();
            shuffle();
        }
        return cards.remove(cards.size() - 1);
    }

    /**
     * @return текущее количество карт в колоде
     */
    public int size() {
        return cards.size();
    }
}