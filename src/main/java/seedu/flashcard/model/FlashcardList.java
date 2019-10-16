package seedu.flashcard.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.model.flashcard.UniqueFlashcardList;

/**
 * Wraps all data at the flashcard list level
 * Duplicates are not allowed
 */
public class FlashcardList implements ReadOnlyFlashcardList {

    private final UniqueFlashcardList flashcards = new UniqueFlashcardList();

    public FlashcardList() {}

    public FlashcardList(ReadOnlyFlashcardList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Replaces the contents of the flashcards of the flashcard list with {@code flashcards}
     * {@code} flashcards should not contain duplicate flashcards
     */
    public void setFlashcards(List<Flashcard> flashcards) {
        this.flashcards.setFlashcards(flashcards);
    }

    /**
     * Resets the existing data of this {@code FlashcardList} with {@code newData}
     */
    public void resetData(ReadOnlyFlashcardList newData) {
        requireNonNull(newData);
        setFlashcards(newData.getFlashcardList());
    }

    /**
<<<<<<< HEAD
     * Returns true if a flashcard with the same word as {@code flashcard} exists in the flashcard list.
     */
    public boolean hasFlashcard(Flashcard flashcard) {
        requireNonNull(flashcard);
        return flashcards.contains(flashcard);
=======
     * Edit the Flashcard of a particular id
     * @param flashcardId the id number of the model we want to edit
     * @param newFlashcard the updated Flashcard for the target model
     */
    public void setFlashcard(int flashcardId, Flashcard newFlashcard) {
        deleteFlashcard(flashcardId);
        if (newFlashcard.getId().getIdentityNumber() == flashcardId) {
            addFlashcard(newFlashcard);
        }
    }

    /**
     * Edit the answer on a particular model
     * @param flashcardId the id number of the model we want to edit
     * @param newAnswer the updated answer for the target model
     */
    public void setFlashcardAnswer(int flashcardId, String newAnswer) {
        Flashcard editFlashcard = getFlashcard(flashcardId);
        editFlashcard.setAnswer(newAnswer);
    }


    /**
     * Edit the options on a particular MCQ flash card
     * @param flashcardId the id number of the model we want to edit
     * @param newOptions the updated options for the target model
     * @throws RuntimeException if the card with this id is not found or the corresponding card is not an MCQ card.
     */
    public void setFlashcardOptions(int flashcardId, ArrayList<String> newOptions) throws RuntimeException {
        Flashcard editFlashcard = getFlashcard(flashcardId);
        if (!(editFlashcard instanceof McqFlashcard)) {
            throw new RuntimeException();
        }
        McqFlashcard castedEditFlashcard = (McqFlashcard) editFlashcard;
        castedEditFlashcard.setOptions(newOptions);
>>>>>>> b2be09c2de08788b6778e7af7935239ad95c4624
    }

    /**
     * Adds a flashcard to the flashcard list.
     */
    public void addFlashcard(Flashcard flashcard) {
        flashcards.add(flashcard);
    }

    /**
     * Replaces the given flashcard {@code flashcard} in the list with {@code editedFlashcard}.
     * {@code target} must exist in the flashcard list.
     * The flashcard word of {@code editedFlashcard} must not
     * be the same as another existing flashcard in the flashcard list.
     */
    public void setFlashcard(Flashcard target, Flashcard editedFlashcard) {
        requireNonNull(editedFlashcard);
        flashcards.setFlashcard(target, editedFlashcard);
    }

    /**
     * Removes {@code key} from the flashcard list
     * {@code key} must exist in the flashcard list
     */
    public void removeFlashcard(Flashcard key) {
        flashcards.remove(key);
    }

<<<<<<< HEAD
    @Override
    public ObservableList<Flashcard> getFlashcardList() {
        return flashcards.asUnimodifiableObservableList();
    }

    @Override
    public String toString() {
        return flashcards.asUnimodifiableObservableList().size() + "flashcards";
        // TODO: refine later
=======
    /**
     * add a shortAnswer flash card into the list
     * @param question the question of the model
     * @param answer the options of the model
     */
    public void addFlashcard(String question, String answer) {
        flashcards.add(new ShortAnswerFlashcard(new ShortAnswerQuestion(question), new Answer(answer)));
    }

    /**
     * add a constructed flashcard directly into the list.
     * @param flashcard the flashcard to be added
     */
    public void addFlashcard(Flashcard flashcard) {
        flashcards.add(flashcard);
    }

    /**
     * Give the target model a tag. If this tag currently does not exist, create a new one in the TagManager
     * @param flashcardId the model to be tagged
     * @param tagName the tag to be added to the model
     */
    public void tagFlashcard(int flashcardId, String tagName) {
        if (!tagManager.hasTag(tagName)) {
            tagManager.addTag(tagName);
        }
        Flashcard targetCard = getFlashcard(flashcardId);
        Tag targetTag = tagManager.getTag(tagName);
        targetCard.addTag(targetTag);
        targetTag.addFlashcard(targetCard);
    }


    /**
     * List all Flashcards in a String
     * @return An appended string of all flashcards
     */
    public String listFlashcards() {
        final StringBuilder builder = new StringBuilder();
        getAllFlashcards().forEach((card) ->
                builder.append(card)
                        .append("\n"));
        return builder.toString();
    }

    /**
     * Inform whether the flashcardList contains a particular flashcard.
     * @param flashcard the flashcard to be searched
     * @return a boolean variable represents the card's existence
     */
    public boolean contains(Flashcard flashcard) {
        requireNonNull(flashcard);
        return flashcards.contains(flashcard);
>>>>>>> b2be09c2de08788b6778e7af7935239ad95c4624
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FlashcardList
                    && flashcards.equals(((FlashcardList) other).flashcards));
    }

    @Override
    public int hashCode() {
        return flashcards.hashCode();
    }


}
