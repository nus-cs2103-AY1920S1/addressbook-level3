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
import seedu.address.model.password.Password;
import seedu.address.testutil.PasswordBuilder;



public class AddPasswordCommandIntegrationTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new FileBook(),
                new CardBook(), getTypicalNoteBook(), new PasswordBook(), new UserPrefs());
    }

    @Test
    public void execute_newPassword_success() {
        Password validPassword = new PasswordBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new FileBook(),
                new CardBook(), getTypicalNoteBook(), new PasswordBook(), new UserPrefs());
        CommandResult commandResult = new CommandResult(
                String.format(AddPasswordCommand.MESSAGE_SUCCESS, validPassword));
        CommandResult result = null;
        try {
            result = new AddPasswordCommand(validPassword).execute(expectedModel);
        } catch (CommandException e) {
            e.printStackTrace();
        }
        assertEquals(commandResult, result);
    }
}
