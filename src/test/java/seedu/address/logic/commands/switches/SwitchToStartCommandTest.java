package seedu.address.logic.commands.switches;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import static seedu.address.logic.commands.switches.SwitchToStartCommand.MESSAGE_TOO_FEW_CANNOT_START;
import static seedu.address.logic.commands.switches.SwitchToStartCommand.MESSAGE_TOO_FEW_CARDS;
import static seedu.address.logic.commands.switches.SwitchToStartCommand.MESSAGE_WORDBANK_NOT_LOADED;
import static seedu.address.testutil.TypicalCards.ABRA;
import static seedu.address.testutil.TypicalCards.BUTTERFREE;
import static seedu.address.testutil.TypicalCards.CHARIZARD;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.util.ModeEnum;
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
import seedu.address.testutil.WordBankBuilder;

class SwitchToStartCommandTest {
    private static final String MESSAGE_GAME_IN_PROGRESS = "A game session is still in progress!"
            + " (Use 'stop' to terminate) Guess the word:";
    private WordBank threeCardWordBank = new WordBankBuilder("threeCardWordBank")
            .withCard(ABRA).withCard(BUTTERFREE).withCard(CHARIZARD).build();
    private WordBank twoCardWordBank = new WordBankBuilder("twoCardWordBank")
            .withCard(ABRA).withCard(BUTTERFREE).build();
    private WordBank zeroCardWordbank = new WordBankBuilder("zeroCardWordBank").build();

    private ArrayList<WordBank> dummyListOfWordBanks;


    @BeforeEach
    void setUpDummyListOfWordBanks() {
        dummyListOfWordBanks = new ArrayList<>();
        dummyListOfWordBanks.add(threeCardWordBank);
        dummyListOfWordBanks.add(twoCardWordBank);
        dummyListOfWordBanks.add(zeroCardWordbank);
    }

    @Test
    void getNewMode() {
        SwitchToStartCommand dummySwitchToStartCommand1 = new SwitchToStartCommand(Optional.empty());
        assertEquals(dummySwitchToStartCommand1.getNewMode(ModeEnum.GAME), ModeEnum.GAME);
    }

    @Test
    void execute_noDifficultySpecified() {
        Model dummyModel = new ModelStub();
        dummyModel.setCurrentWordBank(threeCardWordBank);
        SwitchToStartCommand dummySwitchToStartCommand1 = new SwitchToStartCommand(Optional.empty());

        try {
            dummySwitchToStartCommand1.execute(dummyModel);
        } catch (CommandException e) {
            // Should not throw exception.
            e.printStackTrace();
            fail();
        }

        // No Difficulty specified, defaults to the Model's default difficulty, which is
        assertEquals(DifficultyEnum.EASY, dummyModel.getCurrentGameDifficulty());
    }

    @Test
    void execute_difficultySpecified() {
        Model dummyModel = new ModelStub();
        dummyModel.setCurrentWordBank(threeCardWordBank);
        SwitchToStartCommand dummySwitchToStartCommand1 = new SwitchToStartCommand(Optional.of(DifficultyEnum.HARD));

        try {
            dummySwitchToStartCommand1.execute(dummyModel);
        } catch (CommandException e) {
            // Should not throw exception.
            e.printStackTrace();
            fail();
        }

        // Difficulty is specified, game should be set to input difficulty.
        assertEquals(DifficultyEnum.HARD, dummyModel.getCurrentGameDifficulty());
    }

    @Test
    void execute_lessThanThreeCards_throwsCommandException() {
        Model dummyModel = new ModelStub();
        dummyModel.setCurrentWordBank(twoCardWordBank);
        SwitchToStartCommand dummySwitchToStartCommand1 = new SwitchToStartCommand(Optional.of(DifficultyEnum.HARD));

        assertThrows(CommandException.class, () -> dummySwitchToStartCommand1.execute(dummyModel));
        try {
            dummySwitchToStartCommand1.execute(dummyModel);
        } catch (CommandException ce) {
            assertEquals(ce.getMessage(), MESSAGE_TOO_FEW_CARDS + 2
                    + "\n" + MESSAGE_TOO_FEW_CANNOT_START);
            return;
        }
        // Test Should not reach this point as it returns in catch block.
        fail();
    }

    @Test
    void execute_zeroCards_throwsCommandException() {
        Model dummyModel = new ModelStub();
        dummyModel.setCurrentWordBank(zeroCardWordbank);
        SwitchToStartCommand dummySwitchToStartCommand1 = new SwitchToStartCommand(Optional.of(DifficultyEnum.HARD));

        assertThrows(CommandException.class, () -> dummySwitchToStartCommand1.execute(dummyModel));
        try {
            dummySwitchToStartCommand1.execute(dummyModel);
        } catch (CommandException ce) {
            assertEquals(ce.getMessage(), MESSAGE_TOO_FEW_CARDS + 0
                    + "\n" + MESSAGE_TOO_FEW_CANNOT_START);
            return;
        }
        // Test Should not reach this point as it returns in catch block.
        fail();
    }

    @Test
    void execute_nullWordBank_throwsCommandException() {
        Model dummyModel = new ModelStub();
        SwitchToStartCommand dummySwitchToStartCommand1 = new SwitchToStartCommand(Optional.of(DifficultyEnum.HARD));
        assertThrows(CommandException.class, () -> dummySwitchToStartCommand1.execute(dummyModel));
        try {
            dummySwitchToStartCommand1.execute(dummyModel);
        } catch (CommandException ce) {
            assertEquals(ce.getMessage(), MESSAGE_WORDBANK_NOT_LOADED);
            return;
        }
        // Test Should not reach this point as it returns in catch block.
        fail();
    }

    @Test
    void execute_anotherGameInProgress_throwsCommandException() {
        Model dummyModel = new ModelStub();
        dummyModel.setCurrentWordBank(threeCardWordBank);
        dummyModel.setGame(new Game(threeCardWordBank, x -> {}, DifficultyEnum.EASY));
        SwitchToStartCommand dummySwitchToStartCommand = new SwitchToStartCommand(Optional.of(DifficultyEnum.HARD));
        assertThrows(CommandException.class, () -> dummySwitchToStartCommand.execute(dummyModel));
        try {
            dummySwitchToStartCommand.execute(dummyModel);
        } catch (CommandException ce) {
            assertEquals(ce.getMessage(), MESSAGE_GAME_IN_PROGRESS
                    + "\n" + dummyModel.getGame().getCurrQuestion());
            return;
        }
        fail();
    }

    @Test
    void getDifficulty() {
        // Optional.empty() is passed into constructor
        SwitchToStartCommand dummyStartCommand = new SwitchToStartCommand(Optional.empty());
        assertTrue(dummyStartCommand.getDifficulty().isEmpty());

        // Optional<DifficultyEnum> passed into constructor
        dummyStartCommand = new SwitchToStartCommand(Optional.of(DifficultyEnum.HARD));
        assertEquals(dummyStartCommand.getDifficulty().get(),
                DifficultyEnum.HARD);
    }

    @Test
    void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SwitchToStartCommand(null));
    }

    private class ModelStub implements Model {

        private ReadOnlyWordBank wordBank;
        private Game game;

        private WordBankList wordBanklist = new WordBankList(dummyListOfWordBanks);

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("Method should not be called");
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
            throw new AssertionError("Method should not be called");
        }

        @Override
        public Path getWordBankFilePath() {
            return null;
        }

        @Override
        public void setWordBankFilePath(Path addressBookFilePath) {
            throw new AssertionError("Method should not be called");
        }

        @Override
        public void clearActiveWordBankStatistics() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCurrentWordBank(ReadOnlyWordBank currentWordBank) {
            this.wordBank = currentWordBank;
        }

        @Override
        public void updateWordBank(String name) {
            throw new AssertionError("Method should not be called");
        }

        @Override
        public WordBankStatistics getWordBankStatistics() {
            return null;
        }

        @Override
        public void setWordBankStatistics(WordBankStatistics wordBankStats) {
            throw new AssertionError("Method should not be called");
        }

        @Override
        public void clearWordBankStatistics() {
            throw new AssertionError("Method should not be called");
        }

        @Override
        public void clearWordBank() {
            throw new AssertionError("Method should not be called");
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
            throw new AssertionError("Method should not be called");
        }

        @Override
        public void addCard(Card card) {
            throw new AssertionError("Method should not be called");
        }

        @Override
        public void setCard(Card target, Card editedCard) {
            throw new AssertionError("Method should not be called");
        }

        @Override
        public ObservableList<Card> getFilteredCardList() {
            return null;
        }

        @Override
        public WordBankList getWordBankList() {
            return wordBanklist;
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
            throw new AssertionError("Method should not be called");
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
            throw new AssertionError("Method should not be called");
        }

        @Override
        public DifficultyEnum getDefaultDifficulty() {
            return DifficultyEnum.EASY;
        }

        @Override
        public void setDefaultTheme(ThemeEnum themeEnum) {
            throw new AssertionError("Method should not be called");
        }

        @Override
        public ThemeEnum getDefaultTheme() {
            return null;
        }

        @Override
        public void setHintsEnabled(boolean enabled) {
            throw new AssertionError("Method should not be called");
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
            throw new AssertionError("Method should not be called");
        }

        @Override
        public int getAvatarId() {
            return 0;
        }

    }
}
