package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.appCommands.AddCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.card.Card;
import seedu.address.testutil.CardBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
    }

    @Test
    public void execute_newPerson_success() {
        Card validPerson = new CardBuilder().build();

        Model expectedModel = model = new ModelManager();

        expectedModel.addCard(validPerson);

        assertCommandSuccess(new AddCommand(validPerson), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validPerson), expectedModel);
    }

    //    @Test
    //    public void execute_duplicatePerson_throwsCommandException() {
    //        Card personInList = model.getWordBank().getCardList().get(0);
    //        assertCommandFailure(new AddCommand(personInList), model, AddCommand.MESSAGE_DUPLICATE_CARD);
    //    }

}
