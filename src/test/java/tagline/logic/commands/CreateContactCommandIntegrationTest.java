package tagline.logic.commands;

import static tagline.logic.commands.CommandTestUtil.assertCommandFailure;
import static tagline.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tagline.testutil.TypicalContacts.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tagline.logic.commands.contact.CreateContactCommand;
import tagline.model.Model;
import tagline.model.ModelManager;
import tagline.model.UserPrefs;
import tagline.model.contact.Contact;
import tagline.testutil.ContactBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code CreateContactCommand}.
 */
public class CreateContactCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newContact_success() {
        Contact validContact = new ContactBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addContact(validContact);

        assertCommandSuccess(new CreateContactCommand(validContact), model,
                String.format(CreateContactCommand.MESSAGE_SUCCESS, validContact), expectedModel);
    }

    @Test
    public void execute_duplicateContact_throwsCommandException() {
        Contact contactInList = model.getAddressBook().getContactList().get(0);
        assertCommandFailure(new CreateContactCommand(contactInList), model,
                CreateContactCommand.MESSAGE_DUPLICATE_CONTACT);
    }

}
