package seedu.weme.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_FILEPATH;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.Model;
import seedu.weme.model.meme.Meme;

/**
 * Adds a meme to the meme book.
 */
public class MemeAddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a meme to weme. "
            + "Parameters: "
            + PREFIX_FILEPATH + "PATH "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FILEPATH + "C:\\Users\\username\\Downloads\\funny_meme.jpg "
            + PREFIX_DESCRIPTION + "Popular Meme among SoC Students  "
            + PREFIX_TAG + "funny";

    public static final String MESSAGE_SUCCESS = "New meme added: %1$s";
    public static final String MESSAGE_DUPLICATE_MEME = "This meme already exists in weme";

    private final Meme toAdd;

    /**
     * Creates an MemeAddCommand to add the specified {@code Meme}
     */
    public MemeAddCommand(Meme meme) {
        requireNonNull(meme);
        toAdd = meme;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasMeme(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEME);
        }

        model.addMeme(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MemeAddCommand // instanceof handles nulls
                && toAdd.equals(((MemeAddCommand) other).toAdd));
    }
}
