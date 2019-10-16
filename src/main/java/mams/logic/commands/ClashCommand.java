package mams.logic.commands;

import static mams.logic.parser.CliSyntax.PREFIX_APPEALID;
import static mams.logic.parser.CliSyntax.PREFIX_MATRICID;
import static mams.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import java.util.ArrayList;

import mams.model.module.Module;

/**
 * Encapsulate a ClashCommand class to check clashes in timetable.
 */
public abstract class ClashCommand extends Command {

    public static final String COMMAND_WORD = "clash";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Checks timetable clashes "
            + "by the 2 module codes or 1 appeal ID or 1 student matric number."
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_MODULE_CODE + "CS1010 " + PREFIX_MODULE_CODE + "CS2030 "
            + "\nor " + COMMAND_WORD + " "
            + PREFIX_APPEALID + "c00001"
            + "\nor " + COMMAND_WORD + " "
            + PREFIX_MATRICID + "A0111234X";


    public static final String MESSAGE_CLASH_DETECTED = "Timetable clash detected: \n";
    public static final String MESSAGE_CLASH_NOT_DETECTED = "There is no timetable clash.";
    public static final String MESSAGE_INVALID_MODULE_CODE = "Please enter 2 valid module codes.";
    public static final String MESSAGE_INVALID_MATRICID = "Please enter 1 valid Matric ID. ";
    public static final String MESSAGE_INVALID_APPEALID = "Please enter 1 valid Appeal ID. ";

    protected ArrayList<Integer> clashingSlots;

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
