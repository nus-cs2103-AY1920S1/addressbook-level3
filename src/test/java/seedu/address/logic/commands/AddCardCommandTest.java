package seedu.address.logic.commands;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.card.Card;
import seedu.address.testutil.CardBuilder;


public class AddCardCommandTest {

    @Test
    public void constructor_nullCard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCardCommand(null));
    }

    @Test
    public void execute_duplicateCard_throwsCommandException() {
        Card validCard = new CardBuilder().build();
        AddCardCommand addCardCommand = new AddCardCommand(validCard);
        ModelStub modelStub = new ModelStubWithCard(validCard);
        assertThrows(CommandException.class,
                AddCardCommand.MESSAGE_DUPLICATE_CARD, () -> addCardCommand.execute(modelStub));
    }
}
