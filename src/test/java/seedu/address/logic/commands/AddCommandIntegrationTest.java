package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.ActivityBook;
import seedu.address.model.Context;
import seedu.address.model.InternalState;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(
                getTypicalAddressBook(), new UserPrefs(), new InternalState(), new ActivityBook());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().withName("Alice Bob").build();

        Model expectedModel = new ModelManager(
                model.getAddressBook(), new UserPrefs(), new InternalState(), new ActivityBook());
        expectedModel.addPerson(validPerson);

        Context newContext = new Context(validPerson);
        expectedModel.setContext(newContext);
        assertCommandSuccess(new AddCommand(validPerson), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validPerson), expectedModel, newContext);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddCommand(personInList), model, AddCommand.MESSAGE_DUPLICATE_NAME);
    }

    @Test
    public void execute_duplicateName_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        Person duplicateNamePerson = new PersonBuilder().withName(personInList.getName().fullName).build();
        assertCommandFailure(new AddCommand(duplicateNamePerson), model, AddCommand.MESSAGE_DUPLICATE_NAME);
    }

}
