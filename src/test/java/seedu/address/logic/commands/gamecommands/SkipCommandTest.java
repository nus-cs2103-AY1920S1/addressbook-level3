package seedu.address.logic.commands.gamecommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.gamecommands.SkipCommand.MESSAGE_GAME_OVER;
import static seedu.address.testutil.TypicalCards.CHARIZARD;
import static seedu.address.testutil.TypicalCards.DITTO;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appsettings.DifficultyEnum;
import seedu.address.model.game.Game;
import seedu.address.model.wordbank.WordBank;
import seedu.address.testutil.WordBankBuilder;

class SkipCommandTest {

    private static final String MESSAGE_NO_ACTIVE_GAME = "There is no active game!";
    private static final String MESSAGE_SKIPPED = "Word skipped!";
    private static WordBank dittoCharizardWordbank =
            new WordBankBuilder("SkipCommandTestBank")
                    .withCard(DITTO).withCard(CHARIZARD).build();

    @Test
    public void execute_gameIsNull_throwsCommandException() {
        Model dummyModel = new GameCommandsModelStub();
        assertThrows(CommandException.class, () -> new SkipCommand().execute(dummyModel));

        try {
            new SkipCommand().execute(dummyModel);
        } catch (CommandException e) {
            assertEquals(e.getMessage(), MESSAGE_NO_ACTIVE_GAME);
            return;
        }
        fail();
    }

    @Test
    public void execute_gameIsOver_throwsCommandException() {
        Model dummyModel = new GameCommandsModelStub();
        Game dummyGame = new Game(dittoCharizardWordbank, x -> {}, DifficultyEnum.EASY);
        dummyGame.forceStop();
        dummyModel.setGame(dummyGame);
        assertThrows(CommandException.class, () -> new SkipCommand().execute(dummyModel));

        try {
            new SkipCommand().execute(dummyModel);
        } catch (CommandException e) {
            assertEquals(e.getMessage(), MESSAGE_NO_ACTIVE_GAME);
            return;
        }
        fail();
    }

    @Test
    public void execute_notLastCard_success() {
        Model dummyModel = new GameCommandsModelStub();
        Game dummyGame = new Game(dittoCharizardWordbank, x -> {}, DifficultyEnum.EASY);
        dummyModel.setGame(dummyGame);

        try {
            SkipCommandResult result = (SkipCommandResult) new SkipCommand().execute(dummyModel);
            assertEquals(result.getCard().get(), DITTO);
            assertFalse(result.isExit());
            assertFalse(result.isFinishedGame());
            assertTrue(result.isPromptingGuess());
            assertEquals(result.getFeedbackToUser(), MESSAGE_SKIPPED + "\n" + dummyGame.getCurrQuestion());
        } catch (CommandException e) {
            fail();
        }
    }

    @Test
    public void execute_isLastCard_success() {
        Model dummyModel = new GameCommandsModelStub();
        Game dummyGame = new Game(dittoCharizardWordbank, x -> {}, DifficultyEnum.EASY);
        dummyGame.moveToNextCard();
        dummyModel.setGame(dummyGame);

        try {
            SkipCommandResult result = (SkipCommandResult) new SkipCommand().execute(dummyModel);
            assertEquals(result.getCard().get(), CHARIZARD);
            assertFalse(result.isExit());
            assertTrue(result.isFinishedGame());
            assertFalse(result.isPromptingGuess());
            assertEquals(result.getFeedbackToUser(), MESSAGE_SKIPPED + "\n" + MESSAGE_GAME_OVER);
        } catch (CommandException e) {
            fail();
        }
    }


}
