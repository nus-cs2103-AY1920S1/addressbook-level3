package seedu.exercise.ui.testutil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.IntStream;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionModel;
import javafx.scene.input.Clipboard;
import seedu.exercise.guihandlers.GuiRobot;
import seedu.exercise.guihandlers.ResourceListViewHandle;
import seedu.exercise.model.resource.Resource;
import seedu.exercise.ui.ResourceListPanel;

/**
 * Utility methods for assertions of GUI tests.
 */
public class GuiAssert {

    /**
     * Asserts that none of the items in {@code model} is selected.
     */
    public static <T> void assertSelectionModelNonSelected(SelectionModel<T> model, int size) {
        IntStream.range(0, size)
                 .forEach(index -> assertFalse(model.isSelected(index)));
    }

    /**
     * Asserts that the system's {@code clipboard} is the same as {@code expectedContent}
     */
    public static void assertSystemClipboardStringContentEquals(String expectedContent, Clipboard clipboard) {
        new GuiRobot().interact(() -> {
            assertEquals(expectedContent, clipboard.getString());
        });
    }

    /**
     * Asserts that the window with {@code windowTitle} is shown.
     */
    public static void assertWindowShown(String windowTitle) {
        assertTrue(new GuiRobot().isWindowShown(windowTitle));
    }

    /**
     * Asserts that the window with {@code windowTitle} is not shown.
     */
    public static void assertWindowNotShown(String windowTitle) {
        assertFalse(new GuiRobot().isWindowShown(windowTitle));
    }

    /**
     * Asserts that the resource {@code r} is added to the {@code actualListView} correctly.
     */
    public static <T extends Resource> void assertResourceAddedToListView(ResourceListViewHandle<T> listView,
                                                                           T r, ResourceListPanel actualListView) {
        GuiRobot guiRobot = new GuiRobot();
        guiRobot.interact(() -> listView.addResource(r));
        guiRobot.pauseForHuman();
        assertEquals(listView.getListView(), actualListView.getResourceListView());
    }

    /**
     * Asserts that {@code actualPanel}'s selection model is resetted after a selection.
     */
    public static <T extends Resource> void assertListSelectionReset(ResourceListViewHandle<T> listViewHandle,
                                                                      ResourceListPanel actualPanel) {
        listViewHandle.select(0);
        actualPanel.resetListSelection();
        assertSelectionModelNonSelected(listViewHandle.getSelectionModel(), listViewHandle.getListSize());
    }

    /**
     * Asserts that item at {@code index} is selected from {@codee listView}.
     */
    public static <T extends Resource> void assertItemSelected(ListView<T> listView, int index) {
        assertTrue(listView.getSelectionModel().isSelected(index));
    }

    /**
     * Asserts that item at {@code index} is focused
     */
    public static <T extends Resource> void assertItemFocused(ListView<T> listView, int index) {
        assertTrue(listView.getFocusModel().isFocused(index));
    }

    /**
     * Asserts that {@code node} is of the type {@code clazz}.
     */
    public static void assertNodeType(Class clazz, Node node) {
        assertTrue(clazz.isInstance(node));
    }
}
