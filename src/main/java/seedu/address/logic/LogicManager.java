package seedu.address.logic;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.NoClearCommand;
import seedu.address.logic.commands.NoShortCutCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.FinSecParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.FinSec;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyFinSec;
import seedu.address.model.autocorrectsuggestion.AutocorrectSuggestion;
import seedu.address.model.claim.Claim;
import seedu.address.model.commanditem.CommandItem;
import seedu.address.model.contact.Contact;
import seedu.address.model.income.Income;
import seedu.address.storage.Storage;
import seedu.address.storage.SuggestionsStorage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    public static final String MESSAGE_FINSEC_CLEARED = "FinSec is cleared";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final FinSecParser finSecParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        finSecParser = new FinSecParser(getFilteredCommandsList());
        //set the suggestions list
        SuggestionsStorage.setSuggestionList(getFilteredAutocorrectSuggestionList());
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException,
            IOException, URISyntaxException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = finSecParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveFinSec(model.getFinSec());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    /**
     * Execution method for unknown inputs from user.
     */
    public CommandResult executeUnknownInput(String commandText) throws CommandException,
            IOException, URISyntaxException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        if (commandText.equals("n")) {
            commandResult = new NoShortCutCommand().execute(model);
        } else {
            Command command = finSecParser.checkCommand(commandText, model.getSavedCommand());
            commandResult = command.execute(model);
        }

        try {
            storage.saveFinSec(model.getFinSec());
            //storage.saveCalendar(model.getCalendar());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    /**
     * Executes this during a "clear" input by user
     */
    public CommandResult executeClear(String commandText) throws CommandException {

        CommandResult commandResult;

        if (commandText.equals("N")) {
            commandResult = new NoClearCommand().execute(model);
        } else if (commandText.equals("Y")) {
            model.setFinSec(new FinSec());
            try {
                storage.saveFinSec(model.getFinSec());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
            return new CommandResult(MESSAGE_FINSEC_CLEARED);
        } else {
            throw new CommandException("Input is not valid");
        }


        return commandResult;
    }

    /**
     * Returns the model tagged to this logic
     * @return a model
     */
    public Model getModel() {
        return this.model;
    }

    @Override
    public ReadOnlyFinSec getFinSec() {
        return null;
    }

    @Override
    public ObservableList<Contact> getFilteredContactList() {
        return model.getFilteredContactList();
    }

    //@@author{lawncegoh}
    @Override
    public ObservableList<Claim> getFilteredClaimList() {
        return model.getFilteredClaimList();
    }
    //@@author{lawncegoh}
    @Override
    public ObservableList<Income> getFilteredIncomeList() {
        return model.getFilteredIncomeList();
    }

    @Override
    public ObservableList<AutocorrectSuggestion> getFilteredAutocorrectSuggestionList() {
        return model.getFilteredAutocorrectSuggestionList();
    }

    @Override
    public ObservableList<CommandItem> getFilteredCommandsList() {
        return model.getFilteredCommandsList();
    }

    @Override
    public Path getFinSecFilePath() {
        return model.getFinSecFilePath();
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
