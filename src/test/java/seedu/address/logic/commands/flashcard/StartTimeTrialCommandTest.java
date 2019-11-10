package seedu.address.logic.commands.flashcard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalFlashcards.getTypicalStudyBuddyPro;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.FlashcardContainsTagPredicate;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.FlashcardBuilder;
import seedu.address.testutil.TagUtil;

public class StartTimeTrialCommandTest {

    private Model model = new ModelManager(getTypicalStudyBuddyPro(), new UserPrefs());

    private String firstTagString = "one";
    private String secondTagString = "two";

    private HashSet<Tag> firstTagSet = TagUtil.generateTagSetFromStrings(firstTagString);
    private HashSet<Tag> secondTagSet = TagUtil.generateTagSetFromStrings(secondTagString);

    private String[] firstKeywordArray = TagUtil.generateKeywordList(firstTagString);
    private String[] secondKeywordArray = TagUtil.generateKeywordList(secondTagString);

    @Test
    public void equals() {
        StartTimeTrialCommand firstCommand = new StartTimeTrialCommand(
            new FlashcardContainsTagPredicate(firstTagSet), firstKeywordArray);

        StartTimeTrialCommand secondCommand = new StartTimeTrialCommand(
            new FlashcardContainsTagPredicate(secondTagSet), secondKeywordArray);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        StartTimeTrialCommand firstCommandCopy =
            new StartTimeTrialCommand(new FlashcardContainsTagPredicate(firstTagSet), firstKeywordArray);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different person -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

    @Test
    public void execute_validTag_success() {
        Flashcard flashcard = new FlashcardBuilder().withTags(firstTagString).build();
        Model expectedModel = new ModelManager(getTypicalStudyBuddyPro(), new UserPrefs());
        expectedModel.addFlashcard(flashcard);
        model.addFlashcard(flashcard);

        StartTimeTrialCommand startTimeTrialCommand = new StartTimeTrialCommand(
            new FlashcardContainsTagPredicate(firstTagSet), firstKeywordArray);

        String expectedMessage = StartTimeTrialCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(startTimeTrialCommand, model, expectedMessage, expectedModel);
    }
}
