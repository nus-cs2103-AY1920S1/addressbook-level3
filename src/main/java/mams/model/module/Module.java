package mams.model.module;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import mams.commons.util.CollectionUtil;
import mams.model.tag.Tag;

/**
 * Represents a Module in MAMS.
 */
public class Module {

    public static final String MESSAGE_CONSTRAINTS_MODULE_CODE =
            "Modules should start with 'CS' followed by a 4 digit number";

    public static final String MESSAGE_CONSTRAINTS_SESSION_ID =
            "Session ID can only be 1 or 2";

    public static final String MESSAGE_CONSTRAINTS_TIME_SLOT =
            "Time slots can only range from 1 to 69, and must be in "
                    + "ascending order";

    /*
     * Only CS modules are allowed for adding. The first 2 characters should be
     * "CS" followed by strictly 4 digits.
     */
    public static final String VALIDATION_REGEX_MODULE_CODE = "CS\\d{4}$";

    // Identity fields
    private final String moduleCode;
    private final String sessionId;

    // Data fields
    /**
     * timeSlots of lecture of the module. Time slots can occupy any number
     *  of hours in a day.
     * Assumptions: No more than 1 session per day.
     *   TimeSlots(value) are arranged in ascending order
     */
    private final String timeSlot;
    private final Set<Tag> students = new HashSet<>(); // to be added

    /**
     * Every field must be present and not null.
     */
    public Module(String moduleCode, String sessionId, String timeSlot, Set<Tag> students) {
        CollectionUtil.requireAllNonNull(moduleCode, sessionId, timeSlot, students);
        this.moduleCode = moduleCode;
        this.sessionId = sessionId;
        this.timeSlot = timeSlot;
        this.students.addAll(students);
    }

    /**
     * Returns true if a given string is a valid module code.
     */
    public static boolean isValidModuleCode(String test) {
        return test.matches(VALIDATION_REGEX_MODULE_CODE);
    }

    /**
     * Returns true if a given session ID is valid
     * @param test String sessionId to be tested
     * @return result of test
     */
    public static boolean isValidSessionId(String test) {
        int result = Integer.parseInt(test);
        if (result == 1 || result == 2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns true if a given time slot is valid
     * @param test time slots to be tested
     * @return result of test
     */
    public static boolean isValidTimeSlot(String test) {
        final int lastTimeSlot = 69;
        int currentTimeSlot = 0;

        if (test == null) {
            return false;
        }

        String []arr = test.split(",");

        /*
         * Test for one time slot
         */
        if (arr.length == 1 && Integer.parseInt(arr[0]) <= lastTimeSlot) {
            return true;
        }

        /*
         * Test for more than 1 time slots
         */
        currentTimeSlot = Integer.parseInt(arr[0]);
        for (String str : arr) {
            int temp = Integer.parseInt(str);
            if (temp <= currentTimeSlot || temp > lastTimeSlot) {
                return false;
            }
            currentTimeSlot = temp;
        }

        return true;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public int[] getTimeSlotToIntArray() {
        String []arr = timeSlot.split(",");
        int[] slots = new int[arr.length];

        int x = 0;
        for (String str : arr) {
            slots[x] = Integer.parseInt(str);
        }
        return slots;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getStudents() {
        return Collections.unmodifiableSet(students);
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
                && otherModule.getSessionId().equals(getSessionId())
                && otherModule.getTimeSlot().equals(getTimeSlot());
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
                && otherModule.getSessionId().equals(getSessionId())
                && otherModule.getTimeSlot().equals((getTimeSlot()))
                && otherModule.getStudents().equals(getStudents());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(moduleCode, sessionId, timeSlot, students);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getModuleCode())
                .append(" SessionID: ")
                .append(getSessionId())
                .append(" TimeSlots: ")
                .append(timeSlotsToString())
                .append(" Students: ");
        getStudents().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Returns the string indicating which time slot this module occupies.
     * @return Day and TimeSlots of this module
     */

    public String timeSlotsToString() {

        int[] temp = getTimeSlotToIntArray();

        final StringBuilder builder = new StringBuilder();
        int startTimeSlot = 0;

        for (int i = 0; i < temp.length; i++) {
            if (((startTimeSlot == 0) && (i != temp.length - 1) && (temp[i] < temp[i + 1] - 1))
                    || ((i == temp.length - 1) && (startTimeSlot == 0))) {
                //print for 1hour time slot OR print 1hr time slot(last time in array)
                builder.append(getDay(temp[i]))
                        .append(" ")
                        .append(getTime(temp[i]))
                        .append(" to ")
                        .append(getTime(temp[i] + 1))
                        .append(" "); //Ends at next hour
                startTimeSlot = 0;
            } else if (startTimeSlot == 0 && (i != temp.length - 1)) {
                //first hour of 2/3hr sessions.
                startTimeSlot = temp[i];
            } else if ((i == temp.length - 1) || (temp[i] < temp[i + 1] - 1)) {
                //print time slot 2nd hour of 2hr session/3rd hour of 3 hours session.
                builder.append(getDay(temp[i]))
                        .append(" ")
                        .append(getTime(startTimeSlot))
                        .append(" to ")
                        .append(getTime(temp[i] + 1))
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
