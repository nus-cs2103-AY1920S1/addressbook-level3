package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.StudyBuddyProParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyStudyBuddyPro;
import seedu.address.model.note.Note;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private static FunctionMode mode = FunctionMode.UNDEFINED;

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final CommandHistory commandHistory = new CommandHistory();

    private final Model model;
    private final Storage storage;
    private final StudyBuddyProParser studyBuddyProParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        studyBuddyProParser = new StudyBuddyProParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = studyBuddyProParser.parseCommand(commandText);
        commandResult = command.execute(model);
        commandHistory.addCommand(command);

        try {
            storage.saveStudyBuddyPro(model.getStudyBuddyPro());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyStudyBuddyPro getStudyBuddyPro() {
        return model.getStudyBuddyPro();
    }

    @Override
    public ObservableList<Note> getFilteredNoteList() {
        return model.getFilteredNoteList();
    }

    @Override
    public Path getStudyBuddyProFilePath() {
        return model.getStudyBuddyProFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    public static FunctionMode getMode() {
        return mode;
    }

    public static void setMode(FunctionMode mode) {
        LogicManager.mode = mode;
    }
}
