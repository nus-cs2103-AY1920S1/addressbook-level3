package thrift.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static thrift.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import thrift.model.Model;
import thrift.model.ModelManager;

class ConvertCommandTest {

    private Model model = new ModelManager();

    @Test
    void execute() {
        double amount = 100.00;
        List<String> currencies = List.of("SGD", "MYR");
        ConvertCommand convertCommand = new ConvertCommand(amount, currencies);

        CommandResult expectedCommandResult =
                new CommandResult(ConvertCommand.generateConvertResult(amount, currencies));
        Model expectedModel = model;

        assertCommandSuccess(convertCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    void testEquals() {
        double amountOne = 100.00;
        double amountTwo = 200.00;
        List<String> listOne = List.of("SGD", "MYR");
        List<String> listTwo = List.of("EUR", "USD");
        ConvertCommand firstConvertCommand = new ConvertCommand(amountOne, listOne);
        ConvertCommand secondConvertCommand = new ConvertCommand(amountTwo, listTwo);

        // same object -> returns true
        assertTrue(firstConvertCommand.equals(firstConvertCommand));

        // same values -> returns true
        ConvertCommand firstTagCommandCopy = new ConvertCommand(amountOne, listOne);
        assertTrue(firstConvertCommand.equals(firstTagCommandCopy));

        // different types -> returns false
        assertFalse(firstConvertCommand.equals(1));

        // null -> returns false
        assertFalse(firstConvertCommand.equals(null));

        // different transaction -> returns false
        assertFalse(firstConvertCommand.equals(secondConvertCommand));
    }

}
