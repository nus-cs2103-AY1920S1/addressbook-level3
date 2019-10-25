package dream.fcard.model.cards;

import dream.fcard.model.exceptions.IndexNotFoundException;
import dream.fcard.util.json.JsonInterface;
import dream.fcard.util.json.jsontypes.JsonValue;

/**
 * Interface all flash card types must implement.
 */
public abstract class FlashCard implements JsonInterface, Comparable<FlashCard> {

    /** Text to display in front of FlashCard */
    protected String front;

    /** Text to display in back of FlashCard */
    protected String back;

    /** Number indicating priority level of FlashCard. */
    protected Integer priority;

    /**
     * Evaluate if the input matches the card
     *
     * @param in input
     * @return true if its a valid match
     */
    public abstract Boolean evaluate(String in) throws IndexNotFoundException;

    /**
     * Returns front text of card.
     *
     * @return String of front text of card.
     */
    public abstract String getFront();

    /**
     * Return back text of card.
     *
     * @return String of back text of card.
     */
    public abstract String getBack();

    /**
     * Edits the front text of card.
     *
     * @param newText String of new text to replace in front.
     */
    public abstract void editFront(String newText);

    /**
     * Edits the back text of card.
     *
     * @param newText String of new text to replace in back.
     */
    public abstract void editBack(String newText);

    /**
     * Create JsonValue out of this Flashcard.
     *
     * @return JsonValue of flashcard.
     */
    @Override
    public JsonValue toJson() {
        return null;
    }

    /**
     * Returns an integer value after checking if this card has higher priority than other card.
     * If this card has higher priority, return a positive number.
     * If this card has lower priority, return a negative number.
     *
     * @param otherCard
     * @return
     */
    @Override
    public int compareTo(FlashCard otherCard) {
        return this.priority - otherCard.getPriority();
    }

    /**
     * Returns priority level of flashcard.
     *
     * @return integer value of priorirty level.
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Returns boolean value after checking if FlashCard has choice parameters.
     * If FlashCard has choice parameters (in ArrayList), return true.
     * Else return false.
     *
     * @return Boolean value, true if FlashCard has choices, else return false.
     */
    public abstract boolean hasChoices();
}
