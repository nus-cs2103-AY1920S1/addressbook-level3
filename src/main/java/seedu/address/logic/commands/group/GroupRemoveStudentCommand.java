package seedu.address.logic.commands.group;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents an remove student command, specific to a group.
 */
public class GroupRemoveStudentCommand extends GroupCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes an existing student from an existing group\n"
            + "Parameters:\n"
            + "delete "
            + "groupId/ [GROUP_ID]\n"
            + "Example: groupId/ G03\n\n"
            + "groupIndexNumber/ [INDEX_NUMBER]\n"
            + "Example: groupIndexNumber/ 2 (Specifies the student number in the group to remove)\n"
            + "Full Example: group delete groupID/G03 groupIndexNumber/1"
            + " --> removes student with index number 1 from G03";

    public static final String MESSAGE_SUCCESS = "Removed student: %1$d from group: %2$s";

    private final String groupId;
    private final int groupIndexNumber;

    /**
     * Creates a GroupRemoveStudentCommand instance with the appropriate attributes.
     *
     * @param groupId          The identifier of the group.
     * @param groupIndexNumber The student number of the group to be removed.
     */
    public GroupRemoveStudentCommand(String groupId, int groupIndexNumber) {
        this.groupId = groupId;
        this.groupIndexNumber = groupIndexNumber;
    }

    /**
     * Executes the user command.
     *
     * @param model {@code Model} which the command should operate on.
     * @return The result of the command.
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (groupId.isEmpty() || groupId.equals("")) {
            throw new CommandException(GROUP_ID_LEFT_EMPTY);
        }
        if (!model.checkGroupExists(groupId)) {
            throw new CommandException(String.format(GROUP_DOES_NOT_EXIST, groupId)); //group doesn't exist
        }
        if (groupIndexNumber > model.getGroupSize(groupId) || groupIndexNumber < 1) {
            throw new CommandException(INDEX_OUT_OF_BOUNDS);
        }
        model.removeStudentFromGroup(groupId, groupIndexNumber - 1);
        return new CommandResult(generateSuccessMessage());
    }

    /**
     * Generates a command execution success message.
     *
     * @return The String representation of a success message.
     */
    private String generateSuccessMessage() {
        return String.format(MESSAGE_SUCCESS, groupIndexNumber, groupId);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GroupRemoveStudentCommand)) {
            return false;
        }

        // state check
        GroupRemoveStudentCommand e = (GroupRemoveStudentCommand) other;
        return this.groupId.equals(e.groupId);
    }
}
