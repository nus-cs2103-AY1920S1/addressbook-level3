package seedu.moolah.testutil;

import seedu.moolah.model.general.Category;
import seedu.moolah.model.general.Description;
import seedu.moolah.model.event.Event;
import seedu.moolah.model.general.Price;
import seedu.moolah.model.general.Timestamp;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_EVENT_DESCRIPTION = "Brian bday";
    public static final String DEFAULT_EVENT_PRICE = "30.5";
    public static final String DEFAULT_EVENT_CATEGORY = "Shopping";
    public static final String DEFAULT_EVENT_TIMESTAMP = "2020-01-01T12:00";
    public static final String DEFAULT_EVENT_BUDGET = "Default Budget";

    private Description description;
    private Price price;
    private Category category;
    private Timestamp timestamp;
    private Description budgetName;

    public EventBuilder() {
        description = new Description(DEFAULT_EVENT_DESCRIPTION);
        price = new Price(DEFAULT_EVENT_PRICE);
        category = new Category(DEFAULT_EVENT_CATEGORY);
        timestamp = Timestamp.createTimestampIfValid(DEFAULT_EVENT_TIMESTAMP).get();
        budgetName = new Description(DEFAULT_EVENT_BUDGET);
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        description = eventToCopy.getDescription();
        price = eventToCopy.getPrice();
        category = eventToCopy.getCategory();
        timestamp = eventToCopy.getTimestamp();
        budgetName = eventToCopy.getBudgetName();
    }

    /**
     * Sets the {@code Description} of the {@code Event} that we are building.
     */
    public EventBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Category} of the {@code Event} that we are building.
     */
    public EventBuilder withCategory(String category) {
        this.category = new Category(category);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Event} that we are building.
     */
    public EventBuilder withPrice(String price) {
        this.price = new Price(price);
        return this;
    }

    /**
     * Sets the {@code Timestamp} of the {@code Event} that we are building.
     */
    public EventBuilder withTimestamp(String rawTimestamp) {
        this.timestamp = Timestamp.createTimestampIfValid(rawTimestamp).get();
        return this;
    }

    /**
     * Sets the {@code Timestamp} of the {@code Event} that we are building.
     */
    public EventBuilder withBudgetName(String budgetName) {
        this.budgetName = new Description(budgetName);
        return this;
    }

    public Event build() {
        return new Event(description, price, category, timestamp, budgetName);
    }

}
