package budgetbuddy.storage.loans;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import budgetbuddy.commons.exceptions.IllegalValueException;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Direction;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.loan.LoanList;
import budgetbuddy.model.loan.Status;
import budgetbuddy.model.person.Person;
import budgetbuddy.model.transaction.Amount;

/**
 * Jackson-friendly version of {@link budgetbuddy.model.loan.Loan}.
 */
public class JsonAdaptedLoan {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Loan's %s field is missing!";

    private final String personName;
    private final String direction;
    private final long amount;
    private final Date date;
    private final String description;
    private final String status;

    /**
     * Constructs a {@code JsonAdaptedLoan} with the given loan details.
     */
    public JsonAdaptedLoan(@JsonProperty("personName") String personName,
                           @JsonProperty("direction") String direction,
                           @JsonProperty("amount") long amount,
                           @JsonProperty("date") Date date,
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
        date = source.getDate();
        description = source.getDescription().toString();
        status = source.getStatus().toString();
    }

    /**
     * Converts this Jackson-friendly adapted loan object into the model's {@code Loan} object.
     * @throws IllegalValueException If any data constraints were violated in the adapted loan.
     */
    public Loan toModelType() throws IllegalValueException {
        Person person = new Person(checkName());
        return new Loan(person, checkDirection(), checkAmount(),
                checkDate(), checkDescription(), checkStatus());
    }

    /**
     * Checks that adapted name can be converted into model's {@code Name} object.
     * @throws IllegalValueException If adapted object cannot be converted.
     */
    private Name checkName() throws IllegalValueException {
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
     * Checks that adapted direction can be converted into model's {@code Direction} object.
     * @throws IllegalValueException If adapted object cannot be converted.
     */
    private Direction checkDirection() throws IllegalValueException {
        if (direction == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Direction.class.getSimpleName()));
        }
        if (!Direction.contains(direction)) {
            throw new IllegalValueException(Direction.MESSAGE_CONSTRAINTS);
        }
        return Direction.valueOf(direction);
    }

    /**
     * Checks that adapted amount can be converted into model's {@code Amount} object.
     * @throws IllegalValueException If adapted object cannot be converted.
     */
    private Amount checkAmount() throws IllegalValueException {
        if (!Amount.isValidAmount(amount)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }
        return new Amount(amount);
    }

    /**
     * Checks that adapted date can be converted into model's {@code Date} object.
     * @throws IllegalValueException If adapted object cannot be converted.
     */
    private Date checkDate() throws IllegalValueException {
        // TODO Check validity of date.
        return date;
    }

    /**
     * Checks that adapted description can be converted into model's {@code Description} object.
     * @throws IllegalValueException If adapted object cannot be converted.
     */
    private Description checkDescription() throws IllegalValueException {
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
     * Checks that adapted status can be converted into model's {@code Status} object.
     * @throws IllegalValueException If adapted object cannot be converted.
     */
    private Status checkStatus() throws IllegalValueException {
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
