package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICALCONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENTPHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentNameContainsKeywordsPredicate;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.student.NameContainsKeywordsPredicate;
import seedu.address.model.student.Student;
import seedu.address.testutil.EditAssignmentDescriptorBuilder;
import seedu.address.testutil.EditStudentDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_PARENTPHONE_AMY = "33333333";
    public static final String VALID_PARENTPHONE_BOB = "44444444";
    public static final String VALID_MEDICALCONDITION_AMY = "Asthma";
    public static final String VALID_MEDICALCONDITION_BOB = "Sinus";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_FILE_1 = "file:/C:/ping.png";
    public static final String VALID_FILE_2 = "file:/C:/ping.jpg";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String MEDICALCONDITION_DESC_AMY = " " + PREFIX_MEDICALCONDITION + VALID_MEDICALCONDITION_AMY;
    public static final String MEDICALCONDITION_DESC_BOB = " " + PREFIX_MEDICALCONDITION + VALID_MEDICALCONDITION_BOB;
    public static final String PARENTPHONE_DESC_AMY = " " + PREFIX_PARENTPHONE + VALID_PARENTPHONE_AMY;
    public static final String PARENTPHONE_DESC_BOB = " " + PREFIX_PARENTPHONE + VALID_PARENTPHONE_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_PARENTPHONE_DESC = " " + PREFIX_PARENTPHONE + "91a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String VALID_CLASSNAME_MATH = "Math Class";
    public static final String VALID_CLASSNAME_ENGLISH = "English Class";
    public static final String VALID_STARTTIME_MON = "06/01/2020 1200";
    public static final String VALID_STARTTIME_TUE = "07/01/2020 1200";
    public static final String VALID_STARTTIME_WED = "08/01/2020 1200";
    public static final String VALID_ENDTIME_MON = "06/01/2020 1300";
    public static final String VALID_ENDTIME_TUE = "07/01/2020 1300";
    public static final String VALID_ENDTIME_WED = "08/01/2020 1300";

    public static final String VALID_ASSIGNMENT_NAME_ENGLISH = "English Worksheet 1";
    public static final String VALID_ASSIGNMENT_NAME_MATH = "Math Homework 3";
    public static final String VALID_ASSIGNMENT_DEADLINE_ENGLISH = "02/12/2024 1029";
    public static final String VALID_ASSIGNMENT_DEADLINE_MATH = "12/12/2019 1800";

    public static final String ASSIGNMENT_NAME_DESC_ENGLISH = " " + PREFIX_ASSIGNMENT + VALID_ASSIGNMENT_NAME_ENGLISH;
    public static final String ASSIGNMENT_NAME_DESC_MATH = " " + PREFIX_ASSIGNMENT + VALID_ASSIGNMENT_NAME_MATH;
    public static final String ASSIGNMENT_DEADLINE_DESC_ENGLISH = " " + PREFIX_DEADLINE
            + VALID_ASSIGNMENT_DEADLINE_ENGLISH;
    public static final String ASSIGNMENT_DEADLINE_DESC_MATH = " " + PREFIX_DEADLINE + VALID_ASSIGNMENT_DEADLINE_MATH;

    public static final String INVALID_ASSIGNMENT_NAME_DESC = " " + PREFIX_ASSIGNMENT + "Math&&"; // '&' not allowed
    public static final String INVALID_ASSIGNMENT_DEADLINE_DESC = " " + PREFIX_DEADLINE + "invalid"; // invalid deadline
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditStudentCommand.EditStudentDescriptor DESC_AMY;
    public static final EditStudentCommand.EditStudentDescriptor DESC_BOB;
    public static final EditAssignmentCommand.EditAssignmentDescriptor DESC_ENGLISH;
    public static final EditAssignmentCommand.EditAssignmentDescriptor DESC_MATH;

    static {
        DESC_AMY = new EditStudentDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withParentPhone(VALID_PARENTPHONE_AMY)
                .withAddress(VALID_ADDRESS_AMY).withMedicalCondition(VALID_MEDICALCONDITION_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditStudentDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withParentPhone(VALID_PARENTPHONE_BOB)
                .withAddress(VALID_ADDRESS_BOB).withMedicalCondition((VALID_MEDICALCONDITION_BOB))
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        DESC_ENGLISH = new EditAssignmentDescriptorBuilder().withAssignmentName(VALID_ASSIGNMENT_NAME_ENGLISH)
                .withAssignmentDeadline(VALID_ASSIGNMENT_DEADLINE_ENGLISH).build();
        DESC_MATH = new EditAssignmentDescriptorBuilder().withAssignmentName(VALID_ASSIGNMENT_NAME_MATH)
                .withAssignmentDeadline(VALID_ASSIGNMENT_DEADLINE_MATH).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the classroom, filtered student list and selected student in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Classroom expectedClassroom = new Classroom(actualModel.getCurrentClassroom());
        List<Student> expectedFilteredList = new ArrayList<>(actualModel.getFilteredStudentList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedClassroom, actualModel.getCurrentClassroom());
        assertEquals(expectedFilteredList, actualModel.getFilteredStudentList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the student at the given {@code targetIndex} in the
     * {@code model}'s classroom.
     */
    public static void showStudentAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredStudentList().size());

        Student student = model.getFilteredStudentList().get(targetIndex.getZeroBased());
        final String[] splitName = student.getName().fullName.split("\\s+");
        model.updateFilteredStudentList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredStudentList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the assignment at the given {@code targetIndex} in the
     * {@code model}'s classroom.
     */
    public static void showAssignmentAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredAssignmentList().size());

        Assignment assignment = model.getFilteredAssignmentList().get(targetIndex.getZeroBased());
        final String[] splitName = assignment.getAssignmentName().toString().split("\\s+");
        model.updateFilteredAssignmentList(new AssignmentNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredAssignmentList().size());
    }

}
