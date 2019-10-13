package seedu.ichifund.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.ichifund.commons.core.GuiSettings;
import seedu.ichifund.commons.core.LogsCenter;
import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.commands.CommandResult;
import seedu.ichifund.logic.commands.exceptions.CommandException;
import seedu.ichifund.logic.parser.IchiFundParser;
import seedu.ichifund.logic.parser.exceptions.ParseException;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.ReadOnlyFundBook;
import seedu.ichifund.model.person.Person;
import seedu.ichifund.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final IchiFundParser ichiFundParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        ichiFundParser = new IchiFundParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = ichiFundParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveFundBook(model.getFundBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyFundBook getFundBook() {
        return model.getFundBook();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public Path getFundBookFilePath() {
        return model.getFundBookFilePath();
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
