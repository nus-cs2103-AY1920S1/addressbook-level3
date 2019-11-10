package seedu.planner.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.planner.commons.core.GuiSettings;
import seedu.planner.commons.core.LogsCenter;
import seedu.planner.logic.commands.Command;
import seedu.planner.logic.commands.SetCommand;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.logic.parser.PlannerParser;
import seedu.planner.logic.parser.exceptions.ParseException;

import seedu.planner.model.Model;
import seedu.planner.model.ReadOnlyAccommodation;
import seedu.planner.model.ReadOnlyActivity;
import seedu.planner.model.ReadOnlyContact;
import seedu.planner.model.ReadOnlyItinerary;
import seedu.planner.model.accommodation.Accommodation;
import seedu.planner.model.activity.Activity;
import seedu.planner.model.contact.Contact;
import seedu.planner.model.day.Day;
import seedu.planner.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    public static final String FILE_DELETION_ERROR_MESSAGE = "Could not delete file: ";
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
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = plannerParser.parseCommand(commandText);
        commandResult = command.execute(model);

        if (command instanceof SetCommand) {
            try {
                storage.deletePlannerFilePath();
            } catch (IOException ioe) {
                throw new CommandException(FILE_DELETION_ERROR_MESSAGE + ioe, ioe);
            }
            storage.setAccommodationFilePath(getAccommodationFilePath());
            storage.setActivityFilePath(getActivityFilePath());
            storage.setContactFilePath(getContactFilePath());
            storage.setItineraryFilePath(getItineraryFilePath());
        }

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
    public Path getPlannerFilePath() {
        return model.getPlannerFilePath();
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
