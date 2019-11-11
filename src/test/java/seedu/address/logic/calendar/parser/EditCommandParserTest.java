package seedu.address.logic.calendar.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.calendar.commands.CommandTestUtil.INVALID_TASKDAY_DESC;
import static seedu.address.logic.calendar.commands.CommandTestUtil.INVALID_TASKDESCRIPTION_DESC;
import static seedu.address.logic.calendar.commands.CommandTestUtil.INVALID_TASKTAG_DESC;
import static seedu.address.logic.calendar.commands.CommandTestUtil.INVALID_TASKTIME_DESC;
import static seedu.address.logic.calendar.commands.CommandTestUtil.INVALID_TASKTITLE_DESC;
import static seedu.address.logic.calendar.commands.CommandTestUtil.TASKDAY_DESC_AMY;
import static seedu.address.logic.calendar.commands.CommandTestUtil.TASKDAY_DESC_BOB;
import static seedu.address.logic.calendar.commands.CommandTestUtil.TASKDEADLINE_DESC_BOB;
import static seedu.address.logic.calendar.commands.CommandTestUtil.TASKDESCRIPTION_DESC_AMY;
import static seedu.address.logic.calendar.commands.CommandTestUtil.TASKDESCRIPTION_DESC_BOB;
import static seedu.address.logic.calendar.commands.CommandTestUtil.TASKTAG_DESC_FRIEND;
import static seedu.address.logic.calendar.commands.CommandTestUtil.TASKTAG_DESC_HUSBAND;
import static seedu.address.logic.calendar.commands.CommandTestUtil.TASKTIME_DESC_AMY;
import static seedu.address.logic.calendar.commands.CommandTestUtil.TASKTIME_DESC_BOB;
import static seedu.address.logic.calendar.commands.CommandTestUtil.TASKTITLE_DESC_AMY;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKDAY_AMY;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKDAY_BOB;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKDEADLINE_BOB;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKDESCRIPTION_AMY;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKDESCRIPTION_BOB;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKTAG_FRIEND;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKTAG_HUSBAND;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKTIME_AMY;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKTIME_BOB;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKTITLE_AMY;
import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKTAG;
import static seedu.address.logic.calendar.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.calendar.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.calendar.commands.EditCommand;
import seedu.address.model.calendar.tag.TaskTag;
import seedu.address.model.calendar.task.TaskDay;
import seedu.address.model.calendar.task.TaskTime;
import seedu.address.model.calendar.task.TaskTitle;
import seedu.address.testutil.EditTaskDescriptorBuilder;




public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TASKTAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_TASKTITLE_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + TASKTITLE_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + TASKTITLE_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_TASKDAY_DESC, TaskDay.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_TASKTIME_DESC, TaskTime.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_TASKTAG_DESC, TaskTag.MESSAGE_CONSTRAINTS);

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_TASKDAY_DESC + TASKDESCRIPTION_DESC_AMY,
            TaskDay.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + TASKDAY_DESC_BOB + INVALID_TASKDAY_DESC,
            TaskDay.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Task} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TASKTAG_DESC_FRIEND + TASKTAG_DESC_HUSBAND + TAG_EMPTY,
            TaskTag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TASKTAG_DESC_FRIEND + TAG_EMPTY + TASKTAG_DESC_HUSBAND,
            TaskTag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TASKTAG_DESC_FRIEND + TASKTAG_DESC_HUSBAND,
            TaskTag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_TASKTITLE_DESC + INVALID_TASKDESCRIPTION_DESC
                + VALID_TASKTIME_AMY + VALID_TASKDAY_AMY,
                TaskTitle.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + TASKTITLE_DESC_AMY + TASKDAY_DESC_BOB
             + TASKDESCRIPTION_DESC_AMY + TASKTIME_DESC_AMY + TASKTAG_DESC_HUSBAND + TASKTAG_DESC_FRIEND
             + TASKDEADLINE_DESC_BOB;

        EditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTaskTitle(VALID_TASKTITLE_AMY)
            .withTaskDay(VALID_TASKDAY_BOB).withTaskDescription(VALID_TASKDESCRIPTION_AMY)
            .withTaskTime(VALID_TASKTIME_AMY)
            .withTaskTags(VALID_TASKTAG_HUSBAND, VALID_TASKTAG_FRIEND).withTaskDeadline(VALID_TASKDEADLINE_BOB)
            .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + TASKDAY_DESC_BOB + TASKDESCRIPTION_DESC_AMY;

        EditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTaskDay(VALID_TASKDAY_BOB)
                .withTaskDescription(VALID_TASKDESCRIPTION_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + TASKTITLE_DESC_AMY;
        EditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
            .withTaskTitle(VALID_TASKTITLE_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + TASKDAY_DESC_AMY;
        descriptor = new EditTaskDescriptorBuilder().withTaskDay(VALID_TASKDAY_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + TASKDESCRIPTION_DESC_AMY;
        descriptor = new EditTaskDescriptorBuilder().withTaskDescription(VALID_TASKDESCRIPTION_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + TASKTIME_DESC_AMY;
        descriptor = new EditTaskDescriptorBuilder().withTaskTime(VALID_TASKTIME_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TASKTAG_DESC_FRIEND;
        descriptor = new EditTaskDescriptorBuilder().withTaskTags(VALID_TASKTAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + TASKDAY_DESC_AMY + TASKTIME_DESC_AMY + TASKDAY_DESC_AMY
                + TASKTAG_DESC_FRIEND + TASKDAY_DESC_AMY + TASKTIME_DESC_AMY + TASKDAY_DESC_AMY + TASKTAG_DESC_FRIEND
                + TASKDAY_DESC_BOB + TASKTIME_DESC_BOB + TASKDESCRIPTION_DESC_BOB + TASKTAG_DESC_HUSBAND;

        EditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTaskDay(VALID_TASKDAY_BOB)
                .withTaskDescription(VALID_TASKDESCRIPTION_BOB).withTaskTime(VALID_TASKTIME_BOB)
                .withTaskTags(VALID_TASKTAG_FRIEND, VALID_TASKTAG_HUSBAND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_TASKDAY_DESC + TASKDAY_DESC_BOB;
        EditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
            .withTaskDay(VALID_TASKDAY_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + TASKDESCRIPTION_DESC_BOB + INVALID_TASKDAY_DESC + TASKTIME_DESC_BOB
                + TASKDAY_DESC_BOB;
        descriptor = new EditTaskDescriptorBuilder().withTaskDay(VALID_TASKDAY_BOB)
            .withTaskDescription(VALID_TASKDESCRIPTION_BOB)
                .withTaskTime(VALID_TASKTIME_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTaskTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
