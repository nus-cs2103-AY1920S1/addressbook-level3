package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.policy.PolicyPossessesTagsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Finds and lists all persons in address book who possess all specified tags.
 * Tag matching is case insensitive.
 */
public class FindTagPolicyCommand extends Command {

    public static final String COMMAND_WORD = "findtagpolicy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all policies that possess all "
            + "the specified tags (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: TAG [MORE_TAGS]... (will be converted to lowercase)\n"
            + "Example: " + COMMAND_WORD + " t/term t/senior";

    private final List<String> tagNames;

    public FindTagPolicyCommand(List<String> tagNames) {
        this.tagNames = tagNames;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (tagNames.size() == 0) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        for (String tag : tagNames) {
            if ((tag.length() == 0) || (tag.matches("^.*[^a-z0-9 ].*$"))) {
                throw new CommandException(
                        String.format(
                                Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                                MESSAGE_USAGE
                        )
                );
            }
        }

        List<Tag> tags = this.tagNames.stream().map(p -> new Tag(p)).collect(Collectors.toList());
        PolicyPossessesTagsPredicate predicate = new PolicyPossessesTagsPredicate(tags);

        model.updateFilteredPolicyList(predicate);

        return new CommandResult(
                String.format(Messages.MESSAGE_POLICIES_LISTED_OVERVIEW, model.getFilteredPolicyList().size()),
                false,
                false,
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindTagPolicyCommand // instanceof handles nulls
                && tagNames.equals(((FindTagPolicyCommand) other).tagNames)); // state check
    }
}
