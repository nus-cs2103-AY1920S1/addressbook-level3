package seedu.moneygowhere.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import seedu.moneygowhere.logic.commands.exceptions.CommandException;
import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.currency.Currency;

//@@author Nanosync
/**
 * Views or changes the currency in use.
 */
public class CurrencyCommand extends Command {

    public static final String COMMAND_WORD = "currency";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the currency used in the application.\n"
            + "Example: " + COMMAND_WORD + " sgd";

    public static final String MESSAGE_CURRENCY_DISPLAY_IN_USE = "The current currency in use is: %s";
    public static final String MESSAGE_CURRENCY_NONEXISTENT = "Specified currency does not exist.";
    public static final String MESSAGE_CURRENCY_ALREADY_CHANGED = "Currency already in: %s";
    public static final String MESSAGE_CURRENCY_CHANGE_SUCCESS = "Currency changed to: %s";

    private String currency;

    public CurrencyCommand(String currency) {
        requireNonNull(currency);
        this.currency = currency;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (currency.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_CURRENCY_DISPLAY_IN_USE, model.getCurrencyInUse().name));
        }

        List<Currency> currencies = model.getCurrencies()
                .stream()
                .filter(c -> c.name.equalsIgnoreCase(currency))
                .collect(Collectors.toList());

        if (currencies.isEmpty()) {
            throw new CommandException(MESSAGE_CURRENCY_NONEXISTENT);
        }

        Currency newCurrency = currencies.get(0);

        if (newCurrency.isSameCurrency(model.getCurrencyInUse())) {
            return new CommandResult(String.format(MESSAGE_CURRENCY_ALREADY_CHANGED, newCurrency.name));
        }

        model.setCurrencyInUse(newCurrency);

        return new CommandResult(String.format(MESSAGE_CURRENCY_CHANGE_SUCCESS, newCurrency.name));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CurrencyCommand)) {
            return false;
        }

        // state check
        CurrencyCommand c = (CurrencyCommand) other;
        return currency.equalsIgnoreCase(c.currency);
    }
}
