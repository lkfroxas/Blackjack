package model;

/**
 * Class representing a Player in a game of Blackjack. This child
 * class extends {@link #Dealer <code>Dealer</code>}, adding data
 * and methods for a player's number of wins, ties, and losses.
 * 
 * @author Lowell Roxas
 *
 */
public class Player extends Dealer {
	private int wins, ties, losses;
	
	/**
	 * Initializes the player, setting the wins/ties/losses
	 * to 0 each. Calls upon Dealer's construction to initialize
	 * inherited data.
	 */
	public Player() {
		super();
		wins = 0;
		ties = 0;
		losses = 0;
	}
	
	/**
	 * Returns the number of games won by the player.
	 * 
	 * @return Number of wins
	 */
	public int getWins() {
		return wins;
	}
	
	/**
	 * Increases the number of games won by one.
	 */
	public void addWin() {
		wins++;
	}

	/**
	 * Returns the number of games resulting in a tie.
	 * 
	 * @return Number of ties
	 */
	public int getTies() {
		return ties;
	}
	
	/**
	 * Increases the number of tied games by one.
	 */
	public void addTie() {
		ties++;
	}
	
	/**
	 * Returns the number of games lost.
	 * 
	 * @return Number of losses
	 */
	public int getLosses() {
		return losses;
	}
	
	/**
	 * Increases the number of games lost by one.
	 */
	public void addLoss() {
		losses++;
	}
}
