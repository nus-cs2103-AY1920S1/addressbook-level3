package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains helper methods for testing command parsers.
 */
public class CommandParserTestUtil {

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
     */
    public static void assertParseSuccess(Parser parser, String userInput, Command expectedCommand) {
        try {
            Command command = parser.parse(userInput);
            assertEquals(expectedCommand, command);
        } catch (ParseException | CommandException pe) {
            System.out.println(userInput);
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    /**
     * Abstracted Method has a valid index, with spacing concatenated at the front.
     */
    public static void assertParseCorrectIndexSuccess(Parser parser, String userInput, Command expectedCommand) {
        assertParseSuccess(parser, "1 " + userInput, expectedCommand);
    }

    /**
     * Abstracted Method that provides only a valid index as the input.
     */
    public static void assertParseIndexSuccess(Parser parser, Command expectedCommand) {
        assertParseSuccess(parser, "1", expectedCommand);
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     */
    public static void assertParseFailure(Parser parser, String userInput, String expectedMessage) {
        try {
            parser.parse(userInput);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException | CommandException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }

    /**
     * Abstracted Method which has no field specified, only index.
     */
    public static void assertParseCorrectIndexFailure(Parser parser, String userInput, String expectedMessage) {
        assertParseFailure(parser, "1 " + userInput, expectedMessage);
    }

    /**
     * Abstracted Method which has no field specified, only index.
     */
    public static void assertParseNoFieldFailure(Parser parser, String expectedMessage) {
        assertParseFailure(parser, "1", expectedMessage);
    }

    /**
     * Abstracted Method which has field but no index specified.
     */
    public static void assertParseNoIndexFailure(Parser parser, String remainingInput, String expectedMessage) {
        assertParseFailure(parser, remainingInput, expectedMessage);
    }

    /**
     * Abstracted Method which has no index and field, just white space
     */
    public static void assertParseNoIndexAndFieldFailure(Parser parser, String expectedMessage) {
        assertParseFailure(parser, "    ", expectedMessage);
    }

    /**
     * Abstracted Method that includes the index '-1' in the user input.
     */
    public static void assertParseNegativeIndexFailure(Parser parser, String remainingInput, String expectedMessage) {
        assertParseFailure(parser, "-1 " + remainingInput, expectedMessage);
    }

    /**
     * Abstracted Method that has only index '-1' as the user input.
     */
    public static void assertParseNegativeIndexFailure(Parser parser, String expectedMessage) {
        assertParseFailure(parser, "-1", expectedMessage);
    }

    /**
     * Abstracted Method that includes the index '-1' in the user input.
     */
    public static void assertParseZeroIndexFailure(Parser parser, String remainingInput, String expectedMessage) {
        assertParseFailure(parser, "0 " + remainingInput, expectedMessage);
    }

    /**
     * Abstracted Method that includes the index '-1' in the user input.
     */
    public static void assertParseZeroIndexFailure(Parser parser, String expectedMessage) {
        assertParseFailure(parser, "0 ", expectedMessage);
    }

    /**
     * Abstracted Method which includes a non-integer index in the user input.
     */
    public static void assertParseInvalidIndexFailure(Parser parser, String remainingInput, String expectedMessage) {
        assertParseFailure(parser, "a " + remainingInput, expectedMessage);
    }

    /**
     * Abstracted Method which has only a non-integer index in the user input.
     */
    public static void assertParseInvalidIndexFailure(Parser parser, String expectedMessage) {
        assertParseFailure(parser, "a", expectedMessage);
    }

    /**
     * Abstracted Method that includes invalid preambles
     */
    public static void assertParseInvalidPreambleArgsFailure(Parser parser,
         String remainingInput, String expectedMessage) {
        assertParseFailure(parser, "1 some random string" + remainingInput, expectedMessage);
    }

    /**
     * Abstracted Method that has only invalid preambles
     */
    public static void assertParseInvalidPreambleArgsFailure(Parser parser, String expectedMessage) {
        assertParseInvalidPreambleArgsFailure(parser, "", expectedMessage);
    }

    /**
     * Abstracted Method that includes invalid prefixes
     */
    public static void assertParseInvalidPrefixFailure(Parser parser, String remainingInput, String expectedMessage) {
        assertParseFailure(parser, "1 x/ string" + remainingInput, expectedMessage);
    }

}
