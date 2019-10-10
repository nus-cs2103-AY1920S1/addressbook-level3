package dream.fcard.model.cards;

import java.util.ArrayList;

import dream.fcard.model.exceptions.ChoiceNotFoundException;

import dream.fcard.util.json.jsontypes.JsonValue;

import javafx.scene.Node;

/**
 * FrontBackCard with additional data of multiple choices.
 */
public class MultipleChoiceCard extends FrontBackCard {

    private ArrayList<String> choices;

    public MultipleChoiceCard(String frontString, String backString, ArrayList<String> choicesArg) {
        super(frontString, backString);
        choices = choicesArg;
    }

    // Assume that String in takes in options 1, 2, 3, ... index of choices
    // Can update subsequently
    @Override
    public Boolean evaluate(String in) {
        return in.equals(back);
    }

    public void editFront(String newText) {
        front = newText;
    }

    public void editBack(String newText) {
        back = newText;
    }

    /**
     * Edits one of string in choices, given new text and index.
     */
    public void editChoice(String newChoice, int index) throws ChoiceNotFoundException {
        // Handle the exception if index does not exist
        // Can handle this in command class overall, but here just in case not handled
        if (index < 0 || index > choices.size()) {
            //throw new ArrayIndexOutOfBoundsException();
            throw new ChoiceNotFoundException(new Exception());
        }

        choices.add(index, newChoice);
        choices.remove(index + 1);
    }

    public String getChoice(int index) throws ChoiceNotFoundException {
        if (index < 0 || index > choices.size()) {
            //throw new ArrayIndexOutOfBoundsException();
            throw new ChoiceNotFoundException(new Exception());
        }
        return choices.get(index);
    }

    @Override
    public JsonValue toJson() {
        return super.toJson();
    }

    @Override
    public Node renderFront() {
        return super.renderFront();
    }
}
