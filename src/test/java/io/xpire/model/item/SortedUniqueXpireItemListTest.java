package io.xpire.model.item;

import static io.xpire.testutil.Assert.assertThrows;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_APPLE;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_FRUIT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.xpire.model.item.exceptions.DuplicateItemException;
import io.xpire.model.item.exceptions.ItemNotFoundException;
import io.xpire.testutil.TypicalItems;
import io.xpire.testutil.XpireItemBuilder;

public class SortedUniqueXpireItemListTest {

    private final SortedUniqueXpireItemList uniqueItemList = new SortedUniqueXpireItemList();

    @Test
    public void contains_nullItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueItemList.contains(null));
    }

    @Test
    public void contains_itemNotInList_returnsFalse() {
        assertFalse(uniqueItemList.contains(TypicalItems.APPLE));
    }

    @Test
    public void contains_itemInList_returnsTrue() {
        uniqueItemList.add(TypicalItems.APPLE);
        assertTrue(uniqueItemList.contains(TypicalItems.APPLE));
    }

    @Test
    public void contains_itemWithSameIdentityFieldsInList_returnsTrue() {
        uniqueItemList.add(TypicalItems.APPLE);
        XpireItem editedAlice = new XpireItemBuilder(TypicalItems.APPLE).withExpiryDate(VALID_EXPIRY_DATE_APPLE)
                                                 .build();
        assertTrue(uniqueItemList.contains(editedAlice));
    }

    @Test
    public void add_nullItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueItemList.add(null));
    }

    @Test
    public void add_duplicateItem_throwsDuplicateItemException() {
        uniqueItemList.add(TypicalItems.APPLE);
        assertThrows(DuplicateItemException.class, () -> uniqueItemList.add(TypicalItems.APPLE));
    }

    @Test
    public void addItem_addedItemIsSorted_success() {
        uniqueItemList.add(TypicalItems.APPLE);
        uniqueItemList.add(TypicalItems.BANANA);
        uniqueItemList.add(TypicalItems.DUCK);
        uniqueItemList.add(TypicalItems.FISH);
        SortedUniqueXpireItemList expectedUniqueItemList = new SortedUniqueXpireItemList();
        expectedUniqueItemList.add(TypicalItems.BANANA);
        expectedUniqueItemList.add(TypicalItems.DUCK);
        expectedUniqueItemList.add(TypicalItems.FISH);
        expectedUniqueItemList.add(TypicalItems.APPLE);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItem_nullTargetItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueItemList.setItem(null, TypicalItems.APPLE));
    }

    @Test
    public void setItem_nullEditedItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueItemList.setItem(TypicalItems.APPLE, null));
    }

    @Test
    public void setItem_targetItemNotInList_throwsItemNotFoundException() {
        assertThrows(ItemNotFoundException.class, () -> uniqueItemList.setItem(TypicalItems.APPLE, TypicalItems.APPLE));
    }

    @Test
    public void setItem_editedItemIsSameItem_success() {
        uniqueItemList.add(TypicalItems.APPLE);
        uniqueItemList.setItem(TypicalItems.APPLE, TypicalItems.APPLE);
        SortedUniqueXpireItemList expectedUniqueItemList = new SortedUniqueXpireItemList();
        expectedUniqueItemList.add(TypicalItems.APPLE);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItem_editedItemHasSameIdentity_success() {
        uniqueItemList.add(TypicalItems.APPLE);
        XpireItem editedAlice = new XpireItemBuilder(TypicalItems.APPLE)
                .withExpiryDate(VALID_EXPIRY_DATE_APPLE)
                .withTags(VALID_TAG_FRUIT)
                .build();
        uniqueItemList.setItem(TypicalItems.APPLE, editedAlice);
        SortedUniqueXpireItemList expectedUniqueItemList = new SortedUniqueXpireItemList();
        expectedUniqueItemList.add(editedAlice);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItem_editedItemHasDifferentIdentity_success() {
        uniqueItemList.add(TypicalItems.APPLE);
        uniqueItemList.setItem(TypicalItems.APPLE, TypicalItems.ICE_CREAM);
        SortedUniqueXpireItemList expectedUniqueItemList = new SortedUniqueXpireItemList();
        expectedUniqueItemList.add(TypicalItems.ICE_CREAM);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItem_editedItemHasNonUniqueIdentity_throwsDuplicateItemException() {
        uniqueItemList.add(TypicalItems.APPLE);
        uniqueItemList.add(TypicalItems.ICE_CREAM);
        assertThrows(DuplicateItemException.class, () -> uniqueItemList.setItem(TypicalItems.APPLE,
                TypicalItems.ICE_CREAM));
    }

    @Test
    public void remove_nullItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueItemList.remove(null));
    }

    @Test
    public void remove_itemDoesNotExist_throwsItemNotFoundException() {
        assertThrows(ItemNotFoundException.class, () -> uniqueItemList.remove(TypicalItems.APPLE));
    }

    @Test
    public void remove_existingItem_removesItem() {
        uniqueItemList.add(TypicalItems.APPLE);
        uniqueItemList.remove(TypicalItems.APPLE);
        SortedUniqueXpireItemList expectedUniqueItemList = new SortedUniqueXpireItemList();
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItems_nullUniqueItemList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueItemList.setItems((SortedUniqueXpireItemList) null));
    }

    @Test
    public void setItems_uniqueItemList_replacesOwnListWithProvidedUniqueItemList() {
        uniqueItemList.add(TypicalItems.APPLE);
        SortedUniqueXpireItemList expectedUniqueItemList = new SortedUniqueXpireItemList();
        expectedUniqueItemList.add(TypicalItems.ICE_CREAM);
        uniqueItemList.setItems(expectedUniqueItemList);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItems_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueItemList.setItems((List<XpireItem>) null));
    }

    @Test
    public void setItems_list_replacesOwnListWithProvidedList() {
        uniqueItemList.add(TypicalItems.ICE_CREAM);
        List<XpireItem> xpireItemList = Collections.singletonList(TypicalItems.APPLE);
        uniqueItemList.setItems(xpireItemList);
        SortedUniqueXpireItemList expectedUniqueItemList = new SortedUniqueXpireItemList();
        expectedUniqueItemList.add(TypicalItems.APPLE);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItems_listWithDuplicateItems_throwsDuplicateItemException() {
        List<XpireItem> listWithDuplicateXpireItems = Arrays.asList(TypicalItems.ICE_CREAM,
            TypicalItems.ICE_CREAM);
        assertThrows(DuplicateItemException.class, () -> uniqueItemList.setItems(listWithDuplicateXpireItems));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueItemList.asUnmodifiableObservableList().remove(0));
    }
}
