package seedu.address.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MessagesTest {

    @Test
    public void messagesAreAllCorrect() {
        assertEquals(Messages.getMessageUnknownCommand(), "Unknown command");
        assertEquals(Messages.getMessageInvalidCommandFormat(), "Invalid command format! \n%1$s");
        assertEquals(Messages.getMessageTransactionsListedOverview(), "%1$d transactions listed!");
        assertEquals(Messages.getMessageInvalidTransactionDisplayedIndex(),
            "The transaction index provided is invalid");
        assertEquals(Messages.getMessageInvalidBudgetDisplayedIndex(), "The budget index provided is invalid.");
        assertEquals(Messages.getMessageInvalidCommandUsage(), "Invalid command usage! \n%1$s");
        assertEquals(Messages.getMessageInvalidProjectionDisplayedIndex(), "The projection index provided is invalid.");
    }
}
