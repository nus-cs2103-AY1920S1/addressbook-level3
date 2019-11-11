package seedu.jarvis.model;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;

import java.text.DecimalFormat;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.value.ObservableStringValue;
import javafx.collections.ObservableList;

import seedu.jarvis.commons.core.GuiSettings;
import seedu.jarvis.commons.core.LogsCenter;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.model.cca.Cca;
import seedu.jarvis.model.cca.CcaTracker;
import seedu.jarvis.model.cca.ccaprogress.CcaMilestoneList;
import seedu.jarvis.model.course.Course;
import seedu.jarvis.model.course.CoursePlanner;
import seedu.jarvis.model.finance.FinanceTracker;
import seedu.jarvis.model.finance.MonthlyLimit;
import seedu.jarvis.model.finance.installment.Installment;
import seedu.jarvis.model.finance.purchase.Purchase;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.model.planner.TaskList;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.model.userprefs.ReadOnlyUserPrefs;
import seedu.jarvis.model.userprefs.UserPrefs;
import seedu.jarvis.model.viewstatus.ViewStatus;
import seedu.jarvis.model.viewstatus.ViewType;

/**
 * Represents the in-memory model of Jarvis.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private static DecimalFormat df2 = new DecimalFormat("#.00");

    private final HistoryManager historyManager;
    private final FinanceTracker financeTracker;
    private final UserPrefs userPrefs;
    private final CoursePlanner coursePlanner;
    private final CcaTracker ccaTracker;
    private final Planner planner;
    private ViewStatus viewStatus;

    /**
     * Initializes a ModelManager with the given ccatracker, historymanager, financetracker, userPrefs, planner and
     * courseplanner.
     */
    public ModelManager(CcaTracker ccaTracker, HistoryManager historyManager,
                        FinanceTracker financeTracker, ReadOnlyUserPrefs userPrefs, Planner planner,
                        CoursePlanner coursePlanner) {
        super();
        requireAllNonNull(ccaTracker, historyManager, financeTracker, userPrefs, planner);

        logger.fine("Initializing with JARVIS: user prefs " + userPrefs);

        this.ccaTracker = new CcaTracker(ccaTracker);
        this.historyManager = new HistoryManager(historyManager);
        this.financeTracker = new FinanceTracker(financeTracker);
        this.userPrefs = new UserPrefs(userPrefs);
        this.planner = new Planner(planner);
        this.coursePlanner = new CoursePlanner(coursePlanner);
        this.viewStatus = new ViewStatus(ViewType.HOME_PAGE);
    }

    public ModelManager() {
        this(new CcaTracker(), new HistoryManager(), new FinanceTracker(), new UserPrefs(), new Planner(),
                new CoursePlanner());
    }

    //=========== ViewStatus ==================================================================================

    public ViewStatus getViewStatus() {
        return viewStatus;
    }

    public void setViewStatus(ViewType viewType) {
        viewStatus.setViewType(viewType);
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
     * Gets the maximum number of commands that can be undone/redone.
     *
     * @return maximum number of commands that can be undone/redone.
     */
    @Override
    public int getHistoryRange() {
        return HistoryManager.getHistoryRange();
    }

    /**
     * Gets a {@code ObservableList} of {@code Command} objects that are executed.
     *
     * @return {@code ObservableList} of {@code Command} objects.
     */
    @Override
    public ObservableList<Command> getExecutedCommandsList() {
        return historyManager.getExecutedCommandsList();
    }

    /**
     * Gets a {@code ObservableList} of {@code Command} objects that are inversely executed.
     *
     * @return {@code ObservableList} of {@code Command} objects.
     */
    @Override
    public ObservableList<Command> getInverselyExecutedCommandsList() {
        return historyManager.getInverselyExecutedCommandsList();
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
     * Retrieves purchase at a particular index as seen on the list of finance tracker.
     */
    @Override
    public Purchase getPurchase(int paymentIndex) {
        return financeTracker.getPurchase(paymentIndex);
    }

    /**
     * Updates the filter of the purchase list to be viewed with the new predicate.
     *
     * @param predicate to filter purchases
     */
    @Override
    public void updateFilteredPurchaseList(Predicate<Purchase> predicate) {
        financeTracker.updateFilteredPurchaseList(predicate);
    }

    /**
     * Retrieves list of all purchases with current predicate applied
     *
     * @return ObservableList
     */
    @Override
    public ObservableList<Purchase> getFilteredPurchaseList() {
        return financeTracker.getFilteredPurchaseList();
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

    @Override
    public void addPurchase(int zeroBasedIndex, Purchase newPurchase) {
        financeTracker.addSinglePurchase(zeroBasedIndex, newPurchase);
    }

    /**
     * Deletes single use payment.
     *
     * @param itemNumber to be deleted
     */
    @Override
    public Purchase deletePurchase(int itemNumber) {
        return financeTracker.deleteSinglePurchase(itemNumber);
    }

    /**
     * Deletes a single use payment.
     *
     * @param purchase to be deleted
     */
    @Override
    public Purchase deletePurchase(Purchase purchase) {
        assert financeTracker.hasPurchase(purchase) : String.format("%s should exist", purchase);
        return financeTracker.deleteSinglePurchase(purchase);
    }


    /**
     * Checks for the existence of the purchase.
     *
     * @param purchase
     */
    public boolean hasPurchase(Purchase purchase) {
        return financeTracker.hasPurchase(purchase);
    }

    /**
     * Retrieves installment at a particular index as seen on the list of finance tracker.
     */
    @Override
    public Installment getInstallment(int instalIndex) {
        return financeTracker.getInstallment(instalIndex);
    }

    /**
     * Updates the filter of the installment list to be viewed with the new predicate.
     *
     * @param predicate to filter installments
     */
    @Override
    public void updateFilteredInstallmentList(Predicate<Installment> predicate) {
        financeTracker.updateFilteredInstallmentList(predicate);
    }

    /**
     * Retrieves list of all installments with current predicate applied
     *
     * @return ObservableList
     */
    @Override
    public ObservableList<Installment> getFilteredInstallmentList() {
        return financeTracker.getFilteredInstallmentList();
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

    @Override
    public void addInstallment(int zeroBasedIndex, Installment installment) {
        financeTracker.addInstallment(zeroBasedIndex, installment);
    }

    /**
     * Deletes installment.
     *
     * @param instalNumber
     */
    @Override
    public Installment deleteInstallment(int instalNumber) {
        return financeTracker.deleteInstallment(instalNumber);
    }

    /**
     * Deletes installment.
     *
     * @param installment
     */
    @Override
    public Installment deleteInstallment(Installment installment) {
        assert financeTracker.hasInstallment(installment) : String.format("%s should exist", installment);
        return financeTracker.deleteInstallment(installment);
    }

    /**
     * Checks for the existence of the same installment in the finance tracker.
     *
     * @param installment to be checked
     * @return boolean
     */
    @Override
    public boolean hasInstallment(Installment installment) {
        return financeTracker.hasInstallment(installment);
    }

    /**
     * Checks for the existence of an installment with the same description in the finance tracker.
     *
     * @param installment to be checked
     */
    public boolean hasSimilarInstallment(Installment installment) {
        requireNonNull(installment);

        return financeTracker.hasSimilarInstallment(installment);
    }

    /**
     * Replaces the installment in the list with {@code editedInstallment}.
     * The identity of {@code editedInstallment} must not be the same as another existing installment in the
     * list.
     *
     * @param target installment to be replaced
     * @param editedInstallment installment with all fields edited according to command
     */
    @Override
    public void setInstallment(Installment target, Installment editedInstallment) {
        assert financeTracker.hasInstallment(target) : String.format("%s should exist", target);
        financeTracker.setInstallment(target, editedInstallment);
    }

    /**
     * Sets the monthly limit for spending.
     *
     * @param limit
     */
    @Override
    public void setMonthlyLimit(MonthlyLimit limit) {
        financeTracker.setMonthlyLimit(limit);
    }

    /**
     * Retrieves monthly limit if it has been set by the user.
     *
     * @return Optional containing the monthly limit
     */
    @Override
    public Optional<MonthlyLimit> getMonthlyLimit() {
        return financeTracker.getMonthlyLimit();
    }

    /**
     * Calculates total expenditure by user for this month.
     */
    @Override
    public double calculateTotalSpending() {
        return financeTracker.calculateTotalSpending();
    }

    /**
     * Calculates total expenditure by user for this month to be rendered onto Ui.
     */
    @Override
    public String getTotalSpending() {
        return df2.format(calculateTotalSpending());
    }

    /**
     * Calculates the remaining amount that is available by user.
     */
    @Override
    public double calculateRemainingAmount() {
        return financeTracker.calculateRemainingAmount();
    }

    /**
     * Calculates the remaining amount that is available by user to be rendered onto Ui.
     */
    @Override
    public String getRemainingAmount() {
        return df2.format(calculateRemainingAmount());
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
                && financeTracker.getFilteredPurchaseList().equals(other.financeTracker.getFilteredPurchaseList())
                && financeTracker.getFilteredInstallmentList().equals(other.financeTracker.getFilteredInstallmentList())
                && planner.equals(other.planner)
                && userPrefs.equals(other.userPrefs)
                && coursePlanner.equals(other.coursePlanner)
                && ccaTracker.equals(other.ccaTracker);
    }


    //=========== Cca Tracker ================================================================================

    @Override
    public boolean containsCca(Cca cca) {
        return ccaTracker.containsCca(cca);
    }

    @Override
    public void addCca(Cca cca) {
        requireNonNull(cca);
        ccaTracker.addCca(cca);
    }

    @Override
    public void addCca(Index targetIndex, Cca deletedCca) {
        requireAllNonNull(targetIndex, deletedCca);
        ccaTracker.addCca(targetIndex, deletedCca);
    }

    @Override
    public void removeCca(Cca cca) {
        requireNonNull(cca);
        ccaTracker.removeCca(cca);
    }

    @Override
    public void updateCca(Cca toBeUpdatedCca, Cca updatedCca) {
        requireAllNonNull(toBeUpdatedCca, updatedCca);
        ccaTracker.updateCca(toBeUpdatedCca, updatedCca);
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
    public Cca getCca(Index index) {
        requireNonNull(index.getZeroBased());
        return ccaTracker.getCca(index);
    }

    @Override
    public void updateFilteredCcaList(Predicate<Cca> predicate) {
        ccaTracker.updateFilteredCcaList(predicate);
    };

    @Override
    public ObservableList<Cca> getFilteredCcaList() {
        return ccaTracker.getFilteredCcaList();
    }

    @Override
    public void addProgress(Cca targetCca, CcaMilestoneList toAddCcaMilestoneList) {
        ccaTracker.addProgress(targetCca, toAddCcaMilestoneList);
    }

    @Override
    public void increaseProgress(Index index) {
        ccaTracker.increaseProgress(index);
    }

    @Override
    public boolean ccaContainsProgress(Index index) {
        return ccaTracker.ccaContainsProgress(index);
    }

    @Override
    public boolean ccaAtMaxIncrement(Index targetIndex) {
        return ccaTracker.ccaAtMaxIncrement(targetIndex);
    }

    @Override
    public void removeProgress(Cca targetCca, CcaMilestoneList toRemoveCcaMilestoneList) {
        ccaTracker.removeCcaMilestoneList(targetCca, toRemoveCcaMilestoneList);
    };

    @Override
    public boolean ccaProgressAtMinLevel(Index targetIndex) {
        return ccaTracker.ccaProgressAtMinLevel(targetIndex);
    };

    @Override
    public void decreaseProgress(Index targetIndex) {
        ccaTracker.decreaseProgress(targetIndex);
    }


    //=========== Planner =============================================================

    /**
     * Retrieves the tasks stored in the planner
     * @return a list of tasks stored in the planner
     */
    @Override
    public TaskList getTasks() {
        return planner.getTaskList();
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
     * Adds a {@code Task} at a given {@code Index}
     *
     * @param zeroBasedIndex Zero-based index to add {@code Task} to
     * @param task {@code Task} to be added
     */
    public void addTask(int zeroBasedIndex, Task task) {
        planner.addTask(zeroBasedIndex, task);
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

    /**
     * Retrieves the Planner object
     * @return the planner
     */
    @Override
    public Planner getPlanner() {
        return planner;
    }

    /**
     * Clears all data in a planner to return a new planner
     * @param planner {@code Planner} to take reference from.
     */
    @Override
    public void resetData(Planner planner) {
        this.planner.resetData(planner);
    }

    /**
     * Retrieves a task from the planner at the specified index
     * @param index index of the task that is being retrieved
     * @return the task at the given index
     */
    @Override
    public Task getTask(Index index) {
        return planner.getTask(index);
    }

    /**
     * Deletes a task in the planner at the specified index
     * @param index index of the task to be deleted
     */
    @Override
    public void deleteTask(Index index) {
        planner.deleteTask(index);
    }

    /**
     * Deletes the specified task in the planner
     * @param t the task to be deleted
     */
    @Override
    public void deleteTask(Task t) {
        planner.deleteTask(t);
    }

    /**
     * Retrieves the size of the planner, i.e. the number of tasks in the planner
     * @return the size of the planner
     */
    @Override
    public int size() {
        return planner.size();
    }

    /**
     * Updates the {@code filteredTaskList} in the {@code Planner} according to the
     * given {@code Predicate}
     *
     * @param predicate {@code Predicate} to be applied to filter {@code filteredTaskList}
     */
    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        planner.updateFilteredTaskList(predicate);
    }

    /**
     * Updates the list of tasks according to the day and week
     */
    public void updateSchedule() {
        planner.updateSchedule();
    }

    /**
     * Updates the list of tasks according to changes made in the planner
     */
    @Override
    public void updateUnfilteredTaskList() {
        planner.updateUnfilteredTaskList();
    }

    /**
     * Returns an unmodifiable view of the list of {@code Task} backed by the internal list
     * of {@code Planner}
     */
    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return planner.getFilteredTaskList();
    }

    /**
     * Returns an unmodifiable view of the list of {@code Task} backed by the internal list of
     * {@code Planner}
     * @return a list of all the {@code Task} in the {@code Planner}
     */
    @Override
    public ObservableList<Task> getUnfilteredTaskList() {
        return planner.getUnfilteredTaskList();
    }

    /**
     * Returns an unmodifiable view of the list of {@code Task} that coincide with the given day,
     * backed by the internal list of {@code Planner}
     * @return a list of all the {@code Task} in the {@code Planner}
     */
    @Override
    public ObservableList<Task> getTasksToday() {
        return planner.getTasksToday();
    }

    /**
     * Returns an unmodifiable view of the list of {@code Task} that coincide with the given week,
     * backed by the internal list of {@code Planner}
     * @return a list of all the {@code Task} in the {@code Planner}
     */
    @Override
    public ObservableList<Task> getTasksThisWeek() {
        return planner.getTasksThisWeek();
    }

    /**
     * Marks a {@code Task} at the specified {@code Index} as done
     * @param i {@code Index} of the {@code Task} to be marked as done
     */
    @Override
    public void markTaskAsDone(Index i) {
        planner.markTaskAsDone(i);
    }

    //=========== Course Planner ========================================================

    @Override
    public void lookUpCourse(Course course) {
        coursePlanner.lookUpCourse(course);
    }

    @Override
    public void checkCourse(String val) {
        coursePlanner.checkCourse(val);
    }

    @Override
    public void addCourse(Course course) {
        coursePlanner.addCourse(course);
    }

    @Override
    public void addCourse(int zeroBasedIndex, Course course) {
        coursePlanner.addCourse(zeroBasedIndex, course);
    }

    @Override
    public void deleteCourse(Course course) {
        coursePlanner.deleteCourse(course);
    }

    @Override
    public boolean hasCourse(Course course) {
        return coursePlanner.hasCourse(course);
    }

    @Override
    public ObservableList<Course> getUnfilteredCourseList() {
        return coursePlanner.getCourseList();
    }

    @Override
    public ObservableStringValue getDisplayText() {
        return coursePlanner.getText();
    }

    @Override
    public CoursePlanner getCoursePlanner() {
        return coursePlanner;
    }

    @Override
    public void setCoursePlanner(CoursePlanner cp) {
        coursePlanner.resetData(cp);
    }
}
