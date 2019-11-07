package seedu.address.logic;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public abstract class GraphWithStartNodeAndPreamble extends GraphWithStartNode {

    private List<?> dataList;

    public GraphWithStartNodeAndPreamble(Model model) {
        super(model);
    }

    protected void setDataList(List<?> dataList) {
        this.dataList = dataList;
    }

    @Override
    protected AutoCompleteResult process(String input) {
        SortedSet<String> values = new TreeSet<>();
        String stringToCompare;

        if (input.isBlank()) { // empty, all whitespaces, or null
            // suggest indexes
            int minIndex = 1;
            int maxIndex = dataList.size();
            values.add(String.valueOf(minIndex));
            values.add(String.valueOf(maxIndex));
            stringToCompare = "";
        } else {
            int secondSpace = input.stripLeading().indexOf(" ");
            if (secondSpace == -1) {
                // user is entering preamble
                // suggest indexes
                int minIndex = 1;
                int maxIndex = dataList.size();
                values.add(String.valueOf(minIndex));
                values.add(String.valueOf(maxIndex));
                stringToCompare = input.stripLeading();
            } else {
                String preamble = input.stripLeading().substring(0, secondSpace);
                try {
                    // Parse to ensure it is a valid index, even though the index is used
                    ParserUtil.parseIndex(preamble);
                    String argString = input.substring(preamble.length() + 1);
                    return super.process(argString);
                } catch (ParseException e) {
                    // preamble is not an integer
                    // suggest indexes
                    int minIndex = 1;
                    int maxIndex = dataList.size();
                    values.add(String.valueOf(minIndex));
                    values.add(String.valueOf(maxIndex));
                    stringToCompare = "";
                }
            }
        }
        return new AutoCompleteResult(values, stringToCompare);
    }

}
