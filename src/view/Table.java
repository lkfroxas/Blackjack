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

public class Table {

	private JFrame frame;
	private JLayeredPane dealerPanel, playerPanel;
	private JButton btnHit;
	private JButton btnStay;
	private JButton btnReset;
	private JLabel lblHandValues;
	private JLabel lblResult;
	private JLabel lblScore;
	
	private TableListener tableListener;
	private Dealer dealer;
	private Player player;
	private ArrayList<ArrayList<JLabel>> lblHands;
	private ArrayList<JLayeredPane> panels;
	private int[] cardCoords = {0, 0, 84, 122};
	private int[] panelLayers;
	
	private String back = "/resources/back.png";
	private String fmtValues1 = "Your Hand: %d"; // Shows value of player's hand.
	private String fmtValues2  = "<html>Your Hand: %d\tDealer's Hand: %d</html>"; // Player and dealer hands
	private String fmtRecord = "Wins: %d  Ties: %d  Losses: %d"; // For lblScore.
	private String[] resultStrings = {"You Win!", "You Lose!", "Push!"};

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
		lblResult = new JLabel("");
		lblScore = new JLabel("");
		
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
	
	public void addTableListener(TableListener tableListener) {
		this.tableListener = tableListener;
	}
	
	public void addCard(int id, Card card) {
		JLabel temp = new JLabel();
		lblHands.get(id).add(temp);
		temp.setBounds(cardCoords[0] + (lblHands.get(id).size() - 1) * 45,
					   cardCoords[1], cardCoords[2], cardCoords[3]);
		URL url;
		if (id == 0 && lblHands.get(0).size() == 1)
			url = getClass().getResource(back);
		else url = getClass().getResource(card.getImgName());
		temp.setIcon(new ImageIcon(url));
		
		panels.get(id).add(temp);
		panels.get(id).setLayer(temp, panelLayers[id]);
		panelLayers[id]++;
	}
	
	public void newGame() {
		if (!lblHands.isEmpty()) reset();
		lblHands.add(new ArrayList<JLabel>(5)); // dealer
		lblHands.add(new ArrayList<JLabel>(5)); // player
		panelLayers = new int[lblHands.size()];
		for (int i = 0; i < lblHands.size(); i++) panelLayers[i] = 0;
	}
	
	public void dealerFlip() {
		URL url = getClass().getResource(dealer.getCard(0).getImgName());
		lblHands.get(0).get(0).setIcon(new ImageIcon(url));
	}
	
	public void setValues(boolean showHouse) {
		String values = showHouse ? String.format(fmtValues2, player.getHandValue(), dealer.getHandValue()) :
									String.format(fmtValues1, player.getHandValue());
		lblHandValues.setText(values);
	}
	
	public void setResult(int result) {
		lblResult.setText(resultStrings[result]);
	}
	
	public void setScores() {
		lblScore.setText(String.format(fmtRecord, player.getWins(), player.getTies(), player.getLosses()));
	}
	
	public void setButtons(boolean enable) {
		btnHit.setEnabled(enable);
		btnStay.setEnabled(enable);
		btnReset.setEnabled(!enable);
	}
	
	public void reset() {
		// Clear images, reset hands		
		for (int i = panels.size()-1; i >= 0; i--) {
			for (JLabel cardImg : lblHands.get(i)) {
				cardImg.setVisible(false);
				panels.get(i).remove(cardImg);
			}
			lblHands.remove(i);
		}
		
		lblResult.setVisible(false);
		lblResult.setText(null);
		lblResult.setVisible(true);
	}
}
