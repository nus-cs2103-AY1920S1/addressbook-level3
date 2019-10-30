package dream.fcard.model.cards;

import static dream.fcard.model.cards.Priority.LOW_PRIORITY;

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
    private ArrayList<String> choices;
    private ArrayList<String> displayChoices;

    // Answer index is 1-based
    private int displayChoicesAnswerIndex;

    // Answer index is 1-based
    private int answerIndex;

    // Answer index is 1-based
    private int userAttempt = -1;

    /**
     * Construct a multiple choice card.
     *
     * @param frontString front string
     * @param backString  original sorted answer index
     * @param choicesArg  original sorted choices
     */
    //@@author huiminlim
    public MultipleChoiceCard(String frontString, String backString, ArrayList<String> choicesArg) {
        super(frontString, backString);

        // Checks if choices contain duplicate TODO: handle this problem in the GUI
        boolean hasDuplicateInChoice = hasChoiceContainDuplicate(choicesArg);

        choices = choicesArg;

        // Answer index is 1-based.
        try {
            answerIndex = Integer.parseInt(back);
        } catch (NumberFormatException f) {
            throw new NumberFormatException("Choice provided is invalid - " + answerIndex);
        }

        priority = LOW_PRIORITY;
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

        priority = priorityLevel;
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

        // Assume options must be a non-negative integer
        if (isNotValidChoice(userAnswer)) {
            throw new IndexNotFoundException("Choice provided is invalid - " + in);
        }

        return userAnswer == displayChoicesAnswerIndex;
    }
    //@author

    /**
     * Checks if ArrayList of choices contain duplicates.
     * Returns true if duplicates exist, false if no duplicates.
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
     * Edits the front text of the MultipleChoiceCard.
     *
     * @param newText String of text to replace the front of MultipleChoiceCard.
     */
    //@@author huiminlim
    public void editFront(String newText) {
        front = newText;
    }
    //@author

    /**
     * Edits the back text of the MultipleChoiceCard.
     *
     * @param newText String of text to replace the back of MultipleChoiceCard.
     */
    //@author huiminlim
    public void editBack(String newText) {
        back = newText;
    }
    //@author

    /**
     * Edits one of string in choices, given new text and index.
     *
     * @param indexProvided Integer index of targeted choice to edit.
     * @param newChoice     String text of new choice option to replace current choice.
     * @throws IndexNotFoundException If index >= number of choices or < 0.
     */
    //@author huiminlim
    public void editChoice(int indexProvided, String newChoice) throws IndexNotFoundException {
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
     * Checks if the given choice index provided by the user is correct.
     * Note: the user provided index is 1-based indexing.
     * Valid indexes include 1, 2, 3, ..., choices.
     *
     * @param choiceIndex
     * @return boolean true if not in valid range, false if in valid range.
     */
    //@author huiminlim
    private boolean isNotValidChoice(int choiceIndex) {
        return !(choiceIndex >= 1 && choiceIndex <= choices.size());
    }
    //@author

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
     * @return
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
     * Get the String text of choice given the index of the choice.
     *
     * @param indexProvided Integer index of targeted choice to obtain.
     * @return String of text of targeted option.
     * @throws IndexNotFoundException If index >= number of choices or < 0.
     */
    //@author huiminlim
    public String getChoice(int indexProvided) throws IndexNotFoundException {
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
     * Get the String of front of MultipleChoiceCard.
     *
     * @return String of text in front of MultipleChoiceCard.
     */
    //@author huiminlim
    public String getFront() {
        return front;
    }
    //@author

    /**
     * Get the String of back of MultipleChoiceCard.
     *
     * @return String of text in back of MultipleChoiceCard.
     */
    //@author huiminlim
    public String getBack() {
        return back;
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

    /**
     * Retrieve the user's attempt within the same test.
     * @return
     */
    public int getUserAttempt() {
        return userAttempt;
    }

    /**
     * Set the user's attempt in the test.
     * @param userAttempt
     */
    public void setUserAttempt(int userAttempt) {
        this.userAttempt = userAttempt;
    }

    @Override
    public FlashCard duplicate() {
        return new MultipleChoiceCard(front, back, choices);
    }
}
