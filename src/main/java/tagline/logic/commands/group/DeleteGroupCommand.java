package tagline.logic.commands.group;

import static java.util.Objects.requireNonNull;
import static tagline.model.contact.ContactModel.PREDICATE_SHOW_ALL_CONTACTS;
import static tagline.model.group.GroupModel.PREDICATE_SHOW_ALL_GROUPS;

import tagline.logic.commands.CommandResult;
import tagline.logic.commands.CommandResult.ViewType;
import tagline.logic.commands.exceptions.CommandException;
import tagline.model.Model;

import tagline.model.group.Group;
import tagline.model.group.GroupNameEqualsKeywordPredicate;

/**
 * Deletes and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class DeleteGroupCommand extends GroupCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_KEYWORD_EMPTYLIST = "No groups matching keyword";
    //public static final String MESSAGE_KEYWORD_EMPTYLIST = "No groups matching keyword: %1$s";
    public static final String MESSAGE_UI = "UI: now displaying all Contacts in found group";
    public static final String MESSAGE_KEYWORD_SUCCESS = "Success! Group deleted: %n%s%n" + MESSAGE_UI;

    public static final String MESSAGE_USAGE = COMMAND_KEY + " " + COMMAND_WORD
            + ": Delete a group whose name matches"
            + "the specified keyword (case-insensitive).\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_KEY + " " + COMMAND_WORD + " wanna-one";

    private final GroupNameEqualsKeywordPredicate predicate;

    public DeleteGroupCommand(GroupNameEqualsKeywordPredicate predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Group targetGroup = GroupCommand.findOneGroup(model, predicate);

        model.deleteGroup(targetGroup);
        model.updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);
        // note, after deleting a group, all groups will be shown.
        // this will suffice until a View for Group has been established, note the respective comment in test as well
        model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);

        return new CommandResult(
                String.format(MESSAGE_KEYWORD_SUCCESS, targetGroup.getGroupName().value), ViewType.CONTACT);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteGroupCommand // instanceof handles nulls
                && predicate.equals(((DeleteGroupCommand) other).predicate)); // state check
    }
}
