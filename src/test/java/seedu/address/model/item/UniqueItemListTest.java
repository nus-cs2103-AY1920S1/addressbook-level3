package seedu.address.model.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_KIWI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRUIT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalItems.APPLE;
import static seedu.address.testutil.TypicalItems.KIWI;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.item.exceptions.DuplicateItemException;
import seedu.address.model.item.exceptions.ItemNotFoundException;
import seedu.address.testutil.ItemBuilder;

public class UniqueItemListTest {

    private final UniqueItemList uniqueItemList = new UniqueItemList();

    @Test
    public void contains_nullItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueItemList.contains(null));
    }

    @Test
    public void contains_itemNotInList_returnsFalse() {
        assertFalse(uniqueItemList.contains(KIWI));
    }

    @Test
    public void contains_itemInList_returnsTrue() {
        uniqueItemList.add(KIWI);
        assertTrue(uniqueItemList.contains(KIWI));
    }

    @Test
    public void contains_itemWithSameIdentityFieldsInList_returnsTrue() {
        uniqueItemList.add(KIWI);
        Item editedAlice = new ItemBuilder(KIWI).withExpiryDate(VALID_EXPIRY_DATE_KIWI)
                                                 .build();
        assertTrue(uniqueItemList.contains(editedAlice));
    }

    @Test
    public void add_nullItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueItemList.add(null));
    }

    @Test
    public void add_duplicateItem_throwsDuplicatePersonException() {
        uniqueItemList.add(KIWI);
        assertThrows(DuplicateItemException.class, () -> uniqueItemList.add(KIWI));
    }

    @Test
    public void setPerson_nullTargetItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueItemList.setItem(null, KIWI));
    }

    @Test
    public void setPerson_nullEditedItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueItemList.setItem(KIWI, null));
    }

    @Test
    public void setPerson_targetItemNotInList_throwsPersonNotFoundException() {
        assertThrows(ItemNotFoundException.class, () -> uniqueItemList.setItem(KIWI, KIWI));
    }

    @Test
    public void setItem_editedItemIsSameItem_success() {
        uniqueItemList.add(KIWI);
        uniqueItemList.setItem(KIWI, KIWI);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(KIWI);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItem_editedItemHasSameIdentity_success() {
        uniqueItemList.add(KIWI);
        Item editedAlice = new ItemBuilder(KIWI).withExpiryDate(VALID_EXPIRY_DATE_KIWI).withTags(VALID_TAG_FRUIT)
                                                 .build();
        uniqueItemList.setItem(KIWI, editedAlice);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(editedAlice);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItem_editedItemHasDifferentIdentity_success() {
        uniqueItemList.add(KIWI);
        uniqueItemList.setItem(KIWI, APPLE);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(APPLE);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItem_editedItemHasNonUniqueIdentity_throwsDuplicateItemException() {
        uniqueItemList.add(KIWI);
        uniqueItemList.add(APPLE);
        assertThrows(DuplicateItemException.class, () -> uniqueItemList.setItem(KIWI, APPLE));
    }

    @Test
    public void remove_nullItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueItemList.remove(null));
    }

    @Test
    public void remove_itemDoesNotExist_throwsItemNotFoundException() {
        assertThrows(ItemNotFoundException.class, () -> uniqueItemList.remove(KIWI));
    }

    @Test
    public void remove_existingItem_removesItem() {
        uniqueItemList.add(KIWI);
        uniqueItemList.remove(KIWI);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItems_nullUniqueItemList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueItemList.setItems((UniqueItemList) null));
    }

    @Test
    public void setItems_uniqueItemList_replacesOwnListWithProvidedUniqueItemList() {
        uniqueItemList.add(KIWI);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(APPLE);
        uniqueItemList.setItems(expectedUniqueItemList);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueItemList.setItems((List<Item>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueItemList.add(APPLE);
        List<Item> personList = Collections.singletonList(KIWI);
        uniqueItemList.setItems(personList);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(KIWI);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Item> listWithDuplicatePersons = Arrays.asList(APPLE, APPLE);
        assertThrows(DuplicateItemException.class, () -> uniqueItemList.setItems(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueItemList.asUnmodifiableObservableList().remove(0));
    }
}
