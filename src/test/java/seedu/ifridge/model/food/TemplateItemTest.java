package seedu.ifridge.model.food;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ifridge.logic.commands.CommandTestUtil.VALID_NAME_NUTS;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.TEMPLATE_ITEM_CHEESE;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.TEMPLATE_ITEM_RICE;

import org.junit.jupiter.api.Test;

import seedu.ifridge.testutil.TemplateItemBuilder;

public class TemplateItemTest {

    /**
     * Compare two template items to see if all their fields are identical to each other.
     * @param ti1 TemplateItem1
     * @param ti2 TemplateItem2
     * @return true if all corresponding fields of the shopping items are the same, false otherwise.
     */
    public static boolean areExactlySameTemplateItems(TemplateItem ti1, TemplateItem ti2) {
        return ti1.isSameName(ti2) && (ti1.getAmount().toString().equals(ti2.getAmount().toString()));
    }

    @Test
    public void isSameTemplateItem() {
        // same object -> returns true
        assertTrue(TEMPLATE_ITEM_CHEESE.isSameFood(TEMPLATE_ITEM_CHEESE));

        // null -> returns false
        assertFalse(TEMPLATE_ITEM_CHEESE.isSameFood(null));

        // different name -> returns false
        TemplateItem editedCheese = new TemplateItemBuilder().withName(VALID_NAME_NUTS).build();
        assertFalse(TEMPLATE_ITEM_CHEESE.isSameFood(editedCheese));
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(TEMPLATE_ITEM_CHEESE.equals(TEMPLATE_ITEM_CHEESE));

        // same values -> returns true
        TemplateItem cheeseCopy = new TemplateItemBuilder(TEMPLATE_ITEM_CHEESE).build();
        assertTrue(TEMPLATE_ITEM_CHEESE.equals(cheeseCopy));

        // null -> returns false
        assertFalse(TEMPLATE_ITEM_CHEESE.equals(null));

        // different type -> returns false
        assertFalse(TEMPLATE_ITEM_CHEESE.equals(5));

        // different person -> returns false
        assertFalse(TEMPLATE_ITEM_CHEESE.equals(TEMPLATE_ITEM_RICE));

        // different name -> returns false
        TemplateItem editedCheese = new TemplateItemBuilder(TEMPLATE_ITEM_CHEESE).withName(VALID_NAME_NUTS).build();
        assertFalse(TEMPLATE_ITEM_CHEESE.equals(editedCheese));
    }

}
