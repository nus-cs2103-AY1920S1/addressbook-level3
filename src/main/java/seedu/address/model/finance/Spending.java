package seedu.address.model.finance;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class Spending {
    private final Double spending;
    private final Date date;
    private final String description;
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy");
    public static final String VALIDATION_REGEX = "\\d+.\\d{1,2}?";
    public static final String MESSAGE_CONSTRAINTS = "Spending should be in the form of description in text format, amount in xx.xxformat and date in dd/MM/yy format";

    public Spending(Double spending, Date date, String description) {
        requireAllNonNull(spending, date, description);
        this.spending = spending;
        this.date = date;
        this.description = description;
    }

    public Double getSpending() {
        return spending;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Return is a string is a valid money amount.
     */
    public static boolean isValidAmount(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Return is a string is a valid date.
     */
    public static boolean isValidDate(String test) {
        try {
            DATE_FORMAT.parse(test);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("Spent $%f for %s on %s ", spending, description, DATE_FORMAT.format(date));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Spending)) {
            return false;
        }

        Spending otherSpending = (Spending) other;
        return otherSpending.getSpending().equals(getSpending())
                && otherSpending.getDate().equals(getDate())
                && otherSpending.getDescription().equals(getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, date, spending);
    }
}
