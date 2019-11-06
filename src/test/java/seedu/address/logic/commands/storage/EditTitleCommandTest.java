package seedu.address.logic.commands.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalModulesInfo.getTypicalModulesInfo;
import static seedu.address.testutil.TypicalStudyPlans.getTypicalModulePlanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code EditTitleCommand}.
 */
public class EditTitleCommandTest {

    private static final String VALID_TITLE_1 = "good title";
    private static final String VALID_TITLE_2 = "better title";
    private static final String INVALID_TITLE_NON_ASCII = "学习计划"; // non-ascii
    private static final String INVALID_TITLE_OVER_20_CHARS = "qwertyuiopasdfghjklzxcvbnm"; // more than 20 characters

    private Model model;
    private String newTitle = "new title";

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
    }

    @Test
    public void execute_newStudyPlan_success() throws CommandException {

        Model expectedModel = new ModelManager(model.getModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
        expectedModel.changeActiveStudyPlanTitle(newTitle);

        EditTitleCommand command = new EditTitleCommand(newTitle);
        CommandResult expectedResult =
                new CommandResult(String.format(EditTitleCommand.MESSAGE_EDIT_STUDY_PLAN_SUCCESS, newTitle),
                        true, false);

        // check command result
        CommandResult actualResult = command.execute(model);
        assertEquals(expectedResult, actualResult);
        // check study plans
        assertEquals(expectedModel.getActiveStudyPlan(), model.getActiveStudyPlan());
    }

    @Test
    public void execute_invalidTitleNonAscii_throwsCommandException() throws CommandException {
        EditTitleCommand command = new EditTitleCommand(INVALID_TITLE_NON_ASCII);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_invalidTitleExceedCharacters_throwsCommandException() throws CommandException {
        EditTitleCommand command = new EditTitleCommand(INVALID_TITLE_OVER_20_CHARS);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void equals() {
        EditTitleCommand command1 = new EditTitleCommand(VALID_TITLE_1);
        EditTitleCommand command2 = new EditTitleCommand(VALID_TITLE_2);

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        EditTitleCommand command3 = new EditTitleCommand(VALID_TITLE_1);
        assertTrue(command1.equals(command3));

        // different types -> returns false
        assertFalse(command1.equals(1));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different commit messages -> returns false
        assertFalse(command1.equals(command2));
    }

}
