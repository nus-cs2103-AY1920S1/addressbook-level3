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
 * Placeholder.
 */
public class SelectCurrencyCommand extends Command {
    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Selects a currency from currency list.\n"
            + "Parameters: INDEX (must be a positive integer)";

    private static final String MESSAGE_SELECT_CURRENCY_FAILURE = "Failed to select your currency, "
            + "the index you specified is likely out of bounds!";
    private static final String MESSAGE_SELECT_CURRENCY_SUCCESS = "Selected your currency : %1$s!";

    private final Index indexToSelect;

    public SelectCurrencyCommand(Index indexToSelect) {
        this.indexToSelect = indexToSelect;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<CustomisedCurrency> lastShownList = model.getFilteredCurrencyList();

        if (indexToSelect.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GENERIC_INDEX);
        }

        CustomisedCurrency currencyToSelect = lastShownList.get(indexToSelect.getZeroBased());
        try {
            model.selectCurrency(currencyToSelect);
        } catch (Exception ex) {
            return new CommandResult(MESSAGE_SELECT_CURRENCY_FAILURE);
        }

        model.setPageStatus(model.getPageStatus()
                .withResetEditEventDescriptor()
                .withNewPageType(PageType.ADD_CURRENCY)
                .withResetCustomisedCurrency());

        return new CommandResult(String.format(MESSAGE_SELECT_CURRENCY_SUCCESS, currencyToSelect));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof SelectCurrencyCommand;
    }

}
