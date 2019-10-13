package com.dukeacademy.logic.parser;

import static com.dukeacademy.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static com.dukeacademy.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static com.dukeacademy.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static com.dukeacademy.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static com.dukeacademy.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static com.dukeacademy.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static com.dukeacademy.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static com.dukeacademy.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static com.dukeacademy.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static com.dukeacademy.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static com.dukeacademy.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static com.dukeacademy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static com.dukeacademy.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import com.dukeacademy.commons.core.Messages;
import com.dukeacademy.commons.core.index.Index;
import com.dukeacademy.logic.commands.EditCommand;
import com.dukeacademy.model.question.Difficulty;
import com.dukeacademy.model.question.Status;
import com.dukeacademy.model.question.Title;
import com.dukeacademy.model.question.Topic;
import com.dukeacademy.model.tag.Tag;
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
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Title.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Topic.MESSAGE_CONSTRAINTS); // invalid topic
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Status.MESSAGE_CONSTRAINTS); // invalid status
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC, Difficulty.MESSAGE_CONSTRAINTS); // invalid difficulty
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid topic followed by valid status
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Topic.MESSAGE_CONSTRAINTS);

        // valid topic followed by invalid topic. The test case for invalid topic followed by valid topic
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + PHONE_DESC_BOB + INVALID_PHONE_DESC, Topic.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Question} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND
                + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY
                + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND
                + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY
                        + VALID_PHONE_AMY,
                Title.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_SECOND_QUESTION;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND;

        EditCommand.EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder()
            .withTitle(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withDifficulty(VALID_ADDRESS_AMY)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_QUESTION;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditCommand.EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder()
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = TypicalIndexes.INDEX_THIRD_QUESTION;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditCommand.EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder().withTitle(VALID_NAME_AMY)
                                                                                         .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // topic
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditQuestionDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // status
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditQuestionDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // difficulty
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_AMY;
        descriptor = new EditQuestionDescriptorBuilder().withDifficulty(VALID_ADDRESS_AMY).build();
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
        String userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + TAG_DESC_FRIEND + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND
                + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_HUSBAND;

        EditCommand.EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder()
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withDifficulty(VALID_ADDRESS_BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
            .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = TypicalIndexes.INDEX_FIRST_QUESTION;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditCommand.EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                                                                                         .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + EMAIL_DESC_BOB + INVALID_PHONE_DESC + ADDRESS_DESC_BOB
                + PHONE_DESC_BOB;
        descriptor = new EditQuestionDescriptorBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                                                        .withDifficulty(VALID_ADDRESS_BOB).build();
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
