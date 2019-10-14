package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;


/**
 * A ui for the table that is used to display the user's biography.
 */
public class BioTable extends UiPart<Region> {

    private static final String FXML = "BioTable.fxml";

    @FXML
    private TableView<BioTableFieldDataPair> tableView;

    @FXML
    private TableColumn<String, String> field;

    @FXML
    private TableColumn<String, String> data;

    public BioTable() {
        super(FXML);
        field.setCellValueFactory(new PropertyValueFactory<String, String>("field"));
        data.setCellValueFactory(new PropertyValueFactory<String, String>("data"));
        tableView.setSelectionModel(null);
    }

    public TableView<BioTableFieldDataPair> getTableView() {
        return this.tableView;
    }
}
