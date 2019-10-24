package seedu.address.cashier.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import seedu.address.cashier.logic.commands.Command;
import seedu.address.cashier.logic.parser.Parser;
import seedu.address.person.model.Model;


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
                                                  Model personModel) {
        try {
            Command command = parser.parse(userInput, model, personModel);
            System.out.println("expected: " + expectedCommand);
            System.out.println("actual: " + command);
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
                                                  Model personModel) {
        try {
            parser.parse(userInput, model, personModel);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (Exception e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

}
