package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import seedu.address.transaction.logic.Logic;
import seedu.address.transaction.model.Transaction;

public class Home extends UiPart<Region> {

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

    private List<Transaction> parseTransactionList(Logic logic) throws Exception {
        // parse and construct User datamodel list by looping your ResultSet rs
        // and return the list
        List<Transaction> list = new ArrayList<>();
        for (int i = 0; i < logic.getTransactionList().size(); i++) {
            logic.getTransactionList().get(i).setId(i + 1);
            list.add(logic.getTransactionList().get(i));

        }
        return list;
    }
    private static final String FXML = "Home.fxml";

    public Home (Logic logic) throws Exception {
        super(FXML);
        tableView.getItems().setAll(parseTransactionList(logic));
        idCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("id"));
        dateCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("date"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("description"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("category"));
        amountCol.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("amount"));
        personCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("name"));

    }
}
