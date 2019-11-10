package guitests.guihandles;

import java.util.Collection;

import org.fxmisc.richtext.model.StyleSpans;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import seedu.moolah.ui.textfield.CommandTextField;

/**
 * A handle to the {@code CommandBox} in the GUI.
 *
 * Adapted with major modifications from:
 * https://github.com/se-edu/addressbook-level4/blob/master/src/test/java/guitests/guihandles/CommandBoxHandle.java
 */
public class CommandBoxHandle extends NodeHandle<CommandTextField> {

    public static final String COMMAND_INPUT_FIELD_ID = "#SyntaxHighlightingTextField";

    public CommandBoxHandle(CommandTextField commandBoxNode) {
        super(commandBoxNode);
    }

    /**
     * Returns the text in the command box.
     */
    public String getInput() {
        return getRootNode().getText();
    }

    /**
     * Returns the number of suggestions in suggestions menu (includes 1 separator menu item if there are both
     * missing and optional prefixes.
     */
    public int suggestionCount() {
        return getRootNode().getAutofillMenu().getItems().size();
    }

    /**
     * Returns if the menu is visible.
     */
    public boolean isSuggestionMenuShowing() {
        return getRootNode().getAutofillMenu().isShowing();
    }

    /**
     * Enters the given command in the Command Box and presses and releases enter.
     */
    public void run(String command) {
        click();
        guiRobot.interact(() -> getRootNode().replaceText(command));
        guiRobot.pauseForHuman();
        pushKey(KeyCode.ENTER);
    }

    /**
     * Enters the given command in the Command Box.
     */
    public void type(String command) {
        click();
        guiRobot.interact(() -> getRootNode().appendText(command));
    }

    /**
     * Press and release the {@code KeyCode} specified.
     */
    public void pushKey(KeyCode keyCode) {
        click();
        guiRobot.interact(() -> {
            KeyEvent.fireEvent(getRootNode(), new KeyEvent(KeyEvent.KEY_PRESSED, null,
                    null, keyCode, false, false, false, false));
            KeyEvent.fireEvent(getRootNode(), new KeyEvent(KeyEvent.KEY_RELEASED, null,
                    null, keyCode, false, false, false, false));
        });
    }

    /**
     * Fires an {@code ActionEvent} at the {@code MenuItem} specified by the index {@code KeyCode} provided.
     */
    public void fireActionEvent(int menuItemIndex) {
        click();
        guiRobot.interact(() -> {
            EventTarget target = getRootNode().getAutofillMenu().getItems().get(menuItemIndex);
            KeyEvent.fireEvent(target, new ActionEvent());
        });
    }

    /**
     * Returns the list of style classes present in the command box.
     */
    public ObservableList<String> getStyleClass() {
        return getRootNode().getStyleClass();
    }

    /**
     * Returns the style spans used to style the text in the textfield.
     */
    public StyleSpans<Collection<String>> getStyleSpans() {
        return getRootNode().getStyleSpans(0);
    }
}
