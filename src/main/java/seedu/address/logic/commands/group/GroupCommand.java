package seedu.address.logic.commands.group;

import seedu.address.logic.commands.Command;

/**
 * Represents a group command.
 */
public abstract class GroupCommand extends Command {

    public static final String COMMAND_WORD = "group";
    public static final String GROUP_DOES_NOT_EXIST = "Group with ID '%1$s' not exist, create group first. ";
}
