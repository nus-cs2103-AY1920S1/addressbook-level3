package seedu.savenus.commons.core;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class MessagesTest {

    @Test
    public void messages_differentClass() {
        Messages messages = new Messages();
        assertFalse(messages.equals(new Messages()));
    }
}
