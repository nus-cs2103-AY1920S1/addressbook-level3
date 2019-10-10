package mams.model.module;

import java.util.Objects;

import mams.commons.util.CollectionUtil;
import mams.model.student.Student;


/**
 * Represents a Module in MAMS.
 */
public class Module {
    // Identity fields
    private final String moduleCode;
    private final int sessionId;

    // Data fields
    /**
     * timeSlots of lecture of the module. Time slots can occupy any number
     *  of hours in a day.
     * Assumptions: No more than 1 session per daytracing.
     *   TimeSlots(value) are arranged in ascending order
     */
    private final int[] timeSlot;
    private final Student[] students = null; // to be added

    public static final String MESSAGE_CONSTRAINTS_MODULE_CODE =
            "Modules should start with 'CS' followed by a 4 digit number";

    public static final String MESSAGE_CONSTRAINTS_SESSION_ID =
            "Session ID can only be 1 or 2";

    public static final String MESSAGE_CONSTRAINTS_TIME_SLOT =
            "Time slots can only range from 1 to 69, and must be in " +
                    "ascending order";
    /*
     * Only CS modules are allowed for adding. The first 2 characters should be
     * "CS" followed by strictly 4 digits.
     */
    public static final String VALIDATION_REGEX_MODULE_CODE = "CS\\d{4}$";


    /**
     * Every field must be present and not null.
     */
    public Module(String moduleCode, int sessionId, int[] timeSlot) {
        CollectionUtil.requireAllNonNull(moduleCode, sessionId, timeSlot);
        this.moduleCode = moduleCode;
        this.sessionId = sessionId;
        this.timeSlot = timeSlot;
    }

    /**
     * Returns true if a given string is a valid module code.
     */
    public static boolean isValidModuleCode(String test) {
        return test.matches(VALIDATION_REGEX_MODULE_CODE);
    }

    /*
     * Returns true if a given int is a valid session id.
     */
    public static boolean isValidSessionId(int test) {
        if (test == 1 || test == 2) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidTimeSlot(int[] test) {
        final int lastTimeSlot = 69;
        int currentTimeSlot = 0;

        if (test == null) {
            return false;
        }

        /*
         * Test for one time slot
         */
        if (test.length == 1 && test[0] <= lastTimeSlot) {
            return true;
        }

        /*
         * Test for more than 1 time slots.
         */
        currentTimeSlot = test[0];
        for (int i = 1; i < test.length; i++) {
            if ((test[i] <= currentTimeSlot) || test[i] > lastTimeSlot) {
                return false;
            }
            currentTimeSlot = test[i];
        }

        return true;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public int getSessionId() {
        return sessionId;
    }

    public String getSessionIdString() {
        return String.valueOf(sessionId);
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
                && otherModule.getSessionIdString().equals(getSessionIdString());
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
                && otherModule.getSessionIdString().equals(getSessionIdString());

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
