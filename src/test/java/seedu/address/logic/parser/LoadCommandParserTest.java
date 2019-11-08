package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FILE_NO_PREFIX_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FILE_WITH_PREFIX_JOHN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.LoadCommand;


public class LoadCommandParserTest {
    private LoadCommandParser parser = new LoadCommandParser();

    @Test
    public void parse_allFieldsSpecified_success() {
        Path path = Paths.get("data", VALID_FILE_NO_PREFIX_JOHN);
        LoadCommand expectedCommand = new LoadCommand(path);

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_FILE_WITH_PREFIX_JOHN, expectedCommand);
    }

    @Test
    public void parse_noFieldsSpecified_success() {
        LoadCommand expectedCommand = new LoadCommand();

        assertParseSuccess(parser, "", expectedCommand);
    }

    @Test
    public void parse_invalidFieldsSpecified_success() {
        // invalid user specified
        assertParseFailure(parser, PREFIX_USER + " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoadCommand.MESSAGE_USAGE));
    }
}
