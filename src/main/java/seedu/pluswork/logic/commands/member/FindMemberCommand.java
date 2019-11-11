package seedu.pluswork.logic.commands.member;

import static java.util.Objects.requireNonNull;

import seedu.pluswork.commons.core.Messages;
import seedu.pluswork.commons.util.AppUtil;
import seedu.pluswork.logic.commands.Command;
import seedu.pluswork.logic.commands.CommandResult;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.member.MemberNameContainsKeywordsPredicate;

/**
 * Finds and lists all members in project dashboard book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindMemberCommand extends Command {

    public static final String COMMAND_WORD = "find-member";
    public static final String PREFIX_USAGE = "{KEYWORD}";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final MemberNameContainsKeywordsPredicate predicate;

    public FindMemberCommand(MemberNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredMembersList(predicate);
        AppUtil.scheduleDataUpdate(model::updateData);
        return new CommandResult(
                String.format(Messages.MESSAGE_MEMBERS_LISTED_OVERVIEW, model.getFilteredMembersList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindMemberCommand // instanceof handles nulls
                && predicate.equals(((FindMemberCommand) other).predicate)); // state check
    }
}
