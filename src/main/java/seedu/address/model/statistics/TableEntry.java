package seedu.address.model.statistics;

import seedu.address.model.category.Category;

/**
 * Utility class representing a row in a table. This class is used mainly by the TabularStatistics class.
 */
public class TableEntry {

    private String name;

    private double amount;

    private int numEntries;

    TableEntry(Category category, double amount, int numEntries) {
        this.name = category.getCategoryName();
        this.amount = amount;
        this.numEntries = numEntries;
    }

    TableEntry(String name, double amount, int numEntries) {
        this.name = name;
        this.amount = amount;
        this.numEntries = numEntries;
    }

    static TableEntry createEmptyEntry() {
        return new TableEntry("EMPTY", 0, 0);
    }

    /**O
     * Returns a new TableEntry with its attributes from deducting the callee's attributes from the caller
     * @param other The other Table Entry
     */
    TableEntry minus(TableEntry other) {
        return new TableEntry(this.name, this.amount - other.amount,
                this.numEntries - other.numEntries);
    }

    /**
     * Adds the new values to the existing values in the table entry
     */
    public TableEntry add(TableEntry other) {
        return new TableEntry(this.name, this.amount + other.amount,
                this.numEntries + other.numEntries);
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public int getNumEntries() {
        return numEntries;
    }

    public String toString() {
        return String.format("Category: %s, Amount Spent($): %.2f, numEntries %d", name, amount, numEntries);
    }
}
