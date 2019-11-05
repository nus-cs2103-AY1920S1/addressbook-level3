package seedu.address.itinerary.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.itinerary.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

class DeleteEventCommandParserTest {

    private static final Index INDEX_FIRST_PERSON = Index.fromOneBased(1);
    private static final Index INDEX_LARGE = Index.fromOneBased(10000);

    private DeleteEventCommandParser parser = new DeleteEventCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteEventCommand(INDEX_FIRST_PERSON));

        assertParseSuccess(parser, "10000", new DeleteEventCommand(INDEX_LARGE));
    }


    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Checking for character in index
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteEventCommand.MESSAGE_USAGE));

        // No index given
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteEventCommand.MESSAGE_USAGE));

        // Only single white space character
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteEventCommand.MESSAGE_USAGE));

        // Special symbol used
        assertParseFailure(parser, "$%", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteEventCommand.MESSAGE_USAGE));
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
     */
    public static void assertParseSuccess(Parser<? extends Command> parser, String userInput, Command expectedCommand) {
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
    public static void assertParseFailure(Parser<? extends Command> parser, String userInput, String expectedMessage) {
        try {
            parser.parse(userInput);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }
}
