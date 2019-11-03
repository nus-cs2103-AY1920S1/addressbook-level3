package seedu.address.ui;

import static seedu.address.inventory.model.Item.DECIMAL_FORMAT;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
import seedu.address.person.commons.core.LogsCenter;

/**
 * Defines the display for the Cashier tab in the user interface.
 */
public class Cashier extends UiPart<Region> {

    private static final String FXML = "Cashier.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private TableView<Item> tableView;
    @FXML
    private TableColumn<Item, String> idCol;
    @FXML
    private TableColumn<Item, String> descriptionCol;
    @FXML
    private TableColumn<Item, String> priceCol;
    @FXML
    private TableColumn<Item, String> quantityCol;
    @FXML
    private TableColumn<Item, String> subtotalCol;
    @FXML
    private Label totalAmountLabel;
    @FXML
    private Label cashierLabel;

    public Cashier (Logic logic) throws Exception {
        super(FXML, Lion.getInstance());
        tableView.getItems().setAll(parseTransactionList(logic));
        tableView.setOnMouseClicked(event -> onClickedRow(tableView));
        idCol.setCellValueFactory(new PropertyValueFactory<Item, String>("id"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Item, String>("description"));
        priceCol.setCellValueFactory(item -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(DECIMAL_FORMAT.format(item.getValue().getPrice()));
            return property;
        });
        quantityCol.setCellValueFactory(new PropertyValueFactory<Item, String>("quantity"));
        subtotalCol.setCellValueFactory(item -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(DECIMAL_FORMAT.format(item.getValue().getSubtotal()));
            return property;
        });

        // for total amount
        SimpleStringProperty amount = new SimpleStringProperty();
        String s = logic.getAmount();
        amount.setValue(s);
        amount.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                logger.info("Old amount: " + oldValue + ", New amount: " + newValue);
            }
        });
        totalAmountLabel.textProperty().bind(amount);

        // for cashier-in-charge
        SimpleStringProperty cashierProperty = new SimpleStringProperty();
        String str = String.valueOf(logic.getCashier());
        cashierProperty.setValue(str);
        cashierProperty.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                logger.info("Previous cashier: " + oldValue + ", New cashier: " + newValue);
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



