package seedu.system.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.system.commons.core.GuiSettings;
import seedu.system.commons.core.LogsCenter;
import seedu.system.logic.commands.Command;
import seedu.system.logic.commands.CommandResult;
import seedu.system.logic.commands.exceptions.CommandException;
import seedu.system.logic.parser.SystemParser;
import seedu.system.logic.parser.exceptions.ParseException;
import seedu.system.model.Model;
import seedu.system.model.ReadOnlyData;
import seedu.system.model.competition.Competition;
import seedu.system.model.participation.Participation;
import seedu.system.model.person.Person;
import seedu.system.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final SystemParser systemParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        systemParser = new SystemParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = systemParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.savePersonData(model.getPersons());
            storage.saveCompetitionData(model.getCompetitions());
            storage.saveParticipationData(model.getParticipations());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyData getAddressBook() {
        return model.getPersons();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    public ObservableList<Competition> getFilteredCompetitionList() {
        return model.getFilteredCompetitionList();
    }

    @Override
    public ObservableList<Participation> getFilteredParticipationList() {
        return model.getFilteredParticipationList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getUserPrefsFilePath();
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
