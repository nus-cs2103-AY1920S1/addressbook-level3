package mams.model.module;

import java.util.Arrays;
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

    public static final String MESSAGE_CONSTRAINTS_MODULE_NAME =
            "Module Names should only contain alphanumeric "
                    + "characters and spaces, and it should not be blank";

    public static final String MESSAGE_CONSTRAINTS_MODULE_DESCRIPTION =
            "Module Description should only contain alphanumeric "
                    + "characters and spaces, and it should not be blank";

    public static final String MESSAGE_CONSTRAINTS_LECTURER_NAME =
            "Lecturer Name should only contain alphanumeric "
                    + "characters and spaces, and it should not be blank";

    public static final String MESSAGE_CONSTRAINTS_TIME_SLOT =
            "Time slots can only range from 1 to 69, and must be in "
                    + "ascending order";

    public static final String MESSAGE_CONSTRAINTS_QUOTA =
            "Quota must be more than 0";

    /*
     * Only CS modules are allowed for adding. The first 2 characters should be
     * "CS" followed by strictly 4 digits.
     */
    public static final String VALIDATION_REGEX_MODULE_CODE = "CS\\d{4}$";

    /*
     * The first character of the module name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX_MODULE_NAME = "[\\p{Alnum}][\\p{Alnum} ]*";

    /*
     * The first character of the module name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX_MODULE_DESCRIPTION = "[^\\s].*";


    /*
     * Lecturer name has the same requirements as module name.
     */
    public static final String VALIDATION_REGEX_LECTURER_NAME = "[\\p{Alnum}][\\p{Alnum} ]*";


    // Identity fields
    private final String moduleCode;
    private final String moduleName;

    // Data fields
    private final String moduleDescription;
    private final String lecturerName;
    private final String timeSlot;
    private final String quota;
    private final Set<Tag> students = new HashSet<>(); // to be added

    /**
     * Every field must be present and not null.
     */
    public Module(String moduleCode, String moduleName, String moduleDescription,
                  String lecturerName, String timeSlot, String quota,
                  Set<Tag> students) {
        CollectionUtil.requireAllNonNull(moduleCode, moduleName, timeSlot, students);
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.moduleDescription = moduleDescription;
        this.lecturerName = lecturerName;
        this.timeSlot = timeSlot;
        this.quota = quota;
        this.students.addAll(students);
    }

    /**
     * Returns true if a given string is a valid module code.
     */
    public static boolean isValidModuleCode(String test) {
        return test.matches(VALIDATION_REGEX_MODULE_CODE);
    }

    /**
     * Returns true if a given string is a valid module name.
     */
    public static boolean isValidModuleName(String test) {
        return test.matches(VALIDATION_REGEX_MODULE_NAME);
    }

    /**
     * Returns true if a given string is a valid module description.
     */
    public static boolean isValidModuleDescription(String test) {
        return test.matches(VALIDATION_REGEX_MODULE_DESCRIPTION);
    }

    /**
     * Returns true if a given string is a valid module description.
     */
    public static boolean isValidLecturerName(String test) {
        return test.matches(VALIDATION_REGEX_LECTURER_NAME);
    }

    /**
     * Returns if a given quota is valid. Quota must be more than zero.
     */
    public static boolean isValidQuota(String test) {
        int result = Integer.parseInt(test);
        if (result > 0) {
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

        final int firstTimeSlot = 0;
        final int lastTimeSlot = 69;

        if (test == null || test.isEmpty()) {
            return false;
        }

        String []arr = test.split(",");
        for (String str : arr) {
            int temp = Integer.parseInt(str);
            if (temp > lastTimeSlot || temp < firstTimeSlot) {
                return false;
            }
        }
        return true;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getModuleDescription() {
        return moduleDescription;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public int getQuota() {
        return Integer.parseInt(quota);
    }

    public int getCurrentEnrolment() {
        return students.size();
    }

    public int[] getTimeSlotToIntArray() {
        String []arr = timeSlot.split(",");
        int[] slots = new int[arr.length];

        int x = 0;
        for (String str : arr) {
            slots[x] = Integer.parseInt(str);
            x++;
        }

        Arrays.sort(slots);
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
                && otherModule.getModuleCode().equals(getModuleCode());
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
        return otherModule.getModuleCode().equals(getModuleCode());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(moduleCode);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getModuleCode())
                .append(" Module Name: ")
                .append(getModuleName())
                .append(" Module Description: ")
                .append(getModuleDescription())
                .append(" Lecturer Name: ")
                .append(getLecturerName())
                .append(" TimeSlots: ")
                .append(timeSlotsToString())
                .append(" Quota: ")
                .append(quotaToString())
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

        int test;
        for (int i = 0; i < temp.length; i++) {
            test = temp[i];
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
            return "Monday";
        case 1:
            return "Tuesday";
        case 2:
            return "Wednesday";
        case 3:
            return "Thursday";
        case 4:
            return "Friday";
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

    /**
     * Returns the current number of students and max quota.
     * @return String of quota figures
     */
    public String quotaToString() {
        final StringBuilder builder = new StringBuilder();

        builder.append(getCurrentEnrolment())
                .append("/")
                .append(getQuota());

        return builder.toString();
    }
}
