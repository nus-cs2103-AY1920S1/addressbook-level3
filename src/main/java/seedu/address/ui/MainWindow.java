package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Context;
import seedu.address.model.ContextType;
import seedu.address.model.activity.Activity;
import seedu.address.model.person.Person;

/**
 * The Main Window. Provides the basic application layout containing a status
 * bar in the footer and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private ActivityListPanel activityListPanel;
    private PersonDetailsPanel personDetailsPanel;
    private ActivityDetailsPanel activityDetailsPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    /*
     * Root {@code StackPane} element that contains the command input region.
     */
    @FXML
    private StackPane commandBoxContainer;

    /*
     * Root {@code StackPane} element that contains the main content.
     */
    @FXML
    private StackPane contentContainer;

    /*
     * Root {@code StackPane} element that contains the result display label.
     */
    @FXML
    private StackPane resultDisplayContainer;

    /*
     * Root {@code StackPane} element that contains the status.
     */
    @FXML
    private StackPane statusBarContainer;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Fills up all the containers of this window.
     */
    void fillInnerParts() {
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        activityListPanel = new ActivityListPanel(logic.getFilteredActivityList());

        // Show contacts by default
        contentContainer.getChildren().add(personListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayContainer.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusBarContainer.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxContainer.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    /**
     * Switches the current view in the content container to the view corresponding to the updated
     * {@code ContextType}.
     * @param newContext the {@code ContextType} of the updated GUI view
     */
    private void contextSwitch(Context newContext) {
        requireNonNull(newContext);

        contentContainer.getChildren().clear();

        ContextType newContextType = newContext.getType();

        switch (newContextType) {
        case LIST_ACTIVITY:
            activityListPanel.refreshView();
            contentContainer.getChildren().add(activityListPanel.getRoot());
            break;
        case LIST_CONTACT:
            contentContainer.getChildren().add(personListPanel.getRoot());
            break;
        case VIEW_CONTACT:
            Person viewedContact = newContext.getContact().get();
            List<Activity> associatedActivities = logic.getAssociatedActivities(viewedContact);

            personDetailsPanel = new PersonDetailsPanel(viewedContact, associatedActivities);
            contentContainer.getChildren().add(personDetailsPanel.getRoot());
            break;
        case VIEW_ACTIVITY:
            Activity viewedActivity = newContext.getActivity().get();
            List<Person> associatedPersons = logic.getAssociatedPersons(viewedActivity);

            activityDetailsPanel = new ActivityDetailsPanel(viewedActivity, associatedPersons);
            contentContainer.getChildren().add(activityDetailsPanel.getRoot());
            break;
        default:
            // Do nothing (leave content container empty)
        }
    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    public ActivityListPanel getActivityListPanel() {
        return activityListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            Optional<Context> newContext = commandResult.getUpdatedContext();
            if (newContext.isPresent()) {
                logger.info("Updated context: " + newContext.get().toString());
                contextSwitch(newContext.get());
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
