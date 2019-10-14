package seedu.flashcard.logic.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.flashcard.model.DefaultFlashCards.MOUNT_BLANC_CARD;

import org.junit.jupiter.api.Test;

import seedu.flashcard.logic.commands.CommandResult;
import seedu.flashcard.logic.commands.EditCommand;
import seedu.flashcard.model.FlashcardList;
import seedu.flashcard.model.flashcard.Answer;
import seedu.flashcard.model.flashcard.Question;
import seedu.flashcard.model.flashcard.ShortAnswerQuestion;

public class EditCommandTest {

    public static final String EDIT_CARD_TEST_RESULT = "Question: This is a new question, id: 0";

    @Test
    public void edit_card_test() {
        FlashcardList temp = new FlashcardList();
        temp.addFlashcard(MOUNT_BLANC_CARD);
        EditCommand.EditFlashCardDescriptor descriptor = new EditCommand.EditFlashCardDescriptor();
        descriptor.setAnswer(new Answer("This is a new answer"));
        descriptor.setQuestion(new ShortAnswerQuestion("This is a new question"));
        CommandResult result = new EditCommand(MOUNT_BLANC_CARD.getId().getIdentityNumber(),
                descriptor).execute(temp);
        assertEquals(EDIT_CARD_TEST_RESULT, result.getFeedBackToUser());
    }
}
