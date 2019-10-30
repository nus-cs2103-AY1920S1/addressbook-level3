package seedu.moneygowhere.logic.commands;

import static seedu.moneygowhere.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.ModelManager;
import seedu.moneygowhere.model.currency.Currency;

public class ExchangeRateCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_exchangeRate_success() {
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
}
