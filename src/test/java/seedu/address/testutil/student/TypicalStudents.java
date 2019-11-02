package seedu.address.testutil.student;

import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentRecord;
import seedu.address.model.tag.Tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * A utility class containing a list of {@code Student} objects to be used in tests.
 */
public class TypicalStudents {
    public static final Tag tagOne = new Tag("Chemistry");
    public static final Tag tagTwo = new Tag("Physics");
    public static final Tag tagThree = new Tag("Biology");
    public static final Tag tagFour = new Tag("Math");

    public static HashSet<Tag> tagSetOne = new HashSet<>(){{
        add(tagOne);
        add(tagTwo);
    }};
    public static HashSet<Tag> tagSetTwo = new HashSet<>(){{
        add(tagThree);
    }};
    public static HashSet<Tag> tagSetThree = new HashSet<>(){{
        add(tagFour);
    }};
    public static HashSet<Tag> tagSetFour = new HashSet<>(){{
        add(tagThree);
        add(tagOne);
    }};

    public static final Student StudentOne = new StudentBuilder().withName(new Name("Jonathan Dee"))
            .withTags(tagSetOne).withMark(false).build();
    public static final Student StudentTwo = new StudentBuilder().withName(new Name("Jane Fam"))
            .withTags(tagSetTwo).withMark(true).build();
    public static final Student StudentThree = new StudentBuilder().withName(new Name("Mary Laking"))
            .withTags(tagSetThree).withMark(false).build();
    public static final Student StudentFour = new StudentBuilder().withName(new Name("Deviel Dal"))
            .withTags(tagSetFour).withMark(false).build();

    public static StudentRecord getTypicalStudentRecord() {
        StudentRecord studentRecord = new StudentRecord();
        for (Student student : getTypicalStudents()) {
            studentRecord.addStudent(student);
        }
        return studentRecord;
    }

    public static List<Student> getTypicalStudents() {
        return new ArrayList<>(Arrays.asList(StudentOne, StudentTwo, StudentThree, StudentFour));
    }

}
