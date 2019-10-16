package seedu.tarence.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tarence.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tarence.testutil.TypicalStudents.getTypicalApplication;

import org.junit.jupiter.api.Test;

import seedu.tarence.model.Model;
import seedu.tarence.model.ModelManager;
import seedu.tarence.model.UserPrefs;
import seedu.tarence.model.builder.TutorialBuilder;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;

/**
 * Contains integration tests (interaction with the Model) and unit tests for DisplayAttendanceCommand.
 */
public class DisplayAttendanceCommandTest {
    public static final String VALID_MODCODE = "GET1029";
    public static final String VALID_TUTNAME = "WhyIsThisClassAt8am";
    public static final String VALID_MODCODE_2 = "GET1000";
    public static final String VALID_TUTNAME_2 = "WhyIsThisClassAt9pm";
    private Model model = new ModelManager(getTypicalApplication(), new UserPrefs());

    @Test
    public void execute_constructor_showsSameSuccessOutput() {
        TutorialBuilder.DEFAULT_STUDENTS.clear();
        Tutorial tutorialToDisplay =
                new TutorialBuilder().withModCode(VALID_MODCODE).withTutName(VALID_TUTNAME).build();
        model.addTutorial(tutorialToDisplay);

        DisplayAttendanceCommand displayAttendanceCommand =
                new DisplayAttendanceCommand(tutorialToDisplay.getModCode(), tutorialToDisplay.getTutName());
        Model expectedModel = new ModelManager(model.getApplication(), new UserPrefs());

        assertCommandSuccess(displayAttendanceCommand, model, DisplayAttendanceCommand.MESSAGE_SUCCESS, expectedModel);
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

        ModCode modCode = new ModCode(VALID_MODCODE);
        TutName tutName = new TutName(VALID_TUTNAME);
        DisplayAttendanceCommand displayAttendanceCommand =
                new DisplayAttendanceCommand(modCode, tutName);

        // Correct word
        assertTrue(displayAttendanceCommand.isMatchingCommandWord(validCommand));
        assertTrue(displayAttendanceCommand.isMatchingCommandWord(validCommand2));

        // Incorrect word
        String invalidCommand1 = "DISPLAY";
        String invalidCommand2 = "DisplayAtt";

        assertFalse(displayAttendanceCommand.isMatchingCommandWord(invalidCommand1));
        assertFalse(displayAttendanceCommand.isMatchingCommandWord(invalidCommand2));
    }
}
