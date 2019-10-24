package seedu.jarvis.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.jarvis.commons.core.GuiSettings;
import seedu.jarvis.commons.core.LogsCenter;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.logic.parser.JarvisParser;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.address.ReadOnlyAddressBook;
import seedu.jarvis.model.address.person.Person;
import seedu.jarvis.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final JarvisParser jarvisParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        jarvisParser = new JarvisParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = jarvisParser.parseCommand(commandText);
        commandResult = command.execute(model);
        // updates model after a successful command execution.
        updateModel(command);
        // saves the model to local storage.
        saveModel();

        return commandResult;
    }

    /**
     * Adds {@code Command} to {@code Model} if it is invertible, so that model can remember it for undo and redo
     * operations.
     *
     * @param command {@code Command} to be checked for having an inverse before it being added to {@code Model} if
     *                               necessary.
     */
    private void updateModel(Command command) {
        if (!command.hasInverseExecution()) {
            return;
        }
        model.rememberExecutedCommand(command);
    }

    /**
     * Saves the address book to local storage.
     * Saves the history manager to local storage.
     *
     * @throws CommandException If there was an {@code IOException} when saving a component to local storage.
     */
    private void saveModel() throws CommandException {
        try {
            storage.saveAddressBook(model.getAddressBook());
            storage.saveCcaTracker(model.getCcaTracker());
            storage.saveHistoryManager(model.getHistoryManager());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
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
