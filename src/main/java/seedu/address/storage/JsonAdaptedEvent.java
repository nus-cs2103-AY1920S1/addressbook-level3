package seedu.address.storage;

// import java.time.LocalDate;
// import java.util.ArrayList;
// import java.util.HashSet;
// import java.util.List;
// import java.util.Set;
// import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.expense.Description;
import seedu.address.model.expense.Event;
import seedu.address.model.expense.Price;
import seedu.address.model.expense.Timestamp;
// import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Event}.
 */
class JsonAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Expense's %s field is missing!";

    private final String description;
    private final String price;
    private final String rawTimestamp;
    // private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedExpense} with the given expense details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("description") String description,
                              @JsonProperty("price") String price,
                              @JsonProperty("timestamp") String rawTimestamp) {
        this.description = description;
        this.price = price;
        this.rawTimestamp = rawTimestamp;
        //        if (tagged != null) {
        //            this.tagged.addAll(tagged);
        //        }
    }

    /**
     * Converts a given {@code Expense} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        description = source.getDescription().fullDescription;
        price = source.getPrice().value;
        rawTimestamp = source.getTimestamp().toString();
        //        tagged.addAll(source.getTags().stream()
        //                .map(JsonAdaptedTag::new)
        //                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted expense object into the model's {@code Expense} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted expense.
     */
    public Event toModelType() throws IllegalValueException {
        //        final List<Tag> expenseTags = new ArrayList<>();
        //        for (JsonAdaptedTag tag : tagged) {
        //            expenseTags.add(tag.toModelType());
        //        }

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
        if (!Timestamp.isValidTimestamp(rawTimestamp)) {
            throw new IllegalValueException(Timestamp.MESSAGE_CONSTRAINTS_DATE);
        }
        final Timestamp modelTimestamp = new Timestamp(rawTimestamp);

        // final Set<Tag> modelTags = new HashSet<>(expenseTags);
        return new Event(modelDescription, modelPrice, modelTimestamp);
    }

}
