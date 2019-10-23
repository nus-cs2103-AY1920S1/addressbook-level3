package budgetbuddy.model.loan;

import static budgetbuddy.commons.util.AppUtil.getDateFormat;
import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Direction;
import budgetbuddy.model.person.Person;
import budgetbuddy.model.transaction.Amount;

/**
 * Represents a loan.
 * Guarantees: details are present and not null, field values are validated and immutable.
 */
public class Loan {

    private final Person person;
    private final Direction direction;
    private final Amount amount;
    private final Date date;
    private final Description description;

    private Status status;

    /**
     * Every field must be present and not null.
     */
    public Loan(Person person, Direction direction, Amount amount,
                Date date, Description description, Status status) {
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

    public Date getDate() {
        return date;
    }

    public String getDateString() {
        return getDateFormat().format(date);
    }

    public Description getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Checks if the Loan has been paid.
     * @return True if paid, false otherwise.
     */
    public boolean isPaid() {
        return status == Status.PAID;
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
                && otherLoan.getDescription().equals(description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, direction, amount, description, date, status);
    }

    @Override
    public String toString() {
        String directionWithFunctionWord = getDirection() == Direction.OUT
                ? getDirection().direction.toLowerCase() + " to "
                : getDirection().direction.toLowerCase() + " from ";

        final String divider = " | ";
        final StringBuilder builder = new StringBuilder();
        builder.append(getStatus().getStatusIcon()).append(" ")
                .append(getAmount()).append(" ")
                .append(directionWithFunctionWord)
                .append(getPerson().getName()).append(divider)
                .append(getDateString());
        if (!description.getDescription().isBlank()) {
            builder.append(divider).append(getDescription());
        }
        return builder.toString();
    }
}
