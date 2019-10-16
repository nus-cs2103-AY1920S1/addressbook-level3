package seedu.address.ui;

import java.util.Arrays;
import java.util.List;

/**
 * A class containing command keywords to be matched with for autocompletion.
 */
public class CommandSuggestions {
    private static List<String> suggestions = Arrays.asList(
            "add-c", "add-o", "add-p", "add-s",
            "delete-c", "delete-p", "delete-s",
            "edit-c", "edit-o", "edit-p", "edit-s",
            "switch-c", "switch-o", "switch-p", "switch-s",
            "list", "cancel", "exit", "help",
            "generate-s"
    );

    public static List<String> getSuggestions() {
        return suggestions;
    }

    public static void addSuggestion(String suggestion) {
        suggestions.add(suggestion);
    }

}
