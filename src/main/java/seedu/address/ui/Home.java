package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import seedu.address.person.commons.core.LogsCenter;
import seedu.address.transaction.logic.Logic;
import seedu.address.transaction.model.Transaction;

/**
 * The Home Tab Pane. Provides the basic application layout containing
 * a table which can be populated with other JavaFX elements.
 */
public class Home extends UiPart<Region> {
    private static final String FXML = "Home.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private TableView<Transaction> tableView;
    @FXML
    private TableColumn<Transaction, String> idCol;
    @FXML
    private TableColumn<Transaction, String> dateCol;
    @FXML
    private TableColumn<Transaction, String> descriptionCol;
    @FXML
    private TableColumn<Transaction, String> categoryCol;
    @FXML
    private TableColumn<Transaction, Double> amountCol;
    @FXML
    private TableColumn<Transaction, String> personCol;

    /**
     * Populates the table to show the transactions in transaction list in the transaction model.
     */
    public Home (Logic logic) {
        super(FXML);
        tableView.getItems().setAll(parseTransactionList(logic));
        idCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("id"));
        dateCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("date"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("description"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("category"));
        amountCol.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("amount"));
        personCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("name"));
    }

    /**
     * Parses the filtered list in model to update the indexes and put it into a list.
     * @param logic Transaction Logic
     * @return List of transactions
     */
    private List<Transaction> parseTransactionList(Logic logic) {
        // parse and construct User data model list by looping your transaction list
        // and return the list
        List<Transaction> list = new ArrayList<>();
        for (int i = 0; i < logic.getFilteredList().size(); i++) {
            logic.getFilteredList().get(i).setId(i + 1);
            list.add(logic.getFilteredList().get(i));
        }
        return list;
    }
}
