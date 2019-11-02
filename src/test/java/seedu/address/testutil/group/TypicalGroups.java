package seedu.address.testutil.group;

import seedu.address.model.group.Group;
import seedu.address.model.group.ListOfGroups;
import seedu.address.model.student.Student;
import seedu.address.testutil.student.TypicalStudents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TypicalGroups {

    public static final List<Student> studentListOne =
            new ArrayList<>(Arrays.asList(TypicalStudents.StudentOne,TypicalStudents.StudentTwo));
    public static final List<Student> studentListTwo =
            new ArrayList<>(Arrays.asList(TypicalStudents.StudentThree,TypicalStudents.StudentFour));
    public static final List<Student> studentListThree =
            new ArrayList<>(Arrays.asList(TypicalStudents.StudentOne,TypicalStudents.StudentThree));
    public static final List<Student> studentListFour =
            new ArrayList<Student>(Arrays.asList(TypicalStudents.StudentTwo,TypicalStudents.StudentFour));

    public static final Group groupOne = new GroupBuilder().withGroupId("TestGroupOne")
            .withStudentList(studentListOne).build();
    public static final Group groupTwo = new GroupBuilder().withGroupId("TestGroupTwo")
            .withStudentList(studentListTwo).build();
    public static final Group groupThree = new GroupBuilder().withGroupId("TestGroupThree")
            .withStudentList(studentListThree).build();
    public static final Group groupFour = new GroupBuilder().withGroupId("TestGroupFour")
            .withStudentList(studentListFour).build();

    public static ListOfGroups getListOfGroups() {
        ListOfGroups listOfGroups = new ListOfGroups();
        listOfGroups.setGroups(getTypicalGroups());
        return listOfGroups;
    }

    public static List<Group> getTypicalGroups() {
        return new ArrayList<>(Arrays.asList(groupOne, groupTwo, groupThree, groupFour));
    }
}
