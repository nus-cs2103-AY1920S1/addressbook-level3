package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import seedu.address.commons.core.LogsCenter;

/**
 * Panel containing the different tabs of the application.
 */
public class MainTabPanel extends UiPart<Region> {
    private static final String FXML = "MainTabPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MainTabPanel.class);

    @FXML
    private StackPane transactionListPanelPlaceholder;

    @FXML
    private StackPane budgetListPanelPlaceholder;

    @FXML
    private StackPane ledgerListPanelPlaceholder;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane projectionGraphPlaceholder;

    @FXML
    private TabPane mainTabPanel;

    public MainTabPanel(TransactionListPanel transactionListPanel, BudgetListPanel budgetListPanel,
                        LedgerListPanel ledgerListPanel, ProjectionListPanel projectionListPanel,
                        PersonListPanel personListPanel) {
        super(FXML);

        transactionListPanelPlaceholder.getChildren().add(transactionListPanel.getRoot());

        budgetListPanelPlaceholder.getChildren().add(budgetListPanel.getRoot());

        ledgerListPanelPlaceholder.getChildren().add(ledgerListPanel.getRoot());

        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        projectionGraphPlaceholder.getChildren().add(projectionListPanel.getRoot());
    }

    //  TODO: remove
    protected void switchToTransactionTab() {
        this.mainTabPanel.getSelectionModel().select(0);
    }

    protected void switchToBudgetTab() {
        this.mainTabPanel.getSelectionModel().select(1);
    }

    protected void switchToLedgerTab() {
        this.mainTabPanel.getSelectionModel().select(2);
    }

    protected void switchToProjectionTab() {
        this.mainTabPanel.getSelectionModel().select(3);
    }
}
