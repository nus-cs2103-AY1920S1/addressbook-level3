package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.util.Callback;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.body.Body;

public class BodyTableView extends UiPart<Region> {

    private static final String FXML = "BodyTableView.fxml";
    private final Logger logger = LogsCenter.getLogger(BodyTableView.class);

    @FXML
    private TableView<Body> bodyTableView;

    public BodyTableView (ObservableList<Body> bodyList) {
        super(FXML);
        setColumns();
        bodyTableView.setItems(bodyList);
    }

    private void setColumns() {
        TableColumn<Body, String> name = new TableColumn<>("Name");
        name.setCellValueFactory(new PropertyValueFactory("Name"));
        name.setSortType(TableColumn.SortType.ASCENDING);

        TableColumn<Body, IdentificationNumber> id = new TableColumn<>("Body ID");
        id.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getIdNum()));

        TableColumn<Body, String> dateOfAdmission = new TableColumn<>("Date Of Admission");
        dateOfAdmission.setCellValueFactory(new PropertyValueFactory("dateOfAdmission"));

        bodyTableView.getColumns().addAll(name, id, dateOfAdmission);
    }

    /**
     * Custom {@code TableCell} that displays the graphics of a {@code Body}.
     */
    class BodyTableCell extends TableCell<Body, IdentificationNumber> {
        @Override
        protected void updateItem(IdentificationNumber id, boolean empty) {
            super.updateItem(id, empty);

            if (empty || id == null) {
                setGraphic(null);
                setText(null);
            } else {
                Text idText = new Text();
                idText.setText(id.toString());
                setGraphic(idText);
            }
        }
    }
}
