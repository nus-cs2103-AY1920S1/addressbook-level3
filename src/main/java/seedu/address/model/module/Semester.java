package seedu.address.model.module;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * The semester of the module.
 */
public class Semester {
    private final String semesterNo;
    private final ArrayList<Lesson> timetable = new ArrayList<>();
    private final String examDate;
    private final String examDuration;

    public Semester(JSONObject obj) {
        this.semesterNo = obj.get("semester").toString();

        JSONArray arr = (JSONArray) obj.get("timetable");

        for (int i = 0; i < arr.size(); i++) {
            timetable.add(new Lesson((JSONObject) arr.get(i)));
        }

        this.examDate = obj.get("examDate").toString();
        this.examDuration = obj.get("examDuration").toString();
    }

    public Lesson getLesson(String lessonNo) {
        for (int i = 0; i < timetable.size(); i++) {
            if (timetable.get(i).getLessonNo().equals(lessonNo)) {
                return timetable.get(i);
            }
        }
        System.out.println("Error: No such lessonNo found");
        return null;
    }

    public String getSemesterNo() {
        return semesterNo;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Semester ").append(semesterNo).append("\n");

        result.append("Timetable:\n");
        for (int i = 0; i < timetable.size(); i++) {
            result.append(timetable.get(i).toString()).append("\n");
        }

        return result.toString();
    }
}
