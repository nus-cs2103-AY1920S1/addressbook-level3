package seedu.billboard.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.expense.ExpenseList;
import seedu.billboard.model.recurrence.Recurrence;
import seedu.billboard.model.recurrence.RecurrenceList;
import seedu.billboard.model.tag.Tag;
import seedu.billboard.model.tag.TagCountManager;
import seedu.billboard.model.tag.UniqueTagList;

/**
 * Wraps all data at the Billboard level
 * Duplicates are allowed
 */
public class Billboard implements ReadOnlyBillboard {

    private final ExpenseList expenses;
    private final RecurrenceList recurrences;
    private final UniqueTagList tags;
    private final TagCountManager count;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        expenses = new ExpenseList();
        recurrences = new RecurrenceList();
        tags = new UniqueTagList();
        count = new TagCountManager();
    }

    public Billboard() {}

    /**
     * Creates an Billboard using the Persons in the {@code toBeCopied}
     */
    public Billboard(ReadOnlyBillboard toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    public Billboard(ExpenseList expenses, RecurrenceList recurrences, UniqueTagList tags, TagCountManager count) {
        setExpenses(expenses.asUnmodifiableObservableList());
        setRecurrences(recurrences.asUnmodifiableList());
        setUniqueTagList(tags.getTagList());
        setCountManager(count.getCountMap());
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the expense list with {@code expense}.
     * {@code expense} must not contain duplicate expense.
     */
    public void setExpenses(List<Expense> expense) {
        this.expenses.setExpenses(expense);
    }

    public void setRecurrences(List<Recurrence> recurrences) {
        this.recurrences.setRecurrences(recurrences);
    }

    public void setRecurrences(RecurrenceList recurrences) {
        this.recurrences.setRecurrences(recurrences);
    }

    public void setUniqueTagList(Map<String, Tag> tagList) {
        this.tags.setTagList(tagList);
    }

    public void setCountManager(Map<Tag, Integer> count) {
        this.count.setCountMap(count);
    }


    /**
     * Resets the existing data of this {@code Billboard} with {@code newData}.
     */
    public void resetData(ReadOnlyBillboard newData) {
        requireNonNull(newData);

        setExpenses(newData.getExpenses());
        setRecurrences(newData.getRecurrences());
        setUniqueTagList(newData.getUniqueTagList());
        setCountManager(newData.getCountManager());
    }

    @Override
    public List<Expense> filterArchiveExpenses() {
        return expenses.asUnmodifiableObservableList()
                .stream().filter(Expense::isArchived).collect(Collectors.toList());
    }

    @Override
    public ReadOnlyBillboard removeArchiveExpenses() {
        List<Expense> nonArchiveExpenses = expenses.asUnmodifiableObservableList()
                .stream().filter(x -> !x.isArchived()).collect(Collectors.toList());
        Billboard billboard = new Billboard();
        billboard.setExpenses(nonArchiveExpenses);
        billboard.setRecurrences(recurrences);
        billboard.setCountManager(count.getCountMap());
        billboard.setUniqueTagList(tags.getTagList());
        return billboard;
    }

    public Billboard getClone() {
        return new Billboard(expenses.getClone(), recurrences.getClone(), tags.getClone(), count.getClone());
    }

    public void setBillboard(Billboard billboard) {
        setExpenses(billboard.getExpenses());
        setUniqueTagList(billboard.getTags().getTagList());
        setCountManager(billboard.getCountManager());
    }

    public UniqueTagList getTags() {
        return tags;
    }

    //// expense-level operations

    /**
     * Returns true if a expense with the same identity as {@code expense} exists in the billboard.
     */
    public boolean hasExpense(Expense expense) {
        requireNonNull(expense);
        return expenses.contains(expense);
    }

    /**
     * Adds an expense to the billboard.
     * The expense must not already exist in the billboard.
     */
    public void addExpense(Expense p) {
        expenses.add(p);
    }

    /**
     * Replaces the given expense {@code target} in the list with {@code editedExpense}.
     * {@code target} must exist in the billboard.
     * The expense identity of {@code editedExpense} must not be the same as another
     * existing expense in the billboard.
     */
    public void setExpense(Expense target, Expense editedExpense) {
        requireNonNull(editedExpense);

        expenses.setExpense(target, editedExpense);
    }

    /**
     * Removes {@code key} from this {@code Billboard}.
     * {@code key} must exist in the billboard.
     */
    public void removeExpense(Expense key) {
        expenses.remove(key);
    }

    //// Recurrence methods

    public RecurrenceList getRecurrences() {
        return recurrences;
    }

    /**
     * Returns true if a recurrence with the same identity as {@code expense} exists in the billboard.
     */
    public boolean hasRecurrence(Recurrence recurrence) {
        requireNonNull(recurrence);
        return recurrences.contains(recurrence);
    }

    /**
     * Replaces the given recurrence {@code target} in the list with {@code editedRecurrence}.
     * {@code target} must exist in the billboard.
     * The expense identity of {@code editedReccurence} must not be the same as another
     * existing recurrence in the billboard.
     */
    public void setRecurrence(Recurrence target, Recurrence editedRecurrence) {
        requireNonNull(editedRecurrence);
        recurrences.setRecurrence(target, editedRecurrence);
    }

    /**
     * Adds an recurrence to the billboard.
     * The recurrence must not already exist in the billboard.
     */
    public void addRecurrence(Recurrence recurrence) {
        requireNonNull(recurrence);
        recurrences.add(recurrence);
    }

    /**
     * Adds an recurrence to the billboard.
     * The recurrence must not already exist in the billboard.
     */
    public void addRecurrence(List<Recurrence> recurrences) {
        requireNonNull(recurrences);
        for (Recurrence r : recurrences) {
            recurrences.add(r);
        }
    }

    /**
     * Removes {@code key} from this {@code Billboard}.
     * {@code key} must exist in the billboard.
     */
    public void removeRecurrence(Recurrence key) {
        requireNonNull(key);

        recurrences.remove(key);
    }

    /**
     * Removes recurrence at {@code index} from this {@code Billboard}.
     * Recurrence at {@code index} must exist in the billboard.
     */
    public Recurrence removeRecurrence(int index) {
        requireNonNull(index);

        return recurrences.remove(index);
    }

    //// Tag methods

    /**
     * Retrieves tags to be added to an expense.
     */
    public Set<Tag> retrieveTags(List<String> toRetrieve) {
        return tags.retrieveTags(toRetrieve);
    }

    /**
     * Increments count of tags which will be added to an expense.
     */
    public void incrementCount(Set<Tag> toIncrement) {
        count.incrementAllCount(toIncrement);
    }

    /**
     * Decreases count of tags removed from an expense.
     * Also removes tags whose count is 0 from the unique tag list.
     */
    public void decreaseCount(Set<Tag> toDecrease) {
        count.decreaseAllCount(toDecrease);
        List<Tag> toRemove = count.removeZeroCount();
        tags.removeAll(toRemove);
    }

    /**
     * Gets a list of unique tag names.
     */
    public List<String> getTagNames() {
        return tags.getTagNames();
    }

    //// util methods

    @Override
    public String toString() {
        return expenses.asUnmodifiableObservableList().size() + " expenses";
        // TODO: refine later
    }

    @Override
    public ObservableList<Expense> getExpenses() {
        return expenses.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Billboard // instanceof handles nulls
                && expenses.equals(((Billboard) other).expenses));
    }

    @Override
    public Map<String, Tag> getUniqueTagList() {
        return tags.getTagList();
    }

    @Override
    public Map<Tag, Integer> getCountManager() {
        return count.getCountMap();
    }

    @Override
    public int hashCode() {
        return expenses.hashCode();
    }
}
