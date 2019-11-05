package seedu.address.logic.commands.cardcommands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.card.Card;
import seedu.address.model.wordbankstats.WordBankStatistics;
import seedu.address.testutil.CardBuilder;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_CARD;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
class DeleteCommandIntegrationTest {

    private Model model;

    @BeforeEach
    void setUp() {
        model = new ModelManager();
    }

    @Test
    void execute_deleteCard_success() {
        Card notAbra = new CardBuilder().withWord("Not abra").build();
        WordBankStatistics newWbs = WordBankStatistics.getEmpty("testing");
        Model expectedModel = new ModelManager();
        model.addCard(notAbra);
        model.setWordBankStatistics(newWbs);

//        assertCommandSuccess(new AddCommand(abra), model,
//                String.format(AddCommand.MESSAGE_SUCCESS, abra), expectedModel);

//        assertCommandSuccess(new DeleteCommand(INDEX_FIRST_CARD), model,
//                String.format(DeleteCommand.MESSAGE_DELETE_CARD_SUCCESS, notAbra), expectedModel);
    }


//    @Test
//    void execute_duplicateCard_throwsCommandException() {
//        Card cardInList = model.getCurrentWordBank().getCardList().get(0);
//        assertCommandFailure(new AddCommand(cardInList), model, Messages.MESSAGE_DUPLICATE_CARD);
//    }

//    private class DeleteCardModelStub extends ModelManager {
//
//        @Override
//        public void deleteCard(Card target) {
//            super.currentWordBank.removeCard(target);
//        }
//    }

}
