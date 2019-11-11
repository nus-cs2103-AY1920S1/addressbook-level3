package seedu.address.ui;

import static seedu.address.inventory.model.Item.DECIMAL_FORMAT;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;

import seedu.address.inventory.logic.Logic;
import seedu.address.inventory.model.Item;

/**
 * Defines the display for the Inventory tab in the user interface.
 */
public class Inventory extends UiPart<Region> {
    private static final String FXML = "Inventory.fxml";

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
    private TableColumn<Item, String> costCol;
    @FXML
    private TableColumn<Item, String> totalCostCol;
    @FXML
    private TableColumn<Item, String> priceCol;
    @FXML
    private TableColumn<Item, String> expectedRevenueCol;

    public Inventory (Logic logic) throws Exception {
        super(FXML, Lion.getInstance());
        tableView.getItems().setAll(parseInventoryList(logic));
        tableView.setOnMouseClicked(event -> onClickedRow(tableView));
        idCol.setCellValueFactory(new PropertyValueFactory<Item, String>("id"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Item, String>("description"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<Item, String>("category"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<Item, Integer>("quantity"));
        costCol.setCellValueFactory(item -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(DECIMAL_FORMAT.format(item.getValue().getCost()));
            return property;
        });
        totalCostCol.setCellValueFactory(item -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(DECIMAL_FORMAT.format(item.getValue().getTotalCost()));
            return property;
        });
        priceCol.setCellValueFactory(item -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(DECIMAL_FORMAT.format(item.getValue().getPrice()));
            return property;
        });
        expectedRevenueCol.setCellValueFactory(item -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(DECIMAL_FORMAT.format(item.getValue().getSubtotal()));
            return property;
        });
    }

    /**
     * Parses the filtered list in model to update the indexes and put it into a list.
     * @param logic Inventory Logic
     * @return List of Items.
     */
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
}
