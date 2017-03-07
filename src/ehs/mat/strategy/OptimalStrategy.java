package ehs.mat.strategy;

import ehs.mat.game.BlackjackDeck;
import ehs.mat.game.BlackjackGame;

/**
 * The implementation of the optimal basic strategy for blackjack
 * @author Joseph Azevedo
 */
public class OptimalStrategy extends Strategy {
    private static final int SPLIT = 48;
    private static final int HIT = 49;
    private static final int STAND = 50;
    private static final int DOUBLE = 51;
    private static final int[][] PAIR_ACTIONS = {
            { SPLIT, SPLIT, SPLIT, SPLIT, SPLIT, SPLIT, SPLIT, SPLIT, SPLIT, SPLIT },
            { HIT, SPLIT, SPLIT, SPLIT, SPLIT, SPLIT, SPLIT, HIT, HIT, HIT },
            { HIT, SPLIT, SPLIT, SPLIT, SPLIT, SPLIT, SPLIT, HIT, HIT, HIT },
            { HIT, HIT, HIT, HIT, SPLIT, SPLIT, HIT, HIT, HIT, HIT },
            { HIT, DOUBLE, DOUBLE, DOUBLE, DOUBLE, DOUBLE, DOUBLE, DOUBLE, DOUBLE, HIT },
            { HIT, SPLIT, SPLIT, SPLIT, SPLIT, SPLIT, HIT, HIT, HIT, HIT },
            { HIT, SPLIT, SPLIT, SPLIT, SPLIT, SPLIT, SPLIT, HIT, HIT, HIT },
            { SPLIT, SPLIT, SPLIT, SPLIT, SPLIT, SPLIT, SPLIT, SPLIT, SPLIT, SPLIT},
            { STAND, SPLIT, SPLIT, SPLIT, SPLIT, SPLIT, STAND, SPLIT, SPLIT, STAND},
            { STAND, STAND, STAND, STAND, STAND, STAND, STAND, STAND, STAND, STAND}};
    private static final int[] ALL_HIT = { HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT };
    private static final int[] HIT_ON_ACE_5_STAND_4_HIT = { HIT, STAND, STAND, STAND, STAND, STAND, HIT, HIT, HIT, HIT };
    private static final int[] ALL_STAND = { STAND, STAND, STAND, STAND, STAND, STAND, STAND, STAND, STAND, STAND };
    private static final int[][] HARD_ACTIONS = {
            ALL_HIT,
            ALL_HIT,
            ALL_HIT,
            ALL_HIT,
            ALL_HIT,
            ALL_HIT,
            ALL_HIT,
            ALL_HIT,
            { HIT, HIT, DOUBLE, DOUBLE, DOUBLE, DOUBLE, HIT, HIT, HIT, HIT },
            { HIT, DOUBLE, DOUBLE, DOUBLE, DOUBLE, DOUBLE, DOUBLE, DOUBLE, DOUBLE, HIT },
            { HIT, DOUBLE, DOUBLE, DOUBLE, DOUBLE, DOUBLE, DOUBLE, DOUBLE, DOUBLE, DOUBLE },
            { HIT, HIT, HIT, STAND, STAND, STAND, HIT, HIT, HIT, HIT },
            HIT_ON_ACE_5_STAND_4_HIT,
            HIT_ON_ACE_5_STAND_4_HIT,
            HIT_ON_ACE_5_STAND_4_HIT,
            HIT_ON_ACE_5_STAND_4_HIT,
            ALL_STAND,
            ALL_STAND,
            ALL_STAND,
            ALL_STAND,
            ALL_STAND
    };
    private static final int[][] SOFT_ACTIONS = {
            ALL_STAND,
            ALL_STAND,
            { HIT, HIT, HIT, HIT, DOUBLE, DOUBLE, HIT, HIT, HIT, HIT },
            { HIT, HIT, HIT, HIT, DOUBLE, DOUBLE, HIT, HIT, HIT, HIT },
            { HIT, HIT, HIT, DOUBLE, DOUBLE, DOUBLE, HIT, HIT, HIT, HIT },
            { HIT, HIT, HIT, DOUBLE, DOUBLE, DOUBLE, HIT, HIT, HIT, HIT },
            { HIT, HIT, DOUBLE, DOUBLE, DOUBLE, DOUBLE, HIT, HIT, HIT, HIT },
            { HIT, STAND, DOUBLE, DOUBLE, DOUBLE, DOUBLE, STAND, STAND, HIT, HIT },
            ALL_STAND,
            ALL_STAND,
            ALL_STAND,
            ALL_STAND,
            ALL_STAND,
            ALL_STAND,
            ALL_STAND,
            ALL_STAND,
            ALL_STAND,
            ALL_STAND,
            ALL_STAND,
            ALL_STAND,
            ALL_STAND
    };

    @Override
    protected boolean performSoftDeckAction(BlackjackGame g, BlackjackDeck deck) {
        if(!deck.isDoubleDown() && !deck.isBust()) {
            int action = SOFT_ACTIONS[deck.getMinTotal() - 1][BlackjackDeck.getCardValue(g.getFaceUpDealerCard()) - 1];
            if(action == STAND) return false;
            if(action == HIT) {
                g.hit(deck);
                return true;
            }
            if(action == DOUBLE) {
                deck.doubleDown();
                g.hit(deck);
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean performHardDeckAction(BlackjackGame g, BlackjackDeck deck) {
        if(!deck.isDoubleDown() && !deck.isBust()) {
            int action = HARD_ACTIONS[deck.getMaxTotal() - 1][BlackjackDeck.getCardValue(g.getFaceUpDealerCard()) - 1];
            if(action == STAND) return false;
            if(action == HIT) {
                g.hit(deck);
                return true;
            }
            if(action == DOUBLE) {
                deck.doubleDown();
                g.hit(deck);
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean shouldSplit(BlackjackGame g, BlackjackDeck deck) {
        if(PAIR_ACTIONS[BlackjackDeck.getCardValue(deck.get(0)) - 1][BlackjackDeck.getCardValue(g.getFaceUpDealerCard()) - 1] == SPLIT) return true;
        return false;
    }
}
