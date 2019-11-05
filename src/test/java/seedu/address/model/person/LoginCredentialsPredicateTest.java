package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

class LoginCredentialsPredicateTest {

    @Test
    void testEquals() {
        LoginCredentialsPredicate firstPredicate =
                new LoginCredentialsPredicate(new Username(VALID_USERNAME_AMY), new Password(VALID_PASSWORD_AMY));
        LoginCredentialsPredicate secondPredicate =
                new LoginCredentialsPredicate(new Username(VALID_USERNAME_BOB), new Password(VALID_PASSWORD_BOB));

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        LoginCredentialsPredicate firstPredicateCopy =
                new LoginCredentialsPredicate(new Username(VALID_USERNAME_AMY), new Password(VALID_PASSWORD_AMY));
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    void test_credentialsMatch_returnsTrue() {
        LoginCredentialsPredicate predicate =
                new LoginCredentialsPredicate(new Username(VALID_USERNAME_AMY), new Password(VALID_PASSWORD_AMY));
        assertTrue(predicate.test(new PersonBuilder()
                .withUsername(VALID_USERNAME_AMY).withPassword(VALID_PASSWORD_AMY).build()));
    }

    @Test
    void test_credentialsDifferent_returnsFalse() {
        LoginCredentialsPredicate predicate =
                new LoginCredentialsPredicate(new Username(VALID_USERNAME_AMY), new Password(VALID_PASSWORD_AMY));

        // different username and password
        assertFalse(predicate.test(new PersonBuilder()
                .withUsername(VALID_USERNAME_BOB).withPassword(VALID_PASSWORD_BOB).build()));

        // different username
        assertFalse(predicate.test(new PersonBuilder()
                .withUsername(VALID_USERNAME_BOB).withPassword(VALID_PASSWORD_AMY).build()));

        // different password
        assertFalse(predicate.test(new PersonBuilder()
                .withUsername(VALID_USERNAME_AMY).withPassword(VALID_PASSWORD_BOB).build()));
    }
}
