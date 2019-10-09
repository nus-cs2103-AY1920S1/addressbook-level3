package seedu.savenus.testutil;

import seedu.savenus.model.Menu;
import seedu.savenus.model.food.Food;

/**
 * A utility class to help with building Menu objects.
 * Example usage: <br>
 *     {@code Menu menu = new MenuBuilder().withfood("nasi", "lemak").build();}
 */
public class MenuBuilder {

    private Menu menu;

    public MenuBuilder() {
        menu = new Menu();
    }

    public MenuBuilder(Menu menu) {
        this.menu = menu;
    }

    /**
     * Adds a new {@code Food} to the {@code Menu} that we are building.
     */
    public MenuBuilder withfood(Food food) {
        menu.addFood(food);
        return this;
    }

    public Menu build() {
        return menu;
    }
}
