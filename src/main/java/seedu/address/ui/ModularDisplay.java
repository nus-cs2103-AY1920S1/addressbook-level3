package seedu.address.ui;

import javafx.scene.layout.StackPane;

import seedu.address.model.globalstatistics.GlobalStatistics;
import seedu.address.model.wordbankstatslist.WordBankStatisticsList;
import seedu.address.appmanager.AppManager;
import seedu.address.statistics.GameStatistics;
import seedu.address.statistics.WordBankStatistics;
import seedu.address.ui.layouts.TwoSplitColumnLayout;
import seedu.address.ui.layouts.TwoSplitRowLayout;
import seedu.address.ui.modules.BankLabelPanel;
import seedu.address.ui.modules.CardListPanel;
import seedu.address.ui.modules.GameResultPanel;
import seedu.address.ui.modules.LoadBankPanel;
import seedu.address.ui.modules.MainTitlePanel;
import seedu.address.ui.modules.TitleScreenPanel;
import seedu.address.ui.modules.WordBankStatisticsPanel;

import java.util.Random;

/**
 * Displays the screen for Dukemon.
 */
public class ModularDisplay {

    //Layouts for non single-pane displays
    private TwoSplitColumnLayout twoSplitColumnLayout;
    private TwoSplitRowLayout twoSplitRowLayout;

    //Modules
    private BankLabelPanel bankLabelPanel;
    private final LoadBankPanel loadBankPanel;
    private final TitleScreenPanel titleScreenPanel;
    private final AppManager appManager;

    /**
     * Changes the screen.
     *
     * @param appManager GameManager who will render lists.
     */
    public ModularDisplay(AppManager appManager) {
        loadBankPanel = new LoadBankPanel(appManager.getFilteredWordBankList());
        titleScreenPanel = new TitleScreenPanel();
        this.appManager = appManager;
    }

    /**
     * Initially displays loading.
     *
     * @param paneToDisplay The view to change.
     */
    public void swapToLoadDisplay(StackPane paneToDisplay) {
        twoSplitRowLayout = new TwoSplitRowLayout();
        twoSplitColumnLayout = new TwoSplitColumnLayout();

        // twoSplitRowLayout.addToTopPane(titleScreenPanel.getRoot());
        // twoSplitRowLayout.addToBottomPane(globalStatsPlaceholder.getRoot());
        WordBankStatisticsList wbStatsList = appManager.getActiveWordBankStatisticsList();
        GlobalStatistics globalStats = appManager.getGlobalStatistics();
        twoSplitColumnLayout.addToLeftPane(new MainTitlePanel(
                globalStats,
                wbStatsList.getMostPlayedWordBankStatistics(),
                new Random().nextInt(AvatarImage.TOTAL_NUM) + 1).getRoot());
        // todo avatar should depend on user prefs
        twoSplitColumnLayout.addToRightPane(loadBankPanel.getRoot());
        paneToDisplay.getChildren().add(twoSplitColumnLayout.getRoot());
    }

    /**
     * Changes to the bank preview screen where the user can see the cards in his word bank.
     *
     * @param paneToDisplay The view to change.
     */
    public void swapToBankDisplay(StackPane paneToDisplay) {
        bankLabelPanel = new BankLabelPanel(appManager.getSelectedWbName());
        //  TitleScreenPanel localStatsPlaceholder = new TitleScreenPanel();
        twoSplitRowLayout = new TwoSplitRowLayout();
        twoSplitColumnLayout = new TwoSplitColumnLayout();

        //  twoSplitRowLayout.addToTopPane(bankLabelPanel.getRoot ());
        //  twoSplitRowLayout.addToBottomPane(localStatsPlaceholder.getRoot());
        //  twoSplitColumnLayout.addToLeftPane(twoSplitRowLayout.getRoot());
        twoSplitColumnLayout.addToLeftPane(
                new WordBankStatisticsPanel(appManager.getActiveWordBankStatistics(),
                        appManager.getActiveWordBank()).getRoot());
        twoSplitColumnLayout.addToRightPane(
                new CardListPanel(appManager.getFilteredPersonList()).getRoot());
        paneToDisplay.getChildren().add(twoSplitColumnLayout.getRoot());
    }

    /**
     * Changes back to home display.
     *
     * @param paneToDisplay The view to change.
     */
    public void swapToHome(StackPane paneToDisplay) {
        paneToDisplay.getChildren().clear();
        paneToDisplay.getChildren().add(titleScreenPanel.getRoot());
    }

    /**
     * Changes to the word list.
     *
     * @param paneToDisplay The view to change.
     */
    public void swapToList(StackPane paneToDisplay) {
        paneToDisplay.getChildren().clear();
        paneToDisplay.getChildren().add(new CardListPanel(appManager.getFilteredPersonList()).getRoot());
    }

    /**
     * Changes to the game result.
     *
     * @param paneToDisplay The view to change.
     * @param gameStatistics The statistics to be shown in the game result panel.
     * @param wbStatistics The overall word bank statistics.
     */
    public void swapToGameResult(StackPane paneToDisplay, GameStatistics gameStatistics,
                                 WordBankStatistics wbStatistics) {
        paneToDisplay.getChildren().clear();
        paneToDisplay.getChildren().add(new GameResultPanel(gameStatistics, wbStatistics).getRoot());
    }

    /**
     * Changes to list the word banks.
     *
     * @param paneToDisplay The view to change.
     */
    public void swapToBanks(StackPane paneToDisplay) {
        paneToDisplay.getChildren().clear();
        paneToDisplay.getChildren().add(loadBankPanel.getRoot());
    }

}
