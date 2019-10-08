package seedu.tarence.testutil;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.TimeTable;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.model.tutorial.Week;


/**
 * A utility class to help with building Module objects.
 */
public class TutorialBuilder {

    public static final String DEFAULT_MODCODE = "CS2100";
    public static final String DEFAULT_TUTNAME = "T01";
    public static final int DEFAULT_DURATION = 100;
    public static final String DEFAULT_DAY = "MONDAY";
    public static final String DEFAULT_STARTTIME = "12:00:00";
    public static final Set<Week> DEFAULT_WEEKS = new TreeSet<>();
    public static final List<Student> DEFAULT_STUDENTS = new ArrayList<>();

    private ModCode modCode;
    private Duration duration;
    private DayOfWeek day;
    private LocalTime startTime;
    private Set<Week> weeks;
    private TutName tutName;
    private List<Student> students;

    public TutorialBuilder() {
        modCode = new ModCode(DEFAULT_MODCODE);
        duration = Duration.ofMinutes(DEFAULT_DURATION);
        day = DayOfWeek.valueOf(DEFAULT_DAY);
        startTime = LocalTime.parse(DEFAULT_STARTTIME, DateTimeFormatter.ISO_TIME);
        DEFAULT_WEEKS.clear();
        for (int w = 3; w <= 13; w++) {
            DEFAULT_WEEKS.add(new Week(w));
        }
        weeks = DEFAULT_WEEKS;
        tutName = new TutName(DEFAULT_TUTNAME);
        students = DEFAULT_STUDENTS;
    }

    /**
     * Initializes the ModuleBuilder with the data of {@code m}.
     */
    public TutorialBuilder(Tutorial tutorialToCopy) {
        this.modCode = tutorialToCopy.getModCode();
        this.duration = tutorialToCopy.getTimeTable().getDuration();
        this.day = tutorialToCopy.getTimeTable().getDay();
        this.startTime = tutorialToCopy.getTimeTable().getStartTime();
        this.weeks = tutorialToCopy.getTimeTable().getWeeks();
        this.tutName = tutorialToCopy.getTutName();
        this.students = tutorialToCopy.getStudents();
    }

    /**
     * Sets the {@code ModCode} of the {@code Tutorial} that we are building.
     */
    public TutorialBuilder withModCode(String modCode) {
        this.modCode = new ModCode(modCode);
        return this;
    }

    /**
     * Sets the {@code TimeTable} of the {@code Tutorial} that we are building.
     */
    public TutorialBuilder withTimeTable(TimeTable timeTable) {
        this.duration = timeTable.getDuration();
        this.day = timeTable.getDay();
        this.startTime = timeTable.getStartTime();
        this.weeks = timeTable.getWeeks();
        return this;
    }

    /**
     * Sets the {@code TutName} of the {@code Tutorial} that we are building.
     */
    public TutorialBuilder withTutName(String tutName) {
        this.tutName = new TutName(tutName);
        return this;
    }

    /**
     * Sets the {@code List<Student>} of the {@code Tutorial} that we are building.
     */
    public TutorialBuilder withStudents(List<Student> students) {
        this.students = students;
        return this;
    }

    public Tutorial build() {
        return new Tutorial(tutName, day, startTime, weeks, duration, students, modCode);
    }

}
