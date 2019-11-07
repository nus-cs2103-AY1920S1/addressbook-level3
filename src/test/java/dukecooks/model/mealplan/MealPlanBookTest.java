package dukecooks.model.mealplan;

import static dukecooks.testutil.mealplan.TypicalMealPlans.MILO_MP;
import static dukecooks.testutil.mealplan.TypicalMealPlans.getTypicalMealPlanBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.mealplan.components.MealPlan;
import dukecooks.model.mealplan.exceptions.DuplicateMealPlanException;
import dukecooks.testutil.Assert;
import dukecooks.testutil.mealplan.MealPlanBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MealPlanBookTest {

    private final MealPlanBook mealPlanBook = new MealPlanBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), mealPlanBook.getMealPlanList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> mealPlanBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyMealPlanBook_replacesData() {
        MealPlanBook newData = getTypicalMealPlanBook();
        mealPlanBook.resetData(newData);
        assertEquals(newData, mealPlanBook);
    }

    @Test
    public void resetData_withDuplicateMealPlans_throwsDuplicateMealPlanException() {
        // Two mealPlans with the same identity fields
        MealPlan editedMiloMealPlan = new MealPlanBuilder(MILO_MP).withDay1(CommandTestUtil.VALID_INGREDIENT_BURGER)
                .build();
        List<MealPlan> newMealPlans = Arrays.asList(MILO_MP, editedMiloMealPlan);
        MealPlanBookStub newData = new MealPlanBookStub(newMealPlans);

        Assert.assertThrows(DuplicateMealPlanException.class, () -> mealPlanBook.resetData(newData));
    }

    @Test
    public void hasMealPlan_nullMealPlan_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> mealPlanBook.hasMealPlan(null));
    }

    @Test
    public void hasMealPlan_mealPlanNotInMealPlanBook_returnsFalse() {
        assertFalse(mealPlanBook.hasMealPlan(MILO_MP));
    }

    @Test
    public void hasMealPlan_mealPlanInMealPlanBook_returnsTrue() {
        mealPlanBook.addMealPlan(MILO_MP);
        assertTrue(mealPlanBook.hasMealPlan(MILO_MP));
    }

    @Test
    public void hasMealPlan_mealPlanWithSameIdentityFieldsInMealPlanBook_returnsTrue() {
        mealPlanBook.addMealPlan(MILO_MP);
        MealPlan editedMilo = new MealPlanBuilder(MILO_MP).withDay1(CommandTestUtil.VALID_INGREDIENT_BURGER)
                .build();
        assertTrue(mealPlanBook.hasMealPlan(editedMilo));
    }

    @Test
    public void getMealPlanList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> mealPlanBook.getMealPlanList().remove(0));
    }

    /**
     * A stub ReadOnlyMealPlanBook whose mealPlans list can violate interface constraints.
     */
    private static class MealPlanBookStub implements ReadOnlyMealPlanBook {
        private final ObservableList<MealPlan> mealPlans = FXCollections.observableArrayList();

        MealPlanBookStub(Collection<MealPlan> mealPlans) {
            this.mealPlans.setAll(mealPlans);
        }

        @Override
        public ObservableList<MealPlan> getMealPlanList() {
            return mealPlans;
        }
    }

    @Test
    public void testMealPlanBookHashCode() {
        MealPlanBook book1 = new MealPlanBook(getTypicalMealPlanBook());
        MealPlanBook book2 = new MealPlanBook(getTypicalMealPlanBook());

        assertEquals(book1.hashCode(), book2.hashCode());
    }
}
