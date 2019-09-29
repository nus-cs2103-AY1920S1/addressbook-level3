package seedu.tarence.logic.commands;

import static seedu.tarence.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tarence.testutil.TypicalPersons.getTypicalApplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.tarence.model.Model;
import seedu.tarence.model.ModelManager;
import seedu.tarence.model.UserPrefs;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.testutil.ModuleBuilder;
import seedu.tarence.testutil.StudentBuilder;
import seedu.tarence.testutil.TutorialBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddStudentCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalApplication(), new UserPrefs());
    }

    @Test
    public void execute_newStudent_success() {
        final String validModCode = "GER1000";
        final String validTutName = "T01";
        Model expectedModel = new ModelManager(model.getApplication(), new UserPrefs());

        Tutorial validTutorial = new TutorialBuilder().withModCode(validModCode)
            .withTutName(validTutName).build();
        Module validModule = new ModuleBuilder().withModCode(validModCode).build();
        model.addModule(validModule);
        expectedModel.addModule(validModule);
        model.addTutorial(validTutorial);
        expectedModel.addTutorial(validTutorial);

        Student validStudent = new StudentBuilder().withModCode(validModCode)
            .withTutName(validTutName).build();
        expectedModel.addPerson(validStudent);

        assertCommandSuccess(new AddStudentCommand(validStudent), model,
                String.format(AddStudentCommand.MESSAGE_SUCCESS, validStudent), expectedModel);
    }

    /*
     * Doesn't work anymore because the default Person cannot be used as a Student.
     * To update.
    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getApplication().getPersonList().get(0);
        assertCommandFailure(new AddStudentCommand(personInList), model, AddStudentCommand.MESSAGE_DUPLICATE_PERSON);
    }
    */

}
