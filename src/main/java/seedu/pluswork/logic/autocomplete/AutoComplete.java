package seedu.pluswork.logic.autocomplete;

import java.util.LinkedList;
import java.util.SortedSet;
import java.util.TreeSet;

import seedu.pluswork.commons.Keywords;
import seedu.pluswork.model.Model;

public class AutoComplete {
    /**
     * AutoComplete logic to return suggestions based on command/prefix that the user is typing
     * uses the model to get relevant data for existing tasks, members
     * @param input
     * @param model
     */
    private Model model;

    public AutoComplete(Model model) {
        this.model = model;
    }
    private LinkedList<String> suggestions = new LinkedList<String>();

    public LinkedList<String> completeText(String input) {
        suggestions.clear();
        int firstSpace = input.indexOf(" ");
        if (firstSpace == -1) { // still entering commandWord
            SortedSet <String> commandList = new TreeSet<>(Keywords.commandList);
            suggestions.addAll(commandList.subSet(input, input + Character.MAX_VALUE));
        }
        // String commandWord = input.substring(0, firstSpace);

        // input is not a valid commandWord/ commandWord has no valid prefix to autocomplete
        if (input.endsWith("/")) { // entering a new prefix for the valid commandWord
            suggestions = getPrefixSuggestion(input);
        }
        return suggestions;
    }

    private LinkedList<String> getPrefixSuggestion(String input) {
        int lastSpace = input.lastIndexOf(" ");
        String prefix = input.substring(lastSpace + 1);
            return new LinkedList<>();
    }
}
