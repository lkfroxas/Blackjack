package model;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Stack;

/**
 * Represents a deck of playing cards. The deck, represented by
 * an <code>ArrayList</code> of {@link #model.Card <code>Card</code>}
 * elements, is shuffled upon initialization. Drawing from it will 
 * return the first item in the list. If the deck is empty when an
 * attempt to draw is made, a new deck is made and shuffled.
 * 
 * @author Robby Hart, Robert King, Lowell Roxas, Christopher Ruiter
 *
 */
public class Deck {
	private Stack<Card> deck;
	
	/**
	 * Initializes and shuffles the deck. See {@link #shuffle()
	 * <code>shuffleDeck()</code>}.
	 */
	public Deck() {
		shuffle();
	}
	
	/**
	 * Creates and shuffles a new deck of cards. First, a temporary 
	 * straight deck is generated, with cards arranged by their card
	 * numbers in ascending order. Then the actual deck is initialized.
	 * Cards in the straight deck are chosen at random and added to
	 * the actual deck.
	 */
	public void shuffle() {
		ArrayList<Card> straightDeck = new ArrayList<Card>(52);
		for (int i = 0; i < 52; i++) {
			straightDeck.add(new Card(i));
		}
		
		deck = new Stack<Card>();
		int index = 0;
		while (!straightDeck.isEmpty()) {
			index = ThreadLocalRandom.current().nextInt(0, straightDeck.size());
			deck.push(straightDeck.remove(index));
		}
	}
	
	/**
	 * Deals the first card in the deck, returning it and removing it from
	 * the deck. If the deck is empty, {@link #shuffledeck()
	 * <code>shuffleDeck()</code>} is called.
	 * 
	 * @return The first <code>Card</code> element in <code>deck</code>
	 */
	public Card dealCard() {
		if (deck.isEmpty()) shuffle();
		return deck.pop();
	}
}
