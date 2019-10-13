package seedu.tarence.logic.commands;

import static seedu.tarence.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tarence.testutil.Assert.assertThrows;
import static seedu.tarence.testutil.TypicalPersons.getTypicalApplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.tarence.commons.core.index.Index;
import seedu.tarence.model.Model;
import seedu.tarence.model.ModelManager;
import seedu.tarence.model.UserPrefs;
import seedu.tarence.model.builder.ModuleBuilder;
import seedu.tarence.model.builder.StudentBuilder;
import seedu.tarence.model.builder.TutorialBuilder;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.person.exceptions.DuplicatePersonException;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.Tutorial;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddStudentCommandIntegrationTest {

    public static final String VALID_MOD_CODE = "GER1000";
    public static final String VALID_TUT_NAME = "T01";

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalApplication(), new UserPrefs());
    }

    @Test
    public void execute_newStudentFullFormat_success() {
        Model expectedModel = new ModelManager(model.getApplication(), new UserPrefs());

        Tutorial validTutorial = new TutorialBuilder().withModCode(VALID_MOD_CODE)
            .withTutName(VALID_TUT_NAME).build();
        Module validModule = new ModuleBuilder().withModCode(VALID_MOD_CODE).build();
        model.addModule(validModule);
        expectedModel.addModule(validModule);
        model.addTutorial(validTutorial);
        model.addTutorialToModule(validTutorial);
        expectedModel.addTutorial(validTutorial);
        expectedModel.addTutorialToModule(validTutorial);

        Student validStudent = new StudentBuilder().withModCode(VALID_MOD_CODE)
            .withTutName(VALID_TUT_NAME).build();
        expectedModel.addStudent(validStudent);
        expectedModel.addStudentToTutorial(validStudent);

        assertCommandSuccess(new AddStudentCommand(validStudent), model,
                String.format(AddStudentCommand.MESSAGE_SUCCESS, validStudent), expectedModel);
    }

    @Test
    public void execute_newStudentIndexFormat_success() {
        Model expectedModel = new ModelManager(model.getApplication(), new UserPrefs());

        Tutorial validTutorial = new TutorialBuilder().withModCode(VALID_MOD_CODE)
                .withTutName(VALID_TUT_NAME).build();
        Module validModule = new ModuleBuilder().withModCode(VALID_MOD_CODE).build();
        model.addModule(validModule);
        expectedModel.addModule(validModule);
        model.addTutorial(validTutorial);
        model.addTutorialToModule(validTutorial);
        expectedModel.addTutorial(validTutorial);
        expectedModel.addTutorialToModule(validTutorial);

        Student validStudent = new StudentBuilder().withModCode(VALID_MOD_CODE)
                .withTutName(VALID_TUT_NAME).build();
        expectedModel.addStudent(validStudent);
        expectedModel.addStudentToTutorial(validStudent);

        Student indexedStudent = new StudentBuilder().build();
        Index validTutorialIndex = Index.fromOneBased(1);


        assertCommandSuccess(new AddStudentCommand(indexedStudent, validTutorialIndex), model,
                String.format(AddStudentCommand.MESSAGE_SUCCESS, validStudent), expectedModel);
    }

    @Test
    public void execute_duplicateStudent_throwsCommandException() {
        Tutorial validTutorial = new TutorialBuilder().withModCode(VALID_MOD_CODE)
                .withTutName(VALID_TUT_NAME).build();
        Module validModule = new ModuleBuilder().withModCode(VALID_MOD_CODE).build();
        model.addModule(validModule);
        model.addTutorial(validTutorial);
        model.addTutorialToModule(validTutorial);
        Student student = new StudentBuilder().withModCode(VALID_MOD_CODE)
                .withTutName(VALID_TUT_NAME).build();
        model.addStudent(student);

        assertThrows(DuplicatePersonException.class, () -> model.addStudent(student));
    }

}
