package personal.blackjack;

import java.util.ArrayList;
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
	protected ArrayList<Integer> cardsList = new ArrayList<Integer>();
	protected int identityCounter = 1;
	protected int deckSize;
	
	/** Inner class to represent a single card */
	protected class Card {
		int identity; //Unique id
		int value; // Actual value, specific to the game
		int printValue; // Printed value, 1-13
		int suit; // Heart, spade, clover, diamond <=> 1,2,3,4
		boolean used;
		
		Card (int printValue, int suit, int actualValue) {
			//TODO validation
			this.printValue = printValue;
			this.suit = suit;
			this.value = actualValue;
			this.identity = identityCounter++;
			cardsMap.put(this.identity, this);
			cardsList.add(this.identity);
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
	
	public void tossDeck () {
		identityCounter = 0;
		cardsMap.clear();
		cardsList.clear();
	}
	
	public abstract void shuffle ();
}
