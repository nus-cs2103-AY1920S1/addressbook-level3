package seedu.address.logic.commands.group;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a get students command, specific to a group.
 */
public class GroupGetStudentsCommand extends GroupCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Gets the students from a group\n"
            + "Parameters:\n"
            + "groupID/ [GROUP_ID]\n"
            + "Example: groupID/ G03\n"
            + "Full Example: group groupID/G03 --> lists all students in G03 \n\n";

    private final String groupId;

    /**
     * Creates a GroupGetStudentsCommand instance with the appropriate attributes.
     * @param groupId The identifier of the group.
     */
    public GroupGetStudentsCommand(String groupId) {
        this.groupId = groupId;
    }

    /**
     * Executes the user command.
     * @param model {@code Model} which the command should operate on.
     * @return The result of the command.
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(generateSuccessMessage(model.getStudentsFromGroup(groupId)));
    }

    /**
     * Generates a command execution success message.
     * @param message The relevant message from the model.
     * @return The String representation of a success message.
     */
    private String generateSuccessMessage(String message) {
        System.out.println(message);
        return "These are the students in " + groupId + "\n"
                + message;
    }
}
