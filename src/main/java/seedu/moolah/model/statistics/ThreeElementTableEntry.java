package seedu.moolah.model.statistics;

import seedu.moolah.model.general.Category;

/**
 * Represents a row in a table with 3 elements. This class is used mainly by the TabularStatistics class.
 */
public class ThreeElementTableEntry {

    private String name;

    private double amount;

    private int numEntries;

    ThreeElementTableEntry(Category category, double amount, int numEntries) {
        this.name = category.getCategoryName();
        this.amount = amount;
        this.numEntries = numEntries;
    }

    ThreeElementTableEntry(String name, double amount, int numEntries) {
        this.name = name;
        this.amount = amount;
        this.numEntries = numEntries;
    }

    static ThreeElementTableEntry createEmptyEntry() {
        return new ThreeElementTableEntry("EMPTY", 0, 0);
    }

    /**O
     * Returns a new TableEntry with its attributes from deducting the callee's attributes from the caller
     * @param other The other Table Entry
     */
    ThreeElementTableEntry minus(ThreeElementTableEntry other) {
        return new ThreeElementTableEntry(this.name, this.amount - other.amount,
                this.numEntries - other.numEntries);
    }

    /**
     * Adds the new values to the existing values in the table entry
     */
    public ThreeElementTableEntry add(ThreeElementTableEntry other) {
        return new ThreeElementTableEntry(this.name, this.amount + other.amount,
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
