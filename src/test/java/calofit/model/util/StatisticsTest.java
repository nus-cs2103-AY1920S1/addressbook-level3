package calofit.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.SortedMap;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import calofit.model.CalorieBudget;
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

        assertEquals(test.getAverage(), Math.round(((double) (
                TypicalMeals.SPAGHETTI.getDish().getCalories().getValue()
                        + TypicalMeals.MUSHROOM_SOUP.getDish().getCalories().getValue()
                        + TypicalMeals.CHICKEN_RICE.getDish().getCalories().getValue()))
                / ((double) LocalDate.now().lengthOfMonth())));

        assertEquals(test.getMaximum(),
                 TypicalMeals.SPAGHETTI.getDish().getCalories().getValue()
                        + TypicalMeals.MUSHROOM_SOUP.getDish().getCalories().getValue()
                        + TypicalMeals.CHICKEN_RICE.getDish().getCalories().getValue());

        assertEquals(test.getMinimum(), 0);

        assertEquals(test.getCalorieExceedCount(), 1);

        assertTrue(test.getMostConsumedDishes().contains(TypicalMeals.SPAGHETTI.getDish()));
        assertTrue(test.getMostConsumedDishes().contains(TypicalMeals.MUSHROOM_SOUP.getDish()));
        assertTrue(test.getMostConsumedDishes().contains(TypicalMeals.CHICKEN_RICE.getDish()));

        assertEquals(test.getPieChartData().get(0).getName(), "Spaghetti\nNumber of times eaten: 1\n");
        assertEquals(test.getPieChartData().get(1).getName(), "Chicken Rice\nNumber of times eaten: 1\n");
        assertEquals(test.getPieChartData().get(2).getName(), "Mushroom Soup\nNumber of times eaten: 1\n");

        assertEquals(test.getPieChartData().get(0).getPieValue(), 1);
        assertEquals(test.getPieChartData().get(0).getPieValue(), 1);
        assertEquals(test.getPieChartData().get(0).getPieValue(), 1);

        for (int i = 0; i < LocalDate.now().lengthOfMonth(); i++) {
            assertEquals(test.getCalorieChartSeries().getData().get(i).getXValue(),
                    String.valueOf(i + 1));
            if (i + 1 == LocalDateTime.now().getDayOfMonth()) {
                assertEquals(test.getCalorieChartSeries().getData().get(i).getYValue(),
                        TypicalMeals.SPAGHETTI.getDish().getCalories().getValue()
                                + TypicalMeals.MUSHROOM_SOUP.getDish().getCalories().getValue()
                                + TypicalMeals.CHICKEN_RICE.getDish().getCalories().getValue());

            } else {
                assertEquals(test.getCalorieChartSeries().getData().get(i).getYValue(), 0);
            }
        }
    }

}
