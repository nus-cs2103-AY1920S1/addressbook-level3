package budgetbuddy.logic.commands.accountcommands;

import static budgetbuddy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.model.AccountsManager;
import budgetbuddy.model.Model;
import budgetbuddy.model.ModelManager;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.account.NameHasKeywordsPredicate;
import budgetbuddy.testutil.accountutil.TypicalAccounts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AccountFindCommandTest {
    private Model model = new ModelManager();

    @BeforeEach
    public void initialize() {
        model = new ModelManager();

        for (Account account: TypicalAccounts.ACCOUNT_LIST) {
            model.getAccountsManager().addAccount(account);
        }
    }

    @Test
    public void execute_validKeyword_findSuccessful() {
        List<String> keywords = new ArrayList<>();
        keywords.add("trip");
        NameHasKeywordsPredicate nameHasKeywordsPredicate = new NameHasKeywordsPredicate(keywords);

        CommandResult commandResult = new AccountFindCommand(nameHasKeywordsPredicate).execute(model);

        assertEquals("1 account(s) listed!", commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        List<String> keywords = new ArrayList<>();
        keywords.add("food");
        NameHasKeywordsPredicate nameHasKeywordsPredicate = new NameHasKeywordsPredicate(keywords);
        AccountFindCommand foodFindCommand = new AccountFindCommand(nameHasKeywordsPredicate);

        // same object -> returns true
        assertEquals(foodFindCommand, foodFindCommand);

        // same values -> returns true
        AccountFindCommand sameFindCommand = new AccountFindCommand(nameHasKeywordsPredicate);
        assertEquals(foodFindCommand, sameFindCommand);

        // different types -> returns false
        assertNotEquals(foodFindCommand, 0);

        // null -> returns false
        assertNotEquals(foodFindCommand, null);

        // different account -> returns false
        List<String> secondKeywords = new ArrayList<>();
        keywords.add("school");
        NameHasKeywordsPredicate secondNameHasKeywordsPredicate = new NameHasKeywordsPredicate(secondKeywords);
        AccountFindCommand schoolFindCommand = new AccountFindCommand(secondNameHasKeywordsPredicate);
        assertNotEquals(foodFindCommand, schoolFindCommand);
    }
}
