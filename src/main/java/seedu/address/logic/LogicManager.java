package seedu.address.logic;

import java.io.IOException;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.TravelPalParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.storage.Storage;
import seedu.address.ui.Ui;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final TravelPalParser travelPalParser;

    private Ui ui;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        travelPalParser = new TravelPalParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        assert ui != null : "Tried to execute command without initialising ui.";

        CommandResult commandResult;
        Command command = travelPalParser.parseCommand(commandText, model.getPageStatus());
        commandResult = command.execute(model);

        try {
            storage.saveAddressBook(model.getTravelPal());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    /**
     * Sets the {@code Ui} instance associated with this {@code LogicManager} instance.
     * @param ui Ui instance to use.
     */
    public void setUi(Ui ui) {
        this.ui = ui;
    }
}
