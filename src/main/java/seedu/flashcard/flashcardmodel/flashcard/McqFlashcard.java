package seedu.flashcard.flashcardmodel.flashcard;

import java.util.ArrayList;

/**
 * Represent the flashcards with MCQ questions
 */
public class McqFlashcard extends Flashcard {

    public McqFlashcard(McqQuestion question, Answer answer) {
        super(question, answer);
    }

    /**
     * edit the options in the MCQ
     * TODO: Consider if we can edit a particular option instead of changing them as a whole
     * @param newOptions the updated options for the flashcardmodel
     */
    public void setOptions(ArrayList<String> newOptions) {
        McqQuestion castedTargetQuestion = (McqQuestion) getQuestion();
        castedTargetQuestion.setOptions(newOptions);
    }
}
