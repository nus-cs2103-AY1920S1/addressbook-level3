package seedu.address.logic.parser.addcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ALLOW_DESC_EVERYDAY;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_FRIDAY;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_MONDAY;
import static seedu.address.logic.commands.CommandTestUtil.INDEX_DESC_MONDAY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VENUE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_EVERYDAY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_MONDAY;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_FRIDAY;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_MONDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLOW_EVERYDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_MONDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DISALLOW_EVERYDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INDEX_MONDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_EVERYDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MONDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_MONDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_MONDAY;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_FRIDAY;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_MONDAY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalSchedules.MONDAY_SCHEDULE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.addcommand.AddScheduleCommand;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.Venue;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ScheduleBuilder;

public class AddScheduleCommandParserTest {

    private AddScheduleCommandParser parser = new AddScheduleCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        Schedule expectedSchedule = new ScheduleBuilder(MONDAY_SCHEDULE).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + INDEX_DESC_MONDAY + DATE_DESC_MONDAY
                + TIME_DESC_MONDAY + VENUE_DESC_MONDAY + TAG_DESC_MONDAY + ALLOW_DESC_EVERYDAY,
                new AddScheduleCommand(expectedSchedule, VALID_INDEX_MONDAY, VALID_ALLOW_EVERYDAY));

        // multiple dates - last date accepted
        assertParseSuccess(parser, INDEX_DESC_MONDAY + DATE_DESC_FRIDAY + DATE_DESC_MONDAY + TIME_DESC_MONDAY
                + VENUE_DESC_MONDAY + TAG_DESC_MONDAY + ALLOW_DESC_EVERYDAY,
                new AddScheduleCommand(expectedSchedule, VALID_INDEX_MONDAY, VALID_ALLOW_EVERYDAY));

        // multiple time - last time accepted
        assertParseSuccess(parser, INDEX_DESC_MONDAY + DATE_DESC_MONDAY + TIME_DESC_FRIDAY + TIME_DESC_MONDAY
                + VENUE_DESC_MONDAY + TAG_DESC_MONDAY + ALLOW_DESC_EVERYDAY,
                new AddScheduleCommand(expectedSchedule, VALID_INDEX_MONDAY, VALID_ALLOW_EVERYDAY));

        // multiple venues - last venue accepted
        assertParseSuccess(parser, INDEX_DESC_MONDAY + DATE_DESC_MONDAY + TIME_DESC_MONDAY + VENUE_DESC_FRIDAY
                + VENUE_DESC_MONDAY + TAG_DESC_MONDAY + ALLOW_DESC_EVERYDAY,
                new AddScheduleCommand(expectedSchedule, VALID_INDEX_MONDAY, VALID_ALLOW_EVERYDAY));

        // multiple tags - all accepted
        Schedule expectedScheduleMultipleTags = new ScheduleBuilder(MONDAY_SCHEDULE)
                .withTags(VALID_TAG_MONDAY, VALID_TAG_EVERYDAY).build();
        assertParseSuccess(parser, INDEX_DESC_MONDAY + DATE_DESC_MONDAY + TIME_DESC_MONDAY
                + VENUE_DESC_MONDAY + TAG_DESC_MONDAY + TAG_DESC_EVERYDAY + ALLOW_DESC_EVERYDAY,
                new AddScheduleCommand(expectedScheduleMultipleTags, VALID_INDEX_MONDAY, VALID_ALLOW_EVERYDAY));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Schedule expectedSchedule = new ScheduleBuilder(MONDAY_SCHEDULE).withTags().build();
        assertParseSuccess(parser, INDEX_DESC_MONDAY + DATE_DESC_MONDAY + TIME_DESC_MONDAY + VENUE_DESC_MONDAY
                + ALLOW_DESC_EVERYDAY,
                new AddScheduleCommand(expectedSchedule, VALID_INDEX_MONDAY, VALID_ALLOW_EVERYDAY));

        // no -allow
        expectedSchedule = new ScheduleBuilder(MONDAY_SCHEDULE).build();
        assertParseSuccess(parser, INDEX_DESC_MONDAY + DATE_DESC_MONDAY + TIME_DESC_MONDAY + VENUE_DESC_MONDAY
                        + TAG_DESC_MONDAY,
                new AddScheduleCommand(expectedSchedule, VALID_INDEX_MONDAY, VALID_DISALLOW_EVERYDAY));

        // no tags and -allow
        expectedSchedule = new ScheduleBuilder(MONDAY_SCHEDULE).withTags().build();
        assertParseSuccess(parser, INDEX_DESC_MONDAY + DATE_DESC_MONDAY + TIME_DESC_MONDAY + VENUE_DESC_MONDAY,
                new AddScheduleCommand(expectedSchedule, VALID_INDEX_MONDAY, VALID_DISALLOW_EVERYDAY));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddScheduleCommand.MESSAGE_USAGE);

        // missing date prefix
        assertParseFailure(parser, INDEX_DESC_MONDAY + VALID_DATE_MONDAY + TIME_DESC_MONDAY + VENUE_DESC_MONDAY,
                expectedMessage);

        // missing time prefix
        assertParseFailure(parser, INDEX_DESC_MONDAY + DATE_DESC_MONDAY + VALID_TIME_MONDAY + VENUE_DESC_MONDAY,
                expectedMessage);

        // missing venue prefix
        assertParseFailure(parser, INDEX_DESC_MONDAY + DATE_DESC_MONDAY + TIME_DESC_MONDAY + VALID_VENUE_MONDAY,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, INDEX_DESC_MONDAY + VALID_DATE_MONDAY + VALID_TIME_MONDAY + VALID_VENUE_MONDAY,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid index INVALID_INDEX_DESC
        assertParseFailure(parser, INVALID_DATE_DESC + DATE_DESC_MONDAY + TIME_DESC_MONDAY
                + VENUE_DESC_MONDAY + TAG_DESC_MONDAY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddScheduleCommand.MESSAGE_USAGE));

        // invalid date
        assertParseFailure(parser, INDEX_DESC_MONDAY + INVALID_DATE_DESC + TIME_DESC_MONDAY
                + VENUE_DESC_MONDAY + TAG_DESC_MONDAY, Messages.DATE_MESSAGE_CONSTRAINTS);

        // invalid time
        assertParseFailure(parser, INDEX_DESC_MONDAY + DATE_DESC_MONDAY + INVALID_TIME_DESC
                + VENUE_DESC_MONDAY + TAG_DESC_MONDAY, Messages.TIME_MESSAGE_CONSTRAINTS);

        // invalid venue
        assertParseFailure(parser, INDEX_DESC_MONDAY + DATE_DESC_MONDAY + TIME_DESC_MONDAY
                + INVALID_VENUE_DESC + TAG_DESC_MONDAY, Venue.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, INDEX_DESC_MONDAY + DATE_DESC_MONDAY + TIME_DESC_MONDAY
                + VENUE_DESC_MONDAY + INVALID_TAG_DESC + TAG_DESC_MONDAY, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INDEX_DESC_MONDAY + INVALID_DATE_DESC + TIME_DESC_MONDAY
                        + INVALID_VENUE_DESC ,
                Messages.DATE_MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + INDEX_DESC_MONDAY + INVALID_DATE_DESC + TIME_DESC_MONDAY
                        + VENUE_DESC_MONDAY + TAG_DESC_MONDAY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddScheduleCommand.MESSAGE_USAGE));
    }


}
