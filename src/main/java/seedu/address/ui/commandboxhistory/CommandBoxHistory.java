package seedu.address.ui.commandboxhistory;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Component for CommandBox's History
 */
public class CommandBoxHistory {
    private final LinkedList<String> history = new LinkedList<>();
    private ListIterator<String> caret = history.listIterator();

    public String getOlder() {
        try {
            String commandText = caret.next();
            if (!caret.hasNext()) {
                caret.previous();
            }
            return commandText;
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public String getNewer() {
        try {
            return caret.previous();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    /**
     * Adds String into CommandBoxHistory.
     *
     * @param commandText Command in String to be added into CommandBoxHistory.
     */
    public void add(String commandText) {
        if (!commandText.isBlank()) {
            history.addFirst(commandText);
        }
        caret = history.listIterator();
    }
}
