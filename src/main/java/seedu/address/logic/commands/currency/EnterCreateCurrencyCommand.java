package seedu.address.logic.commands.currency;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;

/**
 * Command that brings the user to the trip creation screen.
 */
public class EnterCreateCurrencyCommand extends Command {
    public static final String COMMAND_WORD = "currency";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enters the currency creation page";

    public static final String MESSAGE_SUCCESS = "Entered the currency creation screen.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.setPageStatus(
                model.getPageStatus().withNewPageType(PageType.ADD_CURRENCY));

        return new CommandResult(MESSAGE_SUCCESS, true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof EnterCreateCurrencyCommand;
    }
}
