package seedu.moolah.storage;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.moolah.commons.exceptions.IllegalValueException;
import seedu.moolah.model.expense.Category;
import seedu.moolah.model.expense.Description;
import seedu.moolah.model.expense.Event;
import seedu.moolah.model.expense.Price;
import seedu.moolah.model.expense.Timestamp;


/**
 * Jackson-friendly version of {@link Event}.
 */
class JsonAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    private final String description;
    private final String price;
    private final String rawTimestamp;
    private final String category;
    private final String budgetName;

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("description") String description,
                              @JsonProperty("price") String price,
                              @JsonProperty("category") String category,
                              @JsonProperty("timestamp") String rawTimestamp,
                              @JsonProperty("budgetName") String budgetName) {
        this.description = description;
        this.price = price;
        this.rawTimestamp = rawTimestamp;
        this.category = category;
        this.budgetName = budgetName;
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        this.description = source.getDescription().fullDescription;
        this.price = source.getPrice().value;
        this.rawTimestamp = source.getTimestamp().fullTimestamp.toString();
        this.category = source.getCategory().getCategoryName();
        this.budgetName = source.getBudgetName().fullDescription;
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Event toModelType() throws IllegalValueException {

        if (category == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Category.class.getSimpleName()));
        }
        if (!Category.isValidCategoryName(category)) {
            throw new IllegalValueException(Category.MESSAGE_CONSTRAINTS);
        }
        Category modelCategory = new Category(category);

        if (description == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (price == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(price)) {
            throw new IllegalValueException(Price.MESSAGE_CONSTRAINTS);
        }
        final Price modelPrice = new Price(price);

        if (rawTimestamp == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Timestamp.class.getSimpleName()));
        }

        Optional<Timestamp> potentialTimestamp = Timestamp.createGeneralTimestampFromStorage(rawTimestamp);
        if (potentialTimestamp.isEmpty()) {
            throw new IllegalValueException(Timestamp.MESSAGE_CONSTRAINTS_GENERAL);
        }
        final Timestamp modelTimestamp = potentialTimestamp.get();

        if (budgetName == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "Budget Name"));
        }
        if (!Description.isValidDescription(budgetName)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelBudgetName = new Description(budgetName);

        return new Event(modelDescription, modelPrice, modelCategory, modelTimestamp, modelBudgetName);
    }

}
