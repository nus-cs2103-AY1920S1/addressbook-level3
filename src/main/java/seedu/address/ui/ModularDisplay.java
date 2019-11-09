package seedu.address.ui;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Random;

import javafx.scene.layout.StackPane;
import seedu.address.appmanager.AppManager;
import seedu.address.model.globalstatistics.GlobalStatistics;
import seedu.address.model.wordbankstats.WordBankStatistics;
import seedu.address.model.wordbankstatslist.WordBankStatisticsList;
import seedu.address.statistics.GameStatistics;
import seedu.address.ui.layouts.TwoSplitColumnLayout;
import seedu.address.ui.layouts.TwoSplitRowLayout;
import seedu.address.ui.modules.CardListPanel;
import seedu.address.ui.modules.GameResultPanel;
import seedu.address.ui.modules.HintLabel;
import seedu.address.ui.modules.LoadBankPanel;
import seedu.address.ui.modules.MainTitlePanel;
import seedu.address.ui.modules.QuestionLabel;
import seedu.address.ui.modules.SettingsPanel;
import seedu.address.ui.modules.WordBankCard;
import seedu.address.ui.modules.WordBankStatisticsPanel;


/**
 * Displays the screen for Dukemon.
 */
public class ModularDisplay {
    //Overarching AppManager
    private final AppManager appManager;

    //Layouts for non single-pane displays
    private TwoSplitColumnLayout twoSplitColumnLayout;
    private TwoSplitRowLayout twoSplitRowLayout;

    //Modules
    private final LoadBankPanel loadBankPanel;
    private final SettingsPanel settingsPanel;

    //For the game
    private QuestionLabel questionLabel;
    private HintLabel hintLabel;

    /**
     * Changes the screen.
     *
     * @param appManager GameManager who will render lists.
     */
    ModularDisplay(AppManager appManager) {
        loadBankPanel = new LoadBankPanel(appManager.getFilteredWordBankList());
        settingsPanel = new SettingsPanel(appManager.getAppSettings());
        questionLabel = new QuestionLabel();
        hintLabel = new HintLabel();
        this.appManager = appManager;
    }

    /**
     * Initially displays loading.
     *
     * @param paneToDisplay The view to change.
     */
    public void swapToHomeDisplay(StackPane paneToDisplay) {
        twoSplitRowLayout = new TwoSplitRowLayout();
        twoSplitColumnLayout = new TwoSplitColumnLayout();
        int avatarId = appManager.getAppSettings().getAvatarId();

        WordBankStatisticsList wbStatsList = appManager.getActiveWordBankStatisticsList();
        GlobalStatistics globalStats = appManager.getGlobalStatistics();
        twoSplitColumnLayout.addToLeftPane(new MainTitlePanel(
                globalStats,
                wbStatsList.getMostPlayedWordBankStatistics(),
                avatarId == 0
                        ? new Random().nextInt(AvatarImageUtil.TOTAL_NUM) + 1
                        : avatarId).getRoot());
        twoSplitColumnLayout.addToRightPane(loadBankPanel.getRoot());
        paneToDisplay.getChildren().add(twoSplitColumnLayout.getRoot());
    }

    /**
     * Changes to the bank preview screen where the user can see the cards in his word bank.
     *
     * @param paneToDisplay The view to change.
     */
    public void swapToOpenDisplay(StackPane paneToDisplay) {
        twoSplitRowLayout = new TwoSplitRowLayout();
        twoSplitColumnLayout = new TwoSplitColumnLayout();

        twoSplitColumnLayout.addToLeftPane(
                new WordBankStatisticsPanel(appManager.getActiveWordBankStatistics(),
                        appManager.getActiveWordBank()).getRoot());
        twoSplitColumnLayout.addToRightPane(
                new CardListPanel(appManager.getFilteredCardList()).getRoot());
        paneToDisplay.getChildren().add(twoSplitColumnLayout.getRoot());
    }

    /**
     * Changes to the game result.
     *
     * @param paneToDisplay  The view to change.
     * @param gameStatistics The statistics to be shown in the game result panel.
     * @param wbStatistics   The overall word bank statistics.
     */
    public void swapToGameResult(StackPane paneToDisplay, GameStatistics gameStatistics,
                                 WordBankStatistics wbStatistics) {
        paneToDisplay.getChildren().clear();
        if (gameStatistics == null || wbStatistics == null) {
            System.out.println("YOOOOO somthings null here");
        } else {
            paneToDisplay.getChildren().add(new GameResultPanel(gameStatistics, wbStatistics).getRoot());
        }

    }

    /**
     * Changes to the settings page.
     *
     * @param paneToDisplay
     */
    public void swapToSettings(StackPane paneToDisplay) {
        paneToDisplay.getChildren().clear();
        settingsPanel.updateSettings();
        paneToDisplay.getChildren().add(settingsPanel.getRoot());
    }


    /**
     * Creates and returns a {@code TwoSplitRowLayout} that contains the question and hint label.
     *
     * @return
     */
    public TwoSplitRowLayout createQuestionHints() {
        twoSplitRowLayout = new TwoSplitRowLayout();
        twoSplitRowLayout.addToTopPane(questionLabel.getRoot());
        twoSplitRowLayout.addToBottomPane(hintLabel.getRoot());
        return twoSplitRowLayout;
    }

    /**
     * Updates the question label and reconstructs the TwoSplitRowDisplay with
     * {@code question} on the {@code StackPaneToDisplay}.
     */
    public void updateQuestion(String question, StackPane paneToDisplay) {
        questionLabel.updateQuestionLabel(question);
        hintLabel.updateHintLabel("No Hints To Display");
        paneToDisplay.getChildren().clear();
        paneToDisplay.getChildren().add(createQuestionHints().getRoot());
    }

    /**
     * Updates the hint label and reconstructs the TwoSplitRowDisplay with
     * {@code hint} on the {@code StackPaneToDisplay}.
     */
    public void updateHint(String hint, StackPane paneToDisplay) {
        hintLabel.updateHintLabel(hint);
        paneToDisplay.getChildren().clear();
        paneToDisplay.getChildren().add(createQuestionHints().getRoot());
    }

    /**
     * Registers a method that will be called by the ModularDisplay to simulate an Import or Export command as though
     * it were a user.
     *
     * @param importCommandExecutor for import.
     * @param exportCommandExecutor for export.
     */
    void registerDragAndDropCallBack(LoadBankPanel.LoadBankPanelDisplayExecuteCallBack importCommandExecutor,
                                     WordBankCard.WordBankCardExecuteCallBack exportCommandExecutor) {
        requireAllNonNull(importCommandExecutor, exportCommandExecutor);
        loadBankPanel.registerDragAndDropCallBack(importCommandExecutor, exportCommandExecutor);
    }

    void initialiseFilePath(String dataFilePath) {
        loadBankPanel.initialiseFilePath(dataFilePath);
    }
}
