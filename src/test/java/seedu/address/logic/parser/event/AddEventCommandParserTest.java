package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.END_DATE_DESC_MUSICAL;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_DESC_MUSICAL;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MANPOWER_NEEDED_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VENUE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MANPOWER_NEEDED_DESC_MUSICAL;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.START_DATE_DESC_MUSICAL;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_MUSIC;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_PARTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_1_MUSICAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_2_MUSICAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_MUSICAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_PARTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANPOWER_COUNT_MUSICAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MUSICAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PARTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_MUSICAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_PARTY;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_MUSICAL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalEvents.MUSICAL;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.event.AddEventCommand;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventManpowerNeeded;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventVenue;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EventBuilder;

class AddEventCommandParserTest {
    private AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    void parse_allFieldsPresent_success() {
        Event expectedEvent = new EventBuilder(MUSICAL).withTags(VALID_TAG_MUSICAL).build();

        //whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EVENT_NAME_DESC_MUSICAL
                + VENUE_DESC_MUSICAL + MANPOWER_NEEDED_DESC_MUSICAL
                + START_DATE_DESC_MUSICAL + END_DATE_DESC_MUSICAL + TAG_DESC_MUSIC, new AddEventCommand(expectedEvent));

        //multiple tags - all accepted
        Event expectedEventMultipleTags = new EventBuilder(MUSICAL)
                .withTags(VALID_TAG_MUSICAL, VALID_TAG_PARTY).build();
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EVENT_NAME_DESC_MUSICAL
                + VENUE_DESC_MUSICAL + MANPOWER_NEEDED_DESC_MUSICAL + START_DATE_DESC_MUSICAL
                + END_DATE_DESC_MUSICAL + TAG_DESC_MUSIC
                + TAG_DESC_PARTY, new AddEventCommand(expectedEventMultipleTags));
    }

    @Test
    void parse_optionalFieldsMissing_success() {
        // zero tags
        Event expectedEvent = new EventBuilder(MUSICAL).withTags().build();

        assertParseSuccess(parser, EVENT_NAME_DESC_MUSICAL + VENUE_DESC_MUSICAL
                + MANPOWER_NEEDED_DESC_MUSICAL + START_DATE_DESC_MUSICAL
                + END_DATE_DESC_MUSICAL, new AddEventCommand(expectedEvent));
    }

    @Test
    void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_EVENT_NAME_MUSICAL + VENUE_DESC_MUSICAL
                + MANPOWER_NEEDED_DESC_MUSICAL + START_DATE_DESC_MUSICAL + END_DATE_DESC_MUSICAL
                + TAG_DESC_PARTY + TAG_DESC_MUSIC, expectedMessage);

        // missing venue prefix
        assertParseFailure(parser, EVENT_NAME_DESC_MUSICAL
                + VALID_VENUE_PARTY + MANPOWER_NEEDED_DESC_MUSICAL
                + START_DATE_DESC_MUSICAL + END_DATE_DESC_MUSICAL
                + TAG_DESC_PARTY + TAG_DESC_MUSIC, expectedMessage);

        // missing manpower needed prefix
        assertParseFailure(parser, EVENT_NAME_DESC_MUSICAL + VENUE_DESC_MUSICAL + VALID_MANPOWER_COUNT_MUSICAL
                + START_DATE_DESC_MUSICAL + END_DATE_DESC_MUSICAL + TAG_DESC_PARTY + TAG_DESC_MUSIC, expectedMessage);

        // missing start date prefix
        assertParseFailure(parser, EVENT_NAME_DESC_MUSICAL + VENUE_DESC_MUSICAL + MANPOWER_NEEDED_DESC_MUSICAL
                + VALID_DATE_1_MUSICAL + END_DATE_DESC_MUSICAL + TAG_DESC_PARTY + TAG_DESC_MUSIC, expectedMessage);

        // missing end date prefix
        assertParseFailure(parser, EVENT_NAME_DESC_MUSICAL + VENUE_DESC_MUSICAL + MANPOWER_NEEDED_DESC_MUSICAL
                + START_DATE_DESC_MUSICAL + VALID_DATE_1_MUSICAL + TAG_DESC_PARTY + TAG_DESC_MUSIC, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_EVENT_NAME_PARTY + VALID_VENUE_MUSICAL + VALID_MANPOWER_COUNT_MUSICAL
                + VALID_DATE_1_MUSICAL + VALID_DATE_2_MUSICAL + VALID_TAG_MUSICAL, expectedMessage);
    }

    @Test
    void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_EVENT_NAME_DESC + VENUE_DESC_MUSICAL
                + MANPOWER_NEEDED_DESC_MUSICAL + START_DATE_DESC_MUSICAL + END_DATE_DESC_MUSICAL
                + TAG_DESC_PARTY + TAG_DESC_MUSIC, EventName.MESSAGE_CONSTRAINTS);

        // invalid venue
        assertParseFailure(parser, EVENT_NAME_DESC_MUSICAL + INVALID_VENUE_DESC
                + MANPOWER_NEEDED_DESC_MUSICAL + START_DATE_DESC_MUSICAL + END_DATE_DESC_MUSICAL
                + TAG_DESC_PARTY + TAG_DESC_MUSIC, EventVenue.MESSAGE_CONSTRAINTS);

        //invalid manpower needed
        assertParseFailure(parser, EVENT_NAME_DESC_MUSICAL + VENUE_DESC_MUSICAL
                + INVALID_MANPOWER_NEEDED_DESC + START_DATE_DESC_MUSICAL + END_DATE_DESC_MUSICAL
                + TAG_DESC_PARTY + TAG_DESC_MUSIC, EventManpowerNeeded.MESSAGE_CONSTRAINTS);

        //invalid start date
        assertParseFailure(parser, EVENT_NAME_DESC_MUSICAL + VENUE_DESC_MUSICAL
                + MANPOWER_NEEDED_DESC_MUSICAL + INVALID_START_DATE_DESC + END_DATE_DESC_MUSICAL
                + TAG_DESC_PARTY + TAG_DESC_MUSIC,
                String.format(EventDate.MESSAGE_CONSTRAINTS, "2019/10/29"));

        //invalid end date
        assertParseFailure(parser, EVENT_NAME_DESC_MUSICAL + VENUE_DESC_MUSICAL
                + MANPOWER_NEEDED_DESC_MUSICAL + START_DATE_DESC_MUSICAL + INVALID_END_DATE_DESC
                + TAG_DESC_PARTY + TAG_DESC_MUSIC,
                String.format(EventDate.MESSAGE_CONSTRAINTS, "20 Aug 2019"));

        //invalid tag
        assertParseFailure(parser, EVENT_NAME_DESC_MUSICAL + VENUE_DESC_MUSICAL
                + MANPOWER_NEEDED_DESC_MUSICAL + START_DATE_DESC_MUSICAL + END_DATE_DESC_MUSICAL
                + INVALID_TAG_DESC + TAG_DESC_MUSIC, Tag.MESSAGE_CONSTRAINTS);

        //Multiple invalid values, only first reported
        assertParseFailure(parser, EVENT_NAME_DESC_MUSICAL + INVALID_VENUE_DESC
                + INVALID_MANPOWER_NEEDED_DESC + START_DATE_DESC_MUSICAL + END_DATE_DESC_MUSICAL
                + TAG_DESC_PARTY + TAG_DESC_MUSIC, EventVenue.MESSAGE_CONSTRAINTS);

        //Non-Empty Preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + EVENT_NAME_DESC_MUSICAL
                        + VENUE_DESC_MUSICAL + MANPOWER_NEEDED_DESC_MUSICAL
                        + START_DATE_DESC_MUSICAL + END_DATE_DESC_MUSICAL + TAG_DESC_PARTY + TAG_DESC_MUSIC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
    }
}
