package guitests.guihandles;

import com.sun.javafx.scene.control.MenuBarButton;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;

/**
 * Provides a handle to the main menu of the app.
 *
 * Adapted from:
 * https://github.com/se-edu/addressbook-level4/blob/master/src/test/java/guitests/guihandles/MainMenuHandle.java
 */
public class MainMenuHandle extends NodeHandle<Node> {
    public static final String MENU_BAR_ID = "#menuBar";

    public MainMenuHandle(Node mainMenuNode) {
        super(mainMenuNode);
    }

    /**
     * Opens the {@code HelpWindow} using the menu bar in {@code MainWindow}.
     */
    public void openHelpWindowUsingMenu() throws Exception {
        clickOnMenuBarButton("#help");
        guiRobot.pauseForHuman();
        clickOnMenuItem("#help", "helpMenuItem");
    }

    /**
     * Opens the {@code HelpWindow} using the menu bar in {@code MainWindow}.
     */
    public void exitViaMenu() throws Exception {
        clickOnMenuBarButton("#file");
        guiRobot.pauseForHuman();
        clickOnMenuItem("#file", "exit");
    }

    /**
     * Opens the {@code HelpWindow} by pressing the shortcut key associated
     * with the menu bar in {@code MainWindow}.
     */
    public void openHelpWindowUsingAccelerator() {
        pushKey(KeyCode.F1);
    }

    /**
     * Fires an action event targeted at the on a MenuBarButton with the specified fx id.
     */
    private void clickOnMenuBarButton(String menuBarButtonFxId) {
        MenuBarButton mb = (guiRobot.lookup(MENU_BAR_ID + " " + menuBarButtonFxId).query());
        guiRobot.interact(() -> Event.fireEvent(mb, new ActionEvent()));
    }

    /**
     * Fires an action event targeted at the MenuItem within the MenuBarButton as specified by their ids.
     */
    private void clickOnMenuItem(String menuBarButtonFxId, String menuItemId) throws Exception {
        MenuBarButton mb = (guiRobot.lookup(MENU_BAR_ID + " " + menuBarButtonFxId).query());
        MenuItem m = (mb.menu.getItems().stream().filter(x -> x.getId().equals(menuItemId)).findFirst().get());
        if (m.getParentPopup().isShowing()) {
            guiRobot.interact(() -> Event.fireEvent(m, new ActionEvent()));
        } else {
            throw new Exception();
        }
    }
}
