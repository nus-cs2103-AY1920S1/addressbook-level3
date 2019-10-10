package tagline.logic.commands;

import static tagline.logic.commands.CommandTestUtil.assertCommandFailure;
import static tagline.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tagline.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tagline.logic.commands.contact.AddContactCommand;
import tagline.model.Model;
import tagline.model.ModelManager;
import tagline.model.UserPrefs;
import tagline.model.person.Person;
import tagline.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddContactCommand}.
 */
public class AddContactCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(new AddContactCommand(validPerson), model,
                String.format(AddContactCommand.MESSAGE_SUCCESS, validPerson), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddContactCommand(personInList), model, AddContactCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
