package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.entry.Income;

/**
 * Panel containing the list of incomes.
 */
public class IncomeListPanel extends UiPart<Region> {
    private static final String FXML = "IncomeListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(IncomeListPanel.class);

    @FXML
    private ListView<Income> incomeListView;

    public IncomeListPanel(ObservableList<Income> incomeList) {
        super(FXML);
        incomeListView.setItems(incomeList);
        incomeListView.setCellFactory(listView -> new IncomeListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Income} using a {@code IncomeCard}.
     */
    class IncomeListViewCell extends ListCell<Income> {
        @Override
        protected void updateItem(Income income, boolean empty) {
            super.updateItem(income, empty);

            if (empty || income == null) {

                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new IncomeCard(income, getIndex() + 1).getRoot());
            }
        }
    }

}
