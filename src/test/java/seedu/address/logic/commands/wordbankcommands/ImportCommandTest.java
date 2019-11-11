package seedu.address.logic.commands.wordbankcommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
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


class ImportCommandTest {
    private static String validWordBankName = "testBank";
    private static File validDirectory = Paths.get("data", "ImportCommandTest").toFile();

    @Test
    void constructor_nullWordBankName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ImportCommand(null, validDirectory));
    }

    @Test
    void constructor_nullDirectory_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ImportCommand(validWordBankName, null));
    }

    @Test
    void execute_importToModel_importSuccessful() throws Exception {
        ModelStub modelStub = new ModelStub();

        CommandResult commandResult = new ImportCommand(validWordBankName, validDirectory).execute(modelStub);

        assertEquals(String.format(
                String.format(ImportCommand.MESSAGE_IMPORT_CARD_SUCCESS, validWordBankName, validDirectory)),
                commandResult.getFeedbackToUser());
    }

    @Test
    void equals() {
        String validWordBankName2 = "testBank2";
        File validDirectory2 = Paths.get("data", "ConfigUtilTest").toFile();

        ImportCommand importTest1 = new ImportCommand(validWordBankName, validDirectory);
        ImportCommand importTest2 = new ImportCommand(validWordBankName2, validDirectory2);

        // same object -> returns true
        assertEquals(importTest1, importTest1);

        // same values -> returns true
        ImportCommand importTest3 = new ImportCommand(validWordBankName, validDirectory);
        assertEquals(importTest1, importTest3);

        // different types -> returns false
        assertNotEquals(1, importTest1);

        // null -> returns false
        assertNotEquals(null, importTest1);

        // different Command -> returns false
        assertNotEquals(importTest1, importTest2);
    }

    private class ModelStub implements Model {

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {

        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
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
        public Path getWordBankFilePath() {
            return null;
        }

        @Override
        public void setWordBankFilePath(Path addressBookFilePath) {

        }

        @Override
        public void setCurrentWordBank(ReadOnlyWordBank currentWordBank) {

        }

        @Override
        public void updateWordBank(String name) {

        }

        @Override
        public WordBankStatistics getWordBankStatistics() {
            return null;
        }

        @Override
        public void setWordBankStatistics(WordBankStatistics wordBankStats) {

        }

        @Override
        public void clearWordBankStatistics() {

        }

        @Override
        public void clearWordBank() {

        }

        @Override
        public ReadOnlyWordBank getCurrentWordBank() {
            return null;
        }

        @Override
        public boolean hasCard(Card card) {
            return false;
        }

        @Override
        public void deleteCard(Card target) {

        }

        @Override
        public void addCard(Card card) {

        }

        @Override
        public void setCard(Card target, Card editedCard) {

        }

        @Override
        public ObservableList<Card> getFilteredCardList() {
            return null;
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
        public GlobalStatistics getGlobalStatistics() {
            return null;
        }

        @Override
        public void updateFilteredCardList(Predicate<Card> predicate) {

        }

        @Override
        public void setGame(Game game) {

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
        public DifficultyEnum getCurrentGameDifficulty() {
            return null;
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
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public DifficultyEnum getDefaultDifficulty() {
            return null;
        }

        @Override
        public void setDefaultTheme(ThemeEnum themeEnum) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void clearActiveWordBankStatistics() {
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
    }
}
