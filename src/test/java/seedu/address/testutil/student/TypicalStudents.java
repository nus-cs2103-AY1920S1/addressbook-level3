package seedu.address.testutil.student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentRecord;
import seedu.address.model.tag.Tag;

/**
 * A utility class containing a list of {@code Student} objects to be used in tests.
 */
public class TypicalStudents {
    public static final Tag TAG_ONE = new Tag("Chemistry");
    public static final Tag TAG_TWO = new Tag("Physics");
    public static final Tag TAG_THREE = new Tag("Biology");
    public static final Tag TAG_FOUR = new Tag("Math");

    public static final HashSet<Tag> TAG_SET_ONE = new HashSet<>() {{
            add(TAG_ONE);
            add(TAG_TWO);
        }};
    public static final HashSet<Tag> TAG_SET_TWO = new HashSet<>() {{
            add(TAG_THREE);
        }};
    public static final HashSet<Tag> TAG_SET_THREE = new HashSet<>() {{
            add(TAG_FOUR);
        }};
    public static final HashSet<Tag> TAG_SET_FOUR = new HashSet<>() {{
            add(TAG_THREE);
            add(TAG_ONE);
        }};

    public static final Student STUDENT_ONE = new StudentBuilder().withName(new Name("Jonathan Dee"))
            .withTags(TAG_SET_ONE).withMark(false).build();
    public static final Student STUDENT_TWO = new StudentBuilder().withName(new Name("Jane Fam"))
            .withTags(TAG_SET_TWO).withMark(true).build();
    public static final Student STUDENT_THREE = new StudentBuilder().withName(new Name("Mary Laking"))
            .withTags(TAG_SET_THREE).withMark(false).build();
    public static final Student STUDENT_FOUR = new StudentBuilder().withName(new Name("Deviel Dal"))
            .withTags(TAG_SET_FOUR).withMark(false).build();
    public static final Student STUDENT_FIVE = new StudentBuilder().withName(new Name("Fifth")).build();

    public static StudentRecord getTypicalStudentRecord() {
        StudentRecord studentRecord = new StudentRecord();
        for (Student student : getTypicalStudents()) {
            studentRecord.addStudent(student);
        }
        return studentRecord;
    }

    public static List<Student> getTypicalStudents() {
        return new ArrayList<>(Arrays.asList(STUDENT_ONE, STUDENT_TWO, STUDENT_THREE, STUDENT_FOUR, STUDENT_FIVE));
    }

}
