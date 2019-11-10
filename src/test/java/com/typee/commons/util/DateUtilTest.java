package com.typee.commons.util;

import static com.typee.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class DateUtilTest {

    @Test
    public void getFormattedDateString_nullGiven_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> DateUtil.getFormattedDateString(null));
    }

    @Test
    public void getFormattedDateString_validDate_returnsFormattedDateString() {
        LocalDate date = LocalDate.of(2019, 1, 1);
        String formattedDateString = DateUtil.getFormattedDateString(date);
        assertEquals(formattedDateString, "01/01/2019");
    }

}
