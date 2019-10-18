package seedu.weme.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_FILEPATH;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_TAG;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.Model;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.util.MemeUtil;

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
            copiedMeme = MemeUtil.copyMeme(toAdd, model.getMemeImagePath());
        } catch (FileAlreadyExistsException e) {
            throw new CommandException(MESSAGE_DUPLICATE_MEME);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_COPY_FAILURE);
        }

        if (model.hasMeme(copiedMeme)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEME);
        }

        model.addMeme(copiedMeme);
        model.commitMemeBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, copiedMeme));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MemeAddCommand // instanceof handles nulls
                && toAdd.equals(((MemeAddCommand) other).toAdd));
    }
}
