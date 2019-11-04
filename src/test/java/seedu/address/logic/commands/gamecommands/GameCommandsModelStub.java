package seedu.address.logic.commands.gamecommands;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
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

/**
 * Utility class that serves as a stub for the Model dependency that is required for the testing of GameCommands.
 */
class GameCommandsModelStub implements Model {

    private ReadOnlyWordBank wordBank;
    private Game game;

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
    public void setCurrentWordBank(ReadOnlyWordBank currenWordBank) {
        this.wordBank = currenWordBank;
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
        return wordBank;
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
        this.game = game;
    }

    @Override
    public boolean gameIsOver() {
        return game == null || game.isOver();
    }

    @Override
    public boolean getHasBank() {
        return false;
    }

    @Override
    public Game getGame() {
        return game;
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
        return game.getCurrentGameDifficulty();
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

    }

    @Override
    public DifficultyEnum getDefaultDifficulty() {
        return DifficultyEnum.EASY;
    }

    @Override
    public void setDefaultTheme(ThemeEnum themeEnum) {

    }

    @Override
    public ThemeEnum getDefaultTheme() {
        return null;
    }

    @Override
    public void setHintsEnabled(boolean enabled) {

    }

    @Override
    public long getTimeAllowedPerQuestion() {
        return 0;
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
    public boolean getHintsEnabled() {
        return false;
    }

    @Override
    public void setAvatarId(int avatarId) {}

    @Override
    public int getAvatarId() {
        return 0;
    }

}
