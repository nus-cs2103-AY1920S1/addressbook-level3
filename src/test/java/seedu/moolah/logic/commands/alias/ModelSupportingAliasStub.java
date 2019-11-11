package seedu.moolah.logic.commands.alias;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.moolah.commons.core.GuiSettings;
import seedu.moolah.model.Model;
import seedu.moolah.model.ReadOnlyMooLah;
import seedu.moolah.model.ReadOnlyUserPrefs;
import seedu.moolah.model.alias.Alias;
import seedu.moolah.model.alias.AliasMappings;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.expense.Description;
import seedu.moolah.model.expense.Event;
import seedu.moolah.model.expense.Expense;
import seedu.moolah.model.expense.Timestamp;
import seedu.moolah.model.modelhistory.ModelChanges;
import seedu.moolah.model.modelhistory.ReadOnlyModelHistory;
import seedu.moolah.model.statistics.Statistics;

/**
 * Model Stub which supports Alias methods related to Alias feature.
 */
public class ModelSupportingAliasStub implements Model {

    private AliasMappings aliasMappings;

    ModelSupportingAliasStub() {
        aliasMappings = new AliasMappings();
    }

    @Override
    public void resetData(Model model) {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public Model copy() {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public void applyChanges(ModelChanges changes) {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public ReadOnlyModelHistory getModelHistory() {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public void setModelHistory(ReadOnlyModelHistory history) {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public void addToPastChanges(ModelChanges changes) {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public void addToFutureChanges(ModelChanges changes) {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public void commit(String changeMessage, Model prevModel) {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public boolean canRollback() {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public Optional<String> rollback() {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public boolean canMigrate() {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public Optional<String> migrate() {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public AliasMappings getAliasMappings() {
        return aliasMappings;
    }

    @Override
    public void setAliasMappings(AliasMappings aliasMappings) {
        this.aliasMappings = aliasMappings;
    }

    @Override
    public void addUserAlias(Alias alias) {
        aliasMappings = aliasMappings.addAlias(alias);
    }

    @Override
    public boolean removeAliasWithName(String aliasName) {
        return aliasMappings.removeAlias(aliasName);
    }

    @Override
    public boolean aliasWithNameExists(String aliasName) {
        return aliasMappings.aliasWithNameExists(aliasName);
    }

    @Override
    public Path getMooLahFilePath() {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public void setMooLahFilePath(Path mooLahFilePath) {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public ReadOnlyMooLah getMooLah() {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public void setMooLah(ReadOnlyMooLah mooLah) {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public boolean hasExpense(Expense expense) {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public void deleteExpense(Expense target) {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public void addExpense(Expense expense) {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public void setExpense(Expense target, Expense editedExpense) {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public ObservableList<Expense> getFilteredExpenseList() {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public Predicate<? super Expense> getFilteredExpensePredicate() {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public void updateFilteredExpenseList(Predicate<? super Expense> predicate) {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public boolean hasBudget(Budget budget) {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public boolean hasBudgetWithName(Description targetDescription) {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public Budget getPrimaryBudget() {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public boolean hasPrimaryBudget() {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public void addBudget(Budget budget) {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public void switchBudgetTo(Description description) {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public void deleteBudget(Budget target) {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public void setBudget(Budget target, Budget editedBudget) {

    }

    @Override
    public void changePrimaryBudgetWindow(Timestamp pastDate) {

    }

    @Override
    public ObservableList<Budget> getFilteredBudgetList() {
        return null;
    }

    @Override
    public void updateFilteredBudgetList(Predicate<? super Budget> budget) {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public Predicate<? super Budget> getFilteredBudgetPredicate() {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public void clearBudgets() {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public void deleteBudgetWithName(Description description) {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public ObservableList<Event> getFilteredEventList() {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public Predicate<? super Event> getFilteredEventPredicate() {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public void updateFilteredEventList(Predicate<? super Event> predicate) {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public void notifyAboutTranspiredEvents(List<Event> events) {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public boolean hasEvent(Event event) {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public void addEvent(Event event) {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public void deleteEvent(Event target) {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public void setEvent(Event eventToEdit, Event editedEvent) {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public Statistics getStatistics() {
        throw new AssertionError("Method should not be called.");
    }

    @Override
    public void setStatistics(Statistics statistics) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void handleAlreadyTranspiredEvents() {
        throw new AssertionError("This method should not be called.");
    }
}
