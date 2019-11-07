package seedu.address.logic.commands.flashcard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static seedu.address.testutil.TypicalFlashcards.getTypicalStudyBuddyPro;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.flashcard.Question;

public class RemindFlashcardCommandTest {

    private Model model = new ModelManager(getTypicalStudyBuddyPro(), new UserPrefs());

    @Test
    public void test() {
        assertEquals(new Question("test"), new Question("test"));
    }
}
