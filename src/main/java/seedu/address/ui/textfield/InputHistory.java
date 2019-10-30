package seedu.address.ui.textfield;

import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * History of inputted commands.
 */
public class InputHistory {
    private Stack<String> commandHistory;
    private ListIterator<String> iterator;

    public InputHistory() {
        commandHistory = new Stack<>();
        iterator = commandHistory.listIterator();
    }

    /**
     * Adds an input to the history and resets the iterator.
     * @param string the input to push.
     */
    public void push(String string) {
        commandHistory.push(string);
        // reset iterator
        iterator = commandHistory.listIterator(commandHistory.size());
    }

    public String getPreviousInput() throws NoSuchElementException {
        return iterator.previous();
    }

    public String getNextInput() throws NoSuchElementException {
        return iterator.next();
    }
}
