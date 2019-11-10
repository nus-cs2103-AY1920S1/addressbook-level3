package seedu.moolah.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guitests.guihandles.StatusBarFooterHandle;

/**
 * Contains tests for {@code StatusBarFooter}.
 *
 * Source from (with some modifications):
 * https://github.com/se-edu/addressbook-level4/blob/master/src/test/java/seedu/address/ui/StatusBarFooterTest.java
 */
public class StatusBarFooterTest extends GuiUnitTest {

    private static final Path STUB_SAVE_LOCATION = Paths.get("Stub");
    private static final Path RELATIVE_PATH = Paths.get(".");

    private StatusBarFooterHandle statusBarFooterHandle;

    @BeforeEach
    public void setUp() {
        StatusBarFooter statusBarFooter = new StatusBarFooter(STUB_SAVE_LOCATION);
        uiPartExtension.setUiPart(statusBarFooter);

        statusBarFooterHandle = new StatusBarFooterHandle(statusBarFooter.getRoot());
    }

    @Test
    public void display() {
        // initial state
        assertStatusBarContent(RELATIVE_PATH.resolve(STUB_SAVE_LOCATION).toString());
    }

    /**
     * Asserts that the save location matches that of {@code expectedSaveLocation}, and the
     * sync status matches that of {@code expectedSyncStatus}.
     */
    private void assertStatusBarContent(String expectedSaveLocation) {
        assertEquals(expectedSaveLocation, statusBarFooterHandle.getSaveLocation());
        guiRobot.pauseForHuman();
    }

}
