package seedu.ichifund.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.ichifund.model.budget.Budget;
import seedu.ichifund.model.budget.UniqueBudgetList;
import seedu.ichifund.model.person.Person;
import seedu.ichifund.model.person.UniquePersonList;

/**
 * Wraps all data at the fund book level
 * Duplicates are not allowed (by .isSamePerson and .isSameBudget comparison)
 */
public class FundBook implements ReadOnlyFundBook {

    private final UniquePersonList persons;
    private final UniqueBudgetList budgets;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        budgets = new UniqueBudgetList();
    }

    public FundBook() {}

    /**
     * Creates an FundBook using the Persons in the {@code toBeCopied}
     */
    public FundBook(ReadOnlyFundBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the budget list with {@code budgets}.
     * {@code budgets} must not contain duplicate budgets.
     */
    public void setBudgets(List<Budget> budgets) {
        this.budgets.setBudgets(budgets);
    }

    /**
     * Resets the existing data of this {@code FundBook} with {@code newData}.
     */
    public void resetData(ReadOnlyFundBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setBudgets(newData.getBudgetList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the fund book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the fund book.
     * The person must not already exist in the fund book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the fund book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the fund book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code FundBook}.
     * {@code key} must exist in the fund book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// budget-level operations

    /**
     * Returns true if a budget with the same identity as {@code budget} exists in the fund book.
     */
    public boolean hasBudget(Budget budget) {
        requireNonNull(budget);
        return budgets.contains(budget);
    }

    /**
     * Adds a budget to the fund book.
     * The budget must not already exist in the fund book.
     */
    public void addBudget(Budget budget) {
        budgets.add(budget);
    }

    /**
     * Replaces the given budget {@code target} in the list with {@code editedBudget}.
     * {@code target} must exist in the fund book.
     * The budget identity of {@code editedBudget} must not be the same as another existing budget in the fund book.
     */
    public void setBudget(Budget target, Budget editedBudget) {
        requireNonNull(editedBudget);

        budgets.setBudget(target, editedBudget);
    }

    /**
     * Removes budget {@code key} from this {@code FundBook}.
     * {@code key} must exist in the fund book.
     */
    public void removeBudget(Budget key) {
        budgets.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Budget> getBudgetList() {
        return budgets.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FundBook // instanceof handles nulls
                && persons.equals(((FundBook) other).persons)
                && budgets.equals(((FundBook) other).budgets));
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, budgets);
    }
}
