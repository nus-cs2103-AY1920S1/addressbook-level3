package seedu.algobase.ui.details;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import seedu.algobase.logic.Logic;
import seedu.algobase.model.gui.AlgoBaseTab;
import seedu.algobase.model.gui.TabManager;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.ui.ProblemDetails;
import seedu.algobase.ui.UiPart;

/**
 * Contains details about a specific model.
 */
public class DetailsTabPane extends UiPart<Region> {

    private static final String FXML = "DetailsTabPane.fxml";

    @FXML
    private TabPane tabsPlaceholder;

    public DetailsTabPane(Logic logic) {
        super(FXML);
        TabManager tabManager = logic.getGuiState().getTabManager();
        addListenerForTabChange(tabManager, logic);
    }

    /**
     * Adds a list of details tabs to the tab pane.
     *
     * @param detailsTabs List of tabs to be displayed.
     */
    private void addTabsToTabPane(DetailsTab... detailsTabs) {
        for (DetailsTab detailsTab: detailsTabs) {
            this.tabsPlaceholder.getTabs().add(detailsTab.getTab());
        }
    }

    /**
     * Adds a listener to handle tab changes.
     *
     * @param tabManager Tab manager that processes list changes.
     * @param logic The logic to retrieve objects from.
     */
    private void addListenerForTabChange(TabManager tabManager, Logic logic) {
        tabManager.getTabs().addListener(new ListChangeListener<AlgoBaseTab>() {
            @Override
            public void onChanged(Change<? extends AlgoBaseTab> change) {
                for (AlgoBaseTab algoBaseTab : change.getList()) {
                    Problem problem = logic.getProcessedProblemList().get(algoBaseTab.getModelIndex().getZeroBased());
                    addTabsToTabPane(new DetailsTab(problem.getName().fullName, new ProblemDetails(problem)));
                }
            }
        });
    }
}
