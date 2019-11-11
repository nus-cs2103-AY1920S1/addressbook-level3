package seedu.address.logic.autocomplete.graphs;

import static seedu.address.commons.util.ProviderUtil.extractPreamble;
import static seedu.address.commons.util.ProviderUtil.hasCompletePreamble;
import static seedu.address.commons.util.ProviderUtil.isValidIndex;
import static seedu.address.commons.util.ProviderUtil.populateValuesWithIndexes;
import static seedu.address.logic.parser.ParserUtil.parseIndex;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.autocomplete.AutoCompleteResult;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a {@code Graph} that supports commands that accept both a preamble and arguments.
 */
public abstract class GraphWithStartNodeAndPreamble extends GraphWithStartNode {

    private final List<?> dataList;

    public GraphWithStartNodeAndPreamble(List<?> dataList) {
        super();
        this.dataList = dataList;
    }

    @Override
    public AutoCompleteResult process(String input) {
        SortedSet<String> values = new TreeSet<>();
        String stringToCompare = input; // dummy value

        if (input.isBlank()) { // empty, all whitespaces, or null
            // suggest indexes
            populateValuesWithIndexes(values, dataList);
            // match any
            stringToCompare = "";
        } else {
            if (!hasCompletePreamble(input)) {
                // user is entering preamble
                // suggest indexes
                populateValuesWithIndexes(values, dataList);
                // match partially entered preamble
                stringToCompare = input.stripLeading();
            } else {
                String preamble = extractPreamble(input);
                try {
                    // parse to ensure it is a valid index
                    Index index = parseIndex(preamble);
                    boolean valid = isValidIndex(index, dataList);
                    if (valid) {
                        // if valid, extract arguments
                        String argString = input.stripLeading().substring(preamble.length());
                        // return autocomplete result from arguments
                        return super.process(argString);
                    }
                } catch (ParseException e) {
                    // preamble is invalid
                    // suggest nothing
                }
            }
        }
        return new AutoCompleteResult(values, stringToCompare);
    }

}
