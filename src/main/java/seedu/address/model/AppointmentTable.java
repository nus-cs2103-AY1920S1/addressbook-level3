package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.reminder.Appointment;


/**
 * AppointmentTable object with description and dates remaining
 */
public class AppointmentTable {

    private HashMap<String, Integer> reminders;
    private HashMap<String, Integer> followup;

    /**
     * Initializes new AppointmentTable object
     */
    public AppointmentTable() {
        reminders = new HashMap<>();
        followup = new HashMap<>();
    }

    public static AppointmentTable getDefaultAppointments() {
        AppointmentTable def = new AppointmentTable();
        return def;
    }

    /**
     * Returns the list for view
     * @return ObservableList of Appointment objects
     */
    public ObservableList<Appointment> getAppointmentList() {
        ArrayList<Appointment> appointmentArrayList = new ArrayList<>();;
        Iterator it = reminders.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry) it.next();
            appointmentArrayList.add(new Appointment("[R] " + pair.getKey(), (int) pair.getValue()));
        }
        it = followup.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry) it.next();
            appointmentArrayList.add(new Appointment("[F] " + pair.getKey(), (int) pair.getValue()));
        }

        return FXCollections.observableArrayList(appointmentArrayList);
    }

    /**
     * Adds a new Appointment to VISIT
     */
    public AppointmentTable addAppointment(int type, String description, int days) throws CommandException {
        requireNonNull(type);
        requireNonNull(description);
        requireNonNull(days);
        if (type == 0) {
            if (antiDuplicate(reminders, description, days)) {
                reminders.put(description, days);
            } else {
                throw new CommandException("Appointment already exists");
            }
        } else {
            if (antiDuplicate(followup, description, days)) {
                followup.put(description, days);
            } else {
                throw new CommandException("Appointment already exists");
            }
        }
        return this;
    }

    /**
     * Deletes an appointment from VISIT
     */
    public AppointmentTable deleteAppointment(String description, int days) {
        requireNonNull(description);
        requireNonNull(days);

        if (days == -1) {
            reminders.remove(description);
            followup.remove(description);
        } else {
            reminders.remove(description, days);
            followup.remove(description, days);
        }

        return this;
    }

    /**
     * Checks if the appointment already exists
     */
    private boolean antiDuplicate(HashMap<String, Integer> check, String description, int days) {
        Iterator it = check.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry) it.next();
            if (pair.getKey().equals(description) && (int) pair.getValue() == days) {
                return false;
            }
        }
        return true;
    }

    /**
     * Decrements the days an appointment has left
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
    public String outputAppointments() {
        StringBuilder sb = new StringBuilder();
        sb.append("Reminders:\n");
        if (reminders.size() < 1) {
            sb.append("No reminders found.\n");
        } else {
            Iterator it = reminders.entrySet().iterator();
            while (it.hasNext()) {
                HashMap.Entry pair = (HashMap.Entry) it.next();
                sb.append(pair.getKey() + ": for " + pair.getValue() + " days\n");
            }
        }
        sb.append("\nFollow-ups:\n");
        if (followup.size() < 1) {
            sb.append("No follow-ups found.\n");
        } else {
            Iterator it = followup.entrySet().iterator();
            while (it.hasNext()) {
                HashMap.Entry pair = (HashMap.Entry) it.next();
                sb.append(pair.getKey() + ": in " + pair.getValue() + " days\n");
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nAppointments : --hidden--");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppointmentTable)) {
            return false;
        }
        AppointmentTable other = (AppointmentTable) o;
        return other.reminders.equals(this.reminders)
                && other.followup.equals(this.followup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reminders, followup);
    }

}

