package seedu.system.logic.parser;

import static seedu.system.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.system.logic.commands.CommandTestUtil.DOB_DESC_AMY;
import static seedu.system.logic.commands.CommandTestUtil.DOB_DESC_BOB;
import static seedu.system.logic.commands.CommandTestUtil.GENDER_DESC_AMY;
import static seedu.system.logic.commands.CommandTestUtil.GENDER_DESC_BOB;
import static seedu.system.logic.commands.CommandTestUtil.INVALID_DOB_DESC;
import static seedu.system.logic.commands.CommandTestUtil.INVALID_GENDER_DESC;
import static seedu.system.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.system.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.system.logic.commands.CommandTestUtil.VALID_DOB_AMY;
import static seedu.system.logic.commands.CommandTestUtil.VALID_DOB_BOB;
import static seedu.system.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.system.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.system.logic.commands.CommandTestUtil.VALID_NAME_AMY;

import static seedu.system.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.system.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.system.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.system.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.system.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.jupiter.api.Test;

import seedu.system.commons.core.index.Index;
import seedu.system.logic.commands.outofsession.EditPersonCommand;
import seedu.system.logic.commands.outofsession.EditPersonCommand.EditPersonDescriptor;
import seedu.system.logic.parser.outofsession.EditPersonCommandParser;
import seedu.system.model.person.CustomDate;
import seedu.system.model.person.Gender;
import seedu.system.model.person.Name;

import seedu.system.testutil.EditPersonDescriptorBuilder;

public class EditPersonCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPersonCommand.MESSAGE_USAGE);

    private EditPersonCommandParser parser = new EditPersonCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditPersonCommand.MESSAGE_NOT_EDITED);

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
        assertParseFailure(parser, "1" + INVALID_DOB_DESC, CustomDate.MESSAGE_CONSTRAINTS); // invalid DOB
        assertParseFailure(parser, "1" + INVALID_GENDER_DESC, Gender.MESSAGE_CONSTRAINTS); // invalid gender

        // invalid DOB followed by valid gender
        assertParseFailure(parser, "1" + INVALID_DOB_DESC + GENDER_DESC_AMY, CustomDate.MESSAGE_CONSTRAINTS);

        // valid DOB followed by invalid DOB. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + DOB_DESC_BOB + INVALID_DOB_DESC, CustomDate.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_DOB_DESC
                + VALID_GENDER_AMY, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY + DOB_DESC_BOB + GENDER_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withDateOfBirth(VALID_DOB_BOB).withGender(VALID_GENDER_AMY).build();

        EditPersonCommand expectedCommand = new EditPersonCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + DOB_DESC_BOB;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withDateOfBirth(VALID_DOB_BOB).build();
        EditPersonCommand expectedCommand = new EditPersonCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditPersonCommand expectedCommand = new EditPersonCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // DOB
        userInput = targetIndex.getOneBased() + DOB_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withDateOfBirth(VALID_DOB_AMY).build();
        expectedCommand = new EditPersonCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // gender
        userInput = targetIndex.getOneBased() + GENDER_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withGender(VALID_GENDER_AMY).build();
        expectedCommand = new EditPersonCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + DOB_DESC_AMY + GENDER_DESC_AMY
                + DOB_DESC_BOB + DOB_DESC_BOB + GENDER_DESC_BOB;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withDateOfBirth(VALID_DOB_BOB)
                .withGender(VALID_GENDER_BOB).build();
        EditPersonCommand expectedCommand = new EditPersonCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INVALID_DOB_DESC + DOB_DESC_BOB;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withDateOfBirth(VALID_DOB_BOB).build();
        EditPersonCommand expectedCommand = new EditPersonCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INVALID_DOB_DESC + GENDER_DESC_BOB + DOB_DESC_BOB;
        descriptor = new EditPersonDescriptorBuilder().withDateOfBirth(VALID_DOB_BOB)
                .withGender(VALID_GENDER_BOB).build();

        expectedCommand = new EditPersonCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
