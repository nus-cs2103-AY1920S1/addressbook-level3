package seedu.address.model.person;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.CategoryNotFoundException;
import seedu.address.model.person.exceptions.DuplicateCategoryException;
import seedu.address.model.person.exceptions.DuplicateEntryException;
import seedu.address.model.person.exceptions.EntryNotFoundException;

public class CategoryList {

    public static final String MESSAGE_CONSTRAINTS_IN_LIST =
            "The category is already in the existing list of categories. ";

    /*
     * The first character of the address must not be a whitespace, otherwise " " (a
     * blank string) becomes a valid input.
     */
    private final ObservableList<Category> internalListForIncome = FXCollections.observableArrayList();
    private final ObservableList<Category> internalListForOtherEntries = FXCollections.observableArrayList();


    public ObservableList<Category> determineWhichList(String input) {
        ObservableList<Category> typeOfCategory;
        if(input.equalsIgnoreCase("Income")) {
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

    public boolean contains(Category toCheck) {
        requireNonNull(toCheck);
        ObservableList<Category> typeOfCategory = determineWhichList(toCheck.categoryType);
        return typeOfCategory.stream().anyMatch(toCheck::equals);
    }

    public void setEntries(List<Category> replacementExpenseList, List<Category> replacementIncomeList) {
        requireNonNull(replacementExpenseList);
        requireNonNull(replacementIncomeList);
        if (!categoriesAreUnique(replacementExpenseList) || !categoriesAreUnique(replacementIncomeList)) {
            throw new DuplicateCategoryException();
        }
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

    public void add(Category category) {
        requireNonNull(category);
        checkArgument(isValidAndNotInList(category), MESSAGE_CONSTRAINTS_IN_LIST);
        ObservableList<Category> typeOfCategory;
        if(category.categoryType.equalsIgnoreCase("Income")) {
            typeOfCategory = internalListForIncome;
        } else {
            typeOfCategory = internalListForOtherEntries;
        }
        typeOfCategory.add(category);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Category toRemove) {
        requireNonNull(toRemove);
        //checks if it is in list
        checkArgument(!isValidAndNotInList(toRemove));
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
