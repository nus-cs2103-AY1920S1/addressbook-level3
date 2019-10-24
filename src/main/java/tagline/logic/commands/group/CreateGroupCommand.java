package tagline.logic.commands.group;

import static java.util.Objects.requireNonNull;
import static tagline.logic.parser.group.GroupCliSyntax.PREFIX_CONTACTID;

import tagline.logic.commands.CommandResult;
import tagline.logic.commands.exceptions.CommandException;
import tagline.model.Model;
import tagline.model.group.Group;

/**
 * Adds a group to the address book.
 */
public class CreateGroupCommand extends GroupCommand {

    public static final String COMMAND_WORD = "create";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a group. "
            + "Parameters: GROUP_NAME "
            + "[" + PREFIX_CONTACTID + "CONTACT_ID]...\n"
            + "Example: " + COMMAND_WORD + " BTS_ARMY "
            + PREFIX_CONTACTID + "1077 "
            + PREFIX_CONTACTID + "1078";

    public static final String MESSAGE_SUCCESS = "New group added: %1$s";
    public static final String MESSAGE_DUPLICATE_GROUP = "This group already exists in the group book";

    private final Group toAdd;

    /**
     * Creates an CreateGroupCommand to add the specified {@code Group}
     */
    public CreateGroupCommand(Group group) {
        requireNonNull(group);
        toAdd = group;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasGroup(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_GROUP);
        }

        // look for the contacts and get their contactID, then edit the Group
        // ensures Group members are ContactIds that can be found in Model
        Group verifiedGroup = GroupCommand.verifyGroupWithModel(model, toAdd);

        model.addGroup(verifiedGroup);
        return new CommandResult(String.format(MESSAGE_SUCCESS, verifiedGroup));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CreateGroupCommand // instanceof handles nulls
                && toAdd.equals(((CreateGroupCommand) other).toAdd));
    }
}
