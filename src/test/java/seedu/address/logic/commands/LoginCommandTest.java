package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.LoginCredentialsPredicate;
import seedu.address.model.person.Password;
import seedu.address.model.person.Person;
import seedu.address.model.person.Username;
import seedu.address.testutil.PersonBuilder;

//@@author madanalogy
class LoginCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private LoginCredentialsPredicate firstPredicate = new LoginCredentialsPredicate(
            new Username(VALID_USERNAME_AMY), new Password(VALID_PASSWORD_AMY));
    private LoginCredentialsPredicate secondPredicate = new LoginCredentialsPredicate(
            new Username(VALID_USERNAME_BOB), new Password(VALID_PASSWORD_BOB));

    @BeforeEach
    void setup() {
        model.setSession(null);
        expectedModel.setSession(null);
    }

    @Test
    void execute_isLoggedIn_throwsCommandException() {
        model.setSession(new PersonBuilder().build());
        assertCommandFailure(new LoginCommand(firstPredicate), model, LoginCommand.MESSAGE_MISUSE);
    }

    @Test
    void execute_invalidCredentials_throwsCommandException() {
        assertCommandFailure(new LoginCommand(new LoginCredentialsPredicate(
                new Username("notInSystem"), new Password("notInSyst3m"))), model, LoginCommand.MESSAGE_FAILURE);
    }

    @Test
    void execute_login_success() {
        Person user = new PersonBuilder().withUsername(VALID_USERNAME_AMY).withPassword(VALID_PASSWORD_AMY).build();
        model.addPerson(user);
        expectedModel.addPerson(user);
        expectedModel.setSession(user);
        CommandResult expectedCommandResult = new CommandResult(LoginCommand.MESSAGE_SUCCESS, true, false, false);
        assertCommandSuccess(new LoginCommand(new LoginCredentialsPredicate(
                new Username(VALID_USERNAME_AMY), new Password(VALID_PASSWORD_AMY))),
                model, expectedCommandResult, expectedModel);
    }

    @Test
    void testEquals() {

        LoginCommand loginFirstCommand = new LoginCommand(firstPredicate);
        LoginCommand loginSecondCommand = new LoginCommand(secondPredicate);

        // same object -> returns true
        assertTrue(loginFirstCommand.equals(loginFirstCommand));

        // same values -> returns true
        LoginCommand loginFirstCommandCopy = new LoginCommand(firstPredicate);
        assertTrue(loginFirstCommand.equals(loginFirstCommandCopy));

        // different types -> returns false
        assertFalse(loginFirstCommand.equals(1));

        // null -> returns false
        assertFalse(loginFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(loginFirstCommand.equals(loginSecondCommand));
    }
}
