package seedu.moneygowhere.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CommandStorageImplTest {

    private CommandStorageImpl commandStorage;

    @Test
    public void getPrevCommand() {
        // before any commands were inputted
        commandStorage = new CommandStorageImpl();
        assertEquals("", commandStorage.getPrevCommand());

        // after commands were inputted
        commandStorage.addCommand("command1");
        commandStorage.addCommand("command2");
        assertEquals("command2", commandStorage.getPrevCommand());
        assertEquals("command1", commandStorage.getPrevCommand());
    }

    @Test
    public void getNextCommand() {
        // before any commands were inputted
        commandStorage = new CommandStorageImpl();
        assertEquals("", commandStorage.getNextCommand());

        // after commands were inputted
        commandStorage.addCommand("command1");
        commandStorage.addCommand("command2");
        assertEquals("", commandStorage.getNextCommand());

        assertEquals("command2", commandStorage.getPrevCommand());
        assertEquals("command1", commandStorage.getPrevCommand());
        assertEquals("command2", commandStorage.getNextCommand());
        assertEquals("", commandStorage.getNextCommand());
    }
}
