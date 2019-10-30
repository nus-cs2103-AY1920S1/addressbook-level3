package seedu.flashcard.model;

import java.util.ArrayList;
import java.util.List;

import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.model.flashcard.exceptions.CardNotFoundException;

public class Quiz {

    List<Flashcard> quizableFlashcards = new ArrayList<>();

    public Quiz() {

    }
    public Quiz(List<Flashcard> quizableFlashcards) {
        this.quizableFlashcards = quizableFlashcards;
    }

    public void setQuizList(List<Flashcard> quizableFlashcards) {
        this.quizableFlashcards = quizableFlashcards;
    }

    public Flashcard quizCard() throws CardNotFoundException {
        if (quizableFlashcards.isEmpty()) {
            throw new CardNotFoundException();
        } else {
            return quizableFlashcards.get(0);
        }
    }

    public void discardFirstCard() throws CardNotFoundException {
        if (quizableFlashcards.isEmpty()) {
            throw new CardNotFoundException();
        } else {
            quizableFlashcards.remove(0);
        }
    }

    public boolean isEmpty() {
        return quizableFlashcards.isEmpty();
    }

}
