package seedu.address.logic.commands.gamecommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.gamecommands.GuessCommand.MESSAGE_GAME_OVER;
import static seedu.address.logic.commands.gamecommands.GuessCommandResult.MESSAGE_CORRECT_GUESS;
import static seedu.address.logic.commands.gamecommands.GuessCommandResult.MESSAGE_WRONG_GUESS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCards.CHARIZARD;
import static seedu.address.testutil.TypicalCards.DITTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appsettings.DifficultyEnum;
import seedu.address.model.game.Game;
import seedu.address.model.game.Guess;
import seedu.address.model.wordbank.WordBank;
import seedu.address.testutil.WordBankBuilder;

class GuessCommandTest {

    private static final String MESSAGE_NO_ACTIVE_GAME = "There is no active game!";
    private static WordBank dittoCharizardWordBank;
    private static Guess charizardGuess = new Guess("CHARIZARD");
    private static Guess dummyWrongGuess = new Guess("dsfndshufsi293042");
    private static Guess dittoGuess = new Guess("DITTO");
    private Model dummyModel;

    @BeforeEach
    public void setUp() {
        WordBankBuilder wordBankBuilder = new WordBankBuilder("GuessCommandTestBank");
        wordBankBuilder.withCard(DITTO);
        wordBankBuilder.withCard(CHARIZARD);
        dittoCharizardWordBank = wordBankBuilder.build();
        dummyModel = new GameCommandsModelStub();
    }

    @Test
    public void execute_gameIsNull_throwsCommandException() {
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
    public void execute_gameIsAlreadyOver_throwsCommandException() {
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
    public void execute_isLastCard_correctGuessSuccess() {
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
    public void execute_isLastCard_wrongGuessSuccess() {
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
    public void execute_notLastCard_wrongGuessSuccess() {
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
    public void execute_notLastCard_correctGuessSuccess() {
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
    public void constructor_nullGuess_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GuessCommand(null));
    }


}
