package seedu.address.model.training;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;

import org.junit.jupiter.api.Test;

class AttendanceEntryTest {

    @Test
    void constructorTest() {
        AttendanceEntry entry = new AttendanceEntry(ALICE, true);
        assertNotNull(entry);
    }

    @Test
    void getPerson() {
        AttendanceEntry entry = new AttendanceEntry(BENSON, false);
        assertEquals(entry.getPerson(), BENSON);
    }

    @Test
    void getIsPresent() {
        AttendanceEntry entryAttended = new AttendanceEntry(CARL, true);
        AttendanceEntry entryAbsent = new AttendanceEntry(DANIEL, false);
        assertEquals(entryAttended.getIsPresent(), true);
        assertNotEquals(entryAbsent.getIsPresent(), true);
    }

    @Test
    void equalsTest() {
        AttendanceEntry entry = new AttendanceEntry(ELLE, true);
        AttendanceEntry diffPresentEntry = new AttendanceEntry(ELLE, false);
        AttendanceEntry diffPersonEntry = new AttendanceEntry(FIONA, true);
        AttendanceEntry anotherEntry = new AttendanceEntry(ELLE, true);
        assertNotEquals(entry, diffPresentEntry);
        assertNotEquals(entry, diffPersonEntry);
        assertEquals(entry, anotherEntry);
    }
}
