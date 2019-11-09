package seedu.address.ui.uicomponent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import seedu.address.model.display.sidepanel.GroupDisplay;
import seedu.address.model.group.GroupList;
import seedu.address.testutil.grouputil.TypicalGroups;
import seedu.address.ui.GroupListPanel;

@ExtendWith(ApplicationExtension.class)
public class GroupListTest extends ApplicationTest {
    private static final GroupList TEST_GROUP_LIST = TypicalGroups.generateTypicalGroupList();
    private static final GroupDisplay TEST_DISPLAY_ONE = new GroupDisplay(TEST_GROUP_LIST.getGroups().get(0));
    private static final GroupDisplay TEST_DISPLAY_TWO = new GroupDisplay(TEST_GROUP_LIST.getGroups().get(1));
    private static final ObservableList<GroupDisplay> TEST_LIST = FXCollections.observableList(
            List.of(TEST_DISPLAY_ONE, TEST_DISPLAY_TWO));
    private static final String GROUP_LIST_ID = "#groupListView";

    @AfterEach
    public void pause(FxRobot robot) {
        robot.sleep(500);
    }

    @Stop
    void tearDown() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    /**
     * Initialise person card test with a stage.
     * @param stage
     */
    @Start
    public void start(Stage stage) {
        Parent sceneRoot = new GroupListPanel(TEST_LIST).getRoot();
        Scene scene = new Scene(sceneRoot);
        scene.getStylesheets().add("/view/DarkTheme.css");
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void numberOfGroupsDisplayedTest() {
        ArrayList<Node> cells = new ArrayList<>(lookup(GROUP_LIST_ID + " #groupCardPane").queryAll());
        assertEquals(TEST_LIST.size(), cells.size());
    }

    /**
     * Check if groups are displayed correctly.
     */
    @Test
    public void groupsDisplayedCorrectly() {
        ArrayList<Node> cells = new ArrayList<>(lookup(GROUP_LIST_ID + " .listView #groupCardPane").queryAll());
        for (int i = 0; i < cells.size(); i++) {
            //id displayed correctly.
            verifyThat(from(cells).lookup(" #groupId .label"), hasText(i + 1 + ""));
            //name displayed correctly.
            verifyThat(from(cells).lookup(" #groupName"),
                    hasText(TEST_LIST.get(i).getGroupName().toString()));
        }
    }
}
