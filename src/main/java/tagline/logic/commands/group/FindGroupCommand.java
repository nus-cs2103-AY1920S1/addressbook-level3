package tagline.logic.commands.group;

import static java.util.Objects.requireNonNull;

import tagline.logic.commands.CommandResult;
import tagline.logic.commands.CommandResult.ViewType;
import tagline.logic.commands.exceptions.CommandException;
import tagline.model.Model;

import tagline.model.group.Group;
import tagline.model.group.GroupNameEqualsKeywordPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindGroupCommand extends GroupCommand {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_KEYWORD_EMPTYLIST = "No groups matching keyword";
    //public static final String MESSAGE_KEYWORD_EMPTYLIST = "No groups matching keyword: %1$s";
    public static final String MESSAGE_UI = "UI: now displaying all Contacts in requested group";
    public static final String MESSAGE_KEYWORD_SUCCESS = "Success! Displaying group: %n%s%n" + MESSAGE_UI;

    public static final String MESSAGE_USAGE = COMMAND_KEY + " " + COMMAND_WORD
            + ": Shows all contacts whose is in Group matching exactly"
            + "the specified keywords (case-insensitive) and displays them as a list.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_KEY + " " + COMMAND_WORD + " exo";

    private final GroupNameEqualsKeywordPredicate predicate;

    public FindGroupCommand(GroupNameEqualsKeywordPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        //Group targetGroup = optionalGroup.get();
        Group targetGroup = GroupCommand.findOneGroup(model, predicate);

        // set Group members to only those that can be found as ContactId in ContactList
        Group verifiedGroup = GroupCommand.verifyGroupWithModel(model, targetGroup);
        model.setGroup(targetGroup, verifiedGroup);

        return new CommandResult(
                String.format(MESSAGE_KEYWORD_SUCCESS, verifiedGroup), ViewType.CONTACT);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindGroupCommand // instanceof handles nulls
                && predicate.equals(((FindGroupCommand) other).predicate)); // state check
    }
}
