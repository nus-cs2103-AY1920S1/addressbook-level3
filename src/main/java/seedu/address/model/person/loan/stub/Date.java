package seedu.address.model.person.loan.stub;

/**
 * Represents the date of a loan.
 * Guarantees: immutable.
 */
public class Date {

    public final String date;

    public Date(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Date)) {
            return false;
        }

        Date otherDate = (Date) other;
        return date.equals(otherDate.date);
    }
}
