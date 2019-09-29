package seedu.tarence.testutil;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.TimeTable;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;


/**
 * A utility class to help with building Module objects.
 */
public class TutorialBuilder {

    public static final String DEFAULT_MODCODE = "CS2100";
    public static final String DEFAULT_TUTNAME = "T01";
    public static final int DEFAULT_DURATION = 100;
    public static final String DEFAULT_DAY = "MONDAY";
    public static final String DEFAULT_TIME = "12:00:00";
    public static final List<Integer> DEFAULT_WEEKS = new ArrayList<>();
    public static final List<Student> DEFAULT_STUDENTS = new ArrayList<>();

    private ModCode modCode;
    private Duration duration;
    private DayOfWeek day;
    private LocalTime time;
    private List<Integer> weeks;
    private TutName tutName;

    public TutorialBuilder() {
        modCode = new ModCode(DEFAULT_MODCODE);
        duration = Duration.ofMinutes(DEFAULT_DURATION);
        day = DayOfWeek.valueOf(DEFAULT_DAY);
        time = LocalTime.parse(DEFAULT_TIME, DateTimeFormatter.ISO_TIME);
        DEFAULT_WEEKS.add(1);
        weeks = DEFAULT_WEEKS;
        tutName = new TutName(DEFAULT_TUTNAME);
    }

    /**
     * Initializes the ModuleBuilder with the data of {@code m}.
     */
    public TutorialBuilder(Tutorial tutorialToCopy) {
        this.modCode = tutorialToCopy.getModCode();
        this.duration = tutorialToCopy.getTimeTable().getDuration();
        this.day = tutorialToCopy.getTimeTable().getDay();
        this.time = tutorialToCopy.getTimeTable().getTime();
        this.weeks = tutorialToCopy.getTimeTable().getWeeks();
        this.tutName = tutorialToCopy.getTutName();
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
        this.time = timeTable.getTime();
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

    public Tutorial build() {
        return new Tutorial(tutName, day, time, weeks, duration, DEFAULT_STUDENTS, modCode);
    }

}
