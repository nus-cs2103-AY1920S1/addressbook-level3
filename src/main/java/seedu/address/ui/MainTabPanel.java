package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;

import java.util.logging.Logger;

public class MainTabPanel extends UiPart<Region> {
    private static final String FXML = "MainTabPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MainTabPanel.class);

    @FXML
    private StackPane transactionListPanelPlaceholder;

    @FXML
    private StackPane budgetListPanelPlaceholder;

    @FXML
    private TabPane mainTabPanel;

    public MainTabPanel(TransactionListPanel transactionListPanel, BudgetListPanel budgetListPanel) {
        super(FXML);

        transactionListPanelPlaceholder.getChildren().add(transactionListPanel.getRoot());

        budgetListPanelPlaceholder.getChildren().add(budgetListPanel.getRoot());

    }

    protected void switchToTransactionTab() {
        this.mainTabPanel.getSelectionModel().select(0);
    }

    protected void switchToBudgetTab() {
        this.mainTabPanel.getSelectionModel().select(1);
    }
}
