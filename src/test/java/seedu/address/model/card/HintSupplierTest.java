package seedu.address.model.card;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;


public class HintSupplierTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new HintSupplier(null));
    }

    @Test
    public void getRemainingHints() {
        String name = "Sudowoodo";
        HintSupplier hintSupplier = new HintSupplier(name);
        assertEquals(hintSupplier.getRemainingHints(), name.length());
    }

    @Test
    public void get() {
        String name = "Sudowoodo";
        HintSupplier hintSupplier = new HintSupplier(name);
        while (hintSupplier.getRemainingHints() > 0) {
            Hint hint = hintSupplier.get();
            assertEquals(name.charAt(hint.index.getZeroBased()), (char) hint.letter);
        }
    }
}
