package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.suggestions.SuggestionLogicManager;
import seedu.address.logic.parser.TimeBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.TimeBook;
import seedu.address.model.display.scheduledisplay.ScheduleDisplay;
import seedu.address.model.display.sidepanel.GroupDisplay;
import seedu.address.model.display.sidepanel.PersonDisplay;
import seedu.address.model.display.sidepanel.SidePanelDisplay;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.storage.Storage;
import seedu.address.ui.SuggestingCommandBox.SuggestionLogic;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic, SuggestionLogic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final SuggestionLogic suggestionLogic;
    private final TimeBookParser timeBookParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        this.suggestionLogic = new SuggestionLogicManager(model);
        timeBookParser = new TimeBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = timeBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveTimeBook(model.getTimeBook());
            logger.info("Attempting to save timebook");

        } catch (IOException ioe) {
            logger.severe("Unable to save timebook");
        }
        return commandResult;
    }

    @Override
    public TimeBook getTimeBook() {
        return model.getTimeBook();
    }

    //=========== UI Model =============================================================

    @Override
    public ScheduleDisplay getScheduleDisplay() {
        return model.getScheduleDisplay();
    }

    @Override
    public SidePanelDisplay getSidePanelDisplay() {
        return model.getSidePanelDisplay();
    }

    @Override
    public ObservableList<PersonDisplay> getFilteredPersonDisplayList() {
        ObservableList<Person> persons = model.getObservablePersonList();
        return FXCollections.observableList(persons.stream()
                .map(person -> new PersonDisplay((Person) person))
                .collect(Collectors.toCollection(ArrayList::new)));
    }

    @Override
    public ObservableList<GroupDisplay> getFilteredGroupDisplayList() {
        ObservableList<Group> persons = model.getObservableGroupList();
        return FXCollections.observableList(persons.stream()
                .map(group -> new GroupDisplay(group)).collect(Collectors.toCollection(ArrayList::new)));
    }


    //=========== Suggesters =============================================================

    @Override
    public ArrayList<String> personSuggester(String prefix) {
        return model.personSuggester(prefix);
    }

    @Override
    public ArrayList<String> personSuggester(String prefix, String groupName) {
        return model.personSuggester(prefix, groupName);
    }

    @Override
    public ArrayList<String> groupSuggester(String prefix) {
        return model.groupSuggester(prefix);
    }

    @Override
    public ArrayList<String> validLocationSuggester(String prefix) {
        return model.validLocationSuggester(prefix);
    }

    //=========== Legacy =============================================================

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getObservablePersonList();
    }

    @Override
    public ObservableList<Group> getGroupList() {
        //To Do.
        return model.getObservableGroupList();
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
    public ObservableList<String> getSuggestions(final String commandText, final int caretPosition) {
        return suggestionLogic.getSuggestions(commandText, caretPosition);
    }

    @Override
    public SelectionResult selectSuggestion(
            final String commandText, final int caretPosition, final String selectedValue) {
        return suggestionLogic.selectSuggestion(commandText, caretPosition, selectedValue);
    }
}
