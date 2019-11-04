package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.WHITESPACE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddNusModsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.NusModsShareLink;
import seedu.address.model.person.Name;
import seedu.address.testutil.moduleutil.NusModsShareLinkStrings;

class AddNusModsCommandParserTest {

    private AddNusModsCommandParser parser = new AddNusModsCommandParser();

    @Test
    void parse_success() throws ParseException {
        Name name = ALICE.getName();
        NusModsShareLink link = NusModsShareLink.parseLink(NusModsShareLinkStrings.VALID_LINK_1);

        AddNusModsCommand expectedCommand = new AddNusModsCommand(name, link);

        assertParseSuccess(parser,
                WHITESPACE + PREFIX_NAME + ALICE.getName().toString()
                        + WHITESPACE + PREFIX_LINK + NusModsShareLinkStrings.VALID_LINK_1, expectedCommand);
    }

    @Test
    void parse_failure() {
        assertParseFailure(parser,
                WHITESPACE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNusModsCommand.MESSAGE_USAGE));

    }
}
