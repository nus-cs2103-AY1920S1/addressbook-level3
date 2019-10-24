package dream.fcard.model;

import static dream.fcard.model.cards.Priority.HIGH_PRIORITY;
import static dream.fcard.model.cards.Priority.LOW_PRIORITY;

import java.util.ArrayList;
import java.util.Random;

import dream.fcard.logic.storage.Schema;
import dream.fcard.model.cards.FlashCard;
import dream.fcard.model.exceptions.IndexNotFoundException;
import dream.fcard.util.json.JsonInterface;
import dream.fcard.util.json.jsontypes.JsonArray;
import dream.fcard.util.json.jsontypes.JsonObject;
import dream.fcard.util.json.jsontypes.JsonValue;

import javafx.scene.Node;

/**
 * Collection of cards.
 */
public class Deck implements JsonInterface {
    private String deckName;
    private ArrayList<FlashCard> cards;
    private ArrayList<FlashCard> highPriorityQueue;
    private ArrayList<FlashCard> lowPriorityQueue;

    /**
     * Constructor to create a Deck with no name and cards.
     */
    public Deck() {
        cards = new ArrayList<>();
        deckName = "untitled";

        highPriorityQueue = new ArrayList<>();
        lowPriorityQueue = new ArrayList<>();
    }

    /**
     * Constructor to create a Deck with name and cards.
     *
     * @param name String name of the deck.
     */
    public Deck(String name) {
        cards = new ArrayList<>();
        deckName = name;

        highPriorityQueue = new ArrayList<>();
        lowPriorityQueue = new ArrayList<>();
    }

    /**
     * Constructor to create a Deck with name and cards.
     *
     * @param name String name of the deck.
     */
    public Deck(ArrayList<FlashCard> initialCards, String name) {
        cards = initialCards;
        deckName = name;

        highPriorityQueue = new ArrayList<>();
        lowPriorityQueue = new ArrayList<>();

        addCardsToQueues(initialCards);
    }

    /**
     * @param list
     */
    private void addCardsToQueues(ArrayList<FlashCard> list) {
        for (int i = 0; i < list.size(); i++) {
            FlashCard card = list.get(i);

            boolean isHighPriorityCard = card.getPriority() == HIGH_PRIORITY;
            boolean isLowPriorityCard = card.getPriority() == LOW_PRIORITY;

            if (isHighPriorityCard) {
                highPriorityQueue.add(card);
            }

            if (isLowPriorityCard) {
                lowPriorityQueue.add(card);
            }
        }
    }

    /**
     * Render all the cards of the deck in a list.
     *
     * @return Node of list
     */
    public Node renderListView() {
        return null;
    }

    /**
     * Render tile icon of this deck to display alongside other decks.
     *
     * @return Node of tile
     */
    public Node renderTileView() {
        return null;
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
     * Removes a specified card using index from the list of cards.
     *
     * @param index Integer index of card to remove from list of Cards.
     */
    public void removeCard(int index) throws IndexNotFoundException {
        if (index >= cards.size() || index < 0) {
            String errorMessage = "Index entered is invalid: " + index;
            throw new IndexNotFoundException(errorMessage);
        }

        cards.remove(index);
    }

    /**
     * Edit front of card.
     *
     * @param newFront String of new text to replace front of card.
     * @param index    Index of card in the Deck.
     * @throws IndexNotFoundException If index >= number of cards in deck or < 0.
     */
    public void editFrontCardInDeck(String newFront, int index) throws IndexNotFoundException {
        if (index >= cards.size() || index < 0) {
            String errorMessage = "Index entered is invalid: " + index;
            throw new IndexNotFoundException(errorMessage);
        }
        FlashCard cardToChange = cards.get(index);
        cardToChange.editFront(newFront);
    }

    /**
     * Edit back of card.
     *
     * @param newBack String of new text to replace back of card.
     * @param index   Index of card in the Deck.
     * @throws IndexNotFoundException If index >= number of cards in deck or < 0.
     */
    public void editBackCardInDeck(String newBack, int index) throws IndexNotFoundException {
        if (index >= cards.size() || index < 0) {
            String errorMessage = "Index entered is invalid: " + index;
            throw new IndexNotFoundException(errorMessage);
        }
        FlashCard cardToChange = cards.get(index);
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
     * @return
     */
    public String getName() {
        return deckName;
    }

    /**
     * @return
     */
    public ArrayList<FlashCard> getHighPriorityQueue() {
        return highPriorityQueue;
    }

    /**
     * @return
     */
    public ArrayList<FlashCard> getLowPriorityQueue() {
        return lowPriorityQueue;
    }

    /**
     * @return
     */
    public ArrayList<FlashCard> getSubsetForTest() {
        ArrayList<FlashCard> testSet = new ArrayList<>();
        int totalDeckSize = cards.size();

        if (totalDeckSize <= 10) {
            return cards;
        }

        int sizeOfLowPrioritySet = (int) Math.floor(totalDeckSize * 0.4);
        int sizeOfHighPrioritySet = totalDeckSize - sizeOfLowPrioritySet;
        int sizeOfTestSet = sizeOfHighPrioritySet + sizeOfLowPrioritySet;

        for (int i = 0; i < sizeOfHighPrioritySet; i++) {
            FlashCard chosenCard = getRandomCard(highPriorityQueue);
            testSet.add(chosenCard);
        }

        for (int i = 0; i < sizeOfLowPrioritySet; i++) {
            FlashCard chosenCard = getRandomCard(lowPriorityQueue);
            testSet.add(chosenCard);
        }

        return testSet;
    }

    /**
     * @param list
     * @return
     */
    private FlashCard getRandomCard(ArrayList<FlashCard> list) {
        Random rand = new Random(System.currentTimeMillis());
        int chosenCardIndex = rand.nextInt(list.size());
        return list.get(chosenCardIndex);
    }
}
