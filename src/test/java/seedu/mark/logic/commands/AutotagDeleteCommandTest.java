package seedu.mark.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.testutil.Assert.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.model.ModelStub;
import seedu.mark.model.autotag.SelectiveBookmarkTagger;
import seedu.mark.model.predicates.BookmarkPredicate;
import seedu.mark.model.tag.Tag;
import seedu.mark.storage.Storage;
import seedu.mark.storage.StorageStub;

/**
 * Contains unit tests for {@link AutotagDeleteCommand}.
 */
public class AutotagDeleteCommandTest {

    private final Storage storageStub = new StorageStub();
    private final String tagName = "myTag";

    @Test
    public void constructor_nullString_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AutotagDeleteCommand(null));
    }

    @Test
    public void execute_taggerExistsInModel_deleteSuccessful() throws Exception {
        CommandResult commandResult = new AutotagDeleteCommand(tagName)
                .execute(new ModelStubWithAutotags(), storageStub);

        assertEquals(String.format(AutotagDeleteCommand.MESSAGE_AUTOTAG_DELETED, tagName),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_duplicateBookmark_throwsCommandException() {
        AutotagDeleteCommand autotagDeleteCommand = new AutotagDeleteCommand(tagName);
        String expectedMessage = String.format(AutotagDeleteCommand.MESSAGE_AUTOTAG_DOES_NOT_EXIST, tagName);

        assertThrows(CommandException.class, expectedMessage, () ->
                autotagDeleteCommand.execute(new ModelStubNoAutotags(), storageStub));
    }

    @Test
    public void equals() {
        String quizTagName = "Quiz";
        String readLaterTagName = "readLater";
        AutotagDeleteCommand firstCommand = new AutotagDeleteCommand(quizTagName);
        AutotagDeleteCommand secondCommand = new AutotagDeleteCommand(readLaterTagName);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        AutotagDeleteCommand firstCommandCopy = new AutotagDeleteCommand(quizTagName);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different bookmark -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

    /**
     * A Model stub that always returns a tagger when #removeTagger(String) is called.
     */
    private class ModelStubWithAutotags extends ModelStub {
        @Override
        public Optional<SelectiveBookmarkTagger> removeTagger(String taggerName) {
            return Optional.of(new SelectiveBookmarkTagger(new Tag(taggerName), new BookmarkPredicate()));
        }

        @Override
        public void saveMark(String record) {
            // called in AutotagDeleteCommand#execute()
        }
    }

    /**
     * A Model stub that always returns an empty {@code Optional} when attempting to remove a tagger.
     */
    private class ModelStubNoAutotags extends ModelStub {
        @Override
        public Optional<SelectiveBookmarkTagger> removeTagger(String taggerName) {
            return Optional.empty();
        }

        @Override
        public void saveMark(String record) {
            // called in AutotagDeleteCommand#execute()
        }
    }
}
