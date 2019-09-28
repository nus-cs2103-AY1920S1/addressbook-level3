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
import seedu.jarvis.logic.commands.exceptions.CommandNotInvertibleException;
import seedu.jarvis.logic.commands.exceptions.DuplicateCommandException;
import seedu.jarvis.logic.parser.JarvisParser;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.logic.version.VersionControl;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ReadOnlyAddressBook;
import seedu.jarvis.model.person.Person;
import seedu.jarvis.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    public static final String VERSION_CONTROL_DUPLICATION_MESSAGE = "Command already saved to Version Control.";
    public static final String VERSION_CONTROL_COMMAND_NOT_INVERTIBLE =
            "Could not add non-invertible command to Version Control";
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
        if (command.hasInverseExecution()) {
            syncVersionControl(command);
        }

        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    /**
     * Adds a {@code Command} to {@code VersionControl} for future reference in undo redo operations.
     *
     * @param command {@code Command} to be saved to {@code VersionControl}.
     * @throws CommandException If {@code Command} instance to be saved to {@code VersionControl} is already exit in
     * {@code VersionControl}.
     */
    private void syncVersionControl(Command command) throws CommandException {
        try {
            VersionControl.INSTANCE.addExecutedCommand(command);
        } catch (CommandNotInvertibleException cnie) {
            throw new CommandException(VERSION_CONTROL_COMMAND_NOT_INVERTIBLE + cnie, cnie);
        } catch (DuplicateCommandException dce) {
            throw new CommandException(VERSION_CONTROL_DUPLICATION_MESSAGE + dce, dce);
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
