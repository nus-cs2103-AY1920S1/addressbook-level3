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
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.answerable.Answerable;
import seedu.address.testutil.AnswerableBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullAnswerable_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_answerableAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingAnswerableAdded modelStub = new ModelStubAcceptingAnswerableAdded();
        Answerable validAnswerable = new AnswerableBuilder().build();

        CommandResult commandResult = new AddCommand(validAnswerable).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validAnswerable), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validAnswerable), modelStub.answerablesAdded);
    }

    @Test
    public void execute_duplicateAnswerable_throwsCommandException() {
        Answerable validAnswerable = new AnswerableBuilder().build();
        AddCommand addCommand = new AddCommand(validAnswerable);
        ModelStub modelStub = new ModelStubWithAnswerable(validAnswerable);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_ANSWERABLE, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Answerable alice = new AnswerableBuilder().withQuestion("Alice").build();
        Answerable bob = new AnswerableBuilder().withQuestion("Bob").build();
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

        // different answerable -> returns false
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
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAnswerable(Answerable answerable) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAnswerable(Answerable answerable) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteAnswerable(Answerable target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAnswerable(Answerable target, Answerable editedAnswerable) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Answerable> getFilteredAnswerableList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredAnswerableList(Predicate<Answerable> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single answerable.
     */
    private class ModelStubWithAnswerable extends ModelStub {
        private final Answerable answerable;

        ModelStubWithAnswerable(Answerable answerable) {
            requireNonNull(answerable);
            this.answerable = answerable;
        }

        @Override
        public boolean hasAnswerable(Answerable answerable) {
            requireNonNull(answerable);
            return this.answerable.isSameAnswerable(answerable);
        }
    }

    /**
     * A Model stub that always accept the answerable being added.
     */
    private class ModelStubAcceptingAnswerableAdded extends ModelStub {
        final ArrayList<Answerable> answerablesAdded = new ArrayList<>();

        @Override
        public boolean hasAnswerable(Answerable answerable) {
            requireNonNull(answerable);
            return answerablesAdded.stream().anyMatch(answerable::isSameAnswerable);
        }

        @Override
        public void addAnswerable(Answerable answerable) {
            requireNonNull(answerable);
            answerablesAdded.add(answerable);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
