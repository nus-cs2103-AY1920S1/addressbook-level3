package unrealunity.visit.model.appointment;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import unrealunity.visit.logic.commands.exceptions.CommandException;

/**
 * AppointmentTable facilitates the storing of Reminders and Follow-Ups in JSON
 * and performs operations dealing with Appointments.
 *
 * Runs in parallel with AppointmentList, which handles UI.
 * This is BY DESIGN and an intentional break of OOP.
 */
public class AppointmentTable {

    /**
     * Creates a new json structure under UserPrefs' storage for Reminders.
     */
    private HashMap<String, Integer> reminders;

    /**
     * Creates a new json structure under UserPrefs' storage for Follow-Ups.
     */
    private HashMap<String, Integer> followup;

    /**
     * Initializes new AppointmentTable object.
     */
    public AppointmentTable() {
        reminders = new HashMap<>();
        followup = new HashMap<>();
    }

    /**
     * Returns a default, empty AppointmentTable.
     */
    public static AppointmentTable getDefaultAppointments() {
        AppointmentTable def = new AppointmentTable();
        return def;
    }

    /**
     * Returns an ObservableList version of the Appointments for UI usage.
     * This is necessary as AppointmentTable is loaded on launch and
     * AppointmentList uses this data to propagate the UI.
     *
     * @return ObservableList of Appointment objects
     */
    public ObservableList<Appointment> getAppointmentList() {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        Iterator it = followup.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry) it.next();
            appointmentList.add(new Appointment("[F] " + pair.getKey(), (int) pair.getValue()));
        }
        it = reminders.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry) it.next();
            appointmentList.add(new Appointment("[R] " + pair.getKey(), (int) pair.getValue()));
        }

        return appointmentList;
    }

    /**
     * Adds a new Appointment.
     *
     * @param type The type of appointment. 0 = Reminder, 1 = Follow-Up.
     * @param description The description of the Appointment.
     * @param days How many days the Appointment has remaining.
     */
    public AppointmentTable addAppointment(int type, String description, int days) throws CommandException {
        requireNonNull(type);
        requireNonNull(description);
        requireNonNull(days);
        if (type == Appointment.Type.REMINDER) {
            if (antiDuplicate(reminders, description, days)) {
                reminders.put(description, days);
            } else {
                throw new CommandException("Appointment already exists.");
            }
        } else {
            if (antiDuplicate(followup, description, days)) {
                followup.put(description, days);
            } else {
                throw new CommandException("Appointment already exists.");
            }
        }
        return this;
    }

    /**
     * Deletes an appointment from VISIT.
     *
     * @param description The description of the appointment to delete.
     * @param days Optional number of days to specifically target the exact appointment to delete.
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
     * Checks if the Appointment already exists.
     *
     * @param check The HashMap containing the Appointment to be checked against.
     * @param description The description of the Appointment to search for.
     * @param days Number of days to specifically target the exact appointment.
     *
     * @return True if there is no duplicate, false if there is a duplicate.
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
     * Sorts the list of appointments by days remaining, then name.
     */
    public void sortAppointments() {
        reminders = sort(reminders);
        followup = sort(followup);
    }

    /**
     * Sorting algorithm for HashMap.
     *
     * @param hashMap The HashMap to be sorted.
     */
    private HashMap<String, Integer> sort(HashMap<String, Integer> hashMap) {
        List<HashMap.Entry<String, Integer>> list =
                new LinkedList<>(hashMap.entrySet());

        Comparator<HashMap.Entry<String, Integer>> compareByValue = Comparator.comparing(HashMap.Entry::getValue);
        Comparator<HashMap.Entry<String, Integer>> compareByKey = Comparator.comparing(HashMap.Entry::getKey);
        Comparator<HashMap.Entry<String, Integer>> compareByValueThenKey = compareByValue.thenComparing(compareByKey);
        Collections.sort(list, compareByValueThenKey);

        HashMap<String, Integer> tmp = new LinkedHashMap<>();
        for (HashMap.Entry<String, Integer> entry : list) {
            tmp.put(entry.getKey(), entry.getValue());
        }
        return tmp;
    }

    /**
     * Decrements the days an Appointment has left.
     * Run on application launch by UserPrefs after calculating days elapsed.
     *
     * @days Number of days to decrement each Appointment by.
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
     * Outputs the Appointments to readable String.
     * Used in the Message of the Day output.
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

