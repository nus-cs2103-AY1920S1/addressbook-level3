package thrift.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MessagesTest {

    @Test
    public void correctMessageValues() {
        assertEquals(Messages.MESSAGE_UNKNOWN_COMMAND, "Unknown command");
        assertEquals(Messages.MESSAGE_INVALID_COMMAND_FORMAT, "Invalid command format! \n%1$s");
        assertEquals(Messages.MESSAGE_INVALID_COMMAND_FORMAT_WITH_PE, "Invalid command format! %2$s\n%1$s");
        assertEquals(Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX, "The transaction index provided is invalid");
        assertEquals(Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW, "%1$d transactions listed!");
    }
}
