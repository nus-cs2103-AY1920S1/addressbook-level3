package seedu.guilttrip.model.entry;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.model.entry.exceptions.CategoryNotFoundException;
import seedu.guilttrip.model.entry.exceptions.DuplicateCategoryException;
import seedu.guilttrip.model.util.CategoryType;

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

    private static final Logger logger = LogsCenter.getLogger(CategoryList.class);

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
    public ObservableList<Category> determineWhichList(CategoryType input) {
        ObservableList<Category> typeOfCategory;
        if (input.equals(CategoryType.INCOME)) {
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
        ObservableList<Category> typeOfCategory = determineWhichList(toCheck.getCategoryType());
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
        ObservableList<Category> internalList = determineWhichList(target.getCategoryType());
        int index = internalList.indexOf(target);
        if (index == -1) {
            logger.info("Category isn't contained in GuiltTrip.");
            throw new CategoryNotFoundException();
        }

        if (target.equals(editedCategory) && contains(editedCategory)) {
            logger.info("Category is a duplicate in GuiltTrip.It already exists.");
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

        if (category.getCategoryType().getCatType().equalsIgnoreCase("Income")) {
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
        ObservableList internalList = determineWhichList(toRemove.getCategoryType());

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
