package seedu.address.logic.commands.flashcard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.address.logic.commands.flashcard.FilterFlashcardByTagCommand.NO_ITEM_FOUND;
import static seedu.address.testutil.TypicalFlashcards.getTypicalStudyBuddyPro;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.flashcard.FlashcardContainsTagPredicate;
import seedu.address.model.tag.Tag;

public class FilterFlashcardByTagCommandTest {

    private Model model = new ModelManager(getTypicalStudyBuddyPro(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalStudyBuddyPro(), new UserPrefs());

    /* For Jun Ren to look through
    @Test
    public void execute_zeroKeywords_noFlashcardFound() {
        String expectedMessage = String.format(NO_ITEM_FOUND, 0);
        FlashcardContainsTagPredicate predicate = new FlashcardContainsTagPredicate(new HashSet<>());
        FilterFlashcardByTagCommand command = new FilterFlashcardByTagCommand(predicate, new ArrayList<>());
        expectedModel.updateFilteredFlashcardList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }
    */

    @Test
    public void equals() {
        String firstKeyword = "one";
        String secondKeyword = "two";

        Tag firstTag = new Tag(firstKeyword);
        Tag secondTag = new Tag(secondKeyword);

        Set<Tag> firstTagSet = new HashSet<>();
        Set<Tag> secondTagSet = new HashSet<>();

        firstTagSet.add(firstTag);
        secondTagSet.add(secondTag);

        ArrayList<String> firstKeywordList = new ArrayList<>();
        ArrayList<String> secondKeywordList = new ArrayList<>();

        firstKeywordList.add(firstKeyword);
        secondKeywordList.add(secondKeyword);

        FilterFlashcardByTagCommand firstCommand = new FilterFlashcardByTagCommand(
            new FlashcardContainsTagPredicate(firstTagSet), firstKeywordList);
        FilterFlashcardByTagCommand secondCommand = new FilterFlashcardByTagCommand(
            new FlashcardContainsTagPredicate(secondTagSet), secondKeywordList);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        FilterFlashcardByTagCommand firstCommandCopy =
            new FilterFlashcardByTagCommand(new FlashcardContainsTagPredicate(firstTagSet), firstKeywordList);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different person -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }
}
