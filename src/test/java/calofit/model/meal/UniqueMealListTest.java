package calofit.model.meal;

import calofit.logic.commands.CommandTestUtil;
import calofit.model.meal.exceptions.DuplicateMealException;
import calofit.model.meal.exceptions.MealNotFoundException;
import calofit.testutil.Assert;
import calofit.testutil.MealBuilder;
import calofit.testutil.TypicalMeals;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UniqueMealListTest {

    private final UniqueMealList uniqueMealList = new UniqueMealList();

    @Test
    public void contains_nullMeal_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueMealList.contains(null));
    }

    @Test
    public void contains_mealNotInList_returnsFalse() {
        assertFalse(uniqueMealList.contains(TypicalMeals.ALICE));
    }

    @Test
    public void contains_mealInList_returnsTrue() {
        uniqueMealList.add(TypicalMeals.ALICE);
        assertTrue(uniqueMealList.contains(TypicalMeals.ALICE));
    }

    @Test
    public void contains_mealWithSameIdentityFieldsInList_returnsTrue() {
        uniqueMealList.add(TypicalMeals.ALICE);
        Meal editedAlice = new MealBuilder(TypicalMeals.ALICE).withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueMealList.contains(editedAlice));
    }

    @Test
    public void add_nullMeal_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueMealList.add(null));
    }

    @Test
    public void add_duplicateMeal_throwsDuplicateMealException() {
        uniqueMealList.add(TypicalMeals.ALICE);
        Assert.assertThrows(DuplicateMealException.class, () -> uniqueMealList.add(TypicalMeals.ALICE));
    }

    @Test
    public void setMeal_nullTargetMeal_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueMealList.setMeal(null, TypicalMeals.ALICE));
    }

    @Test
    public void setMeal_nullEditedMeal_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueMealList.setMeal(TypicalMeals.ALICE, null));
    }

    @Test
    public void setMeal_targetMealNotInList_throwsMealNotFoundException() {
        Assert.assertThrows(MealNotFoundException.class, () -> uniqueMealList.setMeal(TypicalMeals.ALICE, TypicalMeals.ALICE));
    }

    @Test
    public void setMeal_editedMealIsSameMeal_success() {
        uniqueMealList.add(TypicalMeals.ALICE);
        uniqueMealList.setMeal(TypicalMeals.ALICE, TypicalMeals.ALICE);
        UniqueMealList expectedUniqueMealList = new UniqueMealList();
        expectedUniqueMealList.add(TypicalMeals.ALICE);
        assertEquals(expectedUniqueMealList, uniqueMealList);
    }

    @Test
    public void setMeal_editedMealHasSameIdentity_success() {
        uniqueMealList.add(TypicalMeals.ALICE);
        Meal editedAlice = new MealBuilder(TypicalMeals.ALICE).withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        uniqueMealList.setMeal(TypicalMeals.ALICE, editedAlice);
        UniqueMealList expectedUniqueMealList = new UniqueMealList();
        expectedUniqueMealList.add(editedAlice);
        assertEquals(expectedUniqueMealList, uniqueMealList);
    }

    @Test
    public void setMeal_editedMealHasDifferentIdentity_success() {
        uniqueMealList.add(TypicalMeals.ALICE);
        uniqueMealList.setMeal(TypicalMeals.ALICE, TypicalMeals.BOB);
        UniqueMealList expectedUniqueMealList = new UniqueMealList();
        expectedUniqueMealList.add(TypicalMeals.BOB);
        assertEquals(expectedUniqueMealList, uniqueMealList);
    }

    @Test
    public void setMeal_editedMealHasNonUniqueIdentity_throwsDuplicateMealException() {
        uniqueMealList.add(TypicalMeals.ALICE);
        uniqueMealList.add(TypicalMeals.BOB);
        Assert.assertThrows(DuplicateMealException.class, () -> uniqueMealList.setMeal(TypicalMeals.ALICE, TypicalMeals.BOB));
    }

    @Test
    public void remove_nullMeal_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueMealList.remove(null));
    }

    @Test
    public void remove_mealDoesNotExist_throwsMealNotFoundException() {
        Assert.assertThrows(MealNotFoundException.class, () -> uniqueMealList.remove(TypicalMeals.ALICE));
    }

    @Test
    public void remove_existingMeal_removesMeal() {
        uniqueMealList.add(TypicalMeals.ALICE);
        uniqueMealList.remove(TypicalMeals.ALICE);
        UniqueMealList expectedUniqueMealList = new UniqueMealList();
        assertEquals(expectedUniqueMealList, uniqueMealList);
    }

    @Test
    public void setMeals_nullUniqueMealList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueMealList.setMeals((UniqueMealList) null));
    }

    @Test
    public void setMeals_uniqueMealList_replacesOwnListWithProvidedUniqueMealList() {
        uniqueMealList.add(TypicalMeals.ALICE);
        UniqueMealList expectedUniqueMealList = new UniqueMealList();
        expectedUniqueMealList.add(TypicalMeals.BOB);
        uniqueMealList.setMeals(expectedUniqueMealList);
        assertEquals(expectedUniqueMealList, uniqueMealList);
    }

    @Test
    public void setMeals_nullList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueMealList.setMeals((List<Meal>) null));
    }

    @Test
    public void setMeals_list_replacesOwnListWithProvidedList() {
        uniqueMealList.add(TypicalMeals.ALICE);
        List<Meal> mealList = Collections.singletonList(TypicalMeals.BOB);
        uniqueMealList.setMeals(mealList);
        UniqueMealList expectedUniqueMealList = new UniqueMealList();
        expectedUniqueMealList.add(TypicalMeals.BOB);
        assertEquals(expectedUniqueMealList, uniqueMealList);
    }

    @Test
    public void setMeals_listWithDuplicateMeals_throwsDuplicateMealException() {
        List<Meal> listWithDuplicateMeals = Arrays.asList(TypicalMeals.ALICE, TypicalMeals.ALICE);
        Assert.assertThrows(DuplicateMealException.class, () -> uniqueMealList.setMeals(listWithDuplicateMeals));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, ()
            -> uniqueMealList.asUnmodifiableObservableList().remove(0));
    }
}
