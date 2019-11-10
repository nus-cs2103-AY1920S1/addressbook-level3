package seedu.moolah.ui.budget;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import seedu.moolah.commons.core.LogsCenter;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.ui.panel.Panel;
import seedu.moolah.ui.panel.PanelName;


/**
 * Panel containing the list of budgets.
 */
public class BudgetListPanel extends Panel {
    public static final PanelName PANEL_NAME = new PanelName("Budget List");
    private static final String FXML = "ListPanel.fxml";
    private static final Border PRIMARY_BUDGET_BORDER = new Border(new BorderStroke(Color.RED,
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

    private final Logger logger = LogsCenter.getLogger(BudgetListPanel.class);

    @FXML
    private StackPane titlePlaceHolder;
    @FXML
    private ListView<Budget> listView;

    public BudgetListPanel(ObservableList<Budget> budgetList) {
        super(FXML);

        titlePlaceHolder.getChildren().add(new Label("Budget List"));
        listView.setItems(budgetList);
        listView.setCellFactory(listView -> new BudgetListViewCell());

    }

    @Override
    public void view() {
        getRoot().setVisible(true);
        getRoot().setDisable(false);
    }

    @Override
    public void hide() {
        getRoot().setVisible(false);
        getRoot().setDisable(true);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Budget} using a {@code BudgetCard}.
     */
    class BudgetListViewCell extends ListCell<Budget> {
        @Override
        protected void updateItem(Budget budget, boolean empty) {
            super.updateItem(budget, empty);

            if (empty || budget == null) {
                setGraphic(null);
                setText(null);
            } else {
                BudgetCard budgetCard = new BudgetCard(budget);
                if (budget.isPrimary()) {
                    budgetCard.setBorder(PRIMARY_BUDGET_BORDER);
                }
                setGraphic(budgetCard.getRoot());
            }
        }
    }

}
