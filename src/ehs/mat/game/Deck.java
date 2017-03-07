package ehs.mat.game;

import java.util.ArrayList;

/**
 * A generic object to represent a deck of normal playing cards that can be instantiated either empty or full
 * @author Joseph Azevedo
 */
public class Deck {
    protected ArrayList<Card> cards;

    /**
     * Initializes a new deck object full of one full 52-card deck of standard playing cards
     */
    public Deck() {
        cards = new ArrayList<>();
        add52Cards();
    }

    /**
     * Initializes a new deck object that is optionally empty
     * @param empty Whether or not to start empty
     */
    public Deck(boolean empty) {
        cards = new ArrayList<>();
        if(!empty) {
            add52Cards();
        }
    }

    /**
     * Initializes a new deck object with decks amount of decks added
     * @param decks Amount of decks to be added
     */
    public Deck(int decks) {
        cards = new ArrayList<>();
        for(int i = 0; i < decks; ++i) {
            add52Cards();
        }
    }

    /**
     * Adds one full 52-card deck of standard playing cards
     */
    private void add52Cards() {
        Card.Suit[] suits = {Card.Suit.CLUBS, Card.Suit.DIAMONDS, Card.Suit.HEARTS, Card.Suit.SPADES};
        Card.Face[] faces = {Card.Face.ACE, Card.Face.TWO, Card.Face.THREE, Card.Face.FOUR, Card.Face.FIVE, Card.Face.SIX, Card.Face.SEVEN, Card.Face.EIGHT, Card.Face.NINE, Card.Face.TEN, Card.Face.JACK, Card.Face.QUEEN, Card.Face.KING};
        for(int i = 0; i < suits.length; ++i) {
            for(int j = 0; j < faces.length; ++j) {
                cards.add(new Card(suits[i], faces[j]));
            }
        }
    }

    /**
     * Shuffles the cards in the deck
     */
    public void shuffle() {
        for(int i = 0; i < cards.size(); ++i) {
            int rand = (int)(Math.random() * (i + 1));
            Card card = cards.remove(rand);
            cards.add(card);
        }
    }

    public Card get(int i) {
        return cards.get(i);
    }

    public Card draw() {
        return cards.remove(0);
    }

    public void add(Card c) {
        cards.add(c);
    }
}
