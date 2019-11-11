package seedu.mark.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.logic.commands.AutotagCommand.MESSAGE_AUTOTAG_ADDED;
import static seedu.mark.logic.commands.AutotagCommand.MESSAGE_AUTOTAG_EXISTS;
import static seedu.mark.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.mark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.mark.testutil.TypicalBookmarks.ALICE;
import static seedu.mark.testutil.TypicalBookmarks.CARL;
import static seedu.mark.testutil.TypicalBookmarks.FIONA;
import static seedu.mark.testutil.TypicalBookmarks.getTypicalMark;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.mark.model.Model;
import seedu.mark.model.ModelManager;
import seedu.mark.model.UserPrefs;
import seedu.mark.model.autotag.SelectiveBookmarkTagger;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.util.BookmarkBuilder;
import seedu.mark.model.predicates.BookmarkPredicate;
import seedu.mark.model.tag.Tag;
import seedu.mark.storage.Storage;
import seedu.mark.storage.StorageStub;

/**
 * Contains integration tests (interaction with the Model) for {@code AutotagCommand}.
 */
public class AutotagCommandTest {

    private final Storage storage = new StorageStub();

    @Test
    public void equals() {
        SelectiveBookmarkTagger firstTagger = new SelectiveBookmarkTagger(new Tag("myTag"),
                prepareNamePredicate("hello"));
        SelectiveBookmarkTagger secondTagger = new SelectiveBookmarkTagger(new Tag("tagTwo"),
                prepareNamePredicate("first second"));

        AutotagCommand firstCommand = new AutotagCommand(firstTagger);
        AutotagCommand secondCommand = new AutotagCommand(secondTagger);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        AutotagCommand findFirstCommandCopy = new AutotagCommand(firstTagger);
        assertTrue(firstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different tagger -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

    @Test
    public void execute_taggerAlreadyExists_throwsException() {
        BookmarkPredicate predicate = prepareNamePredicate("zzz");
        SelectiveBookmarkTagger tagger = new SelectiveBookmarkTagger(new Tag("notTagged"), predicate);
        AutotagCommand command = new AutotagCommand(tagger);

        Model model = new ModelManager(getTypicalMark(), new UserPrefs());
        SelectiveBookmarkTagger existingTagger = new SelectiveBookmarkTagger(new Tag("notTagged"), predicate);
        model.addTagger(existingTagger);

        assertCommandFailure(command, model, storage, String.format(MESSAGE_AUTOTAG_EXISTS, tagger));
    }

    @Test
    public void execute_newTaggerDoesNotMatchBookmarks_successNoBookmarksTagged() {
        Model model = new ModelManager(getTypicalMark(), new UserPrefs());

        BookmarkPredicate predicate = prepareNamePredicate("zzz");
        SelectiveBookmarkTagger tagger = new SelectiveBookmarkTagger(new Tag("notTagged"), predicate);
        AutotagCommand command = new AutotagCommand(tagger);

        Model expectedModel = new ModelManager(getTypicalMark(), new UserPrefs());
        String expectedMessage = String.format(MESSAGE_AUTOTAG_ADDED, tagger);
        expectedModel.saveMark(expectedMessage);
        assertCommandSuccess(command, model, storage, expectedMessage, expectedModel);
    }

    @Test
    public void execute_newTaggerMatchesOneBookmark_successBookmarkTagged() {
        Model model = new ModelManager(getTypicalMark(), new UserPrefs());

        BookmarkPredicate predicate = prepareNamePredicate("Alice Pauline");
        SelectiveBookmarkTagger tagger = new SelectiveBookmarkTagger(new Tag("oneTagged"), predicate);
        AutotagCommand command = new AutotagCommand(tagger);

        Bookmark expectedBookmark1 = new BookmarkBuilder(ALICE).withTags("friends", "oneTagged").build();
        Model expectedModel = new ModelManager(getTypicalMark(), new UserPrefs());
        String expectedMessage = String.format(MESSAGE_AUTOTAG_ADDED, tagger);
        expectedModel.setBookmark(ALICE, expectedBookmark1);
        expectedModel.saveMark(expectedMessage);

        assertCommandSuccess(command, model, storage, expectedMessage, expectedModel);
    }

    @Test
    public void execute_newTaggerMatchesMultipleBookmarks_successBookmarksTagged() {
        Model model = new ModelManager(getTypicalMark(), new UserPrefs());

        BookmarkPredicate predicate = prepareNamePredicate("ku");
        SelectiveBookmarkTagger tagger = new SelectiveBookmarkTagger(new Tag("namesContainKu"), predicate);
        AutotagCommand command = new AutotagCommand(tagger);

        Bookmark expectedBookmark1 = new BookmarkBuilder(CARL).withTags("namesContainKu").build();
        Bookmark expectedBookmark2 = new BookmarkBuilder(FIONA).withTags("namesContainKu").build();
        Model expectedModel = new ModelManager(getTypicalMark(), new UserPrefs());
        String expectedMessage = String.format(MESSAGE_AUTOTAG_ADDED, tagger);
        expectedModel.setBookmark(CARL, expectedBookmark1);
        expectedModel.setBookmark(FIONA, expectedBookmark2);
        expectedModel.saveMark(expectedMessage);

        assertCommandSuccess(command, model, storage, expectedMessage, expectedModel);
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private BookmarkPredicate prepareNamePredicate(String userInput) {
        return new BookmarkPredicate().withNameKeywords(Arrays.asList(userInput.split("\\s+")));
    }
}
