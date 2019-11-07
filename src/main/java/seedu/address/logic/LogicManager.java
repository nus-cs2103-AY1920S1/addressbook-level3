package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.util.Pair;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UserSettings;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddCriteriaCommand;
import seedu.address.logic.commands.AddPolicyCommand;
import seedu.address.logic.commands.AddPolicyTagCommand;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.logic.commands.AssignPolicyCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteCriteriaCommand;
import seedu.address.logic.commands.DeletePolicyCommand;
import seedu.address.logic.commands.DeletePolicyTagCommand;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.commands.DisplayCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditPolicyCommand;
import seedu.address.logic.commands.EligiblePeopleCommand;
import seedu.address.logic.commands.EligiblePoliciesCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExpandPersonCommand;
import seedu.address.logic.commands.ExpandPolicyCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindPolicyCommand;
import seedu.address.logic.commands.FindPolicyholdersCommand;
import seedu.address.logic.commands.FindTagPeopleCommand;
import seedu.address.logic.commands.FindTagPolicyCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListBinCommand;
import seedu.address.logic.commands.ListPeopleCommand;
import seedu.address.logic.commands.ListPolicyCommand;
import seedu.address.logic.commands.RestoreCommand;
import seedu.address.logic.commands.SuggestionCommand;
import seedu.address.logic.commands.SuggestionSwitchCommand;
import seedu.address.logic.commands.UnassignPolicyCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.binitem.BinItem;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;
import seedu.address.storage.Storage;

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
        addressBookParser = new AddressBookParser(model.getUserSettings().isSuggestionsOn());
        initialiseCommandsInParserUtil();
    }

    /**
     * Initialises all the command words in ParserUtil. Needs to be updated every time a command is added.
     */
    //todo : update command words
    public void initialiseCommandsInParserUtil() {
        ParserUtil.addCommands(AddCommand.COMMAND_WORD, AddCriteriaCommand.COMMAND_WORD,
            AddPolicyCommand.COMMAND_WORD, AddPolicyTagCommand.COMMAND_WORD, AddTagCommand.COMMAND_WORD,
            AssignPolicyCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD,
            DeleteCriteriaCommand.COMMAND_WORD, DeletePolicyCommand.COMMAND_WORD, DeletePolicyTagCommand.COMMAND_WORD,
            DeleteTagCommand.COMMAND_WORD, EditCommand.COMMAND_WORD, EditPolicyCommand.COMMAND_WORD,
            EligiblePeopleCommand.COMMAND_WORD, EligiblePoliciesCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD,
            ExpandPolicyCommand.COMMAND_WORD, ExpandPersonCommand.COMMAND_WORD, FindCommand.COMMAND_WORD,
            FindPolicyCommand.COMMAND_WORD, FindPolicyholdersCommand.COMMAND_WORD, FindTagPeopleCommand.COMMAND_WORD,
            FindTagPolicyCommand.COMMAND_WORD, HelpCommand.COMMAND_WORD, HistoryCommand.COMMAND_WORD,
            ListPeopleCommand.COMMAND_WORD, ListPolicyCommand.COMMAND_WORD, SuggestionSwitchCommand.COMMAND_WORD,
            UnassignPolicyCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD, DisplayCommand.COMMAND_WORD,
            ListBinCommand.COMMAND_WORD, RestoreCommand.COMMAND_WORD);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText);

        addCommandToHistory(command, commandText);
        commandResult = command.execute(model);

        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public CommandResult execute(String commandText, boolean isSystemInput) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText, isSystemInput);

        addCommandToHistory(command, commandText);
        commandResult = command.execute(model);

        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
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
    public ObservableList<Policy> getFilteredPolicyList() {
        return model.getFilteredPolicyList();
    }

    @Override
    public ObservableList<Pair<String, String>> getHistoryList() {
        return model.getHistoryList();
    }

    @Override
    public ObservableList<BinItem> getFilteredBinItemList() {
        return model.getFilteredBinItemList();
    }

    @Override
    public ObservableMap<String, Integer> getPolicyPopularityBreakdown() {
        return model.getPolicyPopularityBreakdown();
    }

    @Override
    public ObservableMap<String, Integer> getAgeGroupBreakdown() {
        return model.getAgeGroupBreakdown();
    }

    @Override
    public ObservableMap<String, Integer> getGenderBreakdown() {
        return model.getGenderBreakdown();
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

    @Override
    public UserSettings getUserSettings() {
        return model.getUserSettings();
    }

    @Override
    public void setUserSettings(UserSettings userSettings) {
        model.setUserSettings(userSettings);
    }

    @Override
    public void setUserSettings() {
        boolean suggestionOn = addressBookParser.isSuggestionOn();
        UserSettings userSettings = new UserSettings(suggestionOn,
            model.getUserSettings().getTimeToLiveAmount(),
            model.getUserSettings().getTimeToLiveUnit());
        model.setUserSettings(userSettings);
    }

    /**
     * Adds the command to the command history list of the model.
     */
    private void addCommandToHistory(Command command, String commandText) throws ParseException {
        // to prevent adding invalid commands and adding to history during merging
        Optional<String> commandWord = addressBookParser.getCommandWord(commandText);

        if (commandWord.isPresent() && !(command instanceof SuggestionCommand)) {
            model.addCommandToHistory(commandWord.get(), commandText);
        }
    }
}
