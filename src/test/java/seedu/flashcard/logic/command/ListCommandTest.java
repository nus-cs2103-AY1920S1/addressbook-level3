package seedu.flashcard.logic.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.flashcard.model.DefaultFlashCards.KANETSU_CARD;
import static seedu.flashcard.model.DefaultFlashCards.MOUNT_BLANC_CARD;

import org.junit.jupiter.api.Test;

import seedu.flashcard.logic.commands.CommandResult;
import seedu.flashcard.logic.commands.ListCommand;
import seedu.flashcard.model.FlashcardList;

public class ListCommandTest {

    public static final String LIST_CARD_TEST_RESULT = "Question: What happened in Mount Blanc Tunnel in 1999?, id: 0\n";
    public static final String LIST_MULTIPLE_CARDS_TEST_RESULT = LIST_CARD_TEST_RESULT +
            "Question: How long is Kan-etsu Tunnel?, id: 1\n";
    @Test
    public void list_card_test() {
        FlashcardList temp = new FlashcardList();
        temp.addFlashcard(MOUNT_BLANC_CARD);
        CommandResult result_1 = new ListCommand().execute(temp);
        assertEquals(LIST_CARD_TEST_RESULT, result_1.getFeedBackToUser());
    }

    @Test
    public void list_multiple_cards_test() {
        FlashcardList temp = new FlashcardList();
        temp.addFlashcard(MOUNT_BLANC_CARD);
        temp.addFlashcard(KANETSU_CARD);
        CommandResult result_2 = new ListCommand().execute(temp);
        assertEquals(LIST_MULTIPLE_CARDS_TEST_RESULT, result_2.getFeedBackToUser());
    }
}
