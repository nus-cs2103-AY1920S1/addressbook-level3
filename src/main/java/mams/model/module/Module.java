package mams.model.module;

import java.util.Objects;

import mams.commons.util.CollectionUtil;


/**
 * Represents a Module in MAMS.
 */
public class Module {
    // Identity fields
    private final String moduleCode;
    private final String sessionId;

    // Data fields
    /**
     * timeSlots of lecture of the module. Time slots can occupy any number
     *  of hours in a day.
     * Assumptions: No more than 1 session per daytracing.
     *   TimeSlots(value) are arranged in ascending order
     */
    private final int[] timeSlot;
    //private final Student[] students; // to be added

    /**
     * Every field must be present and not null.
     */
    public Module(String moduleCode, String sessionId, int[] timeSlot) {
        CollectionUtil.requireAllNonNull(moduleCode, sessionId, timeSlot);
        this.moduleCode = moduleCode;
        this.sessionId = sessionId;
        this.timeSlot = timeSlot;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public String getSessionId() {
        return sessionId;
    }

    public int[] getTimeSlot() {
        return timeSlot;
    }

    /**
     * Returns true if both module of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two students.
     */
    public boolean isSameModule(Module otherModule) {
        if (otherModule == this) {
            return true;
        }

        return otherModule != null
                && otherModule.getModuleCode().equals(getModuleCode())
                && otherModule.getSessionId().equals(getSessionId());
    }

    /**
     * Returns true if both students have the same identity and data fields.
     * This defines a stronger notion of equality between two students.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Module)) {
            return false;
        }

        Module otherModule = (Module) other;
        return otherModule.getModuleCode().equals(getModuleCode())
                && otherModule.getSessionId().equals(getSessionId());

    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(moduleCode, sessionId);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getModuleCode())
                .append(" SessionID: ")
                .append(getSessionId())
                .append(" TimeSlots: ")
                .append(timeSlotsToString());

        return builder.toString();
    }

    /**
     * Returns the string indicating which time slot this module occupies.
     * @return Day and TimeSlots of this module
     */
    String timeSlotsToString() {
        final StringBuilder builder = new StringBuilder();
        int startTimeSlot = 0;

        for (int i = 0; i < timeSlot.length; i++) {
            if (((startTimeSlot == 0) && (i != timeSlot.length - 1) && (timeSlot[i] < timeSlot[i + 1] - 1))
                    || ((i == timeSlot.length - 1) && (startTimeSlot == 0))) {
                //print for 1hour time slot OR print 1hr time slot(last time in array)
                builder.append(getDay(timeSlot[i]))
                        .append(" ")
                        .append(getTime(timeSlot[i]))
                        .append(" to ")
                        .append(getTime(timeSlot[i] + 1))
                        .append(" "); //Ends at next hour
                startTimeSlot = 0;
            } else if (startTimeSlot == 0 && (i != timeSlot.length - 1)) {
                //first hour of 2/3hr sessions.
                startTimeSlot = timeSlot[i];
            } else if ((i == timeSlot.length - 1) || (timeSlot[i] < timeSlot[i + 1] - 1)) {
                //print time slot 2nd hour of 2hr session/3rd hour of 3 hours session.
                builder.append(getDay(timeSlot[i]))
                        .append(" ")
                        .append(getTime(startTimeSlot))
                        .append(" to ")
                        .append(getTime(timeSlot[i] + 1))
                        .append(" "); //Ends at next hour
                startTimeSlot = 0;
            }
        }
        return builder.toString();
    }

    /**
     * Returns the day of the time slot. Only weekdays are possible days for modules.
     * @param timeSlot integer value that identifies which timeslot of the week the module
     *                 occupies. Range: (1-69)
     * @return String indicating which day the module falls on
     */
    String getDay(int timeSlot) {
        final int timeSlotsPerDay = 14;
        int day = timeSlot / timeSlotsPerDay;

        switch (day) {
        case 0:
            return "MONDAY";
        case 1:
            return "TUESDAY";
        case 2:
            return "WEDNESDAY";
        case 3:
            return "THURSDAY";
        case 4:
            return "FRIDAY";
        default:
            return "Invalid day";
        }
    }

    /**
     * Returns the time with respect to the timeslot. All days of the week have 13 possible time slots.
     * Note: The 14th time slot (21:00) is only valid as a end time, and is not a valid time slot.
     * @param timeSlot integer value that identifies which timeslot of the week the module
     *                 occupies. Range: (1-69)
     * @return String of the time of which the modules falls on
     */
    String getTime(int timeSlot) {
        final int numberOfTimeSlots = 14;
        int actualTime = timeSlot % numberOfTimeSlots;

        switch (actualTime) {
        case 1:
            return "08:00";
        case 2:
            return "09:00";
        case 3:
            return "10:00";
        case 4:
            return "11:00";
        case 5:
            return "12:00";
        case 6:
            return "13:00";
        case 7:
            return "14:00";
        case 8:
            return "15:00";
        case 9:
            return "16:00";
        case 10:
            return "17:00";
        case 11:
            return "18:00";
        case 12:
            return "19:00";
        case 13:
            return "20:00";
        case 0:
            return "21:00";
        default:
            return "Invalid time";
        }
    }
}
