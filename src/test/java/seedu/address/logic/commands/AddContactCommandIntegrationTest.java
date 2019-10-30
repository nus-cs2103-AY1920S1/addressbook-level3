package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.accommodation.TypicalAccommodations.getTypicalAccommodationManager;
import static seedu.address.testutil.activity.TypicalActivity.getTypicalActivityManager;
import static seedu.address.testutil.contact.TypicalContacts.getTypicalContactManager;
import static seedu.address.testutil.day.TypicalDays.getTypicalItinerary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.testutil.contact.ContactBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddContactCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAccommodationManager(), getTypicalActivityManager(),
                getTypicalContactManager(), getTypicalItinerary(), new UserPrefs());
    }

    @Test
    public void execute_newContact_success() {
        Contact validContact = new ContactBuilder().build();

        Model expectedModel = new ModelManager(model.getAccommodations(), model.getActivities(),
                model.getContacts(), model.getItinerary(), new UserPrefs());
        expectedModel.addContact(validContact);

        assertCommandSuccess(new AddContactCommand(validContact), model,
                String.format(AddContactCommand.MESSAGE_SUCCESS, validContact), expectedModel);
    }

    @Test
    public void execute_duplicateContact_throwsCommandException() {
        Contact contactInList = model.getContacts().getContactList().get(0);
        assertCommandFailure(new AddContactCommand(contactInList), model, AddContactCommand.MESSAGE_DUPLICATE_CONTACT);
    }

}

