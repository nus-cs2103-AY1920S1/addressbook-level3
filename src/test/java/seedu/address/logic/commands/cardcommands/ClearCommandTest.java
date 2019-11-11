package seedu.address.logic.commands.cardcommands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.card.Card;
import seedu.address.testutil.CardBuilder;

class ClearCommandTest {

    @Test
    void execute_emptyWordBank_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        new ClearCommand().execute(expectedModel);

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    void execute_nonEmptyWordBank_success() {
        Card abra = new CardBuilder().withWord("Abra").build();
        Card charmander = new CardBuilder().withWord("Charmander").build();

        Model model = new ModelManager();
        model.addCard(abra);

        Model expectedModel = new ModelManager();
        expectedModel.addCard(charmander);
        new ClearCommand().execute(expectedModel);

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
