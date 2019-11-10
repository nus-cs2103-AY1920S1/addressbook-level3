package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommandHistoryManagerTest {
    private CommandHistoryManager hist;
    private int capacity;

    @BeforeEach
    void setUp() {
        this.hist = new CommandHistoryManager();
        this.capacity = 50;
    }

    void setToMaxCapacity() {
        for (int i = 0; i < this.capacity; i++) {
            this.hist.saveCommandExecutionString("Executing Command Number " + (i + 1));
        }
    }

    @Test
    void saveCommandExecutionString_normalExecution_success() {
        for (int i = 0; i < 3; i++) {
            this.hist.saveCommandExecutionString("Successful Command Execution Number " + (i + 1));
            assertTrue(this.hist.getSizeOfHistory() == (i + 1));
        }
    }

    @Test
    void saveCommandExecutionString_hitMaxCapacity_success() {
        this.hist.saveCommandExecutionString("Initial Command");
        assertTrue(this.hist.getSizeOfHistory() == 1);
        setToMaxCapacity();
        assertTrue(this.hist.getSizeOfHistory() == this.capacity); //Checks that size never exceeds capacity
    }

    @Test
    void getPrevCommandString_emptyHistory() {
        String prevCommand = this.hist.getPrevCommandString();
        assertTrue(prevCommand.equals(""));
    }

    @Test
    void getPrevCommandString_nonEmptyHistory() {
        String inputCommandString = "Initial Command";
        this.hist.saveCommandExecutionString(inputCommandString);
        String prevCommand = this.hist.getPrevCommandString();
        assertTrue(prevCommand.equals(inputCommandString));
    }

    @Test
    void getPrevCommandString_maxCapacityHistory() {
        String inputCommandString = "Initial Command";
        this.hist.saveCommandExecutionString(inputCommandString);
        setToMaxCapacity();
        for (int i = this.capacity; i > 0; i--) {
            assertEquals(this.hist.getPrevCommandString(), "Executing Command Number " + i);
        }
        String beforeStartingIndexCommandString = this.hist.getPrevCommandString();
        assertEquals(beforeStartingIndexCommandString, "Executing Command Number 1");
    }

    @Test
    void getNextCommandString_emptyHistory() {
        String prevCommand = this.hist.getNextCommandString();
        assertTrue(prevCommand.equals(""));
    }

    @Test
    void getNextCommandString_nonEmptyHistory() {
        String inputCommandString = "Initial Command";
        this.hist.saveCommandExecutionString(inputCommandString);
        String nextCommand = this.hist.getNextCommandString();
        assertTrue(nextCommand.equals(""));
    }

    @Test
    void getNextCommandString_nonEmptyHistoryWithPrevNavigation() {
        setToMaxCapacity();
        for (int i = 0; i < this.capacity; i++) {
            this.hist.getPrevCommandString();
        }

        for (int i = 0; i < this.capacity - 1; i++) {
            String nextCommand = this.hist.getNextCommandString();
            assertEquals(nextCommand, "Executing Command Number " + (i + 2));
        }
        assertEquals(this.hist.getNextCommandString(), "");
    }
}
