package seedu.mark.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.mark.logic.commands.EditAutotagCommand.MESSAGE_AUTOTAG_EDITED;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.model.Model;
import seedu.mark.model.ModelStub;
import seedu.mark.model.autotag.SelectiveBookmarkTagger;
import seedu.mark.model.predicates.BookmarkPredicate;
import seedu.mark.model.tag.Tag;
import seedu.mark.storage.Storage;
import seedu.mark.storage.StorageStub;

/**
 * Contains integration tests (interaction with the Model) for {@code AutotagCommand}.
 */
public class EditAutotagCommandTest {

    private static final SelectiveBookmarkTagger TAGGER_HELLO = new SelectiveBookmarkTagger(new Tag("Hello"),
            prepareNamePredicate("hello"));
    private static final SelectiveBookmarkTagger TAGGER_FIRST_SECOND = new SelectiveBookmarkTagger(
            new Tag("validTag"), prepareNamePredicate("first second"));

    private static final String AUTOTAG_NAME_VALID = "validTag";
    private static final String AUTOTAG_NAME_INVALID = "nonExistent";

    private final Storage storage = new StorageStub();

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private static BookmarkPredicate prepareNamePredicate(String userInput) {
        return new BookmarkPredicate().withNameKeywords(Arrays.asList(userInput.split("\\s+")));
    }

    @Test
    public void equals() {
        EditAutotagCommand firstCommand = new EditAutotagCommand(AUTOTAG_NAME_VALID, TAGGER_HELLO);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        EditAutotagCommand findFirstCommandCopy = new EditAutotagCommand(AUTOTAG_NAME_VALID, TAGGER_HELLO);
        assertTrue(firstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different tag name -> returns false
        assertFalse(firstCommand.equals(new EditAutotagCommand(AUTOTAG_NAME_INVALID, TAGGER_HELLO)));

        // different tagger -> returns false
        assertFalse(firstCommand.equals(new EditAutotagCommand(AUTOTAG_NAME_VALID, TAGGER_FIRST_SECOND)));
    }

    @Test
    public void execute_noTaggerExists_throwsException() {
        Model model = new ModelStubNoTaggers();
        EditAutotagCommand command = new EditAutotagCommand(AUTOTAG_NAME_INVALID, TAGGER_HELLO);

        assertThrows(CommandException.class, () -> command.execute(model, storage));
    }

    // Note: this also tests that editing the tag name works
    @Test
    public void execute_newTaggerHasEmptyPredicate_successOldPredicateUsed() {
        Model model = new ModelStubAcceptingTaggerAdded();

        SelectiveBookmarkTagger tagger = new SelectiveBookmarkTagger(new Tag("NewTag"),
                new BookmarkPredicate());
        EditAutotagCommand command = new EditAutotagCommand(AUTOTAG_NAME_VALID, tagger);

        Model expectedModel = new ModelStubAcceptingTaggerAdded();
        SelectiveBookmarkTagger taggerRemoved = expectedModel.removeTagger(AUTOTAG_NAME_VALID).get();
        SelectiveBookmarkTagger expectedTagger = new SelectiveBookmarkTagger(
                tagger.getTagToApply(), taggerRemoved.getPredicate());

        String expectedMessage = String.format(MESSAGE_AUTOTAG_EDITED, expectedTagger);
        expectedModel.addTagger(expectedTagger);
        expectedModel.saveMark(expectedMessage);

        assertCommandSuccess(command, model, storage, expectedMessage, expectedModel);
    }

    @Test
    public void execute_predicateChanged_newTaggerAdded() {
        Model model = new ModelStubAcceptingTaggerAdded();

        SelectiveBookmarkTagger tagger = new SelectiveBookmarkTagger(new Tag(AUTOTAG_NAME_VALID),
                new BookmarkPredicate().withFolder(List.of("NewFolder")));
        EditAutotagCommand command = new EditAutotagCommand(AUTOTAG_NAME_VALID, tagger);

        Model expectedModel = new ModelStubAcceptingTaggerAdded();
        expectedModel.removeTagger(AUTOTAG_NAME_VALID).get();

        String expectedMessage = String.format(MESSAGE_AUTOTAG_EDITED, tagger);
        expectedModel.addTagger(tagger);
        expectedModel.saveMark(expectedMessage);

        assertCommandSuccess(command, model, storage, expectedMessage, expectedModel);
    }

    /**
     * A ModelStub that always allows taggers to be added.
     */
    private static class ModelStubAcceptingTaggerAdded extends ModelStub {
        private List<SelectiveBookmarkTagger> taggers = new ArrayList<>();

        ModelStubAcceptingTaggerAdded() {
            taggers.add(TAGGER_FIRST_SECOND);
        }

        @Override
        public boolean hasTagger(SelectiveBookmarkTagger tagger) {
            return taggers.contains(tagger);
        }

        @Override
        public Optional<SelectiveBookmarkTagger> removeTagger(String taggerName) {
            taggers.remove(TAGGER_FIRST_SECOND); // hard-coded
            return Optional.of(TAGGER_FIRST_SECOND);
        }

        @Override
        public void addTagger(SelectiveBookmarkTagger tagger) {
            taggers.add(tagger);
        }

        @Override
        public void applyAllTaggers() {
            // called in EditAutotagCommand#execute()
        }

        @Override
        public void saveMark(String record) {
            // called in EditAutotagCommand#execute()
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof ModelStubAcceptingTaggerAdded
                    && taggers.equals(((ModelStubAcceptingTaggerAdded) other).taggers);
        }
    }

    /**
     * A ModelStub that always returns an empty Optional when #removeTagger(String)
     * is called.
     */
    private static class ModelStubNoTaggers extends ModelStub {
        @Override
        public Optional<SelectiveBookmarkTagger> removeTagger(String taggerName) {
            return Optional.empty();
        }
    }
}
