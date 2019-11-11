package seedu.address.cashier.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import seedu.address.cashier.logic.commands.Command;

/**
 * Contains helper methods for testing command parsers.
 */
public class CommandParserTestUtil {

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
     */
    public static void assertCommandParserSuccess(Parser parser, String userInput,
                                                  Command expectedCommand, seedu.address.cashier.model.Model model,
                                                  seedu.address.person.model.CheckAndGetPersonByNameModel personModel) {
        try {
            Command command = parser.parse(userInput, model, personModel);
            assertEquals(expectedCommand, command);
        } catch (Exception pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     */
    public static void assertCommandParserFailure(Parser parser, String userInput,
                                                  String expectedMessage, seedu.address.cashier.model.Model model,
                                                  seedu.address.person.model.CheckAndGetPersonByNameModel personModel) {
        try {
            parser.parse(userInput, model, personModel);
            throw new AssertionError("The expected exception was not thrown.");
        } catch (Exception e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

}
