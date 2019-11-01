package seedu.address.logic.commands.currency;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.currency.CustomisedCurrency;
import seedu.address.model.currency.exceptions.CurrencyNotFoundException;

/**
 * Placeholder.
 */
public class DoneEditCurrencyCommand extends Command {
    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Commits your new or edited currency information.";

    public static final String MESSAGE_CREATE_EXPENDITURE_SUCCESS = "Created CustomisedCurrency: %1$s";
    public static final String MESSAGE_EDIT_EXPENDITURE_SUCCESS = "Edited CustomisedCurrency: %1$s";
    public static final String MESSAGE_NOT_EDITED = "All the fields must be provided!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        EditCurrencyFieldCommand.EditCurrencyDescriptor editCurrencyDescriptor = model.getPageStatus()
                .getEditCurrencyDescriptor();
        CustomisedCurrency currencyToEdit = model.getPageStatus().getCustomisedCurrency();
        CustomisedCurrency currencyToAdd;

        if (editCurrencyDescriptor == null) {
            return new CommandResult(MESSAGE_NOT_EDITED);
        }

        try {

            if (currencyToEdit == null) {
                //buildCustomisedCurrency() requires compulsory fields to be non null, failing which
                //NullPointerException is caught below
                currencyToAdd = editCurrencyDescriptor.buildCurrency();
                model.addCurrency(currencyToAdd);
                model.selectCurrency(currencyToAdd);
            } else {
                //edit the current "selected" currency
                currencyToAdd = editCurrencyDescriptor.buildCurrency(currencyToEdit);
                model.setCurrency(currencyToEdit, currencyToAdd);
                model.selectCurrency(currencyToAdd);
            }

            model.setPageStatus(model.getPageStatus()
                    .withResetEditEventDescriptor()
                    .withNewPageType(PageType.ADD_CURRENCY)
                    .withResetCustomisedCurrency());

            return new CommandResult(String.format(MESSAGE_EDIT_EXPENDITURE_SUCCESS, currencyToAdd), true);
        } catch (NullPointerException | CurrencyNotFoundException ex) {
            throw new CommandException(MESSAGE_NOT_EDITED);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof DoneEditCurrencyCommand;
    }

}
