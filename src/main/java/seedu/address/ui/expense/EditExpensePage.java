package seedu.address.ui.expense;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.expense.edit.CancelEditExpenseCommand;
import seedu.address.logic.commands.expense.edit.DoneEditExpenseCommand;
import seedu.address.logic.commands.expense.edit.EditExpenseFieldCommand;
import seedu.address.model.Model;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.MiscExpense;
import seedu.address.ui.MainWindow;
import seedu.address.ui.components.form.DoubleFormItem;
import seedu.address.ui.components.form.TextFormItem;
import seedu.address.ui.template.Page;


/**
 * {@code Page} for displaying the expenses.
 */
public class EditExpensePage extends Page<AnchorPane> {

    private static final String FXML = "expense/EditExpensePage.fxml";
    private static final String FORM_ITEM_STYLESHEET = "/view/trips/trips.css";

    /** Format string for executing commands from the user interface form. */
    private static final String EXECUTE_COMMAND_FORMAT = EditExpenseFieldCommand.COMMAND_WORD + " %1$s%2$s";

    private TextFormItem expenseNameFormItem;
    private DoubleFormItem expenseAmountFormItem;
    private TextFormItem expenseDayFormItem;
    private Expense expenseToEdit;

    @FXML
    private VBox formItemsPlaceholder;

    @FXML
    private Button addButton;

    public EditExpensePage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
        expenseToEdit = model.getPageStatus().getExpense();
        initFormWithModel();
    }

    /**
     * Fills the {@code FormItem}s with the model data.
     */
    public void fillPage() {
        EditExpenseFieldCommand.EditExpenseDescriptor currentEditDescriptor =
                model.getPageStatus().getEditExpenseDescriptor();

        if (currentEditDescriptor == null) {
            return;
        }

        currentEditDescriptor.getName().ifPresent(name ->
                expenseNameFormItem.setValue(name.toString()));
        currentEditDescriptor.getBudget().ifPresent(budget ->
                expenseAmountFormItem.setValue(budget.getValue()));
        currentEditDescriptor.getDayNumber().ifPresent(dayNumber ->
                expenseDayFormItem.setValue(dayNumber.toString()));
    }

    /**
     * Sets fields as disabled.
     */
    public void setDisabledFields() {
        if (expenseToEdit == null || expenseToEdit instanceof MiscExpense) {
            return;
        } else {
            expenseNameFormItem.getRoot().setDisable(true);
            expenseDayFormItem.getRoot().setDisable(true);
        }
    }

    /**
     * Initialises and fills up all the placeholders of this window.
     */
    private void initFormWithModel() {
        //Initialise with new display data
        expenseNameFormItem = new TextFormItem("Name of Expense: ", nameFormValue -> {
            mainWindow.executeGuiCommand(String.format(EXECUTE_COMMAND_FORMAT, PREFIX_NAME, nameFormValue));
        });
        expenseAmountFormItem = new DoubleFormItem("Total amount (in Singapore Dollar): ", amount -> {
            mainWindow.executeGuiCommand(String.format(EXECUTE_COMMAND_FORMAT, PREFIX_BUDGET,
                    String.format("%.2f", amount)));
        });
        expenseDayFormItem = new TextFormItem("Day Number: ", dayNumber -> {
            mainWindow.executeGuiCommand(String.format(EXECUTE_COMMAND_FORMAT, PREFIX_DAY_NUMBER, dayNumber));
        });

        fillPage(); //update and overwrite with existing edit descriptor
        setDisabledFields();

        expenseNameFormItem.getRoot().getStylesheets().clear();
        expenseAmountFormItem.getRoot().getStylesheets().clear();
        expenseDayFormItem.getRoot().getStylesheets().clear();

        expenseNameFormItem.getRoot().getStylesheets().add(FORM_ITEM_STYLESHEET);
        expenseAmountFormItem.getRoot().getStylesheets().add(FORM_ITEM_STYLESHEET);
        expenseDayFormItem.getRoot().getStylesheets().add(FORM_ITEM_STYLESHEET);

        formItemsPlaceholder.getChildren().addAll(
                expenseNameFormItem.getRoot(),
                expenseAmountFormItem.getRoot(),
                expenseDayFormItem.getRoot());
    }

    @FXML
    private void handleEditExpenseDone() {
        String commandText = DoneEditExpenseCommand.COMMAND_WORD;
        mainWindow.executeGuiCommand(commandText);
    }

    @FXML
    private void handleEditCancel() {
        String commandText = CancelEditExpenseCommand.COMMAND_WORD;
        mainWindow.executeGuiCommand(commandText);
    }
}
