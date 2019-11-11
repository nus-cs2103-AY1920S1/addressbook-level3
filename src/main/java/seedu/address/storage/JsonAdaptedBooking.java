package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.Name;
import seedu.address.model.expense.Expense;

import java.util.Optional;

/**
 * Jackson friendly version of {@code Expenditure}.
 */
public class JsonAdaptedBooking {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Booking's %s field is missing!";

    private final Name name;
    private final String contact;
    private final Expense expense;

    /**
     * Constructs a {@code JsonAdaptedExpenditure} with the given Expenditure details.
     */
    @JsonCreator
    public JsonAdaptedBooking(@JsonProperty("name") Name name,
                              @JsonProperty("contact") String contact,
                              @JsonProperty("expense") Expense expense) {
        this.name = name;
        this.contact = contact;
        this.expense = expense;
    }

    /**
     * Converts a given {@code Expenditure} into this class for Jackson use.
     */
    public JsonAdaptedBooking(Booking source) {
        this.name = source.getName();
        this.contact = source.getContact();
        this.expense = source.getExpense();
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

        return new Booking(name, contact, expense) {
        };
    }
}
