package thrift.ui;

import static thrift.model.transaction.Value.DECIMAL_FORMATTER;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A ui for displaying the remaining balance for the month.
 */
public class BalanceBar extends UiPart<Region> {

    private static final String FXML = "BalanceBar.fxml";

    @FXML
    private Label monthYearLabel;

    @FXML
    private Label monthBudgetValueLabel;

    @FXML
    private Label monthBalanceValueLabel;

    @FXML
    private Label monthExpenseValueLabel;

    @FXML
    private Label monthIncomeValueLabel;

    public BalanceBar(String monthYear, double monthBudget, double monthBalance, double monthExpense,
            double monthIncome) {
        super(FXML);
        setMonthYear(monthYear);
        setMonthBudget(monthBudget);
        setMonthBalance(monthBalance);
        setMonthExpense(monthExpense);
        setMonthIncome(monthIncome);
        monthBalanceValueLabel.setWrapText(true);
    }

    public void setMonthYear(String monthYear) {
        monthYearLabel.setText(monthYear);
    }

    public void setMonthBudget(double monthBudget) {
        monthBudgetValueLabel.setText("Budget: $" + String.valueOf(DECIMAL_FORMATTER.format(monthBudget)));
    }

    public void setMonthBalance(double monthBalance) {
        StringBuilder sb = new StringBuilder();
        if (monthBalance < 0) {
            sb.append("-$").append(DECIMAL_FORMATTER.format(monthBalance * (-1)));
            monthBalanceValueLabel.setStyle("-fx-text-fill: #ff6c4f;");
        } else if (monthBalance == 0) {
            sb.append("$").append(DECIMAL_FORMATTER.format(monthBalance));
            monthBalanceValueLabel.setStyle("-fx-text-fill: #757575;");
        } else {
            sb.append("$").append(DECIMAL_FORMATTER.format(monthBalance));
            monthBalanceValueLabel.setStyle("-fx-text-fill: #4CAF50;");
        }
        monthBalanceValueLabel.setText(sb.toString());
    }

    /**
     * Updates the monthly expense text in UI.
     *
     * @param monthExpense is the expense to be updated with.
     */
    public void setMonthExpense(double monthExpense) {
        if (monthExpense == 0) {
            monthExpenseValueLabel.setStyle("-fx-text-fill: #757575;");
        } else {
            monthExpenseValueLabel.setStyle("-fx-text-fill: #ff6c4f;");
        }
        monthExpenseValueLabel.setText("$" + DECIMAL_FORMATTER.format(monthExpense));
    }

    /**
     * Updates the monthly income text in UI.
     *
     * @param monthIncome is the income to be updated with.
     */
    public void setMonthIncome(double monthIncome) {
        if (monthIncome == 0) {
            monthIncomeValueLabel.setStyle("-fx-text-fill: #757575;");
        } else {
            monthIncomeValueLabel.setStyle("-fx-text-fill: #4CAF50;");
        }
        monthIncomeValueLabel.setText("$" + DECIMAL_FORMATTER.format(monthIncome));
    }
}
