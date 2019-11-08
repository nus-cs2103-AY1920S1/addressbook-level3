package seedu.jarvis.testutil;

import java.util.Optional;
import java.util.function.Predicate;

import javafx.beans.value.ObservableStringValue;
import javafx.collections.ObservableList;
import seedu.jarvis.commons.core.GuiSettings;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.exceptions.CommandNotInvertibleException;
import seedu.jarvis.model.Model;
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
import seedu.jarvis.model.viewstatus.ViewStatus;
import seedu.jarvis.model.viewstatus.ViewType;

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
    public HistoryManager getHistoryManager() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setHistoryManager(HistoryManager historyManager) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public int getHistoryRange() {
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
    public Purchase getPurchase(int paymentIndex) {
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
    public Installment getInstallment(int instalIndex) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredInstallmentList(Predicate<Installment> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Installment> getFilteredInstallmentList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addPurchase(Purchase purchase) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addPurchase(int zeroBasedIndex, Purchase newPurchase) {
        throw new AssertionError("This method should not be called.");
    }


    @Override
    public Purchase deletePurchase(int itemNumber) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Purchase deletePurchase(Purchase purchase) {
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
    public void addInstallment(int zeroBasedIndex, Installment installment) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Installment deleteInstallment(int instalNumber) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Installment deleteInstallment(Installment installment) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasInstallment(Installment installment) {
        throw new AssertionError("This method should not be called.");
    }

    public boolean hasSimilarInstallment(Installment installment) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setInstallment(Installment target, Installment editedInstallment) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setMonthlyLimit(MonthlyLimit limit) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Optional<MonthlyLimit> getMonthlyLimit() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public double calculateTotalSpending() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public String getTotalSpending() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public double calculateRemainingAmount() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public String getRemainingAmount() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean containsCca(Cca cca) {
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
    public void addCca(Index targetIndex, Cca cca) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addTask(Task t) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addTask(int zeroBasedIndex, Task task) {
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
    public CcaTracker getCcaTracker() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public int getNumberOfCcas() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Cca getCca(Index index) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredCcaList(Predicate<Cca> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Cca> getFilteredCcaList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addProgress(Cca targetCca, CcaMilestoneList toAddCcaMilestoneList) {
        throw new AssertionError("This method should not be called.");

    }

    @Override
    public void increaseProgress(Index index) {
        throw new AssertionError("This method should not be called.");

    }

    @Override
    public boolean ccaContainsProgress(Index targetIndex) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean ccaAtMaxIncrement(Index targetIndex) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void removeProgress(Cca targetCca, CcaMilestoneList toAddCcaMilestoneList) {
        throw new AssertionError("This method should not be called.");

    }

    @Override
    public boolean ccaProgressAtMinLevel(Index targetIndex) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void decreaseProgress(Index targetIndex) {
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
    public void deleteTask(Task t) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public int size() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateSchedule() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateUnfilteredTaskList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Task> getUnfilteredTaskList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Task> getTasksToday() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Task> getTasksThisWeek() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void markTaskAsDone(Index i) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void lookUpCourse(Course code) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void checkCourse(String val) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addCourse(Course course) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addCourse(int zeroBasedIndex, Course course) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteCourse(Course course) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasCourse(Course course) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Course> getUnfilteredCourseList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableStringValue getDisplayText() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public CoursePlanner getCoursePlanner() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setCoursePlanner(CoursePlanner cp) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ViewStatus getViewStatus() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setViewStatus(ViewType viewType) {
        throw new AssertionError("This method should not be called.");
    }
}
