package mams.logic.commands;

import static mams.logic.parser.CliSyntax.PREFIX_MODULE;
import static mams.logic.parser.CliSyntax.PREFIX_STUDENT;


/**
 * Abstract class for AddModCommand and RemoveModCommand
 */
public abstract class ModCommand extends Command {

    public static final String COMMAND_WORD_ADD_MOD = "addmod";
    public static final String COMMAND_WORD_REMOVE_MOD = "removemod";
    public static final String MESSAGE_USAGE_ADD_MOD = COMMAND_WORD_ADD_MOD + ": Adds a module to a student in MAMS "
            + "identified by index number used in the displayed student list or "
            + "their matric id. \n"
            + "Parameters: INDEX (must be a positive integer) or "
            + PREFIX_STUDENT + "MATRIC_ID and "
            + PREFIX_MODULE + "MODULE_CODE ";

    public static final String MESSAGE_USAGE_REMOVE_MOD = COMMAND_WORD_REMOVE_MOD
            + ": Remove a module from a student in MAMS "
            + "identified by index number used in the displayed student list or "
            + "their matric id. \n"
            + "Parameters: INDEX (must be a positive integer) or "
            + PREFIX_STUDENT + "MATRIC_ID and "
            + PREFIX_MODULE + "MODULE_CODE ";

    public static final String MESSAGE_INVALID_MATRICID = "Please enter 1 valid Matric ID or Index. ";
    public static final String MESSAGE_INVALID_MODULE_CODE = "Please enter 1 valid Module Code. ";
    public static final String MESSAGE_DUPLICATE_MODULE_CODE = "Student is already registered for this module.";
    public static final String MESSAGE_MISSING_MODULE_CODE = "Student is not registered for this module. ";

}
