package ru.nsu.asemenychev.task112;

/**
 * Участник игры (игрок или дилер). Имеет имя и руку с картами.
 * Служит родительским классом для класса Dealer.
 */
public class Player {
    private final Hand hand = new Hand();

    /**
     * @return рука участника
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * Добавляет карту в руку участника.
     *
     * @param card добавляемая карта
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * Очищает руку перед новым раундом.
     */
    public void clearHand() {
        hand.clear();
    }
}