package seedu.tarence.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tarence.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tarence.testutil.TypicalIndexes.INDEX_FIRST_IN_LIST;
import static seedu.tarence.testutil.TypicalStudents.getTypicalApplication;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.tarence.commons.core.Messages;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.ModelManager;
import seedu.tarence.model.UserPrefs;
import seedu.tarence.model.builder.ModuleBuilder;
import seedu.tarence.model.builder.TutorialBuilder;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;

/**
 * Contains integration tests (interaction with the Model) and unit tests for DisplayAttendanceCommand.
 */
public class DisplayAttendanceCommandTest {
    public static final String VALID_MODCODE = "GET1029";
    public static final String VALID_TUTNAME = "WhyIsThisClassAt8am";
    public static final String SIMILAR_MODCODE = "GET1028";
    public static final String SIMILAR_TUTNAME = "WhyIsThisClassAt8pm";
    public static final String VALID_MODCODE_2 = "GET1000";
    public static final String VALID_TUTNAME_2 = "WhyIsThisClassAt9pm";
    private Model model = new ModelManager(getTypicalApplication(), new UserPrefs());

    @Test
    public void execute_constructor_showsSameSuccessOutput() {
        TutorialBuilder.DEFAULT_STUDENTS.clear();
        Tutorial tutorialToDisplay =
                new TutorialBuilder().withModCode(VALID_MODCODE_2).withTutName(VALID_TUTNAME_2).build();
        model.addTutorial(tutorialToDisplay);

        DisplayAttendanceCommand displayAttendanceCommand =
                new DisplayAttendanceCommand(tutorialToDisplay.getModCode(), tutorialToDisplay.getTutName());
        Model expectedModel = new ModelManager(model.getApplication(), new UserPrefs());

        assertCommandSuccess(displayAttendanceCommand, model, DisplayAttendanceCommand.MESSAGE_SUCCESS, expectedModel);

        DisplayAttendanceCommand displayAttendanceCommandIndex =
                new DisplayAttendanceCommand(INDEX_FIRST_IN_LIST);

        assertCommandSuccess(
                displayAttendanceCommandIndex, model, DisplayAttendanceCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_similarTutorialName_showSuggestedCommands() throws CommandException {
        model = new ModelManager(getTypicalApplication(), new UserPrefs());
        TutorialBuilder.DEFAULT_STUDENTS.clear();
        Tutorial similarTutorial =
                new TutorialBuilder().withModCode(VALID_MODCODE).withTutName(SIMILAR_TUTNAME).build();
        Module validModule = new ModuleBuilder().withModCode(VALID_MODCODE).withTutorials(new ArrayList<>()).build();
        model.addModule(validModule);
        model.addTutorial(similarTutorial);
        validModule.addTutorial(similarTutorial);

        DisplayAttendanceCommand displayAttendanceCommand =
                new DisplayAttendanceCommand(new ModCode(VALID_MODCODE), new TutName(VALID_TUTNAME));

        CommandResult commandResult = displayAttendanceCommand.execute(model);
        String expectedMessage = String.format(Messages.MESSAGE_SUGGESTED_CORRECTIONS, "Tutorial",
                VALID_MODCODE + " " + VALID_TUTNAME)
                + "1. " + VALID_MODCODE + ", " + SIMILAR_TUTNAME + "\n";

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        ModCode modCode = new ModCode(VALID_MODCODE);
        TutName tutName = new TutName(VALID_TUTNAME);
        DisplayAttendanceCommand displayAttendanceCommand =
                new DisplayAttendanceCommand(modCode, tutName);

        // same object -> returns true
        assertTrue(displayAttendanceCommand.equals(displayAttendanceCommand));

        // different types -> returns false
        assertFalse(displayAttendanceCommand.equals(1));

        // null -> returns false
        assertFalse(displayAttendanceCommand.equals(null));

        // same values -> returns true
        ModCode sameModCode = new ModCode(VALID_MODCODE);
        TutName sameTutName = new TutName(VALID_TUTNAME);
        DisplayAttendanceCommand sameDisplayAttendanceCommand =
                new DisplayAttendanceCommand(sameModCode, sameTutName);
        assertTrue(displayAttendanceCommand.equals(sameDisplayAttendanceCommand));

        // different value -> returns false

        ModCode diffModCode = new ModCode(VALID_MODCODE_2);
        TutName diffTutName = new TutName(VALID_TUTNAME_2);

        DisplayAttendanceCommand diffDisplayAttendanceCommand =
                new DisplayAttendanceCommand(diffModCode, diffTutName);
        assertFalse(displayAttendanceCommand.equals(diffDisplayAttendanceCommand));
    }

    @Test
    public void execute_differentCommandWord_showSuccess() {
        String validCommand = "diSplAyAtTenDaNce";
        String validCommand2 = "DISPLAYATTENDANCE";
        String validCommand3 = "DisplayAtt";

        ModCode modCode = new ModCode(VALID_MODCODE);
        TutName tutName = new TutName(VALID_TUTNAME);
        DisplayAttendanceCommand displayAttendanceCommand =
                new DisplayAttendanceCommand(modCode, tutName);

        // Correct word
        assertTrue(displayAttendanceCommand.isMatchingCommandWord(validCommand));
        assertTrue(displayAttendanceCommand.isMatchingCommandWord(validCommand2));
        assertTrue(displayAttendanceCommand.isMatchingCommandWord(validCommand3));

        // Incorrect word
        String invalidCommand = "DISPLAY";

        assertFalse(displayAttendanceCommand.isMatchingCommandWord(invalidCommand));
    }
}
