package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import seedu.address.inventory.logic.Logic;
import seedu.address.inventory.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines the display for the Inventory tab in the user interface.
 */
public class Inventory extends UiPart<Region> {

    @FXML
    private TableView<Item> tableView;
    @FXML
    private TableColumn<Item, String> idCol;
    @FXML
    private TableColumn<Item, String> descriptionCol;
    @FXML
    private TableColumn<Item, String> categoryCol;
    @FXML
    private TableColumn<Item, Integer> quantityCol;
    @FXML
    private TableColumn<Item, Double> costCol;
    @FXML
    private TableColumn<Item, Double> priceCol;

    private List<Item> parseInventoryList(Logic logic) throws Exception {
        // parse and construct User datamodel list by looping your ResultSet rs
        // and return the list
        List<Item> list = new ArrayList<>();
        for (int i = 0; i < logic.getInventoryList().size(); i++) {
            logic.getInventoryList().getItemByIndex(i).setId(i + 1);
            list.add(logic.getInventoryList().getItemByIndex(i));
        }
        return list;
    }

    private static final String FXML = "Inventory.fxml";

    public Inventory (Logic logic) throws Exception {
        super(FXML);
        tableView.getItems().setAll(parseInventoryList(logic));
        idCol.setCellValueFactory(new PropertyValueFactory<Item, String>("id"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Item, String>("description"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<Item, String>("category"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<Item, Integer>("quantity"));
        costCol.setCellValueFactory(new PropertyValueFactory<Item, Double>("cost"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Item, Double>("price"));
    }
}
