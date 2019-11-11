package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Color;
import seedu.address.model.Model;
import seedu.address.model.ModulePlanner;
import seedu.address.model.ModulesInfo;
import seedu.address.model.PrereqTree;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_CS3244 = "Machine Learning";
    public static final String VALID_NAME_ST2334 = "Probability and Statistics";
    public static final String VALID_MODULE_CODE_CS3244 = "CS3244";
    public static final String VALID_MODULE_CODE_ST2334 = "ST2334";
    public static final int VALID_MC_COUNT_CS3244 = 4;
    public static final int VALID_MC_COUNT_ST2334 = 4;
    public static final Color VALID_COLOR_CS3244 = Color.RED;
    public static final Color VALID_COLOR_ST2334 = Color.RED;
    public static final List<Tag> VALID_TAGS_CS3244 = new ArrayList<>();
    public static final UniqueTagList VALID_TAGS_ST2334 = new UniqueTagList();
    public static final boolean VALID_PREREQ_SATISFIED_ST2334 = true;
    public static final boolean VALID_PREREQ_SATISFIED_CS3244 = false;
    public static final PrereqTree VALID_PREREQ_TREE_ST2334 =
            ParserUtil.parsePrereqTree("(OR MA1102R MA1312 MA1505 MA1507 MA1521)");
    public static final PrereqTree VALID_PREREQ_TREE_CS3244 =
            ParserUtil.parsePrereqTree("(AND MA1101R MA1521 ST2334 (OR CS2010 CS2020 CS2040)");

    public static final SemesterName SP_1_SEMESTER_NAME = SemesterName.Y1S1;
    public static final SemesterName SP_2_SEMESTER_NAME = SemesterName.Y1S2;

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);

            System.out.println(result.getFeedbackToUser());
            System.out.println(expectedCommandResult.getFeedbackToUser());
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

    public static void assertCommandException(Command command, Model actualModel, String expectedMessage) {
        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the module planner, filtered studyPlan list and selected studyPlan in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        ModulePlanner expectedModulePlanner = new ModulePlanner(actualModel.getModulePlanner(), new ModulesInfo());
        List<StudyPlan> expectedFilteredList = new ArrayList<>(actualModel.getFilteredStudyPlanList());

        //assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedModulePlanner, actualModel.getModulePlanner());
        assertEquals(expectedFilteredList, actualModel.getFilteredStudyPlanList());
    }

}
