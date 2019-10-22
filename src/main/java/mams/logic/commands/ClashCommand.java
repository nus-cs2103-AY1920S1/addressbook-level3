package mams.logic.commands;

import static mams.logic.parser.CliSyntax.PREFIX_APPEAL;
import static mams.logic.parser.CliSyntax.PREFIX_MODULE;
import static mams.logic.parser.CliSyntax.PREFIX_STUDENT;

import java.util.ArrayList;
import java.util.HashSet;

import mams.model.module.Module;
import mams.model.tag.Tag;

/**
 * Encapsulate a ClashCommand class to check clashes in timetable.
 */
public abstract class ClashCommand extends Command {

    public static final String COMMAND_WORD = "clash";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Checks timetable clashes "
            + "by the 2 module codes or 1 appeal ID or 1 student matric number."
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS1010 " + PREFIX_MODULE + "CS2030 "
            + "\nor " + COMMAND_WORD + " "
            + PREFIX_APPEAL + "c00001"
            + "\nor " + COMMAND_WORD + " "
            + PREFIX_STUDENT + "A0111234X";


    public static final String MESSAGE_CLASH_DETECTED = "Timetable clash detected: \n";
    public static final String MESSAGE_CLASH_NOT_DETECTED = "There is no timetable clash.";
    public static final String MESSAGE_INVALID_MODULE_CODE = "Please enter 2 valid module codes.";
    public static final String MESSAGE_INVALID_MATRICID = "Please enter 1 valid Matric ID. ";
    public static final String MESSAGE_INVALID_APPEALID = "Please enter 1 valid Appeal ID. ";
    public static final String MESSAGE_INVALID_MODULETOADD = "The module requested is not available. ";
    public static final String MESSAGE_INVALID_STUDENT = "The student submitting the appeal is not found. ";
    public static final String MESSAGE_NOT_ADDMOD_APPEAL = "This is not a add module appeal. No need to check clashes.";
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
     * Returns a temporary module object which stores the clashing time slots
     * @return Returns a temporary module object which stores the clashing time slots
     */
    protected Module generateTempMod() {
        StringBuilder sb = new StringBuilder("");
        for (int slot : clashingSlots) {
            sb.append(slot).append(",");
        }

        return new Module("", "", "", "", sb.toString(),
                "", new HashSet<Tag>());
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
