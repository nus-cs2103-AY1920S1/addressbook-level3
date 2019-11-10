package budgetbuddy.model.loan;

import static budgetbuddy.commons.util.AppUtil.getDateFormatter;
import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Objects;

import budgetbuddy.model.attributes.Amount;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Direction;
import budgetbuddy.model.person.Person;

/**
 * Represents a loan.
 * Guarantees: details are present and not null, field values are validated and immutable.
 */
public class Loan {

    public static final String MESSAGE_AMOUNT_POSITIVE_CONSTRAINT = "Amounts for loans must be positive numbers.";

    private final Person person;
    private final Direction direction;
    private final Amount amount;
    private final LocalDate date;
    private final Description description;
    private final Status status;

    /**
     * Every field must be present and not null.
     */
    public Loan(Person person, Direction direction, Amount amount,
                LocalDate date, Description description, Status status) {
        requireAllNonNull(person, direction, amount, date, description, status);
        this.person = person;
        this.direction = direction;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.status = status;
    }

    public Person getPerson() {
        return person;
    }

    public Direction getDirection() {
        return direction;
    }

    public Amount getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDateString() {
        return getDateFormatter().format(date);
    }

    public Description getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Checks if the Loan has been paid.
     * @return True if paid, false otherwise.
     */
    public boolean isPaid() {
        return status == Status.PAID;
    }

    /**
     * Returns a {@code string} containing the amount, direction and person of the loan.
     */
    public String getEssentialInfo() {
        return getDirection() == Direction.OUT
                ? String.format("%s owes you %s", getPerson(), getAmount())
                : String.format("You owe %s %s", getPerson(), getAmount());
    }

    /**
     * Returns true if all fields (EXCEPT status) of a given loan are identical to this one.
     * @param loan The loan to check.
     */
    public boolean isSameLoan(Loan loan) {
        return getPerson().equals(loan.getPerson())
                && getDirection().equals(loan.getDirection())
                && getAmount().equals(loan.getAmount())
                && getDate().equals(loan.getDate())
                && getDescription().equals(loan.getDescription());
    }

    /**
     * Checks all fields of a Loan for equality (person, direction, amount, date, description, status).
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Loan)) {
            return false;
        }

        Loan otherLoan = (Loan) other;
        return otherLoan.getPerson().isSamePerson(person)
                && otherLoan.getDirection() == direction
                && otherLoan.getAmount().equals(amount)
                && otherLoan.getDate().equals(date)
                && otherLoan.getDescription().equals(description)
                && otherLoan.getStatus().equals(status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, direction, amount, description, date, status);
    }

    @Override
    public String toString() {
        String directionWithFunctionWord = getDirection() == Direction.OUT
                ? getDirection().direction.toLowerCase() + " to"
                : getDirection().direction.toLowerCase() + " from";

        final String divider = " | ";
        final StringBuilder builder = new StringBuilder();
        builder.append(getStatus().getStatusIcon()).append(" ")
                .append(getAmount()).append(" ")
                .append(directionWithFunctionWord).append(" ")
                .append(getPerson())
                .append(divider)
                .append(getDateString());
        if (!description.getDescription().isBlank()) {
            builder.append(divider).append(getDescription());
        }
        return builder.toString();
    }
}
