package seedu.address.logic.autocomplete;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import seedu.address.logic.autocomplete.graphs.add.AddCustomerGraph;
import seedu.address.logic.autocomplete.graphs.add.AddOrderGraph;
import seedu.address.logic.autocomplete.graphs.add.AddPhoneGraph;
import seedu.address.logic.autocomplete.graphs.add.AddScheduleGraph;
import seedu.address.logic.autocomplete.graphs.copy.CopyCustomerGraph;
import seedu.address.logic.autocomplete.graphs.copy.CopyOrderGraph;
import seedu.address.logic.autocomplete.graphs.copy.CopyPhoneGraph;
import seedu.address.logic.autocomplete.graphs.delete.DeleteCustomerGraph;
import seedu.address.logic.autocomplete.graphs.delete.DeletePhoneGraph;
import seedu.address.logic.autocomplete.graphs.delete.DeleteScheduleGraph;
import seedu.address.logic.autocomplete.graphs.edit.EditCustomerGraph;
import seedu.address.logic.autocomplete.graphs.edit.EditOrderGraph;
import seedu.address.logic.autocomplete.graphs.edit.EditPhoneGraph;
import seedu.address.logic.autocomplete.graphs.edit.EditScheduleGraph;
import seedu.address.logic.autocomplete.graphs.find.FindCustomerGraph;
import seedu.address.logic.autocomplete.graphs.find.FindOrderGraph;
import seedu.address.logic.autocomplete.graphs.find.FindPhoneGraph;
import seedu.address.logic.autocomplete.graphs.misc.CancelOrderGraph;
import seedu.address.logic.autocomplete.graphs.misc.CompleteOrderGraph;
import seedu.address.logic.autocomplete.graphs.misc.GenerateStatsGraph;
import seedu.address.logic.autocomplete.graphs.misc.ViewScheduleGraph;
import seedu.address.model.Model;

/**
 * Represents a generator for autocomplete support.
 */
public class AutoCompleteResultGenerator {

    private final Model model;
    private final Map<String, AutoCompleteResultProvider> providers;

    public AutoCompleteResultGenerator(Model model) {
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
        providers.put("delete-c", new DeleteCustomerGraph(model));
        providers.put("find-c", new FindCustomerGraph(model));
        providers.put("list-c", EmptyProvider.getInstance());
        providers.put("clear-c", EmptyProvider.getInstance());
        providers.put("edit-c", new EditCustomerGraph(model));
        providers.put("copy-c", new CopyCustomerGraph(model));

        // Phone commands
        providers.put("switch-p", EmptyProvider.getInstance());
        providers.put("add-p", new AddPhoneGraph(model));
        providers.put("delete-p", new DeletePhoneGraph(model));
        providers.put("find-p", new FindPhoneGraph(model));
        providers.put("list-p", EmptyProvider.getInstance());
        providers.put("clear-p", EmptyProvider.getInstance());
        providers.put("edit-p", new EditPhoneGraph(model));
        providers.put("copy-p", new CopyPhoneGraph(model));

        // Order commands
        providers.put("switch-o", EmptyProvider.getInstance());
        providers.put("add-o", new AddOrderGraph(model));
        providers.put("find-o", new FindOrderGraph(model));
        providers.put("complete", new CompleteOrderGraph(model));
        providers.put("cancel", new CancelOrderGraph(model));
        providers.put("list-o", EmptyProvider.getInstance());
        providers.put("clear-o", EmptyProvider.getInstance());
        providers.put("edit-o", new EditOrderGraph(model));
        providers.put("copy-o", new CopyOrderGraph(model));

        // Schedule commands
        providers.put("switch-s", EmptyProvider.getInstance());
        providers.put("schedule", new ViewScheduleGraph(model));
        providers.put("add-s", new AddScheduleGraph(model));
        providers.put("delete-s", new DeleteScheduleGraph(model));
        providers.put("edit-s", new EditScheduleGraph(model));
        providers.put("clear-s", EmptyProvider.getInstance());

        // Archived order commands
        providers.put("switch-a", EmptyProvider.getInstance());
        providers.put("clear-a", EmptyProvider.getInstance());

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

    public Set<String> getSupportedCommandWords() {
        return providers.keySet();
    }

    /**
     * Processes an input string and returns possible autocomplete results.
     * @param input A user input string.
     * @return An {@code AutoCompleteResult} containing possible autocomplete values.
     */
    public AutoCompleteResult process(String input) {
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

}
