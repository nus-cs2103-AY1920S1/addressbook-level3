package seedu.planner.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.planner.commons.core.GuiSettings;
import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.exceptions.DataConversionException;
import seedu.planner.logic.commands.Command;
import seedu.planner.logic.commands.CopyToCommand;
import seedu.planner.logic.commands.LoadCommand;
import seedu.planner.logic.commands.NewCommand;
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

//@@author OneArmyj
/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_FORMAT_ERROR_MESSAGE = "Data file not in correct format: ";
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
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = plannerParser.parseCommand(commandText);
        commandResult = command.execute(model);

        //@@author ernestyyh
        if (command instanceof SetCommand) {
            storage.deletePlannerFilePath();
        }
        if (command instanceof SetCommand || command instanceof NewCommand || command instanceof LoadCommand
                || command instanceof CopyToCommand) {
            storage.setAccommodationFilePath(getAccommodationFilePath());
            storage.setActivityFilePath(getActivityFilePath());
            storage.setContactFilePath(getContactFilePath());
            storage.setItineraryFilePath(getItineraryFilePath());
        }

        try {
            if (command instanceof LoadCommand) {
                model.setAccommodations(storage.readAccommodation().get());
                model.setActivities(storage.readActivity().get());
                model.setContacts(storage.readContact().get());
                model.setItinerary(storage.readItinerary().get());
            }
            storage.saveAccommodation(model.getAccommodations());
            storage.saveActivity(model.getActivities());
            storage.saveContact(model.getContacts());
            storage.saveItinerary(model.getItinerary());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        } catch (DataConversionException dce) {
            throw new CommandException(FILE_FORMAT_ERROR_MESSAGE + dce, dce);
        }

        return commandResult;
    }

    //@@author OneArmyj
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

    //@@author ernestyyh
    @Override
    public Path getPlannerFilePath() {
        return model.getPlannerFilePath();
    }

    @Override
    public Path getItineraryFilePath() {
        return model.getItineraryFilePath();
    }

    //@@author OneArmyj
    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
