package seedu.address.logic;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AlfredParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.CommandRecord;
import seedu.address.model.Model;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.Team;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final AlfredParser alfredParser;

    public LogicManager(Model model) {
        this.model = model;
        alfredParser = new AlfredParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = alfredParser.parseCommand(commandText);
        commandResult = command.execute(model);

        return commandResult;
    }

    @Override
    public ObservableList<Participant> getFilteredParticipantList() {
        return model.getFilteredParticipantList();
    }

    @Override
    public ObservableList<Team> getFilteredTeamList() {
        return model.getFilteredTeamList();
    }

    @Override
    public ObservableList<Mentor> getFilteredMentorList() {
        return model.getFilteredMentorList();
    }

    @Override
    public ArrayList<CommandRecord> getCommandHistory() {
        return model.getCommandHistory();
    }

    //TODO: May update the three methods below to get Alfred file path instead
    @Override
    public Path getParticipantListFilePath() {
        return model.getParticipantListFilePath();
    }

    @Override
    public Path getTeamListFilePath() {
        return model.getTeamListFilePath();
    }

    @Override
    public Path getMentorListFilePath() {
        return model.getMentorListFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public List<String> getUndoCommandHistory() {
        return model.getUndoCommandHistory();
    }

    @Override
    public List<String> getRedoCommandHistory() {
        return model.getRedoCommandHistory();
    }
}
