package seedu.address.appmanager;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.TypicalCards.SINGLE_LETTER_CARD;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.util.AutoFillAction;
import seedu.address.logic.util.ModeEnum;
import seedu.address.model.Model;
import seedu.address.model.appsettings.AppSettings;
import seedu.address.model.card.Card;
import seedu.address.model.card.FormattedHint;
import seedu.address.model.globalstatistics.GlobalStatistics;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.model.wordbank.WordBank;
import seedu.address.model.wordbankstats.WordBankStatistics;
import seedu.address.model.wordbankstatslist.WordBankStatisticsList;
import seedu.address.statistics.GameStatistics;
import seedu.address.statistics.GameStatisticsBuilder;
import seedu.address.storage.Storage;

class AppManagerTest {

    private AppManager dummyAppManager;

    @BeforeEach
    void setUp() {
        dummyAppManager = new AppManager(new LogicStub());
    }

    @Test
    void setGuiSettings() {
    }

    @Test
    void execute() {
    }

    @Test
    void getSelectedWbName() {
    }

    @Test
    void getLogic() {
        LogicStub logicStub = new LogicStub();
        dummyAppManager = new AppManager(logicStub);
        assertTrue(dummyAppManager.getLogic().equals(logicStub));
    }

    @Test
    void constructor_nullLogic_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AppManager(null));
    }

    @Test
    void getActiveWordBank() {
    }

    @Test
    void getAppSettings() {
    }

    @Test
    void getGameStatistics() {
    }

    @Test
    void initGameStatistics() {
        GameStatisticsBuilder tempStatisticsBuilder = new GameStatisticsBuilder("dummyStatistics");
        dummyAppManager.initGameStatistics("dummyStatistics");
        try {
            final Field field = dummyAppManager.getClass().getDeclaredField("gameStatisticsBuilder");
            field.setAccessible(true);
            GameStatisticsBuilder newlyInitializedBuilder = (GameStatisticsBuilder) field.get(dummyAppManager);
            assertTrue(tempStatisticsBuilder.getTitle()
                    .equals(newlyInitializedBuilder.getTitle()));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
    }

    @Test
    void getActiveWordBankStatistics() {
    }

    @Test
    void getActiveWordBankStatisticsList() {
    }

    @Test
    void getGlobalStatistics() {
    }

    @Test
    void getFilteredPersonList() {
    }

    @Test
    void getFilteredWordBankList() {
    }

    @Test
    void getGuiSettings() {
    }

    @Test
    void getAddressBookFilePath() {
    }

    @Test
    void registerTimerDisplayCallBack() {
        TimerDisplayStub timerDisplayStub = new TimerDisplayStub();
        AppManager.TimerDisplayCallBack dummyCallBack = timerDisplayStub::updateTimerDisplay;
        dummyAppManager.registerTimerDisplayCallBack(dummyCallBack);
        try {
            final Field field = dummyAppManager.getClass().getDeclaredField("timerDisplayCallBack");
            field.setAccessible(true);
            assertTrue(dummyCallBack.equals(field.get(dummyAppManager)));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
    }

    @Test
    void registerTimerDisplayCallBack_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> dummyAppManager.registerTimerDisplayCallBack(null));
    }

    @Test
    void registerHintDisplayCallBack() {
        HintDisplayStub hintDisplayStub = new HintDisplayStub();
        AppManager.HintDisplayCallBack dummyCallBack = hintDisplayStub::updateHintDisplay;
        dummyAppManager.registerHintDisplayCallBack(dummyCallBack);
        try {
            final Field field = dummyAppManager.getClass().getDeclaredField("hintDisplayCallBack");
            field.setAccessible(true);
            assertTrue(dummyCallBack.equals(field.get(dummyAppManager)));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
    }

    @Test
    void registerHintDisplayCallBack_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> dummyAppManager.registerHintDisplayCallBack(null));
    }

    @Test
    void registerMainWindowExecuteCallBack() {
        MainWindowStub mainWindowStub = new MainWindowStub();
        AppManager.MainWindowExecuteCallBack dummyCallBack = mainWindowStub::execute;
        dummyAppManager.registerMainWindowExecuteCallBack(dummyCallBack);
        try {
            final Field field = dummyAppManager.getClass().getDeclaredField("mainWindowExecuteCallBack");
            field.setAccessible(true);
            assertTrue(dummyCallBack.equals(field.get(dummyAppManager)));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
    }

    @Test
    void registerMainWindowExecuteCallBack_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> dummyAppManager.registerMainWindowExecuteCallBack(null));
    }

    @Test
    void registerQuestionDisplayCallBack() {
        QuestionDisplayStub questionDisplayStub = new QuestionDisplayStub();
        AppManager.QuestionDisplayCallBack dummyCallBack = questionDisplayStub::updateQuestionDisplay;
        dummyAppManager.registerQuestionDisplayCallBack(dummyCallBack);
        try {
            final Field field = dummyAppManager.getClass().getDeclaredField("questionDisplayCallBack");
            field.setAccessible(true);
            assertTrue(dummyCallBack.equals(field.get(dummyAppManager)));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
    }

    @Test
    void registerQuestionDisplayCallBack_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> dummyAppManager.registerQuestionDisplayCallBack(null));
    }

    private class LogicStub implements Logic {

        @Override
        public CommandResult execute(String commandText) throws CommandException, ParseException {
            return null;
        }

        @Override
        public ReadOnlyWordBank getCurrentWordBank() {
            return null;
        }

        @Override
        public ObservableList<Card> getFilteredCardList() {
            return null;
        }

        @Override
        public ObservableList<WordBank> getFilteredWordBankList() {
            return null;
        }

        @Override
        public Path getWordBanksFilePath() {
            return null;
        }

        @Override
        public GuiSettings getGuiSettings() {
            return null;
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {

        }

        @Override
        public void updateStatistics(GameStatistics gameStats) throws CommandException {

        }

        @Override
        public void updateRevisionBank(GameStatistics gameStats) {

        }

        @Override
        public WordBankStatistics getActiveWordBankStatistics() {
            return null;
        }

        @Override
        public WordBankStatisticsList getWordBankStatisticsList() {
            return null;
        }

        @Override
        public GlobalStatistics getGlobalStatistics() {
            return null;
        }

        @Override
        public ReadOnlyWordBank getActiveWordBank() {
            return null;
        }

        @Override
        public long getTimeAllowedPerQuestion() {
            return 0;
        }

        @Override
        public AppSettings getAppSettings() {
            return null;
        }

        @Override
        public String getCurrentQuestion() {
            return null;
        }

        @Override
        public FormattedHint getHintFormatFromCurrentGame() {
            return SINGLE_LETTER_CARD.clone().getFormattedHint();
        }

        @Override
        public int getHintFormatSizeFromCurrentGame() {
            return 0;
        }

        @Override
        public boolean hintsAreEnabled() {
            return false;
        }

        @Override
        public List<AutoFillAction> getMenuItems(String text) {
            return null;
        }

        @Override
        public ModeEnum getMode() {
            return null;
        }

        @Override
        public List<ModeEnum> getModes() {
            return null;
        }

        @Override
        public Storage getStorage() {
            return null;
        }

        @Override
        public Model getModel() {
            return null;
        }
    }

    private class HintDisplayStub {
        private boolean isUpdatedFromGameTimer = false;

        void updateHintDisplay(String message) {
            this.isUpdatedFromGameTimer = true;
        }
    }

    // Stub class for TimerDisplay component of UI
    private class TimerDisplayStub {
        private boolean isUpdatedFromGameTimer = false;

        void updateTimerDisplay(String timerMessage, long timeLeft, long totalTimeGiven) {
            this.isUpdatedFromGameTimer = true;
        }
    }

    // Stub class for MainWindow component of UI
    private class MainWindowStub {
        private boolean isExecutedFromGameTimer = false;

        CommandResult execute(String commandText) {
            isExecutedFromGameTimer = true;
            return null;
        }
    }

    private class QuestionDisplayStub {
        private boolean isExecutedFromAppManager = false;

        void updateQuestionDisplay(String message) {
            isExecutedFromAppManager = true;
        }
    }
}
