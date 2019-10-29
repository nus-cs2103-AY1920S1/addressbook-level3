package seedu.tarence.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tarence.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tarence.logic.commands.DisplayFormat.GRAPH;
import static seedu.tarence.testutil.TypicalIndexes.INDEX_FIRST_IN_LIST;
import static seedu.tarence.testutil.TypicalIndexes.INDEX_SECOND_IN_LIST;
import static seedu.tarence.testutil.TypicalIndexes.INDEX_THIRD_IN_LIST;
import static seedu.tarence.testutil.TypicalStudents.getTypicalApplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.ModelManager;
import seedu.tarence.model.UserPrefs;
import seedu.tarence.model.builder.TutorialBuilder;
import seedu.tarence.model.tutorial.Assignment;
import seedu.tarence.model.tutorial.Tutorial;

/**
 * Contains integration tests (interaction with the Model) and unit tests for DisplayAssignmentScoreCommand.
 */
public class DisplayAssignmentScoreCommandTest {
    public static final String VALID_MODCODE_2 = "GET1000";
    public static final String VALID_TUTNAME_2 = "WhyIsThisClassAt9pm";
    public static final Assignment VALID_ASSIGNMENT =
            new Assignment("Dummy assignment", 10,
                    new Date(1991 / 1 / 1), new Date(1992 / 1 / 1));

    private Model model = new ModelManager(getTypicalApplication(), new UserPrefs());

    @Test
    public void execute_constructor_showsSameSuccessOutput() {
        List<Assignment> assignments = new ArrayList<>();
        assignments.add(VALID_ASSIGNMENT);

        TutorialBuilder.DEFAULT_STUDENTS.clear();
        Tutorial tutorialToDisplay =
                new TutorialBuilder().withModCode(VALID_MODCODE_2).withTutName(VALID_TUTNAME_2)
                        .withAssignments(assignments).build();
        model.addTutorial(tutorialToDisplay);

        DisplayAssignmentScoreCommand displayAssignmentScoreCommand =
                new DisplayAssignmentScoreCommand(INDEX_FIRST_IN_LIST, "Dummy assignment",
                        DisplayFormat.TABLE);

        Model expectedModel = new ModelManager(model.getApplication(), new UserPrefs());

        assertCommandSuccess(displayAssignmentScoreCommand, model,
                DisplayAssignmentScoreCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_constructor_throwsCommandException() {
        List<Assignment> assignments = new ArrayList<>();
        assignments.add(VALID_ASSIGNMENT);

        TutorialBuilder.DEFAULT_STUDENTS.clear();
        Tutorial tutorialToDisplay =
                new TutorialBuilder().withModCode(VALID_MODCODE_2).withTutName(VALID_TUTNAME_2)
                        .withAssignments(assignments).build();
        model.addTutorial(tutorialToDisplay);

        // Display out of bounds tutorial index
        DisplayAssignmentScoreCommand displayInvalidIndexAssignmentScoreCommand =
                new DisplayAssignmentScoreCommand(INDEX_THIRD_IN_LIST, "Dummy assignment",
                        DisplayFormat.TABLE);

        assertThrows(CommandException.class, () -> displayInvalidIndexAssignmentScoreCommand.execute(model));

        // Display invalid assignment name
        DisplayAssignmentScoreCommand displayInvalidNameAssignmentScoreCommand =
                new DisplayAssignmentScoreCommand(INDEX_THIRD_IN_LIST, "invalid name",
                        DisplayFormat.TABLE);

        assertThrows(CommandException.class, () -> displayInvalidNameAssignmentScoreCommand.execute(model));
    }

    @Test
    public void equals() {
        DisplayFormat displayFormat = DisplayFormat.TABLE;
        DisplayAssignmentScoreCommand displayAssignmentScoreCommand =
                new DisplayAssignmentScoreCommand(INDEX_FIRST_IN_LIST, "Assignment", displayFormat);

        // same object -> returns true
        assertTrue(displayAssignmentScoreCommand.equals(displayAssignmentScoreCommand));

        // different types -> returns false
        assertFalse(displayAssignmentScoreCommand.equals(1));

        // null -> returns false
        assertFalse(displayAssignmentScoreCommand.equals(null));

        // same values -> returns true
        DisplayAssignmentScoreCommand displayAssignmentScoreCommand2 =
                new DisplayAssignmentScoreCommand(INDEX_FIRST_IN_LIST, "Assignment", displayFormat);
        assertEquals(displayAssignmentScoreCommand, displayAssignmentScoreCommand2);

        // different Index -> returns false
        DisplayAssignmentScoreCommand diffIndexDisplayAssignmentScoreCommand =
                new DisplayAssignmentScoreCommand(INDEX_SECOND_IN_LIST, "Assignment", displayFormat);
        assertFalse(displayAssignmentScoreCommand.equals(diffIndexDisplayAssignmentScoreCommand));

        // different Name -> returns false
        DisplayAssignmentScoreCommand diffNameDisplayAssignmentScoreCommand =
                new DisplayAssignmentScoreCommand(INDEX_FIRST_IN_LIST, "Assignment2", displayFormat);
        assertFalse(displayAssignmentScoreCommand.equals(diffNameDisplayAssignmentScoreCommand));

        // different Format -> returns false
        DisplayAssignmentScoreCommand diffFormatDisplayAssignmentScoreCommand =
                new DisplayAssignmentScoreCommand(INDEX_FIRST_IN_LIST, "Assignment", GRAPH);
        assertFalse(displayAssignmentScoreCommand.equals(diffFormatDisplayAssignmentScoreCommand));
    }
    @Test
    public void execute_differentCommandWord_showSuccess() {
        String validCommand = "diSplAyAssignMent";
        String validCommand2 = "displayscore".toUpperCase();
        String validCommand3 = "displayAssign";

        // Correct word
        assertTrue(DisplayAssignmentScoreCommand.isMatchingCommandWord(validCommand));
        assertTrue(DisplayAssignmentScoreCommand.isMatchingCommandWord(validCommand2));
        assertTrue(DisplayAssignmentScoreCommand.isMatchingCommandWord(validCommand3));

        // Incorrect word
        String invalidCommand = "DISPLAY";

        assertFalse(DisplayAssignmentScoreCommand.isMatchingCommandWord(invalidCommand));
    }
}

