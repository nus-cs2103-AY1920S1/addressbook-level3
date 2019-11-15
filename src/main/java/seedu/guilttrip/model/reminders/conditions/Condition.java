package seedu.guilttrip.model.reminders.conditions;

import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.commons.util.ListenerSupport;
import seedu.guilttrip.commons.util.ObservableSupport;
import seedu.guilttrip.model.entry.Entry;
import seedu.guilttrip.ui.reminder.ReminderListEntry;

/**
 * Tells generalReminder when to activate. All types of conditions extend form this class.
 * Functions as an observable.
 */
public abstract class Condition implements ReminderListEntry {
    private String conditionType;
    private Predicate<Entry> pred;
    private ObservableSupport support = new ObservableSupport();
    private Entry beingAdded = null;
    private Entry beingRemoved = null;
    private final Logger logger = LogsCenter.getLogger(getClass());

    public Condition(String conditionType) {
        this.conditionType = conditionType;
    }
    public String getConditionType() {
        return conditionType;
    }
    protected Predicate<Entry> getPred() {
        return pred;
    }
    protected void setPred(Predicate<Entry> pred) {
        this.pred = pred;
    }
    protected Entry getBeingAdded() {
        return beingAdded;
    }
    protected Entry getBeingRemoved() {
        return beingRemoved;
    }
    /**
     * checks if condition is met. Triggered when entry is added/ entry is replacing another entry.
     * @param entry
     */
    public void addEntryUpdate(Entry entry) {
        if (pred.test(entry)) {
            logger.info("Entry added: condition met. Updating "
                    + support.getPropertyChangeListeners().size() + " generalReminder(s)");
            support.firePropertyChange("beingAdded", beingAdded, entry);
            beingAdded = entry;
        }
    }
    /**
     * checks if condition is met. Triggered when entry is removed/ entry is being replaced by another entry.
     * @param entry
     */
    public void removeEntryUpdate(Entry entry) {
        if (pred.test(entry)) {
            support.firePropertyChange("beingRemoved", null, entry);
            beingRemoved = entry;
        }
    }
    public ObservableSupport getSupport() {
        return support;
    }
    public void addPropertyChangeListener(ListenerSupport pcl) {
        support.addPropertyChangeListener(pcl);
    }
    public void removePropertyChangeListener(ListenerSupport pcl) {
        support.removePropertyChangeListener(pcl);
    }
    @Override
    public boolean equals(Object other) {
        if (this instanceof TypeCondition) {
            if (other instanceof TypeCondition) {
                return ((TypeCondition) this).equals((TypeCondition) other);
            } else {
                return false;
            }
        } else if (this instanceof DateCondition) {
            if (other instanceof DateCondition) {
                return this.equals(other);
            } else {
                return false;
            }
        } else if (this instanceof KeyWordsCondition) {
            if (other instanceof KeyWordsCondition) {
                return this.equals(other);
            } else {
                return false;
            }
        } else if (this instanceof QuotaCondition) {
            if (other instanceof QuotaCondition) {
                return this.equals(other);
            } else {
                return false;
            }
        } else if (this instanceof TagsCondition) {
            if (other instanceof TagsCondition) {
                return this.equals(other);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
