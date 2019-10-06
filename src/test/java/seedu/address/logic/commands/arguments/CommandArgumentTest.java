package seedu.address.logic.commands.arguments;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.ArgumentException;

class CommandArgumentTest {

    @Test
    void getValue_optionalArgument_success() {
        assertDoesNotThrow(() -> new CommandArgumentMock(false).getValue());
    }

    @Test
    void getValue_setValueOptionalArgument_success() {
        String[] tests = { "", " ", "a" };
        for (String test : tests) {
            assertDoesNotThrow(() -> {
                CommandArgument argument = new CommandArgumentMock(false);
                argument.setValue(test);
                argument.getValue();
            });
        }
    }

    @Test
    void getValue_requiredArgument_failure() {
        assertThrows(ArgumentException.class, () -> new CommandArgumentMock(true).getValue());
    }

    @Test
    void getValue_setValueRequiredArgument_success() {
        String[] tests = { "", " ", "a" };
        for (String test : tests) {
            assertDoesNotThrow(() -> {
                CommandArgument argument = new CommandArgumentMock(true);
                argument.setValue(test);
                argument.getValue();
            });
        }
    }

    private static class CommandArgumentMock extends CommandArgument {

        CommandArgumentMock(boolean required) {
            super("", required);
        }

        @Override
        Object parse(String userInput) {
            return "";
        }
    }
}
