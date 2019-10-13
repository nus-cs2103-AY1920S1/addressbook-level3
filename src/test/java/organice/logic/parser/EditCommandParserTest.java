package organice.logic.parser;

import static organice.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static organice.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static organice.logic.commands.CommandTestUtil.INVALID_NRIC_DESC;
import static organice.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static organice.logic.commands.CommandTestUtil.INVALID_TYPE_DESC;
import static organice.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static organice.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static organice.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static organice.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static organice.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static organice.logic.commands.CommandTestUtil.TYPE_DESC_AMY;
import static organice.logic.commands.CommandTestUtil.TYPE_DESC_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static organice.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_TYPE_AMY;
import static organice.logic.commands.CommandTestUtil.VALID_TYPE_BOB;
import static organice.logic.parser.CommandParserTestUtil.assertParseFailure;
import static organice.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static organice.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static organice.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static organice.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import organice.commons.core.index.Index;
import organice.logic.commands.EditCommand;
import organice.logic.commands.EditCommand.EditPersonDescriptor;
import organice.model.person.Name;
import organice.model.person.Nric;
import organice.model.person.Phone;
import organice.model.person.Type;
import organice.testutil.EditPersonDescriptorBuilder;

public class EditCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

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
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_NRIC_DESC, Nric.MESSAGE_CONSTRAINTS); // invalid nric
        assertParseFailure(parser, "1" + INVALID_TYPE_DESC, Type.MESSAGE_CONSTRAINTS); // invalid type

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + TYPE_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB + INVALID_PHONE_DESC,
                Phone.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + TYPE_DESC_BOB + INVALID_NRIC_DESC + INVALID_NAME_DESC
                + INVALID_PHONE_DESC, Nric.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + TYPE_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB + NAME_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withType(VALID_TYPE_BOB)
            .withNric(VALID_NRIC_BOB).withPhone(VALID_PHONE_BOB).withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + TYPE_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withType(VALID_TYPE_BOB)
                .withNric(VALID_NRIC_BOB).withPhone(VALID_PHONE_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // nric
        userInput = targetIndex.getOneBased() + NRIC_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withNric(VALID_NRIC_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // type
        userInput = targetIndex.getOneBased() + TYPE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withType(VALID_TYPE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + TYPE_DESC_AMY + TYPE_DESC_BOB
                + PHONE_DESC_AMY + PHONE_DESC_BOB;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withType(VALID_TYPE_BOB)
                .withPhone(VALID_PHONE_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB + NRIC_DESC_BOB;
        descriptor = new EditPersonDescriptorBuilder().withNric(VALID_NRIC_BOB).withPhone(VALID_PHONE_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
