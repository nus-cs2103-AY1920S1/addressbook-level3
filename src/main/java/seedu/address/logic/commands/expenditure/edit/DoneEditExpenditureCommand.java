package seedu.address.logic.commands.expenditure.edit;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.model.expenditure.exceptions.ExpenditureNotFoundException;

/**
 * Placeholder.
 */
public class DoneEditExpenditureCommand extends Command {
    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Commits your new or edited expenditure information.";

    public static final String MESSAGE_CREATE_EXPENDITURE_SUCCESS = "Created Expenditure: %1$s";
    public static final String MESSAGE_EDIT_EXPENDITURE_SUCCESS = "Edited Expenditure: %1$s";
    public static final String MESSAGE_NOT_EDITED = "All the fields must be provided!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        EditExpenditureFieldCommand.EditExpenditureDescriptor editExpenditureDescriptor = model.getPageStatus()
                .getEditExpenditureDescriptor();
        Expenditure expenditureToEdit = model.getPageStatus().getExpenditure();
        Expenditure expenditureToAdd;

        if (editExpenditureDescriptor == null) {
            return new CommandResult(MESSAGE_NOT_EDITED);
        }

        try {
            if (expenditureToEdit == null) {
                //buildExpenditure() requires compulsory fields to be non null, failing which
                //NullPointerException is caught below
                expenditureToAdd = editExpenditureDescriptor.buildExpenditure();
                model.getPageStatus().getTrip().getExpenditureList().add(expenditureToAdd);
            } else {
                //edit the current "selected" expenditure
                expenditureToAdd = editExpenditureDescriptor.buildExpenditure(expenditureToEdit);
                model.getPageStatus().getTrip().getExpenditureList().set(expenditureToEdit, expenditureToAdd);
            }

            model.setPageStatus(model.getPageStatus()
                    .withResetEditEventDescriptor()
                    .withNewPageType(PageType.EXPENSE_MANAGER)
                    .withResetExpenditure());

            return new CommandResult(String.format(MESSAGE_EDIT_EXPENDITURE_SUCCESS, expenditureToAdd), true);
        } catch (NullPointerException | ExpenditureNotFoundException ex) {
            throw new CommandException(MESSAGE_NOT_EDITED);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof DoneEditExpenditureCommand;
    }

}
