package seedu.weme.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.weme.commons.core.GuiSettings;
import seedu.weme.commons.core.LogsCenter;
import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.logic.parser.ParserUtil;
import seedu.weme.logic.parser.WemeParser;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.model.Model;
import seedu.weme.model.ModelContext;
import seedu.weme.model.ReadOnlyMemeBook;
import seedu.weme.model.meme.Meme;
import seedu.weme.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;

        ModelContext currentContext = model.getContext();
        WemeParser wemeParser = ParserUtil.forContext(currentContext);
        Command command = wemeParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveMemeBook(model.getMemeBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyMemeBook getMemeBook() {
        return model.getMemeBook();
    }

    @Override
    public ObservableList<Meme> getFilteredMemeList() {
        return model.getFilteredMemeList();
    }

    @Override
    public Path getMemeBookFilePath() {
        return model.getMemeBookFilePath();
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
