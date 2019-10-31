package dukecooks.ui;

import java.util.logging.Logger;

import dukecooks.commons.core.Event;
import dukecooks.commons.core.GuiSettings;
import dukecooks.commons.core.LogsCenter;
import dukecooks.logic.Logic;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.logic.parser.exceptions.ParseException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    private UiManager uiManager;

    // Independent Ui parts residing in this Ui container
    private DashboardListPanel dashboardListPanel;
    private RecipeListPanel recipeListPanel;
    private MealPlanListPanel mealPlanListPanel;
    private RecordListPanel recordListPanel;
    private PersonListPanel personListPanel;
    private ExerciseListPanel exerciseListPanel;
    private DiaryListPanel diaryListPanel;
    private ResultDisplay resultDisplay;
    private RewardWindow rewardWindow;
    private HelpWindow helpWindow;
    private Event event;

    private StatusBarFooter dashboardPathStatus;
    private StatusBarFooter recipePathStatus;
    private StatusBarFooter mealPlanPathStatus;
    private StatusBarFooter recordPathStatus;
    private StatusBarFooter personPathStatus;
    private StatusBarFooter exercisePathStatus;
    private StatusBarFooter diaryPathStatus;

    @FXML
    private Label featureMode;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane versatilePanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private Button home;

    @FXML
    private Button profile;

    @FXML
    private Button recipe;

    @FXML
    private Button exercise;

    @FXML
    private Button health;

    @FXML
    private Button diary;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI

        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
        rewardWindow = new RewardWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     * with health records tab view.
     */
    void fillInnerParts() {
        initializePanels();
        initializeFilePaths();

        //default start up screen - dashboard page
        versatilePanelPlaceholder.getChildren().add(dashboardListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        statusbarPlaceholder.getChildren().add(dashboardPathStatus.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Initializes all Panels.
     */
    void initializePanels() {
        dashboardListPanel = new DashboardListPanel(logic.getFilteredDashboardList());
        recipeListPanel = new RecipeListPanel(logic.getFilteredRecipeList());
        mealPlanListPanel = new MealPlanListPanel(logic.getFilteredMealPlanList());
        recordListPanel = new RecordListPanel(logic.getFilteredRecordList());
        exerciseListPanel = new ExerciseListPanel(logic.getFilteredExerciseList());
        diaryListPanel = new DiaryListPanel(logic.getFilteredDiaryList(), 0);
    }

    /**
     * Initializes all Filepaths.
     */
    void initializeFilePaths() {
        dashboardPathStatus = new StatusBarFooter(logic.getDashboardFilePath());
        recipePathStatus = new StatusBarFooter(logic.getRecipesFilePath());
        mealPlanPathStatus = new StatusBarFooter(logic.getMealPlansFilePath());
        recordPathStatus = new StatusBarFooter(logic.getHealthRecordsFilePath());
        personPathStatus = new StatusBarFooter(logic.getUserProfileFilePath());
        //TODO EXERCISE: set up getExerciseFilePath() method in Logic class
        diaryPathStatus = new StatusBarFooter(logic.getDiaryFilePath());
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
     * Opens the reward window
     */
    @FXML
    public void handleReward() {
        if (!rewardWindow.isShowing()) {
            rewardWindow.show();
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
     * Handles mode view switches of the application.
     */
    @FXML
    void handleSwitch() {
        event = Event.getInstance();
        String mode = event.getMode();
        String type = event.getType();

        //reset panel and statusbar
        versatilePanelPlaceholder.getChildren().clear();
        statusbarPlaceholder.getChildren().clear();

        //TODO NOTE: Do your internal #handleSwitch in individual panels - rmb to parse type as param

        switch (mode) {
        case "dashboard":
            //TODO:
            versatilePanelPlaceholder.getChildren().add(dashboardListPanel.getRoot());
            statusbarPlaceholder.getChildren().add(dashboardPathStatus.getRoot());
            featureMode.setText("Dashboard");
            break;
        case "recipe":
            //TODO:
            versatilePanelPlaceholder.getChildren().add(recipeListPanel.getRoot());
            statusbarPlaceholder.getChildren().add(recipePathStatus.getRoot());
            featureMode.setText("Recipe Book: Recipes");
            recipeListPanel.handleSwitch(type);
            break;
        case "mealPlan":
            versatilePanelPlaceholder.getChildren().add(mealPlanListPanel.getRoot());
            statusbarPlaceholder.getChildren().add(mealPlanPathStatus.getRoot());
            featureMode.setText("Recipe Book: Meal Plans");
            mealPlanListPanel.handleSwitch(type);
            break;
        case "health":
            versatilePanelPlaceholder.getChildren().add(recordListPanel.getRoot());
            statusbarPlaceholder.getChildren().add(recordPathStatus.getRoot());
            featureMode.setText("Health Records");
            recordListPanel.handleSwitch(type);
            break;
        case "exercise":
            //TODO:
            versatilePanelPlaceholder.getChildren().add(exerciseListPanel.getRoot());
            //TODO EXERCISE: add exercisePathStatus once missing getExerciseFilePath() is in Logic class
            // - refer to initializeFilePath()
            //statusbarPlaceholder.getChildren().add(exercisePathStatus.getRoot());
            featureMode.setText("Exercise");
            break;
        case "diary":
            //TODO:
            versatilePanelPlaceholder.getChildren().add(diaryListPanel.getRoot());
            statusbarPlaceholder.getChildren().add(diaryPathStatus.getRoot());
            featureMode.setText("Diary");
            diaryListPanel.handleSwitch(type);
            break;
        default:
            //TODO: PLEASE EDIT THIS ERROR MESSAGE TO SOMETHING USEFUL!
            throw new AssertionError("There should exist a valid event for UiManager!");
        }
    }

    /**
     * Switch to home page.
     */
    @FXML
    private void switchHome() {
        event = Event.getInstance();
        event.set("dashboard", "all");
        resultDisplay.setFeedbackToUser("");
    }

    /**
     * Switch to recipe page.
     */
    @FXML
    private void switchRecipe() {
        event = Event.getInstance();
        event.set("recipe", "all");
        resultDisplay.setFeedbackToUser("");
    }

    /**
     * Switch to exercise page.
     */
    @FXML
    private void switchExercise() {
        event = Event.getInstance();
        event.set("exercise", "all");
        resultDisplay.setFeedbackToUser("");
    }

    /**
     * Switch to health page.
     */
    @FXML
    private void switchHealth() throws CommandException, ParseException {
        logic.execute("list health");
        resultDisplay.setFeedbackToUser("");
    }

    /**
     * Switch to diary page.
     */
    @FXML
    private void switchDiary() {
        event = Event.getInstance();
        event.set("diary", "all");
        resultDisplay.setFeedbackToUser("");
    }

    /**
     * Executes the command and returns the result.
     *
     * @see Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowReward()) {
                handleReward();
            }

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
