package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.CategoryNotFoundException;
import seedu.address.model.person.exceptions.DuplicateCategoryException;

/**
 * A list of categories that enforces uniqueness between its elements and does not
 * allow nulls.
 */
public class CategoryList {

    public static final String MESSAGE_CONSTRAINTS_IN_LIST =
            "The category is already in the existing list of categories. ";

    public static final String MESSAGE_CONSTRAINTS_NOT_IN_LIST =
            "The category is not in the existing list of categories. ";

    public static final String LIST_IS_EMPTY =
            "Unable to remove category from the category empty list!. ";
    /*
     * The first character of the address must not be a whitespace, otherwise " " (a
     * blank string) becomes a valid input.
     */
    private final ObservableList<Category> internalListForIncome = FXCollections.observableArrayList();
    private final ObservableList<Category> internalListForOtherEntries = FXCollections.observableArrayList();


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
     * Returns true if the new Category to be added and created is not in the CategoryList.
     */
    public boolean isValidAndNotInList(Category category) {
        ObservableList<Category> typeOfCategory = determineWhichList(category.categoryType);
        return !typeOfCategory.stream().anyMatch(t -> t.toString().equalsIgnoreCase(category.categoryName));
    }

    /**
     * Returns true if the Category is in the CategoryList.
     */
    public boolean contains(Category toCheck) {
        requireNonNull(toCheck);
        ObservableList<Category> typeOfCategory = determineWhichList(toCheck.categoryType);
        return typeOfCategory.stream().anyMatch(toCheck::equals);
    }

    public void setEntries(List<Category> replacementExpenseList, List<Category> replacementIncomeList) {
        requireNonNull(replacementExpenseList);
        requireNonNull(replacementIncomeList);
        internalListForOtherEntries.setAll(replacementExpenseList);
        internalListForIncome.setAll(replacementIncomeList);
    }

    public ObservableList<Category> getInternalListForIncome() {
        return internalListForIncome;
    }

    public ObservableList<Category> getInternalListForOtherEntries() {
        return internalListForOtherEntries;
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setCategory(Category target, Category editedCategory) {
        requireAllNonNull(target, editedCategory);
        ObservableList internalList = determineWhichList(target.categoryType);
        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new CategoryNotFoundException();
        }

        if (!target.equals(editedCategory) && contains(editedCategory)) {
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
        checkArgument(isValidAndNotInList(category), MESSAGE_CONSTRAINTS_IN_LIST);
        ObservableList<Category> typeOfCategory;
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
        checkArgument(!isValidAndNotInList(toRemove), MESSAGE_CONSTRAINTS_NOT_IN_LIST);
        ObservableList internalList = determineWhichList(toRemove.categoryType);

        if (!internalList.remove(toRemove)) {
            throw new CategoryNotFoundException();
        }
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean categoriesAreUnique(List<Category> categoryToCheck) {
        for (int i = 0; i < categoryToCheck.size() - 1; i++) {
            for (int j = i + 1; j < categoryToCheck.size(); j++) {
                if (categoryToCheck.get(i).isSameCategory(categoryToCheck.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
