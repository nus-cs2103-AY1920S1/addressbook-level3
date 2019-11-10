package seedu.savenus.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import seedu.savenus.commons.core.LogsCenter;
import seedu.savenus.logic.commands.InfoCommand;
import seedu.savenus.model.info.AddInfo;
import seedu.savenus.model.info.AliasInfo;
import seedu.savenus.model.info.AutoSortInfo;
import seedu.savenus.model.info.BudgetInfo;
import seedu.savenus.model.info.BuyInfo;
import seedu.savenus.model.info.ClearInfo;
import seedu.savenus.model.info.CustomSortInfo;
import seedu.savenus.model.info.DefaultInfo;
import seedu.savenus.model.info.DeleteInfo;
import seedu.savenus.model.info.DislikeInfo;
import seedu.savenus.model.info.EditInfo;
import seedu.savenus.model.info.ExitInfo;
import seedu.savenus.model.info.FilterInfo;
import seedu.savenus.model.info.FindInfo;
import seedu.savenus.model.info.HelpInfo;
import seedu.savenus.model.info.HistoryInfo;
import seedu.savenus.model.info.InfoInfo;
import seedu.savenus.model.info.LikeInfo;
import seedu.savenus.model.info.ListInfo;
import seedu.savenus.model.info.MakeSortInfo;
import seedu.savenus.model.info.RecommendInfo;
import seedu.savenus.model.info.RemoveDislikeInfo;
import seedu.savenus.model.info.RemoveLikeInfo;
import seedu.savenus.model.info.SaveInfo;
import seedu.savenus.model.info.ShowInfo;
import seedu.savenus.model.info.SortInfo;
import seedu.savenus.model.info.ThemeInfo;
import seedu.savenus.model.info.TopUpInfo;
import seedu.savenus.model.info.ViewSortInfo;
import seedu.savenus.model.info.WithdrawInfo;

//@@author robytanama
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
     * Sets the info window theme to light theme.
     */
    public void setToLightTheme() {
        getRoot().getScene().getStylesheets().clear();
        getRoot().getScene().setUserAgentStylesheet(null);
        getRoot().getScene().getStylesheets()
                .add(MainWindow.LIGHT_THEME_CSS);
    }

    /**
     * Sets the info window theme to dark theme.
     */
    public void setToDarkTheme() {
        getRoot().getScene().getStylesheets().clear();
        getRoot().getScene().setUserAgentStylesheet(null);
        getRoot().getScene().getStylesheets()
                .add(MainWindow.DARK_THEME_CSS);
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
        case InfoCommand.ALIAS_INFO :
            commandWord.setText(AliasInfo.COMMAND_WORD);
            infoMessage.setText(AliasInfo.INFORMATION);
            usageExample.setText(AliasInfo.USAGE);
            output.setText(AliasInfo.OUTPUT);
            break;
        case InfoCommand.AUTO_SORT_INFO :
            commandWord.setText(AutoSortInfo.COMMAND_WORD);
            infoMessage.setText(AutoSortInfo.INFORMATION);
            usageExample.setText(AutoSortInfo.USAGE);
            output.setText(AutoSortInfo.OUTPUT);
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
        case InfoCommand.CUSTOM_SORT_INFO :
            commandWord.setText(CustomSortInfo.COMMAND_WORD);
            infoMessage.setText(CustomSortInfo.INFORMATION);
            usageExample.setText(CustomSortInfo.USAGE);
            output.setText(CustomSortInfo.OUTPUT);
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
        case InfoCommand.DISLIKE_INFO :
            commandWord.setText(DislikeInfo.COMMAND_WORD);
            infoMessage.setText(DislikeInfo.INFORMATION);
            usageExample.setText(DislikeInfo.USAGE);
            output.setText(DislikeInfo.OUTPUT);
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
        case InfoCommand.FILTER_INFO:
            commandWord.setText(FilterInfo.COMMAND_WORD);
            infoMessage.setText(FilterInfo.INFORMATION);
            usageExample.setText(FilterInfo.USAGE);
            output.setText(FilterInfo.OUTPUT);
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
        case InfoCommand.HISTORY_INFO :
            commandWord.setText(HistoryInfo.COMMAND_WORD);
            infoMessage.setText(HistoryInfo.INFORMATION);
            usageExample.setText(HistoryInfo.USAGE);
            output.setText(HistoryInfo.OUTPUT);
            break;
        case InfoCommand.INFO_INFO :
            commandWord.setText(InfoInfo.COMMAND_WORD);
            infoMessage.setText(InfoInfo.INFORMATION);
            usageExample.setText(InfoInfo.USAGE);
            output.setText(InfoInfo.OUTPUT);
            break;
        case InfoCommand.LIKE_INFO :
            commandWord.setText(LikeInfo.COMMAND_WORD);
            infoMessage.setText(LikeInfo.INFORMATION);
            usageExample.setText(LikeInfo.USAGE);
            output.setText(LikeInfo.OUTPUT);
            break;
        case InfoCommand.LIST_INFO :
            commandWord.setText(ListInfo.COMMAND_WORD);
            infoMessage.setText(ListInfo.INFORMATION);
            usageExample.setText(ListInfo.USAGE);
            output.setText(ListInfo.OUTPUT);
            break;
        case InfoCommand.MAKE_SORT_INFO :
            commandWord.setText(MakeSortInfo.COMMAND_WORD);
            infoMessage.setText(MakeSortInfo.INFORMATION);
            usageExample.setText(MakeSortInfo.USAGE);
            output.setText(MakeSortInfo.OUTPUT);
            break;
        case InfoCommand.RECOMMEND_INFO :
            commandWord.setText(RecommendInfo.COMMAND_WORD);
            infoMessage.setText(RecommendInfo.INFORMATION);
            usageExample.setText(RecommendInfo.USAGE);
            output.setText(RecommendInfo.OUTPUT);
            break;
        case InfoCommand.REMOVEDISLIKE_INFO :
            commandWord.setText(RemoveDislikeInfo.COMMAND_WORD);
            infoMessage.setText(RemoveDislikeInfo.INFORMATION);
            usageExample.setText(RemoveDislikeInfo.USAGE);
            output.setText(RemoveDislikeInfo.OUTPUT);
            break;
        case InfoCommand.REMOVELIKE_INFO :
            commandWord.setText(RemoveLikeInfo.COMMAND_WORD);
            infoMessage.setText(RemoveLikeInfo.INFORMATION);
            usageExample.setText(RemoveLikeInfo.USAGE);
            output.setText(RemoveLikeInfo.OUTPUT);
            break;
        case InfoCommand.SAVE_INFO:
            commandWord.setText(SaveInfo.COMMAND_WORD);
            infoMessage.setText(SaveInfo.INFORMATION);
            usageExample.setText(SaveInfo.USAGE);
            output.setText(SaveInfo.OUTPUT);
            break;
        case InfoCommand.SORT_INFO :
            commandWord.setText(SortInfo.COMMAND_WORD);
            infoMessage.setText(SortInfo.INFORMATION);
            usageExample.setText(SortInfo.USAGE);
            output.setText(SortInfo.OUTPUT);
            break;
        case InfoCommand.THEME_INFO :
            commandWord.setText(ThemeInfo.COMMAND_WORD);
            infoMessage.setText(ThemeInfo.INFORMATION);
            usageExample.setText(ThemeInfo.USAGE);
            output.setText(ThemeInfo.OUTPUT);
            break;
        case InfoCommand.TOP_UP_INFO :
            commandWord.setText(TopUpInfo.COMMAND_WORD);
            infoMessage.setText(TopUpInfo.INFORMATION);
            usageExample.setText(TopUpInfo.USAGE);
            output.setText(TopUpInfo.OUTPUT);
            break;
        case InfoCommand.VIEW_SORT_INFO :
            commandWord.setText(ViewSortInfo.COMMAND_WORD);
            infoMessage.setText(ViewSortInfo.INFORMATION);
            usageExample.setText(ViewSortInfo.USAGE);
            output.setText(ViewSortInfo.OUTPUT);
            break;
        case InfoCommand.WITHDRAW_INFO :
            commandWord.setText(WithdrawInfo.COMMAND_WORD);
            infoMessage.setText(WithdrawInfo.INFORMATION);
            usageExample.setText(WithdrawInfo.USAGE);
            output.setText(WithdrawInfo.OUTPUT);
            break;
        case InfoCommand.SHOW_INFO :
            commandWord.setText(ShowInfo.COMMAND_WORD);
            infoMessage.setText(ShowInfo.INFORMATION);
            usageExample.setText(ShowInfo.USAGE);
            output.setText(ShowInfo.OUTPUT);
            break;
        default :
            commandWord.setText("YOU ARE NOT SUP  POSED TO SEE TH IS PAG  E");
            infoMessage.setText("PLEA SE   EXIT TH E INFO SCR EEN");
            usageExample.setText("DO IT N O W                    BEFORE");
            output.setText(".");
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
