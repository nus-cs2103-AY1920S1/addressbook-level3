package seedu.ifridge.model.food;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ifridge.logic.commands.grocerylist.GroceryCommandTestUtil.VALID_TAG_NUTS;
import static seedu.ifridge.testutil.Assert.assertThrows;
import static seedu.ifridge.testutil.TypicalGroceryItems.BANANA;
import static seedu.ifridge.testutil.TypicalGroceryItems.SPAGHETTI;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.ifridge.model.food.exceptions.FoodNotFoundException;
import seedu.ifridge.testutil.GroceryItemBuilder;


public class UniqueGroceryListTest {

    private final UniqueGroceryList uniqueGroceryList = new UniqueGroceryList();

    @Test
    public void contains_nullGroceryItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroceryList.contains(null));
    }

    @Test
    public void contains_groceryItemNotInList_returnsFalse() {
        assertFalse(uniqueGroceryList.contains(BANANA));
    }

    @Test
    public void contains_groceryItemInList_returnsTrue() {
        uniqueGroceryList.add(BANANA);
        assertTrue(uniqueGroceryList.contains(BANANA));
    }

    @Test
    public void contains_groceryItemWithSameNameAndExpiryDate_returnsTrue() {
        uniqueGroceryList.add(BANANA);
        GroceryItem editedBanana = new GroceryItemBuilder(BANANA).withTags(VALID_TAG_NUTS)
                .build();
        assertTrue(uniqueGroceryList.contains(editedBanana));
    }

    @Test
    public void add_nullGroceryItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroceryList.add(null));
    }

    // TODO: move exception from add command to uniqueGroceryListist#add()
    /*@Test
    public void add_duplicateGroceryItem_throwsCommandException() {
        uniqueGroceryList.add(BANANA);
        assertThrows(CommandException.class, () -> uniqueGroceryList.add(BANANA));
    }*/

    @Test
    public void setGroceryItem_nullTargetGroceryItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroceryList.setGroceryItem(null, BANANA));
    }

    @Test
    public void setGroceryItem_nullEditedGroceryItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroceryList.setGroceryItem(BANANA, null));
    }

    @Test
    public void setGroceryItem_targetGroceryItemNotInList_throwsFoodNotFoundException() {
        assertThrows(FoodNotFoundException.class, () -> uniqueGroceryList.setGroceryItem(BANANA, BANANA));
    }

    @Test
    public void setGroceryItem_editedGroceryItemIsSameGroceryItem_success() {
        uniqueGroceryList.add(BANANA);
        uniqueGroceryList.setGroceryItem(BANANA, BANANA);
        UniqueGroceryList expectedUniqueGroceryList = new UniqueGroceryList();
        expectedUniqueGroceryList.add(BANANA);
        assertEquals(expectedUniqueGroceryList, uniqueGroceryList);
    }

    @Test
    public void setGroceryItem_editedGroceryItemHasSameIdentity_success() {
        uniqueGroceryList.add(BANANA);
        GroceryItem editedBanana = new GroceryItemBuilder(BANANA).withTags(VALID_TAG_NUTS)
                .build();
        uniqueGroceryList.setGroceryItem(BANANA, editedBanana);
        UniqueGroceryList expectedUniqueGroceryList = new UniqueGroceryList();
        expectedUniqueGroceryList.add(editedBanana);
        assertEquals(expectedUniqueGroceryList, uniqueGroceryList);
    }

    @Test
    public void setGroceryItem_editedGroceryItemHasDifferentIdentity_success() {
        uniqueGroceryList.add(BANANA);
        uniqueGroceryList.setGroceryItem(BANANA, SPAGHETTI);
        UniqueGroceryList expectedUniqueGroceryList = new UniqueGroceryList();
        expectedUniqueGroceryList.add(SPAGHETTI);
        assertEquals(expectedUniqueGroceryList, uniqueGroceryList);
    }

    /*@Test
    public void setGroceryItem_editedGroceryItemHasNonUniqueIdentity_throwsDuplicateFoodException() {
        uniqueGroceryList.add(BANANA);
        uniqueGroceryList.add(SPAGHETTI);
        assertThrows(DuplicateFoodException.class, () -> uniqueGroceryList.setGroceryItem(BANANA, SPAGHETTI));
    }*/

    @Test
    public void remove_nullGroceryItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroceryList.remove(null));
    }

    @Test
    public void remove_groceryItemDoesNotExist_throwsFoodNotFoundException() {
        assertThrows(FoodNotFoundException.class, () -> uniqueGroceryList.remove(BANANA));
    }

    @Test
    public void remove_existingGroceryItem_removesGroceryItem() {
        uniqueGroceryList.add(BANANA);
        uniqueGroceryList.remove(BANANA);
        UniqueGroceryList expectedUniqueGroceryList = new UniqueGroceryList();
        assertEquals(expectedUniqueGroceryList, uniqueGroceryList);
    }

    @Test
    public void setGroceryList_nullUniqueGroceryList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroceryList.setGroceryList((UniqueGroceryList) null));
    }

    @Test
    public void setGroceryList_uniqueGroceryList_replacesOwnListWithProvidedUniqueGroceryList() {
        uniqueGroceryList.add(BANANA);
        UniqueGroceryList expectedUniqueGroceryList = new UniqueGroceryList();
        expectedUniqueGroceryList.add(SPAGHETTI);
        uniqueGroceryList.setGroceryList(expectedUniqueGroceryList);
        assertEquals(expectedUniqueGroceryList, uniqueGroceryList);
    }

    @Test
    public void setGroceryList_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroceryList.setGroceryList((List<GroceryItem>) null));
    }

    @Test
    public void setGroceryList_list_replacesOwnListWithProvidedList() {
        uniqueGroceryList.add(BANANA);
        List<GroceryItem> groceryList = Collections.singletonList(SPAGHETTI);
        uniqueGroceryList.setGroceryList(groceryList);
        UniqueGroceryList expectedUniqueGroceryList = new UniqueGroceryList();
        expectedUniqueGroceryList.add(SPAGHETTI);
        assertEquals(expectedUniqueGroceryList, uniqueGroceryList);
    }

    /*@Test
    public void setGroceryList_listWithDuplicateGroceryItem_throwsDuplicateFoodException() {
        List<GroceryItem> listWithDuplicateGroceryItems = Arrays.asList(BANANA, BANANA);
        assertThrows(DuplicateFoodException.class, () ->
                uniqueGroceryList.setGroceryList(listWithDuplicateGroceryItems));
    }*/

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueGroceryList.asUnmodifiableObservableList().remove(0));
    }
}
