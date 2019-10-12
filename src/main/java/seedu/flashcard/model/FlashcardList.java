package seedu.flashcard.model;

import java.util.ArrayList;

import seedu.flashcard.model.exceptions.CardNotFoundException;
import seedu.flashcard.model.exceptions.DuplicateTagException;
import seedu.flashcard.model.flashcard.*;

/**
 * The list of all model list, meanwhile, holding the {@Code TagManager}
 */
public class FlashcardList {

    private ArrayList<Flashcard> flashcards;
    private TagManager tagManager;

    public FlashcardList() {
        flashcards = new ArrayList<Flashcard>();
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
     * add an MCQ flash card into the list
     * @param question the question of the model
     * @param options the options of the model
     * @param answer the answer of this MCQ, simply "A", "B", "C", "D".
     */
    public void addFlashcard (String question, ArrayList<String> options, String answer) {
        flashcards.add(new McqFlashcard(new McqQuestion(question, options), new Answer(answer)));
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
}
