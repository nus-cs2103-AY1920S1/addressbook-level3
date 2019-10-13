package seedu.jarvis.logic.commands.finance;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.jarvis.commons.core.GuiSettings;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandNotInvertibleException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.address.AddressBook;
import seedu.jarvis.model.address.ReadOnlyAddressBook;
import seedu.jarvis.model.address.person.Person;
import seedu.jarvis.model.financetracker.FinanceTracker;
import seedu.jarvis.model.financetracker.Installment;
import seedu.jarvis.model.financetracker.Purchase;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.userprefs.ReadOnlyUserPrefs;
import seedu.jarvis.testutil.PurchaseBuilder;

public class PaidCommandTest {

    /**
     * Verifies that checking {@code PaidCommand} for the availability of inverse execution returns true.
     */
    @BeforeEach
    public void hasInverseExecution() {
        Purchase validPurchase = new PurchaseBuilder().build();
        PaidCommand paidCommand = new PaidCommand(validPurchase);
        assertTrue(paidCommand.hasInverseExecution());
    }

    @Test
    public void constructor_nullPurchase_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PaidCommand(null));
    }

    @Test
    public void execute_purchaseAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPurchaseAdded modelStub = new ModelStubAcceptingPurchaseAdded();
        Purchase validPurchase = new PurchaseBuilder().build();

        CommandResult commandResult = new PaidCommand(validPurchase).execute(modelStub);

        assertEquals(String.format(PaidCommand.MESSAGE_SUCCESS, validPurchase), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPurchase), modelStub.purchasesAdded);
    }

    @Test
    public void equals() {
        Purchase movie = new PurchaseBuilder().withDescription("movie ticket").build();
        Purchase karaoke = new PurchaseBuilder().withDescription("karaoke night").build();
        PaidCommand addMovieCommand = new PaidCommand(movie);
        PaidCommand addKaraokeCommand = new PaidCommand(karaoke);

        // same object -> returns true
        assertTrue(addMovieCommand.equals(addMovieCommand));

        // same values -> returns true
        PaidCommand addMovieCommandCopy = new PaidCommand(movie);
        assertTrue(addMovieCommand.equals(addMovieCommandCopy));

        // different types -> returns false
        assertFalse(addMovieCommand.equals(1));

        // null -> returns false
        assertFalse(addMovieCommand.equals(null));

        // different purchase -> returns false
        assertFalse(addMovieCommand.equals(addKaraokeCommand));
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
        public ObservableList<Purchase> getFilteredPurchaseList() {
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

    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPurchase extends PaidCommandTest.ModelStub {
        private final Purchase purchase;

        ModelStubWithPurchase(Purchase purchase) {
            requireNonNull(purchase);
            this.purchase = purchase;
        }

        @Override
        public boolean hasPurchase(Purchase purchase) {
            requireNonNull(purchase);
            return this.purchase.isSamePurchase(purchase);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPurchaseAdded extends PaidCommandTest.ModelStub {
        final ArrayList<Purchase> purchasesAdded = new ArrayList<>();

        @Override
        public boolean hasPurchase(Purchase purchase) {
            requireNonNull(purchase);
            return purchasesAdded.stream().anyMatch(purchase::isSamePurchase);
        }

        @Override
        public void addPurchase(Purchase purchase) {
            requireNonNull(purchase);
            purchasesAdded.add(purchase);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
