package seedu.address.logic.commands.expenditure.edit;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.expenditure.Expenditure;

import static java.util.Objects.requireNonNull;

/**
 * Command that cancels editing the expenditure, bringing the user back to the expenses manager screen.
 */
public class CancelEditExpenditureCommand extends Command {
    public static final String COMMAND_WORD = "cancel";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Cancels editing or creating a new expenditure.";

    public static final String MESSAGE_CANCEL_CREATE_SUCCESS = "Cancelled creating the expenditure!";
    public static final String MESSAGE_CANCEL_EDIT_SUCCESS = "Cancelled editing the expenditure: %1$s";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Expenditure currentlyEditingExpenditure = model.getPageStatus().getExpenditure();
        model.setPageStatus(model.getPageStatus()
                .withResetEditExpenditureDescriptor()
                .withNewPageType(PageType.OVERALL_VIEW)
                .withResetExpenditure());

        if (currentlyEditingExpenditure == null) {
            return new CommandResult(MESSAGE_CANCEL_CREATE_SUCCESS, true);
        } else {
            return new CommandResult(
                    String.format(MESSAGE_CANCEL_EDIT_SUCCESS, currentlyEditingExpenditure), true);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof CancelEditExpenditureCommand;
    }
}
