package seedu.elisa.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.elisa.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.elisa.commons.exceptions.DuplicateItemException;
import seedu.elisa.testutil.TypicalItems;

public class ItemStorageTest {
    private ItemStorage storageTest = new ItemStorage();

    @Test
    public void add_duplicateItem_throwsDuplicateItemException() {
        storageTest.add(TypicalItems.ITEM_WITH_EVENT);
        assertThrows(DuplicateItemException.class, () -> storageTest.add(TypicalItems.ITEM_WITH_EVENT));
    }

    @Test
    public void deepCopy_returnsNewItemStorage() {
        storageTest.add(TypicalItems.ITEM_WITH_EVENT);
        assertTrue(storageTest.equals(storageTest.deepCopy()));
        assertFalse(storageTest == storageTest.deepCopy());
    }
}
