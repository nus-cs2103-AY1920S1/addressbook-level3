package io.xpire.model.item;

import static io.xpire.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ReminderDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ReminderDate(null));
    }
}


