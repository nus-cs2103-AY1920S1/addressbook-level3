package seedu.address.model.cap.util;

import java.util.HashMap;

/**
 * Constructs a Hashmap take stores the corresponding grades and their grade points.
 */
public class GradeHash {

    private HashMap<String, Double> gradeMap;

    public GradeHash() {
        gradeMap = new HashMap<>();
        gradeMap.put("A+", 5.0);
        gradeMap.put("A", 5.0);
        gradeMap.put("A-", 4.5);
        gradeMap.put("B+", 4.0);
        gradeMap.put("B", 3.5);
        gradeMap.put("B-", 3.0);
        gradeMap.put("C+", 2.5);
        gradeMap.put("C", 2.0);
        gradeMap.put("D+", 1.5);
        gradeMap.put("D", 1.0);
        gradeMap.put("F", 0.0);
    }

    public double convertToGradePoint(String grade) {
        return gradeMap.get(grade);
    }
}
