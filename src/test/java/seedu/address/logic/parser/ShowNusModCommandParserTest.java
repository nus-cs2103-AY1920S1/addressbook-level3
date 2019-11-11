package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.WHITESPACE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ShowNusModCommand;
import seedu.address.model.module.ModuleCode;

class ShowNusModCommandParserTest {

    private ShowNusModCommandParser parser = new ShowNusModCommandParser();

    @Test
    void parse_success() {
        ModuleCode moduleCode = new ModuleCode("CS3230");

        ShowNusModCommand expectedCommand = new ShowNusModCommand(moduleCode);

        assertParseSuccess(parser, WHITESPACE + PREFIX_MODULE_CODE + "CS3230", expectedCommand);
    }

    @Test
    void parse_failure() {
        assertParseFailure(parser,
                WHITESPACE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowNusModCommand.MESSAGE_USAGE));

    }
}
