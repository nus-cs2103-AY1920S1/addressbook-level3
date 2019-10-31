package seedu.address.testutil.statistics;

import seedu.address.model.note.Note;
import seedu.address.model.note.NotesRecord;
import seedu.address.model.note.Priority;
import seedu.address.model.statistics.Statistics;
import seedu.address.model.statistics.StatisticsRecord;
import seedu.address.testutil.note.NoteBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * A utility class containing a typical {@code Statistics} objects to be used in tests.
 */
public class TypicalStatistics {

    private HashMap<String, HashMap<String, Double>> emptyMap;
    private HashMap<String, HashMap<String, Double>> nullMap;
    private HashMap<String, HashMap<String, Double>> singleValueMap;
    private HashMap<String, HashMap<String, Double>> typicalMap;

    public TypicalStatistics() {
        emptyMap = new HashMap<>();
        nullMap  = null;
        singleValueMap = populateSingleValueMap();
        typicalMap = populateTypicalMap();
    }

    public HashMap<String, HashMap<String, Double>> populateSingleValueMap() {
        HashMap<String, Double> subject = new HashMap<>();
        subject.put("Science", 72.0);
        HashMap<String, HashMap<String, Double>> singleValueMap = new HashMap<>();
        singleValueMap.put("Jake", subject);
        return singleValueMap;
    }

    public HashMap<String, HashMap<String, Double>> populateTypicalMap() {
        HashMap<String, Double> subject = new HashMap<>();
        subject.put("Science", 75.5);
        subject.put("Math", 100.0);
        subject.put("English", 80.0);

        HashMap<String, Double> subject1 = new HashMap<>();
        subject1.put("Science", 20.0);
        subject1.put("Math", 100.0);
        subject1.put("English", 50.0);

        HashMap<String, Double> subject2 = new HashMap<>();
        subject2.put("Science", 50.0);
        subject2.put("Math", 50.0);
        subject2.put("English", 50.0);

        HashMap<String, HashMap<String, Double>> typicalMap = new HashMap<>();
        typicalMap.put("Jason", subject);
        typicalMap.put("Susan", subject1);
        typicalMap.put("Lily", subject2);
        return typicalMap;
    }

    /**
     * Returns an {@code StatisticsRecord} with all the typical statistics.
     */
    public StatisticsRecord getTypicalStatisticsRecord() {
        StatisticsRecord sr = new StatisticsRecord();
        sr.setStatistics(new Statistics(typicalMap));
        return sr;
    }

    public Statistics getTypicalStatistics() {
        return new Statistics(typicalMap);
    }

    public Statistics getEmptyDataStatistics() {
        return new Statistics(emptyMap);
    }

    public Statistics getNullDataStatistics() {
        return new Statistics(nullMap);
    }
    public Statistics getSingleValueStatistics() {
        return new Statistics(singleValueMap);
    }
}
