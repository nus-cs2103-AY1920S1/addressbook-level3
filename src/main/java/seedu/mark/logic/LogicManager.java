package seedu.mark.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.mark.commons.core.GuiSettings;
import seedu.mark.commons.core.LogsCenter;
import seedu.mark.logic.commands.Command;
import seedu.mark.logic.commands.CommandResult;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.parser.AddressBookParser;
import seedu.mark.logic.parser.exceptions.ParseException;
import seedu.mark.model.Model;
import seedu.mark.model.ReadOnlyBookmarkManager;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveBookmarkManager(model.getBookmarkManager());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyBookmarkManager getAddressBook() {
        return model.getBookmarkManager();
    }

    @Override
    public ObservableList<Bookmark> getFilteredPersonList() {
        return model.getFilteredBookmarkList();
    }

    @Override
    public Path getBookmarkManagerFilePath() {
        return model.getBookmarkManagerFilePath();
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
