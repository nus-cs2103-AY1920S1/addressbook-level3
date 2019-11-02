package seedu.address.logic.commands.gamecommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.gamecommands.GuessCommand.MESSAGE_GAME_OVER;
import static seedu.address.logic.commands.gamecommands.GuessCommandResult.MESSAGE_CORRECT_GUESS;
import static seedu.address.logic.commands.gamecommands.GuessCommandResult.MESSAGE_WRONG_GUESS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCards.CHARIZARD;
import static seedu.address.testutil.TypicalCards.DITTO;

import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.appsettings.AppSettings;
import seedu.address.model.appsettings.DifficultyEnum;
import seedu.address.model.appsettings.ThemeEnum;
import seedu.address.model.card.Card;
import seedu.address.model.card.FormattedHint;
import seedu.address.model.game.Game;
import seedu.address.model.game.Guess;
import seedu.address.model.globalstatistics.GlobalStatistics;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.model.wordbank.WordBank;
import seedu.address.model.wordbanklist.WordBankList;
import seedu.address.model.wordbankstatslist.WordBankStatisticsList;
import seedu.address.model.wordbankstats.WordBankStatistics;
import seedu.address.testutil.WordBankBuilder;

class GuessCommandTest {

    private static final String MESSAGE_NO_ACTIVE_GAME = "There is no active game!";
    private static WordBank dittoCharizardWordBank;
    private static Guess charizardGuess = new Guess("CHARIZARD");
    private static Guess dummyWrongGuess = new Guess("dsfndshufsi293042");
    private static Guess dittoGuess = new Guess("DITTO");
    private Model dummyModel;

    @BeforeEach
    void setUp() {
        WordBankBuilder wordBankBuilder = new WordBankBuilder("GuessCommandTestBank");
        wordBankBuilder.withCard(DITTO);
        wordBankBuilder.withCard(CHARIZARD);
        dittoCharizardWordBank = wordBankBuilder.build();
        dummyModel = new ModelStub();
    }

    @Test
    void execute_gameIsNull_throwsCommandException() {
        dummyModel.setGame(null);
        try {
            new GuessCommand(charizardGuess).execute(dummyModel);
        } catch (CommandException e) {
            assertEquals(e.getMessage(), MESSAGE_NO_ACTIVE_GAME);
            return;
        }
        fail();
    }

    @Test
    void execute_gameIsAlreadyOver_throwsCommandException() {
        Game dummyGame = new Game(dittoCharizardWordBank, cardsToShuffle -> {}, DifficultyEnum.EASY);
        dummyGame.forceStop();
        dummyModel.setGame(dummyGame);
        try {
            new GuessCommand(charizardGuess).execute(dummyModel);
        } catch (CommandException e) {
            assertEquals(e.getMessage(), MESSAGE_NO_ACTIVE_GAME);
            return;
        }
        fail();
    }

    @Test
    void execute_isLastCard_correctGuessSuccess() {
        Game dummyGame = new Game(dittoCharizardWordBank, cardsToShuffle -> {}, DifficultyEnum.EASY);
        dummyModel.setGame(dummyGame);
        try {
            new GuessCommand(charizardGuess).execute(dummyModel);
            CommandResult res = new GuessCommand(charizardGuess).execute(dummyModel);
            assertEquals(res.getFeedbackToUser(),
                    MESSAGE_CORRECT_GUESS + "\n"
                            + MESSAGE_GAME_OVER);
        } catch (CommandException e) {
            fail();
        }
    }

    @Test
    void execute_isLastCard_wrongGuessSuccess() {
        Game dummyGame = new Game(dittoCharizardWordBank, cardsToShuffle -> {}, DifficultyEnum.EASY);
        dummyModel.setGame(dummyGame);
        try {
            new GuessCommand(charizardGuess).execute(dummyModel);
            CommandResult res = new GuessCommand(dummyWrongGuess).execute(dummyModel);
            assertEquals(res.getFeedbackToUser(),
                    MESSAGE_WRONG_GUESS + "\n"
                            + MESSAGE_GAME_OVER);
        } catch (CommandException e) {
            fail();
        }
    }

    @Test
    void execute_notLastCard_wrongGuessSuccess() {
        Game dummyGame = new Game(dittoCharizardWordBank, cardsToShuffle -> {}, DifficultyEnum.EASY);
        dummyModel.setGame(dummyGame);
        try {
            CommandResult res = new GuessCommand(dummyWrongGuess).execute(dummyModel);
            assertEquals(res.getFeedbackToUser(),
                    MESSAGE_WRONG_GUESS + "\n"
                            + dummyGame.getCurrQuestion());
        } catch (CommandException e) {
            fail();
        }
    }

    @Test
    void execute_notLastCard_correctGuessSuccess() {
        Game dummyGame = new Game(dittoCharizardWordBank, cardsToShuffle -> {}, DifficultyEnum.EASY);
        dummyModel.setGame(dummyGame);
        try {
            CommandResult res = new GuessCommand(dittoGuess).execute(dummyModel);
            assertEquals(res.getFeedbackToUser(),
                    MESSAGE_CORRECT_GUESS + "\n"
                            + dummyGame.getCurrQuestion());
        } catch (CommandException e) {
            fail();
        }
    }

    @Test
    void constructor_nullGuess_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GuessCommand(null));
    }

    private class ModelStub implements Model {

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
            throw new AssertionError("This method should not be called.");
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
            this.game = game;
        }

        @Override
        public boolean gameIsOver() {
            return game == null ? true : this.game.isOver();
        }

        @Override
        public boolean getHasBank() {
            return false;
        }

        @Override
        public Game getGame() {
            return this.game;
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

        }

        @Override
        public DifficultyEnum getDefaultDifficulty() {
            return null;
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
        public void setAvatarId(int avatarId) {

        }

        @Override
        public int getAvatarId() {
            return 0;
        }
    }
}
