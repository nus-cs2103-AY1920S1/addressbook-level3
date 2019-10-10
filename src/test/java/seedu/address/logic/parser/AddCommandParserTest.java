package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_STATUS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FINANCE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_PUBLICITY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_URGENCY;
import static seedu.address.logic.commands.CommandTestUtil.TASK_NAME_DESC_FINANCE;
import static seedu.address.logic.commands.CommandTestUtil.TASK_NAME_DESC_PUBLICITY;
import static seedu.address.logic.commands.CommandTestUtil.TASK_STATUS_DESC_FINANCE;
import static seedu.address.logic.commands.CommandTestUtil.TASK_STATUS_DESC_PUBLICITY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PUBLICITY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_URGENCY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_FINANCE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalTasks.BUILD_WEBSITE;
import static seedu.address.testutil.TypicalTasks.REVIEW_BUDGET;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskStatus;
import seedu.address.testutil.TaskBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Task expectedTask = new TaskBuilder(BUILD_WEBSITE).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TASK_NAME_DESC_PUBLICITY
                + TASK_STATUS_DESC_PUBLICITY + TAG_DESC_PUBLICITY, new AddCommand(expectedTask));

        // multiple names - last name accepted
        assertParseSuccess(parser, TASK_NAME_DESC_FINANCE + TASK_NAME_DESC_PUBLICITY
                 + TASK_STATUS_DESC_PUBLICITY + TAG_DESC_PUBLICITY, new AddCommand(expectedTask));

        // multiple status - last status accepted
        assertParseSuccess(parser, TASK_NAME_DESC_PUBLICITY
                + TASK_STATUS_DESC_FINANCE
                + TASK_STATUS_DESC_PUBLICITY + TAG_DESC_PUBLICITY, new AddCommand(expectedTask));

        // multiple tags - all accepted
        Task expectedTaskMultipleTags = new TaskBuilder(BUILD_WEBSITE).withTags(VALID_TAG_PUBLICITY, VALID_TAG_URGENCY)
                .build();
        assertParseSuccess(parser, TASK_NAME_DESC_PUBLICITY
                + TASK_STATUS_DESC_PUBLICITY
                + TAG_DESC_URGENCY + TAG_DESC_PUBLICITY, new AddCommand(expectedTaskMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Task expectedTask = new TaskBuilder(REVIEW_BUDGET).withTags().build();
        assertParseSuccess(parser, TASK_NAME_DESC_FINANCE, new AddCommand(expectedTask));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_TASK_NAME_FINANCE, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TASK_NAME_FINANCE, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_TASK_NAME_DESC + TAG_DESC_FINANCE + TAG_DESC_PUBLICITY,
                Name.MESSAGE_CONSTRAINTS);

        // invalid task status
        assertParseFailure(parser, TASK_NAME_DESC_PUBLICITY
                + INVALID_TASK_STATUS_DESC
                + TAG_DESC_FINANCE + TAG_DESC_PUBLICITY, TaskStatus.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, TASK_NAME_DESC_PUBLICITY + INVALID_TAG_DESC + VALID_TAG_PUBLICITY,
                Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_TASK_NAME_DESC, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TASK_NAME_DESC_PUBLICITY
                + TAG_DESC_FINANCE + TAG_DESC_PUBLICITY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
