package seedu.address.ui.currency;

import static seedu.address.logic.parser.CliSyntax.PREFIX_SYMBOL;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.currency.EditCurrencyFieldCommand;
import seedu.address.model.Model;
import seedu.address.model.currency.Symbol;
import seedu.address.model.expense.Expense;
import seedu.address.ui.MainWindow;
import seedu.address.ui.UiPart;

/**
 * A component for displaying the details of preset currency symbols.
 */
public class PresetSymbols extends UiPart<VBox> {

    private static final String FXML = "currency/PresetSymbols.fxml";

    @FXML
    private ToggleButton option1;
    @FXML
    private ToggleButton option2;
    @FXML
    private ToggleButton option3;
    @FXML
    private ToggleButton option4;
    @FXML
    private ToggleButton option5;
    @FXML
    private ToggleButton option6;
    @FXML
    private ToggleButton option7;


    @FXML
    private VBox propertiesContainer;
    private ToggleGroup group;
    private Expense expense;
    private Index displayedIndex;
    private Model model;
    private MainWindow mainWindow;

    public PresetSymbols(MainWindow mainWindow) {
        super(FXML);
        this.mainWindow = mainWindow;
        fillPresetSymbols();
    }

    /**
     * Fills the labels of this expense card.
     */
    private void fillPresetSymbols() {
        group = new ToggleGroup();
        option1.setText(new Symbol("1").toString() + " (1)");
        option1.setToggleGroup(group);
        option2.setText(new Symbol("2").toString() + " (2)");
        option2.setToggleGroup(group);
        option3.setText(new Symbol("3").toString() + " (3)");
        option3.setToggleGroup(group);
        option4.setText(new Symbol("4").toString() + " (4)");
        option4.setToggleGroup(group);
        option5.setText(new Symbol("5").toString() + " (5)");
        option5.setToggleGroup(group);
        option6.setText(new Symbol("6").toString() + " (6)");
        option6.setToggleGroup(group);
        option7.setText(new Symbol("7").toString() + " (7)");
        option7.setToggleGroup(group);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        } else {
            return false;
        }
    }

    @FXML
    private void handleToggle() {
        ToggleButton toggleButton = (ToggleButton) group.getSelectedToggle();
        mainWindow.executeGuiCommand(EditCurrencyFieldCommand.COMMAND_WORD
                + " " + PREFIX_SYMBOL + toggleButton.getText().split(" ")[1].charAt(1));
    }

}
