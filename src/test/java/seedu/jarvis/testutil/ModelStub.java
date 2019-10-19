package seedu.jarvis.testutil;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.jarvis.commons.core.GuiSettings;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.logic.commands.exceptions.CommandNotInvertibleException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.address.ReadOnlyAddressBook;
import seedu.jarvis.model.address.person.Person;
import seedu.jarvis.model.cca.Cca;
import seedu.jarvis.model.cca.CcaTracker;
import seedu.jarvis.model.course.Course;
import seedu.jarvis.model.course.CoursePlanner;
import seedu.jarvis.model.financetracker.FinanceTracker;
import seedu.jarvis.model.financetracker.Purchase;
import seedu.jarvis.model.financetracker.installment.Installment;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.model.planner.TaskList;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.model.userprefs.ReadOnlyUserPrefs;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
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
    public ObservableList<Command> getExecutedCommandsList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Command> getInverselyExecutedCommandsList() {
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
    public void contains(Cca cca) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public TaskList getTasks() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addCca(Cca cca) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addTask(Task t) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void removeCca(Cca cca) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasTask(Task t) {
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
    public CcaTracker getCcaTracker() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public int getNumberOfCcas() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Cca getCca(Index index) throws CommandException {
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

    @Override
    public Task getTask(Index index) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteTask(Index index) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public int size() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void lookUpCourse(Course code) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public CoursePlanner getCoursePlanner() {
        throw new AssertionError("This method should not be called.");
    }
}
