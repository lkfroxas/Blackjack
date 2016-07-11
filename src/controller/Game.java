package controller;
import model.*;
import view.*;

/**
 * Controller class that manages a game of Blackjack.
 * 
 * @author Lowell Roxas
 */
public class Game implements TableListener {
	private Deck deck;
	private Player player;
	private Dealer dealer;
	private Table view;
	
	/**
	 * Sets up the primary elements of the Blackjack game: the
	 * dealer, the player, the deck, and the game table (UI).
	 * 
	 * @param deck The deck of cards
	 * @param dealer The dealer/house
	 * @param player The player
	 * @param view The UI
	 */
	public Game(Deck deck, Dealer dealer, Player player, Table view) {
		this.deck = deck;
		this.dealer = dealer;
		this.player = player;
		this.view = view;
		
		this.dealer.setId(0);
		this.player.setId(1);
		this.view.setScores();
		
		newGame();
	}	
	
	/**
	 * Sets up a new game of Blackjack, giving new starting hands
	 * to the player and dealer.
	 */
	public void newGame() {
		view.newGame(); // reset the table
		
		dealer.newGame(); // dealer's starting hand
		addCard(dealer);
		addCard(dealer);
		
		player.newGame(); // player's starting hand
		addCard(player);
		addCard(player);
		
		view.setValues(false); // update player's hand score on UI
		
		if (player.getHandValue() == 21) { // check for player blackjack
			view.setButtons(false);
			findWinner(0);
		} else view.setButtons(true); // begin normal play
	}
	
	/**
	 * Adds a card from the deck to either the dealer or the player's 
	 * hand. The UI is updated to reflect this.
	 * 
	 * @param person Person receiving a new card.
	 */
	public void addCard(Dealer person) {
		Card draw = deck.dealCard();
		person.addToHand(draw);
		view.addCard(person.getId(), draw);
	}
	
	/**
	 * Adds a card to the player's hand, then updates and checks the
	 * value of the player's hand. If the value exceeds over 21 (after the
	 * player's internal aceCheck()), the player busts.
	 */
	public void hit() {
		addCard(player);
		view.setValues(false);
		if (player.getHandValue() > 21)	{
			view.setButtons(false);
			findWinner(3);
		}
	}
	
	/**
	 * Flips the dealer's first card, then checks the dealer's hand to
	 * determine the results of the game. If the dealer has a blackjack,
	 * the dealer wins. Otherwise, the dealer draws until the value of its
	 * hand exceeds 17. If the value is then over 21, the dealer busts and
	 * the player wins. Otherwise, the dealer and player's hands are compared.
	 */
	public void stay() {
		view.setButtons(false);
		view.dealerFlip();
		if (dealer.getHandValue() == 21) findWinner(2);
		else {
			while (dealer.getHandValue() < 17)
				addCard(dealer);
			if (dealer.getHandValue() > 21) findWinner(1);
			else findWinner(4);
		}
	}
	
	/**
	 * Given the value of the parameter, determines a winner and updates the
	 * player's scores accordingly.
	 * Value of <code>scenario</code>:
	 * <ul>
	 *    <li>0: The player has a blackjack. If the dealer does not have
	 *    a blackjack, the player wins. Otherwise the game is tied.</li>
	 *    <li>1: The dealer busts. The player wins.</li>
	 *    <li>2: The dealer has a blackjack while the player doesn't. The
	 *    player loses.</li>
	 *    <li>3: The player busts. The player loses.</li>
	 *    <li>
	 *       4: No blackjacks or busts occur. The hands are compared.
	 *       <ul>
	 *          <li>The value of the player's hand is higher. The player wins.</li>
	 *          <li>The value of the player's hand is lower. The player loses.</li>
	 *          <li>The value of both hands are equal. The game is tied.</li>
	 *       </ul>
	 *    </li>
	 * </ul>
	 * 
	 * @param scenario  Denotes the game-ending conditions
	 * @throws IllegalArgumentException If <code>scenario</code> does not
	 * 									equal an integer from 0 to 4.
	 */
	public void findWinner (int scenario) throws IllegalArgumentException {
		if (scenario != 3) view.setValues(true);
		switch (scenario) {
		case 0: { // player blackjack
			view.dealerFlip();
			if (dealer.getHandValue() != 21) {
				player.addWin(); // no house blackjack  = win
				view.setResult(0);
			} else {
				player.addTie(); // house blackjack = tie;
				view.setResult(2);
			}
			break;
		}
		case 1: { // dealer busts = win
			player.addWin();
			view.setResult(0);
			break;
		}
		case 2: { // house blackjack + no player blackjack = loss
			player.addLoss();
			view.setResult(1);
			break;
		}
		case 3: { // player busts = loss
			player.addLoss();
			view.setResult(1);
			break;
		}
		case 4: { // no blackjacks, no busts
			if (player.getHandValue() > dealer.getHandValue()) {
				player.addWin();
				view.setResult(0);
			} else if (player.getHandValue() < dealer.getHandValue()) {
				player.addLoss();
				view.setResult(1);
			} else {
				player.addTie();
				view.setResult(2);
			}
			break;
		}
		default: throw new IllegalArgumentException();
		}
		
		view.setScores(); // update scores
	}
}
