package seedu.address.logic.commands.arguments;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.ArgumentException;

class CommandArgumentTest {

    @Test
    void getValue_optionalArgument_success() {
        assertDoesNotThrow(() -> new CommandArgumentMock().build(false));
    }

    @Test
    void getValue_setValueOptionalArgument_success() {
        String[] tests = { "", " ", "a" };
        for (String test : tests) {
            assertDoesNotThrow(() -> {
                Argument argument = new CommandArgumentMock();
                argument.accept(test);
                argument.build(false);
            });
        }
    }

    @Test
    void getValue_requiredArgument_failure() {
        assertThrows(ArgumentException.class, () -> new CommandArgumentMock().build(true));
    }

    @Test
    void getValue_setValueRequiredArgument_success() {
        String[] tests = { "", " ", "a" };
        for (String test : tests) {
            assertDoesNotThrow(() -> {
                Argument argument = new CommandArgumentMock();
                argument.accept(test);
                argument.build(true);
            });
        }
    }

    private static class CommandArgumentMock extends Argument<Object> {

        CommandArgumentMock() {
            super(new CommandArgumentBuilderStub(""));
        }

        @Override
        Object parse(String userInput) {
            return "";
        }
    }

    private static class CommandArgumentBuilderStub extends ArgumentBuilder<Object> {

        CommandArgumentBuilderStub(String description) {
            super(description);
        }

        @Override
        Argument<Object> argumentBuild() {
            return new CommandArgumentMock();
        }
    }
}
