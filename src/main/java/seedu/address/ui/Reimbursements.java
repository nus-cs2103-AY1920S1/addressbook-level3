package seedu.address.ui;

import static seedu.address.transaction.model.transaction.Transaction.DECIMAL_FORMAT;

import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import seedu.address.person.commons.core.LogsCenter;
import seedu.address.reimbursement.logic.Logic;
import seedu.address.reimbursement.model.Reimbursement;


/**
 * Defines the display for the Reimbursements tab in the user interface.
 */
public class Reimbursements extends UiPart<Region> {
    private static final String FXML = "Reimbursements.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private TableView<Reimbursement> tableView;
    @FXML
    private TableColumn<Reimbursement, String> idCol;
    @FXML
    private TableColumn<Reimbursement, String> personCol;
    @FXML
    private TableColumn<Reimbursement, String> descriptionCol;
    @FXML
    private TableColumn<Reimbursement, String> amountCol;
    @FXML
    private TableColumn<Reimbursement, String> deadlineCol;

    /**
     * Populates the table to show the transactions in transaction list in the transaction model.
     */
    public Reimbursements(Logic logic) {
        super(FXML, Lion.getInstance());
        tableView.getItems().setAll(parseReimbursementList(logic));
        tableView.setOnMouseClicked(event -> onClickedRow(tableView));
        idCol.setCellValueFactory(new PropertyValueFactory<Reimbursement, String>("idCol"));
        personCol.setCellValueFactory(new PropertyValueFactory<Reimbursement, String>("personCol"));
        amountCol.setCellValueFactory(reimbursement -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(DECIMAL_FORMAT.format(reimbursement.getValue().getAmount()));
            return property;
        });
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Reimbursement, String>("descriptionCol"));
        deadlineCol.setCellValueFactory(new PropertyValueFactory<Reimbursement, String>("deadlineCol"));

        idCol.setSortable(false);
        personCol.setSortable(false);
        amountCol.setSortable(false);
        descriptionCol.setSortable(false);
        deadlineCol.setSortable(false);
    }

    /**
     * Parses the filtered list in model to update the indexes and put it into a list.
     *
     * @param logic Transaction Logic
     * @return List of transactions
     */
    private ArrayList<Reimbursement> parseReimbursementList(Logic logic) {
        ArrayList<Reimbursement> list = logic.getFilteredList().getList();
        for (int i = 0; i < list.size(); i++) {
            Reimbursement rmb = list.get(i);
            rmb.setIdCol(i + 1);
            rmb.setPersonCol();
            rmb.setDescriptionCol();
            rmb.setDeadlineCol();
        }
        return list;
    }
}
