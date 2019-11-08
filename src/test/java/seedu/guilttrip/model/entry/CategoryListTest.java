package seedu.guilttrip.model.entry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.guilttrip.testutil.Assert.assertThrows;
import static seedu.guilttrip.testutil.TypicalEntries.CATEGORY_BUSINESS;
import static seedu.guilttrip.testutil.TypicalEntries.CATEGORY_FOOD;
import static seedu.guilttrip.testutil.TypicalEntries.CATEGORY_SHOPPING;
import static seedu.guilttrip.testutil.TypicalEntries.CATEGORY_STOCKS;
import static seedu.guilttrip.testutil.TypicalEntries.CATEGORY_TRAVEL;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.guilttrip.model.entry.exceptions.CategoryNotFoundException;
import seedu.guilttrip.model.entry.exceptions.DuplicateCategoryException;

public class CategoryListTest {

    private final CategoryList categoryList = new CategoryList();

    @Test
    public void contains_nullCategory_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> categoryList.contains(null));
    }

    @Test
    public void contains_expenseCategoryNotInList_returnsFalse() {
        assertFalse(categoryList.contains(CATEGORY_FOOD));
    }

    @Test
    public void contains_expenseCategoryInList_returnsTrue() {
        //Add Expense
        categoryList.add(CATEGORY_FOOD);
        assertTrue(categoryList.contains(CATEGORY_FOOD));

        //Add Income
        categoryList.add(CATEGORY_BUSINESS);
        assertTrue(categoryList.contains(CATEGORY_BUSINESS));
    }

    @Test
    public void add_nullCategory_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> categoryList.add(null));
    }

    @Test
    public void add_duplicateCategory_throwsIllegalArgumentException() {
        categoryList.add(CATEGORY_FOOD);
        assertThrows(DuplicateCategoryException.class, () -> categoryList.add(CATEGORY_FOOD));
    }

    @Test
    public void add_validCategory_success() {
        categoryList.add(CATEGORY_FOOD);
        CategoryList expectedCategoryList = new CategoryList();
        expectedCategoryList.add(CATEGORY_FOOD);
        assertEquals(expectedCategoryList, categoryList);
    }

    @Test
    public void setCategory_nullTargetCategory_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> categoryList.setCategory(null, CATEGORY_FOOD));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> categoryList.setCategory(CATEGORY_FOOD, null));
    }

    @Test
    public void setCategory_editedCategoryIsSameCategory_throwsIllegalArgumentException() {
        categoryList.add(CATEGORY_FOOD);
        assertThrows(DuplicateCategoryException.class, () -> categoryList.setCategory(CATEGORY_FOOD, CATEGORY_FOOD));
    }

    @Test
    public void setCategory_editedCategoryHasDifferentName_success() {
        //Add Expense Category
        categoryList.add(CATEGORY_FOOD);
        categoryList.setCategory(CATEGORY_FOOD, CATEGORY_TRAVEL);
        CategoryList expectedUniqueCategoryList = new CategoryList();
        expectedUniqueCategoryList.add(CATEGORY_TRAVEL);
        assertEquals(expectedUniqueCategoryList, categoryList);

        //Add Income Category with existing Expense
        categoryList.add(CATEGORY_BUSINESS);
        categoryList.setCategory(CATEGORY_BUSINESS, CATEGORY_STOCKS);
        expectedUniqueCategoryList.add(CATEGORY_STOCKS);
        assertEquals(expectedUniqueCategoryList, categoryList);

    }

    @Test
    public void remove_nullCategory_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> categoryList.remove(null));
    }

    @Test
    public void remove_categoryDoesNotExist_throwsIllegalArgumentException() {
        assertThrows(CategoryNotFoundException.class, () -> categoryList.remove(CATEGORY_FOOD));
    }

    @Test
    public void remove_existingCategory_removesCategory() {
        //remove Income Category
        categoryList.add(CATEGORY_STOCKS);
        categoryList.remove(CATEGORY_STOCKS);
        CategoryList expectedCategoryList = new CategoryList();
        assertEquals(expectedCategoryList, categoryList);

        //remove Expense Category
        categoryList.add(CATEGORY_BUSINESS);
        categoryList.remove(CATEGORY_BUSINESS);
        assertEquals(expectedCategoryList, categoryList);
    }

    @Test
    public void setCategory_nullCategoryList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> categoryList.setCategories(null,
                null));
    }

    @Test
    public void setCategories_categoryList_replacesOwnListWithProvidedCategoryList() {
        categoryList.add(CATEGORY_BUSINESS);
        categoryList.add(CATEGORY_FOOD);
        CategoryList expectedUniqueCategoryList = new CategoryList();
        expectedUniqueCategoryList.add(CATEGORY_STOCKS);
        expectedUniqueCategoryList.add(CATEGORY_SHOPPING);
        categoryList.setCategories(expectedUniqueCategoryList.getInternalListForOtherEntries(),
                expectedUniqueCategoryList.getInternalListForIncome());
        assertEquals(categoryList, expectedUniqueCategoryList);
    }

    @Test
    public void setCategoryList_listWithDuplicateCategories_throwsDuplicateCategoryException() {
        List<Category> listWithDuplicateExpenseCategories = Arrays.asList(CATEGORY_FOOD, CATEGORY_FOOD);
        List<Category> listWithDuplicateIncomeCategories = Arrays.asList(CATEGORY_BUSINESS, CATEGORY_BUSINESS);
        List<Category> listWithNoDuplicateIncomeCategories = Arrays.asList(CATEGORY_BUSINESS, CATEGORY_STOCKS);

        assertThrows(DuplicateCategoryException.class, () -> categoryList
                .setCategories(listWithDuplicateExpenseCategories, listWithNoDuplicateIncomeCategories));
        assertThrows(DuplicateCategoryException.class, () -> categoryList
                .setCategories(listWithDuplicateExpenseCategories, listWithDuplicateIncomeCategories));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> categoryList.getInternalListForOtherEntries().remove(0));
    }
}

