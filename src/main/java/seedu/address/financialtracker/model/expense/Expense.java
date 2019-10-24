package seedu.address.financialtracker.model.expense;

import seedu.address.financialtracker.ui.CountriesDropdown;

public class Expense {

    private Amount amount;
    private Description desc;
    private Country country;
    private Type type;

    public Expense(Amount amount, Description desc, Type type) {
        this.amount = amount;
        this.desc = desc;
        this.type = type;
        this.country = new Country(CountriesDropdown.getDropdownText());
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

    public void setCountry(Country country) {
        this.country = country;
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
        return otherExpense.amount.value.equals(this.amount.value)
                && otherExpense.desc.value.equals(this.desc.value)
                && otherExpense.type.value.equals(this.type.value)
                && otherExpense.country.value.equals(this.country.value);
    }
}
