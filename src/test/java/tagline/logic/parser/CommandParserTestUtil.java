package tagline.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import tagline.logic.commands.Command;
import tagline.logic.parser.exceptions.ParseException;
import tagline.logic.parser.exceptions.PromptRequestException;

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
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     */
    public static void assertParseFailure(Parser parser, String userInput, String expectedMessage) {
        try {
            parser.parse(userInput);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and that a
     * {@code PromptRequestException} is thrown.
     */
    public static void assertPromptRequest(Parser parser, String userInput, List<Prompt> prompts) {
        try {
            parser.parse(userInput);
            throw new AssertionError("The expected PromptRequestException was not thrown.");
        } catch (PromptRequestException pre) {
            //unordered check for equality
            assertEquals(pre.getPrompts(), prompts);
        } catch (ParseException pe) {
            throw new AssertionError("The expected PromptRequestException was not thrown.");
        }
    }
}
