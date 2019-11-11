package seedu.flashcard.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.model.flashcard.exceptions.CardNotFoundException;

/**
 * Quiz object containing all quizable flashcards when quiz is initiated.
 */
public class Quiz {

    private List<Flashcard> quizableFlashcards = new ArrayList<>();
    private IntegerProperty duration = new SimpleIntegerProperty();
    private IntegerProperty totalCards = new SimpleIntegerProperty();
    private IntegerProperty remainingCards = new SimpleIntegerProperty();

    public Quiz() {

    }

    /**
     * Sets a list of flashcards to be quizzed.
     * @param quizableFlashcards List of flashcards to be quizzed.
     */
    public void setQuizList(List<Flashcard> quizableFlashcards) {
        this.quizableFlashcards = quizableFlashcards;
        totalCards.set(quizableFlashcards.size());
        remainingCards.set(quizableFlashcards.size());
    }

    /**
     * Gets the list of quizable flashcards.
     * @return List of quizable flashcards.
     */
    public List<Flashcard> getQuizableFlashcards() {
        return quizableFlashcards;
    }

    /**
     * Returns the first card in the list.
     * @return Flashcard to be quizzed.
     * @throws CardNotFoundException when there are no cards in the list.
     */
    public Flashcard quizCard() throws CardNotFoundException {
        if (quizableFlashcards.isEmpty()) {
            throw new CardNotFoundException();
        } else {
            remainingCards.set(quizableFlashcards.size());
            return quizableFlashcards.get(0);
        }
    }

    /**
     * Discards the first card in the list.
     * @throws CardNotFoundException when there are no cards to be discarded.
     */
    public void discardFirstCard() throws CardNotFoundException {
        if (this.isEmpty()) {
            throw new CardNotFoundException();
        } else {
            quizableFlashcards.remove(0);
        }
    }

    /**
     * Checks if the quizable list is empty.
     * @return true if there are no cards in the quizable list.
     */
    public boolean isEmpty() {
        return quizableFlashcards.isEmpty();
    }

    public IntegerProperty getDurationProperty() {
        return duration;
    }

    public IntegerProperty totalCardsProperty() {
        return totalCards;
    }

    public IntegerProperty remainingCardsProperty() {
        return remainingCards;
    }

    public void setDuration(Integer duration) {
        this.duration.setValue(duration);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Quiz)) {
            return false;
        }
        Quiz obj = (Quiz) other;
        return quizableFlashcards.equals(obj.quizableFlashcards);
    }
}
