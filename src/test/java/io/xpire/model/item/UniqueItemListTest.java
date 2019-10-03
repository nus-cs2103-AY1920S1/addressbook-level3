package io.xpire.model.item;

import static io.xpire.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_KIWI;
import static io.xpire.logic.commands.CommandTestUtil.VALID_TAG_FRUIT;
import static io.xpire.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.xpire.model.item.exceptions.DuplicateItemException;
import io.xpire.model.item.exceptions.ItemNotFoundException;
import io.xpire.testutil.ItemBuilder;
import io.xpire.testutil.TypicalItems;

public class UniqueItemListTest {

    private final UniqueItemList uniqueItemList = new UniqueItemList();

    @Test
    public void contains_nullItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueItemList.contains(null));
    }

    @Test
    public void contains_itemNotInList_returnsFalse() {
        assertFalse(uniqueItemList.contains(TypicalItems.KIWI));
    }

    @Test
    public void contains_itemInList_returnsTrue() {
        uniqueItemList.add(TypicalItems.KIWI);
        assertTrue(uniqueItemList.contains(TypicalItems.KIWI));
    }

    @Test
    public void contains_itemWithSameIdentityFieldsInList_returnsTrue() {
        uniqueItemList.add(TypicalItems.KIWI);
        Item editedAlice = new ItemBuilder(TypicalItems.KIWI).withExpiryDate(VALID_EXPIRY_DATE_KIWI)
                                                 .build();
        assertTrue(uniqueItemList.contains(editedAlice));
    }

    @Test
    public void add_nullItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueItemList.add(null));
    }

    @Test
    public void add_duplicateItem_throwsDuplicatePersonException() {
        uniqueItemList.add(TypicalItems.KIWI);
        assertThrows(DuplicateItemException.class, () -> uniqueItemList.add(TypicalItems.KIWI));
    }

    @Test
    public void setPerson_nullTargetItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueItemList.setItem(null, TypicalItems.KIWI));
    }

    @Test
    public void setPerson_nullEditedItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueItemList.setItem(TypicalItems.KIWI, null));
    }

    @Test
    public void setPerson_targetItemNotInList_throwsPersonNotFoundException() {
        assertThrows(ItemNotFoundException.class, () -> uniqueItemList.setItem(TypicalItems.KIWI, TypicalItems.KIWI));
    }

    @Test
    public void setItem_editedItemIsSameItem_success() {
        uniqueItemList.add(TypicalItems.KIWI);
        uniqueItemList.setItem(TypicalItems.KIWI, TypicalItems.KIWI);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(TypicalItems.KIWI);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItem_editedItemHasSameIdentity_success() {
        uniqueItemList.add(TypicalItems.KIWI);
        Item editedAlice = new ItemBuilder(TypicalItems.KIWI)
                .withExpiryDate(VALID_EXPIRY_DATE_KIWI)
                .withTags(VALID_TAG_FRUIT)
                .build();
        uniqueItemList.setItem(TypicalItems.KIWI, editedAlice);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(editedAlice);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItem_editedItemHasDifferentIdentity_success() {
        uniqueItemList.add(TypicalItems.KIWI);
        uniqueItemList.setItem(TypicalItems.KIWI, TypicalItems.APPLE);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(TypicalItems.APPLE);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItem_editedItemHasNonUniqueIdentity_throwsDuplicateItemException() {
        uniqueItemList.add(TypicalItems.KIWI);
        uniqueItemList.add(TypicalItems.APPLE);
        assertThrows(DuplicateItemException.class, () -> uniqueItemList.setItem(TypicalItems.KIWI, TypicalItems.APPLE));
    }

    @Test
    public void remove_nullItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueItemList.remove(null));
    }

    @Test
    public void remove_itemDoesNotExist_throwsItemNotFoundException() {
        assertThrows(ItemNotFoundException.class, () -> uniqueItemList.remove(TypicalItems.KIWI));
    }

    @Test
    public void remove_existingItem_removesItem() {
        uniqueItemList.add(TypicalItems.KIWI);
        uniqueItemList.remove(TypicalItems.KIWI);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItems_nullUniqueItemList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueItemList.setItems((UniqueItemList) null));
    }

    @Test
    public void setItems_uniqueItemList_replacesOwnListWithProvidedUniqueItemList() {
        uniqueItemList.add(TypicalItems.KIWI);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(TypicalItems.APPLE);
        uniqueItemList.setItems(expectedUniqueItemList);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueItemList.setItems((List<Item>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueItemList.add(TypicalItems.APPLE);
        List<Item> personList = Collections.singletonList(TypicalItems.KIWI);
        uniqueItemList.setItems(personList);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(TypicalItems.KIWI);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Item> listWithDuplicatePersons = Arrays.asList(TypicalItems.APPLE, TypicalItems.APPLE);
        assertThrows(DuplicateItemException.class, () -> uniqueItemList.setItems(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueItemList.asUnmodifiableObservableList().remove(0));
    }
}
