package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyProjectList;
import seedu.address.model.performanceoverview.PerformanceOverview;
import seedu.address.model.person.Person;
import seedu.address.model.project.Project;
import seedu.address.storage.Storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Stack;
import java.util.logging.Logger;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;
    // Stores all the changes in Ui display
    private Stack<UiEvent> viewHistory = new Stack<>();

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, IllegalValueException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveAddressBook(model.getAddressBook());
            storage.saveProjectList(model.getProjectList());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public CommandResult executeImageDrop(File imgFile, Person person) throws CommandException, IllegalValueException {
        int index = 1;
        for (Person curr : model.getFilteredPersonList()) {
            if (curr.getName().equals(person.getName())) {
                break;
            } else {
                index++;
            }
        }
        String commandText = "addProfilePicture " + index + " f/" + imgFile.getPath();
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        commandResult = execute(commandText);
        return commandResult;
    }

    //======== AddressBook =======================================================================

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

    //======== ProjectList =======================================================================

    @Override
    public ReadOnlyProjectList getProjectList() {
        return model.getProjectList();
    }

    @Override
    public ObservableList<Project> getFilteredProjectList() {
        return model.getFilteredProjectList();
    }

    @Override
    public Path getProjectListFilePath() {
        return model.getProjectListFilePath();
    }

    @Override
    public Optional<Project> getWorkingProject() {
        return model.getWorkingProject();
    }

    @Override
    public void removeWorkingProject() {
        model.removeWorkingProject();
    }

    @Override
    public void setWorkingProject(Project project) {
        model.setWorkingProject(project);
    }

    @Override
    public PerformanceOverview getPerformanceOverview() {
        return model.getPerformanceOverview();
    }

    //======== GUI =======================================================================

    @Override
    public void addUiEvent(UiEvent event) {
        this.viewHistory.push(event);
    }

    @Override
    public UiEvent getPreviousEvent() {
        viewHistory.pop();
        return viewHistory.peek();
    }

    @Override
    public void eraseHistory() {
        viewHistory = new Stack<>();
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
