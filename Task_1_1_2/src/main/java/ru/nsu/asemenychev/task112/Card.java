package ru.nsu.asemenychev.task112;

/**
 * Класс создающий карту.
 */
public class Card {
    /**
     * Масть карты. Хранит три формы названия
     * для корректного склонения в русском языке:
     * множественное число / женский род / мужской род.
     */
    public enum Suit {
        SPADES("Пики", "Пиковая", "Пиковый"),
        HEARTS("Червы", "Червонная", "Червонный"),
        CLUBS("Трефы", "Трефовая", "Трефовый"),
        DIAMONDS("Бубны", "Бубновая", "Бубновый");

        private final String plural;
        private final String feminine;
        private final String masculine;

        /**
         * @param plural    название масти во множественном числе
         * @param feminine  прилагательное в женском роде
         * @param masculine прилагательное в мужском роде
         */
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

    /**
     * Номинал карты. Хранит русское название и базовое число очков.
     * Валет, дама, король дают 10 очков, туз — 11.
     */
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

        /**
         * @param displayName название на русском
         * @param value       базовое количество очков
         */
        Rank(String displayName, int value) {
            this.displayName = displayName;
            this.value = value;
        }

        /**
         * @return русское название номинала
         */
        public String getDisplayName() {
            return displayName;
        }

        /**
         * @return базовое количество очков
         */
        public int getValue() {
            return value;
        }
    }

    private final Rank rank;
    private final Suit suit;

    /**
     * Создаёт карту с заданными мастью и номиналом.
     *
     * @param rank номинал карты
     * @param suit масть карты
     */
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * @return номинал карты
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * @return масть карты
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Базовое количество очков карты (без учёта пересчёта тузов).
     *
     * @return 2–10 для числовых карт, 10 для картинок, 11 для туза
     */
    public int baseValue() {
        return rank.getValue();
    }

    /**
     * Формирует строковое представление карты с указанным значением очков.
     * Род прилагательного подбирается по номиналу: женский для дамы,
     * мужской для короля, множественное число — для остальных.
     *
     * @param value значение очков, которое нужно показать в скобках
     * @return строка вида "Масть Номинал (значение)"
     */
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

    /**
     * @return отображение карты с базовым значением очков
     */
    @Override
    public String toString() {
        return display(baseValue());
    }
}