package budgetbuddy.storage.loans;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import budgetbuddy.commons.exceptions.IllegalValueException;
import budgetbuddy.model.Direction;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.loan.LoanList;
import budgetbuddy.model.loan.Status;
import budgetbuddy.model.person.Person;
import budgetbuddy.model.transaction.Amount;
import budgetbuddy.model.transaction.stub.Description;

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
        if (!Name.isValidName(personName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        if (!Direction.contains(direction)) {
            throw new IllegalValueException(Direction.MESSAGE_CONSTRAINTS);
        }
        if (!Amount.isValidAmount(amount)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }
        // TODO Check date validity.
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        if (!Status.contains(status)) {
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        }

        Person person = new Person(new Name(personName), new LoanList());
        Loan loan = new Loan(person, Direction.valueOf(direction), new Amount(amount),
                date, new Description(description), Status.valueOf(status));
        person.addLoan(loan);
        return loan;
    }
}
