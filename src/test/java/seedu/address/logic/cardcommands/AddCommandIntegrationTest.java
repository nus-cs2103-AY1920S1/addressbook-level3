package seedu.address.logic.cardcommands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.cardcommands.AddCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.card.Card;
import seedu.address.testutil.CardBuilder;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;

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
        Card validPerson = new CardBuilder().build();

        Model expectedModel = new ModelManager();

        expectedModel.addCard(validPerson);

        assertCommandSuccess(new AddCommand(validPerson), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validPerson), expectedModel);
    }


    @Test
    void execute_duplicatePerson_throwsCommandException() {
        Card personInList = model.getCurrentWordBank().getCardList().get(0);
        assertCommandFailure(new AddCommand(personInList), model, Messages.MESSAGE_DUPLICATE_CARD);
    }

}
