package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void equals_valid_success() {
        assertEquals(new Name("Probability and Statistics"), new Name("Probability and Statistics"));
        assertNotSame(new Name("Probability and Statistics"), new Name("Software Engineering"));
    }

    @Test
    public void toString_valid_success() {
        assertEquals(new Name("Probability and Statistics").toString(), "Probability and Statistics");
        assertNotSame(new Name("Probability and Statistics").toString(), "Software Engineering");
    }

    @Test
    public void hashCode_valid_success() {
        assertEquals(new Name("Software Engineering").hashCode(), new Name("Software Engineering").hashCode());
        assertNotSame(new Name("Software Engineering").hashCode(), new Name("Probability and Statistics").hashCode());
    }
}
