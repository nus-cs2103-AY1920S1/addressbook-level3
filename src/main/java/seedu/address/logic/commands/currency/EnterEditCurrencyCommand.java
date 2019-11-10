package seedu.address.logic.commands.currency;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.currency.CustomisedCurrency;

/**
 * Enters the currency editing screen for the indicated currency.
 */
public class EnterEditCurrencyCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enters the currency information editing screen";

    public static final String MESSAGE_ENTER_EDIT_CURRENCY_SUCCESS = " Currency successfully added ! %1$s";

    private final Index indexToEdit;

    public EnterEditCurrencyCommand(Index indexToEdit) {
        this.indexToEdit = indexToEdit;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<CustomisedCurrency> lastShownList = model.getFilteredCurrencyList();

        if (indexToEdit.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GENERIC_INDEX);
        }

        CustomisedCurrency currencyToEdit = lastShownList.get(indexToEdit.getZeroBased());
        EditCurrencyFieldCommand.EditCurrencyDescriptor editCurrencyDescriptor =
                new EditCurrencyFieldCommand.EditCurrencyDescriptor(currencyToEdit);

        model.setPageStatus(model.getPageStatus()
                .withNewPageType(PageType.ADD_CURRENCY)
                .withNewCustomisedCurrency(currencyToEdit)
                .withNewEditCurrencyDescriptor(editCurrencyDescriptor));

        return new CommandResult(String.format(MESSAGE_ENTER_EDIT_CURRENCY_SUCCESS, currencyToEdit), true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof EnterEditCurrencyCommand;
    }
}
