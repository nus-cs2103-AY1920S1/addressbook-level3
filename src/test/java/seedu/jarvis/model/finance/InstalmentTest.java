package seedu.jarvis.model.finance;

import static seedu.jarvis.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.financetracker.Instalment;

/**
 * Tests instalment class.
 */
public class InstalmentTest {

    @Test
    public void constructor_nullDescription_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Instalment(null, 0.0));
    }

    @Test
    public void editInstalment_nullDescription_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new InstalmentStub().editDescription(null));
    }
}

class InstalmentStub extends Instalment {
    public InstalmentStub() {
        super("Spotify subscription", 9.5);
    }
}



