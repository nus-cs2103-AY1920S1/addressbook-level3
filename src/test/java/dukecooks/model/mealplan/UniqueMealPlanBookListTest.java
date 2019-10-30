package dukecooks.model.mealplan;

import static dukecooks.testutil.mealplan.TypicalMealPlans.BURGER_MP;
import static dukecooks.testutil.mealplan.TypicalMealPlans.MILO_MP;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.mealplan.components.MealPlan;
import dukecooks.model.mealplan.components.UniqueMealPlanList;
import dukecooks.model.mealplan.exceptions.DuplicateMealPlanException;
import dukecooks.model.mealplan.exceptions.MealPlanNotFoundException;
import dukecooks.testutil.Assert;
import dukecooks.testutil.mealplan.MealPlanBuilder;

public class UniqueMealPlanBookListTest {

    private final UniqueMealPlanList uniqueMealPlanList = new UniqueMealPlanList();

    @Test
    public void contains_nullMealPlan_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueMealPlanList.contains(null));
    }

    @Test
    public void contains_mealPlanNotInList_returnsFalse() {
        assertFalse(uniqueMealPlanList.contains(MILO_MP));
    }

    @Test
    public void contains_mealPlanInList_returnsTrue() {
        uniqueMealPlanList.add(MILO_MP);
        assertTrue(uniqueMealPlanList.contains(MILO_MP));
    }

    @Test
    public void contains_mealPlanWithSameIdentityFieldsInList_returnsTrue() {
        uniqueMealPlanList.add(MILO_MP);
        MealPlan editedAlice = new MealPlanBuilder(MILO_MP).withDay1(CommandTestUtil.VALID_NAME_BURGER)
                .build();
        assertTrue(uniqueMealPlanList.contains(editedAlice));
        editedAlice = new MealPlanBuilder(MILO_MP).withDay2(CommandTestUtil.VALID_NAME_BURGER)
                .build();
        assertTrue(uniqueMealPlanList.contains(editedAlice));
        editedAlice = new MealPlanBuilder(MILO_MP).withDay3(CommandTestUtil.VALID_NAME_BURGER)
                .build();
        assertTrue(uniqueMealPlanList.contains(editedAlice));
        editedAlice = new MealPlanBuilder(MILO_MP).withDay4(CommandTestUtil.VALID_NAME_BURGER)
                .build();
        assertTrue(uniqueMealPlanList.contains(editedAlice));
        editedAlice = new MealPlanBuilder(MILO_MP).withDay5(CommandTestUtil.VALID_NAME_BURGER)
                .build();
        assertTrue(uniqueMealPlanList.contains(editedAlice));
        editedAlice = new MealPlanBuilder(MILO_MP).withDay6(CommandTestUtil.VALID_NAME_BURGER)
                .build();
        assertTrue(uniqueMealPlanList.contains(editedAlice));
        editedAlice = new MealPlanBuilder(MILO_MP).withDay7(CommandTestUtil.VALID_NAME_BURGER)
                .build();
        assertTrue(uniqueMealPlanList.contains(editedAlice));
    }

    @Test
    public void add_nullMealPlan_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueMealPlanList.add(null));
    }

    @Test
    public void add_duplicateMealPlan_throwsDuplicateMealPlanException() {
        uniqueMealPlanList.add(MILO_MP);
        Assert.assertThrows(DuplicateMealPlanException.class, () -> uniqueMealPlanList.add(MILO_MP));
    }

    @Test
    public void setMealPlan_nullTargetMealPlan_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueMealPlanList.setMealPlan(null, MILO_MP));
    }

    @Test
    public void setMealPlan_nullEditedMealPlan_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueMealPlanList.setMealPlan(MILO_MP, null));
    }

    @Test
    public void setMealPlan_targetMealPlanNotInList_throwsMealPlanNotFoundException() {
        Assert.assertThrows(MealPlanNotFoundException.class, () -> uniqueMealPlanList.setMealPlan(MILO_MP, MILO_MP));
    }

    @Test
    public void setMealPlan_editedMealPlanIsSameMealPlan_success() {
        uniqueMealPlanList.add(MILO_MP);
        uniqueMealPlanList.setMealPlan(MILO_MP, MILO_MP);
        UniqueMealPlanList expectedUniqueMealPlanList = new UniqueMealPlanList();
        expectedUniqueMealPlanList.add(MILO_MP);
        assertEquals(expectedUniqueMealPlanList, uniqueMealPlanList);
    }

    @Test
    public void setMealPlan_editedMealPlanHasSameIdentity_success() {
        uniqueMealPlanList.add(MILO_MP);

        MealPlan editedAlice = new MealPlanBuilder(MILO_MP).withDay1(CommandTestUtil.VALID_NAME_BURGER)
                .build();
        uniqueMealPlanList.setMealPlan(MILO_MP, editedAlice);
        UniqueMealPlanList expectedUniqueMealPlanList = new UniqueMealPlanList();
        expectedUniqueMealPlanList.add(editedAlice);
        assertEquals(expectedUniqueMealPlanList, uniqueMealPlanList);
        uniqueMealPlanList.remove(editedAlice);

        uniqueMealPlanList.add(MILO_MP);
        editedAlice = new MealPlanBuilder(MILO_MP).withDay2(CommandTestUtil.VALID_NAME_BURGER)
                .build();
        uniqueMealPlanList.setMealPlan(MILO_MP, editedAlice);
        expectedUniqueMealPlanList = new UniqueMealPlanList();
        expectedUniqueMealPlanList.add(editedAlice);
        assertEquals(expectedUniqueMealPlanList, uniqueMealPlanList);
        uniqueMealPlanList.remove(editedAlice);

        uniqueMealPlanList.add(MILO_MP);
        editedAlice = new MealPlanBuilder(MILO_MP).withDay3(CommandTestUtil.VALID_NAME_BURGER)
                .build();
        uniqueMealPlanList.setMealPlan(MILO_MP, editedAlice);
        expectedUniqueMealPlanList = new UniqueMealPlanList();
        expectedUniqueMealPlanList.add(editedAlice);
        assertEquals(expectedUniqueMealPlanList, uniqueMealPlanList);
        uniqueMealPlanList.remove(editedAlice);

        uniqueMealPlanList.add(MILO_MP);
        editedAlice = new MealPlanBuilder(MILO_MP).withDay4(CommandTestUtil.VALID_NAME_BURGER)
                .build();
        uniqueMealPlanList.setMealPlan(MILO_MP, editedAlice);
        expectedUniqueMealPlanList = new UniqueMealPlanList();
        expectedUniqueMealPlanList.add(editedAlice);
        assertEquals(expectedUniqueMealPlanList, uniqueMealPlanList);
        uniqueMealPlanList.remove(editedAlice);

        uniqueMealPlanList.add(MILO_MP);
        editedAlice = new MealPlanBuilder(MILO_MP).withDay5(CommandTestUtil.VALID_NAME_BURGER)
                .build();
        uniqueMealPlanList.setMealPlan(MILO_MP, editedAlice);
        expectedUniqueMealPlanList = new UniqueMealPlanList();
        expectedUniqueMealPlanList.add(editedAlice);
        assertEquals(expectedUniqueMealPlanList, uniqueMealPlanList);
        uniqueMealPlanList.remove(editedAlice);

        uniqueMealPlanList.add(MILO_MP);
        editedAlice = new MealPlanBuilder(MILO_MP).withDay6(CommandTestUtil.VALID_NAME_BURGER)
                .build();
        uniqueMealPlanList.setMealPlan(MILO_MP, editedAlice);
        expectedUniqueMealPlanList = new UniqueMealPlanList();
        expectedUniqueMealPlanList.add(editedAlice);
        assertEquals(expectedUniqueMealPlanList, uniqueMealPlanList);
        uniqueMealPlanList.remove(editedAlice);

        uniqueMealPlanList.add(MILO_MP);
        editedAlice = new MealPlanBuilder(MILO_MP).withDay7(CommandTestUtil.VALID_NAME_BURGER)
                .build();
        uniqueMealPlanList.setMealPlan(MILO_MP, editedAlice);
        expectedUniqueMealPlanList = new UniqueMealPlanList();
        expectedUniqueMealPlanList.add(editedAlice);
        assertEquals(expectedUniqueMealPlanList, uniqueMealPlanList);
    }

    @Test
    public void setMealPlan_editedMealPlanHasDifferentIdentity_success() {
        uniqueMealPlanList.add(MILO_MP);
        uniqueMealPlanList.setMealPlan(MILO_MP, BURGER_MP);
        UniqueMealPlanList expectedUniqueMealPlanList = new UniqueMealPlanList();
        expectedUniqueMealPlanList.add(BURGER_MP);
        assertEquals(expectedUniqueMealPlanList, uniqueMealPlanList);
    }

    @Test
    public void setMealPlan_editedMealPlanHasNonUniqueIdentity_throwsDuplicateMealPlanException() {
        uniqueMealPlanList.add(MILO_MP);
        uniqueMealPlanList.add(BURGER_MP);
        Assert.assertThrows(DuplicateMealPlanException.class, () -> uniqueMealPlanList.setMealPlan(MILO_MP, BURGER_MP));
    }

    @Test
    public void remove_nullMealPlan_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueMealPlanList.remove(null));
    }

    @Test
    public void remove_mealPlanDoesNotExist_throwsMealPlanNotFoundException() {
        Assert.assertThrows(MealPlanNotFoundException.class, () -> uniqueMealPlanList.remove(MILO_MP));
    }

    @Test
    public void remove_existingMealPlan_removesMealPlan() {
        uniqueMealPlanList.add(MILO_MP);
        uniqueMealPlanList.remove(MILO_MP);
        UniqueMealPlanList expectedUniqueMealPlanList = new UniqueMealPlanList();
        assertEquals(expectedUniqueMealPlanList, uniqueMealPlanList);
    }

    @Test
    public void setMealPlans_nullUniqueMealPlanList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueMealPlanList
                .setMealPlans((UniqueMealPlanList) null));
    }

    @Test
    public void setMealPlans_uniqueMealPlanList_replacesOwnListWithProvidedUniqueMealPlanList() {
        uniqueMealPlanList.add(MILO_MP);
        UniqueMealPlanList expectedUniqueMealPlanList = new UniqueMealPlanList();
        expectedUniqueMealPlanList.add(BURGER_MP);
        uniqueMealPlanList.setMealPlans(expectedUniqueMealPlanList);
        assertEquals(expectedUniqueMealPlanList, uniqueMealPlanList);
    }

    @Test
    public void setMealPlans_nullList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueMealPlanList.setMealPlans((List<MealPlan>) null));
    }

    @Test
    public void setMealPlans_list_replacesOwnListWithProvidedList() {
        uniqueMealPlanList.add(MILO_MP);
        List<MealPlan> mealPlanList = Collections.singletonList(BURGER_MP);
        uniqueMealPlanList.setMealPlans(mealPlanList);
        UniqueMealPlanList expectedUniqueMealPlanList = new UniqueMealPlanList();
        expectedUniqueMealPlanList.add(BURGER_MP);
        assertEquals(expectedUniqueMealPlanList, uniqueMealPlanList);
    }

    @Test
    public void setMealPlans_listWithDuplicateMealPlans_throwsDuplicateMealPlanException() {
        List<MealPlan> listWithDuplicateMealPlans = Arrays.asList(MILO_MP, MILO_MP);
        Assert.assertThrows(DuplicateMealPlanException.class, ()
            -> uniqueMealPlanList.setMealPlans(listWithDuplicateMealPlans));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, ()
            -> uniqueMealPlanList.asUnmodifiableObservableList().remove(0));
    }
}
