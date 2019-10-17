package seedu.ezwatchlist.logic.parser;

import static seedu.ezwatchlist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ezwatchlist.commons.core.Messages.MESSAGE_INVALID_SHOW_TYPE;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.*;
import static seedu.ezwatchlist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ezwatchlist.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.ezwatchlist.testutil.TypicalShows.*;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.logic.commands.AddCommand;
import seedu.ezwatchlist.model.show.Description;
import seedu.ezwatchlist.model.show.IsWatched;
import seedu.ezwatchlist.model.show.RunningTime;
import seedu.ezwatchlist.model.show.Show;
import seedu.ezwatchlist.testutil.ShowBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Show expectedShow = new ShowBuilder(BOBTHEBUILDER).withActors(VALID_ACTOR_BOB_THE_BUILDER).build();

        // whitespace only preamble
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE
                        + NAME_DESC_BOB_THE_BUILDER + TYPE_DESC_BOB_THE_BUILDER
                        + DESCRIPTION_DESC_BOB_THE_BUILDER + WATCHED_DESC_ANNABELLE
                        + DATE_DESC_BOB_THE_BUILDER + RUNNING_TIME_DESC_BOB_THE_BUILDER
                        + ACTOR_DESC_BOB_THE_BUILDER, new AddCommand(expectedShow));

        // multiple names - last name accepted
        assertParseSuccess(parser,
                        NAME_DESC_ANNABELLE
                        + NAME_DESC_BOB_THE_BUILDER + TYPE_DESC_BOB_THE_BUILDER
                        + DESCRIPTION_DESC_BOB_THE_BUILDER + WATCHED_DESC_BOB_THE_BUILDER
                        + DATE_DESC_BOB_THE_BUILDER + RUNNING_TIME_DESC_BOB_THE_BUILDER
                        + ACTOR_DESC_BOB_THE_BUILDER, new AddCommand(expectedShow));

        // multiple types - last type accepted
        assertParseSuccess(parser,
                NAME_DESC_BOB_THE_BUILDER + TYPE_DESC_ANNABELLE + TYPE_DESC_BOB_THE_BUILDER
                        + DESCRIPTION_DESC_BOB_THE_BUILDER + WATCHED_DESC_BOB_THE_BUILDER
                        + DATE_DESC_BOB_THE_BUILDER + RUNNING_TIME_DESC_BOB_THE_BUILDER
                        + ACTOR_DESC_BOB_THE_BUILDER, new AddCommand(expectedShow));

        // multiple description - last description accepted
        assertParseSuccess(parser,
                NAME_DESC_BOB_THE_BUILDER + TYPE_DESC_BOB_THE_BUILDER
                        + DESCRIPTION_DESC_ANNABELLE
                        + DESCRIPTION_DESC_BOB_THE_BUILDER + WATCHED_DESC_BOB_THE_BUILDER
                        + DATE_DESC_BOB_THE_BUILDER + RUNNING_TIME_DESC_BOB_THE_BUILDER
                        + ACTOR_DESC_BOB_THE_BUILDER, new AddCommand(expectedShow));

        // multiple IsWatched - last watched status accepted
        assertParseSuccess(parser,
                NAME_DESC_BOB_THE_BUILDER + TYPE_DESC_BOB_THE_BUILDER
                        + DESCRIPTION_DESC_BOB_THE_BUILDER + WATCHED_DESC_ANNABELLE + WATCHED_DESC_BOB_THE_BUILDER
                        + DATE_DESC_BOB_THE_BUILDER + RUNNING_TIME_DESC_BOB_THE_BUILDER
                        + ACTOR_DESC_BOB_THE_BUILDER, new AddCommand(expectedShow));

        // multiple date of release - last date accepted
        assertParseSuccess(parser,
                NAME_DESC_BOB_THE_BUILDER + TYPE_DESC_BOB_THE_BUILDER
                        + DESCRIPTION_DESC_BOB_THE_BUILDER + WATCHED_DESC_BOB_THE_BUILDER
                        + DATE_DESC_ANNABELLE
                        + DATE_DESC_BOB_THE_BUILDER + RUNNING_TIME_DESC_BOB_THE_BUILDER
                        + ACTOR_DESC_BOB_THE_BUILDER, new AddCommand(expectedShow));

        // multiple actors - all accepted
        Show expectedShowMultipleTags = new ShowBuilder(FIGHTCLUB).withActors(VALID_ACTOR_ANNABELLE, VALID_ACTOR_BOB_THE_BUILDER)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB_THE_BUILDER + TYPE_DESC_BOB_THE_BUILDER
                        + DESCRIPTION_DESC_BOB_THE_BUILDER + WATCHED_DESC_BOB_THE_BUILDER
                        + DATE_DESC_BOB_THE_BUILDER + RUNNING_TIME_DESC_BOB_THE_BUILDER
                        + ACTOR_DESC_ANNABELLE + ACTOR_DESC_BOB_THE_BUILDER,
                new AddCommand(expectedShowMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Show expectedShow = new ShowBuilder(ANNABELLE).withActors(VALID_ACTOR_ANNABELLE).build();
        assertParseSuccess(parser,
                NAME_DESC_ANNABELLE + TYPE_DESC_ANNABELLE + DESCRIPTION_DESC_ANNABELLE
                        + WATCHED_DESC_ANNABELLE + DATE_DESC_ANNABELLE + RUNNING_TIME_DESC_ANNABELLE
                        + ACTOR_DESC_ANNABELLE,
                new AddCommand(expectedShow));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser,
                VALID_NAME_BOB_THE_BUILDER + TYPE_DESC_BOB_THE_BUILDER
                        + DATE_DESC_BOB_THE_BUILDER + RUNNING_TIME_DESC_BOB_THE_BUILDER
                        + WATCHED_DESC_BOB_THE_BUILDER + DESCRIPTION_DESC_BOB_THE_BUILDER,
                        expectedMessage);

        // missing type prefix
        assertParseFailure(parser,
                NAME_DESC_BOB_THE_BUILDER + VALID_TYPE_BOB_THE_BUILDER
                        + DATE_DESC_BOB_THE_BUILDER + RUNNING_TIME_DESC_BOB_THE_BUILDER
                        + WATCHED_DESC_BOB_THE_BUILDER + DESCRIPTION_DESC_BOB_THE_BUILDER,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser,
                NAME_DESC_BOB_THE_BUILDER + TYPE_DESC_BOB_THE_BUILDER
                        + VALID_DATE_BOB_THE_BUILDER + RUNNING_TIME_DESC_BOB_THE_BUILDER
                        + WATCHED_DESC_BOB_THE_BUILDER + DESCRIPTION_DESC_BOB_THE_BUILDER,
                expectedMessage);

        // missing running time prefix
        assertParseFailure(parser,
                NAME_DESC_BOB_THE_BUILDER + TYPE_DESC_BOB_THE_BUILDER
                        + DATE_DESC_BOB_THE_BUILDER + VALID_RUNNING_TIME_BOB_THE_BUILDER
                        + WATCHED_DESC_BOB_THE_BUILDER + DESCRIPTION_DESC_BOB_THE_BUILDER,
                expectedMessage);

        // missing watched prefix
        assertParseFailure(parser,
                NAME_DESC_BOB_THE_BUILDER + TYPE_DESC_BOB_THE_BUILDER
                        + DATE_DESC_BOB_THE_BUILDER + VALID_RUNNING_TIME_BOB_THE_BUILDER
                        + VALID_WATCHED_BOB_THE_BUILDER + VALID_DESCRIPTION_BOB_THE_BUILDER,
                expectedMessage);

        // missing description prefix
        assertParseFailure(parser,
                NAME_DESC_BOB_THE_BUILDER + TYPE_DESC_BOB_THE_BUILDER
                        + DATE_DESC_BOB_THE_BUILDER + RUNNING_TIME_DESC_BOB_THE_BUILDER
                        + WATCHED_DESC_BOB_THE_BUILDER + DESCRIPTION_DESC_BOB_THE_BUILDER,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser,
                VALID_NAME_BOB_THE_BUILDER + VALID_TYPE_BOB_THE_BUILDER
                        + VALID_DATE_BOB_THE_BUILDER + VALID_RUNNING_TIME_BOB_THE_BUILDER
                        + VALID_WATCHED_BOB_THE_BUILDER + VALID_DESCRIPTION_BOB_THE_BUILDER,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid type
        assertParseFailure(parser,
                NAME_DESC_BOB_THE_BUILDER + INVALID_TYPE_DESC
                        + DATE_DESC_BOB_THE_BUILDER + RUNNING_TIME_DESC_BOB_THE_BUILDER
                        + WATCHED_DESC_BOB_THE_BUILDER + DESCRIPTION_DESC_BOB_THE_BUILDER
                        + ACTOR_DESC_BOB_THE_BUILDER,
                MESSAGE_INVALID_SHOW_TYPE);

        // invalid isWatched
        assertParseFailure(parser,
                NAME_DESC_BOB_THE_BUILDER + TYPE_DESC_BOB_THE_BUILDER
                        + DATE_DESC_BOB_THE_BUILDER + RUNNING_TIME_DESC_BOB_THE_BUILDER
                        + INVALID_IS_WATCHED_DESC + DESCRIPTION_DESC_BOB_THE_BUILDER
                        + ACTOR_DESC_BOB_THE_BUILDER,
                IsWatched.MESSAGE_CONSTRAINTS);

        // invalid running time
        assertParseFailure(parser,
                NAME_DESC_BOB_THE_BUILDER + TYPE_DESC_BOB_THE_BUILDER
                        + DATE_DESC_BOB_THE_BUILDER + INVALID_RUNNING_TIME_DESC
                        + WATCHED_DESC_BOB_THE_BUILDER + DESCRIPTION_DESC_BOB_THE_BUILDER
                        + ACTOR_DESC_BOB_THE_BUILDER,
                RunningTime.MESSAGE_CONSTRAINTS);

        // invalid description
        assertParseFailure(parser,
                NAME_DESC_BOB_THE_BUILDER + TYPE_DESC_BOB_THE_BUILDER
                        + DATE_DESC_BOB_THE_BUILDER + RUNNING_TIME_DESC_BOB_THE_BUILDER
                        + WATCHED_DESC_BOB_THE_BUILDER + INVALID_DESCRIPTION_DESC
                        + ACTOR_DESC_BOB_THE_BUILDER,
                Description.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser,
                NAME_DESC_BOB_THE_BUILDER + INVALID_TYPE_DESC
                        + DATE_DESC_BOB_THE_BUILDER + RUNNING_TIME_DESC_BOB_THE_BUILDER
                        + WATCHED_DESC_BOB_THE_BUILDER + INVALID_DESCRIPTION_DESC
                        + ACTOR_DESC_BOB_THE_BUILDER,
                MESSAGE_INVALID_SHOW_TYPE);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY
                        + NAME_DESC_BOB_THE_BUILDER + INVALID_TYPE_DESC
                        + DATE_DESC_BOB_THE_BUILDER + RUNNING_TIME_DESC_BOB_THE_BUILDER
                        + WATCHED_DESC_BOB_THE_BUILDER + INVALID_DESCRIPTION_DESC
                        + ACTOR_DESC_BOB_THE_BUILDER,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
