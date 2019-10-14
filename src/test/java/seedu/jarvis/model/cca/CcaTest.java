package seedu.jarvis.model.cca;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.logic.commands.cca.CcaCommandTestUtil.*;
import static seedu.jarvis.testutil.cca.TypicalCcas.CANOEING;
import static seedu.jarvis.testutil.cca.TypicalCcas.GUITAR_ENSEMBLE;
import static seedu.jarvis.testutil.cca.TypicalEquipmentList.GUITAR_ENSEMBLE_EQUIPMENT_LIST;

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

        // same name, same type,  different attributes -> returns true
        editedCanoeing = new CcaBuilder(CANOEING)
                .withEquipmentList(GUITAR_ENSEMBLE_EQUIPMENT_LIST).build();
        assertTrue(CANOEING.isSameCca(editedCanoeing));

    }

    @Test
    public void equals() {
        // same values -> returns true
        Cca canoeingCopy = new CcaBuilder(CANOEING).build();
        assertTrue(CANOEING.equals(canoeingCopy));

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

        // different type -> returns false
        editedCanoeing = new CcaBuilder(CANOEING).withType(VALID_NAME_TYPE_CLUB).build();
        assertFalse(CANOEING.equals(editedCanoeing));

    }
}
