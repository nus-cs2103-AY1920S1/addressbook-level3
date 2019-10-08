package seedu.address.ui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.scene.control.skin.TableViewSkin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.util.Callback;

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
