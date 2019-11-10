package seedu.exercise.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.exercise.commons.core.GuiSettings;
import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.commons.core.index.Index;
import seedu.exercise.logic.Logic;
import seedu.exercise.logic.commands.CommandResult;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.logic.commands.statistic.Statistic;
import seedu.exercise.logic.parser.exceptions.ParseException;
import seedu.exercise.model.resource.Resource;
import seedu.exercise.ui.util.ChartUtil;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private ResolveWindow resolveWindow;
    private CustomPropertiesWindow customPropertiesWindow;
    private ResourceListPanel exerciseListPanel;
    private ResourceListPanel regimeListPanel;
    private ResourceListPanel scheduleListPanel;
    private ResourceListPanel suggestionListPanel;
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

    @FXML
    private StackPane statsPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());
        primaryStage.setTitle("ExerHealth");

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        Image imageView = new Image(getClass().getResource("/images/logo_no_bg_eH.png").toExternalForm());
        logoImageView.setImage(imageView);

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        resolveWindow = new ResolveWindow(logic, resultDisplay);

        customPropertiesWindow = new CustomPropertiesWindow();

        exerciseListPanel = new ExerciseListPanel(logic.getSortedExerciseList());
        exerciseListTabPlaceholder = new Tab();
        exerciseListTabPlaceholder.setContent(exerciseListPanel.getResourceListView());

        regimeListPanel = new RegimeListPanel(logic.getSortedRegimeList());
        regimeListTabPlaceholder = new Tab();
        regimeListTabPlaceholder.setContent(regimeListPanel.getResourceListView());

        scheduleListPanel = new ScheduleListPanel(logic.getSortedScheduleList());
        scheduleListTabPlaceholder = new Tab();
        scheduleListTabPlaceholder.setContent(scheduleListPanel.getResourceListView());

        suggestionListPanel = new SuggestionListPanel(logic.getSuggestedExerciseList());
        suggestionListTabPlaceholder = new Tab();
        suggestionListTabPlaceholder.setContent(suggestionListPanel.getResourceListView());

        resourceListPanelPlaceholder.getTabs().add(exerciseListTabPlaceholder);
        resourceListPanelPlaceholder.getTabs().add(regimeListTabPlaceholder);
        resourceListPanelPlaceholder.getTabs().add(scheduleListTabPlaceholder);
        resourceListPanelPlaceholder.getTabs().add(suggestionListTabPlaceholder);

        chartPlaceholder.getChildren().add(new LineChartPanel(logic.getStatistic()).getRoot());
        Label totalAndAverage = new Label(getTotalAndAverage());
        statsPlaceholder.getChildren().add(totalAndAverage);

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
        commandBox.requestFocus();

        infoDisplayPanel = new InfoDisplayPanel();
        infoDisplayPanelPlaceholder.getChildren().add(infoDisplayPanel.getRoot());

        initListeners();
        displayInitialList();
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

    private String getTotalAndAverage() {
        Statistic statistic = logic.getStatistic();
        return ChartUtil.totalFormatter(statistic.getCategory(), statistic.getTotal()) + "\n"
            + ChartUtil.averageFormatter(statistic.getCategory(), statistic.getAverage());
    }

    private void setStats() {
        statsPlaceholder.getChildren().clear();
        Label totalAndAverage = new Label(getTotalAndAverage());
        statsPlaceholder.getChildren().add(totalAndAverage);
    }

    private void displayInitialList() {
        changeTab(scheduleListTabPlaceholder);
        infoDisplayPanel.showDefaultMessage();
    }

    /**
     * Initialises a listener for each list panel on the left of the window
     */
    private void initListeners() {
        exerciseListPanel.setOnItemSelectListener(getOnItemSelectListener());
        regimeListPanel.setOnItemSelectListener(getOnItemSelectListener());
        scheduleListPanel.setOnItemSelectListener(getOnItemSelectListener());
        suggestionListPanel.setOnItemSelectListener(getOnItemSelectListener());
        resolveWindow.setOnResolveSuccessListener(getResolveSuccessListener());

        logger.info("Listeners for main window set");
    }

    private ResolveWindow.OnResolveSuccessListener getResolveSuccessListener() {
        return result -> {
            updateResourceListTab(result, -1);
            resultDisplay.setFeedbackToUser(result.getFeedbackToUser());
        };
    }

    private ResourceListPanel.OnItemSelectListener getOnItemSelectListener() {
        return new ResourceListPanel.OnItemSelectListener() {
            @Override
            public void onItemSelect(Resource selected) {
                infoDisplayPanel.update(selected);
            }
        };
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
            setStats();
            resetResourceListTabs();

            shouldShowWindowsBasedOnCommandResult(commandResult);
            shouldExitAppBasedOnCommandResult(commandResult);
            updateResourceListTab(commandResult, -1); // Negative index means nothing is selected.

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Resets the selection models of the resource list panels on the left of the GUI.
     */
    private void resetResourceListTabs() {
        exerciseListPanel.resetListSelection();
        regimeListPanel.resetListSelection();
        scheduleListPanel.resetListSelection();
        suggestionListPanel.resetListSelection();
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

        if (commandResult.isShowCustomProperties()) {
            handleViewCustom();
        }

        if (commandResult.isSelectResource()) {
            handleSelectResource(commandResult);
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
     * Opens the resolve window and blocks all events until closed.
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

    /**
     * Opens the custom properties window or focuses on it if it's already opened.
     */
    @FXML
    private void handleViewCustom() {
        customPropertiesWindow.initialiseTable();
        if (!customPropertiesWindow.isShowing()) {
            customPropertiesWindow.show();
        } else {
            customPropertiesWindow.focus();
        }
    }

    /**
     * Selects the resource at the given index of the desired resource list.
     */
    @FXML
    private void handleSelectResource(CommandResult commandResult) {
        Index selectedIndex = commandResult.getSelectedIndex().get();
        updateResourceListTab(commandResult, selectedIndex.getZeroBased());
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
        customPropertiesWindow.hide();
        primaryStage.hide();
    }

    /**
     * Checks if a the resource list has to change based on the {@code CommandResult}
     */
    private void updateResourceListTab(CommandResult commandResult, int index) {
        logger.info("Changing resource list panel to show " + commandResult.getShowListResourceType());
        switch (commandResult.getShowListResourceType()) {
        case NULL:
            //no change to GUI
            return;
        case EXERCISE:
            handleShowResourceList(exerciseListTabPlaceholder);
            exerciseListPanel.selectGivenIndex(index);
            return;
        case REGIME:
            handleShowResourceList(regimeListTabPlaceholder);
            regimeListPanel.selectGivenIndex(index);
            return;
        case SCHEDULE:
            handleShowResourceList(scheduleListTabPlaceholder);
            scheduleListPanel.selectGivenIndex(index);
            return;
        case SUGGESTION:
            handleShowResourceList(suggestionListTabPlaceholder);
            suggestionListPanel.selectGivenIndex(index);
            return;
        default:
            throw new AssertionError(ListResourceType.LIST_RESOURCE_TYPE_CONSTRAINTS);
        }
    }

    private void changeTab(Tab tab) {
        resourceListPanelPlaceholder.getSelectionModel().select(tab);
    }

    /**
     * Updates the GUI to show the resource list tab and refresh info display panel if the tab did change.
     */
    private void handleShowResourceList(Tab resourceListTabPlaceholder) {
        if (!(isResourceListPanelShown(resourceListTabPlaceholder))) {
            infoDisplayPanel.showDefaultMessage();
        }
        resourceListPanelPlaceholder.getSelectionModel().select(resourceListTabPlaceholder);
    }

    private boolean isResourceListPanelShown(Tab resourceListPlaceholder) {
        return resourceListPanelPlaceholder.getSelectionModel().getSelectedItem().equals(resourceListPlaceholder);
    }
}
