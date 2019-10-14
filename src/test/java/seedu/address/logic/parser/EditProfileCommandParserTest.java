package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.HISTORY_DESC_DENGUE;
import static seedu.address.logic.commands.CommandTestUtil.HISTORY_DESC_STROKE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HISTORY_STROKE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICALHISTORY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditProfileCommand;
import seedu.address.logic.commands.EditProfileCommand.EditPersonDescriptor;
import seedu.address.profile.medical.MedicalHistory;
import seedu.address.profile.person.Name;
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class EditProfileCommandParserTest {

    private static final String MEDICALHISTORY_EMPTY = " " + PREFIX_MEDICALHISTORY;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditProfileCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no field specified
        assertParseFailure(parser, "", EditProfileCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, MedicalHistory.MESSAGE_CONSTRAINTS); // invalid tag

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + HISTORY_DESC_STROKE + HISTORY_DESC_DENGUE + MEDICALHISTORY_EMPTY,
                MedicalHistory.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + HISTORY_DESC_STROKE + MEDICALHISTORY_EMPTY + HISTORY_DESC_DENGUE,
                MedicalHistory.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + MEDICALHISTORY_EMPTY + HISTORY_DESC_STROKE + HISTORY_DESC_DENGUE,
                MedicalHistory.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS);
    }


    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        String userInput = NAME_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditProfileCommand expectedCommand = new EditProfileCommand(descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = HISTORY_DESC_STROKE;
        descriptor = new EditPersonDescriptorBuilder().withMedicalHistories(VALID_HISTORY_STROKE).build();
        expectedCommand = new EditProfileCommand(descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        String userInput = MEDICALHISTORY_EMPTY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withMedicalHistories().build();
        EditProfileCommand expectedCommand = new EditProfileCommand(descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
