package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class EventEndDateTest {

    @Test
    void eventEndDateToString() {
        assertEquals(new EventEndDate(LocalDate.of(2019, 10, 20)).toString(),
                "20/10/2019");
    }

    @Test
    void eventEndDateEquals() {
        assertEquals(new EventEndDate(LocalDate.of(2019, 10, 20)),
                new EventEndDate(LocalDate.of(2019, 10, 20)));

        assertNotEquals(new EventEndDate(LocalDate.of(2019, 10, 20)),
                new EventEndDate(LocalDate.of(2019, 10, 19))); //diff day

        assertNotEquals(new EventEndDate(LocalDate.of(2019, 10, 20)),
                new EventEndDate(LocalDate.of(2019, 9, 19))); //diff month

        assertNotEquals(new EventEndDate(LocalDate.of(2019, 10, 20)),
                new EventEndDate(LocalDate.of(2020, 10, 19))); //diff year
    }
}
