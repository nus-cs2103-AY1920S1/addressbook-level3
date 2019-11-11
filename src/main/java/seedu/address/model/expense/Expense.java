package seedu.address.model.expense;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.expense.Currency.DEFAULT_BASE_CURRENCY;

import java.util.Comparator;
import java.util.Objects;

import seedu.address.model.exchangedata.ExchangeDataSingleton;
import seedu.address.model.tag.Tag;

/**
 * Represents an expense in the expense list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Expense implements Comparable<Expense> {

    // Identity fields
    private final Name name;
    private final Amount amount;
    private final Currency currency;

    // Data Fields
    private final Date date;
    private final Tag tag;

    /**
     * Every field must be present and not null.
     */
    public Expense(Name name, Amount amount, Currency currency, Date date, Tag tag) {
        requireAllNonNull(name, amount, currency, date, tag);
        this.name = name;
        this.amount = amount;
        this.currency = currency;
        this.date = date;
        this.tag = tag;
    }

    public Name getName() {
        return name;
    }

    public Amount getConvertedAmount() {
        double convertedAmount = amount.getValue() / currency.getRate();
        return new Amount(String.format("%.2f", convertedAmount));
    }

    public Amount getConvertedAmount(Currency targetCurrency) {
        // Convert back to base
        double amountAsBase = amount.getValue() / currency.getRate();
        double amountAsTarget = amountAsBase * ExchangeDataSingleton.getInstance()
            .getRates().getRate(targetCurrency.name);
        return new Amount(String.format("%.2f", amountAsTarget));
    }

    public Amount getAmount() {
        return amount;
    }

    public boolean isForeign() {
        return !currency.name.equals(DEFAULT_BASE_CURRENCY);
    }

    public Currency getCurrency() {
        return currency;
    }

    public Date getDate() {
        return date;
    }

    public Tag getTag() {
        return tag;
    }

    /**
     * Returns true if both expenses of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two expenses.
     */
    public boolean isSameExpense(Expense otherExpense) {
        if (otherExpense == this) {
            return true;
        }

        return otherExpense != null
            && otherExpense.getName().equals(getName())
            && otherExpense.getAmount().equals(getAmount())
            && otherExpense.getCurrency().equals(getCurrency())
            && otherExpense.getDate().equals(getDate());
    }

    /**
     * Returns true if both expenses have the same identity and data fields.
     * This defines a stronger notion of equality between two expenses.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Expense)) {
            return false;
        }

        Expense otherExpense = (Expense) other;
        return otherExpense.getName().equals(getName())
            && otherExpense.getAmount().equals(getAmount())
            && otherExpense.getCurrency().equals(getCurrency())
            && otherExpense.getDate().equals(getDate())
            && otherExpense.getTag().equals(getTag());
    }

    @Override
    public int compareTo(Expense other) {
        if (date.localDate.isBefore(other.getDate().localDate)) {
            return -1;
        } else if (date.localDate.isAfter(other.getDate().localDate)) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, amount, currency, date, tag);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("\n")
            .append("Name: " + getName())
            .append("\n");
        if (isForeign()) {
            builder.append("Amount: " + getConvertedAmount())
                .append(" " + DEFAULT_BASE_CURRENCY)
                .append(" (" + String.format("%.2f", getAmount().getValue()))
                .append(" " + getCurrency().toString())
                .append(")");
        } else {
            builder.append("Amount: " + getAmount())
                .append(" " + getCurrency().name);
        }
        builder.append("\n")
            .append("Date: " + getDate())
            .append("\n")
            .append("Tag: " + getTag());
        return builder.toString();
    }

    /**
     * Return Comparator to sort data automatically
     */
    public static class SortByDate implements Comparator<Expense> {

        public int compare(Expense a, Expense b) {
            return a.compareTo(b);
        }
    }
}
