package seedu.guilttrip.model.reminders;

import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.commons.util.ObservableSupport;
import seedu.guilttrip.commons.util.TimeUtil;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.entry.Entry;
import seedu.guilttrip.model.entry.Expense;
import seedu.guilttrip.model.entry.Income;
import seedu.guilttrip.model.entry.Period;
import seedu.guilttrip.model.reminders.conditions.Condition;
import seedu.guilttrip.model.reminders.messages.Message;
import seedu.guilttrip.model.reminders.messages.Notification;
import seedu.guilttrip.model.util.Frequency;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class IncomeExpenseReminders extends Reminder implements PropertyChangeListener {
    String uniqueID = UUID.randomUUID().toString();
    Date currDate;
    Date lastActive;
    Date nextActive;
    Period period;
    Frequency frquency;

    private Description header;

    private Entry entry;

    private Status status = Status.unmet;

    private boolean displayPopUp = false;
    private Message message;

    private final Logger logger = LogsCenter.getLogger(getClass());
    private ObservableSupport support = new ObservableSupport();

    public IncomeExpenseReminders(Entry entry, Period period, Frequency frequency) {
        assert entry instanceof Expense || entry instanceof Income;
        this.entry = entry;
        this.nextActive = entry.getDate().minus(period);
        this.period = period;
        this.frquency = frequency;
        TimeUtil.addPropertyChangeListener(this);
        TimeUtil.manualUpdate();
    }

    //===== Handling Pre-Display Condition =====//
    public void setEntry(Entry entry) {
        this.entry = entry;
        if (nextActive.plus(frquency).isBefore(entry.getDate())) {
            nextActive = entry.getDate().minus(period);
        }
        TimeUtil.manualUpdate();
    }

    public Date getNextActive() {
        return this.nextActive;
    }

    public void setNextActive() {
        nextActive = nextActive.plus(frquency);
        if (nextActive.isAfter(entry.getDate())) {
            nextActive = entry.getDate();
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

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("currDate")) {
            this.currDate = new Date((LocalDate) evt.getNewValue());
        }
        update();
    }

    public void update() {
        if (status.equals(Status.unmet)) {
            if (currDate.isAfter(nextActive) && currDate.isBefore(entry.getDate())) {
                status = Status.met;
            } if (currDate.isAfter(entry.getDate())) {
                status = Status.exceeded;
            }
        }
    }

    @Override
    public Status getStatus() {
        return this.status;
    }
}
