package seedu.address.logic.commands.group;

import seedu.address.logic.commands.Command;

/**
 * Represents a group command.
 */
public abstract class GroupCommand extends Command {

    protected static final String INDEX_OUT_OF_BOUNDS = "Group index input lies outside of the group.";
    protected static final String GROUP_ALREADY_EXISTS = "Group with group ID %1$s already exists";
    protected static final String STUDENT_EXISTS_IN_GROUP = "%1$s already exists in the group";
    protected static final String STUDENT_NUMBER_OUT_OF_BOUNDS = "Student number input is out of bounds";
    protected static final String GROUP_INDEX_OUT_OF_BOUNDS = "Group index number input is out of bounds";
    protected static final String FIELDS_LEFT_EMPTY = "Input fields cannot be left empty";
    public static final String COMMAND_WORD = "group";
    protected static final String GROUP_DOES_NOT_EXIST = "Group with ID '%1$s' not exist, create group first. ";
    protected static final String GROUP_ID_LEFT_EMPTY = "Group ID parameter cannot be left empty";

}
