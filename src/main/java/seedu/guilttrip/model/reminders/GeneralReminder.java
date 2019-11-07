package seedu.guilttrip.model.reminders;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.Period;
import java.util.List;
import java.util.logging.Logger;

import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.entry.Entry;
import seedu.guilttrip.model.reminders.conditions.Condition;
import seedu.guilttrip.model.reminders.conditions.EntrySpecificCondition;
import seedu.guilttrip.model.reminders.messages.Message;
import seedu.guilttrip.model.reminders.messages.Notification;

/**
 * Basic generalReminder class with minimal functionality.
 */
public class GeneralReminder implements PropertyChangeListener, Reminder{

    private Description header;

    private Entry beingAdded;

    private List<Condition> conditions;
    private long numberOfConditions;
    private long numberOfConditionsMet = 0;
    private Status status = Status.unmet;

    private boolean displayPopUp;
    private Message message;

    private final Logger logger = LogsCenter.getLogger(getClass());
    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    //===== Constructor =====//
    public GeneralReminder(Description header, List<Condition> conditions) {
        this.header = header;
        this.conditions = conditions;
        for (Condition condition : conditions) {
            condition.addPropertyChangeListener(this);
        }
        numberOfConditions = conditions.size();
    }

    //===== getters and setters =====//

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
     * This is what allows the generalReminder to be notified when conditions are metb
     * and be added removed from the active generalReminder list.
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
                    support.firePropertyChange("statusChange", null, Status.met);
                    status = Status.met;
                    logger.info("All condition met. Setting GeneralReminder status to " + status.toString());
                }
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
    // ===== generalReminder display behavior =====//

    public boolean willDisplayPopUp() {
        return displayPopUp;
    }

    @Override
    public void togglePopUpDisplay(boolean willDisplayPopup) {
        this.displayPopUp = willDisplayPopup;
        if (displayPopUp && message == null) {
            message = new Message(header.toString(),2,2);
        }
    }

    @Override
    public Message getMessage() {
        return message;
    }

    @Override
    public void setMessage(Message message) {
        this.message = message;
    }

    public Notification genNotification() {
        return new Notification(this.header.toString());
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
        } else if (!(other instanceof GeneralReminder)) {
            return false;
        } else {
            GeneralReminder otherGeneralReminder = (GeneralReminder) other;
            return this.header.equals(otherGeneralReminder.getHeader())
                    //&& this.priority.equals(otherGeneralReminder.getPriority())
                    && this.conditions.stream()
                    .allMatch(condition -> otherGeneralReminder.getConditions().contains(condition));
        }
    }

    @Override
    public void reset() {
        this.status = Status.unmet;
        this.numberOfConditionsMet = 0;
    }
}
