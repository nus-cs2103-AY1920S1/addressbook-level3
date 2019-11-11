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

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import seedu.address.model.display.sidepanel.GroupDisplay;
import seedu.address.model.display.sidepanel.PersonDisplay;
import seedu.address.model.group.GroupDescription;
import seedu.address.model.group.GroupName;
import seedu.address.model.mapping.Role;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;
import seedu.address.ui.schedule.GroupInformationDisplay;
import seedu.address.ui.util.ColorGenerator;

@ExtendWith(ApplicationExtension.class)
public class GroupInformationDisplayTest extends ApplicationTest {
    private static final Person ALICE = TypicalPersons.ALICE;
    private static final Person BENSON = TypicalPersons.BENSON;
    private static final Person CARL = TypicalPersons.CARL;
    private static final List<PersonDisplay> SAMPLE_PERSON_DISPLAYS = List.of(new PersonDisplay(ALICE,
                    new Role("Leader")), new PersonDisplay(BENSON, new Role("Member")),
            new PersonDisplay(CARL, new Role("Freeloader")));
    private static final GroupName SAMPLE_GROUP_NAME = new GroupName("SAMPLE GROUP");
    private static final GroupDescription SAMPLE_GROUP_DESCRIPTION = new GroupDescription("SAMPLE GROUP DESCRIPTION");
    private static final GroupDisplay SAMPLE_GROUP_DISPLAY = new GroupDisplay(SAMPLE_GROUP_NAME,
            SAMPLE_GROUP_DESCRIPTION);
    private static final String GROUP_NAME_ID = "#groupDetails #groupDetailContainer #groupName";
    private static final String GROUP_DESC_ID = "#groupDetails #groupDetailContainer #groupDescription";
    private static final String GROUP_MEMBERS_ID = "#groupMembers";

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
        //Scenario is only Alice is focused.
        Parent sceneRoot = new GroupInformationDisplay(SAMPLE_PERSON_DISPLAYS, List.of(ALICE.getName()),
                SAMPLE_GROUP_DISPLAY, ColorGenerator::generateColor).getRoot();
        Scene scene = new Scene(sceneRoot);
        scene.getStylesheets().add("/view/DarkTheme.css");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Checks if group name and group description is displayed.
     */
    @Test
    public void groupNameAndDescriptionDisplayedCorrectly() {
        verifyThat(GROUP_NAME_ID, hasText(SAMPLE_GROUP_NAME.toString()));
        verifyThat(GROUP_DESC_ID, hasText(SAMPLE_GROUP_DESCRIPTION.toString()));
    }

    /**
     * Checks if the group members are shown correctly.
     */
    @Test
    public void groupMembersDisplayedCorrectly() {
        String groupMemberCardId = GROUP_MEMBERS_ID + " #listMembers #memberCard";
        ArrayList<Node> groupMemberCards = new ArrayList<>(lookup(groupMemberCardId).queryAll());
        for (int i = 0; i < groupMemberCards.size(); i++) {
            Label name = (Label) from(groupMemberCards.get(i)).lookup("#memberName").query();
            Label email = (Label) from(groupMemberCards.get(i)).lookup("#memberEmail").query();
            Label role = (Label) from(groupMemberCards.get(i)).lookup("#memberRole").query();
            verifyThat(name, hasText(SAMPLE_PERSON_DISPLAYS.get(i).getName().fullName));
            verifyThat(email, hasText(SAMPLE_PERSON_DISPLAYS.get(i).getEmail().value));
            verifyThat(role, hasText(SAMPLE_PERSON_DISPLAYS.get(i).getRole().toString()));
        }
    }

    /**
     * Checks to see if cards are filtered correctly.
     */
    @Test
    public void groupMemberCardsAreFilteredCorrectly() {
        String groupMemberCardId = GROUP_MEMBERS_ID + " #listMembers #memberCard";
        ArrayList<Node> groupMemberCards = new ArrayList<>(lookup(groupMemberCardId).queryAll());
        for (Node card : groupMemberCards) {
            Label name = (Label) from(card).lookup("#memberName").query();
            if (name.getText().equals(ALICE.getName().fullName)) {
                assertEquals(1, ((GridPane) card).getOpacity());
            } else {
                assertEquals(0.5, ((GridPane) card).getOpacity());
            }
        }
    }

}
