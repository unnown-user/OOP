package ru.nsu.asemenychev.Task_1_1_2;

public class Dealer extends Player {
    public Dealer() {
        super("Дилер");
    }

    public boolean shouldHit() {
        return getHand().total() < 17;
    }
}