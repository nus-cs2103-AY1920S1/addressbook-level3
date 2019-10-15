package seedu.address.ui.expenditure;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.expenditure.edit.CancelEditExpenditureCommand;
import seedu.address.logic.commands.expenditure.edit.DoneEditExpenditureCommand;
import seedu.address.logic.commands.expenditure.edit.EditExpenditureFieldCommand;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;
import seedu.address.ui.components.form.DoubleFormItem;
import seedu.address.ui.components.form.TextFormItem;
import seedu.address.ui.template.Page;

import static seedu.address.logic.parser.CliSyntax.*;

/**
 * WARNING INCOMEPLETE: TODO: FIELDS FOR INVENTORY AND BOOKING.
 */
public class EditExpenditurePage extends Page<AnchorPane> {

    private static final String FXML = "expenses/EditExpenditurePage.fxml";

    private TextFormItem expenditureNameFormItem;
    private DoubleFormItem expenditureAmountFormItem;
    private TextFormItem expenditureDayFormItem;

    @javafx.fxml.FXML
    private VBox formItemsPlaceholder;

    @FXML
    private Button addButton;

    public EditExpenditurePage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
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
     * Initialises and fills up all the placeholders of this window.
     */
    private void initFormWithModel() {
        //Initialise with new display data
        expenditureNameFormItem = new TextFormItem("Name of Expenditure : ", nameFormValue -> {
            mainWindow.executeGuiCommand(
                    EditExpenditureFieldCommand.COMMAND_WORD
                            + " " + PREFIX_NAME + nameFormValue);
        });
        expenditureAmountFormItem = new DoubleFormItem("Total amount : ", totalBudget -> {
            mainWindow.executeGuiCommand(EditExpenditureFieldCommand.COMMAND_WORD
                    + " " + PREFIX_BUDGET + totalBudget);
        });
        expenditureDayFormItem = new TextFormItem("Day Number : ", dayNumber -> {
            mainWindow.executeGuiCommand(EditExpenditureFieldCommand.COMMAND_WORD
                    + " " + PREFIX_DAY_NUMBER + dayNumber);
        });

        fillPage(); //update and overwrite with existing edit descriptor

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
