package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;

/**
 * A handle to the {@code HelpWindow} of the application.
 *
 * Adapted from:
 * https://github.com/se-edu/addressbook-level4/blob/master/src/test/java/guitests/guihandles/HelpWindowHandle.java
 */
public class HelpWindowHandle extends StageHandle {

    public static final String HELP_WINDOW_TITLE = "Help";

    public HelpWindowHandle(Stage helpWindowStage) {
        super(helpWindowStage);
    }

    /**
     * Returns true if a help window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(HELP_WINDOW_TITLE);
    }
}
