package seedu.exercise.ui;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.ui.testutil.GuiAssert.assertSystemClipboardStringContentEquals;
import static seedu.exercise.ui.testutil.GuiAssert.assertWindowNotShown;
import static seedu.exercise.ui.testutil.GuiAssert.assertWindowShown;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.input.Clipboard;
import seedu.exercise.guihandlers.ButtonHandle;
import seedu.exercise.guihandlers.LabelHandle;
import seedu.exercise.testutil.CommonTestData;
import seedu.exercise.ui.testutil.GuiUnitTest;

public class HelpWindowTest extends GuiUnitTest {

    private HelpWindow helpWindow;
    private ButtonHandle copyButtonHandle;
    private LabelHandle helpMessageHandle;

    @BeforeEach
    private void setUp() {
        guiRobot.interact(() -> helpWindow = new HelpWindow());
        copyButtonHandle = new ButtonHandle(
                getChildNode(helpWindow.getRoot(), CommonTestData.HELP_WINDOW_BUTTON_ID));
        helpMessageHandle = new LabelHandle(
                getChildNode(helpWindow.getRoot(), CommonTestData.HELP_WINDOW_LABEL_ID));
    }

    @Test
    public void show_helpWindowShows() {
        guiRobot.interact(() -> helpWindow.show());
        assertWindowShown(CommonTestData.HELP_WINDOW_STAGE_TITLE);
        assertTrue(helpWindow.isShowing());
    }

    @Test
    public void hide_helpWindowNotShown() {
        guiRobot.interact(() -> helpWindow.hide());
        assertWindowNotShown(CommonTestData.HELP_WINDOW_STAGE_TITLE);
    }

    @Test
    public void clickCopyButton_userGuideUrl_copiedToClipboard() {
        copyButtonHandle.click();
        assertSystemClipboardStringContentEquals(HelpWindow.USERGUIDE_URL, Clipboard.getSystemClipboard());
    }
}
