package cs.f10.t1.nursetraverse.ui;

import cs.f10.t1.nursetraverse.commons.util.CollectionUtil;
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
    private final Tab appointmentTabPage;

    public DataPanelsTabPaneManager(TabPane pane, Tab patientTabPage, Tab ongoingVisitTabPage, Tab appointmentTabPage) {
        CollectionUtil.requireAllNonNull(pane, patientTabPage, ongoingVisitTabPage, appointmentTabPage);
        tabPane = pane;
        this.patientTabPage = patientTabPage;
        this.ongoingVisitTabPage = ongoingVisitTabPage;
        this.appointmentTabPage = appointmentTabPage;
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

    /**
     * Change the current tab to ongoing visit tab.
     */
    public void changeToAppointmentTab() {
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(appointmentTabPage);
    }
}
