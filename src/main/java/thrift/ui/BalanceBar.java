package thrift.ui;

import static thrift.model.transaction.Value.DECIMAL_FORMATTER;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A ui for displaying the remaining balance for the month. It sits right on top of the status bar footer.
 */
public class BalanceBar extends UiPart<Region> {

    private static final String FXML = "BalanceBar.fxml";

    @FXML
    private Label monthYearLabel;

    @FXML
    private Label monthBudgetLabel;

    @FXML
    private Label monthBudgetValueLabel;

    @FXML
    private Label monthBalanceLabel;

    @FXML
    private Label monthBalanceValueLabel;

    public BalanceBar(String monthYear, double monthBudget, double monthBalance) {
        super(FXML);
        setMonthYear(monthYear);
        monthBudgetLabel.setText("Budget: $");
        setMonthBudget(monthBudget);
        monthBalanceLabel.setText("\tBalance: ");
        setMonthBalance(monthBalance);

        monthBudgetLabel.setWrapText(true);
        monthBalanceValueLabel.setWrapText(true);
    }

    public void setMonthYear(String monthYear) {
        monthYearLabel.setText(monthYear);
    }

    public void setMonthBudget(double monthBudget) {
        monthBudgetValueLabel.setText(String.valueOf(DECIMAL_FORMATTER.format(monthBudget)));
    }

    public void setMonthBalance(double monthBalance) {
        StringBuilder sb = new StringBuilder();
        if (monthBalance < 0) {
            sb.append("-$").append(DECIMAL_FORMATTER.format(monthBalance * (-1)));
            monthBalanceValueLabel.setStyle("-fx-text-fill: #ff6c4f;");
        } else {
            sb.append("$").append(DECIMAL_FORMATTER.format(monthBalance));
            monthBalanceValueLabel.setStyle("-fx-text-fill: #69ff4f;");
        }
        monthBalanceValueLabel.setText(sb.toString());
    }
}
