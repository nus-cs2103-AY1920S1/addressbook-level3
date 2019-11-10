package seedu.address.logic.commands.expenditure;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.expenditure.Expenditure;

/**
 * Placeholder.
 */
public class DeleteExpenditureCommand extends Command {
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an expenditure from expense manager.\n"
            + "Parameters: INDEX (must be a positive integer)";

    public static final String MESSAGE_DELETE_EXPENDITURE_FAILURE = "Failed to delete your expenditure, "
            + "the expenditure you are trying to remove is likely to be associated with an event, please go to"
            + " the corresponding event to delete the expenditure";
    public static final String MESSAGE_DELETE_EXPENDITURE_SUCCESS = "Deleted your expenditure : ";

    private final Index indexToDelete;

    public DeleteExpenditureCommand(Index indexToDelete) {
        this.indexToDelete = indexToDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Assumes enter trip has been called first
        List<Expenditure> lastShownList = model.getPageStatus().getTrip().getExpenditureList().internalList;

        if (indexToDelete.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GENERIC_INDEX);
        }

        // References preserved by PageStatus
        Expenditure expenditureToDelete = lastShownList.get(indexToDelete.getZeroBased());
        try {
            model.getPageStatus().getTrip().getExpenditureList().removeByUser(expenditureToDelete);
        } catch (Exception ex) {
            return new CommandResult(MESSAGE_DELETE_EXPENDITURE_FAILURE);
        }

        model.setPageStatus(model.getPageStatus()
                .withResetEditEventDescriptor()
                .withNewPageType(PageType.EXPENSE_MANAGER));

        return new CommandResult(MESSAGE_DELETE_EXPENDITURE_SUCCESS
                + expenditureToDelete.getName().toString(), true, COMMAND_WORD);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof DeleteExpenditureCommand;
    }

}
