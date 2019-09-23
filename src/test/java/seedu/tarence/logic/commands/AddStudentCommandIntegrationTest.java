package seedu.tarence.logic.commands;

import static seedu.tarence.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tarence.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tarence.testutil.TypicalPersons.getTypicalApplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.tarence.model.Model;
import seedu.tarence.model.ModelManager;
import seedu.tarence.model.UserPrefs;
import seedu.tarence.model.person.Person;
import seedu.tarence.testutil.PersonBuilder;

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
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getApplication(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(new AddStudentCommand(validPerson), model,
                String.format(AddStudentCommand.MESSAGE_SUCCESS, validPerson), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getApplication().getPersonList().get(0);
        assertCommandFailure(new AddStudentCommand(personInList), model, AddStudentCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
