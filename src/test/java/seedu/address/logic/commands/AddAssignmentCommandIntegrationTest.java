package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalNotebook.getTypicalNotebook;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.Assignment;
import seedu.address.testutil.AssignmentBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddAssignmentCommand}.
 */
public class AddAssignmentCommandIntegrationTest {

    private Model model;


    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalNotebook(), new UserPrefs());
    }

    @Test
    public void execute_newAssignment_success() {
        Assignment validAssignment = new AssignmentBuilder().withAssignmentName("Math Worksheet").build();
        Model expectedModel = new ModelManager(model.getNotebook(), new UserPrefs());

        List<String> studentNames = new ArrayList<>();
        expectedModel.getCurrentClassroom().getStudentList().forEach(student -> studentNames.add(student.getName().toString()));
        validAssignment.initialiseGrades(studentNames);

        expectedModel.addAssignment(validAssignment);

        assertCommandSuccess(new AddAssignmentCommand(validAssignment), model,
                String.format(AddAssignmentCommand.MESSAGE_SUCCESS, validAssignment), expectedModel);
    }


    @Test
    public void execute_duplicateAssignment_throwsCommandException() {
        Assignment assignmentInList = model.getCurrentClassroom().getAssignmentList().get(0);
        assertCommandFailure(new AddAssignmentCommand(assignmentInList), model,
                AddAssignmentCommand.MESSAGE_DUPLICATE_ASSIGNMENT);
    }

}
