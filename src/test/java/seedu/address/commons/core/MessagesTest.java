package seedu.address.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MessagesTest {

    @Test
    public void messagesAreAllCorrect() {
        assertEquals(Messages.MESSAGE_UNKNOWN_COMMAND, "Unknown command");
        assertEquals(Messages.MESSAGE_INVALID_COMMAND_FORMAT, "Invalid command format! \n%1$s");
        assertEquals(Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW, "%1$d transactions listed!");
    }
}
