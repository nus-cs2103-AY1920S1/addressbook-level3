package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyTutorAid;
import seedu.address.model.TutorAid;
import seedu.address.model.classid.ClassId;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.ReminderTime;
import seedu.address.model.task.TaskTime;


/**
 * Contains utility methods for populating {@code TutorAid} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new ClassId("CS1231")),
            new Person(new Name("Bernice Yu"), new ClassId("CS2100"))
        };
    }

    public static ReadOnlyTutorAid getSampleTutorAid() {
        TutorAid sampleAb = new TutorAid();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static TreeSet<TaskTime> getTaskTimeSet(String... strings) {
        Set<TaskTime> taskTimes = Arrays.stream(strings)
                .map(TaskTime::new)
                .collect(Collectors.toSet());
        return new TreeSet<>(taskTimes);

    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static TreeSet<ReminderTime> getReminderTimeSet(String... strings) {
        Set<ReminderTime> reminderTimes = Arrays.stream(strings)
                .map(ReminderTime::new)
                .collect(Collectors.toSet());
        return new TreeSet<>(reminderTimes);

    }

}
