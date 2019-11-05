//@@author tanlk99
package tagline.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.testfx.util.NodeQueryUtils.hasText;
import static tagline.testutil.group.TypicalGroups.GUARDIANS;
import static tagline.testutil.group.TypicalGroups.WAKANDAN_ROYAL;
import static tagline.ui.GuiTestUtil.getChildNode;
import static tagline.ui.GuiTestUtil.getChildNodes;
import static tagline.ui.GuiTestUtil.hasChildNode;
import static tagline.ui.group.GroupListCard.EMPTY_GROUP_STRING;

import java.util.Collection;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;

import javafx.scene.control.Label;
import javafx.stage.Stage;
import tagline.model.group.Group;
import tagline.ui.group.GroupListCard;

@ExtendWith(ApplicationExtension.class)
public class GroupListCardTest {
    private GuiTestController controller = GuiTestController.getInstance();
    private GroupListCard groupListCard;

    @Start
    private void setUp(Stage stage) {
        controller.initStageStyle(stage);
        stage.setWidth(500); //for human-viewable results
    }

    private void setGroupDisplayed(Group group) throws TimeoutException {
        groupListCard = new GroupListCard(group);
        controller.initSceneRoot(groupListCard.getRoot());
    }

    @Stop
    private void tearDown() throws TimeoutException {
        controller.doTearDown();
    }

    @AfterEach
    private void pause(FxRobot robot) {
        controller.pause(robot);
    }

    @Test
    public void checkFieldsDisplayedCorrectly_groupNotEmpty_successful(FxRobot robot) throws TimeoutException {
        setGroupDisplayed(WAKANDAN_ROYAL);

        FxAssert.verifyThat(getChildNode(robot, "#name"),
                hasText(WAKANDAN_ROYAL.getGroupName().value));
        FxAssert.verifyThat(getChildNode(robot, "#description"),
                hasText(WAKANDAN_ROYAL.getGroupDescription().value));
        FxAssert.verifyThat(getChildNode(robot, "#memberIdsLabel"), hasText("Members:"));

        assertEquals(WAKANDAN_ROYAL.getMemberIds().size(), getChildNodes(robot, "#memberIds > .label").size());

        Collection<String> memberIdStrings = getChildNodes(robot, "#memberIds > .label").stream()
                .map(label -> ((Label) label).getText()).sorted().collect(Collectors.toList());

        Collection<String> expectedMemberIdStrings = WAKANDAN_ROYAL.getMemberIds().stream()
                .map(id -> "#" + id.value).sorted().collect(Collectors.toList());

        assertEquals(expectedMemberIdStrings, memberIdStrings);
    }

    @Test
    public void checkFieldsDisplayedCorrectly_groupEmpty_successful(FxRobot robot) throws TimeoutException {
        setGroupDisplayed(GUARDIANS);

        FxAssert.verifyThat(getChildNode(robot, "#name"),
                hasText(GUARDIANS.getGroupName().value));
        FxAssert.verifyThat(getChildNode(robot, "#emptyGroupLabel"),
                hasText(EMPTY_GROUP_STRING));

        assertFalse(hasChildNode(robot, "#description"));
        assertEquals(0, getChildNodes(robot, "#memberIds > .label").size());
    }
}
