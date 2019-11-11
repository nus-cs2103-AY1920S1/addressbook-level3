package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.Name;
import seedu.address.model.itinerary.Budget;

/**
 * Jackson friendly version of {@code Booking}.
 */
public class JsonAdaptedBooking {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Booking's %s field is missing!";

    private final String name;
    private final String contact;
    private final double budget;

    /**
     * Constructs a {@code JsonAdaptedExpenditure} with the given Expenditure details.
     */
    @JsonCreator
    public JsonAdaptedBooking(@JsonProperty("name") String name,
                              @JsonProperty("contact") String contact,
                              @JsonProperty("budget") double budget) {
        this.name = name;
        this.contact = contact;
        this.budget = budget;
    }

    /**
     * Converts a given {@code Expenditure} into this class for Jackson use.
     */
    public JsonAdaptedBooking(Booking source) {
        this.name = source.getName().fullName;
        this.contact = source.getContact();
        this.budget = source.getBudget().getValue();
    }

    /**
     * Converts this Jackson-friendly adapted day object into the model's {@code Expenditure} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted day.
     */
    public Booking toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }

        return new Booking(new Name(name), contact, new Budget(budget));
    }
}
