package seedu.address.logic.commands.gamecommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.TypicalCards.CHARIZARD;
import static seedu.address.testutil.TypicalCards.DITTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appsettings.DifficultyEnum;
import seedu.address.model.game.Game;
import seedu.address.model.wordbank.WordBank;
import seedu.address.testutil.WordBankBuilder;

class StopCommandTest {

    private static final String MESSAGE_STOPPED = "Current Game has been forcibly stopped!";
    private static final String MESSAGE_NO_SESSION = "There is no active game session to stop!";
    private static WordBank dittoCharizardWordbank =
            new WordBankBuilder("SkipCommandTestBank")
                    .withCard(DITTO).withCard(CHARIZARD).build();
    private Model dummyModel;

    @BeforeEach
    public void setUp() {
        dummyModel = new GameCommandsModelStub();
    }

    @Test
    public void execute_gameAlreadyOver_throwsCommandException() {
        Game dummyGame = new Game(dittoCharizardWordbank, x -> {}, DifficultyEnum.EASY);
        dummyGame.forceStop();
        dummyModel.setGame(dummyGame);
        assertThrows(CommandException.class, () -> new StopCommand().execute(dummyModel));
        try {
            new StopCommand().execute(dummyModel);
        } catch (CommandException e) {
            assertEquals(e.getMessage(), MESSAGE_NO_SESSION);
            return;
        }
        fail();
    }

    @Test
    public void execute_gameNotOver_success() {
        Game dummyGame = new Game(dittoCharizardWordbank, x -> {}, DifficultyEnum.EASY);
        dummyModel.setGame(dummyGame);
        try {
            CommandResult res = new StopCommand().execute(dummyModel);
            assertEquals(res.getFeedbackToUser(), MESSAGE_STOPPED);
        } catch (CommandException e) {
            e.printStackTrace();
            fail();
        }
    }
}
