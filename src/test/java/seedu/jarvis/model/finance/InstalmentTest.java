package seedu.jarvis.model.finance;

import org.junit.jupiter.api.Test;
import seedu.jarvis.model.financetracker.Instalment;

import static seedu.jarvis.testutil.Assert.assertThrows;

public class InstalmentTest {

    public static void main(String[] args) {
        constructor_nullDescription_throwsNullPointerException();
        editInstalment_nullDescription_throwsNullPointerException();
    }

    @Test
    public static void constructor_nullDescription_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Instalment(null, 0.0));
    }

    @Test
    public static void editInstalment_nullDescription_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new InstalmentStub2().editDescription(null));
    }
}

class InstalmentStub extends Instalment {
    public InstalmentStub() {
        super("Spotify subscription", 9.5);
    }
}



