package seedu.address.logic.commands.student;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.student.TypicalStudents.getTypicalStudentRecord;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResultType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

/**
 * Test for StudentListCommand.
 */
public class StudentListCommandTest {

    private Model model = new ModelManager();

    /**
     * Creates an instance of StudentListCommandTest.
     */
    public StudentListCommandTest() {
        model.setStudentRecord(getTypicalStudentRecord());
    }

    /**
     * Test for successfully showing list of students.
     */
    @Test
    public void execute_validList_showsEverything() {
        Model expectedModel = new ModelManager();
        expectedModel.setStudentRecord(getTypicalStudentRecord());

        assertCommandSuccess(
                new StudentListCommand(),
                model,
                "This is the list of students: " + "\n" + model.getStudentSummary(),
                expectedModel,
                CommandResultType.SHOW_STUDENT);
    }
}
