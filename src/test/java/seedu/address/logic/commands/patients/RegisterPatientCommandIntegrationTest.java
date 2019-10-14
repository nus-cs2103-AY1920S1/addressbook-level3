package seedu.address.logic.commands.patients;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TestUtil;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class RegisterPatientCommandIntegrationTest {

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();
        RegisterPatientCommand command = new RegisterPatientCommand(validPerson);

        Model model = TestUtil.getTypicalModelManager();
        Model expectedModel = TestUtil.getTypicalModelManager();
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(command, model,
                String.format(RegisterPatientCommand.MESSAGE_SUCCESS, validPerson), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Model model = TestUtil.getTypicalModelManager();
        Person personInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new RegisterPatientCommand(personInList), model,
            RegisterPatientCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
