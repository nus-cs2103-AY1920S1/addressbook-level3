package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

/**
 * Reminder object with description and dates remaining
 */
public class Reminder {

    private HashMap<String, Integer> reminders;
    private HashMap<String, Integer> followup;

    /**
     * Initializes new Reminder object
     */
    public Reminder() {
        reminders = new HashMap<>();
        followup = new HashMap<>();
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
            reminders.put(description, days);
        }
        return this;
    }

    public String getReminders() {
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
                    it.remove();
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
                    it.remove();
                }
            }
        }
        return sb.toString();
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

