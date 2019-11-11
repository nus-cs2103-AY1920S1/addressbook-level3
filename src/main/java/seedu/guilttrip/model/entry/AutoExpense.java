package seedu.guilttrip.model.entry;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import seedu.guilttrip.model.tag.Tag;
import seedu.guilttrip.model.util.Frequency;

/**
 * An expense factory, basically.
 */
public class AutoExpense extends Entry {

    private static final String ENTRY_TYPE = "AutoExpense";
    private final Frequency freq;
    private Date lastTime;
    private final List<Expense> expensesCreated = new LinkedList<>();

    public AutoExpense(Category category, Description desc, Amount amount, Set<Tag> tags, Frequency freq, Date date) {
        super(category, desc, date, amount, tags);
        this.lastTime = this.getDate();
        this.freq = freq;
    }

    public AutoExpense(Category category, Description desc, Amount amount, Set<Tag> tags, Frequency freq, Date date,
                       Date lastTime) {
        super(category, desc, date, amount, tags);
        this.lastTime = lastTime;
        this.freq = freq;
    }

    public String getType() {
        return ENTRY_TYPE;
    }

    public Date getNextTime() {
        return lastTime.plus(freq);
    }

    public Date getLastTime() {
        return lastTime;
    }

    public boolean isUpToDate() {
        return getNextTime().isAfter(Date.now());
    }

    /**
     * Returns a new AutoExpense if and only if it's category is edited.
     */
    public AutoExpense modifiedAutoExpense(String newName) {
        Category newCategory = new Category(newName, super.getCategory().getCategoryType());
        return new AutoExpense(newCategory, super.getDesc(), this.getAmount(), super.getTags(), this.freq,
                this.getDate());
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
            Expense nextExpense = generateNextExpense();
            newExpenses.add(nextExpense);
            expensesCreated.add(nextExpense);
        }
        return newExpenses;
    }

    /**
     * Side effect - updates lastTime to getNextTime.
     */
    private Expense generateNextExpense() {
        Expense expense = new Expense(getCategory(), getDesc(), getNextTime(), getAmount(), getTags());
        lastTime = expense.getDate();
        return expense;
    }

    /**
     * Gets the frequency.
     */
    public Frequency getFrequency() {
        return freq;
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
        builder.append(getDesc()).append(" Amount: ").append(getAmount()).append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append("(every " + freq + ", last updated: " + getLastTime() + ")");
        return builder.toString();
    }

}
