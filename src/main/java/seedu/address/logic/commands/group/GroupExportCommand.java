package seedu.address.logic.commands.group;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a group export command.
 */
public class GroupExportCommand extends GroupCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports the group to a word document\n"
            + "Parameters:\n"
            + "export \n"
            + "groupID/ [GROUP_ID]\n"
            + "Example: groupID/ G03\n"
            + "Full Example: group export/ groupID/G03 --> exports G03 to a word document \n\n";

    public static final String MESSAGE_SUCCESS = "Successfully exported group to exports/%1$s.docx";
    private final String groupId;

    /**
     * Creates a GroupExportCommand instance with the appropriate attributes.
     *
     * @param groupId The identifier of the group.
     */
    public GroupExportCommand(String groupId) {
        this.groupId = groupId;
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
        model.exportGroup(groupId);
        return new CommandResult(generateSuccessMessage());
    }

    /**
     * Generates a command execution success message.
     *
     * @return The String representation of a success message.
     */
    private String generateSuccessMessage() {
        return String.format(MESSAGE_SUCCESS, groupId);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GroupExportCommand)) {
            return false;
        }

        // state check
        GroupExportCommand e = (GroupExportCommand) other;
        return this.groupId.equals(e.groupId);
    }
}
