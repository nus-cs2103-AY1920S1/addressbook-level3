package tagline.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.testfx.util.NodeQueryUtils.hasText;
import static tagline.testutil.group.TypicalGroups.GUARDIANS;
import static tagline.testutil.group.TypicalGroups.WAKANDAN_ROYAL;
import static tagline.ui.group.GroupListCard.EMPTY_GROUP_STRING;

import java.util.Collection;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;

import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tagline.model.group.Group;
import tagline.ui.group.GroupListCard;

@ExtendWith(ApplicationExtension.class)
public class GroupListCardTest {
    private GroupListCard groupListCard;

    /**
     * Sets up the stage style. Can only be done once per testing session.
     */
    private void initStage(Stage stage) {
        if (stage.getStyle() != StageStyle.DECORATED) {
            stage.initStyle(StageStyle.DECORATED);
        }
    }

    @Start
    void setUp(Stage stage) {
        initStage(stage);
        stage.setWidth(500); //for human-viewable results
    }

    private void setGroupDisplayed(Group group) throws TimeoutException {
        groupListCard = new GroupListCard(group);
        FxToolkit.setupSceneRoot(() -> groupListCard.getRoot());
        FxToolkit.showStage();
    }

    @Stop
    void tearDown() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    @AfterEach
    void pause(FxRobot robot) {
        String headlessPropertyValue = System.getProperty("testfx.headless");
        if (headlessPropertyValue != null && headlessPropertyValue.equals("true")) {
            return;
        }

        robot.sleep(500);
    }

    private boolean hasChildNode(FxRobot robot, String id) {
        return robot.lookup(id).tryQuery().isPresent();
    }

    private Node getChildNode(FxRobot robot, String id) {
        return robot.lookup(id).query();
    }

    private Collection<Node> getChildNodes(FxRobot robot, String id) {
        return robot.lookup(id).queryAll();
    }

    @Test
    void checkFieldsDisplayedCorrectly_groupNotEmpty_successful(FxRobot robot) throws TimeoutException {
        setGroupDisplayed(WAKANDAN_ROYAL);

        FxAssert.verifyThat(getChildNode(robot, "#name"),
                hasText(WAKANDAN_ROYAL.getGroupName().value));
        FxAssert.verifyThat(getChildNode(robot, "#description"),
                hasText(WAKANDAN_ROYAL.getGroupDescription().value));
        FxAssert.verifyThat(getChildNode(robot, "#memberIdsLabel"), hasText("Members:"));

        assertEquals(4, getChildNodes(robot, "#memberIds > .label").size());
    }

    @Test
    void checkFieldsDisplayedCorrectly_groupEmpty_successful(FxRobot robot) throws TimeoutException {
        setGroupDisplayed(GUARDIANS);

        FxAssert.verifyThat(getChildNode(robot, "#name"),
                hasText(GUARDIANS.getGroupName().value));
        FxAssert.verifyThat(getChildNode(robot, "#emptyGroupLabel"),
                hasText(EMPTY_GROUP_STRING));

        assertFalse(hasChildNode(robot, "#description"));
        assertEquals(0, getChildNodes(robot, "#memberIds > .label").size());
    }
}
