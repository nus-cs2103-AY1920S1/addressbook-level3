package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_INVENTORY_NAME_MUSIC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INVENTORY_NAME_SPORTS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INVENTORY_PRICE_MUSIC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INVENTORY_PRICE_SPORTS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.ProjectDashboard;
import seedu.address.model.inventory.Inventory;
import seedu.address.model.inventory.Price;

/**
 * A utility class containing a list of {@code Inventory} objects to be used in tests.
 */
public class TypicalInventories {
    public static final Inventory SHIRTS = new InventoryBuilder().withName("Shirts")
            .withPrice(new Price(5)).build();
    public static final Inventory POSTERS = new InventoryBuilder().withName("Posters")
            .withPrice(new Price(24.99)).build();
    public static final Inventory BADGES = new InventoryBuilder().withName("Badges")
            .withPrice(new Price(15.50)).build();
    public static final Inventory BALLS = new InventoryBuilder().withName("Balls")
            .withPrice(new Price(0)).build();
    public static final Inventory CATERING = new InventoryBuilder().withName("Caterers")
            .withPrice(new Price(550)).build();
    public static final Inventory PRESENTATION_ITEMS = new InventoryBuilder().withName("Presentation Items")
            .withPrice(new Price(9.90)).build();
    public static final Inventory BOXES = new InventoryBuilder().withName("Boxes")
            .withPrice(new Price(12.10)).build();

    // Manually added
    public static final Inventory MAKEUP = new InventoryBuilder()
            .withName("Makeup")
            .withPrice(new Price(49.90)).build();
    public static final Inventory PARTY_EQUIPS = new InventoryBuilder()
            .withPrice(new Price(2.90))
            .withName("Party equipments").build();

    // Manually added - Task's details found in {@code CommandTestUtil}
    public static final Inventory SPORTS = new InventoryBuilder().withName(VALID_INVENTORY_NAME_SPORTS)
            .withPrice(new Price(VALID_INVENTORY_PRICE_SPORTS)).build();
    public static final Inventory MUSIC = new InventoryBuilder().withName(VALID_INVENTORY_NAME_MUSIC)
            .withPrice(new Price(VALID_INVENTORY_PRICE_MUSIC)).build();

    private TypicalInventories() {} // prevents instantiation

    /**
     * Returns an {@code ProjectDashboard} with all the typical persons.
     */
    public static ProjectDashboard getTypicalProjectDashboard() {
        ProjectDashboard ab = new ProjectDashboard();
        for (Inventory inventory : getTypicalInventories()) {
            ab.addInventory(inventory);
        }
        return ab;
    }

    public static List<Inventory> getTypicalInventories() {
        return new ArrayList<>(Arrays.asList(SHIRTS, POSTERS, BADGES,
                BALLS, CATERING, PRESENTATION_ITEMS, BOXES));
    }
}
