package seedu.moolah.model.menu;

import java.util.List;
import java.util.NoSuchElementException;

import seedu.moolah.model.expense.Description;
import seedu.moolah.model.expense.Price;

/**
 * Menu represents the collection of {@code MenuItems} supported by MooLah.
 */
public class Menu {

    private static final List<MenuItem> MENU_ITEMS = List.of(
            new MenuItem(new Description("Deck Chicken Rice"), new Price("2.50")),
            new MenuItem(new Description("Finefood Western Combo Set Meal"), new Price("7.50")),
            new MenuItem(new Description("YIH Nutella Waffle"), new Price("1.50")),
            new MenuItem(new Description("Deck Ice Milo"), new Price("1.50")),
            new MenuItem(new Description("E2 Fried Fish Fillet"), new Price("3.50"))
    );

    /**
     * Returns the corresponding menu item that has description {@code desc}.
     * @throws NoSuchElementException if there are no such item.
     */
    public static MenuItem findMenuItemByDescription(Description desc) throws NoSuchElementException {
        for (MenuItem item : MENU_ITEMS) {
            if (item.getDescription().equals(desc)) {
                return item;
            }
        }
        throw new NoSuchElementException();
    }

}
