package seedu.address.testutil.group;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.group.Group;
import seedu.address.model.group.ListOfGroups;
import seedu.address.model.student.Student;
import seedu.address.testutil.student.TypicalStudents;

/**
 * Represents the typical groups that a user can make.
 */
public class TypicalGroups {

    public static final List<Student> STUDENT_LIST_ONE =
            new ArrayList<>(Arrays.asList(TypicalStudents.STUDENT_ONE, TypicalStudents.STUDENT_TWO));
    public static final List<Student> STUDENT_LIST_TWO =
            new ArrayList<>(Arrays.asList(TypicalStudents.STUDENT_THREE, TypicalStudents.STUDENT_FOUR));
    public static final List<Student> STUDENT_LIST_THREE =
            new ArrayList<>(Arrays.asList(TypicalStudents.STUDENT_ONE, TypicalStudents.STUDENT_THREE));
    public static final List<Student> STUDENT_LIST_FOUR =
            new ArrayList<Student>(Arrays.asList(TypicalStudents.STUDENT_TWO, TypicalStudents.STUDENT_FOUR));

    public static final Group GROUP_ONE = new GroupBuilder().withGroupId("TestGROUP_ONE")
            .withStudentList(STUDENT_LIST_ONE).build();
    public static final Group GROUP_TWO = new GroupBuilder().withGroupId("TestGROUP_TWO")
            .withStudentList(STUDENT_LIST_TWO).build();
    public static final Group GROUP_THREE = new GroupBuilder().withGroupId("TestGROUP_THREE")
            .withStudentList(STUDENT_LIST_THREE).build();
    public static final Group GROUP_FOUR = new GroupBuilder().withGroupId("TestGROUP_FOUR")
            .withStudentList(STUDENT_LIST_FOUR).build();

    public static ListOfGroups getListOfGroups() {
        ListOfGroups listOfGroups = new ListOfGroups();
        listOfGroups.setGroups(getTypicalGroups());
        return listOfGroups;
    }

    public static List<Group> getTypicalGroups() {
        return new ArrayList<>(Arrays.asList(GROUP_ONE, GROUP_TWO, GROUP_THREE, GROUP_FOUR));
    }
}
