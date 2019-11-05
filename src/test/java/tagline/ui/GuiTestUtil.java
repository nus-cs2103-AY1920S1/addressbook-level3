//@@author tanlk99
package tagline.ui;

import java.util.Collection;

import org.testfx.api.FxRobot;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * A utility class containing useful methods common to several test classes.
 */
public class GuiTestUtil {
    public static final String COMMAND_TEXT_FIELD_CLASS = ".commandTextField";
    public static final String COMMAND_SEND_BUTTON_CLASS = ".commandSendButton";

    //============================= Main Window Tests =============================

    /**
     * Types a command in the command text field.
     */
    public static void typeCommand(FxRobot robot, String command) {
        TextField textField = robot.lookup(COMMAND_TEXT_FIELD_CLASS).queryAs(TextField.class);
        robot.clickOn(textField);
        robot.interact(() -> textField.setText(command));
    }

    /**
     * Enters a command into the command text field and sends it with the Enter key.
     */
    public static void enterCommand(FxRobot robot, String command) {
        typeCommand(robot, command);
        TextField textField = robot.lookup(COMMAND_TEXT_FIELD_CLASS).queryAs(TextField.class);
        robot.interact(() -> textField.fireEvent(new ActionEvent()));
    }

    /**
     * Enters a command into the command text field and sends it by pressing the button.
     */
    public static void buttonCommand(FxRobot robot, String command) {
        typeCommand(robot, command);
        Button button = robot.lookup(COMMAND_SEND_BUTTON_CLASS).queryButton();
        robot.interact(button::fire);
    }

    //======================================== Misc ==========================================

    public static boolean hasChildNode(FxRobot robot, String id) {
        return robot.lookup(id).tryQuery().isPresent();
    }

    public static Node getChildNode(FxRobot robot, String id) {
        return robot.lookup(id).query();
    }

    public static Collection<Node> getChildNodes(FxRobot robot, String id) {
        return robot.lookup(id).queryAll();
    }
}
