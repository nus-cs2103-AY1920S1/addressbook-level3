package seedu.address.model.person.loan;

import seedu.address.model.person.loan.stub.Amount;
import seedu.address.model.person.loan.stub.Date;
import seedu.address.model.person.Person;

import java.util.Objects;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Loan in a LoanList.
 * Guarantees: details are present and not null, field values are validated and immutable.
 */
public class Loan {

    private final Person person;
    private final Direction direction;
    private final Amount amount;
    private final Date date;
    private final Description description;
    private final Status status;

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

    public Person getPerson(){
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
        return status.toString().equals("PAID");
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
                && otherLoan.getDirection().equals(direction)
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
        String direction = getDirection().direction.equals("Out")
                ? getDirection().direction.toLowerCase() + " to "
                : getDirection().direction.toLowerCase() + " from ";

        final String divider = " | ";
        final StringBuilder builder = new StringBuilder();
        builder.append(" ").append(getStatus().getStatusIcon()).append(" ")
                .append(getAmount()).append(" ")
                .append(direction)
                .append(getPerson().getName()).append(divider)
                .append(getDate()).append(divider)
                .append(getDescription()).append(divider);
        return builder.toString();
    }
}
