package seedu.jarvis.logic.commands.cca;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.jarvis.commons.core.GuiSettings;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.logic.commands.exceptions.CommandNotInvertibleException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.address.ReadOnlyAddressBook;
import seedu.jarvis.model.address.person.Person;
import seedu.jarvis.model.cca.Cca;
import seedu.jarvis.model.cca.CcaList;
import seedu.jarvis.model.cca.CcaTracker;
import seedu.jarvis.model.financetracker.FinanceTracker;
import seedu.jarvis.model.financetracker.Purchase;
import seedu.jarvis.model.financetracker.installment.Installment;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.model.planner.TaskList;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.model.userprefs.ReadOnlyUserPrefs;
import seedu.jarvis.testutil.cca.CcaBuilder;

/**
 * AddCcaCommandTest basically checks just 3 scenarios - adding null, adding a new {@code Cca} and adding
 * a duplicate {@code Cca}.
 * Since this test is not an integration test, we can just use a model stub.
 */
public class AddCcaCommandTest {

    @Test
    public void constructor_nullCca_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCcaCommand(null));
    }

    @Test
    public void execute_ccaAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingCcaAdded modelStub = new ModelStubAcceptingCcaAdded();
        Cca validCca = new CcaBuilder().build();

        CommandResult commandResult = new AddCcaCommand(validCca).execute(modelStub);

        assertEquals(String.format(AddCcaCommand.MESSAGE_SUCCESS, validCca), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_duplicateCca_throwsCommandException() {
        Cca validCca = new CcaBuilder().build();
        AddCcaCommand addCcaCommand = new AddCcaCommand(validCca);
        ModelStub modelStub = new ModelStubWithCca(validCca);

        assertThrows(CommandException.class,
                AddCcaCommand.MESSAGE_DUPLICATE_CCA, () -> addCcaCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Cca canoeing = new CcaBuilder().withName("Canoeing").build();
        Cca guitarEnsemble = new CcaBuilder().withName("Guitar ensemble").build();
        AddCcaCommand addCanoeingCommand = new AddCcaCommand((canoeing));
        AddCcaCommand addGuitarCommand = new AddCcaCommand((guitarEnsemble));

        // same object -> returns true
        assertTrue(addCanoeingCommand.equals(addCanoeingCommand));

        // same values -> returns true
        AddCcaCommand addCanoeingCommandCopy = new AddCcaCommand(canoeing);
        assertTrue(addCanoeingCommand.equals(addCanoeingCommandCopy));

        // different types -> returns false
        assertFalse(addCanoeingCommand.equals(1));

        // null -> returns false
        assertFalse(addCanoeingCommand.equals(null));

        // different person -> returns false
        assertFalse(addCanoeingCommand.equals(addGuitarCommand));
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
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(int zeroBasedIndex, Person person) {
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
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public HistoryManager getHistoryManager() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setHistoryManager(HistoryManager historyManager) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getAvailableNumberOfExecutedCommands() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getAvailableNumberOfInverselyExecutedCommands() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRollback() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canCommit() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void rememberExecutedCommand(Command command) throws CommandNotInvertibleException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean rollback() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean commit() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public FinanceTracker getFinanceTracker() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFinanceTracker(FinanceTracker financeTracker) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Purchase getPurchase(int paymentIndex) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Installment getInstallment(int instalIndex) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPurchase(Purchase purchase) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public void deletePurchase(int itemNumber) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPurchase(Purchase purchase) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addInstallment(Installment installment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Installment deleteInstallment(int instalNumber) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasInstallment(Installment installment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void editInstallmentByValue(int installmentNumber, String description, double value) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ArrayList<Installment> getInstallmentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMonthlyLimit(double value) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void listSpending() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Purchase> getPurchasesList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPurchaseList(Predicate<Purchase> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Purchase> getFilteredPurchaseList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public TaskList getTasks() {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public void addTask(Task t) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTask(Task t) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCca(Cca cca) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeCca(Cca cca) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateCca(Cca toBeUpdatedCca, Cca updatedCca) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCca(Cca cca) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void contains(Cca cca) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public CcaTracker getCcaTracker() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Planner getPlanner() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(Planner planner) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithCca extends ModelStub {
        private final Cca cca;

        ModelStubWithCca(Cca cca) {
            requireNonNull(cca);
            this.cca = cca;
        }

        @Override
        public boolean hasCca(Cca cca) {
            requireNonNull(cca);
            return this.cca.isSameCca(cca);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingCcaAdded extends ModelStub {
        private final CcaList ccaList = new CcaList();

        @Override
        public boolean hasCca(Cca cca) {
            requireNonNull(cca);
            return ccaList.contains(cca);
        }

        @Override
        public void addCca(Cca cca) {
            requireNonNull(cca);
            ccaList.addCca(cca);
        }
    }
}
