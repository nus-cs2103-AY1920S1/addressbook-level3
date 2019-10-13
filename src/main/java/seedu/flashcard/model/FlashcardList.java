package seedu.flashcard.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.flashcard.model.exceptions.CardNotFoundException;
import seedu.flashcard.model.exceptions.DuplicateTagException;
import seedu.flashcard.model.flashcard.Answer;
import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.model.flashcard.McqFlashcard;
import seedu.flashcard.model.flashcard.McqQuestion;
import seedu.flashcard.model.flashcard.ShortAnswerFlashcard;
import seedu.flashcard.model.flashcard.ShortAnswerQuestion;


/**
 * The list of all model list, meanwhile, holding the {@Code TagManager}
 */
public class FlashcardList {

    private ArrayList<Flashcard> flashcards;
    private TagManager tagManager;

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

    public ArrayList<Flashcard> getAllFlashcards() {
        return flashcards;
    }

    /**
     * Fetch the particular model based on its id number
     * @param flashcardId the id number of the model we are looking for
     * @return the model with this id number
     * @throws CardNotFoundException if model with this number was not found
     */
    public Flashcard getFlashcard(int flashcardId) throws CardNotFoundException {
        for (Flashcard flashcard : flashcards) {
            if (flashcard.getId().getIdentityNumber() == flashcardId) {
                return flashcard;
            }
        }
        throw new CardNotFoundException();
    }

    /**
     * Edit the question on a particular model
     * @param flashcardId the id number of the model we want to edit
     * @param newQuestion the updated question for the target model
     */
    public void setFlashcard(int flashcardId, String newQuestion) {
        Flashcard editFlashcard = getFlashcard(flashcardId);
        editFlashcard.setQuestion(newQuestion);
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
    }


    /**
     * Look up for a model whose id number, question or answer contains this specific keyword
     * @param search the keyword we want to look up for
     * @return list of the flashcards that matches the keyword
     * @throws CardNotFoundException when no cards of this keyword was found
     */
    public ArrayList<Flashcard> findFlashcard(String search) throws CardNotFoundException {
        ArrayList<Flashcard> matchingFlashcards = new ArrayList<Flashcard>();
        for (Flashcard flashcard : flashcards) {
            if (flashcard.contains(search)) {
                matchingFlashcards.add(flashcard);
            }
        }
        if (matchingFlashcards.isEmpty()) {
            throw new CardNotFoundException();
        }
        return matchingFlashcards;
    }

    /**
     * delete the model based on its id
     * @param flashcardId the id of the model we want to delete
     */
    public void deleteFlashcard (int flashcardId) throws CardNotFoundException {
        Flashcard flashcardDelete = getFlashcard(flashcardId);
        for (Tag tag : flashcardDelete.getTags()) {
            tag.deleteFlashcard(flashcardId);
        }
        flashcards.remove(flashcardDelete);
    }

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
    public void tagFlashcard(int flashcardId, String tagName) throws DuplicateTagException {
        if (!tagManager.hasTag(tagName)) {
            tagManager.addTag(tagName);
        }
        Flashcard targetCard = getFlashcard(flashcardId);
        Tag targetTag = tagManager.getTag(tagName);
        if (targetTag.hasCard(targetCard)) {
            throw new DuplicateTagException();
        }
        targetCard.addTag(targetTag);
        targetTag.addFlashcard(targetCard);
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
        ArrayList<Flashcard> l1 = this.getAllFlashcards();
        ArrayList<Flashcard> l2 = otherList.getAllFlashcards();
        return checkListEqual(l1, l2, 0, 0);
    }

    /**
     * A utility function for checking equivalence of two flashcard lists.
     * @param list1 the first flashcard list
     * @param list2 the second flashcard list
     * @return a boolean variable representing whether the two lists are identical
     */
    public boolean checkListEqual(ArrayList<Flashcard> list1, ArrayList<Flashcard> list2, int i, int j) {
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
