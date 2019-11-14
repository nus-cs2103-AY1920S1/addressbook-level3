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
import seedu.address.model.currency.exceptions.CurrencyNotRemovableException;

/**
 * Deletes a customised currency from currency list.
 */
public class DeleteCurrencyCommand extends Command {
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a currency from currency list.\n"
            + "Parameters: INDEX (must be a positive integer)";
    private static final String MESSAGE_DELETE_DEFAULT_CURRENCY_FAILURE = "Failed to delete your currency, "
            + "Singapore dollar is the default currency of the application and it is not removable.";
    private static final String MESSAGE_DELETE_CURRENCY_FAILURE = "Failed to delete your currency, "
            + "the index you specified is likely out of bounds.";
    private static final String MESSAGE_DELETE_CURRENCY_SUCCESS = "Deleted your currency : %1$s!";

    private final Index indexToDelete;

    public DeleteCurrencyCommand(Index indexToDelete) {
        this.indexToDelete = indexToDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<CustomisedCurrency> lastShownList = model.getFilteredCurrencyList();

        if (indexToDelete.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GENERIC_INDEX);
        }

        CustomisedCurrency currencyToDelete = lastShownList.get(indexToDelete.getZeroBased());
        try {
            model.deleteCurrency(currencyToDelete);
        } catch (CurrencyNotRemovableException ex) {
            return new CommandResult(MESSAGE_DELETE_DEFAULT_CURRENCY_FAILURE);
        } catch (Exception e) {
            return new CommandResult(MESSAGE_DELETE_CURRENCY_FAILURE);
        }
        model.setPageStatus(model.getPageStatus()
                .withResetEditEventDescriptor()
                .withNewPageType(PageType.ADD_CURRENCY)
                .withResetCustomisedCurrency());
        return new CommandResult(String.format(MESSAGE_DELETE_CURRENCY_SUCCESS, currencyToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof DeleteCurrencyCommand;
    }

}
