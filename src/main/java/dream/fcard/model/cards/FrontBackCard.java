package dream.fcard.model.cards;

import static dream.fcard.model.cards.Priority.LOW_PRIORITY;

import dream.fcard.logic.stats.Statistics;
import dream.fcard.logic.storage.Schema;
import dream.fcard.model.exceptions.IndexNotFoundException;
import dream.fcard.util.json.jsontypes.JsonObject;
import dream.fcard.util.json.jsontypes.JsonValue;

/**
 * Card that evaluates input to match back of card.
 */
public class FrontBackCard extends FlashCard {
    protected String back;
    protected String front;
    protected Statistics stats;
    /**
     * Constructor to create a FrontBackCard.
     * Takes in 2 String, front text and back text.
     *
     * @param frontString
     * @param backString
     */
    public FrontBackCard(String frontString, String backString) {
        back = backString;
        front = frontString;
        // Default priority is 1
        priority = LOW_PRIORITY;
    }

    /**
     * Constructor to create a FrontBackCard with front and back string, and integer priority levle.
     *
     * @param frontString   String of front text.
     * @param backString    String of back text.
     * @param priorityLevel Integer of priority level.
     */
    public FrontBackCard(String frontString, String backString, int priorityLevel) {
        back = backString;
        front = frontString;

        priority = priorityLevel;
        stats = new Statistics();
    }

    /**
     * Returns JsonValue of flashcard.
     *
     * @return JsonValue of flashcard.
     */
    @Override
    public JsonValue toJson() {
        JsonObject obj = new JsonObject();
        obj.put(Schema.TYPE_FIELD, Schema.FRONT_BACK_TYPE);
        obj.put(Schema.FRONT_FIELD, front);
        obj.put(Schema.BACK_FIELD, back);
        return new JsonValue(obj);
    }

    @Override
    public FlashCard duplicate() {
        return new FrontBackCard(front, back, 0);
    }

    /**
     * @param in String input by user.
     * @return Boolean value, if correct return true, else return false.
     * @throws IndexNotFoundException
     */
    public Boolean evaluate(String in) throws IndexNotFoundException {
        return in.equals(back);
    }

    /**
     * Edits the front text of card.
     *
     * @param newText String of front text to replace the front.
     */
    public void editFront(String newText) {
        front = newText;
    }

    /**
     * Edits the back text of card.
     *
     * @param newText String of new text to replace the back text.
     */
    public void editBack(String newText) {
        back = newText;
    }

    /**
     * Returns front string of card.
     *
     * @return String of front text.
     */
    public String getFront() {
        return front;
    }

    /**
     * Returns back string of card.
     *
     * @return String of back text.
     */
    public String getBack() {
        return back;
    }
}
