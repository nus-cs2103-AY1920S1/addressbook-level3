package seedu.tarence.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_DAY;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_DURATION_IN_MINUTES;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_INDEX;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_START_TIME;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_WEEKS;
import static seedu.tarence.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Application;
import seedu.tarence.model.Model;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.person.NameContainsKeywordsPredicate;
import seedu.tarence.model.person.Person;
import seedu.tarence.model.student.MatricNum;
import seedu.tarence.model.student.NusnetId;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.testutil.EditPersonDescriptorBuilder;
import seedu.tarence.testutil.EditStudentDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_MATRIC_AMY = "A0123456A";
    public static final String VALID_MATRIC_BOB = "a3456789a";
    public static final String VALID_MODULE_AMY = "CS1010";
    public static final String VALID_MODULE_BOB = "CS2030";
    public static final String VALID_NUSNET_AMY = "E0123456";
    public static final String VALID_NUSNET_BOB = "E0035152";
    public static final String VALID_TUTORIAL_NAME_AMY = "T01";
    public static final String VALID_TUTORIAL_NAME_BOB = "T10";
    public static final String VALID_TUTORIAL_DAY_AMY = "MONDAY";
    public static final String VALID_TUTORIAL_DAY_BOB = "TUESDAY";
    public static final Integer VALID_TUTORIAL_INDEX_AMY = 3;
    public static final Integer VALID_TUTORIAL_INDEX_BOB = 1;

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String MATRIC_DESC_AMY = " " + VALID_MATRIC_AMY;
    public static final String MODULE_DESC_AMY = " " + PREFIX_MODULE + VALID_MODULE_AMY;
    public static final String MODULE_DESC_BOB = " " + PREFIX_MODULE + VALID_MODULE_BOB;
    public static final String NUSNET_DESC_AMY = " " + VALID_NUSNET_AMY;
    public static final String TUTORIAL_DESC_AMY = " " + PREFIX_TUTORIAL_NAME + VALID_TUTORIAL_NAME_AMY;
    public static final String TUTORIAL_DESC_BOB = " " + PREFIX_TUTORIAL_NAME + VALID_TUTORIAL_NAME_BOB;
    public static final String TUTORIAL_IDX_DESC_AMY = " " + PREFIX_TUTORIAL_INDEX + VALID_TUTORIAL_INDEX_AMY;
    public static final String TUTORIAL_IDX_DESC_BOB = " " + PREFIX_TUTORIAL_INDEX + VALID_TUTORIAL_INDEX_BOB;

    public static final String VALID_MODCODE = "EC2303";
    public static final String VALID_TUTORIAL_NAME = "Tutorial 5";
    public static final String VALID_TUTORIAL_DAY = "TUESDAY";
    public static final String VALID_TUTORIAL_START_HHMMSS = "11:00:00";
    public static final String VALID_TUTORIAL_START_HHMM = "1100";
    public static final int VALID_TUTORIAL_DUR = 60;
    public static final String VALID_WEEKS = "1,5,6,8,10";
    public static final ArrayList<Integer> VALID_WEEKS_LIST = new ArrayList<>(Arrays.asList(1, 5, 6, 8, 10));
    public static final String VALID_WEEKS_ODD = "odd";
    public static final ArrayList<Integer> VALID_WEEKS_ODD_LIST = new ArrayList<>(Arrays.asList(3, 5, 7, 9, 11, 13));
    public static final String VALID_WEEKS_RANGE = "5-10";
    public static final ArrayList<Integer> VALID_WEEKS_RANGE_LIST = new ArrayList<>(Arrays.asList(5, 6, 7, 8, 9, 10));
    public static final int VALID_WEEK = 1;

    public static final String INVALID_MODCODE = "ABCD1100S";

    public static final String INVALID_WEEKS_RANGE = "1-14";

    public static final String VALID_MODCODE_DESC = " " + PREFIX_MODULE + VALID_MODCODE;
    public static final String VALID_TUTORIAL_NAME_DESC = " " + PREFIX_TUTORIAL_NAME + VALID_TUTORIAL_NAME;
    public static final String VALID_TUTORIAL_DAY_DESC = " " + PREFIX_TUTORIAL_DAY + VALID_TUTORIAL_DAY;
    public static final String VALID_TUTORIAL_START_DESC = " " + PREFIX_TUTORIAL_START_TIME + VALID_TUTORIAL_START_HHMM;
    public static final String VALID_TUTORIAL_DUR_DESC = " " + PREFIX_TUTORIAL_DURATION_IN_MINUTES + VALID_TUTORIAL_DUR;
    public static final String VALID_WEEKS_DESC = " " + PREFIX_TUTORIAL_WEEKS + VALID_WEEKS;
    public static final String VALID_WEEKS_ODD_DESC = " " + PREFIX_TUTORIAL_WEEKS + VALID_WEEKS_ODD;
    public static final String VALID_WEEKS_RANGE_DESC = " " + PREFIX_TUTORIAL_WEEKS + VALID_WEEKS_RANGE;
    public static final String VALID_WEEK_DESC = " " + PREFIX_TUTORIAL_WEEKS + VALID_WEEK;
    public static final String INVALID_WEEKS_RANGE_DESC = " " + PREFIX_TUTORIAL_WEEKS + INVALID_WEEKS_RANGE;

    public static final String INVALID_MODCODE_DESC = " " + PREFIX_MODULE + INVALID_MODCODE;


    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol

    public static final String INVALID_TUTORIAL_INDEX_DESC_1 = " " + PREFIX_TUTORIAL_INDEX + "sa&";
    public static final String INVALID_TUTORIAL_INDEX_DESC_2 = " " + PREFIX_TUTORIAL_INDEX + "☹";
    public static final String INVALID_TUTORIAL_INDEX_DESC_3 = " " + PREFIX_TUTORIAL_INDEX + "0";


    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;
    public static final EditCommand.EditStudentDescriptor DESC_STUDENT_AMY;
    public static final EditCommand.EditStudentDescriptor DESC_STUDENT_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).withEmail(VALID_EMAIL_AMY).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).withEmail(VALID_EMAIL_BOB).build();
        DESC_STUDENT_AMY = new EditStudentDescriptorBuilder().withName(VALID_NAME_AMY)
                .withEmail(VALID_EMAIL_AMY).withModCode(new ModCode(VALID_MODCODE))
                .withTutName(new TutName(VALID_TUTORIAL_NAME))
                .withMatricNum(Optional.of(new MatricNum(VALID_MATRIC_AMY)))
                .withNusnetId(Optional.of(new NusnetId(VALID_NUSNET_AMY))).build();
        DESC_STUDENT_BOB = new EditStudentDescriptorBuilder().withName(VALID_NAME_AMY)
                .withEmail(VALID_EMAIL_BOB).withModCode(new ModCode(VALID_MODCODE))
                .withTutName(new TutName(VALID_TUTORIAL_NAME))
                .withMatricNum(Optional.of(new MatricNum(VALID_MATRIC_BOB)))
                .withNusnetId(Optional.of(new NusnetId(VALID_NUSNET_BOB))).build();
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
            assertEquals(expectedCommandResult.toString(), result.toString());
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
     * - the application, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Application expectedApplication = new Application(actualModel.getApplication());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedApplication, actualModel.getApplication());
    }

    /**
     * Executes a given {@code command} where the expected result is deferring its execution pending user confirmation.
     * Checks that the command is correctly stored by the model for later execution and that the appropriate
     * confirmation dialogue is sent to the user.
     */
    public static void assertCommandDelayed(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        try {
            CommandResult actualCommandResult = command.execute(actualModel);
            assertEquals(actualCommandResult, expectedCommandResult);
            assertEquals(actualModel, expectedModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Updates {@code model}'s application to show only the person at the given {@code targetIndex} in the
     * {@code model}'s application.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Updates {@code model}'s application to show only the student at the given {@code targetIndex} in the
     * {@code model}'s application.
     */
    public static void showStudentAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredStudentList().size());

        Student student = model.getFilteredStudentList().get(targetIndex.getZeroBased());
        final String[] splitName = student.getName().fullName.split("\\s+");
        model.updateFilteredStudentList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredStudentList().size());
    }

}
