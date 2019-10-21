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
        String word = "Sudowoodo";
        HintSupplier hintSupplier = new HintSupplier(word);
        FormattedHint formattedHint = hintSupplier.get();
        for (int i = 0; i < word.length() - 1; i++) {
            formattedHint = hintSupplier.get();
        }
        // After all hint characters are supplied, the formatted hint should be same as original word.
        assertEquals(word, formattedHint.toString());
    }
}
