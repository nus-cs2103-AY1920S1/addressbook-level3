package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.util.Frequency;

/**
 * An expense factory, basically.
 */
public class AutoExpense extends Entry {

    private static final String ENTRY_TYPE = "AutoExpense";
    private final Date lastTime;
    private final Frequency freq;

    public AutoExpense(Description desc, Amount amount, Set<Tag> tags, Frequency freq) {
        super(desc, Date.now(), amount, tags);
        this.lastTime = this.getDate();
        this.freq = freq;
    }

    public String getType() {
        return this.ENTRY_TYPE;
    }

    /**
     * Get the next time it is supposed to but have not occurred yet.
     *
     * @return
     */
    public Date getNextTime() {
        return lastTime.plus(freq);
    }

    /**
     * Get the last time it created a Expense object.
     *
     * @return
     */
    public Date getLastTime() {
        return lastTime;
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
        return otherAutoExpense.getDesc().equals(getDesc())
                && otherAutoExpense.getAmount().equals(getAmount())
                && otherAutoExpense.getTags().equals(getTags())
                && otherAutoExpense.getFrequency().equals(getFrequency());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(ENTRY_TYPE + ": ").append(getDesc()).append(" Amount: ").append(getAmount())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append("( every " + freq + ", last updated:" + getLastTime() + ")");
        return builder.toString();
    }

}
