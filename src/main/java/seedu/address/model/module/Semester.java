package seedu.address.model.module;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * The semester of the module
 */
public class Semester {
    private final SemesterNo semesterNo;
    private final ArrayList<Lesson> timetable = new ArrayList<>();
    private final Exam exam;

    public Semester(SemesterNo semesterNo, List<Lesson> timetable) {
        requireNonNull(semesterNo);
        requireNonNull(timetable);
        this.semesterNo = semesterNo;
        this.exam = null;
        this.timetable.addAll(timetable);
    }

    public Semester(SemesterNo semesterNo, List<Lesson> timetable,
                    Exam exam) {
        requireNonNull(semesterNo);
        requireNonNull(timetable);
        requireNonNull(exam);
        this.semesterNo = semesterNo;
        this.exam = exam;
        this.timetable.addAll(timetable);
    }

    /**
     * Find all Lessons with the given lesson type and lesson number.
     * @param lessonType LessonType of Lesson.
     * @param lessonNo LessonNo Lesson.
     * @return ArrayList of Lessons matching the lesson number, returns empty list if no matching lessons.
     */
    public ArrayList<Lesson> findLessons(LessonType lessonType, LessonNo lessonNo) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        for (Lesson lesson : timetable) {
            if (lesson.getLessonType().equals(lessonType) && lesson.getLessonNo().equals(lessonNo)) {
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

    public Optional<Exam> getExam() {
        return Optional.ofNullable(exam);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(semesterNo.toString()).append("\n");

        if (exam != null) {
            result.append(exam).append("\n");
        } else {
            result.append("No Exam\n");
        }

        result.append("Timetable:\n");
        for (int i = 0; i < timetable.size(); i++) {
            result.append(timetable.get(i).toString()).append("\n");
        }

        return result.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Semester)) {
            return false;
        }
        Semester s = (Semester) other;
        if (s == this) {
            return true;
        } else if (s.semesterNo.equals(this.semesterNo)
                && s.timetable.equals(this.timetable)
                && s.exam.equals(this.exam)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(semesterNo, timetable, exam);
    }
}
