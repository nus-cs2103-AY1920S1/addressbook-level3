package dream.fcard.model.cards;

import dream.fcard.model.exceptions.IndexNotFoundException;
import dream.fcard.util.json.JsonInterface;
import dream.fcard.util.json.jsontypes.JsonValue;

/**
 * Interface all flash card types must implement.
 */
public abstract class FlashCard implements JsonInterface, Comparable<FlashCard> {
    protected String front;
    protected String back;
    protected Integer priority = Priority.LOW_PRIORITY;
    protected int cardResult;


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
     * Checks if this card has higher priority than other card.
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
        return this.priority;
    }

    /**
     * Duplciates the flash card.
     *
     * @return FlashCard object duplicated.
     */
    public abstract FlashCard duplicate();

    /**
     * Sets the score if correct answer.
     *
     * @param isCorrect Boolean indicating correct answer.
     */
    public abstract void updateScore(Boolean isCorrect);

    /**
     * Returns result of the card.
     *
     * @return Integer value of the score.
     */
    public abstract int getCardResult();

    /**
     * Sets the front text of the card.
     *
     * @param front String of new front text.
     */
    public void setFront(String front) {
        this.front = front;
    }

    /**
     * Sets the back text of the card.
     *
     * @param back String of new back text.
     */
    public void setBack(String back) {
        this.back = back;
    }
}
