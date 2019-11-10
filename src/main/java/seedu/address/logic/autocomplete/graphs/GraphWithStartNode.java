package seedu.address.logic.autocomplete.graphs;

import java.util.List;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.autocomplete.AutoCompleteResult;
import seedu.address.logic.autocomplete.nodes.AutoCompleteNode;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;

/**
 * Represents a {@code Graph} that supports commands that accept arguments only.
 */
public abstract class GraphWithStartNode extends GraphBuiltFromModel<Prefix> {

    private AutoCompleteNode<?> startingNode;

    public GraphWithStartNode(Model model) {
        super(model);
    }

    protected void setStartingNode(AutoCompleteNode<?> startingNode) {
        this.startingNode = startingNode;
    }

    @Override
    public AutoCompleteResult process(String input) {
        SortedSet<String> values = new TreeSet<>();
        String stringToCompare;

        Pattern prefixPattern = Pattern.compile(" .{1,2}/");
        Matcher matcher = prefixPattern.matcher(input);

        AutoCompleteNode<?> currentNode = startingNode;
        int offset = -1; // offset to track end index of last matched region

        while (matcher.find()) {
            Prefix prefix = new Prefix(matcher.group().stripLeading());
            Optional<AutoCompleteNode<?>> nextNode = traverse(currentNode, prefix);
            if (nextNode.isPresent()) {
                currentNode = nextNode.get();
            }
            offset = matcher.end(); // set offset to end index of last matched region
        }

        if (offset == -1) { // no prefix entered
            // suggest prefixes
            List<Prefix> prefixes = getWeights(currentNode);
            prefixes.forEach(p -> values.add(p.toString()));
            // match input
            stringToCompare = input.stripLeading();
        } else { // there is at least one prefix entered
            // determine index of last space
            int lastSpace = input.lastIndexOf(" ");
            if (offset > lastSpace) { // if prefix is fully entered and no space after that, assume entering argument
                // suggest possible values
                values.addAll(currentNode.getValues());
                stringToCompare = input.substring(offset); // match the partially entered argument
            } else {
                // suggest possible prefixes
                List<Prefix> prefixes = getWeights(currentNode);
                prefixes.forEach(p -> values.add(p.toString()));
                stringToCompare = input.substring(lastSpace + 1); // match the partially entered prefix
            }
        }

        return new AutoCompleteResult(values, stringToCompare);
    }

}
