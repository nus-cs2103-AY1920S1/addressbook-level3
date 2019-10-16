package seedu.savenus.model.util;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class TimeFormatterTest {

    @Test
    public void timeFormatter_differentClass() {
        TimeFormatter timeFormatter = new TimeFormatter();
        assertFalse(timeFormatter.equals(new TimeFormatter()));
    }
}
