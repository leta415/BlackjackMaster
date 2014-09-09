package personal.blackjack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
	static {
		for (int i = 1; i < 14; i++) {
			if(i < 11)	valuesMap.put(i, i);
			else		valuesMap.put(i, 10);
		}
	}
	
	/* CONSTRUCTORS */
	public BlackjackDeck () {
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
		int i = 0;
		while (i < 7) {
			GSRShuffle();
			i++;
		}
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
	
	/**
	 * Simulate my overhand shuffle - recursively cutting the stack and placing the bottom part on top. 
	 * Recommended for single deck only to best simulate human shuffling technique.
	 */
	public void myOverhandShuffle() {
		Random r = new Random();
		int cutFromTop = r.nextInt(5) + 7;	//my special formula = number of cards to cut off from top
		this.cardsList = recursiveCuts (cutFromTop, new ArrayList<Integer>(), cardsList);
	}
	public List<Integer> recursiveCuts (int cutFromTop, List<Integer> updatedList, List<Integer> leftoverList) {
		if (leftoverList.size() < cutFromTop) {
			Collections.reverse(leftoverList);
			updatedList.addAll(leftoverList);
			return updatedList;
		}
		System.out.println("recursive call");
		List<Integer> top = leftoverList.subList(0, cutFromTop);
		List<Integer> bottom = leftoverList.subList(cutFromTop, leftoverList.size());
		Collections.reverse(top);
		updatedList.addAll(top);
		
		if (cutFromTop > 1 && leftoverList.size() < 15) {
			cutFromTop--;
		}
		return recursiveCuts (cutFromTop, updatedList, bottom);
	}
	
	/**
	 * Simulate Mongean shuffle - take top card and put it in new stack, then bottom card. Alternate.
	 */
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
		System.out.println("Before shuffling: " + deck.toString());
		deck.myOverhandShuffle();
		System.out.println("After shuffling: " + deck.toString());
	}
}
