package dream.fcard.model;

import static dream.fcard.model.cards.Priority.HIGH_PRIORITY;
import static dream.fcard.model.cards.Priority.LOW_PRIORITY;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import dream.fcard.logic.stats.SessionList;
import dream.fcard.logic.stats.StatsHolder;
import dream.fcard.logic.storage.Schema;
import dream.fcard.model.cards.FlashCard;
import dream.fcard.model.cards.Priority;
import dream.fcard.model.exceptions.IndexNotFoundException;
import dream.fcard.util.DeepCopy;
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

    /** List of FlashCards with High priority levels. */
    private ArrayList<FlashCard> highPriorityList;

    /** List of FlashCards with Low priority levels. */
    private ArrayList<FlashCard> lowPriorityList;

    //@@author huiminlim
    /**
     * Constructor to create a Deck with no name and cards.
     */
    public Deck() {
        cards = new ArrayList<>();
        deckName = "untitled";
        highPriorityQueue = new ArrayList<>();
        lowPriorityQueue = new ArrayList<>();
        highPriorityList = new ArrayList<>();
        lowPriorityList = new ArrayList<>();
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
     * Adds cards to the priority queue.
     *
     * @param list ArrayList of cards to add to queue.
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
    //@author

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

    //@@author huiminlim
    /**
     * Adds new card to the ArrayList of card objects.
     *
     * @param newCard FlashCard object to add to Deck.
     */
    public void addNewCard(FlashCard newCard) {
        cards.add(newCard);
        int priority = newCard.getPriority();
        if (priority == Priority.LOW_PRIORITY) {
            lowPriorityQueue.add(newCard);
        } else {
            highPriorityQueue.add(newCard);
        }
    }

    /**
     * Removes a specified card using index from the list of cards.
     *
     * @param indexProvided Integer index of card to remove from list of Cards.
     */
    public void removeCard(int indexProvided) throws IndexNotFoundException {
        boolean isUserProvidedIndexValid = isIndexProvidedByUserValid(indexProvided);

        if (!isUserProvidedIndexValid) {
            String errorMessage = "Index entered is invalid: " + indexProvided;
            throw new IndexNotFoundException(errorMessage);
        }

        FlashCard card = cards.remove(indexProvided - 1);
        int priorityLevel = card.getPriority();
        if (priorityLevel == Priority.LOW_PRIORITY) {
            removeCardFromQueue(lowPriorityQueue, card);
        } else {
            removeCardFromQueue(highPriorityQueue, card);
        }
    }

    /**
     *
     *
     * @param queue
     */
    private void removeCardFromQueue(ArrayList<FlashCard> queue, FlashCard toRemove) {
        for (int i = 0; i < queue.size(); i++) {
            FlashCard card = queue.get(i);

            String front = card.getFront();
            String back = card.getBack();

            // todo: a better solution
            boolean isMatchFront = front.equals(toRemove.getFront());
            boolean isMatchBack = back.equals(toRemove.getBack());
            if (isMatchBack && isMatchFront) {
                queue.remove(i);
                break;
            }
        }
    }
    //@author

    //@@author huiminlim
    /**
     * Edit front of card.
     *
     * @param newFront      String of new text to replace front of card.
     * @param indexProvided Index of card in the Deck.
     * @throws IndexNotFoundException If index >= number of cards in deck or < 0.
     */
    public void editFrontCardInDeck(String newFront, int indexProvided) throws IndexNotFoundException {
        boolean isUserProvidedIndexValid = isIndexProvidedByUserValid(indexProvided);

        if (!isUserProvidedIndexValid) {
            String errorMessage = "Index entered is invalid: " + indexProvided;
            throw new IndexNotFoundException(errorMessage);
        }
        FlashCard cardToChange = cards.get(indexProvided);
        cardToChange.editFront(newFront);
    }
    //@author

    @Override
    public String toString() {
        return getDeckName();
    }

    //@@author
    /**
     * Edit back of card.
     *
     * @param newBack       String of new text to replace back of card.
     * @param indexProvided Index of card in the Deck.
     * @throws IndexNotFoundException If index >= number of cards in deck or < 0.
     */
    public void editBackCardInDeck(String newBack, int indexProvided) throws IndexNotFoundException {
        boolean isUserProvidedIndexValid = isIndexProvidedByUserValid(indexProvided);

        if (!isUserProvidedIndexValid) {
            String errorMessage = "Index entered is invalid: " + indexProvided;
            throw new IndexNotFoundException(errorMessage);
        }
        FlashCard cardToChange = cards.get(indexProvided);
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
     * Returns name of the deck.
     *
     * @return String name of deck.
     */
    public String getDeckName() {
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
     * @return ArrayList of flashcards of priority level, high.
     */
    public ArrayList<FlashCard> getHighPriorityQueue() {
        return highPriorityQueue;
    }

    /**
     * Returns queue storing all low priority flashcards.
     *
     * @return ArrayList of flashcards of priority level low.
     */
    public ArrayList<FlashCard> getLowPriorityQueue() {
        return lowPriorityQueue;
    }

    /**
     * Generates a subset of 10 flashcards for test in ExamRunner.
     * Currently, fixed at 6 flashcards of high priority.
     * And 4 flashcards of low priority.
     *
     * @return ArrayList of shuffled flashcards.
     */
    public ArrayList<FlashCard> getSubsetForTest() {
        ArrayList<FlashCard> testSet = new ArrayList<>();
        int totalDeckSize = cards.size();

        if (totalDeckSize <= 10) {
            return this.duplicateMyself().getCards();
        }

        int sizeOfHighPrioritySet = 6;
        if (highPriorityQueue.size() < 6) {
            sizeOfHighPrioritySet = highPriorityQueue.size();
        }
        int sizeOfLowPrioritySet = 10 - sizeOfHighPrioritySet;

        for (int i = 0; i < sizeOfHighPrioritySet; i++) {
            FlashCard chosenCard = getRandomCard(highPriorityQueue);
            testSet.add(chosenCard);
        }

        for (int i = 0; i < sizeOfLowPrioritySet; i++) {
            FlashCard chosenCard = getRandomCard(lowPriorityQueue);
            testSet.add(chosenCard);
        }

        Collections.shuffle(testSet);

        return testSet;
    }

    /**
     * Returns a random flashcard from an Arraylist.
     *
     * @param list ArrayList of Flashcards.
     * @return Flashcard chosen randomly from ArrayList.
     */
    private FlashCard getRandomCard(ArrayList<FlashCard> list) {
        Random rand = new Random(System.currentTimeMillis());

        System.out.println(list.size());
        int chosenCardIndex = rand.nextInt(list.size());
        return list.get(chosenCardIndex);
    }


    /**
     * Checks if a user provided index is valid.
     * Uses provide index that is 1-based, i.e. 1, 2, 3, etc.
     * If index provided is within 1, 2, 3, ..., last card, then it is valid and return true.
     * Else return false.
     *
     * @param indexProvided Boolean indicating if index provided by user is valid.
     * @return True if valid, false if invalid.
     */
    private boolean isIndexProvidedByUserValid(int indexProvided) {
        return indexProvided <= cards.size() && indexProvided > 0;
    }
    //@author

    public Deck duplicateMyself() {
        return new Deck(DeepCopy.duplicateCards(cards), deckName);
    }

    //@@author nattanyz
    /** Get the SessionList pertaining to this deck. */
    public SessionList getTestSessionList() {
        return StatsHolder.getDeckStats().getSessionListForDeck(getDeckName());
    }

    /** Get number of cards in this deck.*/
    public Integer getNumberOfCards() {
        return this.cards.size();
    }

    /** Get the number of sessions for this deck. */
    public Integer getNumberOfSessions() {
        return this.getTestSessionList().getNumberOfSessions();
    }

    /** Gets the average score of the test sessions for this deck. */
    public String getAverageScore() {
        return this.getTestSessionList().getAverageScore();
    }
    //@@author

    /**
     * Returns a duplicate of the deck.
     *
     * @return Deck object of the duplicated deck.
     */
    public Deck duplicate() {
        ArrayList<FlashCard> duplicated = new ArrayList<>();

        for (FlashCard f : cards) {
            duplicated.add(f.duplicate());
        }
        return new Deck(duplicated, deckName);
    }
}
