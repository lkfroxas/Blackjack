package core;

import java.awt.EventQueue;
import controller.Game;
import view.Table;
import model.*;

/**
 * Initializes the game.
 * @author Lowell Roxas
 */
public class Main {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Deck deck = new Deck();
					Player player = new Player();
					Dealer dealer = new Dealer();
					Table view = new Table(dealer, player);
					Game game = new Game(deck, dealer, player, view);
					view.addTableListener(game);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
