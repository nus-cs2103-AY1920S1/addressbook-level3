package seedu.flashcard.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.flashcard.model.FlashcardList;


public class ProgressCommandTest {
    private FlashcardList testModelProgress;

    public void setup() {
        testModelProgress = new FlashcardList();
        testModelProgress.addFlashcard("TEST", "ANS");
        testModelProgress.getAllFlashcards().get(0).isAnswerCorrect(true);
        testModelProgress.getAllFlashcards().get(0).isAnswerCorrect(false);
        testModelProgress.addFlashcard("TEST2", "ANS2");
        testModelProgress.getAllFlashcards().get(1).isAnswerCorrect(true);
        testModelProgress.getAllFlashcards().get(1).isAnswerCorrect(true);
        int id = testModelProgress.getAllFlashcards().get(1).getId().getIdentityNumber();
        testModelProgress.tagFlashcard(id, "TESTTAG");
    }

    @Test
    public void execute_showOverallStatistics_sucess() {
        setup();
        CommandResult commandResult = new ProgressCommand("").execute(testModelProgress);
        String expected = "Here are the overall statistics\n"
                + "Total number of times answered correctly:3\n"
                + "Total number of times answered wrongly:1\n"
                + "Total number of attempts:4";
        assertEquals(expected, commandResult.getFeedBackToUser());
    }

    @Test
    public void execute_showTagStatistics_sucess() {
        setup();
        CommandResult commandResult = new ProgressCommand("TESTTAG").execute(testModelProgress);
        String expected = "Here are the statistics for TESTTAG\n"
                + "Total number of times answered correctly:2\n"
                + "Total number of times answered wrongly:0\n"
                + "Total number of attempts:2";
        assertEquals(expected, commandResult.getFeedBackToUser());
    }

    @Test
    public void execute_nonExistantTag_fail() {
        setup();
        CommandResult commandResult = new ProgressCommand("random").execute(testModelProgress);
        String expected = "tag may not exist or wrong name given";
        assertEquals(expected, commandResult.getFeedBackToUser());
    }

}

