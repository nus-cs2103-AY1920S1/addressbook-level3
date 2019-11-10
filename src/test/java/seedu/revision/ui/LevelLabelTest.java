package seedu.revision.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guitests.guihandles.LevelLabelHandle;

class LevelLabelTest extends GuiUnitTest {

    private static final int VALID_LEVEL_1 = 1;
    private static final int VALID_LEVEL_2 = 2;
    private static final int VALID_LEVEL_3 = 3;
    private static final int INVALID_LEVEL_0 = 0;
    private static final int INVALID_LEVEL_4 = 4;

    private LevelLabel levelLabel;
    private LevelLabelHandle levelLabelHandle;

    @BeforeAll
    public static void runHeadless() {
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
    }

    @BeforeEach
    public void setUp() {
        levelLabel = new LevelLabel(VALID_LEVEL_1); //set to default level 1
        levelLabelHandle = new LevelLabelHandle(getChildNode(levelLabel.getRoot(),
                LevelLabelHandle.LEVEL_LABEL_ID));
        levelLabel.getLevelLabel().setStyle("-fx-min-width: 300");
        uiPartExtension.setUiPart(levelLabel);
    }

    /**
     * Tests that the {@code LevelLabel} updates correctly when valid levels are passed to it.
     */
    @Test
    public void levelLabel_updateLevel_shouldShowNextLevel() {
        guiRobot.pause();
        assertEquals("Level 1", levelLabelHandle.getText()); //default level 1
        levelLabel.updateLevelLabel(VALID_LEVEL_2);
        guiRobot.pause();
        assertEquals("Level 2", levelLabelHandle.getText()); //update to level 2
        levelLabel.updateLevelLabel(VALID_LEVEL_3);
        guiRobot.pause();
        assertEquals("Level 3", levelLabelHandle.getText()); //update to level 3
    }


    /**
     * Tests that the {@code LevelLabel} throws an assertion error when invalid levels are passed to it.
     */
    @Test
    public void levelLabel_updateInvalidLevel_shouldThrowAssertionError() {
        //Invalid Boundary values 0, 4
        guiRobot.pauseForHuman();
        assertThrows(AssertionError.class, () -> levelLabel.updateLevelLabel(INVALID_LEVEL_0));
        guiRobot.pauseForHuman();
        assertThrows(AssertionError.class, () -> levelLabel.updateLevelLabel(INVALID_LEVEL_4));
    }
}
