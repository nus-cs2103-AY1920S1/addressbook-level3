package seedu.revision.logic.commands.main;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.revision.commons.core.Messages;
import seedu.revision.commons.core.index.Index;
import seedu.revision.logic.commands.Command;
import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.model.Model;
import seedu.revision.model.answerable.Answerable;

/**
 * Deletes one or more answerables identified using it's displayed index from the revision tool.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes one or more questions identified by the index number used in the displayed question list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 2 3";

    public static final String MESSAGE_DELETE_ANSWERABLE_SUCCESS = "Deleted Questions: %s";

    private ArrayList<Index> targetIndexList = new ArrayList<>();

    public DeleteCommand(ArrayList<Index> targetIndexList) {
        for (Index targetIndex : targetIndexList) {
            this.targetIndexList.add(targetIndex);
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Answerable> lastShownList = model.getFilteredAnswerableList();
        ArrayList<Answerable> toBeDeleted = new ArrayList<>();

        for (Index targetIndex : targetIndexList) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_ANSWERABLE_DISPLAYED_INDEX);
            }
        }

        for (int i = 0; i <= targetIndexList.size() - 1; i++) {
            for (int j = i + 1; j <= targetIndexList.size() - 1; j++) {
                if (targetIndexList.get(i).equals(targetIndexList.get(j))) {
                    throw new CommandException("You have indicated duplicate questions to be deleted. \n"
                            + "Please indicate only once for each question to be deleted!");
                }
            }
        }

        for (Index targetIndex : targetIndexList) {
            Answerable answerableToDelete = lastShownList.get(targetIndex.getZeroBased());
            toBeDeleted.add(answerableToDelete);
        }

        for (Answerable answerableToDelete : toBeDeleted) {
            model.deleteAnswerable(answerableToDelete);
        }
        return new CommandResultBuilder().withFeedBack(String.format(MESSAGE_DELETE_ANSWERABLE_SUCCESS,
                toBeDeleted.toString())).build();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand); // instanceof handles nulls
                // && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
