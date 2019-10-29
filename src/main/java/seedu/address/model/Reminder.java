package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.reminder.ReminderStub;


/**
 * Reminder object with description and dates remaining
 */
public class Reminder {

    private HashMap<String, Integer> reminders;
    private HashMap<String, Integer> followup;
    private ArrayList<ReminderStub> reminderArrayList;

    /**
     * Initializes new Reminder object
     */
    public Reminder() {
        reminders = new HashMap<>();
        followup = new HashMap<>();
        // Stub for creating list for UI use
        reminderArrayList = new ArrayList<ReminderStub>();
        reminderArrayList.add(new ReminderStub("test", 1));
    }

    public static Reminder getDefaultReminders() {
        Reminder def = new Reminder();
        return def;
    }

    /**
     * Adds a new Reminder to VISIT
     */
    public Reminder addReminder(int type, String description, int days) {
        requireNonNull(type);
        requireNonNull(description);
        requireNonNull(days);
        if (type == 0) {
            reminders.put(description, days);
        } else {
            followup.put(description, days);
        }
        return this;
    }

    /**
     * Decrements the days a reminder has left
     */
    public void cascadeDay(int days) {
        HashMap<String, Integer> cascadeReminders = new HashMap<>();
        HashMap<String, Integer> cascadeFollowups = new HashMap<>();
        Iterator it = reminders.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry) it.next();
            String key = pair.getKey().toString();
            int value = Integer.parseInt(pair.getValue().toString()) - days;
            if (value >= 0) {
                cascadeReminders.put(key, value);
            }
            it.remove();
        }
        reminders = cascadeReminders;

        it = followup.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry) it.next();
            String key = pair.getKey().toString();
            int value = Integer.parseInt(pair.getValue().toString()) - days;
            if (value >= 0) {
                cascadeFollowups.put(key, value);
            }
            it.remove();
        }
        followup = cascadeFollowups;
    }

    /**
     * Outputs the Reminders and Follow-Up to readable String
     */
    public String outputReminders() {
        StringBuilder sb = new StringBuilder();
        sb.append("Reminders:\n");
        if (reminders.size() < 1) {
            sb.append("No reminders found.\n");
        } else {
            for (int i = 0; i < reminders.size(); i++) {
                Iterator it = reminders.entrySet().iterator();
                while (it.hasNext()) {
                    HashMap.Entry pair = (HashMap.Entry) it.next();
                    sb.append(pair.getKey() + ": for " + pair.getValue() + " days\n");
                }
            }
        }
        sb.append("\nFollow-ups:\n");
        if (followup.size() < 1) {
            sb.append("No reminders found.\n");
        } else {
            for (int i = 0; i < followup.size(); i++) {
                Iterator it = followup.entrySet().iterator();
                while (it.hasNext()) {
                    HashMap.Entry pair = (HashMap.Entry) it.next();
                    sb.append(pair.getKey() + ": in " + pair.getValue() + " days\n");
                }
            }
        }
        return sb.toString();
    }

    /**
     * Example of method needed to return the list for view
     * @return ObservableList of Reminder objects
     */
    public ObservableList<ReminderStub> getReminderList() {
        return FXCollections.observableArrayList(this.reminderArrayList);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nReminders : --hidden--");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reminder)) {
            return false;
        }
        Reminder other = (Reminder) o;
        return other.reminders.equals(this.reminders)
                && other.followup.equals(this.followup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reminders, followup);
    }

}

