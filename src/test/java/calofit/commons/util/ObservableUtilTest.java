package calofit.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;

import javafx.beans.InvalidationListener;
import javafx.beans.binding.ObjectExpression;
import javafx.beans.property.SimpleObjectProperty;

import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;

class ObservableUtilTest {

    public static final LocalDate TEST_DATE = LocalDate.of(2019, Month.NOVEMBER, 1);
    @Test
    public void testCachingMap() {
        SimpleObjectProperty<LocalDate> prop = new SimpleObjectProperty<>(TEST_DATE);
        ObjectExpression<Month> derived = ObservableUtil.cachingMap(prop, LocalDate::getMonth);
        assertEquals(prop.get().getMonth(), derived.getValue());

        InvalidationListener wrap = Mockito.mock(InvalidationListener.class);
        Mockito.doAnswer(AdditionalAnswers.answerVoid(obj -> assertEquals(derived, obj)))
            .when(wrap).invalidated(Mockito.any());
        derived.addListener(wrap);

        prop.set(TEST_DATE.plus(Period.ofDays(1)));
        Mockito.verifyNoInteractions(wrap);

        prop.set(TEST_DATE.minus(Period.ofMonths(2)));
        Mockito.verify(wrap).invalidated(derived);
    }
}
