package seedu.address.model.flashcard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.model.flashcard.exceptions.StringToScheduleIncrementConversionException;

public class ScheduleIncrementTest {

    @Test
    public void test() {
        assertNotNull(ScheduleIncrement.valueOf("FIRST"));
        assertNotNull(ScheduleIncrement.valueOf("SECOND"));
        assertNotNull(ScheduleIncrement.valueOf("THIRD"));
        assertNotNull(ScheduleIncrement.valueOf("FOURTH"));
        assertNotNull(ScheduleIncrement.valueOf("FIFTH"));
        assertNotNull(ScheduleIncrement.valueOf("SIXTH"));
        assertNotNull(ScheduleIncrement.valueOf("FINAL"));
    }

    @Test
    public void getScheduleIncrementFromString() {
        assertEquals(ScheduleIncrement.FIRST, ScheduleIncrement.getScheduleIncrementFromString("FIRST"));
        assertEquals(ScheduleIncrement.SECOND, ScheduleIncrement.getScheduleIncrementFromString("SECOND"));
        assertEquals(ScheduleIncrement.THIRD, ScheduleIncrement.getScheduleIncrementFromString("THIRD"));
        assertEquals(ScheduleIncrement.FOURTH, ScheduleIncrement.getScheduleIncrementFromString("FOURTH"));
        assertEquals(ScheduleIncrement.FIFTH, ScheduleIncrement.getScheduleIncrementFromString("FIFTH"));
        assertEquals(ScheduleIncrement.SIXTH, ScheduleIncrement.getScheduleIncrementFromString("SIXTH"));
        assertEquals(ScheduleIncrement.FINAL, ScheduleIncrement.getScheduleIncrementFromString("FINAL"));
    }

    @Test
    public void getScheduleIncrementFromString_invalidValue_throwsStringToScheduleIncrementConversionException() {
        ArrayList<String> invalidStringScheduleIncrements = new ArrayList<>();
        invalidStringScheduleIncrements.add("first");
        invalidStringScheduleIncrements.add("FIrST");
        invalidStringScheduleIncrements.add("SEVENTH");
        invalidStringScheduleIncrements.add("1");
        invalidStringScheduleIncrements.add("1st");
        for (String invalidStringScheduleIncrement: invalidStringScheduleIncrements) {
            assertThrows(StringToScheduleIncrementConversionException.class, () -> ScheduleIncrement
                    .getScheduleIncrementFromString(invalidStringScheduleIncrement));
        }
    }

    @Test
    public void getNumberOfDays() {
        assertEquals(ScheduleIncrement.FIRST.getNumberOfDays(), 1);
        assertEquals(ScheduleIncrement.SECOND.getNumberOfDays(), 1);
        assertEquals(ScheduleIncrement.THIRD.getNumberOfDays(), 2);
        assertEquals(ScheduleIncrement.FOURTH.getNumberOfDays(), 3);
        assertEquals(ScheduleIncrement.FIFTH.getNumberOfDays(), 5);
        assertEquals(ScheduleIncrement.SIXTH.getNumberOfDays(), 5);
        assertEquals(ScheduleIncrement.FINAL.getNumberOfDays(), 7);
    }

    @Test
    public void getNextIncrement() {
        assertEquals(ScheduleIncrement.FIRST.getNextIncrement(), ScheduleIncrement.SECOND);
        assertEquals(ScheduleIncrement.SECOND.getNextIncrement(), ScheduleIncrement.THIRD);
        assertEquals(ScheduleIncrement.THIRD.getNextIncrement(), ScheduleIncrement.FOURTH);
        assertEquals(ScheduleIncrement.FOURTH.getNextIncrement(), ScheduleIncrement.FIFTH);
        assertEquals(ScheduleIncrement.FIFTH.getNextIncrement(), ScheduleIncrement.SIXTH);
        assertEquals(ScheduleIncrement.SIXTH.getNextIncrement(), ScheduleIncrement.FINAL);
        assertEquals(ScheduleIncrement.FINAL.getNextIncrement(), ScheduleIncrement.FINAL);
    }

    @Test
    public void method_toString() {
        assertEquals(ScheduleIncrement.FIRST.toString(), "FIRST");
        assertEquals(ScheduleIncrement.SECOND.toString(), "SECOND");
        assertEquals(ScheduleIncrement.THIRD.toString(), "THIRD");
        assertEquals(ScheduleIncrement.FOURTH.toString(), "FOURTH");
        assertEquals(ScheduleIncrement.FIFTH.toString(), "FIFTH");
        assertEquals(ScheduleIncrement.SIXTH.toString(), "SIXTH");
        assertEquals(ScheduleIncrement.FINAL.toString(), "FINAL");
    }

}
