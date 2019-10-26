package seedu.address.model.statistics;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

/**
 * Represents a Statistics report generated from input data.
 */
public class Statistics {

    //Grade Ranges
    public static final String EIGHTY_AND_ABOVE = "eightyAndAbove";
    public static final String SEVENTY_TO_SEVENTY_NINE = "seventies";
    public static final String SIXTY_TO_SIXTY_NINE = "sixties";
    public static final String FIFTY_TO_FIFTY_NINE = "fifties";
    public static final String BELOW_FIFTY = "belowFifty";

    private HashMap<String, HashMap<String, Double>> data; //mapping of name to map of {subject, scores}
    private ArrayList<StudentStat> studentWeightedScores;
    private HashMap<Integer, Integer> scoreCounters; //frequency of individual (rounded down) scores
    private HashMap<String, Integer> gradeGroupings; //frequency of score ranges
    private int totalStudents;
    private double min;
    private double max;
    private double mean;
    private double median;
    private double standardDev;

    /**
     * Creates a new question.
     *
     * @param data mapping of name to score to set.
     */
    public Statistics(HashMap<String, HashMap<String, Double>> data) {
        requireAllNonNull(data);
        this.data = data;
        this.studentWeightedScores = new ArrayList<>();
        this.gradeGroupings = new HashMap<>();
        this.scoreCounters = new HashMap<>();
        initializeData();
        processData();
    }

    /**
     * Initializes the groupings's size to zero before populating them with processed data.
     */
    public void initializeData() {
        gradeGroupings.put(EIGHTY_AND_ABOVE, 0);
        gradeGroupings.put(SEVENTY_TO_SEVENTY_NINE, 0);
        gradeGroupings.put(SIXTY_TO_SIXTY_NINE, 0);
        gradeGroupings.put(FIFTY_TO_FIFTY_NINE, 0);
        gradeGroupings.put(BELOW_FIFTY, 0);
    }

    /**
     * Generate the processed data.
     * Finds: ranking, total data entries, min, max, mean, median, standard deviation.
     */
    public void processData() {
        data.forEach((name, subjectScoreMap) -> studentWeightedScores.add(new StudentStat(name, subjectScoreMap)));
        studentWeightedScores.sort((s1, s2) -> s1.weightedScore >= s2.weightedScore ? 1 : -1);
        studentWeightedScores.stream().forEach(x -> sortIntoGrade(x));
        studentWeightedScores.stream()
            .mapToInt(studentStat -> (int) studentStat.weightedScore)
            .forEach(score -> allocateDistribution(score));

        DescriptiveStatistics statsGenerator = new DescriptiveStatistics();
        studentWeightedScores.stream().forEach((dataValue) -> statsGenerator.addValue(dataValue.weightedScore));
        totalStudents = (int) statsGenerator.getN();
        min = statsGenerator.getMin();
        max = statsGenerator.getMax();
        mean = statsGenerator.getMean();
        median = statsGenerator.getPercentile(50);
        standardDev = statsGenerator.getStandardDeviation();
    }

    /**
     * Updates the counter for number of students who got the same score.
     * @param score
     */
    private void allocateDistribution(int score) {
        if (scoreCounters.containsKey(score)) {
            scoreCounters.put(score, scoreCounters.get(score) + 1);
        } else {
            scoreCounters.put(score, 1);
        }
    }

    /**
     * Places each {@code StudentStat} into their respective score range groupings and updates the counter.
     * @param studentStat
     */
    private void sortIntoGrade(StudentStat studentStat) {
        if (studentStat.weightedScore >= 80) {
            gradeGroupings.put(EIGHTY_AND_ABOVE, gradeGroupings.get(EIGHTY_AND_ABOVE) + 1);
        } else if (studentStat.weightedScore >= 70) {
            gradeGroupings.put(SEVENTY_TO_SEVENTY_NINE, gradeGroupings.get(SEVENTY_TO_SEVENTY_NINE) + 1);
        } else if (studentStat.weightedScore >= 60) {
            gradeGroupings.put(SIXTY_TO_SIXTY_NINE, gradeGroupings.get(SIXTY_TO_SIXTY_NINE) + 1);
        } else if (studentStat.weightedScore >= 50) {
            gradeGroupings.put(FIFTY_TO_FIFTY_NINE, gradeGroupings.get(FIFTY_TO_FIFTY_NINE) + 1);
        } else {
            gradeGroupings.put(BELOW_FIFTY, gradeGroupings.get(BELOW_FIFTY) + 1);
        }
    }

    public HashMap<Integer, Integer> getFrequencyDistribution() {
        return scoreCounters;
    }

    public HashMap<String, Integer> getGradeGroupings() {
        return gradeGroupings;
    }

    public void setData(HashMap<String, HashMap<String, Double>> data) {
        this.data = data;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getMean() {
        return mean;
    }

    public double getMedian() {
        return median;
    }

    public double getStandardDev() {
        return standardDev;
    }

    public int getTotalStudents() {
        return totalStudents;
    }

    /**
     * Represents a student data entry and their respective scores.
     */
    private class StudentStat {
        private String name;
        private double weightedScore;
        private HashMap<String, Double> subjectScoreMap;

        public StudentStat(String name, HashMap<String, Double> subjectScoreMap) {
            this.name = name;
            this.subjectScoreMap = subjectScoreMap;
            calcWeightedScore();
        }

        public void calcWeightedScore() {
            int noSubjects = subjectScoreMap.size();
            double total = subjectScoreMap.values().stream().mapToDouble(score -> Double.valueOf(score)).sum();
            weightedScore = total / noSubjects;
        }
    }
}
