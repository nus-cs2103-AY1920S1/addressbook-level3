package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_STATUS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FINANCE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_PUBLICITY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_URGENCY;
import static seedu.address.logic.commands.CommandTestUtil.TASK_NAME_DESC_FINANCE;
import static seedu.address.logic.commands.CommandTestUtil.TASK_NAME_DESC_PUBLICITY;
import static seedu.address.logic.commands.CommandTestUtil.TASK_STATUS_DESC_FINANCE;
import static seedu.address.logic.commands.CommandTestUtil.TASK_STATUS_DESC_PUBLICITY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FINANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PUBLICITY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_URGENCY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_FINANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_PUBLICITY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_STATUS_PUBLICITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_TASK;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
<<<<<<< HEAD
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
=======
import seedu.address.logic.commands.EditCommand.EditTaskDescriptor;
>>>>>>> team/master
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Name;
import seedu.address.model.task.TaskStatus;
import seedu.address.testutil.EditTaskDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TASK_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_TASK_NAME_FINANCE, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + TASK_NAME_DESC_FINANCE, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + TASK_NAME_DESC_FINANCE, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_TASK_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag


        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Task} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_PUBLICITY + TAG_DESC_FINANCE + TAG_EMPTY,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_PUBLICITY + TAG_EMPTY + TAG_DESC_FINANCE,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_PUBLICITY + TAG_DESC_FINANCE,
                Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_TASK_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_TASK;
        String userInput = targetIndex.getOneBased() + TAG_DESC_FINANCE
                + TASK_NAME_DESC_FINANCE;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withName(VALID_TASK_NAME_FINANCE)
                .withTags(VALID_TAG_FINANCE).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + TASK_NAME_DESC_FINANCE;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withName(VALID_TASK_NAME_FINANCE).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    // TODO add tests for member and progress
    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_TASK;
        String userInput = targetIndex.getOneBased() + TASK_NAME_DESC_FINANCE;
        EditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withName(VALID_TASK_NAME_FINANCE).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_PUBLICITY;
        descriptor = new EditTaskDescriptorBuilder().withTags(VALID_TAG_PUBLICITY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased()
                + TASK_NAME_DESC_FINANCE
                + TASK_STATUS_DESC_FINANCE + TAG_DESC_URGENCY
                + TASK_NAME_DESC_PUBLICITY
                + TASK_STATUS_DESC_PUBLICITY + TAG_DESC_PUBLICITY;

        EditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withName(VALID_TASK_NAME_PUBLICITY)
                .withStatus(TaskStatus.valueOf(VALID_TASK_STATUS_PUBLICITY.toUpperCase()))
                .withTags(VALID_TAG_PUBLICITY, VALID_TAG_URGENCY)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + INVALID_TASK_STATUS_DESC + TASK_STATUS_DESC_PUBLICITY;
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withStatus(TaskStatus.valueOf(VALID_TASK_STATUS_PUBLICITY.toUpperCase())).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + TAG_DESC_FINANCE + INVALID_TASK_NAME_DESC
                + TASK_NAME_DESC_FINANCE;
        descriptor = new EditTaskDescriptorBuilder().withName(VALID_TASK_NAME_FINANCE).withTags(VALID_TAG_FINANCE)
                .build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_TASK;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
