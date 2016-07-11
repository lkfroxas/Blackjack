package test;

import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import model.Card;

public class TestCard {
	@Rule
		public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testLegalCards() {
		Card aceClubs = new Card(0);
		Card sevenHearts = new Card(32);
		Card queenSpades = new Card(50);
		
		// Test rank calculation
		assertEquals(1, aceClubs.getRank());
		assertEquals(7, sevenHearts.getRank());
		assertEquals(12, queenSpades.getRank());
		
		// Test value determination
		assertEquals(11, aceClubs.getValue());
		assertEquals(7, sevenHearts.getValue());
		assertEquals(10, queenSpades.getValue());
	}
	
	@Test
	public void testIllegalCards() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Card number must be between 0 and 52");
		new Card(-1);
		new Card(53);
	}

}
