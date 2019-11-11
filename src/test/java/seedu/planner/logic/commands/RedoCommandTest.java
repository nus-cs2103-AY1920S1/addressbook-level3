package seedu.planner.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.Logic;
import seedu.planner.logic.LogicManager;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.accommodation.Accommodation;
import seedu.planner.model.activity.Activity;
import seedu.planner.model.activity.Duration;
import seedu.planner.model.activity.Priority;
import seedu.planner.model.contact.Contact;
import seedu.planner.model.contact.Phone;
import seedu.planner.model.day.Day;
import seedu.planner.model.field.Address;
import seedu.planner.model.field.Name;
import seedu.planner.storage.JsonUserPrefsStorage;
import seedu.planner.storage.Storage;
import seedu.planner.storage.StorageManager;
import seedu.planner.storage.accommodation.JsonAccommodationStorage;
import seedu.planner.storage.activity.JsonActivityStorage;
import seedu.planner.storage.contact.JsonContactStorage;
import seedu.planner.storage.day.JsonItineraryStorage;
//@@author OneArmyj
public class RedoCommandTest {
    @TempDir
    public Path testFolder;

    @Test
    public void execute_redoAddActivity_success() throws CommandException, ParseException {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model, generateTempStorage());

        logic.execute("add activity n/hi a/bye du/111");
        Activity newActivity = new Activity(new Name("hi"), new Address("bye"), null,
                null, new HashSet<>(), new Duration(111), new Priority(0));
        assertTrue(model.hasActivity(newActivity));
        logic.execute("undo");
        assertFalse(model.hasActivity(newActivity));
        logic.execute("redo");
        assertTrue(model.hasActivity(newActivity));
    }

    @Test
    public void execute_redoAddAccommodation_success() throws CommandException, ParseException {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model, generateTempStorage());

        logic.execute("add accommodation n/hi a/bye");
        Accommodation newAccommodation = new Accommodation(new Name("hi"), new Address("bye"), null, new HashSet<>());
        assertTrue(model.hasAccommodation(newAccommodation));
        logic.execute("undo");
        assertFalse(model.hasAccommodation(newAccommodation));
        logic.execute("redo");
        assertTrue(model.hasAccommodation(newAccommodation));
    }

    @Test
    public void execute_redoAddContact_success() throws CommandException, ParseException {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model, generateTempStorage());

        logic.execute("add contact n/hi p/123");
        Contact newContact = new Contact(new Name("hi"), new Phone("123"), null, null, new HashSet<>());
        assertTrue(model.hasContact(newContact));
        logic.execute("undo");
        assertFalse(model.hasContact(newContact));
        logic.execute("redo");
        assertTrue(model.hasContact(newContact));
    }

    @Test
    public void execute_redoAddDay_success() throws CommandException, ParseException {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model, generateTempStorage());

        logic.execute("add days 1");
        Day newDay = new Day();
        assertTrue(model.hasDay(newDay));
        logic.execute("undo");
        assertFalse(model.hasDay(newDay));
        logic.execute("redo");
        assertTrue(model.hasDay(newDay));
    }

    @Test
    public void execute_redoDeleteActivity_success() throws CommandException, ParseException {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model, generateTempStorage());

        logic.execute("add activity n/hi a/bye du/111");
        Activity newActivity = new Activity(new Name("hi"), new Address("bye"), null,
                null, new HashSet<>(), new Duration(111), new Priority(0));
        assertTrue(model.hasActivity(newActivity));
        logic.execute("delete activity 1");
        assertFalse(model.hasActivity(newActivity));
        logic.execute("undo");
        assertTrue(model.hasActivity(newActivity));
        logic.execute("redo");
        assertFalse(model.hasActivity(newActivity));
    }

    @Test
    public void execute_redoDeleteAccommodation_success() throws CommandException, ParseException {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model, generateTempStorage());

        logic.execute("add accommodation n/hi a/bye");
        Accommodation newAccommodation = new Accommodation(new Name("hi"), new Address("bye"), null, new HashSet<>());
        assertTrue(model.hasAccommodation(newAccommodation));
        logic.execute("delete accommodation 1");
        assertFalse(model.hasAccommodation(newAccommodation));
        logic.execute("undo");
        assertTrue(model.hasAccommodation(newAccommodation));
        logic.execute("redo");
        assertFalse(model.hasAccommodation(newAccommodation));
    }

    @Test
    public void execute_redoDeleteContact_success() throws CommandException, ParseException {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model, generateTempStorage());

        logic.execute("add contact n/hi p/123");
        Contact newContact = new Contact(new Name("hi"), new Phone("123"), null, null, new HashSet<>());
        assertTrue(model.hasContact(newContact));
        logic.execute("delete contact 1");
        assertFalse(model.hasContact(newContact));
        logic.execute("undo");
        assertTrue(model.hasContact(newContact));
        logic.execute("redo");
        assertFalse(model.hasContact(newContact));
    }

    @Test
    public void execute_redoDeleteDay_success() throws CommandException, ParseException {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model, generateTempStorage());

        logic.execute("add days 1");
        Day newDay = new Day();
        assertTrue(model.hasDay(newDay));
        logic.execute("delete day 1");
        assertFalse(model.hasDay(newDay));
        logic.execute("undo");
        assertTrue(model.hasDay(newDay));
        logic.execute("redo");
        assertFalse(model.hasDay(newDay));
    }

    @Test
    public void execute_redoEditActivity_success() throws CommandException, ParseException {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model, generateTempStorage());

        logic.execute("add activity n/hi a/bye du/111");
        Activity newActivity = new Activity(new Name("hi"), new Address("bye"), null,
                null, new HashSet<>(), new Duration(111), new Priority(0));
        assertTrue(model.hasActivity(newActivity));
        logic.execute("edit activity 1 n/Different du/123 pr/5");
        Activity editedActivity = new Activity(new Name("Different"), new Address("bye"), null, null,
                new HashSet<>(), new Duration(123), new Priority(5));
        assertTrue(model.hasActivity(editedActivity));
        assertFalse(model.hasActivity(newActivity));

        logic.execute("undo");
        assertFalse(model.hasActivity(editedActivity));
        assertTrue(model.hasActivity(newActivity));

        logic.execute("redo");
        assertTrue(model.hasActivity(editedActivity));
        assertFalse(model.hasActivity(newActivity));
    }

    @Test
    public void execute_redoEditAccommodation_success() throws CommandException, ParseException {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model, generateTempStorage());

        logic.execute("add accommodation n/hi a/bye");
        Accommodation newAccommodation = new Accommodation(new Name("hi"), new Address("bye"), null, new HashSet<>());
        assertTrue(model.hasAccommodation(newAccommodation));
        logic.execute("edit accommodation 1 n/Different");
        Accommodation editedAccommodation = new Accommodation(new Name("Different"), new Address("bye"),
                null, new HashSet<>());
        assertTrue(model.hasAccommodation(editedAccommodation));
        assertFalse(model.hasAccommodation(newAccommodation));

        logic.execute("undo");
        assertFalse(model.hasAccommodation(editedAccommodation));
        assertTrue(model.hasAccommodation(newAccommodation));

        logic.execute("redo");
        assertTrue(model.hasAccommodation(editedAccommodation));
        assertFalse(model.hasAccommodation(newAccommodation));
    }

    @Test
    public void execute_redoEditContact_success() throws CommandException, ParseException {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model, generateTempStorage());

        logic.execute("add contact n/hi p/123");
        Contact newContact = new Contact(new Name("hi"), new Phone("123"), null, null, new HashSet<>());
        assertTrue(model.hasContact(newContact));
        logic.execute("edit contact 1 n/Different");
        Contact editedContact = new Contact(new Name("Different"), new Phone("123"), null, null, new HashSet<>());
        assertTrue(model.hasContact(editedContact));
        assertFalse(model.hasContact(newContact));

        logic.execute("undo");
        assertFalse(model.hasContact(editedContact));
        assertTrue(model.hasContact(newContact));

        logic.execute("redo");
        assertTrue(model.hasContact(editedContact));
        assertFalse(model.hasContact(newContact));
    }

    @Test
    public void execute_undoClearContact_success() throws CommandException, ParseException {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model, generateTempStorage());
        logic.execute("add activity n/hi a/bye du/111");
        logic.execute("add accommodation n/hi a/bye");
        logic.execute("add contact n/hi p/123");
        logic.execute("add days 1");

        Activity newActivity = new Activity(new Name("hi"), new Address("bye"), null,
                null, new HashSet<>(), new Duration(111), new Priority(0));
        assertTrue(model.hasActivity(newActivity));
        Accommodation newAccommodation = new Accommodation(new Name("hi"), new Address("bye"), null, new HashSet<>());
        assertTrue(model.hasAccommodation(newAccommodation));
        Contact newContact = new Contact(new Name("hi"), new Phone("123"), null, null, new HashSet<>());
        assertTrue(model.hasContact(newContact));
        Day newDay = new Day();
        assertTrue(model.hasDay(newDay));

        logic.execute("clear");
        assertFalse(model.hasActivity(newActivity));
        assertFalse(model.hasAccommodation(newAccommodation));
        assertFalse(model.hasContact(newContact));
        assertFalse(model.hasDay(newDay));

        logic.execute("undo");
        assertTrue(model.hasActivity(newActivity));
        assertTrue(model.hasAccommodation(newAccommodation));
        assertTrue(model.hasContact(newContact));
        assertTrue(model.hasDay(newDay));

        logic.execute("redo");
        assertFalse(model.hasActivity(newActivity));
        assertFalse(model.hasAccommodation(newAccommodation));
        assertFalse(model.hasContact(newContact));
        assertFalse(model.hasDay(newDay));
    }

    @Test
    public void execute_undoSchedule_success() throws CommandException, ParseException {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model, generateTempStorage());

        logic.execute("add activity n/hi a/bye du/111");
        Activity newActivity = new Activity(new Name("hi"), new Address("bye"), null,
                null, new HashSet<>(), new Duration(111), new Priority(0));
        assertTrue(model.hasActivity(newActivity));
        logic.execute("add days 1");
        Day newDay = new Day();
        assertTrue(model.hasDay(newDay));
        logic.execute("schedule 1 st/0500 d/1");
        Index dayIndex = Index.fromOneBased(1);
        assertTrue(model.getDay(dayIndex).hasActivity(newActivity));

        logic.execute("undo");
        assertFalse(model.getDay(dayIndex).hasActivity(newActivity));

        logic.execute("redo");
        assertTrue(model.getDay(dayIndex).hasActivity(newActivity));
    }

    @Test
    public void execute_undoUnschedule_success() throws CommandException, ParseException {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model, generateTempStorage());

        logic.execute("add activity n/hi a/bye du/111");
        Activity newActivity = new Activity(new Name("hi"), new Address("bye"), null,
                null, new HashSet<>(), new Duration(111), new Priority(0));
        assertTrue(model.hasActivity(newActivity));
        logic.execute("add days 1");
        Day newDay = new Day();
        assertTrue(model.hasDay(newDay));
        logic.execute("schedule 1 st/0500 d/1");
        Index dayIndex = Index.fromOneBased(1);
        assertTrue(model.getDay(dayIndex).hasActivity(newActivity));
        logic.execute("unschedule 1 d/1");
        assertFalse(model.getDay(dayIndex).hasActivity(newActivity));

        logic.execute("undo");
        assertTrue(model.getDay(dayIndex).hasActivity(newActivity));

        logic.execute("redo");
        assertFalse(model.getDay(dayIndex).hasActivity(newActivity));
    }

    /**
     * A method to generate a dummy Storage for testing purposes.
     */
    public Storage generateTempStorage() {
        Path contactPath = testFolder.resolve("TempContact.json");
        Path accommodationPath = testFolder.resolve("TempAccommodation.json");
        Path activityPath = testFolder.resolve("TempActivity.json");
        Path itineraryPath = testFolder.resolve("TempItinerary.json");
        Path userPrefPath = testFolder.resolve("TempUserPref.json");

        JsonContactStorage contactStorage = new JsonContactStorage(contactPath);
        JsonAccommodationStorage accommodationStorage = new JsonAccommodationStorage(accommodationPath);
        JsonActivityStorage activityStorage = new JsonActivityStorage(activityPath);
        JsonItineraryStorage itineraryStorage = new JsonItineraryStorage(itineraryPath);
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(userPrefPath);

        return new StorageManager(accommodationStorage, activityStorage, contactStorage, itineraryStorage,
                userPrefsStorage);
    }
}
