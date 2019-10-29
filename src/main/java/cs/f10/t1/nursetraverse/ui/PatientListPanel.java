package cs.f10.t1.nursetraverse.ui;

import cs.f10.t1.nursetraverse.model.patient.Patient;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of patients.
 */
public class PatientListPanel extends UiPart<Region> {
    private static final String FXML = "PatientListPanel.fxml";

    @FXML
    private ListView<Patient> patientListView;

    public PatientListPanel(FilteredList<Patient> patientList) {
        super(FXML);
        patientListView.setItems(patientList);
        patientListView.setCellFactory(listView -> new PatientListViewCell());
        patientListView.setPlaceholder(new Label("No patients found."));
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Patient} using a {@code PatientCard}.
     */
    class PatientListViewCell extends ListCell<Patient> {
        @Override
        protected void updateItem(Patient patient, boolean empty) {
            super.updateItem(patient, empty);

            if (empty || patient == null) {
                setGraphic(null);
                setText(null);
            } else {
                //Modify index such that the displayed index is the source index
                FilteredList<Patient> patientList = (FilteredList<Patient>) patientListView.getItems();
                int index = patientList.getSourceIndex(patientList.indexOf(patient));
                setGraphic(new PatientCard(patient, index + 1).getRoot());
            }
        }
    }

}
