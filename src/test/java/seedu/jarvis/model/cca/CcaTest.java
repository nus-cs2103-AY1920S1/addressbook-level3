package seedu.jarvis.model.cca;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.logic.commands.cca.CcaCommandTestUtil.VALID_NAME_GUITAR_ENSEMBLE;
import static seedu.jarvis.testutil.cca.TypicalCcas.CANOEING;
import static seedu.jarvis.testutil.cca.TypicalCcas.GUITAR_ENSEMBLE;

import org.junit.jupiter.api.Test;

import seedu.jarvis.testutil.cca.CcaBuilder;

public class CcaTest {

    @Test
    public void isSameCca() {
        // same object -> returns true
        assertTrue(CANOEING.isSameCca(CANOEING));

        // null -> returns false
        assertFalse(CANOEING.isSameCca(null));

        // different name -> returns false
        Cca editedCanoeing = new CcaBuilder(CANOEING).withName(VALID_NAME_GUITAR_ENSEMBLE).build();
        assertFalse(CANOEING.isSameCca(editedCanoeing));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Cca aliceCopy = new CcaBuilder(CANOEING).build();
        assertTrue(CANOEING.equals(aliceCopy));

        // same object -> returns true
        assertTrue(CANOEING.equals(CANOEING));

        // null -> returns false
        assertFalse(CANOEING.equals(null));

        // different type -> returns false
        assertFalse(CANOEING.equals(5));

        // different person -> returns false
        assertFalse(CANOEING.equals(GUITAR_ENSEMBLE));

        // different name -> returns false
        Cca editedCanoeing = new CcaBuilder(CANOEING).withName(VALID_NAME_GUITAR_ENSEMBLE).build();
        assertFalse(CANOEING.equals(editedCanoeing));

    }
}
