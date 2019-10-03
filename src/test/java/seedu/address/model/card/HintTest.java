package seedu.address.model.card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;

public class HintTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Hint(null, null));
        assertThrows(NullPointerException.class, () -> new Hint('d', null));
        assertThrows(NullPointerException.class, () -> new Hint(null, Index.fromZeroBased(1)));
    }

    @Test
    public void equals() {
        Character validChar = 'C';
        Character validChar2 = 'D';
        Index validIndex = Index.fromZeroBased(0);
        Index validIndex2 = Index.fromZeroBased(1);

        Hint hint = new Hint(validChar, validIndex);
        assertEquals(hint, hint);
        Hint hint2 = new Hint(validChar, validIndex);
        assertEquals(hint, hint2);
        Hint hint3 = new Hint(validChar2, validIndex);
        assertNotEquals(hint, hint3);
        Hint hint4 = new Hint(validChar, validIndex2);
        assertNotEquals(hint, hint4);
    }
}
