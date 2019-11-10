package seedu.guilttrip.model.reminders;

import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.commons.util.ListenerSupport;
import seedu.guilttrip.commons.util.ObservableSupport;
import seedu.guilttrip.commons.util.ObservableSupport.Evt;
import seedu.guilttrip.commons.util.TimeUtil;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.entry.Entry;
import seedu.guilttrip.model.entry.Expense;
import seedu.guilttrip.model.entry.Income;
import seedu.guilttrip.model.entry.Period;
import seedu.guilttrip.model.reminders.messages.Message;
import seedu.guilttrip.model.reminders.messages.Notification;
import seedu.guilttrip.model.util.Frequency;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * IEW stands for income, expense, and wish.
 * Notifies user when current date falls within specified period of date of event.
 */
public class IEWReminder implements Reminder, ListenerSupport {
    private final Logger logger = LogsCenter.getLogger(getClass());
    private Status status = Status.unmet;

    private String uniqueID;
    private Date currDate;
    private Date lastActive;
    private Date nextActive;
    private Period period;
    private Frequency frequency;

    private Description header;
    private Entry entry;

    public IEWReminder(Description header, Entry entry, Period period, Frequency frequency) {
        this.header = header;
        this.uniqueID = UUID.randomUUID().toString();
        assert entry instanceof Expense || entry instanceof Income;
        this.entry = entry;
        this.entry.setUniqueID(uniqueID);
        entry.setHasReminder(true);
        this.period = period;
        nextActive = entry.getDate().minus(period);
        this.frequency = frequency;
        TimeUtil.addPropertyChangeListener(this);
        TimeUtil.manualUpdate();
    }

    /**
     * Used when creating IEWReminder from storage.
     * @param header
     * @param uniqueID used to map reminder to entry.
     * @param period
     * @param frequency
     */
    public IEWReminder(Description header, String uniqueID, Period period, Frequency frequency) {
        this.header = header;
        this.uniqueID = uniqueID;
        this.period = period;
        this.frequency = frequency;
    }

    public String getUniqueID() {
        return this.uniqueID;
    }

    private boolean displayPopUp = false;
    private Message message;

    private ObservableSupport support = new ObservableSupport();

    @Override
    public Description getHeader() {
        return this.header;
    }

    @Override
    public void setHeader(final Description header) {
        this.header = header;
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
        nextActive = entry.getDate().minus(period);
        TimeUtil.addPropertyChangeListener(this);
        TimeUtil.manualUpdate();
    }

    //===== how NextActive calculated =====//
    public Date getNextActive() {
        return this.nextActive;
    }

    public void setNextActive() {
        lastActive = nextActive;
        nextActive = nextActive.plus(frequency);
        if (nextActive.isAfter(entry.getDate())) {
            nextActive = entry.getDate();
        }
        update();
    }

    public Frequency getFrequency() {
        return this.frequency;
    }

    public void setFrequency(Frequency newFrequency) {
        nextActive = lastActive.plus(newFrequency);
        frequency = newFrequency;
    }

    public Period getPeriod() {
        return this.period;
    }

    public void setPeriod(Period period) {
        this.period = period;
        if (entry.getDate().minus(period).isAfter(nextActive)) {
            nextActive = entry.getDate().minus(period);
        }
    }

    //===== Notification and message =====//


    @Override
    public Notification genNotification() {
        return new Notification(header.toString());
    }

    @Override
    public Message getMessage() {
        return this.message;
    }
    //==========

    @Override
    public void propertyChange(final Evt evt) {
        if (evt.getPropertyName().equals("currDate")) {
            this.currDate = new Date((LocalDate) evt.getNewValue());
        }
        update();
    }

    public void update() {
        Status newStatus = status;
        if (status.equals(Status.unmet)) {
            if (!currDate.isBefore(nextActive)) {
                newStatus = Status.met;
            }
            if (currDate.isAfter(entry.getDate())) {
                newStatus = Status.exceeded;
            }
            if (!status.equals(newStatus)) {
                status = newStatus;
                logger.info("Reminder status set to: " + status +
                        ". Time to send reminder notification for: " + entry.toString());
                support.firePropertyChange("statusChange", null, this);
            }
        }
    }

    @Override
    public void reset() {
        status = Status.unmet;
        logger.info("status reset: " + status);
    }

    @Override
    public Status getStatus() {
        return this.status;
    }
    public boolean willDisplayPopUp(){
        return displayPopUp;
    };
    public void togglePopUpDisplay(boolean willDisplayPopup) {
        displayPopUp = willDisplayPopup;
    };
    public void setMessage(Message message) {
        this.message = message;
    };
    public ObservableSupport getSupport() {
        return support;
    };
    public void addPropertyChangeListener(ListenerSupport pcl) {
        support.addPropertyChangeListener(pcl);
    };
    public void removePropertyChangeListener(ListenerSupport pcl) {
        support.removePropertyChangeListener(pcl);
    };
}
