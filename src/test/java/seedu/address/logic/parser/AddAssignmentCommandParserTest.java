package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGNMENT_DEADLINE_DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGNMENT_DEADLINE_DESC_MATH;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGNMENT_NAME_DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGNMENT_NAME_DESC_MATH;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ASSIGNMENT_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ASSIGNMENT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_DEADLINE_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_NAME_MATH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalAssignments.ASSIGNMENT_MATH;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.AddAssignmentCommand;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentDeadline;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.testutil.AssignmentBuilder;


public class AddAssignmentCommandParserTest {
    private AddAssignmentCommandParser parser = new AddAssignmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Assignment expectedAssignment = new AssignmentBuilder(ASSIGNMENT_MATH).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + ASSIGNMENT_NAME_DESC_MATH
                + ASSIGNMENT_DEADLINE_DESC_MATH, new AddAssignmentCommand(expectedAssignment));

        // multiple assignment names - last assignment name accepted
        assertParseSuccess(parser, ASSIGNMENT_NAME_DESC_ENGLISH + ASSIGNMENT_NAME_DESC_MATH
                        + ASSIGNMENT_DEADLINE_DESC_MATH, new AddAssignmentCommand(expectedAssignment));

        // multiple deadlines - last deadline accepted
        assertParseSuccess(parser, ASSIGNMENT_DEADLINE_DESC_MATH + ASSIGNMENT_DEADLINE_DESC_ENGLISH
                + ASSIGNMENT_DEADLINE_DESC_MATH, new AddAssignmentCommand(expectedAssignment));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAssignmentCommand.MESSAGE_USAGE);

        // missing assignment name prefix
        assertParseFailure(parser, VALID_ASSIGNMENT_NAME_MATH + ASSIGNMENT_DEADLINE_DESC_MATH,
                expectedMessage);

        // missing assignment deadline prefix
        assertParseFailure(parser, ASSIGNMENT_NAME_DESC_MATH + VALID_ASSIGNMENT_NAME_MATH, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_ASSIGNMENT_NAME_MATH + VALID_ASSIGNMENT_DEADLINE_MATH,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid assignment name
        assertParseFailure(parser, INVALID_ASSIGNMENT_NAME_DESC + ASSIGNMENT_DEADLINE_DESC_MATH,
                AssignmentName.MESSAGE_CONSTRAINTS);

        // invalid assignment deadline
        assertParseFailure(parser, ASSIGNMENT_NAME_DESC_MATH + INVALID_ASSIGNMENT_DEADLINE_DESC,
                AssignmentDeadline.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_ASSIGNMENT_NAME_DESC + INVALID_ASSIGNMENT_DEADLINE_DESC,
                AssignmentName.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + ASSIGNMENT_NAME_DESC_MATH
                        + ASSIGNMENT_DEADLINE_DESC_MATH , String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddAssignmentCommand.MESSAGE_USAGE));
    }

}
