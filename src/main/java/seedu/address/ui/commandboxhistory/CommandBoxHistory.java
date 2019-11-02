//@@author CarbonGrid
package seedu.address.ui.commandboxhistory;

import java.util.ArrayList;

/**
 * Component for CommandBox's History
 */
public class CommandBoxHistory {
    private final ArrayList<String> history = new ArrayList<>();
    private int index = 0;

    public String getOlder() {
        try {
            index = Math.max(index - 1, 0);
            return history.get(index);
        } catch (IndexOutOfBoundsException e) {
            return "";
        }
    }

    public String getNewer() {
        try {
            index = Math.min(index + 1, history.size());
            return history.get(index);
        } catch (IndexOutOfBoundsException e) {
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
            history.add(commandText);
        }
        index = history.size();
    }
}
