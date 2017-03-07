package ehs.mat.strategy;

import ehs.mat.game.BlackjackDeck;
import ehs.mat.game.BlackjackGame;

/**
 * A basic implementation of strategy that never splits and always hits if the total is below a threshold
 * @author Joseph Azevedo
 */
public class ThresholdStrategy extends Strategy {
    private int threshold;
    public ThresholdStrategy(int threshold) {
        super();
        this.threshold = threshold;
    }

    @Override
    protected boolean shouldSplit(BlackjackGame g, BlackjackDeck deck) {
        return false;
    }

    @Override
    protected boolean performSoftDeckAction(BlackjackGame g, BlackjackDeck deck) {
        if(deck.getMaxTotal() < threshold) {
            g.hit(deck);
            return true;
        } else return false;
    }

    @Override
    protected boolean performHardDeckAction(BlackjackGame g, BlackjackDeck deck) {
        if(deck.getMaxTotal() < threshold) {
            g.hit(deck);
            return true;
        } else return false;
    }
}
