package calofit.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.SortedMap;
import java.util.TreeMap;

import javafx.collections.ObservableList;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import calofit.model.CalorieBudget;
import calofit.model.meal.Meal;
import calofit.model.meal.MealLog;
import calofit.testutil.TypicalMeals;

public class StatisticsTest {

    @Test
    public void statistical_calculation_valid() {

        MealLog mockMealLog = Mockito.mock(MealLog.class);
        CalorieBudget mockCalorieBudget = Mockito.mock(CalorieBudget.class);
        SortedMap<LocalDate, Integer> tempMap = new TreeMap<>();
        tempMap.put(LocalDate.now(), 1000);
        Mockito.doReturn(TypicalMeals.getTypicalMealsObservableListOfSameMonth())
                .when(mockMealLog).getCurrentMonthMeals();
        Mockito.doReturn(tempMap).when(mockCalorieBudget).getCurrentMonthBudgetHistory();

        Statistics test = Statistics.generateStatistics(mockMealLog.getCurrentMonthMeals(), mockCalorieBudget);

        assertEquals(Math.round(((double) (
                TypicalMeals.SPAGHETTI.getDish().getCalories().getValue()
                        + TypicalMeals.MUSHROOM_SOUP.getDish().getCalories().getValue()
                        + TypicalMeals.CHICKEN_RICE.getDish().getCalories().getValue()))
                / ((double) LocalDate.now().lengthOfMonth())), test.getAverage());

        assertEquals(TypicalMeals.SPAGHETTI.getDish().getCalories().getValue()
                        + TypicalMeals.MUSHROOM_SOUP.getDish().getCalories().getValue()
                        + TypicalMeals.CHICKEN_RICE.getDish().getCalories().getValue(), test.getMaximum());

        assertEquals(0, test.getMinimum());

        assertEquals(1, test.getCalorieExceedCount());

        assertTrue(test.getMostConsumedDishes().contains(TypicalMeals.SPAGHETTI.getDish()));
        assertTrue(test.getMostConsumedDishes().contains(TypicalMeals.MUSHROOM_SOUP.getDish()));
        assertTrue(test.getMostConsumedDishes().contains(TypicalMeals.CHICKEN_RICE.getDish()));

        assertEquals("Spaghetti", test.getFoodChartSeries().getData().get(0).getXValue());
        assertEquals("Chicken Rice", test.getFoodChartSeries().getData().get(1).getXValue());
        assertEquals("Mushroom Soup", test.getFoodChartSeries().getData().get(2).getXValue());

        assertEquals(1, test.getFoodChartSeries().getData().get(0).getYValue());
        assertEquals(1, test.getFoodChartSeries().getData().get(0).getYValue());
        assertEquals(1, test.getFoodChartSeries().getData().get(0).getYValue());

        for (int i = 0; i < LocalDate.now().lengthOfMonth(); i++) {
            assertEquals(String.valueOf(i + 1),
                    test.getCalorieChartSeries().getData().get(i).getXValue());
            if (i + 1 == LocalDateTime.now().getDayOfMonth()) {
                assertEquals(TypicalMeals.SPAGHETTI.getDish().getCalories().getValue()
                                + TypicalMeals.MUSHROOM_SOUP.getDish().getCalories().getValue()
                                + TypicalMeals.CHICKEN_RICE.getDish().getCalories().getValue(),
                        test.getCalorieChartSeries().getData().get(i).getYValue());

            } else {
                assertEquals(0, test.getCalorieChartSeries().getData().get(i).getYValue());
            }
        }
    }

    @Test
    public void testNullBudget() {
        ObservableList<Meal> meals = TypicalMeals.getTypicalMealsObservableList();
        assertThrows(NullPointerException.class, () ->
            Statistics.generateStatistics(meals, null));
    }
}
