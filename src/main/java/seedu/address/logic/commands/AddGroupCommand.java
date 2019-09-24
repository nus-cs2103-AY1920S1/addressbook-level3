package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;

/**
 * Add a group.
 */
public class AddGroupCommand extends Command {
    public static final String COMMAND_WORD = "addgroup";
    public static final String MESSAGE_SUCCESS = "New group added: ";
    public static final String MESSAGE_FAILURE = "Unable to add group";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_GROUPNAME + " GROUPNAME";

    private final Group group;

    public AddGroupCommand(Group group) {
        this.group = group;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        if (model.addGroup(group)) {
            return new CommandResult(MESSAGE_SUCCESS + group.getGroupName().toString());
        } else {
            return new CommandResult(MESSAGE_FAILURE);
        }
    }
}
