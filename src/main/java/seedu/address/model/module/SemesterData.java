package seedu.address.model.module;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * A list of semesters
 */
public class SemesterData {
    private final ArrayList<Semester> semester = new ArrayList<>();

    public SemesterData(JSONArray arr) {
        int i;
        for (i = 0; i < arr.size(); i++) {
            semester.add(new Semester((JSONObject) arr.get(i)));
        }
    }

    public Semester getSemester(String semesterNo) {
        int i;
        for (i = 0; i < semester.size(); i++) {
            if (semester.get(i).getSemesterNo().equals(semesterNo)) {
                return semester.get(i);
            }
        }
        System.out.println("Error: No such semesterNo found");
        return null;
    }
}
