package seedu.guilttrip.model.reminders;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.time.Period;
import java.util.List;
import java.util.logging.Logger;

import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.entry.Entry;
import seedu.guilttrip.model.reminders.conditions.Condition;
import seedu.guilttrip.model.reminders.conditions.EntrySpecificCondition;
import seedu.guilttrip.model.reminders.messages.Message;

/**
 * Basic reminder class with minimal functionality.
 */
public class Reminder implements PropertyChangeListener {
    public static final String RESETWHENDISPLAYED = "RESETS";
    public static final String ACTIVEUNTILEXCEEDED = "ACTIVE TILL EXCEEDED";
    private String postDisplay;

    private boolean isEntrySpecific;
    private Description header;

    private Entry beingAdded;

    private List<Condition> conditions;
    private long numberOfConditions;
    private long numberOfConditionsMet = 0;
    private Status status = Status.unmet;

    private boolean displayPopUp;
    private seedu.guilttrip.model.reminders.messages.Message message;

    private final Logger logger = LogsCenter.getLogger(getClass());
    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    //===== Constructor =====//
    public Reminder(Description header, List<Condition> conditions) {
        this.header = header;
        this.conditions = conditions;
        for (Condition condition : conditions) {
            condition.addPropertyChangeListener(this);
        }
        numberOfConditions = conditions.size();
    }

    //===== getters and setters =====//
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

    public Description getHeader() {
        return header;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    //===== Condition handlers =====//
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

    // ===== Property Change Listener and Updating status =====//
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
            if (status.equals(Status.unmet)) {
                if (entryExamined == beingAdded) {
                    numberOfConditionsMet += 1;
                } else {
                    numberOfConditionsMet = 1;
                    beingAdded = entryExamined;
                }
                if (numberOfConditionsMet == numberOfConditions) {
                    message.update(entryExamined);
                    support.firePropertyChange("status", status, Status.met);
                    status = Status.met;
                    logger.info("All condition met. Setting Reminder status to " + status.toString());
                }
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

    public PropertyChangeSupport getSupport() {
        return support;
    }
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }
    // ===== reminder display behavior =====//

    public boolean willDisplayPopUp() {
        return displayPopUp;
    }

    public void togglePopUpDisplay(boolean willDisplayPopup) {
        this.displayPopUp = willDisplayPopup;
        if (displayPopUp && message == null) {
            message = new Message(header.toString(),2,2);
        }
    }

    public Message getMessage() {
        return message;
    }

    //===== post-displayed behavior =====//


    public String getPostDisplay() {
        return postDisplay;
    }

    public void setPostDisplay(String postDisplay) {
        this.postDisplay = postDisplay;
    }

    /**
     * Resets the reminder to the original state when it was created.
     * Currently used for passing entrySpecificConditions from one entry to another entry when
     * a type of set entry command is called.
     */
    public void reset() {
        status = Status.unmet;
        if (conditions.get(0) instanceof EntrySpecificCondition) {
            EntrySpecificCondition thisCondition = (EntrySpecificCondition) conditions.get(0);
            thisCondition.update();
        }
    }

    //===== standard methods =====//

    @Override
    public String toString() {
        return "(" + status.toString() + ") " + header;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof Reminder)) {
            return false;
        } else {
            Reminder otherReminder = (Reminder) other;
            return this.header.equals(otherReminder.getHeader())
                    //&& this.priority.equals(otherReminder.getPriority())
                    && this.conditions.stream()
                    .allMatch(condition -> otherReminder.getConditions().contains(condition));
        }
    }

    /**
     * Status of condition
     */
    public enum Status {
        unmet,
        met,
        active,
        exceeded,
        expired;

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
            case "active":
                return active;
            default:
                return unmet;
            }
        }
    }
}
