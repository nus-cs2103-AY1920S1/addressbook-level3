package seedu.address.logic.autocomplete.graphs;

import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import seedu.address.logic.autocomplete.AutoCompleteResult;
import seedu.address.logic.autocomplete.AutoCompleteResultProvider;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

/**
 * Represents a {@code Graph} that supports commands that accept a preamble only.
 */
public abstract class GraphWithPreamble<T> extends GraphBuiltFromModel<T> implements AutoCompleteResultProvider {

    private List<?> dataList;

    public GraphWithPreamble(Model model) {
        super(model);
    }

    protected void setDataList(List<?> dataList) {
        this.dataList = dataList;
    }

    @Override
    public AutoCompleteResult process(String input) {
        SortedSet<String> values = new TreeSet<>();
        String stringToCompare;

        if (input.isBlank()) { // empty, all whitespaces, or null
            // suggest indexes
            int minIndex = 1;
            int maxIndex = dataList.size();
            if (maxIndex > 0) {
                values.add(String.valueOf(minIndex));
                values.add(String.valueOf(maxIndex));
            }
            stringToCompare = "";
        } else {
            int secondSpace = input.stripLeading().indexOf(" ");
            if (secondSpace == -1) {
                // user is entering preamble
                // suggest indexes
                int minIndex = 1;
                int maxIndex = dataList.size();
                if (maxIndex > 0) {
                    values.add(String.valueOf(minIndex));
                    values.add(String.valueOf(maxIndex));
                }
                stringToCompare = input.stripLeading();
            } else {
                String preamble = input.stripLeading().substring(0, secondSpace);
                try {
                    // Parse to ensure it is a valid index, even though the index is used
                    ParserUtil.parseIndex(preamble);
                    // If it is valid, suggest nothing
                    return new AutoCompleteResult(Collections.emptySortedSet(), "");
                } catch (ParseException e) {
                    // preamble is not an integer
                    // suggest indexes
                    int minIndex = 1;
                    int maxIndex = dataList.size();
                    if (maxIndex > 0) {
                        values.add(String.valueOf(minIndex));
                        values.add(String.valueOf(maxIndex));
                    }
                    stringToCompare = "";
                }
            }
        }
        return new AutoCompleteResult(values, stringToCompare);
    }

}
