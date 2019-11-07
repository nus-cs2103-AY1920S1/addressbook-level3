package seedu.address.logic.commands.flashcard;

//import static org.junit.jupiter.api.Assertions.assertEquals;

import static seedu.address.logic.commands.CommandTestUtil.DUE_STATISTICS;
import static seedu.address.logic.commands.CommandTestUtil.OVERDUE_STATISICS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_TWO;
import static seedu.address.testutil.TypicalFlashcards.getTypicalStudyBuddyPro;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.flashcard.Flashcard;
//import seedu.address.model.flashcard.Question;
import seedu.address.testutil.FlashcardBuilder;

public class RemindFlashcardCommandTest {
    private Model model = new ModelManager(getTypicalStudyBuddyPro(), new UserPrefs());

    private Flashcard overdueFlashcard = new FlashcardBuilder().withQuestion(VALID_QUESTION_ONE)
            .withAnswer(VALID_ANSWER_ONE)
            .withTitle(VALID_TITLE_ONE)
            .withStatistics(OVERDUE_STATISICS).build();
    private Flashcard dueFlashcard = new FlashcardBuilder().withQuestion(VALID_QUESTION_TWO)
            .withAnswer(VALID_ANSWER_TWO)
            .withTitle(VALID_TITLE_TWO)
            .withStatistics(DUE_STATISTICS).build();
}
