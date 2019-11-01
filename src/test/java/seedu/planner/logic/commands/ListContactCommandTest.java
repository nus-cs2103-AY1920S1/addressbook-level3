//package seedu.planner.logic.commands;
//
//import static seedu.planner.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.planner.logic.commands.CommandTestUtil.showContactAtIndex;
//import static seedu.planner.testutil.TypicalIndexes.INDEX_FIRST_CONTACT;
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
//
///**
// * Contains integration tests (interaction with the Model) and unit tests for ListContactCommand.
// */
//public class ListContactCommandTest {
//
//    private Model model;
//    private Model expectedModel;
//
//    @BeforeEach
//    public void setUp() {
//        model = new ModelManager(getTypicalAccommodationManager(), getTypicalActivityManager(),
//                getTypicalContactManager(), getTypicalItinerary(), new UserPrefs());
//        expectedModel = new ModelManager(model.getAccommodations(), model.getActivities(),
//                model.getContacts(), model.getItinerary(), new UserPrefs());
//    }
//
//    @Test
//    public void execute_listIsNotFiltered_showsSameList() {
//        assertCommandSuccess(new ListContactCommand(), model, ListContactCommand.MESSAGE_SUCCESS, expectedModel);
//    }
//
//    @Test
//    public void execute_listIsFiltered_showsEverything() {
//        showContactAtIndex(model, INDEX_FIRST_CONTACT);
//        assertCommandSuccess(new ListContactCommand(), model, ListContactCommand.MESSAGE_SUCCESS, expectedModel);
//    }
//}
