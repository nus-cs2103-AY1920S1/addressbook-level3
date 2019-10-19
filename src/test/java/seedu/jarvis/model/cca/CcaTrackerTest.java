package seedu.jarvis.model.cca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.testutil.Assert.assertThrows;
import static seedu.jarvis.testutil.cca.TypicalCcas.CANOEING;
import static seedu.jarvis.testutil.cca.TypicalCcas.GUITAR_ENSEMBLE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class CcaTrackerTest {

    private CcaTracker ccaTracker;
    private CcaList ccaList;

    @BeforeEach
    public void setUp() {
        final ObservableList<Cca> ccas = FXCollections.observableArrayList();
        ccaList = new CcaList(ccas);
        ccaTracker = new CcaTracker(ccaList);
    }

    @Test
    public void contains_ccaNotInList_returnsFalse() {
        assertFalse(ccaTracker.containsCca(CANOEING));
    }

    @Test
    public void addCca_normalInput_addedCorrectly() {
        ccaTracker.addCca(GUITAR_ENSEMBLE);
        assertTrue(ccaTracker.containsCca(GUITAR_ENSEMBLE));
    }

    @Test
    public void setCcaList_nullInput_throwsError() {
        assertThrows(NullPointerException.class, () -> ccaTracker.setCcaList(null));
    }

    @Test
    public void setCcaList_normalInput_returnsTrue() {
        CcaList ccaList = new CcaList();
        ccaTracker.setCcaList(ccaList.asUnmodifiableObservableList());
        assertEquals(ccaList.asUnmodifiableObservableList(), ccaTracker.getCcaList());
    }

    @Test
    public void getCcaList_normalInput_returnsTrue() {
        assertEquals(ccaTracker.getCcaList(), ccaList.asUnmodifiableObservableList());
    }

    @Test
    public void removeCca_normalInput_success() {
        ccaTracker.addCca(CANOEING);
        ccaTracker.removeCca(CANOEING);
        assertEquals(ccaTracker.getNumberOfCcas(), 0);
        assertFalse(ccaTracker.containsCca(CANOEING));
    }

    @Test
    public void updateCca_newFields_success() {
        ccaTracker.addCca(CANOEING);
        ccaTracker.updateCca(CANOEING, GUITAR_ENSEMBLE);
        assertTrue(ccaTracker.containsCca(GUITAR_ENSEMBLE));
        assertFalse(ccaTracker.containsCca(CANOEING));
    }

    @Test
    public void updateFilteredCcaList_showAllCcas_success() {
        ccaTracker.updateFilteredCcaList(unused -> true);
        assertEquals(ccaTracker.getFilteredCcaList(), ccaList.asUnmodifiableObservableList());
    }
}
