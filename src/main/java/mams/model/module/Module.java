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
    private final int[] timeSlot;

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

    //work in progress
    String timeSlotsToString(){
        String buffer = "" ;

        int temp = 0;
        for(int i=0; i< timeSlot.length; i++){
            if ((temp == 0) && (i != timeSlot.length - 1) && (timeSlot[i] < timeSlot[i+1] - 1)){
                //print for 1hour time slot.
                System.out.println("print 1 hour time slot" + timeSlot[i]);
            } else if (temp == 0 && (i != timeSlot.length - 1)) {
                //first hour of 2/3hr sessions. do nothing
                temp = timeSlot[i];
                System.out.println("first hour of 2/3hr sessions" + timeSlot[i]);
            } else if ((temp == timeSlot[i] - 1) && (timeSlot[i] == timeSlot[i+1] - 1)) {
                //2nd hour of 3 hr session. do nothing
                System.out.println("2nd hour of 3 hr session. do nothing" + timeSlot[i]);
            } else if ((i == timeSlot.length -1) && (temp == 0)) {
                //print 1 hr session. last item in array
                System.out.println("1 hr session. last item in array" + timeSlot[i]);
                temp = 0;
            } else if ((i == timeSlot.length - 1) || (timeSlot[i] < timeSlot[i+1] - 1)){
                //print time slot 2nd hour of 2hr session/3rd hour of 3 hours session.
                System.out.println("2nd hour of 2hr session/3rd hour of 3 hours session. print time slot." + timeSlot[i]);
                temp = 0;
            }
        }
        return buffer;
    }

    String timeSlot(int timeSlot) {
        switch (timeSlot) {
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
        case 14:
            return "21:00";
        default:
            return "unknown time";
        }
    }
}
