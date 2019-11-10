package seedu.revision.ui.bar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guitests.guihandles.ProgressIndicatorBarHandle;
import javafx.beans.property.ReadOnlyDoubleWrapper;

import seedu.revision.ui.GuiUnitTest;

class ProgressIndicatorBarTest extends GuiUnitTest {

    private static final double TOTAL_PROGRESS = 2.0;
    private static final double VALID_PROGRESS_0 = 0;
    private static final double VALID_PROGRESS_HALF = TOTAL_PROGRESS / 2;
    private static final double VALID_PROGRESS_FULL = TOTAL_PROGRESS;
    private static final double INVALID_PROGRESS_NEGATIVE = -1;
    private static final double INVALID_PROGRESS_LARGER_THAN_TOTAL = TOTAL_PROGRESS + 1;

    private ProgressIndicatorBarHandle progressIndicatorBarHandle;
    private ProgressIndicatorBar progressIndicatorBar;
    private ReadOnlyDoubleWrapper currentProgressIndex = new ReadOnlyDoubleWrapper(
            this, "currentProgressIndex", 0);

    @BeforeAll
    public static void runHeadless() {
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
    }

    @BeforeEach
    public void setUp() {
        progressIndicatorBar = new ProgressIndicatorBar(currentProgressIndex, TOTAL_PROGRESS, "%.0f/2");
        progressIndicatorBarHandle = new ProgressIndicatorBarHandle(getChildNode(progressIndicatorBar.getRoot(),
                ProgressIndicatorBarHandle.PROGRESS_BAR_ID));
        progressIndicatorBar.getBar().setStyle("-fx-min-width: 300");
        uiPartExtension.setUiPart(progressIndicatorBar);
    }
    /**
     * Default progress bar should show no progress.
     */
    @Test
    public void progressBar_noChangeInProgress_shouldShowZeroProgress() {
        assertEquals(VALID_PROGRESS_0, progressIndicatorBarHandle.getProgress());
    }

    /**
     * Progress updated, should show change in progress.
     */
    @Test
    public void progressBar_progressChanged_inputShouldChange() {
        guiRobot.pauseForHuman();
        currentProgressIndex.set(VALID_PROGRESS_HALF);
        guiRobot.pauseForHuman();
        assertEquals(0.5, progressIndicatorBarHandle.getProgress()); //half progress
        guiRobot.pauseForHuman();
        currentProgressIndex.set(VALID_PROGRESS_FULL);
        guiRobot.pauseForHuman();
        assertEquals(1, progressIndicatorBarHandle.getProgress()); //full progress
    }

    /**
     * Progress updated to invalid input, should throw assertion error.
     */
    @Test
    public void progressBar_progressChangedToInvalidProgress_shouldNotUpdate() {
        //Boundary values -1, current > total progress
        guiRobot.pauseForHuman();
        assertThrows(AssertionError.class, () -> currentProgressIndex.set(INVALID_PROGRESS_NEGATIVE));
        //invalid negative progress
        guiRobot.pauseForHuman();
        assertThrows(AssertionError.class, () -> currentProgressIndex.set(INVALID_PROGRESS_LARGER_THAN_TOTAL));
    }
}
