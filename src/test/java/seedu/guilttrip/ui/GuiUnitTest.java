/*package seedu.guilttrip.ui;

//import guitests.guihandles.exceptions.NodeNotFoundException;

import org.junit.jupiter.api.extension.RegisterExtension;
//import seedu.guilttrip.ui.testutil.UiPartExtension;

*//**
 * A GUI unit test class for GuiltTrip.
 * <p>
 * Retrieves the {@code query} node owned by the {@code rootNode}.
 *
 * @param query name of the CSS selector of the node to retrieve.
 * @throws NodeNotFoundException if no such node exists.
 * <p>
 * Retrieves the {@code query} node owned by the {@code rootNode}.
 * @param query name of the CSS selector of the node to retrieve.
 * @throws NodeNotFoundException if no such node exists.
 *//*
public abstract class GuiUnitTest {
    // TODO: Remove this workaround after using JavaFX version 13 or above
    // This is a workaround to solve headless test failure on Windows OS
    // Refer to https://github.com/javafxports/openjdk-jfx/issues/66 for more details.
    static {
        if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
            System.loadLibrary("WindowsCodecs");
        }
    }

    @RegisterExtension
    public final UiPartExtension uiPartExtension = new UiPartExtension();

    *//**
 * Retrieves the {@code query} node owned by the {@code rootNode}.
 *
 * @param query name of the CSS selector of the node to retrieve.
 * @throws NodeNotFoundException if no such node exists.
 *//*
 *//*protected <T extends Node> T getChildNode(Node rootNode, String query) {
        Optional<T> node = guiRobot.from(rootNode).lookup(query).tryQuery();
        return node.orElseThrow(NodeNotFoundException::new);
    }*//*

}*/
