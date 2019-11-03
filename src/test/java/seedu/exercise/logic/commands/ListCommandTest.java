package seedu.exercise.logic.commands;

import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.getTypicalExerciseBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.UserPrefs;
import seedu.exercise.ui.ListResourceType;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalExerciseBook(), new ReadOnlyResourceBook<>(),
            new ReadOnlyResourceBook<>(), new ReadOnlyResourceBook<>(), new UserPrefs());
        expectedModel = new ModelManager(model.getExerciseBookData(), new ReadOnlyResourceBook<>(),
            new ReadOnlyResourceBook<>(), new ReadOnlyResourceBook<>(), new UserPrefs());
    }

    @Test
    public void execute_exerciseListNotFiltered_showsAllExercises() {
        ListResourceType listResourceType = ListResourceType.EXERCISE;
        CommandResult expectedCommandResult = new CommandResult(
            String.format(ListCommand.MESSAGE_SUCCESS, listResourceType.toString().toLowerCase()),
            listResourceType);
        assertCommandSuccess(new ListCommand(listResourceType), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_exerciseListFiltered_showsFilteredExercises() {
        ListResourceType listResourceType = ListResourceType.EXERCISE;
        CommandResult expectedCommandResult = new CommandResult(
            String.format(ListCommand.MESSAGE_SUCCESS, listResourceType.toString().toLowerCase()),
            listResourceType);
        assertCommandSuccess(new ListCommand(listResourceType), model, expectedCommandResult, expectedModel);
    }
}
