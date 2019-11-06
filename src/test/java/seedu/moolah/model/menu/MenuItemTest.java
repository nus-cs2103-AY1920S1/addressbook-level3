package seedu.moolah.model.menu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.moolah.model.expense.Description;
import seedu.moolah.model.expense.Price;

public class MenuItemTest {

    @Test
    public void constructor() {
        // Null description and price
        assertThrows(NullPointerException.class, () -> new MenuItem(null, null));
        // Null description
        assertThrows(NullPointerException.class, () -> new MenuItem(null, new Price("1")));
        // Null price
        assertThrows(NullPointerException.class, () -> new MenuItem(new Description("test"), null));
        // Proper case
        new MenuItem(new Description("test 2"), new Price("2"));
    }

    @Test
    public void equals() {
        MenuItem menuItem = new MenuItem(new Description("item"), new Price("3"));

        // Same object
        assertEquals(menuItem, menuItem);
        // Different types
        assertNotEquals(menuItem, new Object());
        // Different description and price
        assertNotEquals(menuItem, new MenuItem(new Description("other item"), new Price("4")));
        // Different description
        assertNotEquals(menuItem, new MenuItem(new Description("other item 2"), menuItem.getPrice()));
        // Different price
        assertNotEquals(menuItem, new MenuItem(menuItem.getDescription(), new Price("5")));
        // Same description and price
        assertEquals(menuItem, new MenuItem(menuItem.getDescription(), menuItem.getPrice()));
    }
}
