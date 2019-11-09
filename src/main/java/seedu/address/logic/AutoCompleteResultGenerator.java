package seedu.address.logic;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import seedu.address.logic.graphs.AddCustomerGraph;
import seedu.address.logic.graphs.AddOrderGraph;
import seedu.address.logic.graphs.AddPhoneGraph;
import seedu.address.logic.graphs.AddScheduleGraph;
import seedu.address.logic.graphs.EditCustomerGraph;
import seedu.address.logic.graphs.EditOrderGraph;
import seedu.address.logic.graphs.EditPhoneGraph;
import seedu.address.logic.graphs.EditScheduleGraph;
import seedu.address.logic.graphs.GenerateStatsGraph;
import seedu.address.logic.graphs.ViewScheduleGraph;
import seedu.address.model.Model;

/**
 * Represents a generator for autocomplete support.
 */
class AutoCompleteResultGenerator {

    private final Model model;
    private final Map<String, AutoCompleteResultProvider> providers;

    AutoCompleteResultGenerator(Model model) {
        this.model = model;
        providers = new HashMap<>();

        initProviders();
    }

    /**
     * Instantiates supporting providers.
     */
    private void initProviders() {
        // Customer commands
        providers.put("switch-c", EmptyProvider.getInstance());
        providers.put("add-c", new AddCustomerGraph(model));
        providers.put("delete-c", EmptyProvider.getInstance());
        providers.put("find-c", EmptyProvider.getInstance());
        providers.put("list-c", EmptyProvider.getInstance());
        providers.put("clear-c", EmptyProvider.getInstance());
        providers.put("edit-c", new EditCustomerGraph(model));
        providers.put("copy-c", EmptyProvider.getInstance());

        // Phone commands
        providers.put("switch-p", EmptyProvider.getInstance());
        providers.put("add-p", new AddPhoneGraph(model));
        providers.put("delete-p", EmptyProvider.getInstance());
        providers.put("find-p", EmptyProvider.getInstance());
        providers.put("list-p", EmptyProvider.getInstance());
        providers.put("clear-p", EmptyProvider.getInstance());
        providers.put("edit-p", new EditPhoneGraph(model));
        providers.put("copy-p", EmptyProvider.getInstance());

        // Order commands
        providers.put("switch-o", EmptyProvider.getInstance());
        providers.put("add-o", new AddOrderGraph(model));
        providers.put("find-o", EmptyProvider.getInstance());
        providers.put("complete", EmptyProvider.getInstance());
        providers.put("cancel", EmptyProvider.getInstance());
        providers.put("list-o", EmptyProvider.getInstance());
        providers.put("clear-o", EmptyProvider.getInstance());
        providers.put("edit-o", new EditOrderGraph(model));
        providers.put("copy-o", EmptyProvider.getInstance());

        // Schedule commands
        providers.put("switch-s", EmptyProvider.getInstance());
        providers.put("schedule", new ViewScheduleGraph(model));
        providers.put("add-s", new AddScheduleGraph(model));
        providers.put("delete-s", EmptyProvider.getInstance());
        providers.put("edit-s", new EditScheduleGraph(model));

        // Archived order commands
        providers.put("switch-a", EmptyProvider.getInstance());

        // General commands
        providers.put("undo", EmptyProvider.getInstance());
        providers.put("redo", EmptyProvider.getInstance());
        providers.put("history", EmptyProvider.getInstance());
        providers.put("generate-s", new GenerateStatsGraph(model));
        providers.put("exit", EmptyProvider.getInstance());
        providers.put("help", EmptyProvider.getInstance());
        providers.put("export", EmptyProvider.getInstance());
    }

    private Optional<AutoCompleteResultProvider> getProvider(String commandWord) {
        return Optional.ofNullable(providers.get(commandWord));
    }

    private Set<String> getSupportedCommandWords() {
        return providers.keySet();
    }

    /**
     * Processes an input string and returns possible autocomplete results.
     * @param input A user input string.
     * @return An {@code AutoCompleteResult} containing possible autocomplete values.
     */
    AutoCompleteResult process(String input) {
        int firstSpace = input.indexOf(" ");
        if (firstSpace == -1) { // there is no space, indicating still typing command word
            SortedSet<String> values = new TreeSet<>(getSupportedCommandWords());
            return new AutoCompleteResult(values, input);
        } else { // there is at least one space, suggesting command word is present
            String commandWord = input.substring(0, firstSpace);
            Optional<AutoCompleteResultProvider> provider = getProvider(commandWord);
            if (provider.isPresent()) { // command word is supported
                String args = input.substring(firstSpace);
                return provider.get().process(args);
            } else { // command word not supported, return empty set
                return new AutoCompleteResult(Collections.emptySortedSet(), input);
            }
        }
    }

    private static boolean hasCommandWord(String input) {
        return input.stripLeading().contains(" ");
    }

}
