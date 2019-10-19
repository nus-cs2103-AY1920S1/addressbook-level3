package seedu.address.model.statistics;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.ArrayList;
import java.util.HashMap;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class Statistics {

    private HashMap<String, HashMap<String, Double>> data; //mapping of name to map of {subject, scores}
    private ArrayList<StudentStat> studentWeightedScores;
    private HashMap<Integer, Integer> scoreCounters;
    private HashMap<String, Integer> gradeGroupings;
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

    public void initializeData() {
        gradeGroupings.put("aboveEighty", 0);
        gradeGroupings.put("seventies", 0);
        gradeGroupings.put("sixties", 0);
        gradeGroupings.put("fifties", 0);
        gradeGroupings.put("belowFifty", 0);
    }

    public void processData() {
        data.forEach((name, subjectScoreMap) -> studentWeightedScores.add(new StudentStat(name, subjectScoreMap)));
        studentWeightedScores.sort((s1, s2) -> s1.weightedScore >= s2.weightedScore ? 1 : -1);
        studentWeightedScores.stream().forEach(x -> sortIntoGrade(x));
        studentWeightedScores
                .stream()
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

    private void allocateDistribution(int score) {
        if (scoreCounters.containsKey(score)) {
            scoreCounters.put(score, scoreCounters.get(score) + 1);
        }
        else {
            scoreCounters.put(score, 1);
        }
    }

    public HashMap<Integer, Integer> getFrequencyDistribution() {
        return scoreCounters;
    }

    private void sortIntoGrade(StudentStat studentStat) {
        if (studentStat.weightedScore >= 80) {
            gradeGroupings.put("aboveEighty", gradeGroupings.get("aboveEighty") + 1);
        }
        else if (studentStat.weightedScore >= 70) {
            gradeGroupings.put("seventies", gradeGroupings.get("seventies") + 1);
        }
        else if (studentStat.weightedScore >= 60) {
            gradeGroupings.put("sixties", gradeGroupings.get("sixties") + 1);
        }
        else if (studentStat.weightedScore >= 50) {
            gradeGroupings.put("fifties", gradeGroupings.get("fifties") + 1);
        }
        else {
            gradeGroupings.put("belowFifty", gradeGroupings.get("belowFifty") + 1);
        }
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

    private class StudentStat {
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

    class ScoreCounter {
        double score;
        double count;

        public ScoreCounter(double score) {
            this.score = score;
            count = 0;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            else if (other instanceof ScoreCounter) {
                return ((ScoreCounter) other).score == this.score;
            }
            return false;
        }

    }
}
