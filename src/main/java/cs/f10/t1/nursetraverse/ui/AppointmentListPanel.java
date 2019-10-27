package cs.f10.t1.nursetraverse.ui;

import cs.f10.t1.nursetraverse.model.appointment.Appointment;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of appointments.
 */
public class AppointmentListPanel extends UiPart<Region> {
    private static final String FXML = "AppointmentListPanel.fxml";

    @FXML
    private ListView<Appointment> appointmentListView;

    public AppointmentListPanel(FilteredList<Appointment> appointmentList) {
        super(FXML);
        appointmentListView.setItems(appointmentList);
        appointmentListView.setCellFactory(listView -> new AppointmentListViewCell());
        appointmentListView.setPlaceholder(new Label("No appointments found."));
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Appointment} using a {@code AppointmentCard}.
     */
    class AppointmentListViewCell extends ListCell<Appointment> {
        @Override
        protected void updateItem(Appointment appointment, boolean empty) {
            super.updateItem(appointment, empty);

            if (empty || appointment == null) {
                setGraphic(null);
                setText(null);
            } else {
                //Modify index such that the displayed index is the source index
                FilteredList<Appointment> appointmentList = (FilteredList<Appointment>) appointmentListView.getItems();
                int index = appointmentList.getSourceIndex(appointmentList.indexOf(appointment));
                setGraphic(new AppointmentCard(appointment, index + 1).getRoot());
            }
        }
    }

}
