package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;
import seedu.algobase.model.tag.Tag;

/**
 * Adds a Tag to the algobase.
 */
public class AddTagCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Tag to the algobase. "
            + "Parameters: "
            + PREFIX_TAG + "TAG NAME";

    public static final String MESSAGE_SUCCESS = "New Tag added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This Tag already exists in the algobase";

    private final Tag toAdd;

    /**
     * Creates an AddTagCommand to add the specified {@code Tag}
     */
    public AddTagCommand(Tag tag) {
        requireNonNull(tag);
        toAdd = tag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasTag(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addTag(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTagCommand // instanceof handles nulls
                && toAdd.equals(((AddTagCommand) other).toAdd));
    }
}
