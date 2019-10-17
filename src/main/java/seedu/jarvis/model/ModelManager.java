package seedu.jarvis.model;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.jarvis.commons.core.GuiSettings;
import seedu.jarvis.commons.core.LogsCenter;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.address.AddressBook;
import seedu.jarvis.model.address.ReadOnlyAddressBook;
import seedu.jarvis.model.address.person.Person;
import seedu.jarvis.model.cca.Cca;
import seedu.jarvis.model.cca.CcaTracker;
import seedu.jarvis.model.course.Course;
import seedu.jarvis.model.course.CoursePlanner;
import seedu.jarvis.model.financetracker.FinanceTracker;
import seedu.jarvis.model.financetracker.exceptions.InstallmentNotFoundException;
import seedu.jarvis.model.financetracker.exceptions.PurchaseNotFoundException;
import seedu.jarvis.model.financetracker.installment.Installment;
import seedu.jarvis.model.financetracker.purchase.Purchase;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.model.planner.TaskList;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.model.userprefs.ReadOnlyUserPrefs;
import seedu.jarvis.model.userprefs.UserPrefs;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final HistoryManager historyManager;
    private final AddressBook addressBook;
    private final FinanceTracker financeTracker;
    private final UserPrefs userPrefs;
    private final CoursePlanner coursePlanner;
    private final CcaTracker ccaTracker;
    private final FilteredList<Person> filteredPersons;
    private final Planner planner;


    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(CcaTracker ccaTracker, HistoryManager historyManager,
                        FinanceTracker financeTracker, ReadOnlyAddressBook addressBook,
                        ReadOnlyUserPrefs userPrefs, Planner planner, CoursePlanner coursePlanner) {
        super();
        requireAllNonNull(ccaTracker, historyManager, financeTracker, addressBook, userPrefs, planner);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.ccaTracker = new CcaTracker(ccaTracker);
        this.historyManager = new HistoryManager(historyManager);
        this.addressBook = new AddressBook(addressBook);
        this.financeTracker = new FinanceTracker(financeTracker);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        this.userPrefs = new UserPrefs(userPrefs);
        this.planner = new Planner(planner);
        this.coursePlanner = new CoursePlanner(coursePlanner);
    }

    public ModelManager() {
        this(new CcaTracker(), new HistoryManager(), new FinanceTracker(), new AddressBook(),
                new UserPrefs(), new Planner(), new CoursePlanner());
    }


    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    //=========== HistoryManager =============================================================================

    /**
     * Gets the {@code HistoryManager}.
     *
     * @return {@code HistoryManager} object.
     */
    @Override
    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    /**
     * Replaces {@code HistoryManager} data with the data in {@code HistoryManager} given as argument.
     *
     * @param historyManager {@code HistoryManager} data to be used.
     */
    @Override
    public void setHistoryManager(HistoryManager historyManager) {
        this.historyManager.resetData(historyManager);
    }

    /**
     * Gets the number of available executed commands that can be undone.
     *
     * @return The number of available executed commands that can be undone.
     */
    @Override
    public int getAvailableNumberOfExecutedCommands() {
        return historyManager.getNumberOfAvailableExecutedCommands();
    }

    /**
     * Gets the number of available inversely executed commands that can be redone.
     *
     * @return The number of available inversely executed commands that can be redone.
     */
    @Override
    public int getAvailableNumberOfInverselyExecutedCommands() {
        return historyManager.getNumberOfAvailableInverselyExecutedCommands();
    }

    /**
     * Checks whether it is possible to roll back any commands.
     *
     * @return Whether it is possible to roll back any commands.
     */
    @Override
    public boolean canRollback() {
        return historyManager.canRollback();
    }

    /**
     * Checks whether it is possible to commit any commands.
     *
     * @return Whether it is possible to commit any commands.
     */
    @Override
    public boolean canCommit() {
        return historyManager.canCommit();
    }

    /**
     * Adds the latest executed command. {@code Command} to be added must be invertible.
     *
     * @param command {@code Command} to be added.
     */
    @Override
    public void rememberExecutedCommand(Command command) {
        historyManager.rememberExecutedCommand(command);
        historyManager.forgetAllInverselyExecutedCommands();
    }

    /**
     * Rolls back the changes made by the latest executed command by inversely executing the command.
     *
     * @return Whether the inverse execution of the latest executed command was successful.
     */
    @Override
    public boolean rollback() {
        return historyManager.rollback(this);
    }

    /**
     * Commits the changes made by the latest inversely executed command by executing the command.
     *
     * @return Whether the execution of the latest executed command was successful.
     */
    @Override
    public boolean commit() {
        return historyManager.commit(this);
    }

    //=========== FinanceTracker ================================================================================
    /**
     * Gets the {@code FinanceTracker}.
     *
     * @return {@code FinanceTracker} object.
     */
    @Override
    public FinanceTracker getFinanceTracker() {
        return financeTracker;
    }

    /**
     * Replaces {@code FinanceTracker} data with the data in {@code FinanceTracker} given as argument.
     *
     * @param financeTracker {@code FinanceTracker} data to be used.
     */
    @Override
    public void setFinanceTracker(FinanceTracker financeTracker) {
        this.financeTracker.resetData(financeTracker);
    }

    @Override
    public Purchase getPurchase(int paymentIndex) {
        return financeTracker.getPurchase(paymentIndex);
    }

    @Override
    public Installment getInstallment(int instalIndex) throws InstallmentNotFoundException {
        return financeTracker.getInstallment(instalIndex);
    }

    /**
     * Adds single use payment.
     *
     * @param purchase
     */
    @Override
    public void addPurchase(Purchase purchase) {
        financeTracker.addSinglePurchase(purchase);
    }

    /**
     * Deletes single use payment.
     *
     * @param itemNumber
     */
    @Override
    public void deletePurchase(int itemNumber) throws PurchaseNotFoundException {
        financeTracker.deleteSinglePurchase(itemNumber);
    }

    /**
     * Checks for the existence of the same purchase. Purchases are allowed to be duplicated, thus method always returns
     * false.
     *
     * @param purchase to be added
     * @return boolean that is always false
     */
    @Override
    public boolean hasPurchase(Purchase purchase) {
        return false;
    }

    /**
     * Adds installment.
     *
     * @param installment
     */
    @Override
    public void addInstallment(Installment installment) {
        financeTracker.addInstallment(installment);
    }

    /**
     * Deletes installment.
     *
     * @param instalNumber
     */
    @Override
    public Installment deleteInstallment(int instalNumber) throws InstallmentNotFoundException {
        return financeTracker.deleteInstallment(instalNumber);
    }

    @Override
    public boolean hasInstallment(Installment installment) {
        return financeTracker.hasInstallment(installment);
    }

    @Override
    public void setInstallment(Installment target, Installment editedInstallment) {
        financeTracker.setInstallment(target, editedInstallment);
    }

    /**
     * Retrieves list of all installments
     *
     * @return ArrayList
     */
    @Override
    public ArrayList<Installment> getInstallmentList() {
        return financeTracker.getInstallmentList();
    }

    /**
     * Retrieves list of all installments
     *
     * @return ArrayList
     */
    @Override
    public ArrayList<Purchase> getPurchaseList() {
        return financeTracker.getPurchaseList();
    }

    /**
     * Sets the monthly limit for spending.
     *
     * @param value
     */
    @Override
    public void setMonthlyLimit(double value) {
        financeTracker.setMonthlyLimit(value);
    }

    /**
     * Lists all purchases and payments from this month.
     *
     */
    @Override
    public void listSpending() {
        financeTracker.listSpending();
    }

    //=========== AddressBook ================================================================================

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    /**
     * Adds {@code Person} at a given index.
     *
     * @param zeroBasedIndex Zero-based index to add {@code Person} to.
     * @param person {@code Person} to be added.
     */
    @Override
    public void addPerson(int zeroBasedIndex, Person person) {
        addressBook.addPerson(zeroBasedIndex, person);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return addressBook.getFilteredPersonList();
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        addressBook.updateFilteredPersonList(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return historyManager.equals(other.historyManager)
                && financeTracker.equals(other.financeTracker)
                && planner.isEqual(other.planner)
                && addressBook.equals(other.addressBook)
                && addressBook.getFilteredPersonList().equals(other.addressBook.getFilteredPersonList())
                && userPrefs.equals(other.userPrefs)
                && coursePlanner.equals(other.coursePlanner);
    }


    //=========== Cca Tracker ================================================================================

    @Override
    public void contains(Cca cca) {

    }

    @Override
    public void addCca(Cca cca) {
        requireNonNull(cca);
        ccaTracker.addCca(cca);
    }

    @Override
    public void removeCca(Cca cca) {

    }

    @Override
    public void updateCca(Cca toBeUpdatedCca, Cca updatedCca) {

    }

    @Override
    public boolean hasCca(Cca cca) {
        requireNonNull(cca);

        return ccaTracker.contains(cca);
    }

    @Override
    public CcaTracker getCcaTracker() {
        return ccaTracker;
    }

    @Override
    public int getNumberOfCcas() {
        return ccaTracker.getNumberOfCcas();
    }

    @Override
    public Cca getCca(Index index) throws CommandException {
        requireNonNull(index.getZeroBased());
        return ccaTracker.getCca(index);
    }

    //=========== Planner =============================================================

    /**
     * Retrieves the tasks stored in the planner
     * @return a list of tasks stored in the planner
     */
    @Override
    public TaskList getTasks() {
        return planner.getTasks();
    }

    /**
     * Adds a task to the planner
     * @param t the task to be added
     */
    @Override
    public void addTask(Task t) {
        planner.addTask(t);
    }

    /**
     * Determines whether the planner contains the given task
     * @param t the task in question
     * @return true if the planner already contains the task, false if
     *         it does not.
     */
    @Override
    public boolean hasTask(Task t) {
        return planner.hasTask(t);
    }

    @Override
    public Planner getPlanner() {
        return planner;
    }

    @Override
    public void resetData(Planner planner) {
        this.planner.resetData(planner);
    }
    //=========== Course Planner ========================================================

    @Override
    public void lookUpCourse(Course course) {
        coursePlanner.lookUpCourse(course);
    }

    @Override
    public CoursePlanner getCoursePlanner() {
        return coursePlanner;
    }

    @Override
    public boolean isEqual(Planner other) {
        return this.planner.isEqual(other);
    }
}
