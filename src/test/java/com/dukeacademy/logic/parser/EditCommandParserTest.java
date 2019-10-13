package com.dukeacademy.logic.parser;

import static com.dukeacademy.logic.commands.CommandTestUtil.DIFFICULTY_DESC_AMY;
import static com.dukeacademy.logic.commands.CommandTestUtil.DIFFICULTY_DESC_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.INVALID_TOPIC_DESC;
import static com.dukeacademy.logic.commands.CommandTestUtil.STATUS_DESC_AMY;
import static com.dukeacademy.logic.commands.CommandTestUtil.STATUS_DESC_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static com.dukeacademy.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static com.dukeacademy.logic.commands.CommandTestUtil.TITLE_DESC_AMY;
import static com.dukeacademy.logic.commands.CommandTestUtil.TOPIC_DESC_AMY;
import static com.dukeacademy.logic.commands.CommandTestUtil.TOPIC_DESC_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_DIFFICULTY_AMY;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_STATUS_AMY;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_STATUS_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_TITLE_AMY;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_TOPIC_AMY;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_TOPIC_BOB;
import static com.dukeacademy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static com.dukeacademy.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import com.dukeacademy.commons.core.Messages;
import com.dukeacademy.commons.core.index.Index;
import com.dukeacademy.logic.commands.EditCommand;
import com.dukeacademy.testutil.EditQuestionDescriptorBuilder;
import com.dukeacademy.testutil.TypicalIndexes;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + CliSyntax.PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_TITLE_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + TITLE_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + TITLE_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        /*
        assertParseFailure(parser, INVALID_TITLE_DESC,
            Title.MESSAGE_CONSTRAINTS); // invalid title
        assertParseFailure(parser, INVALID_TOPIC_DESC, Topic.MESSAGE_CONSTRAINTS); // invalid topic
        assertParseFailure(parser, INVALID_STATUS_DESC, Status.MESSAGE_CONSTRAINTS); // invalid status
        assertParseFailure(parser, INVALID_DIFFICULTY_DESC, Difficulty.MESSAGE_CONSTRAINTS); // invalid difficulty
        assertParseFailure(parser, INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid topic followed by valid status
        assertParseFailure(parser, INVALID_TOPIC_DESC + STATUS_DESC_AMY, Topic.MESSAGE_CONSTRAINTS);

        // valid topic followed by invalid topic. The test case for invalid topic followed by valid topic
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, TOPIC_DESC_BOB + INVALID_TOPIC_DESC, Topic.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Question} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, TAG_DESC_FRIEND
                + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, TAG_DESC_FRIEND + TAG_EMPTY
                + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, TAG_EMPTY + TAG_DESC_FRIEND
                + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, INVALID_TITLE_DESC + INVALID_STATUS_DESC + VALID_DIFFICULTY_AMY
                        + VALID_TOPIC_AMY,
                Title.MESSAGE_CONSTRAINTS);
         */
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_SECOND_QUESTION;
        String userInput = targetIndex.getOneBased() + TOPIC_DESC_BOB + TAG_DESC_HUSBAND
                + STATUS_DESC_AMY + DIFFICULTY_DESC_AMY + TITLE_DESC_AMY + TAG_DESC_FRIEND;

        EditCommand.EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder()
            .withTitle(VALID_TITLE_AMY)
            .withTopic(VALID_TOPIC_BOB).withStatus(VALID_STATUS_AMY).withDifficulty(VALID_DIFFICULTY_AMY)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_QUESTION;
        String userInput = targetIndex.getOneBased() + TOPIC_DESC_BOB + STATUS_DESC_AMY;

        EditCommand.EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder()
            .withTopic(VALID_TOPIC_BOB)
            .withStatus(VALID_STATUS_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = TypicalIndexes.INDEX_THIRD_QUESTION;
        String userInput = targetIndex.getOneBased() + TITLE_DESC_AMY;
        EditCommand.EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder().withTitle(VALID_TITLE_AMY)
                                                                                         .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // topic
        userInput = targetIndex.getOneBased() + TOPIC_DESC_AMY;
        descriptor = new EditQuestionDescriptorBuilder().withTopic(VALID_TOPIC_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // status
        userInput = targetIndex.getOneBased() + STATUS_DESC_AMY;
        descriptor = new EditQuestionDescriptorBuilder().withStatus(VALID_STATUS_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // difficulty
        userInput = targetIndex.getOneBased() + DIFFICULTY_DESC_AMY;
        descriptor = new EditQuestionDescriptorBuilder().withDifficulty(VALID_DIFFICULTY_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditQuestionDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_QUESTION;
        String userInput = targetIndex.getOneBased() + TOPIC_DESC_AMY + DIFFICULTY_DESC_AMY + STATUS_DESC_AMY
                + TAG_DESC_FRIEND + TOPIC_DESC_AMY + DIFFICULTY_DESC_AMY + STATUS_DESC_AMY + TAG_DESC_FRIEND
                + TOPIC_DESC_BOB + DIFFICULTY_DESC_BOB + STATUS_DESC_BOB + TAG_DESC_HUSBAND;

        EditCommand.EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder()
            .withTopic(VALID_TOPIC_BOB)
            .withStatus(VALID_STATUS_BOB).withDifficulty(VALID_DIFFICULTY_BOB)
            .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
            .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = TypicalIndexes.INDEX_FIRST_QUESTION;
        String userInput = targetIndex.getOneBased() + INVALID_TOPIC_DESC + TOPIC_DESC_BOB;
        EditCommand.EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder().withTopic(VALID_TOPIC_BOB)
                                                                                         .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + STATUS_DESC_BOB + INVALID_TOPIC_DESC + DIFFICULTY_DESC_BOB
                + TOPIC_DESC_BOB;
        descriptor = new EditQuestionDescriptorBuilder().withTopic(VALID_TOPIC_BOB).withStatus(VALID_STATUS_BOB)
                                                        .withDifficulty(VALID_DIFFICULTY_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = TypicalIndexes.INDEX_THIRD_QUESTION;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditCommand.EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
