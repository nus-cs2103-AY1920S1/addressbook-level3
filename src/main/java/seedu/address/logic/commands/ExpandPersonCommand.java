package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Expands the selected person.
 */
public class ExpandPersonCommand extends Command {

    public static final String COMMAND_WORD = "expandperson";

    public static final String MESSAGE_SUCCESS = "Expanded: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
        + "by the index number used in the displayed person list. \n"
        + "Example: " + COMMAND_WORD + " 1 ";

    private final Index index;

    public ExpandPersonCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToExpand = lastShownList.get(index.getZeroBased());

        return new CommandResult(String.format(MESSAGE_SUCCESS, personToExpand), personToExpand);
    }
}
