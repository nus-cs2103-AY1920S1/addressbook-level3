package seedu.address.testutil;

import java.text.DecimalFormat;

import seedu.address.inventory.model.Item;

public class ItemBuilder {
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");
    public static final String DEFAULT_CATEGORY = "food";
    public static final String DEFAULT_DESCRIPTION = "Burger";
    public static final Integer DEFAULT_QUANTITY = 75;
    public static final Double DEFAULT_COST = 4.59;
    public static final Double DEFAULT_TOTALCOST = 344.25;
    public static final Double DEFAULT_PRICE = 6.30;
    public static final Double DEFAULT_SUBTOTAL = 472.50;
    public static final String DEFAULT_ID = "1";

    private String category;
    private String description;
    private Integer quantity;
    private Double cost;
    private Double totalCost;
    private Double price;
    private Double subtotal;
    private String id;

    public ItemBuilder() {
        this.description = DEFAULT_DESCRIPTION;
        this.category = DEFAULT_CATEGORY;
        this.quantity = DEFAULT_QUANTITY;
        this.cost = DEFAULT_COST;
        this.price = DEFAULT_PRICE;
        this.id = "" + DEFAULT_ID;
    }

    /**
     * Sets the description of the {@code Item} that we are building.
     */
    public ItemBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets the category of the {@code Item} that we are building.
     */
    public ItemBuilder withCategory(String category) {
        this.category = category;
        return this;
    }

    /**
     * Sets the quantity of the {@code Item} that we are building.
     */
    public ItemBuilder withQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    /**
     * Sets the cost of the {@code Item} that we are building.
     */
    public ItemBuilder withCost(Double cost) {
        this.cost = cost;
        return this;
    }

    /**
     * Sets the total cost of the {@code Item} that we are building.
     */
    public ItemBuilder withTotalCost(Double totalCost) {
        this.totalCost = totalCost;
        return this;
    }

    /**
     * Sets the price of the {@code Item} that we are building.
     */
    public ItemBuilder withPrice(Double price) {
        this.price = price;
        return this;
    }

    /**
     * Sets the subtotal of the {@code Item} that we are building.
     */
    public ItemBuilder withSubtotal(Double subtotal) {
        this.subtotal = subtotal;
        return this;
    }

    /**
     * Sets the ID of the {@code Item} that we are building.
     */
    public ItemBuilder withId(String Id) {
        this.id = id;
        return this;
    }

    public Item build() {
        return new Item(category, description, quantity, cost, price, Integer.parseInt(id));
    }


}
