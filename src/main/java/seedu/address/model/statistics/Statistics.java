package seedu.address.model.statistics;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.ArrayList;
import java.util.HashMap;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class Statistics {

    protected HashMap<String, HashMap<String, Double>> data; //mapping of name to map of {subject, scores}
    private ArrayList<StudentStat> studentWeightedScores;
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
        processData();
    }

//    public Statistics(HashMap<String, HashMap<String, Double>> data) {
//        requireAllNonNull(data);
//        this.data = data;
//        this.studentWeightedScores = new ArrayList<>();
//        processData();
//    }

    public void processData() {
        data.forEach((name, subjectScoreMap) -> studentWeightedScores.add(new StudentStat(name, subjectScoreMap)));
        studentWeightedScores.sort((s1, s2) -> s1.weightedScore >= s2.weightedScore ? 1 : -1);
        DescriptiveStatistics statsGenerator = new DescriptiveStatistics();
        studentWeightedScores.stream().forEach((dataValue) -> statsGenerator.addValue(dataValue.weightedScore));

        totalStudents = (int) statsGenerator.getN();
        min = statsGenerator.getMin();
        max = statsGenerator.getMax();
        mean = statsGenerator.getMean();
        median = statsGenerator.getPercentile(50);
        standardDev = statsGenerator.getStandardDeviation();

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

    class StudentStat {
        protected String name;
        protected double weightedScore;
        protected HashMap<String, Double> subjectScoreMap;

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
