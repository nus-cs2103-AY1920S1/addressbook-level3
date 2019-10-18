package seedu.weme.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.Model;


/**
 * Stage Command.
 */
public class MemeStageCommand extends Command {

    public static final String COMMAND_WORD = "stage";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Stages a meme. "
            + "Parameters: "
            + "Parameters: INDEX (must be a positive integer) \n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_SUCCESS = "New meme staged: %1$s";

    private final Index toStage;

    /**
     * Creates an MemeAddCommand to add the specified {@code Meme}
     */
    public MemeStageCommand(Index index) {
        requireNonNull(index);
        toStage = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        /*
        if (model.hasMeme(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEME);
        }

        model.addMeme(toAdd);
        */
        return new CommandResult(String.format(MESSAGE_SUCCESS, toStage));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MemeStageCommand // instanceof handles nulls
                && toStage.equals(((MemeStageCommand) other).toStage));
    }
}
