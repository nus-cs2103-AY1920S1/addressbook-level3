package seedu.address.financialtracker.model.expense;

import static java.util.Objects.requireNonNull;

/**
 * An expense.
 */
public class Expense {

    private Date date;
    private Time time;
    private Amount amount;
    private Description desc;
    private Country country;
    private Type type;

    /**
     * Instantiates an expense object. Can only be used when the UI is working properly.
     */
    public Expense(Date date, Time time, Amount amount, Description desc, Type type) {
        this.date = date;
        this.time = time;
        this.amount = amount;
        this.desc = desc;
        this.type = type;
        this.country = null;
    }

    /**
     * Another constructor used to directly construct with the country field.
     */
    public Expense(Date date, Time time, Amount amount, Description desc, Type type, Country country) {
        this.date = date;
        this.time = time;
        this.amount = amount;
        this.desc = desc;
        this.type = type;
        this.country = country;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public Amount getAmount() {
        return amount;
    }

    public Country getCountry() {
        return country;
    }

    public Description getDescription() {
        return desc;
    }

    public Type getType() {
        return type;
    }

    /**
     * Allows financial tracker to set this country field to non-null value.
     */
    public void setCountry(Country country) {
        requireNonNull(country);
        this.country = country;
    }

    /**
     * Returns an exact copy of this expense.
     */
    public Expense makeCopy() {
        Amount amountCopy = new Amount(this.amount.value);
        Description descriptionCopy = new Description(this.desc.value);
        Type typeCopy = new Type(this.type.value);
        Date dateCopy = new Date(this.date.storageDate);
        Time timeCopy = new Time(this.time.storageTime);
        Country countryCopy = new Country(this.country.value);
        return new Expense(dateCopy, timeCopy, amountCopy, descriptionCopy, typeCopy, countryCopy);
    }

    /**
     * Returns true if both expense have the same data fields.
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
        return new Double(otherExpense.amount.numericalValue).equals(this.amount.numericalValue)
                && otherExpense.date.getDateToCompare().equals(this.date.getDateToCompare())
                && otherExpense.time.valueToCompare == this.time.valueToCompare
                && otherExpense.desc.value.equals(this.desc.value)
                && otherExpense.type.value.equals(this.type.value)
                && (otherExpense.country == this.country //short circuit if null
                || otherExpense.country.value.equals(this.country.value));
    }
}
