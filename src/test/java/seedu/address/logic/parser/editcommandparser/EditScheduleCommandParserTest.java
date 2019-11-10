package seedu.address.logic.parser.editcommandparser;

import static seedu.address.commons.core.Messages.DATE_MESSAGE_CONSTRAINTS;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.TIME_MESSAGE_CONSTRAINTS;
import static seedu.address.logic.commands.CommandTestUtil.ALLOW_DESC_EVERYDAY;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_FRIDAY;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_MONDAY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VENUE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_EVERYDAY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIDAY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_MONDAY;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_FRIDAY;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_MONDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLOW_EVERYDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALENDAR_MONDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_EVERYDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MONDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_MONDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_MONDAY;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_FRIDAY;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_MONDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ORDER;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.editcommand.EditScheduleCommand;
import seedu.address.logic.commands.editcommand.EditScheduleCommand.EditScheduleDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.schedule.Venue;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditScheduleDescriptorBuilder;

public class EditScheduleCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditScheduleCommand.MESSAGE_USAGE);

    private EditScheduleCommandParser parser = new EditScheduleCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, DATE_DESC_MONDAY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditScheduleCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + DATE_DESC_MONDAY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + DATE_DESC_MONDAY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_DATE_DESC, DATE_MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_TIME_DESC, TIME_MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_VENUE_DESC, Venue.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid date followed by valid time
        assertParseFailure(parser, "1" + INVALID_DATE_DESC + TIME_DESC_MONDAY, DATE_MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Schedule} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_EVERYDAY + TAG_DESC_MONDAY + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_EVERYDAY + TAG_EMPTY + TAG_DESC_MONDAY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_EVERYDAY + TAG_DESC_MONDAY, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_DATE_DESC + INVALID_TIME_DESC + VALID_VENUE_MONDAY + VALID_TIME_MONDAY,
                DATE_MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() throws ParseException {
        Index targetIndex = INDEX_SECOND_ORDER;
        String userInput = targetIndex.getOneBased() + DATE_DESC_MONDAY + TAG_DESC_EVERYDAY
                + TIME_DESC_MONDAY + VENUE_DESC_MONDAY + ALLOW_DESC_EVERYDAY;

        EditScheduleDescriptor descriptor = new EditScheduleDescriptorBuilder().withDate(VALID_CALENDAR_MONDAY)
                .withTime(VALID_CALENDAR_MONDAY).withVenue(VALID_VENUE_MONDAY)
                .withTags(VALID_TAG_EVERYDAY).build();
        EditScheduleCommand expectedCommand = new EditScheduleCommand(targetIndex, descriptor, VALID_ALLOW_EVERYDAY);
        EditScheduleCommand command = parser.parse(userInput);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_ORDER;
        String userInput = targetIndex.getOneBased() + DATE_DESC_MONDAY + VENUE_DESC_MONDAY;

        EditScheduleDescriptor descriptor = new EditScheduleDescriptorBuilder().withDate(VALID_CALENDAR_MONDAY)
                .withVenue(VALID_VENUE_MONDAY).build();
        EditScheduleCommand expectedCommand = new EditScheduleCommand(targetIndex, descriptor, false);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // date
        Index targetIndex = INDEX_FIRST_ORDER;
        String userInput = targetIndex.getOneBased() + DATE_DESC_MONDAY;
        EditScheduleDescriptor descriptor = new EditScheduleDescriptorBuilder().withDate(VALID_CALENDAR_MONDAY).build();
        EditScheduleCommand expectedCommand = new EditScheduleCommand(targetIndex, descriptor, false);
        assertParseSuccess(parser, userInput, expectedCommand);

        // time
        userInput = targetIndex.getOneBased() + TIME_DESC_MONDAY;
        descriptor = new EditScheduleDescriptorBuilder().withTime(VALID_CALENDAR_MONDAY).build();
        expectedCommand = new EditScheduleCommand(targetIndex, descriptor, false);
        assertParseSuccess(parser, userInput, expectedCommand);

        // venue
        userInput = targetIndex.getOneBased() + VENUE_DESC_MONDAY;
        descriptor = new EditScheduleDescriptorBuilder().withVenue(VALID_VENUE_MONDAY).build();
        expectedCommand = new EditScheduleCommand(targetIndex, descriptor, false);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_MONDAY;
        descriptor = new EditScheduleDescriptorBuilder().withTags(VALID_TAG_MONDAY).build();
        expectedCommand = new EditScheduleCommand(targetIndex, descriptor, false);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_ORDER;
        String userInput = targetIndex.getOneBased() + DATE_DESC_FRIDAY + TIME_DESC_FRIDAY + VENUE_DESC_FRIDAY
                + TAG_DESC_FRIDAY + DATE_DESC_MONDAY + TIME_DESC_MONDAY + VENUE_DESC_MONDAY + TAG_DESC_MONDAY;

        EditScheduleDescriptor descriptor = new EditScheduleDescriptorBuilder().withDate(VALID_CALENDAR_MONDAY)
                .withTime(VALID_CALENDAR_MONDAY).withVenue(VALID_VENUE_MONDAY)
                .withTags(VALID_TAG_MONDAY, VALID_TAG_FRIDAY).build();
        EditScheduleCommand expectedCommand = new EditScheduleCommand(targetIndex, descriptor, false);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_ORDER;
        String userInput = targetIndex.getOneBased() + INVALID_DATE_DESC + DATE_DESC_MONDAY;
        EditScheduleDescriptor descriptor = new EditScheduleDescriptorBuilder().withDate(VALID_CALENDAR_MONDAY).build();
        EditScheduleCommand expectedCommand = new EditScheduleCommand(targetIndex, descriptor, false);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + VENUE_DESC_MONDAY + INVALID_DATE_DESC + TIME_DESC_MONDAY
                + DATE_DESC_MONDAY;
        descriptor = new EditScheduleDescriptorBuilder().withDate(VALID_CALENDAR_MONDAY).withVenue(VALID_VENUE_MONDAY)
                .withTime(VALID_CALENDAR_MONDAY).build();
        expectedCommand = new EditScheduleCommand(targetIndex, descriptor, false);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_FIRST_ORDER;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditScheduleDescriptor descriptor = new EditScheduleDescriptorBuilder().withTags().build();
        EditScheduleCommand expectedCommand = new EditScheduleCommand(targetIndex, descriptor, false);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
