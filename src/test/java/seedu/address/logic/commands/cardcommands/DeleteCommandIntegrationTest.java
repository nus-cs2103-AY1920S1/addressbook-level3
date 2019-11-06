package seedu.address.logic.commands.cardcommands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CARD;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.card.Card;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.model.wordbankstats.WordBankStatistics;

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
        Card toDelete = SampleDataUtil.getPokemonWordBank().getCard(INDEX_FIRST_CARD);
        WordBankStatistics newWbs = WordBankStatistics.getEmpty("testing");

        model.setWordBankStatistics(newWbs);

        Model expectedModel = new ModelManager();
        expectedModel.setWordBankStatistics(newWbs);
        expectedModel.deleteCard(toDelete);

        assertCommandSuccess(new DeleteCommand(INDEX_FIRST_CARD), model,
                String.format(DeleteCommand.MESSAGE_DELETE_CARD_SUCCESS, toDelete), expectedModel);
    }

}
