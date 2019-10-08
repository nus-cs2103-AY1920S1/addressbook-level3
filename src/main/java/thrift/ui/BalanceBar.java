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
    private Label balanceRemainingLabel;

    @FXML
    private Label balance;


    public BalanceBar(Value balanceRemainingValue) {
        super(FXML);
        balanceRemainingLabel.setText("Balance:");
        balance.setText("$" + balanceRemainingValue.toString());
    }
}
