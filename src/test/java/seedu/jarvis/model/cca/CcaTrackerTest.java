package seedu.jarvis.model.cca;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.jarvis.testutil.cca.TypicalCcas.CANOEING;
import static seedu.jarvis.testutil.cca.TypicalCcas.GUITAR_ENSEMBLE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CcaTrackerTest {

    private CcaTracker ccaTracker;

    @BeforeEach
    public void setUp() {
        // TODO is this the right way of stubbing? Do I need to stub the internalCcaList too?
        ccaTracker = new CcaTracker();
        CcaList ccaList = new CcaList();
        ccaList.addCca(CANOEING);
    }

    @Test
    public void contains_ccaNotInList_returnsFalse() {
        assertFalse(ccaTracker.contains(CANOEING));
    }

    @Test
    public void addCca_normalInput_addedCorrectly() {
        ccaTracker.addCca(GUITAR_ENSEMBLE);

    }

}
