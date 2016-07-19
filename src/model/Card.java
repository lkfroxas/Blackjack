package model;

/**
 * Represents a playing card, containing data and methods for
 * a card's rank and value. Cards are constructed using a card
 * number ranging from 0 to 52. Each number corresponds to a
 * different card (see {@link #Card() <code>Card()</code} for
 * details.
 * 
 * @author Robby Hart, Robert King, Lowell Roxas, Christopher Ruiter
 */
public class Card {
	private int cardNumber; // from 0 to 52
	private int rank; // card's rank (1 = ace, 13 = king)
	private int value; // card's value in blackjack
	private String imgName; // file path for card face image
	
	/**
	 * Creates a new Card object using a card number ranging from
	 * 0 to 52. The card's rank, value, and image file name are
	 * determined using this number. The rank is the remainder of
	 * cardNumber divided by 13, plus 1. The value is assigned based 
	 * on what range the rank falls into.
	 * <br>
	 * <ul>
	 *    <li>Rank 1: Aces, value 11</li>
	 *    <li>Rank 2-9: Twos to Nines, value equaling their number</li>
	 *    <li>Rank 10-13: Jacks to Kings, value 10</li>
	 * </ul>
	 * 
	 * @param cardNumber Card's ID, ranging from 0 to 52.
	 */
	public Card(int cardNumber) {
		if (cardNumber < 0 || cardNumber > 52)
			throw new IllegalArgumentException("Card number must be between 0 and 52");
		this.cardNumber = cardNumber;
		this.rank = (cardNumber % 13) + 1;
		
		// Set card value
		if (this.rank == 1) value = 11; // ace
		else if (this.rank > 1 && this.rank < 11) value = this.rank; // 2-9
		else value = 10; // face cards
		
		imgName = "/resources/" + this.cardNumber + ".png";
	}
	
	/**
	 * Returns the card's ID number.
	 * @return Card's ID number
	 */
	public int getCardNumber() {
		return cardNumber;
	}
	
	/**
	 * Returns the card's rank.
	 * @return Card's rank
	 */
	public int getRank() {
		return rank;
	}
	
	/**
	 * Returns the card's value.
	 * @return Card's value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Returns the card's image name.
	 * @return Card's image name
	 */
	public String getImgName() {
		return imgName;
	}
}
