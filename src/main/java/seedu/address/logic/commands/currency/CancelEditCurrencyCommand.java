package seedu.address.logic.commands.currency;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;

/**
 * Command that cancels editing the currency, bringing the user back to the expenses manager screen.
 */
public class CancelEditCurrencyCommand extends Command {
    public static final String COMMAND_WORD = "return";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exited the currency page.";

    public static final String MESSAGE_EXIT_CURRENCY_SUCCESS = "Returned to the expense manager page!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.setPageStatus(model.getPageStatus()
                .withResetEditCurrencyDescriptor()
                .withNewPageType(PageType.EXPENSE_MANAGER)
                .withResetCustomisedCurrency());
        return new CommandResult(MESSAGE_EXIT_CURRENCY_SUCCESS, true);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof CancelEditCurrencyCommand;
    }
}
