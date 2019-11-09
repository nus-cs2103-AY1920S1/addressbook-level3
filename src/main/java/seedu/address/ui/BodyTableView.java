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
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.body.BodyStatus;

//@@author shaoyi1997
/**
 * Table View for list of bodies.
 */
public class BodyTableView extends UiPart<Region> {

    private static final String FXML = "BodyTableView.fxml";
    private static final double COLUMN_WIDTH_FRIDGE_ID = 0.12;
    private static final double COLUMN_WIDTH_NAME = 0.2;
    private static final double COLUMN_WIDTH_ID = 0.15;
    private static final double COLUMN_WIDTH_DATE_OF_ADMISSION = 0.275;
    private static final double COLUMN_WIDTH_BODY_STATUS = 0.24;

    private final Logger logger = LogsCenter.getLogger(BodyTableView.class);

    @FXML
    private TableView<Body> bodyTableView;

    public BodyTableView (ObservableList<Body> bodyList, ObservableValue<Body> selectedBody,
            Consumer<Body> onSelectedBodyChange) {
        super(FXML);
        setupColumns();
        bodyTableView.setItems(bodyList);
        setCellSelectionHandler(selectedBody, onSelectedBodyChange);
    }

    //@@author shaoyi1997-reused
    //Reused from SE-EDU Address Book Level 4
    private void setCellSelectionHandler(ObservableValue<Body> selectedBody, Consumer<Body> onSelectedBodyChange) {
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
    }
    //@@author

    private void setupColumns() {
        TableColumn<Body, IdentificationNumber> fridgeId = setupFridgeIdColumn();
        TableColumn<Body, String> name = setupNameColumn();
        TableColumn<Body, String> id = setupBodyIdColumn();
        TableColumn<Body, String> dateOfAdmission = setupDateOfAdmissionColumn();
        TableColumn<Body, BodyStatus> bodyStatus = setupBodyStatusColumn();
        bodyTableView.getColumns().addAll(name, id, dateOfAdmission, bodyStatus, fridgeId);
    }

    private TableColumn<Body, IdentificationNumber> setupFridgeIdColumn() {
        TableColumn<Body, IdentificationNumber> fridgeId = new TableColumn<>("Fridge ID");
        fridgeId.setCellValueFactory(param ->
                new ReadOnlyObjectWrapper<>(param.getValue().getFridgeId().orElse(null)));
        fridgeId.setCellFactory(tableColumn -> new FridgeIdTableCell());
        fridgeId.prefWidthProperty().bind(bodyTableView.widthProperty().multiply(COLUMN_WIDTH_FRIDGE_ID));
        fridgeId.setStyle("-fx-padding: 0 15 0 0");
        fridgeId.setResizable(false);
        return fridgeId;
    }

    private TableColumn<Body, String> setupNameColumn() {
        return setupStringColumn("Name", COLUMN_WIDTH_NAME,
            param -> new ReadOnlyObjectWrapper<>(param.getValue().getName().toString()));
    }

    private TableColumn<Body, String> setupBodyIdColumn() {
        return setupStringColumn("Body ID", COLUMN_WIDTH_ID,
            param -> new ReadOnlyObjectWrapper<>(param.getValue().getIdNum().toString()));
    }

    private TableColumn<Body, String> setupDateOfAdmissionColumn() {
        return setupStringColumn("Date of Admission", COLUMN_WIDTH_DATE_OF_ADMISSION,
            param -> new ReadOnlyObjectWrapper<>(param.getValue().getDateOfAdmission().toString()));
    }

    private TableColumn<Body, BodyStatus> setupBodyStatusColumn() {
        TableColumn<Body, BodyStatus> bodyStatus = new TableColumn<>("Body Status");
        bodyStatus.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getBodyStatus().get()));
        bodyStatus.setCellFactory(tableColumn -> new BodyStatusTableCell());
        bodyStatus.prefWidthProperty().bind(bodyTableView.widthProperty().multiply(COLUMN_WIDTH_BODY_STATUS));
        bodyStatus.setResizable(false);
        return bodyStatus;
    }

    private TableColumn<Body, String> setupStringColumn(String nameOfColumn, double columnWidth,
            Callback<TableColumn.CellDataFeatures<Body, String>, ObservableValue<String>> value) {
        TableColumn<Body, String> col = new TableColumn<>(nameOfColumn);
        col.prefWidthProperty().bind(bodyTableView.widthProperty()
            .multiply(columnWidth));
        col.setCellValueFactory(value);
        col.setCellFactory(tablecell -> new TableCellForStrings());
        col.setResizable(false);
        return col;
    }

    /**
     * Custom {@code TableCell} that displays and vertically centralizes strings in the cell.
     */
    class TableCellForStrings extends TableCell<Body, String> {
        @Override
        protected void updateItem(String str, boolean empty) {
            super.updateItem(str, empty);

            if (empty | str == null) {
                setGraphic(null);
                setText(null);
            } else {
                StackPane pane = new StackPane();
                Text text = new Text(str);
                text.setFill(Color.WHITE);
                text.wrappingWidthProperty().bind(widthProperty());
                text.textProperty().bind(itemProperty());
                pane.getChildren().add(text);
                setGraphic(pane);
            }
        }
    }

    /**
     * Custom {@code TableCell} that displays the {@code fridgeId} of a {@code Body}.
     */
    class FridgeIdTableCell extends TableCell<Body, IdentificationNumber> {
        @Override
        protected void updateItem(IdentificationNumber id, boolean empty) {
            super.updateItem(id, empty);

            if (empty) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new FridgeIdLabel(id).getRoot());
                setStyle("-fx-padding: 0 15 0 0");
            }
        }
    }

    /**
     * Custom {@code TableCell} that displays the {@code bodyStatus} of a {@code Body}.
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
                label.setText(bodyStatusString.replace("_", " "));
                if (bodyStatusString.equals(ARRIVED.toString())) {
                    label.getStyleClass().add("bodyStatusLabelArrived");
                } else if (bodyStatusString.equals(CLAIMED.toString())) {
                    label.getStyleClass().add("bodyStatusLabelClaimed");
                } else if (bodyStatusString.equals(PENDING_POLICE_REPORT.toString())) {
                    label.getStyleClass().add("bodyStatusLabelPendingPoliceReport");
                } else {
                    label.getStyleClass().add("bodyStatusLabelPending");
                }
                StackPane pane = new StackPane();
                pane.getChildren().add(label);
                pane.setAlignment(Pos.CENTER_LEFT);
                setGraphic(pane);
            }
        }
    }
}
//@@author
