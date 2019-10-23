package seedu.address.appmanager;

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
import seedu.address.model.appsettings.AppSettings;
import seedu.address.model.card.Card;
import seedu.address.model.card.FormattedHint;
import seedu.address.model.globalstatistics.GlobalStatistics;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.model.wordbank.WordBank;
import seedu.address.model.wordbankstatslist.WordBankStatisticsList;
import seedu.address.statistics.GameStatistics;
import seedu.address.statistics.WordBankStatistics;


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
    void setTimerDisplayCallBack() {
    }

    @Test
    void setHintDisplayCallBack() {
    }

    @Test
    void setMainWindowExecuteCallBack() {
    }

    @Test
    void setQuestionDisplayCallBack() {
    }

    private class LogicStub implements Logic {

        @Override
        public CommandResult execute(String commandText) throws CommandException, ParseException {
            return null;
        }

        @Override
        public ReadOnlyWordBank getAddressBook() {
            return null;
        }

        @Override
        public ObservableList<Card> getFilteredPersonList() {
            return null;
        }

        @Override
        public ObservableList<WordBank> getFilteredWordBankList() {
            return null;
        }

        @Override
        public Path getAddressBookFilePath() {
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
        public void saveUpdatedWbStatistics(GameStatistics gameStats) throws CommandException {

        }

        @Override
        public void incrementPlay() throws CommandException {

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
            return null;
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
    }
}
