package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;


/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class BioTable extends UiPart<Region> {

    private static final String FXML = "BioTable.fxml";

    @FXML
    private TableView tableView;

    @FXML
    private TableColumn field;

    @FXML
    private TableColumn data;

    public BioTable() {
        super(FXML);
        field.setCellValueFactory(new PropertyValueFactory<>("field"));
        data.setCellValueFactory(new PropertyValueFactory<>("data"));
        tableView.setSelectionModel(null);
    }

    public TableView getTableView() {
        return this.tableView;
    }
}
