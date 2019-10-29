package dream.fcard.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import dream.fcard.logic.stats.DeckStats;
import dream.fcard.logic.stats.Statistics;
import dream.fcard.logic.storage.Schema;
import dream.fcard.model.cards.FlashCard;
import dream.fcard.model.exceptions.IndexNotFoundException;
import dream.fcard.util.json.JsonInterface;
import dream.fcard.util.json.jsontypes.JsonArray;
import dream.fcard.util.json.jsontypes.JsonObject;
import dream.fcard.util.json.jsontypes.JsonValue;

/**
 * Collection of cards.
 */
public class Deck implements JsonInterface {

    /** Name of this Deck object. */
    private String deckName;

    /** List of FlashCard objects in this Deck object. */
    private ArrayList<FlashCard> cards;

    /** List of FlashCards with High priority levels. */
    private ArrayList<FlashCard> highPriorityList;

    /** List of FlashCards with Low priority levels. */
    private ArrayList<FlashCard> lowPriorityList;

    /**
     * Statistics for this deck.
     */
    private Statistics deckStats;

    /**
     * Constructor to create a Deck object with no name and cards.
     */
    public Deck() {
        cards = new ArrayList<>();
        deckName = "untitled";

        highPriorityList = new ArrayList<>();
        lowPriorityList = new ArrayList<>();

        deckStats = new DeckStats();
    }

    /**
     * Constructor to create a Deck object with name and FlashCard objects.
     *
     * @param name String name of the Deck object.
     */
    public Deck(String name) {
        cards = new ArrayList<>();
        deckName = name;

        highPriorityList = new ArrayList<>();
        lowPriorityList = new ArrayList<>();
    }

    /**
     * Constructor to create a Deck object with name and FlashCard objects.
     *
     * @param name String name of the Deck object.
     */
    public Deck(ArrayList<FlashCard> initialCards, String name) {
        cards = initialCards;
        deckName = name;

        highPriorityList = new ArrayList<>();
        lowPriorityList = new ArrayList<>();

        addCardsToPriorityLists(initialCards);
    }

    /**
     * Adds FlashCard objects to a specific Priority List.
     *
     * @param list ArrayList of FlashCard objects to add to Priority lists.
     */
    private void addCardsToPriorityLists(ArrayList<FlashCard> list) {
        for (int i = 0; i < list.size(); i++) {
            FlashCard card = list.get(i);

            //boolean isHighPriorityCard = card.getPriority() == HIGH_PRIORITY;
            //boolean isLowPriorityCard = card.getPriority() == LOW_PRIORITY;
            //
            //if (isHighPriorityCard) {
            //    highPriorityList.add(card);
            //}
            //
            //if (isLowPriorityCard) {
            //    lowPriorityList.add(card);
            //}
        }
    }

    /**
     * Returns Json format of Deck.
     *
     * @return JsonValue Json object of current Deck.
     */
    @Override
    public JsonValue toJson() {
        JsonArray cardJson = new JsonArray();
        for (FlashCard card : cards) {
            cardJson.add(card.toJson());
        }

        JsonObject obj = new JsonObject();
        obj.put(Schema.DECK_NAME, deckName);
        obj.put(Schema.DECK_CARDS, cardJson);
        return new JsonValue(obj);
    }

    /**
     * Adds new card to the ArrayList of card objects.
     *
     * @param newCard FlashCard object to add to Deck.
     */
    public void addNewCard(FlashCard newCard) {
        cards.add(newCard);
    }

    /**
     * Returns a boolean value, if user provided index is valid.
     * Checks if a user provided index is valid.
     * Uses provide index that is 1-based, i.e. 1, 2, 3, etc.
     * If index provided is within 1, 2, 3, ..., last card, then it is valid and return true.
     * Else return false.
     *
     * @param indexProvided Boolean indicating if index provided by user is valid.
     * @return True if valid, false if invalid.
     */
    private boolean isIndexProvidedByUserValid(int indexProvided) {
        System.out.println(cards.size());
        return indexProvided <= cards.size() && indexProvided > 0;
    }

    /**
     * Removes a specified FlashCard using index from the list of FlashCards.
     *
     * @param indexProvided Integer index of FlashCard to remove from list of FlashCards.
     */
    public void removeCardFromDeck(int indexProvided) throws IndexNotFoundException {
        boolean isUserProvidedIndexValid = isIndexProvidedByUserValid(indexProvided);

        if (!isUserProvidedIndexValid) {
            String errorMessage = "Index entered is invalid: " + indexProvided;
            throw new IndexNotFoundException(errorMessage);
        }

        cards.remove(indexProvided - 1);
    }

    /**
     * Set front of card.
     *
     * @param newFront      String of new text to replace front of FlashCard.
     * @param indexProvided Index of FlashCard in the Deck.
     * @throws IndexNotFoundException If index >= number of FlashCard objects in Deck or < 0.
     */
    public void editFrontCardFromDeck(String newFront, int indexProvided) throws IndexNotFoundException {
        boolean isUserProvidedIndexValid = isIndexProvidedByUserValid(indexProvided);

        if (!isUserProvidedIndexValid) {
            String errorMessage = "Index entered is invalid: " + indexProvided;
            throw new IndexNotFoundException(errorMessage);
        }
        FlashCard cardToChange = cards.get(indexProvided - 1);
        cardToChange.editFront(newFront);
    }

    @Override
    public String toString() {
        return getName();
    }

    /**
     * Set back of FlashCard object.
     *
     * @param newBack       String of new text to replace back of FlashCard.
     * @param indexProvided Index of card in the Deck.
     * @throws IndexNotFoundException If index >= number of FlashCards in Deck or < 0.
     */
    public void editBackCardInDeck(String newBack, int indexProvided) throws IndexNotFoundException {
        boolean isUserProvidedIndexValid = isIndexProvidedByUserValid(indexProvided);

        if (!isUserProvidedIndexValid) {
            String errorMessage = "Index entered is invalid: " + indexProvided;
            throw new IndexNotFoundException(errorMessage);
        }
        FlashCard cardToChange = cards.get(indexProvided - 1);
        cardToChange.editBack(newBack);
    }

    /**
     * Returns list of FlashCards in Deck.
     *
     * @return ArrayList of FlashCard objects currently in Deck.
     */
    public ArrayList<FlashCard> getCards() {
        return this.cards;
    }

    /**
     * Returns name of the Deck.
     *
     * @return String name of Deck.
     */
    public String getName() {
        return deckName;
    }

    /**
     * Sets the name of the deck.
     *
     * @param deckName the name of the deck.
     */
    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    /**
     * Returns queue storing all high priority flashcards.
     *
     * @return ArrayList of FlashCards of priority level, High.
     */
    public ArrayList<FlashCard> getHighPriorityList() {
        return highPriorityList;
    }

    /**
     * Returns list storing all low priority flashcards.
     *
     * @return ArrayList of flashcards of priority level low.
     */
    public ArrayList<FlashCard> getLowPriorityList() {
        return lowPriorityList;
    }

    /**
     * Returns a list of FlashCard objects created for test.
     * Generates a subset of 10 FlashCard objects for test in ExamRunner.
     * Currently, fixed at 6 FlashCard objects of High priority.
     * And 4 FlashCards of Low priority.
     *
     * @return ArrayList of shuffled FlashCards.
     */
    public ArrayList<FlashCard> getSubsetForTest() {
        ArrayList<FlashCard> testSet = new ArrayList<>();
        int totalDeckSize = cards.size();

        if (totalDeckSize <= 10) {
            return cards;
        }

        //int sizeOfLowPrioritySet = (int) Math.floor(totalDeckSize * 0.4);
        //int sizeOfHighPrioritySet = totalDeckSize - sizeOfLowPrioritySet;

        int sizeOfLowPrioritySet = 4;
        int sizeOfHighPrioritySet = 6;

        for (int i = 0; i < sizeOfHighPrioritySet; i++) {
            FlashCard chosenCard = getRandomCard(highPriorityList);
            testSet.add(chosenCard);
        }

        for (int i = 0; i < sizeOfLowPrioritySet; i++) {
            FlashCard chosenCard = getRandomCard(lowPriorityList);
            testSet.add(chosenCard);
        }

        Collections.shuffle(testSet);

        return testSet;
    }

    /**
     * Returns a random FlashCard object from an ArrayList.
     *
     * @param list ArrayList of FlashCards.
     * @return FlashCard chosen randomly from ArrayList.
     */
    private FlashCard getRandomCard(ArrayList<FlashCard> list) {
        Random rand = new Random(System.currentTimeMillis());
        int chosenCardIndex = rand.nextInt(list.size());
        return list.get(chosenCardIndex);
    }
    //
    ///**
    // *
    // *
    // * @return
    // */
    //private boolean isIndexProvidedByUserValid(int indexProvided) {
    //    return indexProvided <= cards.size() && indexProvided > 0;
    //}
}
