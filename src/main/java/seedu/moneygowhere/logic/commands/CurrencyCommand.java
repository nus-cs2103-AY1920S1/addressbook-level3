package seedu.moneygowhere.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import seedu.moneygowhere.logic.commands.exceptions.CommandException;
import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.currency.Currency;

/**
 * Represents the Currency command.
 */
public class CurrencyCommand extends Command {

    public static final String COMMAND_WORD = "currency";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the currency used in the application.\n"
            + "Example: " + COMMAND_WORD + " sgd";

    private String currency;

    public CurrencyCommand(String currency) {
        requireNonNull(currency);
        this.currency = currency;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (currency.isEmpty()) {
            return new CommandResult("The current currency in use is: "
                    + model.getCurrencyInUse().name);
        }

        List<Currency> currencies = model.getCurrencies()
                .stream()
                .filter(c -> c.name.equalsIgnoreCase(currency))
                .collect(Collectors.toList());

        if (currencies.isEmpty()) {
            throw new CommandException("Specified currency does not exist");
        }

        Currency newCurrency = currencies.get(0);

        if (newCurrency.isSameCurrency(model.getCurrencyInUse())) {
            return new CommandResult("Currency already in " + newCurrency.name);
        }

        model.setCurrencyInUse(newCurrency);

        return new CommandResult("Currency changed to " + newCurrency.name);
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
