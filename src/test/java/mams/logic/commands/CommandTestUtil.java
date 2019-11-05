package mams.logic.commands;

import static mams.logic.parser.CliSyntax.PREFIX_CREDITS;
import static mams.logic.parser.CliSyntax.PREFIX_NAME;
import static mams.logic.parser.CliSyntax.PREFIX_PREVMODS;
import static mams.logic.parser.CliSyntax.PREFIX_STUDENT;
import static mams.logic.parser.CliSyntax.PREFIX_TAG;
import static mams.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mams.commons.core.index.Index;
import mams.logic.commands.exceptions.CommandException;
import mams.logic.history.CommandHistory;
import mams.model.Mams;
import mams.model.Model;
import mams.model.appeal.Appeal;
import mams.model.module.Module;
import mams.model.student.Student;
import mams.model.student.StudentContainsKeywordsPredicate;


/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_CREDITS_AMY = "20";
    public static final String VALID_CREDITS_BOB = "20";
    public static final String VALID_PREVMODS_AMY = "CS1010, CS2101";
    public static final String VALID_PREVMODS_BOB = "CS1010, CS2105";
    public static final String VALID_MATRICID_AMY = "A0169928E";
    public static final String VALID_MATRICID_BOB = "A0124123Q";
    public static final String VALID_TAG_APPEAL1 = "C000001";
    public static final String VALID_TAG_APPEAL2 = "C000002";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String CREDITS_DESC_AMY = " " + PREFIX_CREDITS + VALID_CREDITS_AMY;
    public static final String CREDITS_DESC_BOB = " " + PREFIX_CREDITS + VALID_CREDITS_BOB;
    public static final String PREVMODS_DESC_AMY = " " + PREFIX_PREVMODS + VALID_PREVMODS_AMY;
    public static final String PREVMODS_DESC_BOB = " " + PREFIX_PREVMODS + VALID_PREVMODS_BOB;
    public static final String MATRICID_DESC_AMY = " " + PREFIX_STUDENT + VALID_MATRICID_AMY;
    public static final String MATRICID_DESC_BOB = " " + PREFIX_STUDENT + VALID_MATRICID_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_APPEAL2;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_APPEAL1;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_CREDITS_DESC = " " + PREFIX_CREDITS + "6"; // too low
    public static final String INVALID_PREVMODS_DESC = " " + PREFIX_PREVMODS + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_MATRICID_DESC = " " + PREFIX_STUDENT; // empty string not allowed for matricId
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "CS1010*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";


    static {
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     * - does not check for commandHistory match.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel, new CommandHistory());
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
     * Same as {@link #assertCommandSuccess(Command, Model, String, Model)}, but takes in
     * {@code actualCommandHistory} and {@code expectedCommandHistory} for comparison as well.
     */
    public static void assertCommandSuccessWithHistory(Command command,
                                                       Model actualModel,
                                                       CommandResult expectedCommandResult,
                                                       Model expectedModel,
                                                       CommandHistory actualCommandHistory,
                                                       CommandHistory expectedCommandHistory) {
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - MAMS, filtered student list and selected student in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Mams expectedMams = new Mams(actualModel.getMams());
        List<Student> expectedFilteredList = new ArrayList<>(actualModel.getFilteredStudentList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel, new CommandHistory()));
        assertEquals(expectedMams, actualModel.getMams());
        assertEquals(expectedFilteredList, actualModel.getFilteredStudentList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the appeal at the given {@code targetIndex} in the
     * {@code model}'s MAMS.
     */
    public static void showAppealAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredAppealList().size());

        Appeal appeal = model.getFilteredAppealList().get(targetIndex.getZeroBased());
        model.updateFilteredAppealList(appeal::equals);

        assertEquals(1, model.getFilteredAppealList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the module at the given {@code targetIndex} in the
     * {@code model}'s MAMS.
     */
    public static void showModuleAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredModuleList().size());

        Module module = model.getFilteredModuleList().get(targetIndex.getZeroBased());
        model.updateFilteredModuleList(module::equals);

        assertEquals(1, model.getFilteredModuleList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the student at the given {@code targetIndex} in the
     * {@code model}'s MAMS.
     */
    public static void showStudentAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredStudentList().size());

        Student student = model.getFilteredStudentList().get(targetIndex.getZeroBased());
        final String[] splitName = student.getName().fullName.split("\\s+");
        model.updateFilteredStudentList(new StudentContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredStudentList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show all appeals in MAMS.
     */
    public static void showAllAppeals(Model model) {
        model.updateFilteredAppealList(Model.PREDICATE_SHOW_ALL_APPEALS);
    }

    /**
     * Updates {@code model}'s filtered list to show all modules in MAMS.
     */
    public static void showAllModules(Model model) {
        model.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
    }

    /**
     * Updates {@code model}'s filtered list to show all students in MAMS.
     */
    public static void showAllStudents(Model model) {
        model.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);
    }

    /**
     * Updates {@code model}'s filtered lists to show all items in all three lists of MAMS.
     */
    public static void showAll(Model model) {
        showAllAppeals(model);
        showAllModules(model);
        showAllStudents(model);
    }

}
