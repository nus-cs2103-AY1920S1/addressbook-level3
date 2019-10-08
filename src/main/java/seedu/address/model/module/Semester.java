package seedu.address.model.module;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * The semester of the module.
 */
public class Semester {
    private final SemesterNo semesterNo;
    private final ArrayList<Lesson> timetable = new ArrayList<>();
    private final ExamDate examDate;
    private final ExamDuration examDuration;

    public Semester(JSONObject obj) {
        if (obj.containsKey("semester")) {
            this.semesterNo = new SemesterNo(obj.get("semester").toString());
        } else {
            this.semesterNo = new SemesterNo("");
        }

        if (obj.containsKey("timetable")) {
            JSONArray arr = (JSONArray) obj.get("timetable");
            for (int i = 0; i < arr.size(); i++) {
                timetable.add(new Lesson((JSONObject) arr.get(i)));
            }
        }

        if (obj.containsKey("examDate")) {
            this.examDate = new ExamDate(obj.get("examDate").toString());
        } else {
            this.examDate = new ExamDate("");
        }

        if (obj.containsKey("examDuration")) {
            this.examDuration = new ExamDuration(obj.get("examDuration").toString());
        } else {
            this.examDuration = new ExamDuration("");
        }
    }

    public Semester(SemesterNo semesterNo, List<Lesson> timetable,
                    ExamDate examDate, ExamDuration examDuration) {
        this.semesterNo = semesterNo;
        this.examDate = examDate;
        this.examDuration = examDuration;
        this.timetable.addAll(timetable);
    }

    /**
     * Find all Lessons with the given lesson number string.
     * @param lessonNo Lesson number string.
     * @return ArrayList of Lessons matching the lesson number, returns empty list if no matching lessons.
     */
    public ArrayList<Lesson> findLessons(LessonNo lessonNo) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        for (int i = 0; i < timetable.size(); i++) {
            if (timetable.get(i).getLessonNo().equals(lessonNo)) {
                lessons.add(timetable.get(i));
            }
        }
        return lessons;
    }

    public ArrayList<Lesson> getTimetable() {
        return timetable;
    }

    public SemesterNo getSemesterNo() {
        return semesterNo;
    }


    public ExamDate getExamDate() {
        return examDate;
    }

    public ExamDuration getExamDuration() {
        return examDuration;
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
