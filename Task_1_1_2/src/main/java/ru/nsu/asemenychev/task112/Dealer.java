package ru.nsu.asemenychev.task112;

public class Dealer extends Player {
    public Dealer() {
        super("Дилер");
    }

    public boolean shouldHit() {
        return getHand().total() < 17;
    }
}