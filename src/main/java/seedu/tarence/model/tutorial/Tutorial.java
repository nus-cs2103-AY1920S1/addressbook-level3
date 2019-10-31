package seedu.tarence.model.tutorial;

import static seedu.tarence.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import seedu.tarence.commons.core.index.Index;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.exceptions.AssignmentNotFoundException;
import seedu.tarence.model.tutorial.exceptions.DuplicateAssignmentException;
import seedu.tarence.model.tutorial.exceptions.DuplicateEventException;
import seedu.tarence.model.tutorial.exceptions.InvalidScoreException;
import seedu.tarence.model.tutorial.exceptions.StudentNotFoundException;

/**
 * Represents a Tutorial.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Tutorial {
    public static final int NOT_SUBMITTED = -1;
    public static final String DATE_FORMAT = "dd-MM-yyyy HHmm";

    // Identity fields
    protected final TutName tutName;
    protected final TimeTable timeTable;
    protected List<Student> students;
    protected ModCode modCode;
    protected Attendance attendance;
    // TODO: Add assignments to storage
    protected Map<Assignment, Map<Student, Integer>> assignments;
    // TODO: Add to storage
    protected List<Event> eventLog;

    public Tutorial(TutName tutName, DayOfWeek day, LocalTime startTime,
            Set<Week> weeks, Duration duration, List<Student> students,
            ModCode modCode, Attendance attendance,
            List<Assignment> assignments, List<Event> eventLog) {
        requireAllNonNull(tutName, day, startTime, weeks, duration, students, modCode);
        this.tutName = tutName;
        this.timeTable = new TimeTable(day, startTime, weeks, duration);
        this.students = students;
        this.modCode = modCode;
        this.attendance = attendance == null
                ? new Attendance(weeks, students)
                : attendance;
        this.eventLog = eventLog == null
                ? new ArrayList<>()
                : eventLog;
        this.assignments = new TreeMap<>();
        if (assignments != null) {
            for (Assignment assignment : assignments) {
                addAssignment(assignment);
            }
        }
    }

    /**
     * Every field must be present and not null.
     */
    public Tutorial(TutName tutName, DayOfWeek day, LocalTime startTime,
            Set<Week> weeks, Duration duration,
            List<Student> students, ModCode modCode) {
        this(tutName, day, startTime, weeks, duration, students, modCode, null, null, null);
    }

    /**
     * Constructor for a tutorial read from saved file. Difference is that the attendance objact is specified.
     */
    public Tutorial(TutName tutName, DayOfWeek day, LocalTime startTime,
                    Set<Week> weeks, Duration duration,
                    List<Student> students, ModCode modCode, Attendance attendance) {
        this(tutName, day, startTime, weeks, duration, students, modCode, attendance, null, null);
    }

    public TutName getTutName() {
        return tutName;
    }

    public TimeTable getTimeTable() {
        return timeTable;
    }

    public List<Student> getStudents() {
        return students;
    }

    public ModCode getModCode() {
        return modCode;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public List<Assignment> getAssignments() {
        Set<Assignment> assignmentsSet = ((TreeMap<Assignment, Map<Student, Integer>>) assignments).navigableKeySet();
        return new ArrayList<>(assignmentsSet);
    }

    public Assignment getAssignment(Index assignIndex) {
        List<Assignment> assignments = getAssignments();
        Integer index = assignIndex.getZeroBased();
        return assignments.get(index);
    }

    /**
     * Returns Students' scores for an Assignment.
     */
    public Map<Student, Integer> getAssignmentScores(Assignment assignment) {
        if (!assignments.containsKey(assignment)) {
            throw new AssignmentNotFoundException();
        }
        return assignments.get(assignment);
    }

    /**
     * Returns a Student's score for an Assignment.
     */
    public Integer getAssignmentScore(Assignment assignment, Student student) {
        Map<Student, Integer> assignmentScores = getAssignmentScores(assignment);
        if (!assignmentScores.containsKey(student)) {
            throw new StudentNotFoundException();
        }
        return assignmentScores.get(student);
    }

    public Integer getHours() {
        Integer hours = 0;
        List<Event> tutEvents = getTutorialasEvents();
        for (Event event : tutEvents) {
            hours += (int) TimeUnit.HOURS.convert(
                    event.endTime.getTime() - event.startTime.getTime(),
                    TimeUnit.MILLISECONDS);
        }
        return hours;
    }

    /**
     * Returns tutorials up to the current date as a list of events.
     * If semester start date is specified, returns empty list.
     */
    public List<Event> getTutorialasEvents() {
        List<Event> tutorialEvents = new ArrayList<>();
        if (Module.getSemStart() == null) {
            return tutorialEvents;
        }
        Week currWeek = timeTable.getCurrWeek();
        if (currWeek == null) {
            return tutorialEvents;
        }
        Calendar startEvent = new Calendar.Builder().setInstant(Module.getSemStart()).build();
        startEvent.set(Calendar.DAY_OF_WEEK, timeTable.getDay().getValue());
        startEvent.set(Calendar.HOUR, timeTable.getStartTime().getHour());
        startEvent.set(Calendar.MINUTE, timeTable.getStartTime().getMinute());
        Calendar endEvent = (Calendar) startEvent.clone();
        endEvent = new Calendar.Builder()
            .setInstant(Date.from(endEvent.getTime().toInstant().plus(timeTable.getDuration())))
            .build();
        for (Week week : timeTable.getWeeks()) {
            if (week.compareTo(currWeek) <= 0) {
                Event tutEvent = new Event(tutName.tutName + " " + modCode.modCode,
                        startEvent.getTime(),
                        endEvent.getTime());
                tutorialEvents.add(tutEvent);
            }
            startEvent.add(Calendar.DAY_OF_MONTH, 7);
            endEvent.add(Calendar.DAY_OF_MONTH, 7);
        }
        return tutorialEvents;
    }

    /**
     * Returns event log. If semester start date is specified,
     * auto-adds completed tutorials into event log.
     */
    public List<Event> getEventLog() {
        if (Module.getSemStart() != null) {
            List<Event> tutorialEvents = getTutorialasEvents();
            for (Event tutorialEvent : tutorialEvents) {
                if (!eventLog.contains(tutorialEvent)) {
                    addEvent(tutorialEvent);
                }
            }
        }
        return eventLog;
    }

    /**
     * Adds a Student to a Tutorial.
     */
    public void addStudent(Student student) {
        students.add(student);
        attendance.addStudent(student);
        List<Assignment> assignments = getAssignments();
        for (Assignment assignment : assignments) {
            setScore(assignment, student, NOT_SUBMITTED);
        }
    }

    /**
     * Replaces the given student {@code target} in the list with {@code editedStudent}.
     * {@code target} must exist in the application.
     * The person identity of {@code editedStudent} must not be the same as another existing student in the application.
     */
    public void setStudent(Student target, Student editedStudent) {
        addStudent(editedStudent);
        for (Week week : getTimeTable().getWeeks()) {
            setAttendance(week, editedStudent, getAttendance().isPresent(week, target));
        }
        List<Assignment> assignmentsList = getAssignments();
        for (Assignment assignment : assignmentsList) {
            setScore(assignment, editedStudent, getAssignmentScore(assignment, target));
        }
        deleteStudent(target);
    }
    /**
     * Removes a Student from a Tutorial.
     */
    public void deleteStudent(Student student) {
        students.remove(student);
        attendance.deleteStudent(student);
        List<Assignment> assignments = getAssignments();
        for (Assignment assignment : assignments) {
            getAssignmentScores(assignment).remove(student);
        }
    }

    /**
     * Sets a Student's Attendance.
     */
    public void setAttendance(Week week, Student student) {
        attendance.setAttendance(week, student);
    }

    /**
     * Sets a Student's Attendance.
     */
    public void setAttendance(Week week, Student student, boolean isPresent) {
        attendance.setAttendance(week, student, isPresent);
    }

    /**
     * Adds an Assignment to a Tutorial.
     */
    public void addAssignment(Assignment assignment) {
        if (assignments.containsKey(assignment)) {
            throw new DuplicateAssignmentException();
        }
        assignments.put(assignment, new HashMap<>());
        for (Student student : students) {
            getAssignmentScores(assignment).put(student, NOT_SUBMITTED);
        }
        addEvent(new Event(assignment.getAssignName(),
                assignment.getStartDate(),
                assignment.getEndDate()));
    }

    /**
     * Removes an Assignment from a Tutorial. Returns true if removal is successful
     */
    public boolean deleteAssignment(Assignment assignment) {
        deleteEvent(new Event(assignment.getAssignName(),
                assignment.getStartDate(),
                assignment.getEndDate()));
        return assignments.remove(assignment) != null;
    }

    /**
     * Sets an Assignment in a Tutorial.
     */
    public void setAssignment(Assignment target, Assignment assignment) {
        addAssignment(assignment);
        try {
            for (Student student : students) {
                setScore(assignment, student, getAssignmentScore(target, student));
            }
        } catch (InvalidScoreException e) {
            deleteAssignment(assignment);
            throw new InvalidScoreException();
        }
        deleteAssignment(target);
    }

    /**
     * Sets a Student's score for an Assignment.
     */
    public void setScore(Assignment assignment, Student student, Integer score) {
        if (assignment.getMaxScore() < score || score < NOT_SUBMITTED) {
            throw new InvalidScoreException();
        }
        Map<Student, Integer> assignmentScores = getAssignmentScores(assignment);
        assignmentScores.put(student, score);
    }

    /**
     * Add an event. Checks for duplicates.
     */
    public void addEvent(Event event) {
        if (eventLog.contains(event)) {
            throw new DuplicateEventException();
        }
        eventLog.add(event);
        eventLog.sort(Event::compareTo);
    }

    /**
     * Remove an event in eventLog.
     */
    public boolean deleteEvent(Event event) {
        return eventLog.remove(event);
    }

    /**
     * Remove an event based on its index in eventLog.
     */
    public Event deleteEvent(int index) {
        return eventLog.remove(index);
    }

    /**

     * Returns true if both tutorials have the same identity or data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Tutorial)) {
            return false;
        }

        Tutorial otherTutorial = (Tutorial) other;
        return otherTutorial.getTutName().equals(getTutName())
                && otherTutorial.getStudents().equals(getStudents())
                && otherTutorial.getModCode().equals(getModCode())
                && otherTutorial.getTimeTable().equals(getTimeTable())
                && otherTutorial.getAttendance().equals(getAttendance());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tutName, timeTable, students);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTutName());
        builder.append(" | ");
        builder.append(getTimeTable().getDay().toString());
        builder.append(" | ");
        builder.append(getTimeTable().getStartTime().toString());
        builder.append(" | ");
        builder.append(getTimeTable().getWeeks().toString());
        builder.append(" | ");
        builder.append(getTimeTable().getDuration().toString());
        builder.append(" | Students: ");
        for (Student s : students) {
            builder.append(s.toString());

        }
        builder.append(" | ");
        builder.append(getModCode().toString());

        return builder.toString();
    }

    /**
     * Returns true if both Tutorials have the same name, timetable and modcode.
     *
     */
    public boolean isSameTutorial(Tutorial otherTutorial) {
        if (otherTutorial == this) {
            return true;
        }
        return otherTutorial != null
                && otherTutorial.getTimeTable().equals(getTimeTable())
                && otherTutorial.getTutName().equals(getTutName())
                && otherTutorial.getModCode().equals(getModCode());
    }

}
