package seedu.exercise.logic.commands;

import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_EXERCISE_COMPARATOR;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_REGIME_COMPARATOR;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_SCHEDULE_COMPARATOR;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.getTypicalExerciseBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.logic.commands.statistic.StatsCommand;
import seedu.exercise.logic.commands.statistic.StatsFactory;
import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.UserPrefs;
import seedu.exercise.model.property.Date;
import seedu.exercise.model.resource.Exercise;

public class StatsCommandTest {

    private static final String CATEGORY = "calories";
    private static final String CHART = "linechart";
    private static final Date START_DATE = new Date("25/09/2019");
    private static final Date END_DATE = new Date("27/09/2019");

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalExerciseBook(),
                new ReadOnlyResourceBook<>(DEFAULT_REGIME_COMPARATOR),
                new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR),
                new ReadOnlyResourceBook<>(DEFAULT_SCHEDULE_COMPARATOR),
                new UserPrefs());

        expectedModel = new ModelManager(
                new ReadOnlyResourceBook<>(model.getExerciseBookData(), DEFAULT_EXERCISE_COMPARATOR),
                new ReadOnlyResourceBook<>(DEFAULT_REGIME_COMPARATOR),
                new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR),
                new ReadOnlyResourceBook<>(DEFAULT_SCHEDULE_COMPARATOR),
                new UserPrefs());
    }

    @Test
    public void execute_setStatistic_success() {
        ReadOnlyResourceBook<Exercise> exercises = model.getExerciseBookData();
        StatsFactory statsFactory = new StatsFactory(exercises, CHART, CATEGORY, START_DATE, END_DATE);

        expectedModel.setStatistic(statsFactory.generateStatistic());
        StatsCommand statsCommand = new StatsCommand(CHART, CATEGORY, START_DATE, END_DATE);
        String expectedMessage = StatsCommand.MESSAGE_STATS_DISPLAY_SUCCESS;

        assertCommandSuccess(statsCommand, model, expectedMessage, expectedModel);
    }
}
