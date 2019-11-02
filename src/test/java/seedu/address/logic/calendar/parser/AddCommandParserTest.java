package seedu.address.logic.calendar.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.calendar.commands.CommandTestUtil.INVALID_TASKDAY_DESC;
import static seedu.address.logic.calendar.commands.CommandTestUtil.INVALID_TASKTAG_DESC;
import static seedu.address.logic.calendar.commands.CommandTestUtil.INVALID_TASKTIME_DESC;
import static seedu.address.logic.calendar.commands.CommandTestUtil.INVALID_TASKTITLE_DESC;
import static seedu.address.logic.calendar.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.calendar.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.calendar.commands.CommandTestUtil.TASKDAY_DESC_AMY;
import static seedu.address.logic.calendar.commands.CommandTestUtil.TASKDAY_DESC_BOB;
import static seedu.address.logic.calendar.commands.CommandTestUtil.TASKDEADLINE_DESC_AMY;
import static seedu.address.logic.calendar.commands.CommandTestUtil.TASKDEADLINE_DESC_BOB;
import static seedu.address.logic.calendar.commands.CommandTestUtil.TASKDESCRIPTION_DESC_AMY;
import static seedu.address.logic.calendar.commands.CommandTestUtil.TASKDESCRIPTION_DESC_BOB;
import static seedu.address.logic.calendar.commands.CommandTestUtil.TASKTAG_DESC_FRIEND;
import static seedu.address.logic.calendar.commands.CommandTestUtil.TASKTAG_DESC_HUSBAND;
import static seedu.address.logic.calendar.commands.CommandTestUtil.TASKTIME_DESC_AMY;
import static seedu.address.logic.calendar.commands.CommandTestUtil.TASKTIME_DESC_BOB;
import static seedu.address.logic.calendar.commands.CommandTestUtil.TASKTITLE_DESC_AMY;
import static seedu.address.logic.calendar.commands.CommandTestUtil.TASKTITLE_DESC_BOB;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKDAY_BOB;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKDESCRIPTION_BOB;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKTAG_FRIEND;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKTAG_HUSBAND;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKTIME_BOB;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKTITLE_BOB;
import static seedu.address.logic.calendar.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.calendar.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalTasks.AMY;
import static seedu.address.testutil.TypicalTasks.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.calendar.commands.AddCommand;
import seedu.address.model.calendar.tag.TaskTag;
import seedu.address.model.calendar.task.Task;
import seedu.address.model.calendar.task.TaskDay;
import seedu.address.model.calendar.task.TaskTime;
import seedu.address.model.calendar.task.TaskTitle;
import seedu.address.testutil.TaskBuilder;


public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Task expectedPerson = new TaskBuilder(BOB).withTaskTags(VALID_TASKTAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TASKTITLE_DESC_BOB + TASKDAY_DESC_BOB
            + TASKDESCRIPTION_DESC_BOB + TASKDEADLINE_DESC_BOB + TASKTIME_DESC_BOB + TASKTAG_DESC_FRIEND,
            new AddCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, TASKTITLE_DESC_AMY + TASKTITLE_DESC_BOB + TASKDAY_DESC_BOB
            + TASKDESCRIPTION_DESC_BOB + TASKTIME_DESC_BOB + TASKTAG_DESC_FRIEND + TASKDEADLINE_DESC_BOB,
            new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Task expectedPersonMultipleTags = new TaskBuilder(BOB)
            .withTaskTags(VALID_TASKTAG_FRIEND, VALID_TASKTAG_HUSBAND).build();
        assertParseSuccess(parser, TASKTITLE_DESC_BOB + TASKDAY_DESC_BOB + TASKDESCRIPTION_DESC_BOB
            + TASKTIME_DESC_BOB + TASKTAG_DESC_HUSBAND + TASKTAG_DESC_FRIEND + TASKDEADLINE_DESC_BOB,
            new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Task expectedPerson = new TaskBuilder(AMY).withTaskTags().build();
        assertParseSuccess(parser, TASKTITLE_DESC_AMY + TASKTIME_DESC_AMY + TASKDESCRIPTION_DESC_AMY
                + TASKDAY_DESC_AMY + TASKDEADLINE_DESC_AMY, new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_TASKTITLE_BOB + TASKDAY_DESC_BOB + TASKDESCRIPTION_DESC_BOB
                + TASKTIME_DESC_BOB + TASKDEADLINE_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, TASKTITLE_DESC_BOB + VALID_TASKDAY_BOB + TASKDESCRIPTION_DESC_BOB
                + TASKTIME_DESC_BOB + TASKDEADLINE_DESC_BOB, expectedMessage);

        // missing email prefix
        // assertParseFailure(parser, TASKTITLE_DESC_BOB + TASKDAY_DESC_BOB + VALID_TASKDESCRIPTION_BOB
        //         + TASKTIME_DESC_BOB + TASKDEADLINE_DESC_BOB, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, TASKTITLE_DESC_BOB + TASKDAY_DESC_BOB + TASKDESCRIPTION_DESC_BOB
                + VALID_TASKTIME_BOB + TASKDEADLINE_DESC_BOB, expectedMessage);

        // missing deadline prefix
        // assertParseFailure(parser, TASKTITLE_DESC_BOB + TASKDAY_DESC_BOB + TASKDESCRIPTION_DESC_BOB
        //        + TASKTIME_DESC_BOB + VALID_TASKDEADLINE_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TASKTITLE_BOB + VALID_TASKDAY_BOB + VALID_TASKDESCRIPTION_BOB
                + VALID_TASKTIME_BOB + TASKDEADLINE_DESC_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_TASKTITLE_DESC + TASKDAY_DESC_BOB + TASKDESCRIPTION_DESC_BOB
            + TASKTIME_DESC_BOB + TASKTAG_DESC_HUSBAND + TASKTAG_DESC_FRIEND + TASKDEADLINE_DESC_BOB,
            TaskTitle.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, TASKTITLE_DESC_BOB + INVALID_TASKDAY_DESC + TASKDESCRIPTION_DESC_BOB
            + TASKTIME_DESC_BOB + TASKTAG_DESC_HUSBAND + TASKTAG_DESC_FRIEND + TASKDEADLINE_DESC_BOB,
            TaskDay.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, TASKTITLE_DESC_BOB + TASKDAY_DESC_BOB + TASKDESCRIPTION_DESC_BOB
            + INVALID_TASKTIME_DESC + TASKTAG_DESC_HUSBAND + TASKTAG_DESC_FRIEND + TASKDEADLINE_DESC_BOB,
            TaskTime.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, TASKTITLE_DESC_BOB + TASKDAY_DESC_BOB + TASKDESCRIPTION_DESC_BOB
            + TASKTIME_DESC_BOB + INVALID_TASKTAG_DESC + VALID_TASKTAG_FRIEND + TASKDEADLINE_DESC_BOB,
            TaskTag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_TASKTITLE_DESC + TASKDAY_DESC_BOB + TASKDESCRIPTION_DESC_BOB
                + INVALID_TASKTIME_DESC + TASKDEADLINE_DESC_BOB, TaskTitle.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TASKTITLE_DESC_BOB + TASKDAY_DESC_BOB + TASKDESCRIPTION_DESC_BOB
                + TASKTIME_DESC_BOB + TASKTAG_DESC_HUSBAND + TASKTAG_DESC_FRIEND + TASKDEADLINE_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
