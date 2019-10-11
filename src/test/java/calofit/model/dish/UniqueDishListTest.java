package calofit.model.dish;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import calofit.logic.commands.CommandTestUtil;
import calofit.model.dish.exceptions.DishNotFoundException;
import calofit.model.dish.exceptions.DuplicateDishException;
import calofit.testutil.Assert;
import calofit.testutil.DishBuilder;
import calofit.testutil.TypicalDishes;

public class UniqueDishListTest {

    private final UniqueDishList uniqueDishList = new UniqueDishList();

    @Test
    public void contains_nullDish_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueDishList.contains(null));
    }

    @Test
    public void contains_dishNotInList_returnsFalse() {
        assertFalse(uniqueDishList.contains(TypicalDishes.SPAGHETTI));
    }

    @Test
    public void contains_dishInList_returnsTrue() {
        uniqueDishList.add(TypicalDishes.SPAGHETTI);
        assertTrue(uniqueDishList.contains(TypicalDishes.SPAGHETTI));
    }

    @Test
    public void contains_dishWithSameIdentityFieldsInList_returnsTrue() {
        uniqueDishList.add(TypicalDishes.SPAGHETTI);
        Dish editedAlice = new DishBuilder(TypicalDishes.SPAGHETTI).withTags(CommandTestUtil.VALID_TAG_SALTY)
                .build();
        assertTrue(uniqueDishList.contains(editedAlice));
    }

    @Test
    public void add_nullDish_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueDishList.add(null));
    }

    @Test
    public void add_duplicateDish_throwsDuplicateDishException() {
        uniqueDishList.add(TypicalDishes.SPAGHETTI);
        Assert.assertThrows(DuplicateDishException.class, () -> uniqueDishList.add(TypicalDishes.SPAGHETTI));
    }

    @Test
    public void setDish_nullTargetDish_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueDishList.setDish(null, TypicalDishes.SPAGHETTI));
    }

    @Test
    public void setDish_nullEditedDish_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
            uniqueDishList.setDish(TypicalDishes.SPAGHETTI, null));
    }

    @Test
    public void setDish_targetDishNotInList_throwsDishNotFoundException() {
        Assert.assertThrows(DishNotFoundException.class, ()
            -> uniqueDishList.setDish(TypicalDishes.SPAGHETTI, TypicalDishes.SPAGHETTI));
    }

    @Test
    public void setDish_editedDishIsSameDish_success() {
        uniqueDishList.add(TypicalDishes.SPAGHETTI);
        uniqueDishList.setDish(TypicalDishes.SPAGHETTI, TypicalDishes.SPAGHETTI);
        UniqueDishList expectedUniqueDishList = new UniqueDishList();
        expectedUniqueDishList.add(TypicalDishes.SPAGHETTI);
        assertEquals(expectedUniqueDishList, uniqueDishList);
    }

    @Test
    public void setDish_editedDishHasSameIdentity_success() {
        uniqueDishList.add(TypicalDishes.SPAGHETTI);
        Dish editedAlice = new DishBuilder(TypicalDishes.SPAGHETTI).withTags(CommandTestUtil.VALID_TAG_SALTY)
                .build();
        uniqueDishList.setDish(TypicalDishes.SPAGHETTI, editedAlice);
        UniqueDishList expectedUniqueDishList = new UniqueDishList();
        expectedUniqueDishList.add(editedAlice);
        assertEquals(expectedUniqueDishList, uniqueDishList);
    }

    @Test
    public void setDish_editedDishHasDifferentIdentity_success() {
        uniqueDishList.add(TypicalDishes.SPAGHETTI);
        uniqueDishList.setDish(TypicalDishes.SPAGHETTI, TypicalDishes.MACARONI);
        UniqueDishList expectedUniqueDishList = new UniqueDishList();
        expectedUniqueDishList.add(TypicalDishes.MACARONI);
        assertEquals(expectedUniqueDishList, uniqueDishList);
    }

    @Test
    public void setDish_editedDishHasNonUniqueIdentity_throwsDuplicateDishException() {
        uniqueDishList.add(TypicalDishes.SPAGHETTI);
        uniqueDishList.add(TypicalDishes.MACARONI);
        Assert.assertThrows(DuplicateDishException.class, ()
            -> uniqueDishList.setDish(TypicalDishes.SPAGHETTI, TypicalDishes.MACARONI));
    }

    @Test
    public void remove_nullDish_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueDishList.remove(null));
    }

    @Test
    public void remove_dishDoesNotExist_throwsDishNotFoundException() {
        Assert.assertThrows(DishNotFoundException.class, () -> uniqueDishList.remove(TypicalDishes.SPAGHETTI));
    }

    @Test
    public void remove_existingDish_removesDish() {
        uniqueDishList.add(TypicalDishes.SPAGHETTI);
        uniqueDishList.remove(TypicalDishes.SPAGHETTI);
        UniqueDishList expectedUniqueDishList = new UniqueDishList();
        assertEquals(expectedUniqueDishList, uniqueDishList);
    }

    @Test
    public void setDishes_nullUniqueDishList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueDishList.setDishes((UniqueDishList) null));
    }

    @Test
    public void setDishes_uniqueDishList_replacesOwnListWithProvidedUniqueDishList() {
        uniqueDishList.add(TypicalDishes.SPAGHETTI);
        UniqueDishList expectedUniqueDishList = new UniqueDishList();
        expectedUniqueDishList.add(TypicalDishes.MACARONI);
        uniqueDishList.setDishes(expectedUniqueDishList);
        assertEquals(expectedUniqueDishList, uniqueDishList);
    }

    @Test
    public void setDishes_nullList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueDishList.setDishes((List<Dish>) null));
    }

    @Test
    public void setDishes_list_replacesOwnListWithProvidedList() {
        uniqueDishList.add(TypicalDishes.SPAGHETTI);
        List<Dish> dishList = Collections.singletonList(TypicalDishes.MACARONI);
        uniqueDishList.setDishes(dishList);
        UniqueDishList expectedUniqueDishList = new UniqueDishList();
        expectedUniqueDishList.add(TypicalDishes.MACARONI);
        assertEquals(expectedUniqueDishList, uniqueDishList);
    }

    @Test
    public void setDishes_listWithDuplicateDishes_throwsDuplicateDishException() {
        List<Dish> listWithDuplicateDishes = Arrays.asList(TypicalDishes.SPAGHETTI, TypicalDishes.SPAGHETTI);
        Assert.assertThrows(DuplicateDishException.class, () -> uniqueDishList.setDishes(listWithDuplicateDishes));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, ()
            -> uniqueDishList.asUnmodifiableObservableList().remove(0));
    }
}
