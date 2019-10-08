package calofit.model.meal;

import calofit.logic.commands.CommandTestUtil;
import calofit.model.meal.exceptions.DuplicateDishException;
import calofit.model.meal.exceptions.DishNotFoundException;
import calofit.testutil.Assert;
import calofit.testutil.DishBuilder;
import calofit.testutil.TypicalDishes;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UniqueDishListTest {

    private final UniqueDishList uniqueDishList = new UniqueDishList();

    @Test
    public void contains_nullDish_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueDishList.contains(null));
    }

    @Test
    public void contains_dishNotInList_returnsFalse() {
        assertFalse(uniqueDishList.contains(TypicalDishes.ALICE));
    }

    @Test
    public void contains_dishInList_returnsTrue() {
        uniqueDishList.add(TypicalDishes.ALICE);
        assertTrue(uniqueDishList.contains(TypicalDishes.ALICE));
    }

    @Test
    public void contains_dishWithSameIdentityFieldsInList_returnsTrue() {
        uniqueDishList.add(TypicalDishes.ALICE);
        Dish editedAlice = new DishBuilder(TypicalDishes.ALICE).withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueDishList.contains(editedAlice));
    }

    @Test
    public void add_nullDish_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueDishList.add(null));
    }

    @Test
    public void add_duplicateDish_throwsDuplicateDishException() {
        uniqueDishList.add(TypicalDishes.ALICE);
        Assert.assertThrows(DuplicateDishException.class, () -> uniqueDishList.add(TypicalDishes.ALICE));
    }

    @Test
    public void setDish_nullTargetDish_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueDishList.setDish(null, TypicalDishes.ALICE));
    }

    @Test
    public void setDish_nullEditedDish_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueDishList.setDish(TypicalDishes.ALICE, null));
    }

    @Test
    public void setDish_targetDishNotInList_throwsDishNotFoundException() {
        Assert.assertThrows(DishNotFoundException.class, () -> uniqueDishList.setDish(TypicalDishes.ALICE, TypicalDishes.ALICE));
    }

    @Test
    public void setDish_editedDishIsSameDish_success() {
        uniqueDishList.add(TypicalDishes.ALICE);
        uniqueDishList.setDish(TypicalDishes.ALICE, TypicalDishes.ALICE);
        UniqueDishList expectedUniqueDishList = new UniqueDishList();
        expectedUniqueDishList.add(TypicalDishes.ALICE);
        assertEquals(expectedUniqueDishList, uniqueDishList);
    }

    @Test
    public void setDish_editedDishHasSameIdentity_success() {
        uniqueDishList.add(TypicalDishes.ALICE);
        Dish editedAlice = new DishBuilder(TypicalDishes.ALICE).withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        uniqueDishList.setDish(TypicalDishes.ALICE, editedAlice);
        UniqueDishList expectedUniqueDishList = new UniqueDishList();
        expectedUniqueDishList.add(editedAlice);
        assertEquals(expectedUniqueDishList, uniqueDishList);
    }

    @Test
    public void setDish_editedDishHasDifferentIdentity_success() {
        uniqueDishList.add(TypicalDishes.ALICE);
        uniqueDishList.setDish(TypicalDishes.ALICE, TypicalDishes.BOB);
        UniqueDishList expectedUniqueDishList = new UniqueDishList();
        expectedUniqueDishList.add(TypicalDishes.BOB);
        assertEquals(expectedUniqueDishList, uniqueDishList);
    }

    @Test
    public void setDish_editedDishHasNonUniqueIdentity_throwsDuplicateDishException() {
        uniqueDishList.add(TypicalDishes.ALICE);
        uniqueDishList.add(TypicalDishes.BOB);
        Assert.assertThrows(DuplicateDishException.class, () -> uniqueDishList.setDish(TypicalDishes.ALICE, TypicalDishes.BOB));
    }

    @Test
    public void remove_nullDish_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueDishList.remove(null));
    }

    @Test
    public void remove_dishDoesNotExist_throwsDishNotFoundException() {
        Assert.assertThrows(DishNotFoundException.class, () -> uniqueDishList.remove(TypicalDishes.ALICE));
    }

    @Test
    public void remove_existingDish_removesDish() {
        uniqueDishList.add(TypicalDishes.ALICE);
        uniqueDishList.remove(TypicalDishes.ALICE);
        UniqueDishList expectedUniqueDishList = new UniqueDishList();
        assertEquals(expectedUniqueDishList, uniqueDishList);
    }

    @Test
    public void setDishes_nullUniqueDishList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueDishList.setDishes((UniqueDishList) null));
    }

    @Test
    public void setDishes_uniqueDishList_replacesOwnListWithProvidedUniqueDishList() {
        uniqueDishList.add(TypicalDishes.ALICE);
        UniqueDishList expectedUniqueDishList = new UniqueDishList();
        expectedUniqueDishList.add(TypicalDishes.BOB);
        uniqueDishList.setDishes(expectedUniqueDishList);
        assertEquals(expectedUniqueDishList, uniqueDishList);
    }

    @Test
    public void setDishes_nullList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueDishList.setDishes((List<Dish>) null));
    }

    @Test
    public void setDishes_list_replacesOwnListWithProvidedList() {
        uniqueDishList.add(TypicalDishes.ALICE);
        List<Dish> dishList = Collections.singletonList(TypicalDishes.BOB);
        uniqueDishList.setDishes(dishList);
        UniqueDishList expectedUniqueDishList = new UniqueDishList();
        expectedUniqueDishList.add(TypicalDishes.BOB);
        assertEquals(expectedUniqueDishList, uniqueDishList);
    }

    @Test
    public void setDishes_listWithDuplicateDishes_throwsDuplicateDishException() {
        List<Dish> listWithDuplicateDishes = Arrays.asList(TypicalDishes.ALICE, TypicalDishes.ALICE);
        Assert.assertThrows(DuplicateDishException.class, () -> uniqueDishList.setDishes(listWithDuplicateDishes));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, ()
            -> uniqueDishList.asUnmodifiableObservableList().remove(0));
    }
}
