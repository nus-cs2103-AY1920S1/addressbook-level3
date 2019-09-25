package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupDescriptor;
import seedu.address.model.group.GroupName;

/**
 * Add a group.
 */
public class AddGroupCommand extends Command {
    public static final String COMMAND_WORD = "addgroup";
    public static final String MESSAGE_SUCCESS = "New group added: ";
    public static final String MESSAGE_FAILURE = "Unable to add group: Group already exist";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_GROUPNAME + " GROUPNAME";

    private final GroupDescriptor groupDescriptor;

    public AddGroupCommand(GroupDescriptor groupDescriptor) {
        this.groupDescriptor = groupDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        if(model.findGroup(groupDescriptor.getGroupName()) != null){
            return new CommandResult(MESSAGE_FAILURE);
        }

        if (model.addGroup(groupDescriptor)) {
            return new CommandResult(MESSAGE_SUCCESS + groupDescriptor.getGroupName().toString());
        } else {
            return new CommandResult(MESSAGE_FAILURE);
        }
    }
}
