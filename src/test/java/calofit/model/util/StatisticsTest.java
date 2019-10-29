package calofit.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.SortedMap;
import java.util.TreeMap;

import calofit.model.CalorieBudget;
import calofit.model.meal.MealLog;
import calofit.testutil.TypicalMeals;

public class StatisticsTest {

    @Test
    public void statistics_math_calculation_valid() {

        MealLog mockMealLog = Mockito.mock(MealLog.class);
        CalorieBudget mockCalorieBudget = Mockito.mock(CalorieBudget.class);
        SortedMap<LocalDate, Integer> tempMap = new TreeMap<>();
        tempMap.put(LocalDate.now(), 1000);
        Mockito.doReturn(TypicalMeals.getTypicalMealsObservableListOfSameMonth())
                .when(mockMealLog).getCurrentMonthMeals();
        Mockito.doReturn(tempMap).when(mockCalorieBudget).getCurrentMonthBudgetHistory();

        Statistics test = Statistics.generateStatistics(mockMealLog, mockCalorieBudget);

        assertEquals(test.getAverage(),
                (TypicalMeals.SPAGHETTI.getDish().getCalories().getValue() +
                TypicalMeals.MUSHROOM_SOUP.getDish().getCalories().getValue() +
                TypicalMeals.CHICKEN_RICE.getDish().getCalories().getValue()) / LocalDate.now().lengthOfMonth());

        assertEquals(test.getMaximum(),
                 TypicalMeals.SPAGHETTI.getDish().getCalories().getValue() +
                        TypicalMeals.MUSHROOM_SOUP.getDish().getCalories().getValue() +
                        TypicalMeals.CHICKEN_RICE.getDish().getCalories().getValue());

        assertEquals(test.getMinimum(), 0);

        assertEquals(test.getCalorieExceedCount(), 1);

        assertTrue(test.getMostConsumedDishes().contains(TypicalMeals.SPAGHETTI.getDish()));
        assertTrue(test.getMostConsumedDishes().contains(TypicalMeals.MUSHROOM_SOUP.getDish()));
        assertTrue(test.getMostConsumedDishes().contains(TypicalMeals.CHICKEN_RICE.getDish()));
    }

}
