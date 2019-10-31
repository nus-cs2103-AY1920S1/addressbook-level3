package seedu.moneygowhere.logic.commands;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.ModelManager;
import seedu.moneygowhere.model.currency.Currency;

public class ExchangeRateCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_exchangeRateNoArgs_success() {
        StringBuilder sb = new StringBuilder();
        sb.append("Current exchange rates: ");
        for (Currency currency : model.getSpendingBook().getCurrencies()) {
            sb.append(currency);
            sb.append(", ");
        }
        String trimmedResult = sb.substring(0, sb.lastIndexOf(","));

        CommandResult expectedCommandResult = new CommandResult(trimmedResult);
        assertCommandSuccess(new ExchangeRateCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_exchangeRateWithArgs_success() {
        Currency usdCurrency = null;
        for (Currency currency : model.getSpendingBook().getCurrencies()) {
            if (currency.name.equalsIgnoreCase("USD")) {
                usdCurrency = currency;
            }
        }

        assertNotNull(usdCurrency);

        double amount = 5.00;
        String output = String.format("According to our exchange rate, %s%.2f %s = $%.2f SGD",
                usdCurrency.symbol, amount, usdCurrency.name, amount / usdCurrency.rate);

        CommandResult expectedCommandResult = new CommandResult(output);
        assertCommandSuccess(new ExchangeRateCommand(amount, usdCurrency.name), model,
                expectedCommandResult, expectedModel);
    }


    @Test
    public void execute_exchangeRate_fail() {
        assertCommandFailure(new ExchangeRateCommand(1.0, "LOL"), model,
                ExchangeRateCommand.MESSAGE_INVALID_CURRENCY);
    }
}
