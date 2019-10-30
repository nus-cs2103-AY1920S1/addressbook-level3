package seedu.moneygowhere.logic.commands;

import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.currency.Currency;

/**
 * Represents the Exchange Rate command.
 */
public class ExchangeRateCommand extends Command {

    public static final String COMMAND_WORD = "exchangerate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows exchange rates.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) {
        StringBuilder sb = new StringBuilder();
        sb.append("Current exchange rates: ");
        for (Currency currency : model.getSpendingBook().getCurrencies()) {
            sb.append(currency);
            sb.append(", ");
        }
        String trimmedResult = sb.substring(0, sb.lastIndexOf(","));
        return new CommandResult(trimmedResult);
    }
}
