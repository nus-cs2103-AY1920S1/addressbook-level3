package thrift.model.clone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static thrift.testutil.Assert.assertThrows;

import java.util.Calendar;

import org.junit.jupiter.api.Test;

import thrift.logic.commands.exceptions.CommandException;

public class OccurrenceTest {

    @Test
    public void constructor_nullFrequency_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Occurrence(null, 1));
    }

    @Test
    public void getterMethods_returnCorrectValues() {
        String inputFrequency = "monthly";
        int inputNumOccurrences = 2;
        Occurrence occurrence = new Occurrence(inputFrequency, inputNumOccurrences);
        assertEquals(occurrence.getFrequency(), inputFrequency);
        assertEquals(occurrence.getNumOccurrences(), inputNumOccurrences);
    }

    @Test
    public void isValidFrequency() {
        // valid frequencies
        assertTrue(Occurrence.isValidFrequency("daily"));
        assertTrue(Occurrence.isValidFrequency("weekly"));
        assertTrue(Occurrence.isValidFrequency("monthly"));
        assertTrue(Occurrence.isValidFrequency("yearly"));

        // null value
        assertFalse(Occurrence.isValidFrequency(null));

        // invalid frequency
        assertFalse(Occurrence.isValidFrequency("once every day"));
    }

    @Test
    public void getFrequencyCalendarField() {
        try {
            Occurrence dailyOccurrence = new Occurrence("daily", 3);
            assertEquals(dailyOccurrence.getFrequencyCalendarField(), Calendar.DATE);

            Occurrence weeklyOccurrence = new Occurrence("weekly", 3);
            assertEquals(weeklyOccurrence.getFrequencyCalendarField(), Calendar.WEEK_OF_YEAR);

            Occurrence monthlyOccurrence = new Occurrence("monthly", 3);
            assertEquals(monthlyOccurrence.getFrequencyCalendarField(), Calendar.MONTH);

            Occurrence yearlyOccurrence = new Occurrence("yearly", 3);
            assertEquals(yearlyOccurrence.getFrequencyCalendarField(), Calendar.YEAR);

            Occurrence invalidFrequencyOccurrence = new Occurrence("once every month", 3);
            assertThrows(CommandException.class, invalidFrequencyOccurrence::getFrequencyCalendarField);
        } catch (CommandException ce) {
            // Should not reach this line
        }
    }

    @Test
    public void equals() {
        Occurrence occurrence = new Occurrence("weekly", 5);

        // same values -> returns true
        Occurrence occurrenceCopy = new Occurrence("weekly", 5);
        assertTrue(occurrence.equals(occurrenceCopy));

        // same object -> returns true
        assertTrue(occurrence.equals(occurrence));

        // different frequency -> returns false
        Occurrence occurrenceDiffFrequency = new Occurrence("yearly", 5);
        assertFalse(occurrence.equals(occurrenceDiffFrequency));

        // different numOccurrences -> returns false
        Occurrence occurrenceDiffNumOccurrences = new Occurrence("weekly", 6);
        assertFalse(occurrence.equals(occurrenceDiffNumOccurrences));

        // all fields different -> returns false
        Occurrence occurrenceAllDiffFields = new Occurrence("monthly", 4);
        assertFalse(occurrence.equals(occurrenceAllDiffFields));

        // different type -> returns false
        assertFalse(occurrence.equals(100));

        // compare to null -> returns false
        assertFalse(occurrence.equals(null));
    }

}
