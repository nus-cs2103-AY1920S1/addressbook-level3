package seedu.exercise.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.exercise.testutil.CommonTestData.VALID_BAR_CHART;
import static seedu.exercise.testutil.CommonTestData.VALID_LINE_CHART;
import static seedu.exercise.testutil.CommonTestData.VALID_PIE_CHART;
import static seedu.exercise.testutil.CommonTestData.VALID_STATISTIC_CATEGORY_CALORIES;
import static seedu.exercise.testutil.CommonTestData.VALID_STATISTIC_CATEGORY_EXERCISE;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.CYCLING;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.SWIMMING;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.logic.commands.statistic.Statistic;
import seedu.exercise.logic.commands.statistic.StatsFactory;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.property.Date;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.testutil.builder.ExerciseBookBuilder;
import seedu.exercise.testutil.builder.ExerciseBuilder;
import seedu.exercise.testutil.builder.StatisticBuilder;

//@@author jietung
public class StatsFactoryTest {

    private static final String START_DATE = "25/09/2019";
    private static final String END_DATE = "29/09/2019";
    private static final double TOTAL_CALORIES = 672.0;
    private static final double AVERAGE_CALORIES = 134.4;
    private static final double TOTAL_EXERCISE = 4.0;
    private static final double AVERAGE_EXERCISE = 0.8;
    private ReadOnlyResourceBook<Exercise> eb;

    @BeforeEach
    public void setUp() {
        Exercise running = new ExerciseBuilder().build(); //Running, 111 kcal, 0.5 hours
        Exercise anotherRunning = new ExerciseBuilder().withDate("28/09/2019").withQuantity("1.0").build();
        eb = new ExerciseBookBuilder().withExercise(running).withExercise(SWIMMING)
                .withExercise(CYCLING).withExercise(anotherRunning).build();
    }

    @Test
    public void generateLineChartStatistic() {
        String[] dates = new String[]{"25/09/2019", "26/09/2019", "27/09/2019", "28/09/2019", "29/09/2019"};
        ArrayList<String> expectedLineChartProperties = new ArrayList<>(Arrays.asList(dates));
        //======calories================================================================================================
        StatsFactory caloriesStatsFactory = new StatsFactory(eb, VALID_LINE_CHART, VALID_STATISTIC_CATEGORY_CALORIES,
                new Date(START_DATE), new Date(END_DATE));
        Statistic actualCaloriesStatistic = caloriesStatsFactory.generateStatistic();

        Integer[] caloriesArr = new Integer[]{0, 111, 300, 261, 0};
        ArrayList<Integer> expectedCaloriesValues = new ArrayList<>(Arrays.asList(caloriesArr));

        Statistic expectedCaloriesStatistic = new StatisticBuilder().withCategory(VALID_STATISTIC_CATEGORY_CALORIES)
                .withChart(VALID_LINE_CHART).withStartDate(new Date(START_DATE)).withEndDate(new Date(END_DATE))
                .withProperties(expectedLineChartProperties).withValues(expectedCaloriesValues)
                .withTotal(TOTAL_CALORIES).withAverage(AVERAGE_CALORIES).build();

        assertEquals(expectedCaloriesStatistic, actualCaloriesStatistic);

        //======exercise================================================================================================
        StatsFactory exerciseStatsFactory = new StatsFactory(eb, VALID_LINE_CHART, VALID_STATISTIC_CATEGORY_EXERCISE,
                new Date(START_DATE), new Date(END_DATE));
        Statistic actualExerciseStatistic = exerciseStatsFactory.generateStatistic();

        Integer[] valuesArr = new Integer[]{0, 1, 1, 2, 0};
        ArrayList<Integer> expectedExerciseValues = new ArrayList<>(Arrays.asList(valuesArr));

        Statistic expectedExerciseStatistic = new StatisticBuilder().withCategory(VALID_STATISTIC_CATEGORY_EXERCISE)
                .withChart(VALID_LINE_CHART).withStartDate(new Date(START_DATE)).withEndDate(new Date(END_DATE))
                .withProperties(expectedLineChartProperties).withValues(expectedExerciseValues)
                .withTotal(TOTAL_EXERCISE).withAverage(AVERAGE_EXERCISE).build();

        assertEquals(expectedExerciseStatistic, actualExerciseStatistic);

    }

    @Test
    public void generateBarChartStatistic() {
        String[] propertiesArr = new String[]{"Cycling (km)", "Running (hours)", "Swimming (laps)"};
        ArrayList<String> expectedProperties = new ArrayList<>(Arrays.asList(propertiesArr));
        //=====calories=================================================================================================
        StatsFactory caloriesStatsFactory = new StatsFactory(eb, VALID_BAR_CHART, VALID_STATISTIC_CATEGORY_CALORIES,
                new Date(START_DATE), new Date(END_DATE));
        Statistic actualCaloriesStatistic = caloriesStatsFactory.generateStatistic();

        Integer[] caloriesArr = new Integer[]{150, 222, 300};
        ArrayList<Integer> expectedCalories = new ArrayList<>(Arrays.asList(caloriesArr));

        Statistic expectedCaloriesStatistic = new StatisticBuilder().withCategory(VALID_STATISTIC_CATEGORY_CALORIES)
                .withChart(VALID_BAR_CHART).withStartDate(new Date(START_DATE)).withEndDate(new Date(END_DATE))
                .withProperties(expectedProperties).withValues(expectedCalories)
                .withTotal(TOTAL_CALORIES).withAverage(AVERAGE_CALORIES).build();

        assertEquals(expectedCaloriesStatistic, actualCaloriesStatistic);

        //=====exercise=================================================================================================
        StatsFactory exerciseStatsFactory = new StatsFactory(eb, VALID_BAR_CHART, VALID_STATISTIC_CATEGORY_EXERCISE,
                new Date(START_DATE), new Date(END_DATE));
        Statistic actualExerciseStatistic = exerciseStatsFactory.generateStatistic();

        Integer[] valuesArr = new Integer[]{1, 2, 1};
        ArrayList<Integer> expectedValues = new ArrayList<>(Arrays.asList(valuesArr));

        Statistic expectedExerciseStatistic = new StatisticBuilder().withCategory(VALID_STATISTIC_CATEGORY_EXERCISE)
                .withChart(VALID_BAR_CHART).withStartDate(new Date(START_DATE)).withEndDate(new Date(END_DATE))
                .withProperties(expectedProperties).withValues(expectedValues)
                .withTotal(TOTAL_EXERCISE).withAverage(AVERAGE_EXERCISE).build();

        assertEquals(expectedExerciseStatistic, actualExerciseStatistic);
    }

    @Test
    public void generatePieChartStatistic() {
        String[] propertiesArr = new String[]{"Cycling (km)", "Running (hours)", "Swimming (laps)"};
        ArrayList<String> expectedProperties = new ArrayList<>(Arrays.asList(propertiesArr));
        //=====calories=================================================================================================
        StatsFactory caloriesStatsFactory = new StatsFactory(eb, VALID_PIE_CHART, VALID_STATISTIC_CATEGORY_CALORIES,
                new Date(START_DATE), new Date(END_DATE));
        Statistic actualCaloriesStatistic = caloriesStatsFactory.generateStatistic();

        Integer[] caloriesArr = new Integer[]{150, 222, 300};
        ArrayList<Integer> expectedCalories = new ArrayList<>(Arrays.asList(caloriesArr));

        Statistic expectedCaloriesStatistic = new StatisticBuilder().withCategory(VALID_STATISTIC_CATEGORY_CALORIES)
                .withChart(VALID_PIE_CHART).withStartDate(new Date(START_DATE)).withEndDate(new Date(END_DATE))
                .withProperties(expectedProperties).withValues(expectedCalories)
                .withTotal(TOTAL_CALORIES).withAverage(AVERAGE_CALORIES).build();

        assertEquals(expectedCaloriesStatistic, actualCaloriesStatistic);

        //=====exercise=================================================================================================
        StatsFactory exerciseStatsFactory = new StatsFactory(eb, VALID_PIE_CHART, VALID_STATISTIC_CATEGORY_EXERCISE,
                new Date(START_DATE), new Date(END_DATE));
        Statistic actualExerciseStatistic = exerciseStatsFactory.generateStatistic();

        Integer[] valuesArr = new Integer[]{1, 2, 1};
        ArrayList<Integer> expectedValues = new ArrayList<>(Arrays.asList(valuesArr));

        Statistic expectedExerciseStatistic = new StatisticBuilder().withCategory(VALID_STATISTIC_CATEGORY_EXERCISE)
                .withChart(VALID_PIE_CHART).withStartDate(new Date(START_DATE)).withEndDate(new Date(END_DATE))
                .withProperties(expectedProperties).withValues(expectedValues)
                .withTotal(TOTAL_EXERCISE).withAverage(AVERAGE_EXERCISE).build();

        assertEquals(expectedExerciseStatistic, actualExerciseStatistic);
    }
}
