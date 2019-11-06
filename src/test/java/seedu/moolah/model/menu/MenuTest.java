package seedu.moolah.model.menu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EXPENSE_MENU_DESCRIPTION_CHICKEN;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import seedu.moolah.model.expense.Description;

public class MenuTest {

    @Test
    public void findMenuItemByDescription_noMatchingItems_throwsNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> Menu.findMenuItemByDescription(new Description("bla")));
    }

    @Test
    public void findMenuItemByDescription_success() {
        Description desc = new Description(VALID_EXPENSE_MENU_DESCRIPTION_CHICKEN);
        MenuItem item = Menu.findMenuItemByDescription(desc);
        assertNotNull(item);
        assertEquals(item.getDescription(), desc);
    }

}
