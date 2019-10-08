package seedu.tarence.model.util;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import seedu.tarence.model.Application;
import seedu.tarence.model.ReadOnlyApplication;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.person.Email;
import seedu.tarence.model.person.Name;
import seedu.tarence.model.person.Person;
import seedu.tarence.model.student.MatricNum;
import seedu.tarence.model.student.NusnetId;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.TimeTable;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.model.tutorial.Week;

/**
 * Contains utility methods for populating {@code Application} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Email("alexyeoh@example.com")),
            new Person(new Name("Bernice Yu"), new Email("berniceyu@example.com")),
            new Person(new Name("Charlotte Oliveiro"), new Email("charlotte@example.com")),
            new Person(new Name("David Li"), new Email("lidavid@example.com")),
            new Person(new Name("Irfan Ibrahim"), new Email("irfan@example.com")),
            new Person(new Name("Roy Balakrishnan"), new Email("royb@example.com"))
        };
    }

    public static ReadOnlyApplication getSampleApplication() {
        Application sampleAb = new Application();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a sample Student with the following attributes:
     * Name: Alice. Matric No.: A1234567Z. NUSNET ID: E1234567. Email: E1234567@u.nus.edu.
     *
     * @return sample Student object.
     */
    public static Student getSampleStudent() {
        MatricNum matricNum = new MatricNum("A1234567Z");
        NusnetId nusnetId = new NusnetId("E1234567");
        Name alice = new Name("Alice");
        Email email = new Email ("E1234567@u.nus.edu");
        ModCode modCode = new ModCode("CS1101S");
        TutName tutName = new TutName("T01");
        return new Student(alice, email, Optional.of(matricNum), Optional.of(nusnetId), modCode, tutName);
    }

    /**
     * Returns a sample Timetable:
     * Day: Monday.
     * Time: 1200.
     * Tutorial: Only week 5.
     * Duration: 1 HOUR.
     * Timetable vs Tutorial: Tutorial has a list of Students in it.
     *
     * @return sample TimeTable object.
     */
    public static TimeTable getSampleTimeTable() {
        LocalTime time = LocalTime.parse("12:00:00", DateTimeFormatter.ISO_TIME);

        Set<Week> weekFiveTutorial = new TreeSet<>();
        weekFiveTutorial.add(new Week(5));

        Duration oneHour = Duration.ofHours(1);

        return new TimeTable(DayOfWeek.MONDAY, time, weekFiveTutorial, oneHour);
    }

    /**
     * Returns a sample Tutorial with one student (from getSampleStudent).
     * Tutorial details:
     * Day: Monday.
     * Time: 1200.
     * Tutorial: Only week 5.
     * Duration: 1 HOUR.
     *
     * @return sample Tutorial object.
     */
    public static Tutorial getSampleTutorial() {
        LocalTime time = LocalTime.parse("12:00:00", DateTimeFormatter.ISO_TIME);
        Set<Week> weekFiveTutorial = new TreeSet<>();
        weekFiveTutorial.add(new Week(5));
        ArrayList<Student> students = new ArrayList<Student>();
        students.add(getSampleStudent());
        ModCode modCode = new ModCode("CS1101S");

        return new Tutorial(new TutName("Sectional"), DayOfWeek.MONDAY, time, weekFiveTutorial, Duration.ofHours(1),
                students, modCode);
    }

    /**
     * Returns a sample Module with code: XX1234A and a list of tutorial(s).
     *
     * @return sample Module object.
     */
    public static Module getSampleModule() {
        ArrayList<Tutorial> tutorials = new ArrayList<Tutorial>();
        tutorials.add(getSampleTutorial());
        return new Module(new ModCode("XX1234A"), tutorials);
    }

}
