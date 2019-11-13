package budgetbuddy.storage.loans;

import static budgetbuddy.commons.util.AppUtil.getDateFormatter;
import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import budgetbuddy.commons.exceptions.IllegalValueException;
import budgetbuddy.model.attributes.Amount;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Direction;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.loan.Status;
import budgetbuddy.model.person.Person;

/**
 * Jackson-friendly version of {@link budgetbuddy.model.loan.Loan}.
 */
public class JsonAdaptedLoan {

    public static final String MESSAGE_DATE_ERROR = "Error reading stored date.";

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Loan's %s field is missing!";

    private final String personName;
    private final String direction;
    private final long amount;
    private final String date;
    private final String description;
    private final String status;

    /**
     * Constructs a {@code JsonAdaptedLoan} with the given loan details.
     */
    @JsonCreator
    public JsonAdaptedLoan(@JsonProperty("personName") String personName,
                           @JsonProperty("direction") String direction,
                           @JsonProperty("amount") long amount,
                           @JsonProperty("date") String date,
                           @JsonProperty("description") String description,
                           @JsonProperty("status") String status) {
        requireAllNonNull(personName, direction, amount, date, description, status);
        this.personName = personName;
        this.direction = direction;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.status = status;
    }

    /**
     * Converts a given {@code Loan} into this class for Jackson use.
     * @param source
     */
    public JsonAdaptedLoan(Loan source) {
        personName = source.getPerson().getName().toString();
        direction = source.getDirection().toString();
        amount = source.getAmount().toLong();
        date = getDateFormatter().format(source.getDate());
        description = source.getDescription().toString();
        status = source.getStatus().toString();
    }

    /**
     * Converts this Jackson-friendly adapted loan object into the model's {@code Loan} object.
     * @throws IllegalValueException If any data constraints were violated in the adapted loan.
     */
    public Loan toModelType() throws IllegalValueException {
        Person person = new Person(getValidatedName());
        return new Loan(person, getValidatedDirection(), getValidatedAmount(),
                getValidatedDate(), getValidatedDescription(), getValidatedStatus());
    }

    /**
     * Validates and converts the adapted name into the model's {@code Name} object.
     * @return The validated and converted name.
     * @throws IllegalValueException If validation fails.
     */
    private Name getValidatedName() throws IllegalValueException {
        if (personName == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Person.class.getSimpleName()));
        }
        if (!Name.isValidName(personName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(personName);
    }

    /**
     * Validates and converts the adapted direction into the model's {@code Direction} object.
     * @return The validated and converted direction.
     * @throws IllegalValueException If validation fails.
     */
    private Direction getValidatedDirection() throws IllegalValueException {
        if (direction == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Direction.class.getSimpleName()));
        }
        if (!Direction.isValidDirection(direction)) {
            throw new IllegalValueException(Direction.MESSAGE_CONSTRAINTS);
        }
        return Direction.valueOf(direction);
    }

    /**
     * Validates and converts the adapted amount into the model's {@code Amount} object.
     * @return The validated and converted amount.
     * @throws IllegalValueException If validation fails.
     */
    private Amount getValidatedAmount() throws IllegalValueException {
        if (!Amount.isValidAmount(amount)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        } else if (amount <= 0) {
            throw new IllegalValueException(Loan.MESSAGE_AMOUNT_POSITIVE_CONSTRAINT);
        }
        return new Amount(amount);
    }

    /**
     * Validates the adapted date as a {@code LocalDate} object.
     * @return The validated date.
     * @throws IllegalValueException If validation fails.
     */
    private LocalDate getValidatedDate() throws IllegalValueException {
        try {
            return LocalDate.parse(date, getDateFormatter());
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(MESSAGE_DATE_ERROR);
        }
    }

    /**
     * Validates and converts the adapted description into the model's {@code Description} object.
     * @return The validated and converted description.
     * @throws IllegalValueException If validation fails.
     */
    private Description getValidatedDescription() throws IllegalValueException {
        if (description == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(description);
    }

    /**
     * Validates and converts the adapted status into the model's {@code Status} object.
     * @return The validated and converted status.
     * @throws IllegalValueException If validation fails.
     */
    private Status getValidatedStatus() throws IllegalValueException {
        if (status == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName()));
        }
        if (!Status.contains(status)) {
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        }
        return Status.valueOf(status);
    }
}
