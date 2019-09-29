package seedu.tarence.logic.parser;

import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tarence.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.tarence.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.tarence.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.tarence.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.tarence.logic.commands.CommandTestUtil.MATRIC_DESC_AMY;
import static seedu.tarence.logic.commands.CommandTestUtil.MODULE_DESC_AMY;
import static seedu.tarence.logic.commands.CommandTestUtil.MODULE_DESC_BOB;
import static seedu.tarence.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.tarence.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.tarence.logic.commands.CommandTestUtil.NUSNET_DESC_AMY;
import static seedu.tarence.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.tarence.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.tarence.logic.commands.CommandTestUtil.TUTORIAL_DESC_AMY;
import static seedu.tarence.logic.commands.CommandTestUtil.TUTORIAL_DESC_BOB;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.tarence.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tarence.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tarence.testutil.TypicalStudents.BOB;
import static seedu.tarence.testutil.TypicalStudents.AMY;

import org.junit.jupiter.api.Test;

import seedu.tarence.logic.commands.AddStudentCommand;
import seedu.tarence.model.person.Email;
import seedu.tarence.model.person.Name;
import seedu.tarence.model.student.Student;
import seedu.tarence.testutil.StudentBuilder;

public class AddStudentCommandParserTest {
    private AddStudentCommandParser parser = new AddStudentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Student expectedStudent = new StudentBuilder(AMY).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_AMY + EMAIL_DESC_AMY
                        + MATRIC_DESC_AMY + NUSNET_DESC_AMY + MODULE_DESC_AMY + TUTORIAL_DESC_AMY,
                new AddStudentCommand(expectedStudent));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_BOB + NAME_DESC_AMY + EMAIL_DESC_AMY
                        + MATRIC_DESC_AMY + NUSNET_DESC_AMY + MODULE_DESC_AMY + TUTORIAL_DESC_AMY,
                new AddStudentCommand(expectedStudent));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_AMY + EMAIL_DESC_AMY + EMAIL_DESC_AMY
                        + MATRIC_DESC_AMY + NUSNET_DESC_AMY + MODULE_DESC_AMY + TUTORIAL_DESC_AMY,
                new AddStudentCommand(expectedStudent));
    }

    @Test
    public void parse_OptionalFields_success() {
        Student expectedStudentAmy = new StudentBuilder(AMY).build();
        Student expectedStudentBob = new StudentBuilder(BOB).withoutMatricNum().withoutNusnetId().build();

        // Missing optional fields
        assertParseSuccess(parser, NAME_DESC_BOB + EMAIL_DESC_BOB
                + MODULE_DESC_BOB + TUTORIAL_DESC_BOB,
                new AddStudentCommand(expectedStudentBob));

        // random ordering of optional fields
        assertParseSuccess(parser, NAME_DESC_AMY + NUSNET_DESC_AMY
                + TUTORIAL_DESC_AMY + MODULE_DESC_AMY + EMAIL_DESC_AMY + MATRIC_DESC_AMY,
                new AddStudentCommand(expectedStudentAmy));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStudentCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + EMAIL_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_EMAIL_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_EMAIL_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + EMAIL_DESC_BOB
                + MODULE_DESC_BOB + TUTORIAL_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_EMAIL_DESC
                + MODULE_DESC_BOB + TUTORIAL_DESC_BOB, Email.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + EMAIL_DESC_BOB
                + MODULE_DESC_BOB + TUTORIAL_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + EMAIL_DESC_BOB
                        + MODULE_DESC_BOB + TUTORIAL_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStudentCommand.MESSAGE_USAGE));
    }
}
