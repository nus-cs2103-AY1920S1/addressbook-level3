package calofit.logic;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import calofit.model.Model;
import calofit.model.meal.Meal;
import calofit.model.meal.MealLog;
import calofit.model.util.Timestamp;
import calofit.testutil.TypicalDishes;

public class NotificationHelperTest {

    public static final LocalDateTime MISSING_BREAKFAST =
        LocalDateTime.of(2019, 11, 11, 10, 0, 0);
    public static final LocalDateTime BEFORE_LUNCH =
        LocalDateTime.of(2019, 11, 11, 9, 0, 0);
    public static final LocalDateTime MISSING_LUNCH =
        LocalDateTime.of(2019, 11, 11, 14, 0, 0);
    public static final LocalDateTime BEFORE_DINNER =
        LocalDateTime.of(2019, 11, 11, 15, 0, 0);
    public static final LocalDateTime MISSING_DINNER =
        LocalDateTime.of(2019, 11, 11, 20, 0, 0);
    private ObservableList<Meal> meals = FXCollections.observableArrayList();
    private NotificationHelper helper;
    private Model model;
    private MealLog log;

    @BeforeEach
    public void setup() {
        helper = new NotificationHelper(Clock.systemDefaultZone());
        model = Mockito.mock(Model.class);
        log = Mockito.mock(MealLog.class);
        Mockito.doReturn(log).when(model).getMealLog();
        Mockito.doReturn(meals).when(log).getTodayMeals();
    }

    @Test
    public void testEmptyMealLog() {
        Assertions.assertEquals(Optional.of(NotificationHelper.MESSAGE_BREAKFAST),
            helper.execute(model));
        Mockito.verify(model).getMealLog();
        Mockito.verify(log).getTodayMeals();
        Mockito.verifyNoMoreInteractions(model, log);
    }

    private Clock makeFakeTime(LocalDateTime fakeNow) {
        ZoneId zone = ZoneId.of("UTC");
        return Clock.fixed(fakeNow.toInstant(ZoneOffset.UTC), zone);
    }

    @Test
    public void testBreakfast() {
        helper = new NotificationHelper(makeFakeTime(MISSING_BREAKFAST));
        Assertions.assertEquals(Optional.of(NotificationHelper.MESSAGE_BREAKFAST),
            helper.execute(model));
        Mockito.verify(model).getMealLog();
        Mockito.verify(log).getTodayMeals();
        Mockito.verifyNoMoreInteractions(model, log);
    }

    @Test
    public void testLunch() {
        helper = new NotificationHelper(makeFakeTime(MISSING_LUNCH));
        meals.add(new Meal(TypicalDishes.APPLE_PIE, new Timestamp(BEFORE_LUNCH)));
        Assertions.assertEquals(Optional.of(NotificationHelper.MESSAGE_LUNCH),
            helper.execute(model));
        Mockito.verify(model, Mockito.atLeastOnce()).getMealLog();
        Mockito.verify(log, Mockito.atLeastOnce()).getTodayMeals();
        Mockito.verifyNoMoreInteractions(model, log);
    }

    @Test
    public void testDinner() {
        helper = new NotificationHelper(makeFakeTime(MISSING_DINNER));
        meals.add(new Meal(TypicalDishes.APPLE_PIE, new Timestamp(BEFORE_DINNER)));
        Assertions.assertEquals(Optional.of(NotificationHelper.MESSAGE_DINNER),
            helper.execute(model));
        Mockito.verify(model, Mockito.atLeastOnce()).getMealLog();
        Mockito.verify(log, Mockito.atLeastOnce()).getTodayMeals();
        Mockito.verifyNoMoreInteractions(model, log);
    }
}
