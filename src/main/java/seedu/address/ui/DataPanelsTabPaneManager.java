package seedu.address.ui;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * Manages the TabPane and adds programmatic controls to it
 */
public class DataPanelsTabPaneManager {
    private final TabPane tabPane;
    private final Tab patientTabPage;
    private final Tab ongoingVisitTabPage;

    public DataPanelsTabPaneManager(TabPane pane, Tab patientTabPage, Tab ongoingVisitTabPage) {
        requireAllNonNull(pane, patientTabPage, ongoingVisitTabPage);
        tabPane = pane;
        this.patientTabPage = patientTabPage;
        this.ongoingVisitTabPage = ongoingVisitTabPage;
    }

    /**
     * Change the current tab to patient tab.
     */
    public void changeToPatientTab() {
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(patientTabPage);
    }

    /**
     * Change the current tab to ongoing visit tab.
     */
    public void changeToOngoingVisitTab() {
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(ongoingVisitTabPage);
    }
}
