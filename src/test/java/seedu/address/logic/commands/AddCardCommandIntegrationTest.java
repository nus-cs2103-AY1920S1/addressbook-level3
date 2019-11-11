package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static seedu.address.testutil.TypicalNotes.getTypicalNoteBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.CardBook;
import seedu.address.model.FileBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PasswordBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.card.Card;
import seedu.address.testutil.CardBuilder;


public class AddCardCommandIntegrationTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new FileBook(),
                new CardBook(), getTypicalNoteBook(), new PasswordBook(), new UserPrefs());
    }

    @Test
    public void execute_newCard_success() {
        Card validCard = new CardBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new FileBook(),
                new CardBook(), getTypicalNoteBook(), new PasswordBook(), new UserPrefs());
        CommandResult commandResult = new CommandResult(
                String.format(AddCardCommand.MESSAGE_SUCCESS, validCard));
        CommandResult result = null;
        try {
            result = new AddCardCommand(validCard).execute(expectedModel);
        } catch (CommandException e) {
            e.printStackTrace();
        }
        assertEquals(commandResult, result);
    }
}
