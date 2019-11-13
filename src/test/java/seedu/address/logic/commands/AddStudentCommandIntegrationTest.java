package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalNotebook.getTypicalNotebook;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.student.Student;
import seedu.address.testutil.StudentBuilder;



/**
 * Contains integration tests (interaction with the Model) for {@code AddStudentCommand}.
 */
public class AddStudentCommandIntegrationTest {

    private Model model;


    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalNotebook(), new UserPrefs());
    }

    @Test
    public void execute_newStudent_success() {
        Student validStudent = new StudentBuilder().withName("Damith").build();

        Model expectedModel = new ModelManager(getTypicalNotebook(), new UserPrefs());
        expectedModel.addStudent(validStudent);
        List<Assignment> assignmentList = expectedModel.getFilteredAssignmentList();
        for (Assignment assignment: assignmentList) {
            Assignment editedAssignment = new Assignment(assignment.getAssignmentName(),
                    assignment.getAssignmentDeadline());
            editedAssignment.setGrades(assignment.namesStringListFromGrades(),
                    assignment.marksStringListFromGrades());
            editedAssignment.addNewStudentGrade(validStudent.getName().fullName);
            expectedModel.setAssignment(assignment, editedAssignment);
        }

        assertCommandSuccess(new AddStudentCommand(validStudent), model,
                        String.format(AddStudentCommand.MESSAGE_SUCCESS, validStudent), expectedModel);
    }


    @Test
    public void execute_duplicateStudent_throwsCommandException() {
        Student studentInList = model.getCurrentClassroom().getStudentList().get(0);
        assertCommandFailure(new AddStudentCommand(studentInList), model, AddStudentCommand.MESSAGE_DUPLICATE_STUDENT);
    }

}
