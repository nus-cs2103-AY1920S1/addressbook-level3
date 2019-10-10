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
import seedu.address.model.spending.Spending;
import seedu.address.testutil.SpendingBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullSpending_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_spendingAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingSpendingAdded modelStub = new ModelStubAcceptingSpendingAdded();
        Spending validSpending = new SpendingBuilder().build();

        CommandResult commandResult = new AddCommand(validSpending).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validSpending), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validSpending), modelStub.spendingsAdded);
    }

    @Test
    public void execute_duplicateSpending_throwsCommandException() {
        Spending validSpending = new SpendingBuilder().build();
        AddCommand addCommand = new AddCommand(validSpending);
        ModelStub modelStub = new ModelStubWithSpending(validSpending);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_SPENDING, ()
            -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Spending alice = new SpendingBuilder().withName("Alice").build();
        Spending bob = new SpendingBuilder().withName("Bob").build();
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

        // different Spending -> returns false
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
        public void addSpending(Spending spending) {
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
        public boolean hasSpending(Spending spending) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteSpending(Spending target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSpending(Spending target, Spending editedSpending) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Spending> getFilteredSpendingList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredSpendingList(Predicate<Spending> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single Spending.
     */
    private class ModelStubWithSpending extends ModelStub {
        private final Spending spending;

        ModelStubWithSpending(Spending spending) {
            requireNonNull(spending);
            this.spending = spending;
        }

        @Override
        public boolean hasSpending(Spending spending) {
            requireNonNull(spending);
            return this.spending.isSameSpending(spending);
        }
    }

    /**
     * A Model stub that always accept the Spending being added.
     */
    private class ModelStubAcceptingSpendingAdded extends ModelStub {
        final ArrayList<Spending> spendingsAdded = new ArrayList<>();

        @Override
        public boolean hasSpending(Spending spending) {
            requireNonNull(spending);
            return spendingsAdded.stream().anyMatch(spending::isSameSpending);
        }

        @Override
        public void addSpending(Spending spending) {
            requireNonNull(spending);
            spendingsAdded.add(spending);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
