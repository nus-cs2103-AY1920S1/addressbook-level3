package seedu.savenus.model.food;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_TAG_CHICKEN;
import static seedu.savenus.testutil.Assert.assertThrows;
import static seedu.savenus.testutil.TypicalMenu.CARBONARA;
import static seedu.savenus.testutil.TypicalMenu.NASI_LEMAK;

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
        assertFalse(uniqueFoodList.contains(CARBONARA));
    }

    @Test
    public void contains_foodInList_returnsTrue() {
        uniqueFoodList.add(CARBONARA);
        assertTrue(uniqueFoodList.contains(CARBONARA));
    }

    @Test
    public void contains_foodWithSameIdentityFieldsInList_returnsTrue() {
        uniqueFoodList.add(CARBONARA);
        Food editedAlice = new FoodBuilder(CARBONARA).withTags(VALID_TAG_CHICKEN)
                .build();
        assertTrue(uniqueFoodList.contains(editedAlice));
    }

    @Test
    public void add_nullfood_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFoodList.add(null));
    }

    @Test
    public void add_duplicatefood_throwsDuplicatefoodException() {
        uniqueFoodList.add(CARBONARA);
        assertThrows(DuplicateFoodException.class, () -> uniqueFoodList.add(CARBONARA));
    }

    @Test
    public void setFood_nullTargetfood_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFoodList.setFood(null, CARBONARA));
    }

    @Test
    public void setFood_nullEditedfood_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFoodList.setFood(CARBONARA, null));
    }

    @Test
    public void setFood_targetfoodNotInList_throwsfoodNotFoundException() {
        assertThrows(FoodNotFoundException.class, () -> uniqueFoodList.setFood(CARBONARA, CARBONARA));
    }

    @Test
    public void setFood_editedfoodisSameFood_success() {
        uniqueFoodList.add(CARBONARA);
        uniqueFoodList.setFood(CARBONARA, CARBONARA);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        expectedUniqueFoodList.add(CARBONARA);
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setFood_editedfoodHasSameIdentity_success() {
        uniqueFoodList.add(CARBONARA);
        Food editedAlice = new FoodBuilder(CARBONARA).withTags(VALID_TAG_CHICKEN)
                .build();
        uniqueFoodList.setFood(CARBONARA, editedAlice);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        expectedUniqueFoodList.add(editedAlice);
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setFood_editedfoodHasDifferentIdentity_success() {
        uniqueFoodList.add(CARBONARA);
        uniqueFoodList.setFood(CARBONARA, NASI_LEMAK);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        expectedUniqueFoodList.add(NASI_LEMAK);
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setFood_editedfoodHasNonUniqueIdentity_throwsDuplicatefoodException() {
        uniqueFoodList.add(CARBONARA);
        uniqueFoodList.add(NASI_LEMAK);
        assertThrows(DuplicateFoodException.class, () -> uniqueFoodList.setFood(CARBONARA, NASI_LEMAK));
    }

    @Test
    public void remove_nullfood_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFoodList.remove(null));
    }

    @Test
    public void remove_foodDoesNotExist_throwsfoodNotFoundException() {
        assertThrows(FoodNotFoundException.class, () -> uniqueFoodList.remove(CARBONARA));
    }

    @Test
    public void remove_existingfood_removesfood() {
        uniqueFoodList.add(CARBONARA);
        uniqueFoodList.remove(CARBONARA);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setFoods_nullUniqueFoodList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFoodList.setFoods((UniqueFoodList) null));
    }

    @Test
    public void setFoods_uniqueFoodList_replacesOwnListWithProvidedUniqueFoodList() {
        uniqueFoodList.add(CARBONARA);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        expectedUniqueFoodList.add(NASI_LEMAK);
        uniqueFoodList.setFoods(expectedUniqueFoodList);
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setFoods_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFoodList.setFoods((List<Food>) null));
    }

    @Test
    public void setFoods_list_replacesOwnListWithProvidedList() {
        uniqueFoodList.add(CARBONARA);
        List<Food> foodList = Collections.singletonList(NASI_LEMAK);
        uniqueFoodList.setFoods(foodList);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        expectedUniqueFoodList.add(NASI_LEMAK);
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setFoods_listWithDuplicatefoods_throwsDuplicatefoodException() {
        List<Food> listWithDuplicateFoods = Arrays.asList(CARBONARA, CARBONARA);
        assertThrows(DuplicateFoodException.class, () -> uniqueFoodList.setFoods(listWithDuplicateFoods));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueFoodList.asUnmodifiableObservableList().remove(0));
    }
}
