package seedu.savenus.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import seedu.savenus.commons.core.LogsCenter;
import seedu.savenus.model.info.AddInfo;
import seedu.savenus.logic.commands.InfoCommand;
import seedu.savenus.model.info.BudgetInfo;
import seedu.savenus.model.info.BuyInfo;
import seedu.savenus.model.info.ClearInfo;
import seedu.savenus.model.info.DefaultInfo;
import seedu.savenus.model.info.DeleteInfo;
import seedu.savenus.model.info.EditInfo;
import seedu.savenus.model.info.ExitInfo;
import seedu.savenus.model.info.FindInfo;
import seedu.savenus.model.info.HelpInfo;
import seedu.savenus.model.info.InfoInfo;
import seedu.savenus.model.info.ListInfo;
import seedu.savenus.model.info.RecommendInfo;
import seedu.savenus.model.info.SortInfo;

/**
 * Controller for a info page
 */
public class InfoWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(InfoWindow.class);
    private static final String FXML = "InfoWindow.fxml";

    @FXML
    private Label commandWord;

    @FXML
    private Label infoMessage;

    @FXML
    private Label usageExample;

    @FXML
    private Label output;

    @FXML
    private Button quitButton;

    /**
     * Creates a new InfoWindow.
     *
     * @param root Stage to use as the root of the InfoWindow.
     */
    public InfoWindow(Stage root) {
        super(FXML, root);
        root.initStyle(StageStyle.UNDECORATED);
        commandWord.setText("Default");
        infoMessage.setText("Default");
        usageExample.setText("Default");
    }

    /**
     * Creates a new InfoWindow.
     */
    public InfoWindow() {
        this(new Stage());
    }

    public void closeWindow() {
        getRoot().close();
    }

    /**
     * Shows the information window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show(String input) {
        logger.fine("Showing info page about the command.");
        switch (input) {
        case InfoCommand.ADD_INFO :
            commandWord.setText(AddInfo.COMMAND_WORD);
            infoMessage.setText(AddInfo.INFORMATION);
            usageExample.setText(AddInfo.USAGE);
            output.setText(AddInfo.OUTPUT);
            break;
        case InfoCommand.BUDGET_INFO :
            commandWord.setText(BudgetInfo.COMMAND_WORD);
            infoMessage.setText(BudgetInfo.INFORMATION);
            usageExample.setText(BudgetInfo.USAGE);
            output.setText(BudgetInfo.OUTPUT);
            break;
        case InfoCommand.BUY_INFO :
            commandWord.setText(BuyInfo.COMMAND_WORD);
            infoMessage.setText(BuyInfo.INFORMATION);
            usageExample.setText(BuyInfo.USAGE);
            output.setText(BuyInfo.OUTPUT);
            break;
        case InfoCommand.CLEAR_INFO :
            commandWord.setText(ClearInfo.COMMAND_WORD);
            infoMessage.setText(ClearInfo.INFORMATION);
            usageExample.setText(ClearInfo.USAGE);
            output.setText(ClearInfo.OUTPUT);
            break;
        case InfoCommand.DEFAULT_INFO :
            commandWord.setText(DefaultInfo.COMMAND_WORD);
            infoMessage.setText(DefaultInfo.INFORMATION);
            usageExample.setText(DefaultInfo.USAGE);
            output.setText(DefaultInfo.OUTPUT);
            break;
        case InfoCommand.DELETE_INFO :
            commandWord.setText(DeleteInfo.COMMAND_WORD);
            infoMessage.setText(DeleteInfo.INFORMATION);
            usageExample.setText(DeleteInfo.USAGE);
            output.setText(DeleteInfo.OUTPUT);
            break;
        case InfoCommand.EDIT_INFO :
            commandWord.setText(EditInfo.COMMAND_WORD);
            infoMessage.setText(EditInfo.INFORMATION);
            usageExample.setText(EditInfo.USAGE);
            output.setText(EditInfo.OUTPUT);
            break;
        case InfoCommand.EXIT_INFO :
            commandWord.setText(ExitInfo.COMMAND_WORD);
            infoMessage.setText(ExitInfo.INFORMATION);
            usageExample.setText(ExitInfo.USAGE);
            output.setText(ExitInfo.OUTPUT);
            break;
        case InfoCommand.FIND_INFO :
            commandWord.setText(FindInfo.COMMAND_WORD);
            infoMessage.setText(FindInfo.INFORMATION);
            usageExample.setText(FindInfo.USAGE);
            output.setText(FindInfo.OUTPUT);
            break;
        case InfoCommand.HELP_INFO :
            commandWord.setText(HelpInfo.COMMAND_WORD);
            infoMessage.setText(HelpInfo.INFORMATION);
            usageExample.setText(HelpInfo.USAGE);
            output.setText(HelpInfo.OUTPUT);
            break;
        case InfoCommand.INFO_INFO :
            commandWord.setText(InfoInfo.COMMAND_WORD);
            infoMessage.setText(InfoInfo.INFORMATION);
            usageExample.setText(InfoInfo.USAGE);
            output.setText(InfoInfo.OUTPUT);
            break;
        case InfoCommand.LIST_INFO :
            commandWord.setText(ListInfo.COMMAND_WORD);
            infoMessage.setText(ListInfo.INFORMATION);
            usageExample.setText(ListInfo.USAGE);
            output.setText(ListInfo.OUTPUT);
            break;
        case InfoCommand.RECOMMEND_INFO :
            commandWord.setText(RecommendInfo.COMMAND_WORD);
            infoMessage.setText(RecommendInfo.INFORMATION);
            usageExample.setText(RecommendInfo.USAGE);
            output.setText(RecommendInfo.OUTPUT);
            break;
        case InfoCommand.SORT_INFO :
            commandWord.setText(SortInfo.COMMAND_WORD);
            infoMessage.setText(SortInfo.INFORMATION);
            usageExample.setText(SortInfo.USAGE);
            output.setText(SortInfo.OUTPUT);
            break;
        }
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the info window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Focuses on the info window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
