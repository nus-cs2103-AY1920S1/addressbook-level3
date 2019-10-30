package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.PersonPossessesTagsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Finds and lists all persons in address book who possess all specified tags.
 * Tag matching is case insensitive.
 */
public class FindTagPeopleCommand extends Command {

    public static final String COMMAND_WORD = "findtagpeople";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons who possess all "
            + "the specified tags (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " t/nonsmoker t/senior";

    private final List<String> tagNames;

    public FindTagPeopleCommand(List<String> tagNames) {
        this.tagNames = tagNames;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (tagNames.size() == 0) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        for (String tag : tagNames) {
            if ((tag.length() == 0) || (tag.matches("^.*[^a-zA-Z0-9 ].*$"))) {
                throw new CommandException(
                        String.format(
                                Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                                MESSAGE_USAGE
                        )
                );
            }
        }

        List<Tag> tags = this.tagNames.stream().map(p -> new Tag(p)).collect(Collectors.toList());
        PersonPossessesTagsPredicate predicate = new PersonPossessesTagsPredicate(tags);

        model.updateFilteredPersonList(predicate);

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()),
                false,
                false,
                false,
                true,
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
                || (other instanceof FindTagPeopleCommand // instanceof handles nulls
                && tagNames.equals(((FindTagPeopleCommand) other).tagNames)); // state check
    }
}
