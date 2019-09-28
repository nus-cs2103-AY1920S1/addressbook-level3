package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.MemeBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.meme.Meme;
import seedu.address.testutil.MemeBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullMeme_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_memeAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingMemeAdded modelStub = new ModelStubAcceptingMemeAdded();
        Meme validMeme = new MemeBuilder().build();

        CommandResult commandResult = new AddCommand(validMeme).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validMeme), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validMeme), modelStub.memesAdded);
    }

    @Test
    public void execute_duplicateMeme_throwsCommandException() {
        Meme validMeme = new MemeBuilder().build();
        AddCommand addCommand = new AddCommand(validMeme);
        ModelStub modelStub = new ModelStubWithMeme(validMeme);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_MEME, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Meme alice = new MemeBuilder().withName("Alice").build();
        Meme bob = new MemeBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different meme -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
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
        public void setMemeBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMeme(Meme meme) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMemeBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getMemeBook() {
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
        public ReadOnlyAddressBook getMemeBook() {
            return new MemeBook();
        }
    }

}
