package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;

public class DeleteGroupCommand extends Command{
    public static final String COMMAND_WORD = "deletegroup";
    public static final String MESSAGE_SUCCESS = "Delete group success";
    public static final String MESSAGE_FAILURE = "Unable to delete group";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_GROUPNAME + " GROUPNAME";

    public final GroupName groupName;

    public DeleteGroupCommand(GroupName groupName){
        this.groupName = groupName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Group toDelete = model.findGroup(groupName);
        if(toDelete != null){
            if(model.deleteGroup(toDelete.getGroupID())){
                return new CommandResult(MESSAGE_SUCCESS);
            }
        }
        return new CommandResult(MESSAGE_FAILURE);
    }
}
