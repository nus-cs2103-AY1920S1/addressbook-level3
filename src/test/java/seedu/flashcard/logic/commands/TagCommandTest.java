package seedu.flashcard.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.flashcard.model.FlashcardList;


public class TagCommandTest {

    private FlashcardList testModel;
    private int id;

    @BeforeEach
    void setup() {
        testModel = new FlashcardList();
        testModel.addFlashcard("TESTQ", "TESTANS");
    }


    @Test
    public void execute_tagFlashcard_success() {
        int id = testModel.getAllFlashcards().get(0).getId().getIdentityNumber();
        CommandResult commandResult = new TagCommand(id, "TEST").execute(testModel);
        assertEquals("Successfully tagged " + id + " with TEST", commandResult.getFeedBackToUser());
        assertTrue(testModel.getFlashcard(id).hasTag("TEST"));
    }

    @Test
    public void execute_tagNonexistingflashcard_fail() {
        int id = testModel.getAllFlashcards().get(0).getId().getIdentityNumber();
        CommandResult commandResult = new TagCommand(id + 23, "TEST").execute(testModel);
        assertEquals("Flashcard doesn't exist", commandResult.getFeedBackToUser());
    }

    @Test
    public void execute_tagTaggedFlashcard_fail() {
        int id = testModel.getAllFlashcards().get(0).getId().getIdentityNumber();
        testModel.tagFlashcard(id, "TEST2");
        CommandResult commandResult = new TagCommand(id, "TEST2").execute(testModel);
        assertEquals("Flashcard already has that tag", commandResult.getFeedBackToUser());
    }



}
