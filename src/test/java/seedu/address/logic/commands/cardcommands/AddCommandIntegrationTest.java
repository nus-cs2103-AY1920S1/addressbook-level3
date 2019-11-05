package seedu.address.logic.commands.cardcommands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.card.Card;
import seedu.address.testutil.CardBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    void setUp() {
        model = new ModelManager();
    }

    @Test
    void execute_newCard_success() {
        Card abra = new CardBuilder().withWord("Abra").build();

        Model expectedModel = new ModelManager();

        expectedModel.addCard(abra);

        assertCommandSuccess(new AddCommand(abra), model,
                String.format(AddCommand.MESSAGE_SUCCESS, abra), expectedModel);
    }


    @Test
    void execute_duplicateCard_throwsCommandException() {
        Card cardInList = model.getCurrentWordBank().getCardList().get(0);
        assertCommandFailure(new AddCommand(cardInList), model, Messages.MESSAGE_DUPLICATE_CARD);
    }

}
