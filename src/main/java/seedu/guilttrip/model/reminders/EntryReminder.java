package seedu.guilttrip.model.reminders;

import java.time.LocalDate;
import java.util.UUID;
import java.util.logging.Logger;

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

/**
 * IEW stands for income, expense, and wish.
 * Notifies user when current date falls within specified period of date of event.
 */
public class EntryReminder implements Reminder, ListenerSupport {
    private final Logger logger = LogsCenter.getLogger(getClass());
    private Status status = Status.unmet;

    private String uniqueId;
    private Date currDate;
    private Date lastActive;
    private Date nextActive;
    private Period period;
    private Frequency frequency;

    private boolean displayPopUp = false;
    private Message message;

    private Description header;
    private Entry entry;

    private ObservableSupport support = new ObservableSupport();

    public EntryReminder(Description header, Entry entry, Period period, Frequency frequency) {
        this.header = header;
        this.uniqueId = UUID.randomUUID().toString();
        assert entry instanceof Expense || entry instanceof Income;
        this.entry = entry;
        this.entry.setUniqueId(uniqueId);
        entry.setHasReminder(true);
        this.period = period;
        nextActive = entry.getDate().minus(period);
        this.frequency = frequency;
        TimeUtil.addPropertyChangeListener(this);
        TimeUtil.manualUpdate();
    }

    /**
     * Used when creating EntryReminder from storage.
     * @param header
     * @param uniqueId used to map reminder to entry.
     * @param period
     * @param frequency
     */
    public EntryReminder(Description header, String uniqueId, Period period, Frequency frequency) {
        this.header = header;
        this.uniqueId = uniqueId;
        this.period = period;
        this.frequency = frequency;
    }

    public String getUniqueId() {
        return this.uniqueId;
    }

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

    /**
     * Updates the status of the reminder.
     */
    public void update() {
        Status newStatus = status;
        if (status.equals(Status.unmet)) {
            if (!currDate.isBefore(nextActive)) {
                newStatus = Status.met;
            }
            if (currDate.isAfter(entry.getDate())) {
                newStatus = Status.exceeded;
                entry.setHasReminder(false);
                entry.setUniqueId(null);
            }
            if (!status.equals(newStatus)) {
                status = newStatus;
                logger.info("Reminder status set to: " + status
                        + ". Time to send reminder notification for: " + entry.toString());
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
    public boolean willDisplayPopUp() {
        return displayPopUp;
    };

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof EntryReminder)) {
            return false;
        } else {
            EntryReminder otherReminder = (EntryReminder) other;
            return this.entry == otherReminder.entry
                    && this.period.equals(otherReminder.period)
                    && this.frequency.equals(otherReminder.frequency);
        }

    }
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
    @Override
    public String toString() {
        return "Entry Reminder " + header;
    }
}
