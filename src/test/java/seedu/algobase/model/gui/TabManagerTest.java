package seedu.algobase.model.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.DefaultModelStub;
import seedu.algobase.model.Id;
import seedu.algobase.model.ModelType;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.util.SampleDataUtil;

public class TabManagerTest {

    @Test
    public void openDisplayTab_tabDataAcceptedByTabManager_success() {
        NoTabsModelStub noTabsModelStub = new NoTabsModelStub();
        TabManager tabManager = noTabsModelStub.getGuiState().getTabManager();

        // Add first problem to the tabs
        Problem firstProblem = noTabsModelStub.getFilteredProblemList().get(0);
        TabData firstTabData = new TabData(ModelType.PROBLEM, firstProblem.getId());
        tabManager.openDetailsTab(firstTabData);

        // Check that tab data is added
        assertEquals(
            firstTabData,
            tabManager.getTabsDataList().get(0)
        );

        // Check that index is at zero
        assertEquals(
            tabManager.getDetailsTabPaneIndex().intValue(),
            0
        );

        // Add second problem to the tabs
        Problem secondProblem = noTabsModelStub.getFilteredProblemList().get(1);
        TabData secondTabData = new TabData(ModelType.PROBLEM, secondProblem.getId());
        tabManager.openDetailsTab(secondTabData);

        // Check that tab data is added
        assertEquals(
            secondTabData,
            tabManager.getTabsDataList().get(1)
        );

        // Check that index has changed to the newest tab
        assertEquals(
            tabManager.getDetailsTabPaneIndex().intValue(),
            1
        );
    }

    @Test
    public void switchDisplayTab_indexWithinRange_success() {
        FiveTabsModelStub fiveTabsModelStub = new FiveTabsModelStub();
        TabManager tabManager = fiveTabsModelStub.getGuiState().getTabManager();
        Index[] testValues = new Index[] { Index.fromZeroBased(0) };

        tabManager.getDisplayTabPaneIndex().addListener((observable, oldValue, newValue) -> {
            testValues[0] = Index.fromZeroBased((newValue.intValue()));
        });
        tabManager.switchDisplayTab(Index.fromZeroBased(1));

        assertEquals(Index.fromZeroBased(1), testValues[0], "Listener does not update value correctly");
    }

    @Test
    public void switchDetailsTab_indexOutsideRange_throwsIndexOutOfBoundsException() {
        FiveTabsModelStub fiveTabsModelStub = new FiveTabsModelStub();
        TabManager tabManager = fiveTabsModelStub.getGuiState().getTabManager();
        Index[] testValues = new Index[] { Index.fromZeroBased(0) };

        tabManager.getDisplayTabPaneIndex().addListener((observable, oldValue, newValue) -> {
            testValues[0] = Index.fromZeroBased((newValue.intValue()));
        });

        assertThrows(IndexOutOfBoundsException.class, () -> tabManager.switchDetailsTab(Index.fromZeroBased(5)));
    }

    @Test
    public void switchDetailsTab_indexWithinRange_success() {
        FiveTabsModelStub fiveTabsModelStub = new FiveTabsModelStub();
        TabManager tabManager = fiveTabsModelStub.getGuiState().getTabManager();
        Index[] testValues = new Index[] { Index.fromZeroBased(0) };

        tabManager.getDetailsTabPaneIndex().addListener((observable, oldValue, newValue) -> {
            testValues[0] = Index.fromZeroBased((newValue.intValue()));
        });
        tabManager.switchDetailsTab(Index.fromZeroBased(1));

        assertEquals(Index.fromZeroBased(1), testValues[0], "Listener does not update value correctly");
    }

    @Test
    public void closeDisplayTabByIndex_lastTabDataRemovedByTabManager_success() {
        FiveTabsModelStub fiveTabsModelStub = new FiveTabsModelStub();
        TabManager tabManager = fiveTabsModelStub.getGuiState().getTabManager();

        TabData firstTabDataToBeClosed = tabManager.getTabsDataList().get(4);
        TabData secondTabDataToBeClosed = tabManager.getTabsDataList().get(3);

        tabManager.closeDetailsTab(Index.fromZeroBased(4)); // Close last tab

        // Check that first tab data is removed
        assertEquals(
            tabManager.getTabsDataList().contains(firstTabDataToBeClosed),
            false
        );

        assertEquals(
            tabManager.getTabsDataList().contains(secondTabDataToBeClosed),
            true
        );

        assertEquals(
            tabManager.getTabsDataList().size(),
            4
        );

        assertEquals(
            tabManager.getDetailsTabPaneIndex().intValue(),
            3
        );

        tabManager.closeDetailsTab(Index.fromZeroBased(3)); // Close second last tab

        // Check that second tab data is removed
        assertEquals(
            tabManager.getTabsDataList().contains(firstTabDataToBeClosed),
            false
        );

        assertEquals(
            tabManager.getTabsDataList().contains(secondTabDataToBeClosed),
            false
        );

        assertEquals(
            tabManager.getTabsDataList().size(),
            3
        );

        assertEquals(
            tabManager.getDetailsTabPaneIndex().intValue(),
            2
        );
    }

    @Test
    public void closeDisplayTabByIndex_selectedIndexBeforeClosedIndex_success() {
        FiveTabsModelStub fiveTabsModelStub = new FiveTabsModelStub();
        TabManager tabManager = fiveTabsModelStub.getGuiState().getTabManager();
        tabManager.switchDetailsTab(Index.fromZeroBased(1));

        TabData firstTabDataToBeClosed = tabManager.getTabsDataList().get(4);
        TabData secondTabDataToBeClosed = tabManager.getTabsDataList().get(3);

        tabManager.closeDetailsTab(Index.fromZeroBased(4)); // Close last tab

        // Check that first tab data is removed
        assertEquals(
            tabManager.getTabsDataList().contains(firstTabDataToBeClosed),
            false
        );

        assertEquals(
            tabManager.getTabsDataList().contains(secondTabDataToBeClosed),
            true
        );

        assertEquals(
            tabManager.getTabsDataList().size(),
            4
        );

        assertEquals(
            tabManager.getDetailsTabPaneIndex().intValue(),
            1
        );

        tabManager.closeDetailsTab(Index.fromZeroBased(3)); // Close second last tab

        // Check that second tab data is removed
        assertEquals(
            tabManager.getTabsDataList().contains(firstTabDataToBeClosed),
            false
        );

        assertEquals(
            tabManager.getTabsDataList().contains(secondTabDataToBeClosed),
            false
        );

        assertEquals(
            tabManager.getTabsDataList().size(),
            3
        );

        assertEquals(
            tabManager.getDetailsTabPaneIndex().intValue(),
            1
        );
    }

    @Test
    public void closeDisplayTabByIndex_closeTabForEmptyTabList_throwsIndexOutOfBoundsException() {
        NoTabsModelStub noTabsModelStub = new NoTabsModelStub();
        TabManager tabManager = noTabsModelStub.getGuiState().getTabManager();
        assertThrows(
            IndexOutOfBoundsException.class, () -> tabManager.closeDetailsTab(Index.fromZeroBased(0))
        );
    }

    @Test
    public void closeDisplayTabByIndex_closeTabIndexOutOfRange_throwsIndexOutOfBoundsException() {
        FiveTabsModelStub fiveTabsModelStub = new FiveTabsModelStub();
        TabManager tabManager = fiveTabsModelStub.getGuiState().getTabManager();
        assertThrows(
            IndexOutOfBoundsException.class, () -> tabManager.closeDetailsTab(Index.fromZeroBased(5))
        );
    }

    @Test
    public void closeDisplayTabByTabData_lastTabDataRemovedByTabManager_success() {
        FiveTabsModelStub fiveTabsModelStub = new FiveTabsModelStub();
        TabManager tabManager = fiveTabsModelStub.getGuiState().getTabManager();

        TabData firstTabDataToBeClosed = tabManager.getTabsDataList().get(4);
        TabData secondTabDataToBeClosed = tabManager.getTabsDataList().get(3);

        tabManager.closeDetailsTab(firstTabDataToBeClosed); // Close last tab

        // Check that first tab data is removed
        assertEquals(
            tabManager.getTabsDataList().contains(firstTabDataToBeClosed),
            false
        );

        assertEquals(
            tabManager.getTabsDataList().contains(secondTabDataToBeClosed),
            true
        );

        assertEquals(
            tabManager.getTabsDataList().size(),
            4
        );

        assertEquals(
            tabManager.getDetailsTabPaneIndex().intValue(),
            3
        );

        tabManager.closeDetailsTab(secondTabDataToBeClosed); // Close second last tab

        // Check that second tab data is removed
        assertEquals(
            tabManager.getTabsDataList().contains(firstTabDataToBeClosed),
            false
        );

        assertEquals(
            tabManager.getTabsDataList().contains(secondTabDataToBeClosed),
            false
        );

        assertEquals(
            tabManager.getTabsDataList().size(),
            3
        );

        assertEquals(
            tabManager.getDetailsTabPaneIndex().intValue(),
            2
        );
    }

    @Test
    public void closeDisplayTabByTabData_selectedIndexBeforeClosedIndex_success() {
        FiveTabsModelStub fiveTabsModelStub = new FiveTabsModelStub();
        TabManager tabManager = fiveTabsModelStub.getGuiState().getTabManager();
        tabManager.switchDetailsTab(Index.fromZeroBased(1));

        TabData firstTabDataToBeClosed = tabManager.getTabsDataList().get(4);
        TabData secondTabDataToBeClosed = tabManager.getTabsDataList().get(3);

        tabManager.closeDetailsTab(firstTabDataToBeClosed); // Close last tab

        // Check that first tab data is removed
        assertEquals(
            tabManager.getTabsDataList().contains(firstTabDataToBeClosed),
            false
        );

        assertEquals(
            tabManager.getTabsDataList().contains(secondTabDataToBeClosed),
            true
        );

        assertEquals(
            tabManager.getTabsDataList().size(),
            4
        );

        assertEquals(
            tabManager.getDetailsTabPaneIndex().intValue(),
            1
        );

        tabManager.closeDetailsTab(secondTabDataToBeClosed); // Close second last tab

        // Check that second tab data is removed
        assertEquals(
            tabManager.getTabsDataList().contains(firstTabDataToBeClosed),
            false
        );

        assertEquals(
            tabManager.getTabsDataList().contains(secondTabDataToBeClosed),
            false
        );

        assertEquals(
            tabManager.getTabsDataList().size(),
            3
        );

        assertEquals(
            tabManager.getDetailsTabPaneIndex().intValue(),
            1
        );
    }

    @Test
    public void closeDisplayTabByTabData_closeTabForEmptyTabList_throwsNoSuchElementException() {
        NoTabsModelStub noTabsModelStub = new NoTabsModelStub();
        TabManager tabManager = noTabsModelStub.getGuiState().getTabManager();
        assertThrows(
            NoSuchElementException.class, () ->
                tabManager.closeDetailsTab(new TabData(ModelType.PROBLEM, Id.generateId()))
        );
    }

    @Test
    public void closeDisplayTabByTabData_closeTabForNonEmptyTabList_throwsNoSuchElementException() {
        FiveTabsModelStub fiveTabsModelStub = new FiveTabsModelStub();
        TabManager tabManager = fiveTabsModelStub.getGuiState().getTabManager();
        assertThrows(
            NoSuchElementException.class, () ->
                tabManager.closeDetailsTab(new TabData(ModelType.PROBLEM, Id.generateId()))
        );
    }

    /**
     * A Model stub that has a problem list with sample data and a GuiState with 0 tabs in its tab manager.
     */
    private class NoTabsModelStub extends DefaultModelStub {

        private GuiState guiState;
        private ObservableList<Problem> internalList;

        NoTabsModelStub() {

            guiState = new GuiState();

            // Set the internal list to contain sample problems
            Problem[] problems = SampleDataUtil.getSampleProblems();
            internalList = FXCollections.observableList(Arrays.asList(problems));
        }

        @Override
        public ObservableList<Problem> getFilteredProblemList() {
            return internalList;
        }

        @Override
        public GuiState getGuiState() {
            return guiState;
        }
    }

    /**
     * A Model stub that has a problem list with sample data and a GuiState with 5 tabs in its tab manager.
     */
    private class FiveTabsModelStub extends DefaultModelStub {

        private GuiState guiState;
        private ObservableList<Problem> internalList;

        FiveTabsModelStub() {

            guiState = new GuiState();

            // Set the internal list to contain sample problems
            Problem[] problems = SampleDataUtil.getSampleProblems();
            internalList = FXCollections.observableList(Arrays.asList(problems));

            // Open the first 3 problems as tabs
            TabData tabData1 = new TabData(ModelType.PROBLEM, problems[0].getId());
            TabData tabData2 = new TabData(ModelType.PROBLEM, problems[1].getId());
            TabData tabData3 = new TabData(ModelType.PROBLEM, problems[2].getId());
            TabData tabData4 = new TabData(ModelType.PROBLEM, problems[3].getId());
            TabData tabData5 = new TabData(ModelType.PROBLEM, problems[4].getId());
            guiState.getTabManager().openDetailsTab(tabData1);
            guiState.getTabManager().openDetailsTab(tabData2);
            guiState.getTabManager().openDetailsTab(tabData3);
            guiState.getTabManager().openDetailsTab(tabData4);
            guiState.getTabManager().openDetailsTab(tabData5);
        }

        @Override
        public ObservableList<Problem> getFilteredProblemList() {
            return internalList;
        }

        @Override
        public GuiState getGuiState() {
            return guiState;
        }
    }
}
