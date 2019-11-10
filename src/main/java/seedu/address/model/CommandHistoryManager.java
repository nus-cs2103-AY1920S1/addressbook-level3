package seedu.address.model;

import java.util.LinkedList;

/**
 * Keeps track of the valid commands that have been executed in Alfred, and allows command history navigation.
 */
public class CommandHistoryManager implements CommandHistory {
    private static final int CAPACITY = 50;
    private LinkedList<String> history;
    private int currIndex;

    public CommandHistoryManager() {
        this.history = new LinkedList<>();
        this.currIndex = 0;
    }

    /**
     * Saves a record of the execution of a command, by storing the {@code commandInputString}.
     * @param commandInputString string of the command input in Alfred
     */
    public void saveCommandExecutionString(String commandInputString) {
        //Every execution of a new command will cause the currIndex to be reset
        //to the right-most index of the `history` + 1
        if (this.history.size() == CAPACITY) {
            this.history.remove();
        }
        this.history.add(commandInputString);
        this.currIndex = this.history.size();
    }

    /**
     * Gets the previous command String.
     */
    public String getPrevCommandString() {
        if (this.history.size() == 0) {
            return "";
        }

        if (this.currIndex <= 0) {
            return this.history.get(0);
        }

        this.currIndex -= 1;
        return this.history.get(this.currIndex);
    }

    /**
     * Gets the next command String.
     */
    public String getNextCommandString() {
        if (this.history.size() == 0) {
            return "";
        }

        if ((this.currIndex + 1) >= this.history.size()) {
            this.currIndex = this.history.size();
            return "";
        }

        this.currIndex += 1;
        return this.history.get(this.currIndex);
    }

    /**
     * Gets size of LinkedList storing command Strings
     */
    public int getSizeOfHistory() {
        return this.history.size();
    }
}
