package seedu.weme.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.weme.commons.core.GuiSettings;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.MemeBook;
import seedu.weme.model.Model;
import seedu.weme.model.ModelContext;
import seedu.weme.model.ReadOnlyMemeBook;
import seedu.weme.model.ReadOnlyUserPrefs;
import seedu.weme.model.meme.Meme;
import seedu.weme.testutil.MemeBuilder;

public class MemeAddCommandTest {

    @Test
    public void constructor_nullMeme_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MemeAddCommand(null));
    }

    @Test
    public void execute_memeAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingMemeAdded modelStub = new ModelStubAcceptingMemeAdded();
        Meme validMeme = new MemeBuilder().build();

        CommandResult commandResult = new MemeAddCommand(validMeme).execute(modelStub);

        assertEquals(String.format(MemeAddCommand.MESSAGE_SUCCESS, validMeme), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validMeme), modelStub.memesAdded);
    }

    @Test
    public void execute_duplicateMeme_throwsCommandException() {
        Meme validMeme = new MemeBuilder().build();
        MemeAddCommand memeAddCommand = new MemeAddCommand(validMeme);
        ModelStub modelStub = new ModelStubWithMeme(validMeme);

        assertThrows(CommandException.class,
                MemeAddCommand.MESSAGE_DUPLICATE_MEME, () -> memeAddCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Meme meme1 = new MemeBuilder().withFilePath("src/test/data/memes/charmander_meme.jpg").build();
        Meme meme2 = new MemeBuilder().withFilePath("src/test/data/memes/joker_meme.jpg").build();
        MemeAddCommand addMeme1Command = new MemeAddCommand(meme1);
        MemeAddCommand addMeme2Command = new MemeAddCommand(meme2);

        // same object -> returns true
        assertTrue(addMeme1Command.equals(addMeme1Command));

        // same values -> returns true
        MemeAddCommand addAliceCommandCopy = new MemeAddCommand(meme1);
        assertTrue(addMeme1Command.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addMeme1Command.equals(1));

        // null -> returns false
        assertFalse(addMeme1Command.equals(null));

        // different meme -> returns false
        assertFalse(addMeme1Command.equals(addMeme2Command));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getMemeBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMemeBookFilePath(Path memeBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMeme(Meme meme) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMemeBook(ReadOnlyMemeBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyMemeBook getMemeBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasMeme(Meme meme) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteMeme(Meme target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMeme(Meme target, Meme editedMeme) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Meme> getFilteredMemeList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredMemeList(Predicate<Meme> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setContext(ModelContext context) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ModelContext getContext() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single meme.
     */
    private class ModelStubWithMeme extends ModelStub {
        private final Meme meme;

        ModelStubWithMeme(Meme meme) {
            requireNonNull(meme);
            this.meme = meme;
        }

        @Override
        public boolean hasMeme(Meme meme) {
            requireNonNull(meme);
            return this.meme.isSameMeme(meme);
        }
    }

    /**
     * A Model stub that always accept the meme being added.
     */
    private class ModelStubAcceptingMemeAdded extends ModelStub {
        final ArrayList<Meme> memesAdded = new ArrayList<>();

        @Override
        public boolean hasMeme(Meme meme) {
            requireNonNull(meme);
            return memesAdded.stream().anyMatch(meme::isSameMeme);
        }

        @Override
        public void addMeme(Meme meme) {
            requireNonNull(meme);
            memesAdded.add(meme);
        }

        @Override
        public ReadOnlyMemeBook getMemeBook() {
            return new MemeBook();
        }
    }

}
