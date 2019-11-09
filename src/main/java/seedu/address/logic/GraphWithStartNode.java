package seedu.address.logic;

import java.util.List;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Pattern prefixPattern = Pattern.compile(" .{1,2}/");
        String stringToCompare = input;
        AutoCompleteNode<?> currentNode = startingNode;
        SortedSet<String> values = new TreeSet<>();
        Matcher matcher = prefixPattern.matcher(input);
        while (matcher.find()) {
            Prefix prefix = new Prefix(matcher.group().trim());
            Optional<AutoCompleteNode<?>> nextNode = traverse(currentNode, prefix);
            if (nextNode.isPresent()) {
                currentNode = nextNode.get();
            }
            stringToCompare = input.substring(matcher.end());
        }
        if (input.endsWith("/")) { // fill with possible arguments
            values.addAll(currentNode.getValues());
        } else { // fill with possible prefixes
            List<Prefix> prefixes = getWeights(currentNode);
            prefixes.forEach(prefix -> values.add(prefix.toString()));
            stringToCompare = stringToCompare.substring(stringToCompare.lastIndexOf(" ") + 1);
        }
        return new AutoCompleteResult(values, stringToCompare);
    }

}
