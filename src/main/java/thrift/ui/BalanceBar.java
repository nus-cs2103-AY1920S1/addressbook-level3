package thrift.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import thrift.model.transaction.Value;

/**
 * A ui for displaying the remaining balance for the month. It sits right on top of the status bar footer.
 */
public class BalanceBar extends UiPart<Region> {

    private static final String FXML = "BalanceBar.fxml";

    @FXML
    private Label monthlyBudgetLabel;

    @FXML
    private Label monthlyBudget;

    @FXML
    private Label balanceRemainingLabel;

    @FXML
    private Label balance;

    public BalanceBar(Value monthlyBudgetValue, Value balanceRemainingValue) {
        super(FXML);
        monthlyBudgetLabel.setText("Monthly Budget: ");
        monthlyBudget.setText("$" + monthlyBudgetValue.toString());
        balanceRemainingLabel.setText("Balance: ");

        StringBuilder sb = new StringBuilder();
        if (balanceRemainingValue.getMonetaryValue() < 0) {
            sb.append("-$").append(balanceRemainingValue.toString());
            balance.setStyle("-fx-text-fill: #ff6c4f;");
        } else {
            sb.append("$").append(balanceRemainingValue.toString());
            balance.setStyle("-fx-text-fill: #69ff4f;");
        }
        balance.setText(sb.toString());

        monthlyBudget.setWrapText(true);
        balance.setWrapText(true);
    }
}
