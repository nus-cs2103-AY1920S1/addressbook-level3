package seedu.weme.logic.commands.memecommand;

import static java.util.Objects.requireNonNull;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_FILEPATH;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_TAG;

import java.io.IOException;

import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.Model;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.util.ImageUtil;

/**
 * Adds a meme to Weme.
 */
public class MemeAddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_DESCRIPTION = COMMAND_WORD + ": adds a meme to Weme.";
    public static final String MESSAGE_USAGE = MESSAGE_DESCRIPTION
            + " Parameters: "
            + PREFIX_FILEPATH + "PATH "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FILEPATH + "C:\\Users\\username\\Downloads\\funny_meme.jpg "
            + PREFIX_DESCRIPTION + "Popular Meme among SoC Students  "
            + PREFIX_TAG + "funny";

    public static final String MESSAGE_SUCCESS = "New meme added: %1$s";
    public static final String MESSAGE_DUPLICATE_MEME = "This meme already exists in Weme";
    public static final String MESSAGE_COPY_FAILURE = "Error encountered while copying the meme to data folder";


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

        // Copy the meme to internal data directory
        Meme copiedMeme;
        try {
            copiedMeme = ImageUtil.copyMeme(toAdd, model.getMemeImagePath());
            model.addMemeToRecords(toAdd);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_COPY_FAILURE);
        }

        if (model.hasMeme(copiedMeme)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEME);
        }

        model.addMeme(copiedMeme);
        CommandResult result = new CommandResult(String.format(MESSAGE_SUCCESS, copiedMeme));
        model.commitWeme(result.getFeedbackToUser());

        return result;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MemeAddCommand // instanceof handles nulls
                && toAdd.equals(((MemeAddCommand) other).toAdd));
    }
}
