package seedu.jarvis.model.cca;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.jarvis.testutil.cca.TypicalCcas.CANOEING;

import org.junit.jupiter.api.Test;

public class CcaListTest {

    private final CcaList ccaList = new CcaList();

    @Test
    public void contains_ccaNotInList_returnsFalse() {
        assertFalse(ccaList.contains(CANOEING));
    }

}
