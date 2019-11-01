//package seedu.planner.logic.commands;
//
//import static seedu.planner.logic.commands.CommandTestUtil.assertCommandFailure;
//import static seedu.planner.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.planner.testutil.accommodation.TypicalAccommodations.getTypicalAccommodationManager;
//import static seedu.planner.testutil.activity.TypicalActivity.getTypicalActivityManager;
//import static seedu.planner.testutil.contact.TypicalContacts.getTypicalContactManager;
//import static seedu.planner.testutil.day.TypicalDays.getTypicalItinerary;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import seedu.planner.model.Model;
//import seedu.planner.model.ModelManager;
//import seedu.planner.model.UserPrefs;
//import seedu.planner.model.contact.Contact;
//import seedu.planner.testutil.contact.ContactBuilder;
//
///**
// * Contains integration tests (interaction with the Model) for {@code AddCommand}.
// */
//public class AddContactCommandIntegrationTest {
//
//    private Model model;
//
//    @BeforeEach
//    public void setUp() {
//        model = new ModelManager(getTypicalAccommodationManager(), getTypicalActivityManager(),
//                getTypicalContactManager(), getTypicalItinerary(), new UserPrefs());
//    }
//
//    @Test
//    public void execute_newContact_success() {
//        Contact validContact = new ContactBuilder().build();
//
//        Model expectedModel = new ModelManager(model.getAccommodations(), model.getActivities(),
//                model.getContacts(), model.getItinerary(), new UserPrefs());
//        expectedModel.addContact(validContact);
//
//        assertCommandSuccess(new AddContactCommand(validContact), model,
//                String.format(AddContactCommand.MESSAGE_SUCCESS, validContact), expectedModel);
//    }
//
//    @Test
//    public void execute_duplicateContact_throwsCommandException() {
//        Contact contactInList = model.getContacts().getContactList().get(0);
//        assertCommandFailure(new AddContactCommand(contactInList), model,
//        AddContactCommand.MESSAGE_DUPLICATE_CONTACT);
//    }
//
//}
//
