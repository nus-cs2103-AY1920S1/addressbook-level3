package budgetbuddy.storage.loans;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import budgetbuddy.model.loan.Loan;

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
}
