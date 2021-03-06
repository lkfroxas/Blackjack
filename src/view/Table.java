package view;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.net.URL;
import java.util.ArrayList;
import model.*;
import javax.swing.SwingConstants;

/**
 * Graphic user interface for a Blackjack game.
 * 
 * @author Lowell Roxas
 *
 */
public class Table {

	private JFrame frame;
	private JLayeredPane dealerPanel, playerPanel;
	private JButton btnHit;
	private JButton btnStay;
	private JButton btnReset;
	private JLabel lblHandValues; // displays values of hands
	private JLabel lblResult; // displays game result message: win, tie, or losses
	private JLabel lblScore; // displays player's total wins/ties/losses
	
	private TableListener tableListener; // observer
	private Dealer dealer;
	private Player player;
	private ArrayList<ArrayList<JLabel>> lblHands; // outer ArrayList contains the hands
												   // inner ArrayList contains the cards
	private ArrayList<JLayeredPane> panels; // panels for containing hands
	private int[] cardCoords = {0, 0, 84, 122}; // coordinates for card image placement
	private int[] panelLayers;
	
	private String back = "/resources/back.png"; // card back image
	private String fmtValues1 = "Your Hand: %d"; // Shows value of player's hand.
	private String fmtValues2  = "<html>Your Hand: %d\tDealer's Hand: %d</html>"; // Player and dealer hands
	private String fmtRecord = "Wins: %d  Ties: %d  Losses: %d"; // For lblScore.
	private String[] resultStrings = {"You Win!", "You Lose!", "Push!"}; // Game results

	/**
	 * Create the application.
	 */
	public Table(Dealer dealer, Player player) {
		this.dealer = dealer;
		this.player = player;
		lblHands = new ArrayList<ArrayList<JLabel>>(2);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panels = new ArrayList<JLayeredPane>();
		
		dealerPanel = new JLayeredPane();
		panels.add(dealerPanel);
		//dealerPanel.setLayout(new CardLayout(0, 0));
		
		playerPanel = new JLayeredPane();
		panels.add(playerPanel);
		//playerPanel.setLayout(new CardLayout(0, 0));
		
		btnHit = new JButton("Hit");
		btnHit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tableListener.hit();
			}
		});
		
		btnStay = new JButton("Stay");
		btnStay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tableListener.stay();
			}
		});
		
		btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tableListener.newGame();
			}
		});
		
		lblHandValues = new JLabel("");
		lblHandValues.setHorizontalAlignment(SwingConstants.CENTER);
		lblResult = new JLabel("");
		lblResult.setHorizontalAlignment(SwingConstants.CENTER);
		lblScore = new JLabel("");
		
		// Swing group layout management
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(dealerPanel, GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblHandValues, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnHit, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnStay, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblScore, GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnReset, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
						.addComponent(playerPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE))
					.addContainerGap())
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addComponent(lblResult, GroupLayout.PREFERRED_SIZE, 584, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(dealerPanel, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblResult, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(lblHandValues, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(playerPanel, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnHit)
						.addComponent(btnStay)
						.addComponent(btnReset)
						.addComponent(lblScore, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)))
		);
		frame.getContentPane().setLayout(groupLayout);
		
		frame.setTitle("Blackjack");
		frame.setVisible(true);
	}
	
	/**
	 * Sets the observer.
	 * 
	 * @param tableListener  Observer to be set
	 */
	public void addTableListener(TableListener tableListener) {
		this.tableListener = tableListener;
	}
	
	/**
	 * Adds a card to the specified hand, displaying it in the appropriate panel. Uses
	 * the id parameter to determine which hand the Card is added to. If the card belongs
	 * to the dealer and is the dealer's first card, the card back image is set as its
	 * JLabel's icon to signify that it is face-down.
	 * 
	 * @param id The id of the hand/person receiving the card; 0 = dealer, 1 = player
	 * @param card The card being added
	 */
	public void addCard(int id, Card card) {
		JLabel temp = new JLabel();
		lblHands.get(id).add(temp);
		temp.setBounds(cardCoords[0] + (lblHands.get(id).size() - 1) * 45,
					   cardCoords[1], cardCoords[2], cardCoords[3]);
		
		// set card image
		URL url;
		if (id == 0 && lblHands.get(0).size() == 1)
			url = getClass().getResource(back); // dealer's first card is face-down
		else url = getClass().getResource(card.getImgName());
		temp.setIcon(new ImageIcon(url));
		
		// display image in appropriate panel and increment hand panel layers
		panels.get(id).add(temp);
		panels.get(id).setLayer(temp, panelLayers[id]);
		panelLayers[id]++;
	}
	
	/**
	 * Performs initializations necessary to start a new Blackjack game. Sets up the dealer
	 * and player hand.
	 */
	public void newGame() {
		if (!lblHands.isEmpty()) reset(); // reset the game if hands already exist
		lblHands.add(new ArrayList<JLabel>(5)); // dealer's hand
		lblHands.add(new ArrayList<JLabel>(5)); // player's hand
		panelLayers = new int[lblHands.size()]; // layers for the hand panels
		for (int i = 0; i < lblHands.size(); i++) panelLayers[i] = 0;
	}
	
	/**
	 * Flips the first card in the dealer's hand face-up.
	 */
	public void dealerFlip() {
		URL url = getClass().getResource(dealer.getCard(0).getImgName()); // get card's image
		lblHands.get(0).get(0).setIcon(new ImageIcon(url));
	}
	
	/**
	 * Displays the current values of the player's hand. If the parameter is set to
	 * <code>true</code>, then the value of the dealer's hand is shown as well.
	 * 
	 * @param showHouse Determines which scores are shown:
	 * 					<ul><li><b>True:</b> display the value of the player and dealer's hand</li>
	 * 					    <li><b>False:</b> display the value of the player's hand only</li></ul>
	 */
	public void setValues(boolean showHouse) {
		String values = showHouse ? String.format(fmtValues2, player.getHandValue(), dealer.getHandValue()) :
									String.format(fmtValues1, player.getHandValue());
		lblHandValues.setText(values);
	}
	
	/**
	 * Displays the result of the game: win, tie, or loss.
	 * 
	 * @param result Result of the game played
	 */
	public void setResult(int result) {
		lblResult.setText(resultStrings[result]);
	}
	
	/**
	 * Updates the player's game record of wins, ties, and losses.
	 */
	public void setScores() {
		lblScore.setText(String.format(fmtRecord, player.getWins(), player.getTies(), player.getLosses()));
	}
	
	/**
	 * Enables and disables the hit, stay, and reset buttons. The parameter <code>enable</code> should
	 * be set to <code>true</code> when the player is allowed to press the Hit and Stay buttons. The
	 * parameter should be set to <code>false</code> when the game is finished and the player can choose
	 * to reset the game.
	 * 
	 * @param enable Determines which buttons are enabled:
	 * 				 <ul>
	 * 				    <li><b>True</b>: Hit and Stay enabled, Reset disabled</li>
	 * 					<li><b>False</b>: Hit and Stay disabled, Reset enabled</li>
	 * 				 </ul>
	 */
	public void setButtons(boolean enable) {
		btnHit.setEnabled(enable);
		btnStay.setEnabled(enable);
		btnReset.setEnabled(!enable);
	}
	
	/**
	 * Clears the existing hands to make way for a new game of Blackjack.
	 */
	public void reset() {
		// Clear images, reset hands		
		for (int i = panels.size()-1; i >= 0; i--) {
			for (JLabel cardImg : lblHands.get(i)) {
				cardImg.setVisible(false);
				panels.get(i).remove(cardImg);
			}
			lblHands.remove(i);
		}
		
		// Reset result message
		lblResult.setVisible(false);
		lblResult.setText(null);
		lblResult.setVisible(true);
	}
}
