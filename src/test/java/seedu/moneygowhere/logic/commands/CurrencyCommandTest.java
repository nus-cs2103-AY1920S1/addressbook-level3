package seedu.moneygowhere.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moneygowhere.testutil.TypicalSpendings.getTypicalSpendingBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.ModelManager;
import seedu.moneygowhere.model.UserPrefs;
import seedu.moneygowhere.model.currency.Currency;

class CurrencyCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalSpendingBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getSpendingBook(), new UserPrefs());
    }

    @Test
    public void execute_emptyArg() {
        CurrencyCommand currencyCommand = new CurrencyCommand("");

        String expectedMessage = "The current currency in use is: SGD";

        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(currencyCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_currencyChange_alreadyChanged() {
        CurrencyCommand currencyCommand = new CurrencyCommand("SGD");

        String expectedMessage = "Currency already in SGD";

        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(currencyCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_currencyChange_success() {
        CurrencyCommand currencyCommand = new CurrencyCommand("MYR");

        String expectedMessage = "Currency changed to MYR";
        Currency newCurrency = null;
        for (Currency currency : expectedModel.getCurrencies()) {
            if (currency.name.equalsIgnoreCase("MYR")) {
                newCurrency = currency;
            }
        }
        assertNotNull(newCurrency);
        expectedModel.setCurrencyInUse(newCurrency);

        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(currencyCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_currencyChange_invalidCurrency() {
        CurrencyCommand currencyCommand = new CurrencyCommand("LOL");

        String expectedMessage = "Specified currency does not exist";

        assertCommandFailure(currencyCommand, model, expectedMessage);
    }

    @Test
    public void testEquals() {
        CurrencyCommand currencyCommand = new CurrencyCommand("SGD");

        assertTrue(currencyCommand.equals(currencyCommand));
        assertFalse(currencyCommand == null);

        // ignore case
        assertTrue(currencyCommand.equals(new CurrencyCommand("sgd")));
    }
}
