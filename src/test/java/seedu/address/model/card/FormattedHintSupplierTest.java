package seedu.address.model.card;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;


public class FormattedHintSupplierTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FormattedHintSupplier(null));
    }

    @Test
    public void getRemainingHints() {
        String name = "Sudowoodo";
        FormattedHintSupplier formattedHintSupplier = new FormattedHintSupplier(name);
        assertEquals(formattedHintSupplier.getRemainingNumOfHints(), name.length());
    }

    @Test
    public void get() {
        String word = "Sudowoodo";
        FormattedHintSupplier formattedHintSupplier = new FormattedHintSupplier(word);
        FormattedHint formattedHint = formattedHintSupplier.get();
        for (int i = 0; i < word.length() - 1; i++) {
            formattedHint = formattedHintSupplier.get();
        }
        // After all hint characters are supplied, the formatted hint should be same as original word.
        assertEquals(word, formattedHint.toString());
    }
}
