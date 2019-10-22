package mams.logic.commands;

import static mams.logic.parser.CliSyntax.PREFIX_CREDITS;
import static mams.logic.parser.CliSyntax.PREFIX_STUDENT;


/**
 * Abstract class for AddModCommand and RemoveModCommand
 */
public abstract class StudentCommand extends Command {

    public static final String COMMAND_WORD_SET_CREDITS = "setcredits";
    public static final String MESSAGE_USAGE_SETCREDITS = COMMAND_WORD_SET_CREDITS
            + ": Sets a student's maximum credits "
            + "identified by index number used in the displayed student list or "
            + "their matric id. \n"
            + "Parameters: INDEX (must be a positive integer) or "
            + PREFIX_STUDENT + "MATRIC_ID and "
            + PREFIX_CREDITS + "CREDITS ";

    public static final String MESSAGE_INVALID_MATRICID = "Please enter 1 valid Matric ID or Index. ";
    public static final String MESSAGE_INVALID_CREDIT_VALUE = "Please enter 1 valid credit value. ";
}

