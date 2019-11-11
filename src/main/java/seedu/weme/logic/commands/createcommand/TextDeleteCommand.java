package seedu.weme.logic.commands.createcommand;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.weme.commons.core.Messages;
import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.Model;
import seedu.weme.model.template.MemeText;

/**
 * Deletes a meme text identified using it's index from the text list.
 */
public class TextDeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_DESCRIPTION = COMMAND_WORD
            + ": Deletes the meme text identified by the index number in the displayed list.";

    public static final String MESSAGE_USAGE = MESSAGE_DESCRIPTION
            + "\nParameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_MEME_TEXT_SUCCESS = "Deleted text: %1$s";

    private final Index targetIndex;

    public TextDeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<MemeText> memeTextList = model.getMemeTextList();

        if (targetIndex.getZeroBased() >= memeTextList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEME_TEXT_DISPLAYED_INDEX);
        }

        MemeText textToDelete = memeTextList.get(targetIndex.getZeroBased());
        model.deleteMemeText(textToDelete);

        CommandResult result = new CommandResult(String.format(MESSAGE_DELETE_MEME_TEXT_SUCCESS, textToDelete));
        model.commitWeme(result.getFeedbackToUser());

        return result;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TextDeleteCommand // instanceof handles nulls
                && targetIndex.equals(((TextDeleteCommand) other).targetIndex)); // state check
    }
}
