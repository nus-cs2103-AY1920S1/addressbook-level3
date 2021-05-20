package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalObjects.getTypicalFinSec;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddFinSecCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalFinSec(), new UserPrefs());
    }

    //    @Test
    //    public void execute_newPerson_success() {
    //        Contact validContact = new ContactBuilder().build();
    //
    //        Model expectedModel = new ModelManager(model.getFinSec(), new UserPrefs());
    //        expectedModel.addContact(validContact);
    //
    //        assertCommandSuccess(new AddContactCommand(validContact), model,
    //                String.format(AddContactCommand.MESSAGE_SUCCESS, validContact), expectedModel);
    //    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Contact contactInList = model.getFinSec().getContactList().get(0);
        assertCommandFailure(new AddContactCommand(contactInList), model, AddContactCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
