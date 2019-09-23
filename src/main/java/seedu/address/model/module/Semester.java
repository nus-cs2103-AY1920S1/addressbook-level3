package seedu.address.model.module;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * The semester of the module
 */
public class Semester {
    private final String semesterNo;
    private final ArrayList<Timetable> timetable = new ArrayList<>();

    public Semester(JSONObject obj) {
        this.semesterNo = obj.get("semester").toString();
        JSONArray arr = (JSONArray) obj.get("timetable");

        int i;
        for (i = 0; i < arr.size(); i++) {
            timetable.add(new Timetable((JSONObject) arr.get(i)));
        }
    }

    public Timetable getTimetable(String classNo) {
        int i;
        for (i = 0; i < timetable.size(); i++) {
            if (timetable.get(i).getClassNo().equals(classNo)) {
                return timetable.get(i);
            }
        }
        System.out.println("Error: No such classNo found");
        return null;
    }

    public String getSemesterNo() {
        return semesterNo;
    }

    @Override
    public String toString() {
        String s = "Semester " + semesterNo;
        return s;
    }
}
