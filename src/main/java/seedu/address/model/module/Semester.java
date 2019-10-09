package seedu.address.model.module;

import java.util.ArrayList;
import java.util.List;

/**
 * The semester of the module
 */
public class Semester {
    private final SemesterNo semesterNo;
    private final ArrayList<Lesson> timetable = new ArrayList<>();
    private final Exam exam;

    public Semester(SemesterNo semesterNo, List<Lesson> timetable,
                    Exam exam) {
        this.semesterNo = semesterNo;
        this.exam = exam;
        this.timetable.addAll(timetable);
    }

    /**
     * Find all Lessons with the given lesson number string.
     * @param lessonNo Lesson number string.
     * @return ArrayList of Lessons matching the lesson number, returns empty list if no matching lessons.
     */
    public ArrayList<Lesson> findLessons(LessonNo lessonNo) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        for (Lesson lesson : timetable) {
            if (lesson.getLessonNo().equals(lessonNo)) {
                lessons.add(lesson);
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

    public Exam getExam() {
        return exam;
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
