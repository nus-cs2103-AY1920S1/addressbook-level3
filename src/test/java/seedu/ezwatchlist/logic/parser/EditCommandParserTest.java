package seedu.ezwatchlist.logic.parser;

import static seedu.ezwatchlist.commons.core.messages.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.ezwatchlist.logic.commands.CommandTestUtil.CURRENT_TAB_WATCHED_TAB;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.NAME_DESC_ANNABELLE;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_ACTOR;
import static seedu.ezwatchlist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ezwatchlist.testutil.Assert.assertThrows;
import static seedu.ezwatchlist.testutil.TypicalIndexes.INDEX_FIRST_SHOW;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.logic.commands.EditCommand;
import seedu.ezwatchlist.logic.parser.exceptions.ParseException;

public class EditCommandParserTest {

    private static final String ACTOR_EMPTY = " " + PREFIX_ACTOR;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, NAME_DESC_ANNABELLE, MESSAGE_INVALID_FORMAT, CURRENT_TAB_WATCHED_TAB);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED, CURRENT_TAB_WATCHED_TAB);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT, CURRENT_TAB_WATCHED_TAB);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_ANNABELLE, MESSAGE_INVALID_FORMAT, CURRENT_TAB_WATCHED_TAB);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_ANNABELLE, MESSAGE_INVALID_FORMAT, CURRENT_TAB_WATCHED_TAB);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT, CURRENT_TAB_WATCHED_TAB);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT, CURRENT_TAB_WATCHED_TAB);
    }

    @Test
    public void parseInvalidIndex() {
        assertThrows(ParseException.class, ()-> parser.parse("-1", "1"));
    }

    @Test
    public void parseNoEditField() {
        EditCommand.EditShowDescriptor editShowDescriptor = new EditCommand.EditShowDescriptor();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_SHOW, editShowDescriptor);
        assertThrows(ParseException.class, () -> parser.parse("1", "1"));
    }
}
