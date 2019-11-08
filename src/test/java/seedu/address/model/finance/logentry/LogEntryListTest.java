package seedu.address.model.finance.logentry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLogEntries.LOG01;
import static seedu.address.testutil.TypicalLogEntries.LOG02;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.finance.logentry.exceptions.LogEntryNotFoundException;

public class LogEntryListTest {

    private final LogEntryList logEntryList = new LogEntryList();

    @Test
    public void contains_nullLogEntry_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> logEntryList.contains(null));
    }

    @Test
    public void contains_logEntryNotInList_returnsFalse() {
        assertFalse(logEntryList.contains(LOG01));
    }

    @Test
    public void contains_logEntryInList_returnsTrue() {
        logEntryList.add(LOG01);
        assertTrue(logEntryList.contains(LOG01));
    }

    @Test
    public void add_nullLogEntry_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> logEntryList.add(null));
    }

    @Test
    public void setPerson_nullTargetLogEntry_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> logEntryList.setLogEntry(null, LOG01));
    }

    @Test
    public void setPerson_nullEditedPLogEntry_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> logEntryList.setLogEntry(LOG01, null));
    }

    @Test
    public void setPerson_targetLogEntryNotInList_throwsLogEntryNotFoundException() {
        assertThrows(LogEntryNotFoundException.class, () -> logEntryList.setLogEntry(LOG01, LOG01));
    }

    @Test
    public void setPerson_editedLogEntryIsSameLogEntry_success() {
        logEntryList.add(LOG01);
        logEntryList.setLogEntry(LOG01, LOG01);
        LogEntryList expectedLogEntryList = new LogEntryList();
        expectedLogEntryList.add(LOG01);
        assertEquals(expectedLogEntryList, logEntryList);
    }

    @Test
    public void setPerson_editedLogEntryHasDifferentFields_success() {
        logEntryList.add(LOG01);
        logEntryList.setLogEntry(LOG01, LOG02);
        LogEntryList expectedLogEntryList = new LogEntryList();
        expectedLogEntryList.add(LOG02);
        assertEquals(expectedLogEntryList, logEntryList);
    }

    @Test
    public void remove_nullLogEntry_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> logEntryList.remove(null));
    }

    @Test
    public void remove_logEntryDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(LogEntryNotFoundException.class, () -> logEntryList.remove(LOG01));
    }

    @Test
    public void remove_existingLogEntry_removesLogEntry() {
        logEntryList.add(LOG01);
        logEntryList.remove(LOG01);
        LogEntryList expectedLogEntryList = new LogEntryList();
        assertEquals(expectedLogEntryList, logEntryList);
    }

    @Test
    public void setLogEntries_nullLogEntryList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> logEntryList.setLogEntries((LogEntryList) null));
    }

    @Test
    public void setLogEntries_logEntryList_replacesOwnListWithProvidedLogEntryList() {
        logEntryList.add(LOG01);
        LogEntryList expectedLogEntryList = new LogEntryList();
        expectedLogEntryList.add(LOG02);
        logEntryList.setLogEntries(expectedLogEntryList);
        assertEquals(expectedLogEntryList, logEntryList);
    }

    @Test
    public void setLogEntries_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> logEntryList.setLogEntries((List<LogEntry>) null));
    }

    @Test
    public void setLogEntries_list_replacesOwnListWithProvidedList() {
        logEntryList.add(LOG01);
        List<LogEntry> logEntryList = Collections.singletonList(LOG02);
        this.logEntryList.setLogEntries(logEntryList);
        LogEntryList expectedLogEntryList = new LogEntryList();
        expectedLogEntryList.add(LOG02);
        assertEquals(expectedLogEntryList, this.logEntryList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> logEntryList.asUnmodifiableObservableList().remove(0));
    }
}
