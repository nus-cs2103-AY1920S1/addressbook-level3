package seedu.moolah.ui.panel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.layout.Region;
import seedu.moolah.ui.panel.exceptions.UnmappedPanelException;

/**
 * Contains tests for {@code SinglePanelView}.
 */
class SinglePanelViewTest {

    private PanelManager panelManager;
    private PanelName page1;
    private PanelName page2;
    private PanelName page3;
    private Panel panel1;
    private Panel panel2;
    private Panel panel3;

    @BeforeEach
    void setUpFreshPanelManager() {
        panelManager = new SinglePanelView();
        page1 = new PanelName("page1");
        page2 = new PanelName("page2");
        page3 = new PanelName("page3");
        panel1 = new PanelStub();
        panel2 = new PanelStub();
        panel3 = new PanelStub();
    }


    // ---------- Testing SinglePanelView as a PanelManager ------------------------
    @Test
    void setPanel_validArguments_returnTrue() {
        assertTrue(panelManager.setPanel(page1, panel1));
        assertTrue(panelManager.setPanel(page2, panel2));
        assertTrue(panelManager.setPanel(page3, panel3));
    }

    @Test
    void setPanel_nullArgument_throwException() {
        assertThrows(NullPointerException.class, () -> panelManager.setPanel(null, null));
        assertThrows(NullPointerException.class, () -> panelManager.setPanel(page1, null));
        assertThrows(NullPointerException.class, () -> panelManager.setPanel(null, panel1));
    }

    @Test
    void hasPanel_beforeSettingPanel_returnFalse() {
        assertFalse(panelManager.hasPanel(page1));
        assertFalse(panelManager.hasPanel(page2));
        assertFalse(panelManager.hasPanel(page3));
    }

    @Test
    void hasPanel_afterSettingPanel_returnTrue() {
        if (!panelManager.setPanel(page1, panel1)
                || !panelManager.setPanel(page2, panel2)
                || !panelManager.setPanel(page3, panel3)) {
            fail();
        }
        assertTrue(panelManager.hasPanel(page1));
        assertTrue(panelManager.hasPanel(page2));
        assertTrue(panelManager.hasPanel(page3));
    }

    @Test
    void getPanel() {
        try {
            panelManager.setPanel(page1, panel1);
            panelManager.setPanel(page2, panel2);
            assertEquals(panel1, panelManager.getPanel(page1));
            assertEquals(panel2, panelManager.getPanel(page2));
            // overwrite
            panelManager.setPanel(page2, panel3);
            assertEquals(panel3, panelManager.getPanel(page2));
            assertNotEquals(panel2, panelManager.getPanel(page2));
        } catch (UnmappedPanelException e) {
            fail();
        }
    }

    @Test
    void removePanel_panelManagerHasMappedPanelName_success() {
        try {
            panelManager.setPanel(page1, panel1);
            panelManager.setPanel(page2, panel2);
            // panelname mapped to panel success
            assertEquals(panel1, panelManager.removePanel(page1));
            assertEquals(panel2, panelManager.removePanel(page2));
        } catch (UnmappedPanelException e) {
            fail();
        }
    }

    @Test
    void removePanel_panelManagerNeverHadPanel_throwsException() {
        assertThrows(UnmappedPanelException.class, () -> System.out.println(panelManager.removePanel(page1)));
        assertThrows(UnmappedPanelException.class, () -> panelManager.removePanel(page2));
    }

    @Test
    void removePanel_panelWasRemovedAlready_throwsException() {
        try {
            panelManager.setPanel(page1, panel1);
            panelManager.setPanel(page2, panel2);
            panelManager.removePanel(page1);
            panelManager.removePanel(page2);
            assertThrows(UnmappedPanelException.class, () -> panelManager.removePanel(page1));
            assertThrows(UnmappedPanelException.class, () -> panelManager.removePanel(page2));
        } catch (UnmappedPanelException e) {
            fail("Should not throw exception until when trying to remove a Panel which exists");
        }
    }

    // ---------- Testing non-PanelManager methods ------------

    @Test
    void viewPanel_hasPanelAndMultiplePanelsExist_onlyCalledPanelVisibleAtATime() {
        try {
            panelManager.setPanel(page1, panel1);
            panelManager.setPanel(page2, panel2);
            panelManager.setPanel(page3, panel3);

            SinglePanelView singlePanelView = (SinglePanelView) panelManager;
            PanelStub panelStub1 = (PanelStub) panel1;
            PanelStub panelStub2 = (PanelStub) panel2;
            PanelStub panelStub3 = (PanelStub) panel3;

            singlePanelView.viewPanel(page1);
            assertTrue(panelStub1.isVisible() && !panelStub2.isVisible() && !panelStub3.isVisible());
            singlePanelView.viewPanel(page2);
            assertTrue(!panelStub1.isVisible() && panelStub2.isVisible() && !panelStub3.isVisible());
            singlePanelView.viewPanel(page3);
            assertTrue(!panelStub1.isVisible() && !panelStub2.isVisible() && panelStub3.isVisible());
        } catch (UnmappedPanelException e) {
            fail();
        }
    }

    @Test
    void viewPanel_noPanels_throwsException() {
        assertThrows(UnmappedPanelException.class, () -> ((SinglePanelView) panelManager).viewPanel(page1));
    }

    @Test
    void viewPanel_multiplePanelNotAssignedToName_throwsException() {
        panelManager.setPanel(page1, panel1);
        panelManager.setPanel(page2, panel2);
        assertThrows(UnmappedPanelException.class, () -> ((SinglePanelView) panelManager).viewPanel(page3));
    }


    /**
     * Panel Stub to allow testing of SinglePanelView without UI.
     */
    private static class PanelStub extends Panel {
        // uses UiPartTest's valid fxml.
        private static final String VALID_FILE_PATH = "UiPartTest/validFile.fxml";
        private Region r;
        private boolean visible;

        public PanelStub() {
            super(VALID_FILE_PATH);
            r = new Region();
        }

        public boolean isVisible() {
            return visible;
        }

        @Override
        public Region getRoot() {
            return r;
        }

        @Override
        public void view() {
            visible = true;
        }

        @Override
        public void hide() {
            visible = false;
        }
    }
}
