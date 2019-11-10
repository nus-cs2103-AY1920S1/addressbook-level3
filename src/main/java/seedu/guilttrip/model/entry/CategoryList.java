package seedu.guilttrip.model.entry;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.guilttrip.model.entry.exceptions.CategoryNotFoundException;
import seedu.guilttrip.model.entry.exceptions.DuplicateCategoryException;

/**
 * A list of categories that enforces uniqueness between its elements and does not
 * allow nulls.
 */
public class CategoryList {

    public static final String MESSAGE_CONSTRAINTS_IN_LIST =
            "The category is already in the existing " + "%1$s" + " category list";

    public static final String MESSAGE_CONSTRAINTS_NOT_IN_LIST =
            "The category is not in the existing " + "%1$s" + " category list";

    public static final String LIST_IS_EMPTY =
            "Unable to remove category from the category empty list!. ";
    /*
     * The first character of the guilttrip must not be a whitespace, otherwise " " (a
     * blank string) becomes a valid input.
     */
    private final ObservableList<Category> internalListForIncome = FXCollections.observableArrayList();
    private final ObservableList<Category> internalListForOtherEntries = FXCollections.observableArrayList();
    private final ObservableList<Category> internalUnmodifiableListForIncome =
            FXCollections.unmodifiableObservableList(internalListForIncome);
    private final ObservableList<Category> internalUnmodifiableListForOtherEntries =
            FXCollections.unmodifiableObservableList(internalListForOtherEntries);
    /**
     * Determines whether the category belongs to the expense list or the income category list.
     */
    public ObservableList<Category> determineWhichList(String input) {
        ObservableList<Category> typeOfCategory;
        if (input.equalsIgnoreCase("Income")) {
            typeOfCategory = internalListForIncome;
        } else {
            typeOfCategory = internalListForOtherEntries;
        }
        return typeOfCategory;
    }

    /**
     * Returns true if the Category is in the CategoryList.
     */
    public boolean contains(Category toCheck) {
        requireNonNull(toCheck);
        ObservableList<Category> typeOfCategory = determineWhichList(toCheck.categoryType);
        return typeOfCategory.stream().anyMatch(toCheck::equals);
    }

    public void setCategories(List<Category> replacementExpenseList, List<Category> replacementIncomeList) {
        requireNonNull(replacementExpenseList);
        requireNonNull(replacementIncomeList);
        this.internalListForOtherEntries.clear();
        this.internalListForIncome.clear();
        replacementExpenseList.stream().forEach(t -> add(t));
        replacementIncomeList.stream().forEach(t -> add(t));
    }

    public ObservableList<Category> getInternalListForIncome() {
        return internalUnmodifiableListForIncome;
    }

    public ObservableList<Category> getInternalListForOtherEntries() {
        return internalUnmodifiableListForOtherEntries;
    }

    /**
     * Replaces the entry {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The entry identity of {@code editedPerson} must not be the same as another existing entry in the list.
     */
    public void setCategory(Category target, Category editedCategory) {
        requireAllNonNull(target, editedCategory);
        ObservableList internalList = determineWhichList(target.categoryType);
        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new CategoryNotFoundException();
        }

        if (target.equals(editedCategory) && contains(editedCategory)) {
            throw new DuplicateCategoryException();
        }

        internalList.set(index, editedCategory);
    }

    /**
     * Adds a Category to the list.
     * The Category must not exist in the list.
     */
    public void add(Category category) {
        requireNonNull(category);
        ObservableList<Category> typeOfCategory;
        if (contains(category)) {
            throw new DuplicateCategoryException();
        }

        if (category.categoryType.equalsIgnoreCase("Income")) {
            typeOfCategory = internalListForIncome;
        } else {
            typeOfCategory = internalListForOtherEntries;
        }
        typeOfCategory.add(category);
    }

    /**
     * Removes the equivalent Category from the list.
     * The Category must exist in the list.
     */
    public void remove(Category toRemove) {
        requireNonNull(toRemove);
        //checks if it is in list. Also works for cases where List has 0 categories.
        ObservableList internalList = determineWhichList(toRemove.categoryType);

        if (!internalList.remove(toRemove)) {
            throw new CategoryNotFoundException();
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CategoryList // instanceof handles nulls
                && ((CategoryList) other).getInternalListForIncome().equals(internalListForIncome) // state check
                && ((CategoryList) other).getInternalListForOtherEntries().equals(internalListForOtherEntries));
    }

    @Override
    public int hashCode() {
        return internalListForIncome.hashCode() + internalListForOtherEntries.hashCode();
    }
}
