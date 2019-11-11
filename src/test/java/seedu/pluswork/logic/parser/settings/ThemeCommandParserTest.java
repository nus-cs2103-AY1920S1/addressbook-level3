package seedu.pluswork.logic.parser.settings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.pluswork.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pluswork.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.pluswork.logic.commands.settings.ThemeCommand;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.logic.parser.ParserUtilTest;
import seedu.pluswork.logic.parser.exceptions.ParseException;
import seedu.pluswork.logic.parser.settings.ThemeCommandParser;

/**
 * White-box testing for parsing done here, refer to {@link ParserUtilTest} for varied user
 * input tests.
 */
class ThemeCommandParserTest {
    private ThemeCommandParser themeCommandParser = new ThemeCommandParser();

    // enumerations cannot be equal, hence test for equality of instance of theme command created
    @Test
    void parse_validArgs_returnsThemeCommand() throws ParseException {
        assertEquals(themeCommandParser.parse("dark").getClass(), ThemeCommand.class);
    }

    @Test
    void parse_invalidArgs_throwsCommandException() throws CommandException {
        assertParseFailure(themeCommandParser, "theme lig",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ThemeCommand.MESSAGE_USAGE));
    }
}
