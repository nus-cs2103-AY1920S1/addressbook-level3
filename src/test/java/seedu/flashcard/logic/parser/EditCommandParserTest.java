package seedu.flashcard.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.flashcard.model.DefaultFlashCards.MOUNT_BLANC_CARD;

import org.junit.jupiter.api.Test;

import seedu.flashcard.logic.commands.CommandResult;
import seedu.flashcard.logic.commands.EditCommand;
import seedu.flashcard.model.FlashcardList;

public class EditCommandParserTest {

    public static final String EDIT_COMMAND_TEST_RESULT = "Question: This is a new question, id: 0";

    @Test
    public void edit_command_test() {
        EditCommandParser parser = new EditCommandParser();
        EditCommand command = parser.parse("edit 0 -q This is a new question");
        FlashcardList temp = new FlashcardList();
        temp.addFlashcard(MOUNT_BLANC_CARD);
        CommandResult result = command.execute(temp);
        assertEquals(EDIT_COMMAND_TEST_RESULT, result.getFeedBackToUser());
    }
}
