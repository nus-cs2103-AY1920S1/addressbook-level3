package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.events.Event;
import seedu.address.logic.events.EventFactory;
import seedu.address.logic.events.exceptions.EventException;
import seedu.address.logic.parser.PlannerParser;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAccommodation;
import seedu.address.model.ReadOnlyActivity;
import seedu.address.model.ReadOnlyContact;
import seedu.address.model.ReadOnlyItinerary;
import seedu.address.model.contact.Contact;
import seedu.address.model.day.Day;
import seedu.address.model.itineraryitem.accommodation.Accommodation;
import seedu.address.model.itineraryitem.activity.Activity;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final PlannerParser plannerParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        plannerParser = new PlannerParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException, EventException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = plannerParser.parseCommand(commandText);

        if (command instanceof UndoableCommand) {
            Event undoableEvent = EventFactory.parse((UndoableCommand) command, model);
            CommandHistory.addToUndoStack(undoableEvent);
        }
        if (!(command instanceof UndoCommand) && !(command instanceof RedoCommand)) {
            CommandHistory.clearRedoStack();
        }
        commandResult = command.execute(model);

        try {
            storage.saveAccommodation(model.getAccommodations());
            storage.saveActivity(model.getActivities());
            storage.saveContact(model.getContacts());
            storage.saveItinerary(model.getItinerary());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAccommodation getAccommodations() {
        return model.getAccommodations();
    }

    @Override
    public ReadOnlyActivity getActivities() {
        return model.getActivities();
    }

    @Override
    public ReadOnlyContact getContacts() {
        return model.getContacts();
    }

    @Override
    public ReadOnlyItinerary getItinerary() {
        return model.getItinerary();
    }

    @Override
    public ObservableList<Accommodation> getFilteredAccommodationList() {
        return model.getFilteredAccommodationList();
    }

    @Override
    public ObservableList<Activity> getFilteredActivityList() {
        return model.getFilteredActivityList();
    }

    @Override
    public ObservableList<Contact> getFilteredContactList() {
        return model.getFilteredContactList();
    }

    @Override
    public ObservableList<Day> getFilteredItinerary() {
        return model.getFilteredItinerary();
    }

    @Override
    public Path getAccommodationFilePath() {
        return model.getAccommodationFilePath();
    }

    @Override
    public Path getActivityFilePath() {
        return model.getActivityFilePath();
    }

    @Override
    public Path getContactFilePath() {
        return model.getContactFilePath();
    }

    @Override
    public Path getItineraryFilePath() {
        return model.getItineraryFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
