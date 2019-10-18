package seedu.address.model.statistics;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class Statistics {

    protected HashMap<String, HashMap<String, Double>> data; //mapping of name to {subject, scores}

    /**
     * Creates a new question.
     *
     * @param data mapping of name to score to set.
     */
    public Statistics(HashMap<String, HashMap<String, Double>> data) {
        requireAllNonNull(data);
        this.data = data;
    }


}
