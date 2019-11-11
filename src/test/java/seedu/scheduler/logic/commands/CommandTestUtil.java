package seedu.scheduler.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_FACULTY;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_FILE_PATH;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_NUS_WORK_EMAIL;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_PERSONAL_EMAIL;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_SLOT;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_YEAR_OF_STUDY;
import static seedu.scheduler.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.model.IntervieweeList;
import seedu.scheduler.model.InterviewerList;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.ModelManager;
import seedu.scheduler.model.person.Interviewee;
import seedu.scheduler.model.person.IntervieweeNameHasKeywordsPredicate;
import seedu.scheduler.model.person.Interviewer;
import seedu.scheduler.model.person.InterviewerNameHasKeywordsPredicate;
import seedu.scheduler.model.person.Name;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_ROLE_AMY_INTVR = "interviewer";
    public static final String VALID_ROLE_BOB_INTVE = "interviewee";
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_FACULTY_AMY = "School of Engineering";
    public static final String VALID_FACULTY_BOB = "School of Computing";
    public static final String VALID_YEAR_OF_STUDY_AMY = "1";
    public static final String VALID_YEAR_OF_STUDY_BOB = "2";
    public static final String VALID_DEPARTMENT_AMY = "Logistics";
    public static final String VALID_DEPARTMENT_BOB = "Marketing";
    public static final String VALID_SLOT_AMY = "17/10/2019 12:30-13:30";
    public static final String VALID_SLOT_BOB = "17/10/2019 13:30-14:30";
    public static final String VALID_PERSONAL_EMAIL_AMY = "amy_infamy@gmail.com";
    public static final String VALID_NUS_WORK_EMAIL_AMY = "amy_infamy@u.nus.edu";
    public static final String VALID_PERSONAL_EMAIL_BOB = "bob_cat@gmail.com";
    public static final String VALID_NUS_WORK_EMAIL_BOB = "bob_cat@u.nus.edu";
    public static final String VALID_FILE_PATH = "src/test/data/ImportsTest/storage.csv";

    public static final String ROLE_DESC_AMY_INTVR = " " + PREFIX_ROLE + VALID_ROLE_AMY_INTVR;
    public static final String ROLE_DESC_BOB_INTVE = " " + PREFIX_ROLE + VALID_ROLE_BOB_INTVE;
    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String FACULTY_DESC_AMY = " " + PREFIX_FACULTY + VALID_FACULTY_AMY;
    public static final String FACULTY_DESC_BOB = " " + PREFIX_FACULTY + VALID_FACULTY_BOB;
    public static final String YEAR_OF_STUDY_DESC_AMY = " " + PREFIX_YEAR_OF_STUDY + VALID_YEAR_OF_STUDY_AMY;
    public static final String YEAR_OF_STUDY_DESC_BOB = " " + PREFIX_YEAR_OF_STUDY + VALID_YEAR_OF_STUDY_BOB;
    public static final String DEPARTMENT_DESC_AMY = " " + PREFIX_DEPARTMENT + VALID_DEPARTMENT_AMY;
    public static final String DEPARTMENT_DESC_BOB = " " + PREFIX_DEPARTMENT + VALID_DEPARTMENT_BOB;
    public static final String SLOT_DESC_AMY = " " + PREFIX_SLOT + VALID_SLOT_AMY;
    public static final String SLOT_DESC_BOB = " " + PREFIX_SLOT + VALID_SLOT_BOB;
    public static final String EMAIL_PERSONAL_DESC_AMY = " " + PREFIX_PERSONAL_EMAIL + VALID_PERSONAL_EMAIL_AMY;
    public static final String EMAIL_NUS_WORK_DESC_AMY = " " + PREFIX_NUS_WORK_EMAIL + VALID_NUS_WORK_EMAIL_AMY;
    public static final String EMAIL_PERSONAL_DESC_BOB = " " + PREFIX_PERSONAL_EMAIL + VALID_PERSONAL_EMAIL_BOB;
    public static final String EMAIL_NUS_WORK_DESC_BOB = " " + PREFIX_NUS_WORK_EMAIL + VALID_NUS_WORK_EMAIL_BOB;
    public static final String FILE_PATH_DESC = " " + PREFIX_FILE_PATH + VALID_FILE_PATH;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_FACULTY_DESC = " " + PREFIX_FACULTY; // empty string not allowed
    public static final String INVALID_YEAR_OF_STUDY = " " + PREFIX_YEAR_OF_STUDY + "abc"; // only numbers allowed
    public static final String INVALID_DEPARTMENT_DESC = " " + PREFIX_DEPARTMENT; // empty string not allowed for dept
    public static final String INVALID_SLOT_DESC = " " + PREFIX_SLOT + "123456"; // invalid format
    public static final String INVALID_PERSONAL_EMAIL_DESC = " " + PREFIX_PERSONAL_EMAIL + "bool sheet";
    public static final String INVALID_NUS_WORK_EMAIL_DESC = " " + PREFIX_NUS_WORK_EMAIL + "long dong";
    public static final String INVALID_FILE_PATH_DESC = " " + PREFIX_FILE_PATH
            + "src/test/data/ImportsTest/InterviewerInvalidTestData.csv";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    // ==================================================================================================
    // CUSTOM ASSERTIONS FOR USAGE IN TEST PACKAGE
    // ==================================================================================================

    /**
     * Checks that the given {@code interviewee} exsits in the {@code model}.
     */
    public static void assertModelHasInterviewee(Model model, Interviewee interviewee) {
        try {
            model.getInterviewee(interviewee.getName().fullName);
        } catch (NoSuchElementException e) {
            throw new AssertionError("Interviewee does not exist in model.", e);
        }
    }

    /**
     * Checks that the given {@code interviewer} exsits in the {@code model}.
     */
    public static void assertModelHasInterviewer(Model model, Interviewer interviewer) {
        try {
            model.getInterviewer(interviewer.getName().fullName);
        } catch (NoSuchElementException e) {
            throw new AssertionError("Interviewer does not exist in model.", e);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel,
            CommandResult expectedCommandResult, Model expectedModel) {
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
     * - the interviewee/interviewer lists, filtered interviewee/interviewer list and selected person in
     * {@code actualModel} remain unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        IntervieweeList expectedIntervieweeList = new IntervieweeList(actualModel.getMutableIntervieweeList());
        InterviewerList expectedInterviewerList = new InterviewerList(actualModel.getMutableInterviewerList());

        List<Interviewee> expectedFilteredIntervieweeList = new ArrayList<>(actualModel.getFilteredIntervieweeList());
        List<Interviewer> expectedFilteredInterviewerList = new ArrayList<>(actualModel.getFilteredInterviewerList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedIntervieweeList, ((ModelManager) actualModel).getIntervieweeList());
        assertEquals(expectedInterviewerList, ((ModelManager) actualModel).getInterviewerList());

        assertEquals(expectedFilteredIntervieweeList, actualModel.getFilteredIntervieweeList());
        assertEquals(expectedFilteredInterviewerList, actualModel.getFilteredInterviewerList());
    }

    // ===================================================================================================
    // CUSTOM CONVENIENCE METHODS FOR TESTING PURPOSES ONLY
    // ===================================================================================================

    /**
     * Updates {@code model}'s filtered interviewee list to show only the interviewee with {@code name}.
     */
    public static void showIntervieweeWithName(Model model, Name name) {
        try {
            Interviewee i = model.getInterviewee(name.fullName);
            final String[] splitName = i.getName().fullName.split("\\s+");
            model.updateFilteredIntervieweeList(new IntervieweeNameHasKeywordsPredicate(Arrays.asList(splitName[0])));
        } catch (NoSuchElementException e) {
            throw new AssertionError("Name should exist in the model beforehand!");
        }
    }

    /**
     * Updates {@code model}'s filtered interviewee list to show only the interviewer with {@code name}.
     */
    public static void showInterviewerWithName(Model model, Name name) {
        try {
            Interviewer i = model.getInterviewer(name.fullName);
            final String[] splitName = i.getName().fullName.split("\\s+");
            model.updateFilteredInterviewerList(new InterviewerNameHasKeywordsPredicate(Arrays.asList(splitName[0])));
        } catch (NoSuchElementException e) {
            throw new AssertionError("Name should exist in the model beforehand!");
        }
    }

    /**
     * Generates test data for export command.
     * @return expected results for export function.
     */
    public static ArrayList<String> getExpectedExportedData() {
        ArrayList<String> exportedData = new ArrayList<>();
        exportedData.add("10/09/2019,Welfare - Hazel,Technical - Johnathan,Publicity - Lucia");
        exportedData.add("18:00-18:30,John,Steven,0");
        exportedData.add("18:30-19:00,Alex,Clark,John");
        exportedData.add("19:00-19:30,Alicia,0,charlie");
        exportedData.add("19:30-20:00,Charlie,0,Selina");
        exportedData.add("20:00-20:30,Selina,0,0");
        exportedData.add("20:30-21:00,Natal,0,0");
        return exportedData;
    }
}
