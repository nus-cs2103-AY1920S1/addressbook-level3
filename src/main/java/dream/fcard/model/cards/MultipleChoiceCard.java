package dream.fcard.model.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import dream.fcard.logic.storage.Schema;
import dream.fcard.model.exceptions.DuplicateInChoicesException;
import dream.fcard.model.exceptions.IndexNotFoundException;
import dream.fcard.util.json.exceptions.JsonWrongValueException;
import dream.fcard.util.json.jsontypes.JsonArray;
import dream.fcard.util.json.jsontypes.JsonObject;
import dream.fcard.util.json.jsontypes.JsonValue;

/**
 * FrontBackCard with additional data of multiple choices.
 */
public class MultipleChoiceCard extends FrontBackCard {

    /** List of Choices for MultipleChoiceCard. */
    private ArrayList<String> choices;

    /** List of choices to display. */
    private ArrayList<String> displayChoices;

    /** Integer value of the option user should provide as answer. */
    private int displayChoicesAnswerIndex; // Answer index is 1-based

    /** Integer value of the answer from user in user provided choice. */
    private int answerIndex; // Answer index is 1-based

    /**
     * Construct a multiple choice card.
     *
     * @param frontString String of front text.
     * @param backString  Integer value of user visible original answer index.
     * @param choicesArg  List of String choices from original choices.
     */
    //@@author huiminlim
    public MultipleChoiceCard(String frontString, String backString, ArrayList<String> choicesArg)
            throws DuplicateInChoicesException, IndexNotFoundException {
        super(frontString, backString);

        // Checks if choices contain duplicate
        boolean hasDuplicateInChoice = hasChoiceContainDuplicate(choicesArg);
        if (hasDuplicateInChoice) {
            throw new DuplicateInChoicesException("Duplicates found in choices provided.");
        }

        choices = choicesArg;

        // Answer index is 1-based.
        try {
            answerIndex = Integer.parseInt(back);
        } catch (NumberFormatException f) {
            throw new NumberFormatException("Choice provided is invalid - " + answerIndex);
        }

        boolean isNotValidAnswerIndex = isNotValidChoice(answerIndex);
        if (isNotValidAnswerIndex) {
            throw new IndexNotFoundException("Choice provided is invalid - " + answerIndex);
        }
        //priority = LOW_PRIORITY;
        //cardStats = new CardStats();
    }
    //@author

    /**
     * Constructor of MCQ card, with front, back string, choices and priority level.
     *
     * @param frontString String of front text of card.
     * @param backString  String of back text of card.
     * @param choicesArg  Choices available for user to choose.
     * @throws DuplicateInChoicesException Throw exception when duplicates exist in choices.
     */
    //@@author huiminlim
    public MultipleChoiceCard(String frontString, String backString, ArrayList<String> choicesArg, int priorityLevel)
            throws DuplicateInChoicesException {
        super(frontString, backString);

        // Checks if choices contain duplicate
        boolean hasDuplicateInChoice = hasChoiceContainDuplicate(choicesArg);

        if (hasDuplicateInChoice) {
            throw new DuplicateInChoicesException("Duplicates found in choices provided.");
        }

        choices = choicesArg;

        try {
            answerIndex = Integer.parseInt(back);
        } catch (NumberFormatException f) {
            throw new NumberFormatException("Choice provided is invalid - " + answerIndex);
        }

        //priority = priorityLevel;
    }
    //@author

    /**
     * Returns JsonValue of flashcard.
     *
     * @return JsonValue of flashcard.
     */
    @Override
    public JsonValue toJson() {
        try {
            JsonObject obj = super.toJson().getObject();

            JsonArray choicesJson = new JsonArray();
            for (String option : choices) {
                choicesJson.add(option);
            }
            obj.put(Schema.TYPE_FIELD, Schema.MULTIPLE_CHOICE_TYPE);
            obj.put(Schema.CHOICES_FIELD, choicesJson);
            return new JsonValue(obj);
        } catch (JsonWrongValueException e) {
            System.out.println("Inherited FrontBackCard unexpected json object");
        }
        return super.toJson();
    }

    /**
     * Render the front of card to GUI node.
     * .
     */
    //@@author huiminlim
    //@Override
    public void renderFront() {
        // Shuffle choices first
        shuffleChoices();

        //super.renderFront();
    }
    //@author

    /**
     * Returns a list of shuffled choices.
     */
    //@@author Timothy Leong
    public ArrayList<String> getShuffledChoices() {
        shuffleChoices();
        return this.displayChoices;
    }

    /**
     * Evaluates if user input answer is correct.
     *
     * @param in String input provided by user.
     * @return Boolean, if correct return true, else return false.
     * @throws IndexNotFoundException Throw exception when input provided by user is not a valid choice.
     */
    //@@author huiminlim
    @Override

    public Boolean evaluate(String in) throws IndexNotFoundException {

        int userAnswer = -1;

        // User answer is 1-based indexing
        try {
            userAnswer = Integer.parseInt(in);

        } catch (NumberFormatException n) {
            throw new NumberFormatException("Choice provided is invalid - " + in);
        }

        /*
        // Assume options must be a non-negative integer
        if (isNotValidChoice(userAnswer)) {
            throw new IndexNotFoundException("Choice provided is invalid - " + in);
        }

         */

        return userAnswer == displayChoicesAnswerIndex;
    }
    //@author

    /**
     * Return the String of front of MultipleChoiceCard.
     *
     * @return String of text in front of MultipleChoiceCard.
     */
    //@author huiminlim
    public String getFront() {
        return front;
    }
    //@author

    /**
     * Return the String of back of MultipleChoiceCard.
     *
     * @return String of text in back of MultipleChoiceCard.
     */
    //@author huiminlim
    public String getBack() {
        return back;
    }

    /**
     * Return the String text of choice given the index of the choice.
     *
     * @param indexProvided Integer index of targeted choice to obtain.
     * @return String of text of targeted option.
     * @throws IndexNotFoundException If index >= number of choices or < 0.
     */
    //@author huiminlim
    public String getSpecificChoice(int indexProvided) throws IndexNotFoundException {
        if (isNotValidChoice(indexProvided)) {
            throw new IndexNotFoundException("Choice index provided is invalid - " + indexProvided);
        }

        // Use chocies indexing - 0-based indexing
        // choice index is the index that works with the Arraylist
        int choiceIndex = indexProvided - 1;
        return choices.get(choiceIndex);
    }
    //@author

    /**
     * Returns the list of shuffled choices.
     *
     * @return ArrayList of string choices, shuffled.
     */
    public ArrayList<String> getListOfChoices() {
        shuffleChoices();
        return displayChoices;
    }

    /**
     * Shuffles the choices of choices and updates the index of correct answer.
     *
     * @return
     */
    //@author huiminlim
    private void shuffleChoices() {
        // Obtain String of correct answer before sorting
        String correctAnswer = choices.get(answerIndex - 1);

        displayChoices = generateCopyOfChoices();

        Collections.shuffle(displayChoices);

        // Find the index of the correct answer after sorting
        for (int i = 0; i < displayChoices.size(); i++) {
            String currentChoice = displayChoices.get(i);

            boolean isCurrentChoiceEqualAnswer = correctAnswer.equals(currentChoice);

            if (isCurrentChoiceEqualAnswer) {
                displayChoicesAnswerIndex = i + 1;
                break;
            }
        }
    }
    //@author

    /**
     * Returns a new ArrayList of choices as a copy.
     *
     * @return ArrayList of choices as string.
     */
    //@@author huiminlim
    public ArrayList<String> generateCopyOfChoices() {
        ArrayList<String> newList = new ArrayList<>();
        for (int i = 0; i < choices.size(); i++) {
            String newStringObject = choices.get(i);
            newList.add(newStringObject);
        }
        return newList;
    }
    //@author

    /**
     * Sets the front text of the MultipleChoiceCard.
     *
     * @param newText String of text to replace the front of MultipleChoiceCard.
     */
    //@@author huiminlim
    public void editFront(String newText) {
        front = newText;
    }
    //@author

    /**
     * Sets the back text of the MultipleChoiceCard.
     *
     * @param newText String of text to replace the back of MultipleChoiceCard.
     */
    //@author huiminlim
    public void editBack(String newText) {
        back = newText;
    }
    //@author

    /**
     * Sets one of string in choices, given new text and index.
     *
     * @param indexProvided Integer index of targeted choice to edit.
     * @param newChoice     String text of new choice option to replace current choice.
     * @throws IndexNotFoundException If index >= number of choices or < 0.
     */
    //@author huiminlim
    public void editSpecificChoice(int indexProvided, String newChoice) throws IndexNotFoundException {
        if (isNotValidChoice(indexProvided)) {
            throw new IndexNotFoundException("Choice index provided is invalid - " + indexProvided);
        }

        // choice index is the index that works with the Arraylist
        int choiceIndex = indexProvided - 1;
        choices.add(choiceIndex, newChoice);
        choices.remove(choiceIndex + 1);
    }
    //@author

    /**
     * Returns boolean value true.
     * Since choices exist in this class.
     *
     * @return Boolean value true.
     */
    //@Override
    //public boolean hasChoices() {
    //    return true;
    //}
    //@author

    /**
     * Returns true if duplicates exist, false if no duplicates.
     * Checks if ArrayList of choices contain duplicates.
     *
     * @param choiceSet ArrayList of possible String of choices to check.
     * @return Boolean true if ArrayList of choices have duplicates, false if no duplicates.
     */
    //@@author huiminlim
    private boolean hasChoiceContainDuplicate(ArrayList<String> choiceSet) {
        HashMap<String, Integer> choiceMap = new HashMap<>();

        for (int i = 0; i < choiceSet.size(); i++) {
            String choiceText = choiceSet.get(i).trim();

            boolean hasChoice = choiceMap.containsKey(choiceText);

            if (hasChoice) {
                return true;
            } else {
                choiceMap.put(choiceText, 1);
            }
        }
        return false;
    }
    //@author

    /**
     * Returns true if not in valid range, false if in valid range.
     * Checks if the given choice index provided by the user is correct.
     * Note: the user provided index is 1-based indexing.
     * Valid indexes include 1, 2, 3, ..., choices.
     *
     * @param choiceIndex Integer Index provided by user.
     * @return Boolean true if not in valid range, false if in valid range.
     */
    //@author huiminlim
    private boolean isNotValidChoice(int choiceIndex) {
        return choiceIndex < 1 || choiceIndex > choices.size();
    }
    //@author

    /**
     * Get the display choices.
     * @return the array list of choices that have already been shuffled.
     */
    public ArrayList<String> getDisplayChoices() {
        return displayChoices;
    }

    /**
     * Get the correct answer in the shuffled array list.
     * @return the correct answer (1-based).
     */
    public int getDisplayChoicesAnswerIndex() {
        return displayChoicesAnswerIndex;
    }

    /**
     * Get the correct answer in the original array list.
     * @return the correct answer (1-based).
     */
    public int getCorrectAnswerIndex() {
        return answerIndex;
    }
}
