package ehs.mat.strategy;

import ehs.mat.game.BlackjackDeck;
import ehs.mat.game.BlackjackGame;

/**
 * A base object to represent implementable blackjack strategies
 * @author Joseph Azevedo
 */
public abstract class Strategy {
    public void playHand(BlackjackGame g) {
        boolean changed = false;
        do {
            for (BlackjackDeck deck : g.getPlayerDecks()) {
                if (deck.canSplit() && shouldSplit(g, deck)) {
                    g.split(deck);
                    changed = true;
                    break;
                } else {
                    if (deck.isSoft()) {
                        changed = performSoftDeckAction(g, deck);
                    } else {
                        changed = performHardDeckAction(g, deck);
                    }
                }
            }
        } while (changed);
    }

    /**
     * @param g A reference to the simulation BlackjackGame object
     * @param deck The deck to consider
     * @return Whether or not the strategy dictates the deck should be split
     */
    protected abstract boolean shouldSplit(BlackjackGame g, BlackjackDeck deck);

    /**
     * Performs an action on a soft deck
     * @param g A reference to the simulation BlackjackGame object
     * @param deck A reference to the deck to perform upon
     * @return Whether or not the deck was changed in the process
     */
    protected abstract boolean performSoftDeckAction(BlackjackGame g, BlackjackDeck deck);

    /**
     * Performs an action on a hard deck
     * @param g A reference to the simulation BlackjackGame object
     * @param deck A reference to the deck to perform upon
     * @return Whether or not the deck was changed in the process
     */
    protected abstract boolean performHardDeckAction(BlackjackGame g, BlackjackDeck deck);
}

