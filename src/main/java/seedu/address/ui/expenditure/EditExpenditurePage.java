package seedu.address.ui.expenditure;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.expenditure.edit.CancelEditExpenditureCommand;
import seedu.address.logic.commands.expenditure.edit.DoneEditExpenditureCommand;
import seedu.address.logic.commands.expenditure.edit.EditExpenditureFieldCommand;
import seedu.address.model.Model;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.ui.MainWindow;
import seedu.address.ui.components.form.DoubleFormItem;
import seedu.address.ui.components.form.TextFormItem;
import seedu.address.ui.template.Page;


/**
 * WARNING INCOMEPLETE: TODO: FIELDS FOR INVENTORY AND BOOKING.
 */
public class EditExpenditurePage extends Page<AnchorPane> {

    private static final String FXML = "expenses/EditExpenditurePage.fxml";

    private TextFormItem expenditureNameFormItem;
    private DoubleFormItem expenditureAmountFormItem;
    private TextFormItem expenditureDayFormItem;
    private Expenditure expenditureToEdit;
    @javafx.fxml.FXML
    private VBox formItemsPlaceholder;

    @FXML
    private Button addButton;

    public EditExpenditurePage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
        expenditureToEdit = model.getPageStatus().getExpenditure();
        initFormWithModel();
    }

    /**
     * Fills the {@code FormItem}s with the model data.
     */
    public void fillPage() {
        EditExpenditureFieldCommand.EditExpenditureDescriptor currentEditDescriptor =
                model.getPageStatus().getEditExpenditureDescriptor();

        if (currentEditDescriptor == null) {
            return;
        }

        currentEditDescriptor.getName().ifPresent(name ->
                expenditureNameFormItem.setValue(name.toString()));
        currentEditDescriptor.getBudget().ifPresent(budget ->
                expenditureAmountFormItem.setValue(budget.value));
        currentEditDescriptor.getDayNumber().ifPresent(dayNumber ->
                expenditureDayFormItem.setValue(dayNumber.toString()));
    }

    /**
     * Sets fields as disabled.
     */
    public void setDisabledFields() {
        if (expenditureToEdit == null || expenditureToEdit.getRemovability()) {
            return;
        } else {
            expenditureNameFormItem.getRoot().setDisable(true);
            expenditureDayFormItem.getRoot().setDisable(true);
        }
    }

    /**
     * Initialises and fills up all the placeholders of this window.
     */
    private void initFormWithModel() {
        //Initialise with new display data
        expenditureNameFormItem = new TextFormItem("Name of Expenditure : ", nameFormValue -> {
            mainWindow.executeGuiCommand(
                    EditExpenditureFieldCommand.COMMAND_WORD
                            + " " + PREFIX_NAME + nameFormValue);
        });
        expenditureAmountFormItem = new DoubleFormItem("Total amount (in Singapore Dollar): ", totalBudget -> {
            mainWindow.executeGuiCommand(EditExpenditureFieldCommand.COMMAND_WORD
                    + " " + PREFIX_BUDGET + totalBudget);
        });
        expenditureDayFormItem = new TextFormItem("Day Number : ", dayNumber -> {
            mainWindow.executeGuiCommand(EditExpenditureFieldCommand.COMMAND_WORD
                    + " " + PREFIX_DAY_NUMBER + dayNumber);
        });

        fillPage(); //update and overwrite with existing edit descriptor
        setDisabledFields();
        formItemsPlaceholder.getChildren().addAll(
                expenditureNameFormItem.getRoot(),
                expenditureAmountFormItem.getRoot(),
                expenditureDayFormItem.getRoot());
    }

    @FXML
    private void handleEditExpenditureDone() {
        String commandText = DoneEditExpenditureCommand.COMMAND_WORD;
        mainWindow.executeGuiCommand(commandText);
    }

    @FXML
    private void handleEditCancel() {
        String commandText = CancelEditExpenditureCommand.COMMAND_WORD;
        mainWindow.executeGuiCommand(commandText);
    }
}
