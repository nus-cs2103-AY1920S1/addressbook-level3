package seedu.revision.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.revision.testutil.Assert.assertThrows;
import static seedu.revision.testutil.TypicalHistory.getTypicalHistory;

import java.util.Collections;

import org.junit.jupiter.api.Test;


public class HistoryTest {

    private final History history = new History();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), history.getStatisticsList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> history.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyHistory_replacesData() {
        History newData = getTypicalHistory();
        history.resetData(newData);
        assertEquals(newData, history);
    }


    @Test
    public void getStatisticsList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> history.getStatisticsList().remove(0));
    }

}

