package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import seedu.address.cashier.logic.Logic;
import seedu.address.inventory.model.Item;

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

        }
        return list;
    }

    private static final String FXML = "Cashier.fxml";

    public Cashier (Logic logic) throws Exception {
        super(FXML);
        tableView.getItems().setAll(parseTransactionList(logic));
        /*ObservableList<Transaction> transactionObservableList = FXCollections.observableArrayList(parseTransactionList(logic));
        tableView.setItems(transactionObservableList);*/
        //personListView.setCellFactory(listView -> new PersonListViewCell());
        idCol.setCellValueFactory(new PropertyValueFactory<Item, String>("id"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Item, String>("description"));
        //categoryCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("category"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Item, Double>("price"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<Item, String>("quantity"));
        subtotalCol.setCellValueFactory(new PropertyValueFactory<Item, Double>("subtotal"));

        /*idCol.setCellFactory(view -> new SalesViewCell<String>("id"));
        dateCol.setCellFactory(view -> new SalesViewCell<String>("date"));
        descriptionCol.setCellFactory(view -> new SalesViewCell<String>("description"));
        categoryCol.setCellFactory(view -> new SalesViewCell<String>("category"));
        amountCol.setCellFactory(view -> new SalesViewCell<Double>("amount"));
        personCol.setCellFactory(view -> new SalesViewCell<String>("name"));*/

    }

    class SalesViewCell<T> extends TableCell<Item, T> {
        private String attribute;

        public SalesViewCell(String attribute) {
            this.attribute = attribute;
        }

        @Override
        protected void updateItem(T type, boolean empty) {
            super.updateItem(type, empty);

            if (empty || type == null) {
                setGraphic(null);
                setText(null);
            } else {
                setText(new PropertyValueFactory<Item, T>(attribute).getProperty());
            }
        }
    }
}





