package seedu.address.model.reminders.conditions;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Entry;

/**
 * Tells reminder when to activate. All types of conditions extend form this class.
 * Functions as an observable.
 */
public abstract class Condition {
    private String conditionType;
    private Predicate<Entry> pred;
    private PropertyChangeSupport support = new PropertyChangeSupport(this);
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
            support.firePropertyChange("beingAdded", beingAdded, entry);
            logger.info("Entry added: condition met. Updating "
                    + support.getPropertyChangeListeners().length + " reminder(s)");
            beingAdded = entry;
        }
    }
    /**
     * checks if condition is met. Triggered when entry is removed/ entry is being replaced by another entry.
     * @param entry
     */
    public void removeEntryUpdate(Entry entry) {
        if (pred.test(entry)) {
            support.firePropertyChange("beingRemoved", beingRemoved, entry);
            beingRemoved = entry;
        }
    }
    public PropertyChangeSupport getSupport() {
        return support;
    }
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }
    @Override
    public boolean equals(Object other) {
        if (this instanceof ClassCondition) {
            if (other instanceof ClassCondition) {
                return ((ClassCondition) this).equals((ClassCondition) other);
            } else {
                return false;
            }
        } else if (this instanceof DateCondition) {
            if (other instanceof DateCondition) {
                return ((DateCondition) this).equals((DateCondition) other);
            } else {
                return false;
            }
        } else if (this instanceof KeyWordsCondition) {
            if (other instanceof KeyWordsCondition) {
                return ((KeyWordsCondition) this).equals((KeyWordsCondition) other);
            } else {
                return false;
            }
        } else if (this instanceof QuotaCondition) {
            if (other instanceof QuotaCondition) {
                return ((QuotaCondition) this).equals((QuotaCondition) other);
            } else {
                return false;
            }
        } else if (this instanceof TagsCondition) {
            if (other instanceof TagsCondition) {
                return ((TagsCondition) this).equals((TagsCondition) other);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
