package seedu.savenus.logic.parser;

import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.savenus.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.SortCommand;

public class SortCommandParserTest {
    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyFields_failure() {
        String noFieldsMessage = FieldParser.NO_ARGUMENTS_USAGE;
        String noFieldsUsage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                noFieldsMessage + "\n" + SortCommand.EXAMPLE_USAGE);

        assertParseFailure(parser, "           ", noFieldsUsage);
    }
}
