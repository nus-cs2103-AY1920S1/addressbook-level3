package seedu.address.logic.commands.settingscommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.settingcommands.DifficultyCommand;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.appsettings.AppSettings;
import seedu.address.model.appsettings.DifficultyEnum;
import seedu.address.model.appsettings.ThemeEnum;
import seedu.address.model.card.Card;
import seedu.address.model.card.FormattedHint;
import seedu.address.model.game.Game;
import seedu.address.model.globalstatistics.GlobalStatistics;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.model.wordbank.WordBank;
import seedu.address.model.wordbanklist.WordBankList;
import seedu.address.model.wordbankstats.WordBankStatistics;
import seedu.address.model.wordbankstatslist.WordBankStatisticsList;

public class DifficultyCommandTest {

    @Test
    public void constructor_nullDifficulty_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DifficultyCommand(null));
    }

    @Test
    public void execute_properArgument_success() {
        ModelStub stub = new ModelStub();
        DifficultyCommand testCommand = new DifficultyCommand(DifficultyEnum.HARD);
        assertEquals(DifficultyEnum.EASY, stub.getDefaultDifficulty());
        CommandResult result = testCommand.execute(stub);
        assertEquals(DifficultyEnum.HARD, stub.getDefaultDifficulty());
        String expectedString = String.format("Difficulty is now set to: %s (%.1f seconds)",
                DifficultyEnum.HARD,
                DifficultyEnum.HARD.getTimeAllowedPerQuestion() / 1000.0);
        assertEquals(expectedString, result.getFeedbackToUser());
    }

    private class ModelStub implements Model {

        private DifficultyEnum testDifficulty = DifficultyEnum.EASY;

        @Override
        public void setGame(Game game) {
            throw new AssertionError("This method should not be called.");
        }

        public Game getGame() {
            return null;
        }

        @Override
        public boolean hasWordBank(String name) {
            return false;
        }

        @Override
        public WordBank getWordBankFromName(String name) {
            return null;
        }

        @Override
        public boolean gameIsOver() {
            return false;
        }

        @Override
        public boolean getHasBank() {
            return false;
        }

        @Override
        public AppSettings getAppSettings() {
            return null;
        }

        @Override
        public Path getAppSettingsFilePath() {
            return null;
        }

        @Override
        public void setDefaultDifficulty(DifficultyEnum difficultyEnum) {
            this.testDifficulty = difficultyEnum;
        }

        @Override
        public DifficultyEnum getDefaultDifficulty() {
            return testDifficulty;
        }

        @Override
        public DifficultyEnum getCurrentGameDifficulty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDefaultTheme(ThemeEnum themeEnum) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ThemeEnum getDefaultTheme() {
            return null;
        }

        @Override
        public void setHintsEnabled(boolean enabled) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public long getTimeAllowedPerQuestion() {
            return 0;
        }

        @Override
        public FormattedHint getFormattedHintFromCurrGame() {
            return null;
        }

        @Override
        public int getHintFormatSizeFromCurrentGame() {
            return 0;
        }

        @Override
        public boolean getHintsEnabled() {
            return false;
        }

        @Override
        public void setAvatarId(int avatarId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getAvatarId() {
            return 0;
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public void clearActiveWordBankStatistics() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getWordBankFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setWordBankFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCurrentWordBank(ReadOnlyWordBank currenWordBank) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCard(Card card) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public void updateWordBank(String name) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public WordBankStatistics getWordBankStatistics() {
            return null;
        }

        @Override
        public void setWordBankStatistics(WordBankStatistics wordBankStats) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void clearWordBankStatistics() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void clearWordBank() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyWordBank getCurrentWordBank() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCard(Card card) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCard(Card target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCard(Card target, Card editedCard) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Card> getFilteredCardList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public WordBankList getWordBankList() {
            return null;
        }

        @Override
        public WordBankStatisticsList getWordBankStatisticsList() {
            return null;
        }

        @Override
        public void updateFilteredCardList(Predicate<Card> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GlobalStatistics getGlobalStatistics() {
            throw new AssertionError("This method should not be called.");
        }
    }

}

