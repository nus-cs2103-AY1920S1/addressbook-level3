package thrift.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class MessagesTest {
    @Test
    public void messageUnknownCommand_messageAsExpected() {
        assertEquals("Unknown command", Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void messageInvalidCommandFormat_messageAsExpected() {
        assertEquals("Invalid command format! \nError!",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, "Error!"));

        //No space after \n
        assertNotEquals("Invalid command format! \n Error!",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, "Error!"));
    }

    @Test
    public void messageInvalidTransactionDisplayedIndex_messageAsExpected() {
        assertEquals("The transaction index provided is invalid",
                Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void messageTransactionListedOverview_messageAsExpected() {
        assertEquals("3 transactions listed!",
                String.format(Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW, 3));
    }
}
