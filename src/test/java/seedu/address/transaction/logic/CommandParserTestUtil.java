package seedu.address.transaction.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import seedu.address.person.model.Model;
import seedu.address.transaction.commands.Command;

public class CommandParserTestUtil {
    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
     */
    public static void assertAddCommandParseSuccess(AddCommandParser parser, String userInput, Command expectedCommand,
                                                    int size, Model personModel) {
        try {
            Command command = parser.parse(userInput, size, personModel);
            assertEquals(expectedCommand, command);
        } catch (Exception pe) {
            System.out.println(pe.toString());
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     */
    public static void assertAddCommandParseFailure(AddCommandParser parser, String userInput, String expectedMessage,
                                                    int size, Model personModel) {
        try {
            parser.parse(userInput, size, personModel);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (Exception pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
     */
    public static void assertCommandParseWithPersonModelSuccess(CommandParserWithPersonModel parser, String userInput,
                                                                Command expectedCommand, Model personModel) {
        try {
            Command command = parser.parse(userInput, personModel);
            assertEquals(expectedCommand, command);
        } catch (Exception pe) {
            System.out.println(pe.toString());
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     */
    public static void assertCommandParseWithPersonModelFailure(CommandParserWithPersonModel parser, String userInput,
                                                                String expectedMessage, Model personModel) {
        try {
            parser.parse(userInput, personModel);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (Exception pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
     */
    public static void assertCommandParseSuccess(IndependentCommandParser parser, String userInput,
                                                       Command expectedCommand) {
        try {
            Command command = parser.parse(userInput);
            assertEquals(expectedCommand, command);
        } catch (Exception pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     */
    public static void assertCommandParseFailure(IndependentCommandParser parser, String userInput,
                                                                String expectedMessage) {
        try {
            parser.parse(userInput);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (Exception pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }
}
