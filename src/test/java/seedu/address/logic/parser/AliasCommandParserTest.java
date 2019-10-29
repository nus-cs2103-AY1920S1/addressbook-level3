package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS_ALIAS_INPUT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS_ALIAS_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Alias;
import seedu.address.logic.commands.alias.AliasCommand;

public class AliasCommandParserTest {
    private AliasCommandParser parser = new AliasCommandParser();

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE);

        // missing alias name and input
        assertParseFailure(parser,
                String.format(" %s %s", PREFIX_ALIAS_ALIAS_NAME, PREFIX_ALIAS_ALIAS_INPUT),
                expectedMessage);

        // missing input
        assertParseFailure(parser,
                String.format(" %s aliasName %s", PREFIX_ALIAS_ALIAS_NAME, PREFIX_ALIAS_ALIAS_INPUT),
                expectedMessage);

        // missing alias name
        assertParseFailure(parser,
                String.format(" %s %s input here", PREFIX_ALIAS_ALIAS_NAME, PREFIX_ALIAS_ALIAS_INPUT),
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid aliasname
        assertParseFailure(parser,
                String.format(" %s #asd@## %s input here", PREFIX_ALIAS_ALIAS_NAME, PREFIX_ALIAS_ALIAS_INPUT),
                Alias.MESSAGE_NAME_CONSTRAINTS);
        // multiple words which would be individually valid
        assertParseFailure(parser,
                String.format(" %s multiple words %s input here", PREFIX_ALIAS_ALIAS_NAME, PREFIX_ALIAS_ALIAS_INPUT),
                Alias.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    void parse_allFieldsPresent_success() {
        // valid alias name and input
        assertParseSuccess(parser,
                String.format(" %s name %s input", PREFIX_ALIAS_ALIAS_NAME, PREFIX_ALIAS_ALIAS_INPUT),
                new AliasCommand(new Alias("name", "input")));
        // valid alias name and multiple word input
        assertParseSuccess(parser,
                String.format(" %s name %s input input", PREFIX_ALIAS_ALIAS_NAME, PREFIX_ALIAS_ALIAS_INPUT),
                new AliasCommand(new Alias("name", "input input")));
    }
}
