package seedu.address.model.reminders;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.Period;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Description;
import seedu.address.model.person.Entry;
import seedu.address.model.reminders.conditions.Condition;
import seedu.address.model.reminders.conditions.EntrySpecificCondition;

/**
 * Basic reminder class with minimal functionality.
 */
public class Reminder implements PropertyChangeListener {
    private boolean isEntrySpecific;
    private Status status = Status.unmet;
    private Description message;
    private List<Condition> conditions;
    private long numberOfConditions;
    private long numberOfConditionsMet = 0;
    private TrackerType trackerType = TrackerType.none;
    private double currSum;
    private double trackerQuota;
    private Entry beingAdded;
    private Entry beingRemoved;
    private final Logger logger = LogsCenter.getLogger(getClass());
    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * A tracker keeps track of the number/ total amount of entries meeting the predicate.
     */
    public enum TrackerType {
        none,
        num,
        amount;

        /**
         * parses string to TrackerType
         * @param trackerType
         * @return
         */
        public static TrackerType parse(String trackerType) {
            switch (trackerType) {
            case "num":
                return TrackerType.num;
            case "amount":
                return TrackerType.amount;
            default:
                return TrackerType.none;
            }
        }
        @Override
        public String toString() {
            switch(this) {
            case num:
                return "num";
            case amount:
                return "amount";
            default:
                return "none";

            }
        }
    }
    /**
     * Status of condition
     */
    public enum Status {
        unmet,
        met,
        exceeded;

        /**
         * Converts string to status enum.
         * @param status
         * @return
         */
        public static Status parse (String status) {
            switch (status.toLowerCase()) {
            case "met":
                return met;
            case "exceeded":
                return exceeded;
            default:
                return unmet;
            }
        }
    }

    public Reminder(Description message, List<Condition> conditions) {
        this.message = message;
        this.conditions = conditions;
        for (Condition condition : conditions) {
            condition.addPropertyChangeListener(this);
        }
        numberOfConditions = conditions.size();
    }

    public boolean getEntrySpecificity() {
        return isEntrySpecific;
    }

    public void setEntrySpecificity(boolean isEntrySpecific) {
        this.isEntrySpecific = isEntrySpecific;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = Status.parse(status);
    }

    public Description getMessage() {
        return message;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    /**
     * adds condition to condition list
     * @param condition
     */
    public boolean addCondition(Condition condition) {
        if (conditions.contains(condition)) {
            return false;
        } else {
            conditions.add(condition);
            condition.getSupport().addPropertyChangeListener(this);
            numberOfConditions += 1;
            return true;
        }
    }

    /**
     * removes condition to condition list
     * @param condition
     */
    public boolean removeCondition(Condition condition) {
        if (conditions.contains(condition)) {
            conditions.remove(condition);
            condition.getSupport().removePropertyChangeListener(this);
            numberOfConditions -= 1;
            return true;
        } else {
            return false;
        }
    }

    public void setTracker(TrackerType trackerType, double quota) {
        if (!trackerType.equals(TrackerType.none)) {
            this.currSum = 0;
            this.trackerQuota = quota;
        } else {
        }
    }
    public void setTracker(String trackerType, double currSum, double quota) {
        this.trackerType = TrackerType.parse(trackerType);
        if (!trackerType.equals(TrackerType.none)) {
            this.currSum = currSum;
            this.trackerQuota = quota;
        }
    }
    public TrackerType getTrackerType() {
        return trackerType;
    }

    public double getCurrSum() {
        return currSum;
    }

    public double getTrackerQuota() {
        return trackerQuota;
    }

    /**
     * This is what allows the reminder to be notified when conditions are metb
     * and be added removed from the active reminder list.
     * @param evt
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("beingAdded")) {
            logger.info("Condition met: Updating status");
            Entry entryExamined = (Entry) evt.getNewValue();
            if (status.equals(Status.unmet) || !trackerType.equals(TrackerType.none)) {
                if (entryExamined == beingAdded) {
                    numberOfConditionsMet += 1;
                } else {
                    numberOfConditionsMet = 1;
                    beingAdded = entryExamined;
                }
                if (numberOfConditionsMet == numberOfConditions) {
                    if (trackerType.equals(TrackerType.none)) {
                        support.firePropertyChange("status", status, Status.met);
                        status = Status.met;
                        logger.info("All condition met. Setting Reminder status to " + status.toString());
                    } else {
                        addToTracker(entryExamined);
                    }
                }
            }
        } else if (evt.getPropertyName().equals("beingRemoved")) {
            Entry entryExamined = (Entry) evt.getNewValue();
            if (entryExamined == beingRemoved) {
                numberOfConditionsMet += 1;
                if (numberOfConditionsMet == numberOfConditions) {
                    if (trackerType.equals(TrackerType.none)) {
                        if ((beingAdded == beingRemoved) && status.equals(Status.met)) {
                            support.firePropertyChange("status", status, Status.unmet);
                            status = Status.unmet;
                            numberOfConditions = 0;
                        }
                    } else {
                        removeFromTracker(entryExamined);
                    }
                }
            } else {
                numberOfConditionsMet = 1;
                beingRemoved = entryExamined;
            }
        } else if (evt.getPropertyName().equals("timeBeforeDeadline")) {
            Period timeBeforeDeadline = (Period) evt.getNewValue();
            if (timeBeforeDeadline.isZero()) {
                support.firePropertyChange("status", status, Status.met);
                status = Status.met;
            } else if (timeBeforeDeadline.isNegative()) {
                support.firePropertyChange("status", status, Status.exceeded);
                status = Status.exceeded;
            } else {
                support.firePropertyChange("status", status, Status.unmet);
                status = Status.unmet;
            }
        }
    }
    /**
     * Updates currSum when an entry fulfilling pred is added.
     * @param entry
     */
    private void addToTracker(Entry entry) {
        switch (trackerType) {
        case num:
            currSum += 1;
            updateTrackerStatus();
            break;
        case amount:
            currSum += entry.getAmount().value;
            updateTrackerStatus();
            break;
        default:
        }
    }

    /**
     * Updates currSum when an entry fulfilling pred is removed.
     * @param entry
     */
    private void removeFromTracker(Entry entry) {
        switch (trackerType) {
        case num:
            currSum -= 1;
            updateTrackerStatus();
            break;
        case amount:
            currSum -= entry.getAmount().value;
            updateTrackerStatus();
            break;
        default:
        }
    }
    /**
     * Uses currSum and Quota to get status.
     */
    private void updateTrackerStatus() {
        if (currSum == trackerQuota) {
            support.firePropertyChange("status", status, Status.met);
            status = Status.met;
        } else if (currSum > trackerQuota) {
            support.firePropertyChange("status", status, Status.exceeded);
            status = Status.exceeded;
        } else {
            support.firePropertyChange("status", status, Status.unmet);
            status = Status.unmet;
        }
    }

    /**
     * Resets the reminder to the original state when it was created.
     * Currently used for passing entrySpecificConditions from one entry to another entry when
     * a type of set entry command is called.
     */
    public void reset() {
        status = Status.unmet;
        currSum = 0;
        if (conditions.get(0) instanceof EntrySpecificCondition) {
            EntrySpecificCondition thisCondition = (EntrySpecificCondition) conditions.get(0);
            thisCondition.update();
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
    public String toString() {
        return "(" + status.toString() + ") " + message;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof Reminder)) {
            return false;
        } else {
            Reminder otherReminder = (Reminder) other;
            return this.message.equals(otherReminder.getMessage())
                    //&& this.priority.equals(otherReminder.getPriority())
                    && this.conditions.stream()
                    .allMatch(condition -> otherReminder.getConditions().contains(condition));
        }
    };
}
