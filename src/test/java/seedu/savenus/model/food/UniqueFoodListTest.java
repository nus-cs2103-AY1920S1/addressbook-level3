package seedu.savenus.model.food;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.savenus.testutil.Assert.assertThrows;
import static seedu.savenus.testutil.TypicalFood.ALICE;
import static seedu.savenus.testutil.TypicalFood.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.savenus.model.food.exceptions.DuplicateFoodException;
import seedu.savenus.model.food.exceptions.FoodNotFoundException;
import seedu.savenus.testutil.FoodBuilder;

public class UniqueFoodListTest {

    private final UniqueFoodList uniqueFoodList = new UniqueFoodList();

    @Test
    public void contains_nullfood_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFoodList.contains(null));
    }

    @Test
    public void contains_foodNotInList_returnsFalse() {
        assertFalse(uniqueFoodList.contains(ALICE));
    }

    @Test
    public void contains_foodInList_returnsTrue() {
        uniqueFoodList.add(ALICE);
        assertTrue(uniqueFoodList.contains(ALICE));
    }

    @Test
    public void contains_foodWithSameIdentityFieldsInList_returnsTrue() {
        uniqueFoodList.add(ALICE);
        Food editedAlice = new FoodBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueFoodList.contains(editedAlice));
    }

    @Test
    public void add_nullfood_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFoodList.add(null));
    }

    @Test
    public void add_duplicatefood_throwsDuplicatefoodException() {
        uniqueFoodList.add(ALICE);
        assertThrows(DuplicateFoodException.class, () -> uniqueFoodList.add(ALICE));
    }

    @Test
    public void setFood_nullTargetfood_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFoodList.setFood(null, ALICE));
    }

    @Test
    public void setFood_nullEditedfood_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFoodList.setFood(ALICE, null));
    }

    @Test
    public void setFood_targetfoodNotInList_throwsfoodNotFoundException() {
        assertThrows(FoodNotFoundException.class, () -> uniqueFoodList.setFood(ALICE, ALICE));
    }

    @Test
    public void setFood_editedfoodisSameFood_success() {
        uniqueFoodList.add(ALICE);
        uniqueFoodList.setFood(ALICE, ALICE);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        expectedUniqueFoodList.add(ALICE);
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setFood_editedfoodHasSameIdentity_success() {
        uniqueFoodList.add(ALICE);
        Food editedAlice = new FoodBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueFoodList.setFood(ALICE, editedAlice);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        expectedUniqueFoodList.add(editedAlice);
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setFood_editedfoodHasDifferentIdentity_success() {
        uniqueFoodList.add(ALICE);
        uniqueFoodList.setFood(ALICE, BOB);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        expectedUniqueFoodList.add(BOB);
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setFood_editedfoodHasNonUniqueIdentity_throwsDuplicatefoodException() {
        uniqueFoodList.add(ALICE);
        uniqueFoodList.add(BOB);
        assertThrows(DuplicateFoodException.class, () -> uniqueFoodList.setFood(ALICE, BOB));
    }

    @Test
    public void remove_nullfood_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFoodList.remove(null));
    }

    @Test
    public void remove_foodDoesNotExist_throwsfoodNotFoundException() {
        assertThrows(FoodNotFoundException.class, () -> uniqueFoodList.remove(ALICE));
    }

    @Test
    public void remove_existingfood_removesfood() {
        uniqueFoodList.add(ALICE);
        uniqueFoodList.remove(ALICE);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setFoods_nullUniqueFoodList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFoodList.setFoods((UniqueFoodList) null));
    }

    @Test
    public void setFoods_uniqueFoodList_replacesOwnListWithProvidedUniqueFoodList() {
        uniqueFoodList.add(ALICE);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        expectedUniqueFoodList.add(BOB);
        uniqueFoodList.setFoods(expectedUniqueFoodList);
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setFoods_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFoodList.setFoods((List<Food>) null));
    }

    @Test
    public void setFoods_list_replacesOwnListWithProvidedList() {
        uniqueFoodList.add(ALICE);
        List<Food> foodList = Collections.singletonList(BOB);
        uniqueFoodList.setFoods(foodList);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        expectedUniqueFoodList.add(BOB);
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setFoods_listWithDuplicatefoods_throwsDuplicatefoodException() {
        List<Food> listWithDuplicateFoods = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateFoodException.class, () -> uniqueFoodList.setFoods(listWithDuplicateFoods));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueFoodList.asUnmodifiableObservableList().remove(0));
    }
}
