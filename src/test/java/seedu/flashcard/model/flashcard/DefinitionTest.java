package seedu.flashcard.model.flashcard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.flashcard.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DefinitionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Definition(null));
    }

    @Test
    public void constructor_invalidDefinition_throwsIllegalArgumentException() {
        String invalidDefinition = "";
        assertThrows(IllegalArgumentException.class, () -> new Definition(invalidDefinition));
    }

    @Test
    public void isValidDefinition() {
        assertThrows(NullPointerException.class, () -> Definition.isValidDefinition(null));
        assertFalse(Definition.isValidDefinition(""));
        assertFalse(Definition.isValidDefinition(" "));
        assertTrue(Definition.isValidDefinition("Roses are red"));
    }
}
