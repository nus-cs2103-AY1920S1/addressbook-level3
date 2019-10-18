package mams.logic.commands;

import static mams.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static mams.logic.parser.CliSyntax.PREFIX_STUDENT;


/**
 * Abstract class for AddModCommand and RemoveModCommand
 */
public abstract class ModCommand extends Command {

    public static final String COMMAND_WORD = "addmod";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a module to a student in MAMS "
            + "identified by index number used in the displayed student list or "
            + "their matric id. \n"
            + "Parameters: INDEX (must be a positive integer) or "
            + PREFIX_STUDENT + "MATRIC_ID and "
            + PREFIX_MODULE_CODE + "MODULE_CODE ";

    public static final String MESSAGE_INVALID_MATRICID = "Please enter 1 valid Matric ID or Index. ";
    public static final String MESSAGE_INVALID_MODULE_CODE = "Please enter 1 valid Module Code. ";
    public static final String MESSAGE_DUPLICATE_MODULE_CODE = "Student is already registered for this module.";


}
