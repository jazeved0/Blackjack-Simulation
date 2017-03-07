package ehs.mat.game;

/**
 * A class to provide functionality specific to Blackjack, separate from generic 52-card decks; provides integration with BlackjackGame
 * @author Joseph Azevedo
 */
public class BlackjackDeck extends Deck {
    private int splitCount = 0;
    private boolean doubleDown = false;

    public BlackjackDeck() {
        super();
    }

    public BlackjackDeck(boolean empty) {
        super(empty);
    }

    public BlackjackDeck(int deckCount) {
        super(deckCount);
    }

    /**
     * Gets the maximum total of the blackjack deck without going over 21
     * @return The calculated total
     */
    public int getMaxTotal() {
        int total = 0;
        int numOfAces = 0;
        for(Card c : cards) {
            if(c.getFace() != Card.Face.ACE) {
                total += getCardValue(c);
            } else {
                ++numOfAces;
            }
        }

        // Calculate the aces that count as either 1 or 11
        int max = 21 - total;
        int elevenAces = numOfAces;
        for(; (numOfAces - elevenAces) + elevenAces*11 > max; --elevenAces);

        return total + (numOfAces - elevenAces) + elevenAces*11;
    }

    /**
     * @return Whether or not the deck contains an ace
     */
    public boolean isSoft() {
        boolean containsAces = false;
        for(Card c : cards) {
            if(c.getFace() == Card.Face.ACE) {
                containsAces = true;
            }
        }
        return containsAces;
    }

    /**
     * @return Gets the number of times the deck has been split
     */
    public int getSplitCount() {
        return splitCount;
    }

    /**
     * @return Whether or not the deck has been split
     */
    public boolean hasBeenSplit() {
        return (getSplitCount() != 0);
    }

    /**
     * @return Whether or not the deck can be split (only two cards, both same face, hasn't already been split twice
     */
    public boolean canSplit() {
        return (cards.size() == 2 && cards.get(0).getFace() == cards.get(1).getFace() && getSplitCount() < 2);
    }

    /**
     * @return Whether or not the deck is only two cards that add up to 21
     */
    public boolean containsBlackjack() {
        if(cards.size() == 2 && getMaxTotal() == 21) return true;
        else return false;
    }

    /**
     * Doubles down the betting stakes for the deck
     */
    public void doubleDown() {
        doubleDown = true;
    }

    /**
     * @return Whether or not the deck has been doubled down
     */
    public boolean isDoubleDown() {
        return doubleDown;
    }

    /**
     * @return The bet value for the hand, taking into consideration doubling down
     */
    public double getBetValue() {
        return doubleDown ? 2d : 1d;
    }

    /**
     * @return The minimum total of a soft hand, or the total of a hard hand
     */
    public int getMinTotal() {
        int minTotal = 0;
        for(Card c : cards) {
            minTotal += getCardValue(c);
        }
        return minTotal;
    }

    /**
     * @return Whether or not the minimum total for the deck is above 21 (bust)
     */
    public boolean isBust() {
        return getMinTotal() > 21;
    }

    /**
     * Utility method to retrieve the value of a card specific to blackjack
     * @param card The card
     * @return The associated value, 1 for aces
     */
    public static int getCardValue(Card card) {
        switch(card.getFace()) {
            case ACE: return 1;
            case TWO: return 2;
            case THREE: return 3;
            case FOUR: return 4;
            case FIVE: return 5;
            case SIX: return 6;
            case SEVEN: return 7;
            case EIGHT: return 8;
            case NINE: return 9;

            case TEN:
            case JACK:
            case QUEEN:
            case KING:
            default:
                return 10;
        }
    }

    // Package scope: only for use by BlackjackGame
    void split() {
        ++splitCount;
    }
}
