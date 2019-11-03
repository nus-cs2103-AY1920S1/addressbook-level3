package seedu.address.model.password;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PasswordModifiedAtTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PasswordModifiedAt(null));
    }
}
