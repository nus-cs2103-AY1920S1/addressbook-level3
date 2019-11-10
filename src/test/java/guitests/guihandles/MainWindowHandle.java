package guitests.guihandles;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Provides a handle for {@code MainWindow}.
 *
 * Adapted from:
 * https://github.com/se-edu/addressbook-level4/blob/master/src/test/java/guitests/guihandles/MainWindowHandle.java
 */
public class MainWindowHandle extends StageHandle {

    private final SinglePanelViewHandle singlePanelView;
    private final ResultDisplayHandle resultDisplay;
    private final CommandBoxHandle commandBox;
    private final StatusBarFooterHandle statusBarFooter;
    private final MainMenuHandle mainMenu;
    private final Stage stage;

    public MainWindowHandle(Stage stage) {
        super(stage);
        this.stage = stage;

        singlePanelView = new SinglePanelViewHandle(getChildNode(SinglePanelViewHandle.SINGLE_PANEL_VIEW_ID));
        resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
        commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
        mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
    }

    public SinglePanelViewHandle getSinglePanelView() {
        return singlePanelView;
    }

    public ResultDisplayHandle getResultDisplay() {
        return resultDisplay;
    }

    public CommandBoxHandle getCommandBox() {
        return commandBox;
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return statusBarFooter;
    }

    public MainMenuHandle getMainMenu() {
        return mainMenu;
    }

    public void closeMainWindowExternally() {
        guiRobot.interact(() -> stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST)));
    }
}
