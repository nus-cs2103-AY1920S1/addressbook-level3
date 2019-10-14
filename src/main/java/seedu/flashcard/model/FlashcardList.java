package seedu.flashcard.model;

import static java.util.Objects.requireNonNull;
<<<<<<< HEAD

import javafx.collections.ObservableList;
=======

import java.util.ArrayList;

import seedu.flashcard.model.exceptions.CardNotFoundException;
import seedu.flashcard.model.flashcard.Answer;
>>>>>>> d1c57e0ab067dc917c6d7d3f6953a795e5a47fae
import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.model.flashcard.UniqueFlashcardList;

import java.util.List;

/**
 * Wraps all data at the flashcard list level
 * Duplicates are not allowed
 */
public class FlashcardList implements ReadOnlyFlashcardList {

    private final UniqueFlashcardList flashcards = new UniqueFlashcardList();

<<<<<<< HEAD
    public FlashcardList() {}
=======
    /**
     * Construct an instance of a flashcard list object.
     */
    public FlashcardList() {
        flashcards = new ArrayList<>();
        tagManager = new TagManager();
    }

    /**
     * A variant of the flashcard list constructor.
     * @param list the list to be added in
     */
    public FlashcardList(ArrayList<? extends Flashcard> list) {
        flashcards = new ArrayList<>();
        flashcards.addAll(list);
        tagManager = new TagManager();
    }
>>>>>>> d1c57e0ab067dc917c6d7d3f6953a795e5a47fae

    public FlashcardList(ReadOnlyFlashcardList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
<<<<<<< HEAD
     * Replaces the contents of the flashcards of the flashcard list with {@code flashcards}
     * {@code} flashcards should not contain duplicate flashcards
     */
    public void setFlashcards(List<Flashcard> flashcards) {
        this.flashcards.setFlashcards(flashcards);
=======
     * Fetch the particular model based on its id number
     * @param flashcardId the id number of the model we are looking for
     * @return the model with this id number
     */
    public Flashcard getFlashcard(int flashcardId) {
        for (Flashcard flashcard : flashcards) {
            if (flashcard.getId().getIdentityNumber() == flashcardId) {
                return flashcard;
            }
        }
        return null;
>>>>>>> d1c57e0ab067dc917c6d7d3f6953a795e5a47fae
    }

    /**
     * Resets the existing data of this {@code FlashcardList} with {@code newData}
     */
    public void resetData(ReadOnlyFlashcardList newData) {
        requireNonNull(newData);
        setFlashcards(newData.getFlashcardList());
    }

    /**
     * Returns true if a flashcard with the same word as {@code flashcard} exists in the flashcard list.
     */
    public boolean hasFlashcard(Flashcard flashcard) {
        requireNonNull(flashcard);
        return flashcards.contains(flashcard);
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
     * The flashcard word of {@code editedFlashcard} must not be the same as another existing flashcard in the flashcard list.
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
=======
    /**
     * add an MCQ flash card into the list.
     * @param question the question of the model
     * @param options the options of the model
     * @param answer the answer of this MCQ, simply "A", "B", "C", "D".
     */
    public void addFlashcard (String question, ArrayList<String> options, String answer) {
        flashcards.add(new McqFlashcard(new McqQuestion(question, options), new Answer(answer)));
    }

    /**
     * add a constructed flashcard directly into the list.
     * @param flashcard the flashcard to be added
     */
    public void addFlashcard(Flashcard flashcard) {
        flashcards.add(flashcard);
    }

    /**
     * add a shortAnswer flash card into the list
     * @param question the question of the model
     * @param answer the options of the model
     */
    public void addFlashcard(String question, String answer) {
        flashcards.add(new ShortAnswerFlashcard(new ShortAnswerQuestion(question), new Answer(answer)));
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
>>>>>>> d1c57e0ab067dc917c6d7d3f6953a795e5a47fae
    }
  
    /**
     * Inform whether the flashcardList contains a particular flashcard.
     * @param flashcard the flashcard to be searched
     * @return a boolean variable represents the card's existence
     */
    public boolean contains(Flashcard flashcard) {
        requireNonNull(flashcard);
        return flashcards.contains(flashcard);
    }

    /**
     * Check if two flashcard lists are equivalent to each other.
     * Two lists are the same if and only if their content, i.e. the flashcards they contain, are the same.
     * @param other the other flashcard list to be tested.
     * @return a boolean variable that informs whether the two flashcard lists are the same.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof FlashcardList)) {
            return false;
        }
        FlashcardList otherList = (FlashcardList) other;
        ArrayList<? extends Flashcard> l1 = this.getAllFlashcards();
        ArrayList<? extends Flashcard> l2 = otherList.getAllFlashcards();
        return checkListEqual(l1, l2, 0, 0);
    }

    /**
     * A utility function for checking equivalence of two flashcard lists.
     * @param list1 the first flashcard list
     * @param list2 the second flashcard list
     * @return a boolean variable representing whether the two lists are identical
     */
    public boolean checkListEqual(ArrayList<? extends Flashcard> list1,
                                  ArrayList<? extends Flashcard> list2, int i, int j) {
        if (list1.size() != list2.size()) {
            return false;
        }
        if (i == list1.size() && j == list2.size()) {
            return true;
        }
        if (list1.get(i).equals(list2.get(j))) {
            return checkListEqual(list1, list2, i + 1, j + 1);
        } else {
            return false;
        }
    }
}
