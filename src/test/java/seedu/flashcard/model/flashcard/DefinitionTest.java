package seedu.flashcard.model.flashcard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.flashcard.testutil.Assert.assertThrows;


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

        // null Definition
        assertThrows(NullPointerException.class, () -> Definition.isValidDefinition(null));

        // invalid Definition
        assertFalse(Definition.isValidDefinition("")); // empty string
        assertFalse(Definition.isValidDefinition(" ")); // spaces only

        // valid Definition
        assertTrue(Definition.isValidDefinition("3"));
        assertTrue(Definition.isValidDefinition("/")); // one character
        assertTrue(Definition.isValidDefinition("Builds applications by combining functionalities"
                +" packaged as programmatically")); // long answer
    }
}
