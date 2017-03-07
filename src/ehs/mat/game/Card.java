package ehs.mat.game;

/**
 * A simple data structure class to represent one card
 * @author Joseph Azevedo
 */
public class Card {

    private Suit suit;
    private Face face;

    /**
     * Initializes a new card with the specified suit and face value
     * @param suit
     * @param face
     */
    public Card(Suit suit, Face face) {
        this.suit = suit;
        this.face = face;
    }

    public Suit getSuit() {
        return suit;
    }

    public Face getFace() {
        return face;
    }

    public enum Suit {
        SPADES, CLUBS, DIAMONDS, HEARTS
    }

    public enum Face {
        ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
    }
}
