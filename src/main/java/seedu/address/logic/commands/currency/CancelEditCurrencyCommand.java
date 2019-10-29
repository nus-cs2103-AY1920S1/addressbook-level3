package seedu.address.logic.commands.currency;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.currency.CustomisedCurrency;

/**
 * Command that cancels editing the currency, bringing the user back to the expenses manager screen.
 */
public class CancelEditCurrencyCommand extends Command {
    public static final String COMMAND_WORD = "cancel";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Cancels editing or creating a new currency.";

    public static final String MESSAGE_CANCEL_CREATE_SUCCESS = "Cancelled creating the currency!";
    public static final String MESSAGE_CANCEL_EDIT_SUCCESS = "Cancelled editing the currency: %1$s";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        CustomisedCurrency currentlyEditingCustomisedCurrency = model.getPageStatus().getCustomisedCurrency();
        model.setPageStatus(model.getPageStatus()
                .withResetEditCurrencyDescriptor()
                .withNewPageType(PageType.EXPENSE_MANAGER)
                .withResetCustomisedCurrency());

        if (currentlyEditingCustomisedCurrency == null) {
            return new CommandResult(MESSAGE_CANCEL_CREATE_SUCCESS, true);
        } else {
            return new CommandResult(
                    String.format(MESSAGE_CANCEL_EDIT_SUCCESS, currentlyEditingCustomisedCurrency), true);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof CancelEditCurrencyCommand;
    }
}
