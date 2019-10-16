package seedu.address.ui;

import static seedu.address.model.entity.body.BodyStatus.ARRIVED;
import static seedu.address.model.entity.body.BodyStatus.CLAIMED;
import static seedu.address.model.entity.body.BodyStatus.PENDING_POLICE_REPORT;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.body.BodyStatus;

/**
 * Table View for list of bodies.
 */
public class BodyTableView extends UiPart<Region> {

    private static final String FXML = "BodyTableView.fxml";
    private final Logger logger = LogsCenter.getLogger(BodyTableView.class);

    @FXML
    private TableView<Body> bodyTableView;

    public BodyTableView (ObservableList<Body> bodyList, ObservableValue<Body> selectedBody,
            Consumer<Body> onSelectedBodyChange) {
        super(FXML);
        setColumns();
        bodyTableView.setItems(bodyList);

        //@@ shaoyi1997-reused
        //Reused from SE-EDU Address Book Level 4
        bodyTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in body table view changed to : '" + newValue + "'");
            onSelectedBodyChange.accept(newValue);
        });
        selectedBody.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected body changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected person,
            // otherwise we would have an infinite loop.
            if (Objects.equals(bodyTableView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                bodyTableView.getSelectionModel().clearSelection();
            } else {
                int index = bodyTableView.getItems().indexOf(newValue);
                bodyTableView.scrollTo(index);
                bodyTableView.getSelectionModel().clearAndSelect(index);
            }
        });
        //@@author shaoyi1997
    }

    private void setColumns() {
        TableColumn<Body, String> name = new TableColumn<>("Name");
        name.setCellValueFactory(new PropertyValueFactory("Name"));
        name.setSortType(TableColumn.SortType.ASCENDING);

        TableColumn<Body, IdentificationNumber> id = new TableColumn<>("Body ID");
        id.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getIdNum()));

        TableColumn<Body, String> dateOfAdmission = new TableColumn<>("Date Of Admission");
        dateOfAdmission.setCellValueFactory(new PropertyValueFactory("dateOfAdmission"));

        TableColumn<Body, BodyStatus> bodyStatus = new TableColumn<>("Body Status");
        bodyStatus.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getBodyStatus().get()));
        bodyStatus.setCellFactory(tableColumn -> new BodyStatusTableCell());
        bodyStatus.setStyle("-justify-self: center");

        bodyTableView.getColumns().addAll(name, id, dateOfAdmission, bodyStatus);
    }

    /**
     * Custom {@code TableCell} that displays the graphics of a {@code Body}.
     */
    class BodyStatusTableCell extends TableCell<Body, BodyStatus> {
        @Override
        protected void updateItem(BodyStatus bodyStatus, boolean empty) {
            super.updateItem(bodyStatus, empty);

            if (empty || bodyStatus == null) {
                setGraphic(null);
                setText(null);
            } else {
                Label label = new Label();
                String bodyStatusString = bodyStatus.toString();
                label.setText(bodyStatusString);
                if (bodyStatusString.equals(ARRIVED.toString())) {
                    label.getStyleClass().add("bodyStatusLabelArrived");
                } else if (bodyStatusString.equals(CLAIMED.toString())) {
                    label.getStyleClass().add("bodyStatusLabelClaimed");
                } else if (bodyStatusString.equals(PENDING_POLICE_REPORT.toString())) {
                    label.getStyleClass().add("bodyStatusLabelPendingPoliceReport");
                } else {
                    label.getStyleClass().add("bodyStatusLabelPending");
                }
                setGraphic(label);
            }
        }
    }
}
