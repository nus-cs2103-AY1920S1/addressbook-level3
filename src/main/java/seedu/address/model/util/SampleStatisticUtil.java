package seedu.address.model.util;

import java.util.HashMap;

import seedu.address.model.statistics.ReadOnlyStatisticsRecord;
import seedu.address.model.statistics.Statistics;
import seedu.address.model.statistics.StatisticsRecord;

/**
 * Contains utility methods for populating Statistics with sample data.
 */
public class SampleStatisticUtil {

    private static HashMap<String, HashMap<String, Double>> sampleData = new HashMap<>();

    public static Statistics getSampleStatistics() {
        HashMap<String, Double>subjScoreMap = new HashMap<>();
        subjScoreMap.put("Science", 72.0);
        sampleData.put("Jason", subjScoreMap);
        return new Statistics(sampleData);
    }

    public static ReadOnlyStatisticsRecord getSampleStatisticsRecord() {
        StatisticsRecord sampleRc = new StatisticsRecord();
        sampleRc.setStatistics(getSampleStatistics());
        return sampleRc;
    }

}
