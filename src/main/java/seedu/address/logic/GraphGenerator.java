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
import seedu.address.logic.graphs.GenerateStatsGraph;
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
        graphs.put("delete-c", Graph.emptyGraph(model));
        graphs.put("find-c", Graph.emptyGraph(model));
        graphs.put("list-c", Graph.emptyGraph(model));
        graphs.put("clear-c", Graph.emptyGraph(model));
        graphs.put("edit-c", Graph.emptyGraph(model));
        graphs.put("copy-c", Graph.emptyGraph(model));

        // Phone commands
        graphs.put("switch-p", Graph.emptyGraph(model));
        graphs.put("add-p", new AddPhoneGraph(model));
        graphs.put("delete-p", Graph.emptyGraph(model));
        graphs.put("find-p", Graph.emptyGraph(model));
        graphs.put("list-p", Graph.emptyGraph(model));
        graphs.put("clear-p", Graph.emptyGraph(model));
        graphs.put("edit-p", Graph.emptyGraph(model));
        graphs.put("copy-p", Graph.emptyGraph(model));

        // Order commands
        graphs.put("switch-o", Graph.emptyGraph(model));
        graphs.put("add-o", new AddOrderGraph(model));
        graphs.put("find-o", Graph.emptyGraph(model));
        graphs.put("complete", Graph.emptyGraph(model));
        graphs.put("cancel", Graph.emptyGraph(model));
        graphs.put("list-o", Graph.emptyGraph(model));
        graphs.put("clear-o", Graph.emptyGraph(model));
        graphs.put("edit-o", Graph.emptyGraph(model));
        graphs.put("copy-o", Graph.emptyGraph(model));

        // Schedule commands
        graphs.put("switch-s", Graph.emptyGraph(model));
        graphs.put("schedule", Graph.emptyGraph(model));
        graphs.put("add-s", new AddScheduleGraph(model));
        graphs.put("delete-s", Graph.emptyGraph(model));
        graphs.put("edit-s", Graph.emptyGraph(model));

        // Archived order commands
        graphs.put("switch-a", Graph.emptyGraph(model));

        // General commands
        graphs.put("undo", Graph.emptyGraph(model));
        graphs.put("redo", Graph.emptyGraph(model));
        graphs.put("history", Graph.emptyGraph(model));
        graphs.put("generate-s", new GenerateStatsGraph(model));
        graphs.put("exit", Graph.emptyGraph(model));
        graphs.put("help", Graph.emptyGraph(model));
        graphs.put("export", Graph.emptyGraph(model));
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
