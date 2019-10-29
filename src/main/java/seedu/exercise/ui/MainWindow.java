package seedu.exercise.ui;

import java.util.Optional;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.exercise.commons.core.GuiSettings;
import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.logic.Logic;
import seedu.exercise.logic.commands.CommandResult;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.logic.commands.statistic.Statistic;
import seedu.exercise.logic.parser.exceptions.ParseException;
import seedu.exercise.model.resource.Exercise;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static final String DEFAULT_MESSAGE = "Select an exercise/regime/schedule to display its info.";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private ResolveWindow resolveWindow;
    private ExerciseListPanel exerciseListPanel;
    private RegimeListPanel regimeListPanel;
    private ScheduleListPanel scheduleListPanel;
    private SuggestionListPanel suggestionListPanel;
    private InfoDisplayPanel infoDisplayPanel;
    private StatsDisplayPanel statsDisplayPanel;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private TabPane resourceListPanelPlaceholder;

    @FXML
    private Tab exerciseListTabPlaceholder;

    @FXML
    private Tab regimeListTabPlaceholder;

    @FXML
    private Tab scheduleListTabPlaceholder;

    @FXML
    private Tab suggestionListTabPlaceholder;

    @FXML
    private StackPane infoDisplayPanelPlaceholder;

    @FXML
    private ImageView logoImageView;

    @FXML
    private StackPane chartPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());
        primaryStage.setTitle("ExerHealth");

        helpWindow = new HelpWindow();

        resourceListPanelPlaceholder.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            displayInfoPanelResult();
        });
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        Image imageView = new Image(getClass().getResource("/images/logo_eH.png").toExternalForm());
        logoImageView.setImage(imageView);

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        resolveWindow = new ResolveWindow(logic, resultDisplay);

        exerciseListPanel = new ExerciseListPanel(logic.getFilteredExerciseList());
        exerciseListTabPlaceholder = new Tab();
        exerciseListTabPlaceholder.setContent((exerciseListPanel).getExerciseListView());

        regimeListPanel = new RegimeListPanel(logic.getFilteredRegimeList());
        regimeListTabPlaceholder = new Tab();
        regimeListTabPlaceholder.setContent(regimeListPanel.getRegimeListView());

        scheduleListPanel = new ScheduleListPanel(logic.getFilteredScheduleList());
        scheduleListTabPlaceholder = new Tab();
        scheduleListTabPlaceholder.setContent(scheduleListPanel.getScheduleListView());

        suggestionListPanel = new SuggestionListPanel(logic.getSuggestedExerciseList());
        suggestionListTabPlaceholder = new Tab();
        suggestionListTabPlaceholder.setContent(suggestionListPanel.getSuggestionListView());

        resourceListPanelPlaceholder.getTabs().add(exerciseListTabPlaceholder);
        resourceListPanelPlaceholder.getTabs().add(regimeListTabPlaceholder);
        resourceListPanelPlaceholder.getTabs().add(scheduleListTabPlaceholder);
        resourceListPanelPlaceholder.getTabs().add(suggestionListTabPlaceholder);

        displayDefaultMessage();

        chartPlaceholder.getChildren().add(new LineChartPanel(logic.getStatistic()).getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
        commandBox.requestFocus();
    }

    /**
     * Sets the chart type in chartPlaceholder according to user input.
     */
    private void setChart() {
        Statistic statistic = logic.getStatistic();
        String chart = statistic.getChart();
        chartPlaceholder.getChildren().clear();
        if (chart.equals("barchart")) {
            chartPlaceholder.getChildren().add(new BarChartPanel(statistic).getRoot());
        } else if (chart.equals("linechart")) {
            chartPlaceholder.getChildren().add(new LineChartPanel(statistic).getRoot());
        } else {
            chartPlaceholder.getChildren().add(new PieChartPanel(statistic).getRoot());
        }
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
     * Executes the command and returns the result.
     *
     * @see seedu.exercise.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
            setChart();

            shouldShowWindowsBasedOnCommandResult(commandResult);
            shouldExitAppBasedOnCommandResult(commandResult);
            updateResourceListTab(commandResult);
            displayInfoPanelResult();

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Checks if a secondary window should be shown based on the command results.
     * Method will show the windows if it is to be shown.
     */
    private void shouldShowWindowsBasedOnCommandResult(CommandResult commandResult) {
        if (commandResult.isShowHelp()) {
            handleHelp();
        }

        if (commandResult.isShowResolve()) {
            handleResolve();
        }
    }

    private void shouldExitAppBasedOnCommandResult(CommandResult commandResult) {
        if (commandResult.isExit()) {
            handleExit();
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

    /**
     * Opens the resolve window and blocks all events until closed
     */
    @FXML
    private void handleResolve() {
        resolveWindow.setLeftRightPanel();
        if (resolveWindow.isShowing()) {
            resolveWindow.focus();
        } else {
            resolveWindow.show();
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
        resolveWindow.hideAndClearPanels();
        primaryStage.hide();
    }

    /**
     * Checks if a the resource list has to change based on the {@code CommandResult}
     */
    private void updateResourceListTab(CommandResult commandResult) {
        switch (commandResult.getShowListResourceType().name()) {
        case "NULL":
            //no change to GUI
            return;
        case "EXERCISE":
            handleShowExerciseList();
            return;
        case "REGIME":
            handleShowRegimeList();
            return;
        case "SCHEDULE":
            handleShowScheduleList();
            return;
        case "SUGGESTION":
            handleShowSuggestionList();
            return;
        default:
            throw new AssertionError(ListResourceType.LIST_RESOURCE_TYPE_CONSTRAINTS);
        }
    }

    private void changeTab(Tab tab) {
        resourceListPanelPlaceholder.getSelectionModel().select(tab);
    }

    private void handleShowExerciseList() {
        resourceListPanelPlaceholder.getSelectionModel().select(exerciseListTabPlaceholder);
    }

    private void handleShowRegimeList() {
        resourceListPanelPlaceholder.getSelectionModel().select(regimeListTabPlaceholder);
    }

    private void handleShowScheduleList() {
        resourceListPanelPlaceholder.getSelectionModel().select(scheduleListTabPlaceholder);
    }

    private void handleShowSuggestionList() {
        resourceListPanelPlaceholder.getSelectionModel().select(suggestionListTabPlaceholder);
    }

    private void updateDisplayPanel(ExerciseInfoPanel newExerciseDisplay) {
        infoDisplayPanelPlaceholder.getChildren().clear();
        infoDisplayPanelPlaceholder.getChildren().add(newExerciseDisplay.getRoot());
    }

    private void displayDefaultMessage() {
        infoDisplayPanelPlaceholder.getChildren().clear();
        infoDisplayPanelPlaceholder.getChildren().add(new Label(DEFAULT_MESSAGE));
    }

    /**
     * Displays the selected exercise on the info panel if there are any. Otherwise, it will display be the
     * default message.
     */
    private void displayInfoPanelResult() {
        Optional<Exercise> selectedExercise = exerciseListPanel.getSelectedExercise();
        if (selectedExercise.isPresent()) {
            updateDisplayPanel(new ExerciseInfoPanel(selectedExercise.get()));
        } else {
            displayDefaultMessage();
        }
    }

}
