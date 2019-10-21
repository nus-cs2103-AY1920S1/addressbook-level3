package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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

    private static final String FXML = "Cashier.fxml";

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
    @FXML
    private Label totalAmountLabel;
    @FXML
    private Label cashierLabel;

    public Cashier (Logic logic) throws Exception {
        super(FXML);
        tableView.getItems().setAll(parseTransactionList(logic));
        idCol.setCellValueFactory(new PropertyValueFactory<Item, String>("id"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Item, String>("description"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Item, Double>("price"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<Item, String>("quantity"));
        subtotalCol.setCellValueFactory(new PropertyValueFactory<Item, Double>("subtotal"));

        SimpleStringProperty amount = new SimpleStringProperty();
        String s = String.valueOf(logic.getAmount());
        amount.setValue(s);
        amount.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("Old: " + oldValue + ", New: " + newValue);
            }
          });
           totalAmountLabel.textProperty().bind(amount);

        SimpleStringProperty cashierProperty = new SimpleStringProperty();
        String str = String.valueOf(logic.getCashier());
        cashierProperty.setValue(str);
        cashierProperty.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("Old: " + oldValue + ", New: " + newValue);
            }
        });
        cashierLabel.textProperty().bind(cashierProperty);
    }

    /**
     * Parse and construct user datamodel list by looping through a list.
     * Returns the list.
     *
     * @param logic the logic to be used
     * @return the list of sales with ID updated
     * @throws Exception if an item is invalid
     */
    private List<Item> parseTransactionList(Logic logic) throws Exception {
        // parse and construct User datamodel list by looping your ResultSet rs
        // and return the list
        List<Item> list = new ArrayList<>();
        for (int i = 0; i < logic.getSalesList().size(); i++) {
            logic.getSalesList().get(i).setId(i + 1);
            list.add(logic.getSalesList().get(i));
        }
        return list;
    }


}



