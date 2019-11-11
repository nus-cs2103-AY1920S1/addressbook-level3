package tagline.logic.commands.contact;

import static tagline.logic.commands.CommandTestUtil.assertCommandFailure;
import static tagline.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tagline.testutil.contact.TypicalContacts.getTypicalAddressBook;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tagline.logic.commands.CommandResult.ViewType;
import tagline.model.Model;
import tagline.model.ModelManager;
import tagline.model.UserPrefs;
import tagline.model.contact.Contact;
import tagline.model.contact.ContactBuilder;
import tagline.model.contact.ContactIdEqualsSearchIdPredicate;
import tagline.model.group.GroupBook;
import tagline.model.note.NoteBook;
import tagline.model.note.NoteContainsTagsPredicate;
import tagline.model.tag.ContactTag;
import tagline.model.tag.TagBook;

/**
 * Contains integration tests (interaction with the Model) for {@code CreateContactCommand}.
 */
public class CreateContactCommandIntegrationTest {

    private static final ViewType CREATE_CONTACT_COMMAND_VIEW_TYPE = ViewType.CONTACT_PROFILE;
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new NoteBook(),
            new GroupBook(), new TagBook(), new UserPrefs());
    }

    @Test
    public void execute_newContact_success() {
        Contact validContact = new ContactBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new NoteBook(),
            new GroupBook(), new TagBook(), new UserPrefs());
        expectedModel.addContact(validContact);
        expectedModel.updateFilteredContactList(new ContactIdEqualsSearchIdPredicate(validContact.getContactId()));
        expectedModel.updateFilteredNoteList(new NoteContainsTagsPredicate(
                Collections.singletonList(new ContactTag(validContact.getContactId()))));

        assertCommandSuccess(new CreateContactCommand(validContact), model,
            String.format(CreateContactCommand.MESSAGE_SUCCESS, validContact),
            CREATE_CONTACT_COMMAND_VIEW_TYPE, expectedModel);
    }

    @Test
    public void execute_duplicateContact_throwsCommandException() {
        Contact contactInList = model.getAddressBook().getContactList().get(0);
        assertCommandFailure(new CreateContactCommand(contactInList), model,
            CreateContactCommand.MESSAGE_DUPLICATE_CONTACT);
    }

}
