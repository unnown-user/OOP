package ru.nsu.asemenychev.task112;

public class Card {
    public enum Suit {
        SPADES("Пики", "Пиковая", "Пиковый"),
        HEARTS("Червы", "Червонная", "Червонный"),
        CLUBS("Трефы", "Трефовая", "Трефовый"),
        DIAMONDS("Бубны", "Бубновая", "Бубновый");

        private final String plural;
        private final String feminine;
        private final String masculine;

        Suit(String plural, String feminine, String masculine) {
            this.plural = plural;
            this.feminine = feminine;
            this.masculine = masculine;
        }

        public String getPlural() {
            return plural;
        }

        public String getFeminine() {
            return feminine;
        }

        public String getMasculine() {
            return masculine;
        }
    }

    public enum Rank {
        TWO("Двойка", 2),
        THREE("Тройка", 3),
        FOUR("Четверка", 4),
        FIVE("Пятерка", 5),
        SIX("Шестерка", 6),
        SEVEN("Семерка", 7),
        EIGHT("Восьмерка", 8),
        NINE("Девятка", 9),
        TEN("Десятка", 10),
        JACK("Валет", 10),
        QUEEN("Дама", 10),
        KING("Король", 10),
        ACE("Туз", 11);

        private final String displayName;
        private final int value;

        Rank(String displayName, int value) {
            this.displayName = displayName;
            this.value = value;
        }

        public String getDisplayName() {
            return displayName;
        }

        public int getValue() {
            return value;
        }
    }

    private final Rank rank;
    private final Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public int baseValue() {
        return rank.getValue();
    }

    public String display(int value) {
        String name;

        if (rank == Rank.QUEEN) {
            name = suit.getFeminine() + " " + rank.getDisplayName().toLowerCase();
        } else if (rank == Rank.KING) {
            name = suit.getMasculine() + " " + rank.getDisplayName().toLowerCase();
        } else {
            name = rank.getDisplayName() + " " + suit.getPlural();
        }

        return name + " (" + value + ")";
    }

    @Override
    public String toString() {
        return display(baseValue());
    }
}