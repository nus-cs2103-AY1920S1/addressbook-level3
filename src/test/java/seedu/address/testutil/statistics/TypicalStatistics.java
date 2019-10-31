package seedu.address.testutil.statistics;

import java.util.HashMap;

import seedu.address.model.statistics.Statistics;
import seedu.address.model.statistics.StatisticsRecord;

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
        nullMap = null;
        singleValueMap = populateSingleValueMap();
        typicalMap = populateTypicalMap();
    }

    /**
     * Creates a HashMap data of a single data entry for testing.
     */
    public HashMap<String, HashMap<String, Double>> populateSingleValueMap() {
        HashMap<String, Double> subject = new HashMap<>();
        subject.put("Science", 72.0);
        HashMap<String, HashMap<String, Double>> singleValueMap = new HashMap<>();
        singleValueMap.put("Jake", subject);
        return singleValueMap;
    }

    /**
     * Creates a size 3 HashMap data of multiple data entries for testing.
     * Size: 3
     * Min: 10
     * Max: 100
     * Median: 60
     */
    public HashMap<String, HashMap<String, Double>> populateTypicalMap() {
        HashMap<String, Double> subject = new HashMap<>();
        subject.put("Science", 50.5);
        subject.put("Math", 60.0);
        subject.put("English", 69.5);

        HashMap<String, Double> subject1 = new HashMap<>();
        subject1.put("Science", 100.0);
        subject1.put("Math", 100.0);
        subject1.put("English", 100.0);

        HashMap<String, Double> subject2 = new HashMap<>();
        subject2.put("Science", 10.0);
        subject2.put("Math", 15.0);
        subject2.put("English", 5.0);

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
