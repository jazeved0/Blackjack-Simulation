package ehs.mat.game;

import ehs.mat.strategy.Strategy;
import java.util.ArrayList;

/**
 * An object representing one instance of a blackjack game
 * @author Joseph Azevedo
 */
public class BlackjackGame {

    private BlackjackDeck deck;
    private ArrayList<BlackjackDeck> playerDecks;
    private BlackjackDeck dealerDeck;

    /**
     * Initializes the deck and game-board
     * @param deckCount The number of decks to use and draw from
     */
    public BlackjackGame(int deckCount) {
        // Create the deck with deckCount*52 cards
        deck = new BlackjackDeck(deckCount);

        // Shuffle the deck of cards
        deck.shuffle();

        // Initialize deck objects
        playerDecks = new ArrayList<>();
        playerDecks.add(new BlackjackDeck(true));
        dealerDeck = new BlackjackDeck(true);

        // Draw 2 cards to the player
        playerDecks.get(0).add(deck.draw());
        playerDecks.get(0).add(deck.draw());

        // Deal 2 cards to the dealer (one face-down, in the hole)
        dealerDeck.add(deck.draw());
        dealerDeck.add(deck.draw());
    }

    /**
     * Simulates the game until finished
     * @param strategy The strategy to use when playing the player's hands
     * @return A number representing the change in bet that would have been inflicted over the course of the game
     */
    public double playUntilFinished(Strategy strategy) {
        // Play the player's hand according to the strategy provided
        strategy.playHand(this);

        // Is the total of the dealer's hand below 17 points?
        while(dealerDeck.getMaxTotal() < 17) {
            // Hit the dealer's hand
            dealerDeck.add(deck.draw());
        }

        // Determine the change in bet of each hand and add up to generate overall change in bet for the round
        double deltaBet = 0d;
        for(BlackjackDeck playerDeck : playerDecks) {
            // Does the current hand possess a blackjack?
            if(playerDeck.containsBlackjack()) {
                if(dealerDeck.containsBlackjack()) {
                    deltaBet += 0d; // Push, +0
                } else {
                    if(playerDeck.hasBeenSplit()) deltaBet += 1.0d; // Player Split Blackjack, +1
                    else deltaBet += 1.5d; // Player Normal Blackjack, +1.5
                }
            } else {
                // Add or subtract a quantity to the deltaBet according to the outcomes of the hands
                if(playerDeck.isBust()) deltaBet -= (1.0d * playerDeck.getBetValue()); // Player bust, Loss, -1
                else if (dealerDeck.isBust()) deltaBet += (1.0d * playerDeck.getBetValue()); // Dealer bust, Win, +1
                else if (dealerDeck.containsBlackjack()) deltaBet -= (1.0d * playerDeck.getBetValue()); // Dealer Blackjack, Loss, -1
                else if (playerDeck.getMaxTotal() < dealerDeck.getMaxTotal()) deltaBet -= (1.0d * playerDeck.getBetValue()); // Player Hand < Dealer Hand, Loss, -1
                else if (playerDeck.getMaxTotal() > dealerDeck.getMaxTotal()) deltaBet += (1.0d * playerDeck.getBetValue()); // Player Hand > Dealer Hand, Win, +1
                else {
                    // Push, playerDeck.getMaxTotal() == dealerDeck.getMaxTotal(), +0
                    deltaBet += 0d;
                }
            }

        }
        return deltaBet;
    }

    /**
     * Splits the given deck into two, draws a card for each, and adds the new deck to playerDecks
     * Precondition: toSplit.canSplit() == true
     * @param toSplit The deck to split
     */
    public void split(BlackjackDeck toSplit) {
        BlackjackDeck newDeck = new BlackjackDeck();
        newDeck.add(toSplit.draw());
        toSplit.split();
        newDeck.split();

        // Draw a new card for each deck
        toSplit.add(deck.draw());
        newDeck.add(deck.draw());

        playerDecks.add(newDeck);
    }

    public ArrayList<BlackjackDeck> getPlayerDecks() {
        return playerDecks;
    }

    public Card getFaceUpDealerCard() {
        return dealerDeck.get(0);
    }

    public void hit(BlackjackDeck deck) {
        deck.add(this.deck.draw());
    }
}
