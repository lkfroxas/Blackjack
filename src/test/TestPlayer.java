package test;

import static org.junit.Assert.*;
import org.junit.Test;
import model.Dealer;
import model.Card;

public class TestPlayer {

	@Test
	public void testAceCheck() {
		Dealer test = new Dealer();
		// test hand = 3 Aces; expected value = 13
		test.addToHand(new Card(0));
		test.addToHand(new Card(13));
		test.addToHand(new Card(26));
		assertEquals(13, test.getHandValue());
	}

}
