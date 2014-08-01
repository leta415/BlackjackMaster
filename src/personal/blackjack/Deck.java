package personal.blackjack;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to represent a deck of playing cards.
 * 
 * @author Leta
 *
 */
public abstract class Deck {
	protected Map<Integer,Card> cardsMap = new HashMap<Integer,Card>();
	protected int[] cardsArray;
	protected int identityCounter = 0;
	
	/** Inner class to represent a single card */
	protected class Card {
		int identity; //unique id
		int value; // Actual value
		char face; // Printed value
		int suit; // Heart, spade, clover, diamond
		boolean used;
		
		Card (char face, int suit) {
			//TODO validation
			this.face = face;
			this.suit = suit;
			this.identity = identityCounter++;
			cardsMap.put(this.identity, this);
			this.used = false;
		}
		
		void setValue (int value) {
			this.value = value;
		}
		void setUsed () {
			this.used = true;
		}
		boolean isUsed () {
			return this.used;
		}
	}
	
	public abstract void shuffle ();
}
