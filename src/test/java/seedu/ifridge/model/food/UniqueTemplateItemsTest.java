package seedu.ifridge.model.food;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ifridge.testutil.Assert.assertThrows;
import static seedu.ifridge.testutil.TypicalTemplateItems.MINCEDBEEF;
import static seedu.ifridge.testutil.TypicalTemplateItems.MINCEDCHICKEN;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.ifridge.model.food.exceptions.DuplicateFoodException;
import seedu.ifridge.model.food.exceptions.FoodNotFoundException;
import seedu.ifridge.testutil.TemplateItemBuilder;

public class UniqueTemplateItemsTest {

    private final UniqueTemplateItems uniqueTemplateItems = new UniqueTemplateItems(new Name("Necessities"));

    @Test
    public void contains_nullTemplateItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTemplateItems.contains(null));
    }

    @Test
    public void contains_templateItemNotInList_returnsFalse() {
        assertFalse(uniqueTemplateItems.contains(MINCEDBEEF));
    }

    @Test
    public void contains_templateItemInList_returnsTrue() {
        uniqueTemplateItems.add(MINCEDBEEF);
        assertTrue(uniqueTemplateItems.contains(MINCEDBEEF));
    }

    @Test
    public void contains_templateItemWithSameIdentityFieldsInList_returnsTrue() {
        uniqueTemplateItems.add(MINCEDBEEF);
        TemplateItem editedMincedBeef = new TemplateItemBuilder(MINCEDBEEF).withAmount("300g")
                .build();
        assertTrue(uniqueTemplateItems.contains(editedMincedBeef));
    }

    @Test
    public void add_nullTemplateItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTemplateItems.add(null));
    }

    @Test
    public void add_duplicateTemplateItem_throwsDuplicatePersonException() {
        uniqueTemplateItems.add(MINCEDBEEF);
        assertThrows(DuplicateFoodException.class, () -> uniqueTemplateItems.add(MINCEDBEEF));
    }

    @Test
    public void setTemplateItem_nullTargetTemplateItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTemplateItems.setTemplateItem(null, MINCEDBEEF));
    }

    @Test
    public void setTemplateItem_nullEditedTemplateItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueTemplateItems.setTemplateItem(MINCEDBEEF, null));
    }

    @Test
    public void setTemplateItem_targetTemplateItemNotInList_throwsTemplateItemNotFoundException() {
        assertThrows(FoodNotFoundException.class, () -> uniqueTemplateItems.setTemplateItem(MINCEDBEEF, MINCEDBEEF));
    }

    @Test
    public void setTemplateItem_editedTemplateItemIsSameTemplateItem_success() {
        uniqueTemplateItems.add(MINCEDBEEF);
        uniqueTemplateItems.setTemplateItem(MINCEDBEEF, MINCEDBEEF);
        UniqueTemplateItems expectedUniquePersonList = new UniqueTemplateItems(new Name("Necessities"));
        expectedUniquePersonList.add(MINCEDBEEF);
        assertEquals(expectedUniquePersonList, uniqueTemplateItems);
    }

    @Test
    public void setTemplateItem_editedTemplateItemHasSameIdentity_success() {
        uniqueTemplateItems.add(MINCEDBEEF);
        TemplateItem editedMincedBeef = new TemplateItemBuilder(MINCEDBEEF).withAmount("300g")
                .build();
        uniqueTemplateItems.setTemplateItem(MINCEDBEEF, editedMincedBeef);
        UniqueTemplateItems expectedUniqueTemplateItems = new UniqueTemplateItems(new Name("Necessities"));
        expectedUniqueTemplateItems.add(editedMincedBeef);
        assertEquals(expectedUniqueTemplateItems, uniqueTemplateItems);
    }

    @Test
    public void setTemplateItem_editedTemplateItemHasDifferentIdentity_success() {
        uniqueTemplateItems.add(MINCEDBEEF);
        uniqueTemplateItems.setTemplateItem(MINCEDBEEF, MINCEDCHICKEN);
        UniqueTemplateItems expectedUniqueTemplateItems = new UniqueTemplateItems(new Name("Necessities"));
        expectedUniqueTemplateItems.add(MINCEDCHICKEN);
        assertEquals(expectedUniqueTemplateItems, uniqueTemplateItems);
    }

    @Test
    public void setPerson_editedTemplateItemHasNonUniqueIdentity_throwsDuplicateTemplateItemException() {
        uniqueTemplateItems.add(MINCEDBEEF);
        uniqueTemplateItems.add(MINCEDCHICKEN);
        assertThrows(DuplicateFoodException.class, () -> uniqueTemplateItems
                .setTemplateItem(MINCEDBEEF, MINCEDCHICKEN));
    }

    @Test
    public void remove_nullTemplateItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTemplateItems.remove(null));
    }

    @Test
    public void remove_templateItemDoesNotExist_throwsTemplateItemNotFoundException() {
        assertThrows(FoodNotFoundException.class, () -> uniqueTemplateItems.remove(MINCEDBEEF));
    }

    @Test
    public void remove_existingTemplateItem_removesTemplateItem() {
        uniqueTemplateItems.add(MINCEDBEEF);
        uniqueTemplateItems.remove(MINCEDBEEF);
        UniqueTemplateItems expectedUniqueTemplateItems = new UniqueTemplateItems(new Name("Necessities"));
        assertEquals(expectedUniqueTemplateItems, uniqueTemplateItems);
    }

    @Test
    public void setTemplateItems_nullUniqueTemplateItems_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTemplateItems
                .setTemplateItems((UniqueTemplateItems) null));
    }

    @Test
    public void setTemplateItems_uniqueTemplateItems_replacesOwnListWithProvidedUniqueTemplateItems() {
        uniqueTemplateItems.add(MINCEDBEEF);
        UniqueTemplateItems expectedUniqueTemplateItems = new UniqueTemplateItems(new Name("Necessities"));
        expectedUniqueTemplateItems.add(MINCEDCHICKEN);
        uniqueTemplateItems.setTemplateItems(expectedUniqueTemplateItems);
        assertEquals(expectedUniqueTemplateItems, uniqueTemplateItems);
    }

    @Test
    public void setTemplateItems_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTemplateItems.setTemplateItems((List<TemplateItem>) null));
    }

    @Test
    public void setTemplateItem_list_replacesOwnListWithProvidedList() {
        uniqueTemplateItems.add(MINCEDBEEF);
        List<TemplateItem> foodList = Collections.singletonList(MINCEDCHICKEN);
        uniqueTemplateItems.setTemplateItems(foodList);
        UniqueTemplateItems expectedUniquePersonList = new UniqueTemplateItems(new Name ("Necessities"));
        expectedUniquePersonList.add(MINCEDCHICKEN);
        assertEquals(expectedUniquePersonList, uniqueTemplateItems);
    }

    @Test
    public void setTemplateItems_listWithDuplicateTemplateItems_throwsDuplicateTemplateItemException() {
        List<TemplateItem> listWithDuplicateFoods = Arrays.asList(MINCEDBEEF, MINCEDBEEF);
        assertThrows(DuplicateFoodException.class, () -> uniqueTemplateItems.setTemplateItems(listWithDuplicateFoods));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueTemplateItems.asUnmodifiableObservableList().remove(0));
    }

}
