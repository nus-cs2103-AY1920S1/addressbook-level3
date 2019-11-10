package seedu.algobase.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_NAME;
import static seedu.algobase.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.algobase.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.algobase.logic.commands.HelpCommand;
import seedu.algobase.logic.commands.problem.AddCommand;
import seedu.algobase.logic.parser.exceptions.ParseException;

class HelpCommandParserTest {
    private static final String INVALID_COMMAND_WORD = "1nval1dC0mmand";
    private static final String VALID_COMMAND_WORD = AddCommand.COMMAND_WORD;
    private static final String EMPTY_INPUT = "";
    private static final Class VALID_COMMAND_CLASS = AddCommand.class;
    private HelpCommandParser helpCommandParser = new HelpCommandParser();

    @Test
    void parse_emptyInput_returnsListAllHelpCommand() throws ParseException {
        HelpCommand helpCommand = helpCommandParser.parse(EMPTY_INPUT);
        assertTrue(helpCommand.isListAllCommands());
    }

    @Test
    void parse_wrongCommandWord_throwsInvalidCommandWordException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_NAME, INVALID_COMMAND_WORD);
        assertParseFailure(helpCommandParser, INVALID_COMMAND_WORD, expectedMessage);
    }

    @Test
    void parse_correctCommandWord_returnsHelpCommandForTheCommand() {
        assertParseSuccess(helpCommandParser, VALID_COMMAND_WORD,
            new HelpCommand(VALID_COMMAND_CLASS, false));
    }
}
