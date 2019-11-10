package seedu.address.logic.commands.expenditure;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.expenditure.edit.EditExpenditureFieldCommand;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.expenditure.Expenditure;

/**
 * Placeholder.
 */
public class EnterEditExpenditureCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enters the expenditure information editing screen\n"
            + "Parameters: INDEX (must be a positive integer)";

    public static final String MESSAGE_ENTER_EDIT_EXPENDITURE_SUCCESS = " Welcome to your expenditure! %1$s";

    private final Index indexToEdit;

    public EnterEditExpenditureCommand(Index indexToEdit) {
        this.indexToEdit = indexToEdit;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Assumes EnterDayCommand has already been called
        List<Expenditure> lastShownList = model.getPageStatus().getTrip().getExpenditureList().internalList;

        if (indexToEdit.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GENERIC_INDEX);
        }

        Expenditure expenditureToEdit = lastShownList.get(indexToEdit.getZeroBased());
        EditExpenditureFieldCommand.EditExpenditureDescriptor editExpenditureDescriptor =
                new EditExpenditureFieldCommand.EditExpenditureDescriptor(expenditureToEdit);

        model.setPageStatus(model.getPageStatus()
                .withNewPageType(PageType.ADD_EXPENDITURE)
                .withNewExpenditure(expenditureToEdit)
                .withNewEditExpenditureDescriptor(editExpenditureDescriptor));

        return new CommandResult(MESSAGE_ENTER_EDIT_EXPENDITURE_SUCCESS + expenditureToEdit.getName().toString(),
                true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof EnterEditExpenditureCommand;
    }
}
