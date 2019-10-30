package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyNotebook;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.Notebook;
import seedu.address.model.classroom.ReadOnlyClassroom;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentDeadline;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.student.Address;
import seedu.address.model.student.Email;
import seedu.address.model.student.MedicalCondition;
import seedu.address.model.student.Name;
import seedu.address.model.student.ParentPhone;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Student;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Classroom} with sample data.
 */
public class SampleDataUtil {

    public static Student[] getSampleStudents() {
        return new Student[] {
            new Student(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new ParentPhone("91111111"), new Address("Blk 30 Geylang Street 29, #06-40"),
                    new MedicalCondition("Sinus"), getTagSet("friends")),
            new Student(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new ParentPhone("92222222"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new MedicalCondition("Asthma"), getTagSet("colleagues", "friends")),
            new Student(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new ParentPhone("93333333"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new MedicalCondition("Heart Attack"), getTagSet("neighbours")),
            new Student(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new ParentPhone("94444444"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new MedicalCondition("Pimples"), getTagSet("family")),
            new Student(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new ParentPhone("95555555"), new Address("Blk 47 Tampines Street 20, #17-35"),
                    new MedicalCondition("HIV"), getTagSet("classmates")),
            new Student(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new ParentPhone("96666666"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                    new MedicalCondition("Herpes"), getTagSet("colleagues"))
        };
    }

    public static Classroom getSampleClassroom() {
        Classroom sampleClassroom = new Classroom();
        for (Student sampleStudent : getSampleStudents()) {
            sampleClassroom.addStudent(sampleStudent);
        }
        for (Assignment sampleAssignment : getSampleAssignments()) {
            sampleClassroom.addAssignment(sampleAssignment);
        }
        return sampleClassroom;
    }

    public static ReadOnlyNotebook getSampleNotebook() {
        Notebook sampleNotebook = new Notebook();
        //sampleNotebook.addClassroom(new Classroom("testing"));
        sampleNotebook.addClassroom(getSampleClassroom());
        return sampleNotebook;
    }

    public static Assignment[] getSampleAssignments() {
        return new Assignment[] {
            new Assignment(new AssignmentName("Math Assignment 1"), new AssignmentDeadline("24/07/2019 1800")),
            new Assignment(new AssignmentName("English Assignment 3"), new AssignmentDeadline("29/07/2019 2359")),
            new Assignment(new AssignmentName("Math Tutorial 3"), new AssignmentDeadline("01/08/2019 1400")),
            new Assignment(new AssignmentName("English Essay 1"), new AssignmentDeadline("02/08/2019 2359"))
        };
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
