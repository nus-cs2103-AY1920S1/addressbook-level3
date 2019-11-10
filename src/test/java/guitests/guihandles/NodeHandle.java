package guitests.guihandles;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import guitests.GuiRobot;
import guitests.guihandles.exceptions.NodeNotFoundException;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Provides access to a node in a JavaFx application for GUI testing purposes.
 *
 * Sourced from with minor modifications:
 * https://github.com/se-edu/addressbook-level4/blob/master/src/test/java/guitests/guihandles/NodeHandle.java
 */
public abstract class NodeHandle<T extends Node> {
    protected final GuiRobot guiRobot = new GuiRobot();

    private final T rootNode;

    protected NodeHandle(T rootNode) {
        this.rootNode = requireNonNull(rootNode);
    }

    protected T getRootNode() {
        return rootNode;
    }

    /**
     * Retrieves the {@code query} node owned by the {@code rootNode}.
     *
     * @param query name of the CSS selector for the node to retrieve.
     * @throws NodeNotFoundException if no such node exists.
     */
    protected <Q extends Node> Q getChildNode(String query) {
        Optional<Q> node = guiRobot.from(rootNode).lookup(query).tryQuery();
        return node.orElseThrow(NodeNotFoundException::new);
    }

    /**
     * Clicks on the root node (i.e. itself).
     */
    public void click() {
        guiRobot.clickOn(rootNode);
    }

    /**
     * Fires a press and release key event with the specified {@code KeyCode} at the root node.
     * @param keyCode the key code to be pressed.
     */
    public void pushKey(KeyCode keyCode) {
        guiRobot.interact(() -> {
            KeyEvent.fireEvent(getRootNode(), new KeyEvent(KeyEvent.KEY_PRESSED, null, null, keyCode,
                    false, false, false, false));
            KeyEvent.fireEvent(getRootNode(), new KeyEvent(KeyEvent.KEY_RELEASED, null, null, keyCode,
                    false, false, false, false));
        });
    }
}
