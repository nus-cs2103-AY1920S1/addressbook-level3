package seedu.algobase.logic.commands.tag;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TAG_COLOR;

import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.Command;
import seedu.algobase.logic.commands.CommandResult;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;
import seedu.algobase.model.tag.Tag;

/**
 * Adds a Tag to the algobase.
 */
public class AddTagCommand extends Command {

    public static final String COMMAND_WORD = "addtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a Tag to AlgoBase.\n"
            + "Parameters:\n"
            + PREFIX_TAG + "TAG NAME "
            + "[" + PREFIX_TAG_COLOR + "TAG COLOR]\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + PREFIX_TAG + "Easy "
            + PREFIX_TAG_COLOR + "BLUE";

    public static final String MESSAGE_SUCCESS = "New Tag [%1$s] added to AlgoBase.";
    public static final String MESSAGE_DUPLICATE_TAG = "Tag [%1$s] already exists in AlgoBase.";

    private final Tag toAdd;

    /**
     * Creates an AddTagCommand to add the specified {@code Tag}
     */
    public AddTagCommand(Tag tag) {
        requireNonNull(tag);
        toAdd = tag;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasTag(toAdd)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_TAG, toAdd.getName()));
        }

        model.addTag(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTagCommand // instanceof handles nulls
                && toAdd.equals(((AddTagCommand) other).toAdd));
    }
}
