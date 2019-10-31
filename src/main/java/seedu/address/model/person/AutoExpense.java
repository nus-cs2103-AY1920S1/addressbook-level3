package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.util.Frequency;

/**
 * An expense factory, basically.
 */
public class AutoExpense extends Entry {

    private static final String ENTRY_TYPE = "AutoExpense";
    private final Frequency freq;
    private Date lastTime;

    public AutoExpense(Category category, Description desc, Amount amount, Set<Tag> tags, Frequency freq, Date date) {
        super(category, desc, date, amount, tags);
        this.lastTime = this.getDate();
        this.freq = freq;
    }

    public String getType() {
        return this.ENTRY_TYPE;
    }

    public Date getNextTime() {
        return lastTime.plus(freq);
    }

    private Date getLastTime() {
        return lastTime;
    }

    /**
     * Generates a list of Expenses that are not yet generated.
     * Updates internal record.
     *
     * @return a list of Expenses.
     */
    public List<Expense> generateNewExpenses() {
        List<Expense> newExpenses = new ArrayList<>();
        while (getNextTime().lessThanOrEqualsToday()) {
            newExpenses.add(generateNextExpense());
        }
        return newExpenses;
    }

    /**
     * Side effect - updates lastTime to getNextTime.
     *
     * @param date
     * @return
     */
    private Expense generateNextExpense() {
        Expense expense = new Expense(getCategory(), getDesc(), getNextTime(), getAmount(), getTags());
        lastTime = expense.getDate();
        return expense;
    }

    /**
     * Gets the frequency.
     *
     * @return
     */
    public Frequency getFrequency() {
        return freq;
    }

    /**
     * @param lastTime the lastTime to set
     */
    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    /**
     * Returns true if both expenses have the same data fields. This defines a
     * stronger notion of equality between two entries.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // if not (entry and entry.getType() == "AutoExpense") then return false
        if (!(other instanceof Entry)) {
            return false;
        } else if (!(other instanceof AutoExpense)) {
            return false;
        }

        AutoExpense otherAutoExpense = (AutoExpense) other;
        return otherAutoExpense.getDesc().equals(getDesc()) && otherAutoExpense.getAmount().equals(getAmount())
                && otherAutoExpense.getTags().equals(getTags())
                && otherAutoExpense.getFrequency().equals(getFrequency())
                && otherAutoExpense.getDate().equals(getDate());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(ENTRY_TYPE + ": ").append(getDesc()).append(" Amount: ").append(getAmount()).append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append("( every " + freq + ", last updated:" + getLastTime() + ")");
        return builder.toString();
    }

}
