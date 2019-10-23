package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains helper methods for testing command allocators.
 */
public class CommandAllocatorTestUtil {

    /**
     * Asserts that the allocation of {@code userInput} by the {@code CommandAllocator} is successful and the
     * right parser is called and the command created equals to {@code expectedCommand}
     */
    public static void assertAllocatorSuccess(CommandAllocator commandAllocator, String userInput,
                                              Command expectedCommand) {
        try {
            Command command = commandAllocator.allocate(userInput);
            assertEquals(expectedCommand, command);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    /**
     * Asserts that the allocation of {@code userInput} by the {@code CommandAllocator} is unsuccessful
     * and the error message equals to {@code expectedMessage}.
     */
    public static void assertAllocatorFailure(CommandAllocator commandAllocator, String userInput,
                                              String expectedMessage) {
        try {
            commandAllocator.allocate(userInput);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }

}
