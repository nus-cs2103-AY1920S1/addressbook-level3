package seedu.moneygowhere.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.moneygowhere.commons.core.LogsCenter;
import seedu.moneygowhere.logic.commands.exceptions.CommandException;
import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.currency.Currency;

//@@author Nanosync
/**
 * Views the currently stored exchange rates or converts a specified currency into SGD.
 */
public class ExchangeRateCommand extends Command {

    public static final String COMMAND_WORD = "exchangerate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows exchange rates.\n"
            + "If no amount and currency is specified, all exchange rates are shown.\n"
            + "Example: " + COMMAND_WORD + " [AMOUNT] [CURRENCYCODE]\n"
            + "CURRENCYCODE can be: USD, EUR, IDR, MYR, INR, MMK";

    public static final String MESSAGE_INVALID_AMOUNT = "Amount must be non-negative";
    public static final String MESSAGE_INVALID_CURRENCY = "Specified currency does not exist";
    public static final String MESSAGE_SAME_CURRENCY = "Specified currency SGD will return the same result.";

    private double amount;
    private String currencyCode;

    private final Logger logger = LogsCenter.getLogger(ExchangeRateCommand.class);

    public ExchangeRateCommand() {
        amount = 0;
        currencyCode = "";
    }

    public ExchangeRateCommand(double amount, String currencyCode) {
        requireNonNull(currencyCode);
        this.amount = amount;
        this.currencyCode = currencyCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (amount == 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Current exchange rates: ");
            for (Currency currency : model.getSpendingBook().getCurrencies()) {
                sb.append(currency);
                sb.append(", ");
            }
            String trimmedResult = sb.substring(0, sb.lastIndexOf(","));
            return new CommandResult(trimmedResult);
        }

        Currency targetCurrency = null;
        for (Currency c : model.getCurrencies()) {
            if (c.name.equalsIgnoreCase(currencyCode)) {
                targetCurrency = c;
            }
        }

        if (targetCurrency == null) {
            logger.warning("Target currency " + currencyCode + " is invalid");
            throw new CommandException(MESSAGE_INVALID_CURRENCY);
        }

        String output = String.format("According to our exchange rate, %s%.2f %s = $%.2f SGD",
                targetCurrency.symbol, amount, targetCurrency.name, amount / targetCurrency.rate);

        return new CommandResult(output);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExchangeRateCommand // instanceof handles nulls
                && amount == (((ExchangeRateCommand) other).amount)
                && currencyCode.equalsIgnoreCase(((ExchangeRateCommand) other).currencyCode)); // state check
    }
}
