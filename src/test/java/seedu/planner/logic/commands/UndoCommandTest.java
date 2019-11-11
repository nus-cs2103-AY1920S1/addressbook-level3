package seedu.planner.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
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

public class UndoCommandTest {
    @TempDir
    public Path testFolder;

    @Test
    public void execute_undoAddActivity_success() throws CommandException, ParseException {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model, generateTempStorage());

        logic.execute("add activity n/hi a/bye du/111");
        Activity newActivity = new Activity(new Name("hi"), new Address("bye"), null,
                null, new HashSet<>(), new Duration(111), new Priority(0));
        assertTrue(model.hasActivity(newActivity));
        logic.execute("undo");
        assertFalse(model.hasActivity(newActivity));
    }

    @Test
    public void execute_undoAddAccommodation_success() throws CommandException, ParseException {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model, generateTempStorage());

        logic.execute("add accommodation n/hi a/bye");
        Accommodation newAccommodation = new Accommodation(new Name("hi"), new Address("bye"), null, new HashSet<>());
        assertTrue(model.hasAccommodation(newAccommodation));
        logic.execute("undo");
        assertFalse(model.hasAccommodation(newAccommodation));
    }

    @Test
    public void execute_undoAddContact_success() throws CommandException, ParseException {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model, generateTempStorage());

        logic.execute("add contact n/hi p/123");
        Contact newContact = new Contact(new Name("hi"), new Phone("123"), null, null, new HashSet<>());
        assertTrue(model.hasContact(newContact));
        logic.execute("undo");
        assertFalse(model.hasContact(newContact));
    }

    @Test
    public void execute_undoAddDay_success() throws CommandException, ParseException {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model, generateTempStorage());

        logic.execute("add days 1");
        Day newDay = new Day();
        assertTrue(model.hasDay(newDay));
        logic.execute("undo");
        assertFalse(model.hasDay(newDay));
    }

    @Test
    public void execute_undoDeleteActivity_success() throws CommandException, ParseException {
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
    }

    @Test
    public void execute_undoDeleteAccommodation_success() throws CommandException, ParseException {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model, generateTempStorage());

        logic.execute("add accommodation n/hi a/bye");
        Accommodation newAccommodation = new Accommodation(new Name("hi"), new Address("bye"), null, new HashSet<>());
        assertTrue(model.hasAccommodation(newAccommodation));
        logic.execute("delete accommodation 1");
        assertFalse(model.hasAccommodation(newAccommodation));
        logic.execute("undo");
        assertTrue(model.hasAccommodation(newAccommodation));
    }

    @Test
    public void execute_undoDeleteContact_success() throws CommandException, ParseException {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model, generateTempStorage());

        logic.execute("add contact n/hi p/123");
        Contact newContact = new Contact(new Name("hi"), new Phone("123"), null, null, new HashSet<>());
        assertTrue(model.hasContact(newContact));
        logic.execute("delete contact 1");
        assertFalse(model.hasContact(newContact));
        logic.execute("undo");
        assertTrue(model.hasContact(newContact));
    }

    @Test
    public void execute_undoDeleteDay_success() throws CommandException, ParseException {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model, generateTempStorage());

        logic.execute("add days 1");
        Day newDay = new Day();
        assertTrue(model.hasDay(newDay));
        logic.execute("delete day 1");
        assertFalse(model.hasDay(newDay));
        logic.execute("undo");
        assertTrue(model.hasDay(newDay));
    }

    @Test
    public void execute_undoEditActivity_success() throws CommandException, ParseException {
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
    }

    @Test
    public void execute_undoEditAccommodation_success() throws CommandException, ParseException {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model, generateTempStorage());

        logic.execute("add accommodation n/hi a/bye");
        Accommodation newAccommodation = new Accommodation(new Name("hi"), new Address("bye"), null, new HashSet<>());
        assertTrue(model.hasAccommodation(newAccommodation));
        logic.execute("edit accommodation 1 n/Different");
        Accommodation editedAccommodation = new Accommodation(new Name("Different"), new Address("bye"), null, new HashSet<>());
        assertTrue(model.hasAccommodation(editedAccommodation));
        assertFalse(model.hasAccommodation(newAccommodation));

        logic.execute("undo");
        assertFalse(model.hasAccommodation(editedAccommodation));
        assertTrue(model.hasAccommodation(newAccommodation));
    }

    @Test
    public void execute_undoEditContact_success() throws CommandException, ParseException {
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
    }

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