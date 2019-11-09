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
import seedu.address.logic.graphs.CancelOrderGraph;
import seedu.address.logic.graphs.CompleteOrderGraph;
import seedu.address.logic.graphs.CopyCustomerGraph;
import seedu.address.logic.graphs.CopyOrderGraph;
import seedu.address.logic.graphs.CopyPhoneGraph;
import seedu.address.logic.graphs.DeleteCustomerGraph;
import seedu.address.logic.graphs.DeletePhoneGraph;
import seedu.address.logic.graphs.DeleteScheduleGraph;
import seedu.address.logic.graphs.EditCustomerGraph;
import seedu.address.logic.graphs.EditOrderGraph;
import seedu.address.logic.graphs.EditPhoneGraph;
import seedu.address.logic.graphs.EditScheduleGraph;
import seedu.address.logic.graphs.FindCustomerGraph;
import seedu.address.logic.graphs.FindOrderGraph;
import seedu.address.logic.graphs.FindPhoneGraph;
import seedu.address.logic.graphs.GenerateStatsGraph;
import seedu.address.logic.graphs.ViewScheduleGraph;
import seedu.address.model.Model;

/**
 * Represents a generator of graphs for autocomplete support.
 */
class GraphGenerator {

    private final Model model;
    private final Map<String, Graph> graphs;

    GraphGenerator(Model model) {
        this.model = model;
        graphs = new HashMap<>();

        buildGraphs();
    }

    /**
     * Instantiates supporting graphs.
     */
    private void buildGraphs() {
        // Customer commands
        graphs.put("switch-c", Graph.emptyGraph(model));
        graphs.put("add-c", new AddCustomerGraph(model));
        graphs.put("delete-c", new DeleteCustomerGraph(model));
        graphs.put("find-c", new FindCustomerGraph(model));
        graphs.put("list-c", Graph.emptyGraph(model));
        graphs.put("clear-c", Graph.emptyGraph(model));
        graphs.put("edit-c", new EditCustomerGraph(model));
        graphs.put("copy-c", new CopyCustomerGraph(model));

        // Phone commands
        graphs.put("switch-p", Graph.emptyGraph(model));
        graphs.put("add-p", new AddPhoneGraph(model));
        graphs.put("delete-p", new DeletePhoneGraph(model));
        graphs.put("find-p", new FindPhoneGraph(model));
        graphs.put("list-p", Graph.emptyGraph(model));
        graphs.put("clear-p", Graph.emptyGraph(model));
        graphs.put("edit-p", new EditPhoneGraph(model));
        graphs.put("copy-p", new CopyPhoneGraph(model));

        // Order commands
        graphs.put("switch-o", Graph.emptyGraph(model));
        graphs.put("add-o", new AddOrderGraph(model));
        graphs.put("find-o", new FindOrderGraph(model));
        graphs.put("complete", new CompleteOrderGraph(model));
        graphs.put("cancel", new CancelOrderGraph(model));
        graphs.put("list-o", Graph.emptyGraph(model));
        graphs.put("clear-o", Graph.emptyGraph(model));
        graphs.put("edit-o", new EditOrderGraph(model));
        graphs.put("copy-o", new CopyOrderGraph(model));

        // Schedule commands
        graphs.put("switch-s", Graph.emptyGraph(model));
        graphs.put("schedule", new ViewScheduleGraph(model));
        graphs.put("add-s", new AddScheduleGraph(model));
        graphs.put("delete-s", new DeleteScheduleGraph(model));
        graphs.put("edit-s", new EditScheduleGraph(model));

        // Archived order commands
        graphs.put("switch-a", Graph.emptyGraph(model));

        // General commands
        graphs.put("undo", Graph.emptyGraph(model));
        graphs.put("redo", Graph.emptyGraph(model));
        graphs.put("history", Graph.emptyGraph(model));
        graphs.put("generate-s", new GenerateStatsGraph(model));
        graphs.put("exit", Graph.emptyGraph(model));
        graphs.put("help", Graph.emptyGraph(model));
        graphs.put("export", Graph.emptyGraph(model)); //TODO SPECIAL
    }

    private Optional<Graph> getGraph(String commandWord) {
        return Optional.ofNullable(graphs.get(commandWord));
    }

    private Set<String> getCommandWords() {
        return graphs.keySet();
    }

    /**
     * Processes an input string and returns possible autocomplete results.
     * @param input A user input string.
     * @return An {@code AutoCompleteResult} containing possible autocomplete values.
     */
    AutoCompleteResult process(String input) {
        int firstSpace = input.indexOf(" ");
        if (firstSpace == -1) { // there is no space, indicating still typing command word
            SortedSet<String> values = new TreeSet<>(getCommandWords());
            return new AutoCompleteResult(values, input);
        } else { // there is at least one space, suggesting command word is present
            String commandWord = input.substring(0, firstSpace);
            Optional<Graph> graph = getGraph(commandWord);
            if (graph.isPresent()) { // command word is supported
                String args = input.substring(firstSpace);
                return graph.get().process(args);
            } else { // command word not supported, return empty set
                return new AutoCompleteResult(Collections.emptySortedSet(), input);
            }
        }
    }

}
