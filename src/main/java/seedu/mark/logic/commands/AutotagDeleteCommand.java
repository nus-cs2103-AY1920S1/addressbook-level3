package seedu.mark.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;

import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.model.Model;
import seedu.mark.storage.Storage;

/**
 * Deletes an automatic tagger from Mark.
 */
public class AutotagDeleteCommand extends Command {

    public static final String COMMAND_WORD = "autotag-delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the automatic tagger with the given tag name.\n"
            + "Parameters: TAG_NAME\n"
            + "Example: " + COMMAND_WORD + " Quiz";

    public static final String MESSAGE_AUTOTAG_DELETED = "Autotag with this tag name deleted successfully: %1$s";
    public static final String MESSAGE_AUTOTAG_DOES_NOT_EXIST = "No autotag with this tag name was found: %1$s";

    private final String taggerName;

    public AutotagDeleteCommand(String taggerName) {
        requireNonNull(taggerName);
        this.taggerName = taggerName;
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        requireAllNonNull(model, storage);

        boolean taggerRemoved = model.removeTagger(taggerName).isPresent();

        if (!taggerRemoved) {
            throw new CommandException(String.format(MESSAGE_AUTOTAG_DOES_NOT_EXIST, taggerName));
        }

        String resultMessage = String.format(MESSAGE_AUTOTAG_DELETED, taggerName);
        model.saveMark(resultMessage);
        return new CommandResult(resultMessage);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AutotagDeleteCommand // instanceof handles nulls
                && taggerName.equals(((AutotagDeleteCommand) other).taggerName)); // state check
    }

}
