package personal.blackjack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

import personal.blackjack.Deck.Card;

/**
 * Class that extends Deck class to represent a Blackjack deck.
 * 
 * @author Leta
 *
 */
public class BlackjackDeck extends Deck {
	private static final int DEFAULT_NUMBER_OF_STANDARD_DECKS = 6;
	private static Map<Integer, Integer> valuesMap = new HashMap<Integer, Integer>(13);
	
	public BlackjackDeck () {
		// Initialize valuesMap
		for (int i = 1; i < 14; i++) {
			if(i < 11)	valuesMap.put(i, i);
			else		valuesMap.put(i, 10);
		}
		
		// Initialize Cards
		for (int d = 0; d < DEFAULT_NUMBER_OF_STANDARD_DECKS; d++) {
			for (int printVal = 1; printVal < 14; printVal++) {
				for (int suit = 1; suit < 5; suit++) {
					new Card (printVal, suit, valuesMap.get(printVal));
				}
			}
		}
		
		deckSize = (int) DEFAULT_NUMBER_OF_STANDARD_DECKS*52;
	}

	public BlackjackDeck (int numberOfDecks) {
		// Initialize valuesMap
		for (int i = 1; i < 14; i++) {
			if(i < 11)	valuesMap.put(i, i);
			else		valuesMap.put(i, 10);
		}
		// Initialize Cards
		for (int d = 0; d < numberOfDecks; d++) {
			for (int f = 1; f < 14; f++) {
				for (int s = 1; s < 5; s++) {
					new Card (f,s,valuesMap.get(f));
				}
			}
		}
		deckSize = (int) numberOfDecks*52;
	}
	
	@Override
	public void shuffle() {	
		Integer[] array = new Integer[cardsList.size()];
		array = (Integer[]) cardsList.toArray();		
		Random r = new Random();
		int first = r.nextInt();
		
	}
	
	/**
	 * Gilbert-Shannon-Reeds model. Need to call this 7 times to achieve true randomization.
	 */
	public void GSRShuffle () {
		Random randomGen = new Random();
		
		// Transfer cardsList to a stack for easier manipulation
		Stack<Integer> deckStack = new Stack<Integer>();
		deckStack.addAll(cardsList);
		
		// Make 2 random piles
		ArrayList<Stack<Integer>> cardPiles = new ArrayList<Stack<Integer>>();
		cardPiles.add(new Stack<Integer>());
		cardPiles.add(new Stack<Integer>());
		for (int i = 0; i < cardsList.size(); i++) {
			int whichPile = randomGen.nextInt(2);
			cardPiles.get(whichPile).push(deckStack.pop());
		}
		
		// Put the piles randomly back into one
		Integer[] array = new Integer[cardsList.size()];
		array = (Integer[]) cardsList.toArray(array);
		int whichPile = 0;
		int j = 0;
		while (j < cardsList.size()) {
			whichPile = randomGen.nextInt(2);
			Stack<Integer> pile = cardPiles.get(whichPile);
			array[j++] = pile.pop();
			if (pile.isEmpty())	break;
		}
		
		// Now that the current pile is empty, check if the other pile still has cards
		Stack<Integer> otherPile = cardPiles.get(Math.abs(whichPile-1));
		while (!otherPile.isEmpty()) {
			array[j++] = otherPile.pop();
		}
		
		cardsList = new ArrayList<Integer>(Arrays.asList(array));
	}
	
	public void riffleShuffle() {
		
	}
	
	public void mongeanShuffle() {
		Integer[] array = new Integer[cardsList.size()];
		array = (Integer[]) cardsList.toArray(array);
		ArrayList<Integer> updatedCardsList = new ArrayList<Integer>();
		for (int i = 0; i < array.length; i++) {
			if(i%2==0)	updatedCardsList.add(array[i]);
			else		updatedCardsList.add(0, array[i]);
		}
		cardsList = updatedCardsList;
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		Integer[] array = new Integer[cardsList.size()];
		array = (Integer[]) cardsList.toArray(array);		
		for (int i = 0; i < array.length; i++) {
			stringBuilder.append(array[i]);
			stringBuilder.append(",");
		}
		return stringBuilder.toString();
	}
	
	public static void main (String[] args) {
		BlackjackDeck deck = new BlackjackDeck(1);
		for (int i = 0; i < 7; i++) {
			deck.GSRShuffle();
			System.out.println(deck.toString());
		}

	}
}
