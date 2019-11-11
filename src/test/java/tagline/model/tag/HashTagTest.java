// @@author yehezkiel01

package tagline.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class HashTagTest {

    @Test
    public void isValidValue() {
        String tooLongHashTag = "0123456789012345678901234567890";
        assertFalse(HashTag.isValidValue(tooLongHashTag));

        String normalHashTag = "any topic";
        assertTrue(HashTag.isValidValue(normalHashTag));
    }
}
