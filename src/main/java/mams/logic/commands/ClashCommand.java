package mams.logic.commands;

import mams.model.module.Module;

import java.util.ArrayList;

public abstract class ClashCommand extends Command {

    public static final String COMMAND_WORD = "clash";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Checks timetable clashes "
            + "by the module codes or appeal number or student's matric number. \n";

    public static final String MESSAGE_CLASH_DETECTED = "Timetable clash detected: \n";
    public static final String MESSAGE_CLASH_NOT_DETECTED = "There is no timetable clash.";
    public static final String MESSAGE_INVALID_MODULE_CODE = "Please enter 2 valid module codes.";

    ArrayList<Integer> clashingSlots;

    /**
     * Returns an ArrayList of Integers that contains the clashing time slots.
     * @param moduleToCheckA a Module object of module A
     * @param moduleToCheckB a Module object of module B
     * @return an ArrayList of Integers that contains the clashing time slots.
     */
    ArrayList<Integer> getClashingSlots(Module moduleToCheckA, Module moduleToCheckB) {
        int[] timeTableA = moduleToCheckA.getTimeSlotToIntArray();
        int[] timeTableB = moduleToCheckB.getTimeSlotToIntArray();
        for (int i : timeTableA) {
            for (int j : timeTableB) {
                if (i == j) {
                    clashingSlots.add(i);
                }
            }
        }
        return clashingSlots;
    }

    /**
     * Returns a string representation of clashing time slots.
     * @param moduleToCheck a Module object to be checked.
     * @return a string representation of clashing time slots.
     */
    String getTimeSlotsToString(Module moduleToCheck) {
        return moduleToCheck.timeSlotsToString(clashingSlots.stream().mapToInt(i -> i).toArray());
    }
}
