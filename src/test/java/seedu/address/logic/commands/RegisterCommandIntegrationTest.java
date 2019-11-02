package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.getTypicalIncidentManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code RegisterCommand}.
 */
public class RegisterCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalIncidentManager(), new UserPrefs());
        model.setSession(AMY); // Added to simulate a logged in person
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getIncidentManager(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(new RegisterCommand(validPerson), model,
                String.format(RegisterCommand.MESSAGE_SUCCESS, validPerson), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getIncidentManager().getPersonList().get(0);
        assertCommandFailure(new RegisterCommand(personInList), model, RegisterCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
