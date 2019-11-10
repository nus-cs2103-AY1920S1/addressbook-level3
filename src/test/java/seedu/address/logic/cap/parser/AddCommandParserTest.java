package seedu.address.logic.cap.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.cap.commands.CommandTestUtil.GRADE_DESC_CS2100;
import static seedu.address.logic.cap.commands.CommandTestUtil.GRADE_DESC_CS2103;
import static seedu.address.logic.cap.commands.CommandTestUtil.MODULE_CODE_DESC_CS2100;
import static seedu.address.logic.cap.commands.CommandTestUtil.INVALID_MODULE_CODE_DESC;
import static seedu.address.logic.cap.commands.CommandTestUtil.MODULE_CODE_DESC_CS2103;
import static seedu.address.logic.cap.commands.CommandTestUtil.TITLE_DESC_CS2100;
import static seedu.address.logic.cap.commands.CommandTestUtil.TITLE_DESC_CS2103;
import static seedu.address.logic.cap.commands.CommandTestUtil.CREDIT_DESC_CS2100;
import static seedu.address.logic.cap.commands.CommandTestUtil.CREDIT_DESC_CS2103;
import static seedu.address.logic.cap.commands.CommandTestUtil.MODULE_CODE_DESC_CS2100;
import static seedu.address.logic.cap.commands.CommandTestUtil.SEMESTER_DESC_CS2100;
import static seedu.address.logic.cap.commands.CommandTestUtil.SEMESTER_DESC_CS2103;
import static seedu.address.logic.cap.commands.CommandTestUtil.VALID_MODULE_CODE_CS2100;
import static seedu.address.logic.cap.commands.CommandTestUtil.VALID_MODULE_CODE_CS2103;
import static seedu.address.logic.cap.commands.CommandTestUtil.VALID_MODULE_TITLE;
import static seedu.address.logic.cap.commands.CommandTestUtil.VALID_TITLE_CS2100;
import static seedu.address.logic.cap.commands.CommandTestUtil.VALID_TITLE_CS2103;
import static seedu.address.logic.cap.commands.CommandTestUtil.VALID_SEMESTER_CS2100;
import static seedu.address.logic.cap.commands.CommandTestUtil.VALID_CREDIT_CS2103;
import static seedu.address.logic.cap.commands.CommandTestUtil.VALID_CREDIT_CS2100;
import static seedu.address.logic.cap.commands.CommandTestUtil.VALID_CREDIT_CS2103;
import static seedu.address.logic.cap.commands.CommandTestUtil.VALID_GRADE_CS2100;
import static seedu.address.logic.cap.commands.CommandTestUtil.VALID_GRADE_CS2103;
import static seedu.address.logic.cap.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.cap.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.cap.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.cap.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalModule.CS2103;
import static seedu.address.testutil.TypicalModule.CS2100;
import static seedu.address.testutil.TypicalModule.CS2101;

import org.junit.jupiter.api.Test;

import seedu.address.logic.calendar.commands.*;
import seedu.address.logic.cap.commands.AddCommand;
import seedu.address.logic.cap.parser.exceptions.*;
import seedu.address.model.common.Module;
import seedu.address.model.cap.person.Title;
import seedu.address.model.cap.person.Semester;
import seedu.address.model.cap.person.ModuleCode;
import seedu.address.model.cap.person.Credit;
import seedu.address.model.cap.person.Grade;
import seedu.address.testutil.ModuleBuilder;



public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Module expectedModule = new ModuleBuilder(CS2100).build();

        // whitespace only preamble
        assertParseSuccess(parser,  PREAMBLE_WHITESPACE + MODULE_CODE_DESC_CS2100 + TITLE_DESC_CS2100
                + SEMESTER_DESC_CS2100
                + CREDIT_DESC_CS2100 + GRADE_DESC_CS2100, new AddCommand(expectedModule));

        // multiple module code
        assertParseSuccess(parser, MODULE_CODE_DESC_CS2100 + MODULE_CODE_DESC_CS2100 + TITLE_DESC_CS2100
                + SEMESTER_DESC_CS2100
                + CREDIT_DESC_CS2100 + GRADE_DESC_CS2100, new AddCommand(expectedModule));

        // multiple title - last title accepted
        assertParseSuccess(parser, MODULE_CODE_DESC_CS2100 + TITLE_DESC_CS2103 + TITLE_DESC_CS2100
                + SEMESTER_DESC_CS2100
                + CREDIT_DESC_CS2100 + GRADE_DESC_CS2100, new AddCommand(expectedModule));

        // multiple semesters - last semester accepted
        assertParseSuccess(parser, MODULE_CODE_DESC_CS2100 + TITLE_DESC_CS2100 + SEMESTER_DESC_CS2103
                + SEMESTER_DESC_CS2100
                + CREDIT_DESC_CS2100 + GRADE_DESC_CS2100, new AddCommand(expectedModule));


        // multiple credit - last grade accepted
        assertParseSuccess(parser, MODULE_CODE_DESC_CS2100 + TITLE_DESC_CS2100
                + SEMESTER_DESC_CS2100
                + CREDIT_DESC_CS2103 + CREDIT_DESC_CS2100 + GRADE_DESC_CS2100, new AddCommand(expectedModule));

        // multiple grade - last grade accepted
        assertParseSuccess(parser, MODULE_CODE_DESC_CS2100 + TITLE_DESC_CS2100
                        + SEMESTER_DESC_CS2100
                        + CREDIT_DESC_CS2103 + GRADE_DESC_CS2103 + GRADE_DESC_CS2100, new AddCommand(expectedModule));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_MODULE_CODE_CS2100 + SEMESTER_DESC_CS2100 + TITLE_DESC_CS2100 + CREDIT_DESC_CS2100,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, MODULE_CODE_DESC_CS2100 + VALID_SEMESTER_CS2100 + TITLE_DESC_CS2100 + CREDIT_DESC_CS2100,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, MODULE_CODE_DESC_CS2100 + SEMESTER_DESC_CS2100 + VALID_TITLE_CS2100 + CREDIT_DESC_CS2100,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, MODULE_CODE_DESC_CS2100 + SEMESTER_DESC_CS2100 + TITLE_DESC_CS2100 + VALID_CREDIT_CS2100,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_MODULE_CODE_CS2100 + VALID_SEMESTER_CS2100 + VALID_TITLE_CS2100 + VALID_CREDIT_CS2100,
                expectedMessage);
    }

//    @Test
//    public void parse_invalidValue_failure() {
//        // invalid name
//        assertParseFailure(parser, INVALID_MODULE_CODE_DESC + SEMESTER_DESC_CS2100 + TITLE_DESC_CS2100 + CREDIT_DESC_CS2100
//                + GRADE_DESC_HUSBAND + GRADE_DESC_FRIEND, Semester.MESSAGE_CONSTRAINTS);
//
//        // invalid phone
//        assertParseFailure(parser, MODULE_CODE_DESC_CS2100 + INVALID_SEMESTER_DESC + TITLE_DESC_CS2100 + CREDIT_DESC_CS2100
//                + GRADE_DESC_HUSBAND + GRADE_DESC_FRIEND, Credit.MESSAGE_CONSTRAINTS);
//
//        // invalid email
//        assertParseFailure(parser, MODULE_CODE_DESC_CS2100 + SEMESTER_DESC_CS2100 + INVALID_TITLE_DESC + CREDIT_DESC_CS2100
//                + GRADE_DESC_HUSBAND + GRADE_DESC_FRIEND, Title.MESSAGE_CONSTRAINTS);
//
//        // invalid address
//        assertParseFailure(parser, MODULE_CODE_DESC_CS2100 + SEMESTER_DESC_CS2100 + TITLE_DESC_CS2100 + INVALID_CREDIT_DESC
//                + GRADE_DESC_HUSBAND + GRADE_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);
//
//        // invalid tag
//        assertParseFailure(parser, MODULE_CODE_DESC_CS2100 + SEMESTER_DESC_CS2100 + TITLE_DESC_CS2100 + CREDIT_DESC_CS2100
//                + INVALID_GRADE_DESC + VALID_GRADE_FRIEND, Grade.MESSAGE_CONSTRAINTS);
//
//        // two invalid values, only first invalid value reported
//        assertParseFailure(parser, INVALID_MODULE_CODE_DESC + SEMESTER_DESC_CS2100 + TITLE_DESC_CS2100 + INVALID_CREDIT_DESC,
//                Semester.MESSAGE_CONSTRAINTS);
//
//        // non-empty preamble
//        assertParseFailure(parser, PREAMBLE_NON_EMPTY + MODULE_CODE_DESC_CS2100 + SEMESTER_DESC_CS2100 + TITLE_DESC_CS2100
//                + CREDIT_DESC_CS2100 + GRADE_DESC_HUSBAND + GRADE_DESC_FRIEND,
//                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
//    }
}
