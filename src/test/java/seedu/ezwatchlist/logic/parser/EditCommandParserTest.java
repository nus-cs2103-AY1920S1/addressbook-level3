package seedu.ezwatchlist.logic.parser;

import static seedu.ezwatchlist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.ezwatchlist.logic.commands.CommandTestUtil.*;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_ACTOR;
import static seedu.ezwatchlist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ezwatchlist.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.ezwatchlist.testutil.TypicalIndexes.INDEX_FIRST_SHOW;
import static seedu.ezwatchlist.testutil.TypicalIndexes.INDEX_SECOND_SHOW;
import static seedu.ezwatchlist.testutil.TypicalIndexes.INDEX_THIRD_SHOW;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.commons.core.index.Index;
import seedu.ezwatchlist.logic.commands.EditCommand;
import seedu.ezwatchlist.logic.commands.EditCommand.EditShowDescriptor;
import seedu.ezwatchlist.model.show.Name;
import seedu.ezwatchlist.model.show.Description;
import seedu.ezwatchlist.model.show.IsWatched;
import seedu.ezwatchlist.model.show.Date;
import seedu.ezwatchlist.model.show.RunningTime;
import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.testutil.EditShowDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_ACTOR;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }


}
