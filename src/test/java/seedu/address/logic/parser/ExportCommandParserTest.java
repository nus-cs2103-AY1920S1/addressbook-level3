package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.model.person.Name;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.WHITESPACE;

public class ExportCommandParserTest {
    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parseSuccess() {
        assertParseSuccess(parser, WHITESPACE + PREFIX_NAME + ALICE.getName().toString(),
                new ExportCommand(new Name(ALICE.getName().toString())));
    }

    @Test
    public void parseFailure() {
        assertParseFailure(parser, WHITESPACE, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ExportCommand.MESSAGE_USAGE));
    }
}
