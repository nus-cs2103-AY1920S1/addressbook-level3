package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import seedu.address.cashier.logic.Logic;
import seedu.address.inventory.model.Item;

/**
 * Defines the display for the Cashier tab in the user interface.
 */
public class Cashier extends UiPart<Region> {

    @FXML
    private TableView<Item> tableView;
    @FXML
    private TableColumn<Item, String> idCol;
    @FXML
    private TableColumn<Item, String> descriptionCol;
    @FXML
    private TableColumn<Item, Double> priceCol;
    @FXML
    private TableColumn<Item, String> quantityCol;
    @FXML
    private TableColumn<Item, Double> subtotalCol;

    private List<Item> parseTransactionList(Logic logic) throws Exception {
        // parse and construct User datamodel list by looping your ResultSet rs
        // and return the list
        List<Item> list = new ArrayList<>();
        for (int i = 0; i < logic.getSalesList().size(); i++) {
            logic.getSalesList().get(i).setId(i + 1);
            list.add(logic.getSalesList().get(i));
            System.out.println(logic.getSalesList().get(i).getQuantity());
        }
        return list;
    }

    private static final String FXML = "Cashier.fxml";

    public Cashier (Logic logic) throws Exception {
        super(FXML);
        tableView.getItems().setAll(parseTransactionList(logic));
        idCol.setCellValueFactory(new PropertyValueFactory<Item, String>("id"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Item, String>("description"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Item, Double>("price"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<Item, String>("quantity"));
        subtotalCol.setCellValueFactory(new PropertyValueFactory<Item, Double>("subtotal"));
    }

}





