package seedu.exercise.logic.commands;

import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.exercise.model.util.DefaultPropertyBookUtil.getDefaultPropertyBook;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.getTypicalExerciseBook;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.UserPrefs;
import seedu.exercise.model.property.Muscle;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.ui.ListResourceType;

public class SuggestPossibleCommandTest {

    private Model model;
    private Model expectedModel;
    private Set<Muscle> targetMuscles;
    private Map<String, String> targetCustomProperties;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalExerciseBook(), new ReadOnlyResourceBook<>(),
                getTypicalExerciseBook(), new ReadOnlyResourceBook<>(),
                new UserPrefs(), getDefaultPropertyBook());
        expectedModel = new ModelManager(model.getExerciseBookData(), new ReadOnlyResourceBook<>(),
                getTypicalExerciseBook(), new ReadOnlyResourceBook<>(),
                new UserPrefs(), getDefaultPropertyBook());
        targetMuscles = new HashSet<>();
        targetCustomProperties = new HashMap<>();
    }

    @Test
    public void execute_suggestPossible_success() {
        expectedModel.updateSuggestedExerciseList(getPredicate());
        String expectedMessage = SuggestPossibleCommand.MESSAGE_SUCCESS;

        CommandResult expectedCommandResult = new CommandResult(expectedMessage, ListResourceType.SUGGESTION);
        assertCommandSuccess(new SuggestPossibleCommand(targetMuscles, targetCustomProperties),
                model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_suggestPossibleMuscle_success() {
        Muscle chest = new Muscle("Chest");
        targetMuscles.add(chest);
        expectedModel.updateSuggestedExerciseList(getPredicate());
        String expectedMessage = SuggestPossibleCommand.MESSAGE_SUCCESS;

        CommandResult expectedCommandResult = new CommandResult(expectedMessage, ListResourceType.SUGGESTION);
        assertCommandSuccess(new SuggestPossibleCommand(targetMuscles, targetCustomProperties),
                model, expectedCommandResult, expectedModel);
    }

    private Predicate<Exercise> getMusclePredicate() {
        return exercise -> {
            for (Muscle muscle : targetMuscles) {
                if (!(exercise.getMuscles().contains(muscle))) {
                    return false;
                }
            }
            return true;
        };
    }

    private Predicate<Exercise> getCustomPropertyPredicate() {
        return exercise -> {
            for (String key : targetCustomProperties.keySet()) {
                if (!(targetCustomProperties.get(key).equals(exercise.getCustomProperties().get(key)))) {
                    return false;
                }
            }
            return true;
        };
    }

    private Predicate<Exercise> getPredicate() {
        return getMusclePredicate().and(getCustomPropertyPredicate());
    }

}
