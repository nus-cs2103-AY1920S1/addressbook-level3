package seedu.moolah.model.modelhistory;

import static java.util.Objects.requireNonNull;
import static seedu.moolah.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Optional;
import java.util.function.Predicate;

import seedu.moolah.model.Model;
import seedu.moolah.model.ReadOnlyMooLah;
import seedu.moolah.model.ReadOnlyUserPrefs;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.expense.Event;
import seedu.moolah.model.expense.Expense;

/**
 * Represents the changes of a model before a particular execution of a command described by the change message.
 */
public class ModelChanges {

    private String changeMessage;
    private ReadOnlyMooLah mooLah;
    private ReadOnlyUserPrefs userPrefs;
    private Predicate<? super Expense> expensePredicate;
    private Predicate<? super Event> eventPredicate;
    private Predicate<? super Budget> budgetPredicate;

    /**
     * Constructs a ModelChanges object.
     * @param changeMessage the change message
     */
    public ModelChanges(String changeMessage) {
        requireNonNull(changeMessage);
        setChangeMessage(changeMessage);
    }

    /**
     * Copy constructor for ModelChanges.
     * @param changes the ModelChanges object to be copied.
     */
    public ModelChanges(ModelChanges changes) {
        requireNonNull(changes);
        setChangeMessage(changes.changeMessage);
        setMooLah(changes.mooLah);
        setUserPrefs(changes.userPrefs);
        setExpensePredicate(changes.expensePredicate);
        setEventPredicate(changes.eventPredicate);
        setBudgetPredicate(changes.budgetPredicate);
    }

    /**
     * Creates a copy of the current ModelChanges.
     * @return a new ModelChanges object with values equal to the current ModelChanges.
     */
    public ModelChanges copy() {
        return new ModelChanges(this);
    }

    public String getChangeMessage() {
        return changeMessage;
    }

    public Optional<ReadOnlyMooLah> getMooLah() {
        return Optional.ofNullable(mooLah);
    }

    public Optional<ReadOnlyUserPrefs> getUserPrefs() {
        return Optional.ofNullable(userPrefs);
    }

    public Optional<Predicate<? super Expense>> getExpensePredicate() {
        return Optional.ofNullable(expensePredicate);
    }

    public Optional<Predicate<? super Event>> getEventPredicate() {
        return Optional.ofNullable(eventPredicate);
    }

    public Optional<Predicate<? super Budget>> getBudgetPredicate() {
        return Optional.ofNullable(budgetPredicate);
    }

    public ModelChanges setChangeMessage(String changeMessage) {
        requireNonNull(changeMessage);
        this.changeMessage = changeMessage;
        return this;
    }

    public ModelChanges setMooLah(ReadOnlyMooLah mooLah) {
        if (mooLah != null) {
            this.mooLah = mooLah.copy();
        }
        return this;
    }

    public ModelChanges setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        if (userPrefs != null) {
            this.userPrefs = userPrefs.copy();
        }
        return this;
    }

    public ModelChanges setExpensePredicate(Predicate<? super Expense> expensePredicate) {
        this.expensePredicate = expensePredicate;
        return this;
    }

    public ModelChanges setEventPredicate(Predicate<? super Event> eventPredicate) {
        this.eventPredicate = eventPredicate;
        return this;
    }

    public ModelChanges setBudgetPredicate(Predicate<? super Budget> budgetPredicate) {
        this.budgetPredicate = budgetPredicate;
        return this;
    }

    /**
     * Creates a {@code ModelChanges} object that can revert the current changes with respect to a reference model.
     * @param base The reference model.
     * @return a new {@code ModelChanges} object that describes the changes that can revert the current changes.
     */
    public ModelChanges revertChanges(Model base) {
        requireAllNonNull(base);

        ModelChanges revert = new ModelChanges(this.getChangeMessage());

        if (getMooLah().isPresent()) {
            revert.setMooLah(base.getMooLah());
        }

        if (getUserPrefs().isPresent()) {
            revert.setUserPrefs(base.getUserPrefs());
        }

        if (getExpensePredicate().isPresent()) {
            revert.setExpensePredicate(base.getFilteredExpensePredicate());
        }

        if (getEventPredicate().isPresent()) {
            revert.setEventPredicate(base.getFilteredEventPredicate());
        }

        if (getBudgetPredicate().isPresent()) {
            revert.setBudgetPredicate(base.getFilteredBudgetPredicate());
        }

        return revert;
    }

    /**
     * Compares two {@code Model}s for changes and keeping the first model data if there are any differences.
     * @param changeMessage The change message to be included if there are changes.
     * @param base The first model to be compared.
     * @param other The second model to be compared.
     * @return a {@code ModelChanges} that describes the changes between the two models.
     */
    public static ModelChanges compareModels(String changeMessage, Model base, Model other) {
        requireAllNonNull(changeMessage, base, other);

        ModelChanges changes = new ModelChanges(changeMessage);

        if (!base.getMooLah().equals(other.getMooLah())) {
            changes.setMooLah(base.getMooLah());
        }

        if (!base.getUserPrefs().equals(other.getUserPrefs())) {
            changes.setUserPrefs(base.getUserPrefs());
        }

        if (!base.getFilteredExpensePredicate().equals(other.getFilteredExpensePredicate())) {
            changes.setExpensePredicate(base.getFilteredExpensePredicate());
        }

        if (!base.getFilteredEventPredicate().equals(other.getFilteredEventPredicate())) {
            changes.setEventPredicate(base.getFilteredEventPredicate());
        }

        if (!base.getFilteredBudgetPredicate().equals(other.getFilteredBudgetPredicate())) {
            changes.setBudgetPredicate(base.getFilteredBudgetPredicate());
        }

        return changes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("changed:");

        if (getMooLah().isPresent()) {
            sb.append(" mooLah");
        }

        if (getUserPrefs().isPresent()) {
            sb.append(" userPrefs");
        }

        if (getExpensePredicate().isPresent()) {
            sb.append(" expensePredicate");
        }

        if (getEventPredicate().isPresent()) {
            sb.append(" eventPredicate");
        }

        if (getBudgetPredicate().isPresent()) {
            sb.append(" budgetPredicate");
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof ModelChanges)) {
            return false;
        }

        ModelChanges other = (ModelChanges) obj;
        return changeMessage.equals(other.changeMessage)
            && compareMooLahFieldOnly(other)
            && compareUserPrefsFieldOnly(other)
            && (expensePredicate == other.expensePredicate)
            && (eventPredicate == other.eventPredicate)
            && (budgetPredicate == other.budgetPredicate);
    }

    /**
     * Compares the MooLah field with the other ModelChanges given.
     * @param other the other ModelChanges to compare to.
     * @return true if both fields are null or both are equal, false otherwise.
     */
    private boolean compareMooLahFieldOnly(ModelChanges other) {
        if (getMooLah().isEmpty()) {
            return other.getMooLah().isEmpty();
        } else {
            return mooLah.equals(other.mooLah);
        }
    }

    /**
     * Compares the UserPrefs field with the other ModelChanges given.
     * @param other the other ModelChanges to compare to.
     * @return true if both fields are null or both are equal, false otherwise.
     */
    private boolean compareUserPrefsFieldOnly(ModelChanges other) {
        if (getUserPrefs().isEmpty()) {
            return other.getUserPrefs().isEmpty();
        } else {
            return userPrefs.equals(other.userPrefs);
        }
    }

}
