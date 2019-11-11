package seedu.pluswork.logic.parser;

import static seedu.pluswork.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pluswork.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.pluswork.logic.commands.CommandTestUtil.INVALID_TASK_NAME_DESC;
import static seedu.pluswork.logic.commands.CommandTestUtil.INVALID_TASK_STATUS_DESC;
import static seedu.pluswork.logic.commands.CommandTestUtil.TAG_DESC_FINANCE;
import static seedu.pluswork.logic.commands.CommandTestUtil.TAG_DESC_PUBLICITY;
import static seedu.pluswork.logic.commands.CommandTestUtil.TAG_DESC_URGENCY;
import static seedu.pluswork.logic.commands.CommandTestUtil.TASK_NAME_DESC_FINANCE;
import static seedu.pluswork.logic.commands.CommandTestUtil.TASK_NAME_DESC_PUBLICITY;
import static seedu.pluswork.logic.commands.CommandTestUtil.TASK_STATUS_DESC_FINANCE;
import static seedu.pluswork.logic.commands.CommandTestUtil.TASK_STATUS_DESC_PUBLICITY;
import static seedu.pluswork.logic.commands.CommandTestUtil.VALID_TAG_FINANCE;
import static seedu.pluswork.logic.commands.CommandTestUtil.VALID_TAG_PUBLICITY;
import static seedu.pluswork.logic.commands.CommandTestUtil.VALID_TAG_URGENCY;
import static seedu.pluswork.logic.commands.CommandTestUtil.VALID_TASK_NAME_FINANCE;
import static seedu.pluswork.logic.commands.CommandTestUtil.VALID_TASK_NAME_PUBLICITY;
import static seedu.pluswork.logic.commands.CommandTestUtil.VALID_TASK_STATUS_PUBLICITY;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_TASK_INDEX;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_TASK_TAG;
import static seedu.pluswork.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.pluswork.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.pluswork.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.pluswork.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.pluswork.testutil.TypicalIndexes.INDEX_THIRD_TASK;

import org.junit.jupiter.api.Test;

import seedu.pluswork.commons.core.index.Index;
import seedu.pluswork.logic.commands.EditTaskCommand;
import seedu.pluswork.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.model.tag.Tag;
import seedu.pluswork.model.task.Name;
import seedu.pluswork.model.task.TaskStatus;
import seedu.pluswork.testutil.EditTaskDescriptorBuilder;

public class EditTaskCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TASK_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE);

    private EditTaskCommandParser parser = new EditTaskCommandParser();

    @Test
    public void parse_missingParts_failure() throws CommandException {
        // no index specified
        assertParseFailure(parser, "edit-task " + VALID_TASK_NAME_FINANCE, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "edit-task ti/1 ", EditTaskCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "edit-task ti/", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() throws CommandException {
        // negative index
        assertParseFailure(parser, "edit-task ti/-5" + TASK_NAME_DESC_FINANCE, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "edit-task ti/0" + TASK_NAME_DESC_FINANCE, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "edit-task ti/1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "edit-task ti/1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() throws CommandException {
        assertParseFailure(parser, "edit-task ti/1 "
                + INVALID_TASK_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "edit-task ti/1 "
                + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag


        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Task} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "edit-task ti/1" + TAG_DESC_PUBLICITY + TAG_DESC_FINANCE + TAG_EMPTY,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "edit-task ti/1" + TAG_DESC_PUBLICITY + TAG_EMPTY + TAG_DESC_FINANCE,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "edit-task ti/1" + TAG_EMPTY + TAG_DESC_PUBLICITY + TAG_DESC_FINANCE,
                Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "edit-task ti/1" + INVALID_TASK_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() throws CommandException {
        Index targetIndex = INDEX_SECOND_TASK;
        String userInput = "edit-task " + PREFIX_TASK_INDEX + targetIndex.getOneBased() + TAG_DESC_FINANCE
                + TASK_NAME_DESC_FINANCE;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withName(VALID_TASK_NAME_FINANCE)
                .withTags(VALID_TAG_FINANCE).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() throws CommandException {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = "edit-task " + PREFIX_TASK_INDEX + targetIndex.getOneBased() + TASK_NAME_DESC_FINANCE;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withName(VALID_TASK_NAME_FINANCE).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() throws CommandException {
        // name
        Index targetIndex = INDEX_THIRD_TASK;
        String userInput = "edit-task " + PREFIX_TASK_INDEX + targetIndex.getOneBased() + TASK_NAME_DESC_FINANCE;
        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withName(VALID_TASK_NAME_FINANCE).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = "edit-task " + PREFIX_TASK_INDEX + targetIndex.getOneBased() + TAG_DESC_PUBLICITY;
        descriptor = new EditTaskDescriptorBuilder().withTags(VALID_TAG_PUBLICITY).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() throws CommandException {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = "edit-task " + PREFIX_TASK_INDEX + targetIndex.getOneBased()
                + TASK_NAME_DESC_FINANCE
                + TASK_STATUS_DESC_FINANCE + TAG_DESC_URGENCY
                + TASK_NAME_DESC_PUBLICITY
                + TASK_STATUS_DESC_PUBLICITY + TAG_DESC_PUBLICITY;

        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withName(VALID_TASK_NAME_PUBLICITY)
                .withStatus(TaskStatus.valueOf(VALID_TASK_STATUS_PUBLICITY.toUpperCase()))
                .withTags(VALID_TAG_PUBLICITY, VALID_TAG_URGENCY)
                .build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() throws CommandException {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = "edit-task " + PREFIX_TASK_INDEX + targetIndex.getOneBased()
                + INVALID_TASK_STATUS_DESC + TASK_STATUS_DESC_PUBLICITY;
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withStatus(TaskStatus.valueOf(VALID_TASK_STATUS_PUBLICITY.toUpperCase())).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = "edit-task " + PREFIX_TASK_INDEX + targetIndex.getOneBased()
                + TAG_DESC_FINANCE + INVALID_TASK_NAME_DESC + TASK_NAME_DESC_FINANCE;
        descriptor = new EditTaskDescriptorBuilder().withName(VALID_TASK_NAME_FINANCE).withTags(VALID_TAG_FINANCE)
                .build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() throws CommandException {
        Index targetIndex = INDEX_THIRD_TASK;
        String userInput = "edit-task " + PREFIX_TASK_INDEX + targetIndex.getOneBased() + TAG_EMPTY;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTags().build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
