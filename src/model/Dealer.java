package model;

import java.util.ArrayList;

/**
 * Represents a Blackjack dealer, containing data
 * and methods for managing a dealer's hand of cards and
 * the value of the hand. The Player class is a
 * child class of Dealer.
 * 
 * @author Robby Hart, Robert King, Lowell Roxas, Christopher Ruiter
 *
 */
public class Dealer {
	private int handValue; // Value of player's hand
	private ArrayList<Card> hand; // Player's hand
	private int numAces, lastAceIndex; // for checking aces
	private int id; // 0 for dealer, 1 for player
	
	/**
	 * Initializes a new player/dealer with an empty hand and hand value
	 * of 0.
	 */
	public Dealer() {
		newGame();
	}
	
	public void newGame() {
		handValue = 0;
		hand = new ArrayList<Card>();
		numAces = 0;
		lastAceIndex = 0;
	}
	
	/**
	 * Adds a <code>Card</code> to the player/dealer's hand. The hand's
	 * value is updated accordingly. If the hand's value exceeds 21,
	 * {@link #aceCheck() <code>aceCheck()</code>} is called.
	 * 
	 * @param tempCard Card added to hand
	 */
	public void addToHand(Card tempCard) {
		hand.add(tempCard);
		handValue += tempCard.getValue();
		if (handValue > 21) aceCheck();
	}
	
	/**
	 * To be called if the player/dealer's hand value exceeds 21. Checks the
	 * player/dealer's hand for any Aces, then reduces the hand value by 10 for
	 * each Ace or until the hand value goes below 21. Effectively, Aces
	 * contributing to this value reduction have their card value reduced
	 * to 1, and are no longer eligible for counting to any future
	 * reduction.
	 */
	public void aceCheck() {
		numAces = 0;
		
		// count aces from the position of the last counted ace
		for (int i = lastAceIndex; i < hand.size(); i++) {
			if (hand.get(i).getCardNumber() % 13 == 0) {
				numAces++;
				lastAceIndex = i + 1;
			}
		}
		
		// reduce hand value for each ace or until the hand value <= 21
		while (handValue > 21 && numAces > 0) {
			handValue -= 10;
			numAces--;
		}
	}
	
	/**
	 * Returns the value of the player/dealer's hand.
	 * 
	 * @return Value of player/dealer's hand
	 */
	public int getHandValue() {
		return handValue;
	}

	/**
	 * Returns the player/dealer's ID. If this object is the game's
	 * dealer, the return value should equal 0. If this object is the
	 * game's player, the return value should equal 1.
	 * 
	 * @return Player/dealer's ID
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Sets the player/dealer's ID. If this object is the game's dealer,
	 * ID should be set to 0. If not, ID should be set to 1.
	 * 
	 * @param id Desired ID (0 for dealer, 1 for player)
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Returns the card at the desired index in the player/dealer's hand.
	 * This method's main use is finding the dealer's first card, which is
	 * face-down initially, so it can be flipped face-up.
	 * 
	 * @param index Index in player/dealer's hand
	 * @return Card at index in player/dealer's hand
	 */
	public Card getCard(int index) {
		return hand.get(0);
	}
}
