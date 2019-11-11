//@@author e0031374
package tagline.logic.commands.group;

import static java.util.Objects.requireNonNull;
import static tagline.model.contact.ContactModel.PREDICATE_SHOW_ALL_CONTACTS;
import static tagline.model.group.GroupModel.PREDICATE_SHOW_ALL_GROUPS;

import java.util.function.Predicate;

import tagline.logic.commands.CommandResult;
import tagline.logic.commands.CommandResult.ViewType;
import tagline.logic.commands.exceptions.CommandException;
import tagline.model.Model;
import tagline.model.group.Group;

/**
 * Lists all contacts in the address book to the user.
 */
public class ListGroupCommand extends GroupCommand {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all groups";
    public static final String MESSAGE_KEYWORD_SUCCESS = "Success! Listing groups found.";
    public static final String MESSAGE_KEYWORD_EMPTYLIST = "No groups matching keywords";

    public static final String MESSAGE_USAGE = COMMAND_KEY + " " + COMMAND_WORD + ": List all groups "
            + "stored.\n "
            + "Example: " + COMMAND_KEY + " " + COMMAND_WORD + "\n";

    private final Predicate<Group> predicate;

    /**
     * @param predicate to list groups by
     */
    public ListGroupCommand(Predicate<Group> predicate) {
        this.predicate = predicate;
    }

    public ListGroupCommand() {
        this.predicate = PREDICATE_SHOW_ALL_GROUPS;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        GroupCommand.syncGroupBook(model); // updates all Groups to ensure no outdated MemberIds

        model.updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);
        model.updateFilteredGroupList(predicate);

        if (model.getFilteredGroupList().size() == 0) {
            throw new CommandException(String.format(MESSAGE_KEYWORD_EMPTYLIST));
        }

        return new CommandResult(MESSAGE_KEYWORD_SUCCESS, ViewType.GROUP_LIST);
    }

}
