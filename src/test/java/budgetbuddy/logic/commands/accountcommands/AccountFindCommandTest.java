package budgetbuddy.logic.commands.accountcommands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.model.AccountsManager;
import budgetbuddy.model.Model;
import budgetbuddy.model.account.NameHasKeywordsPredicate;
import budgetbuddy.testutil.accountutil.TypicalAccounts;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AccountFindCommandTest {
    private AccountsManager accountsManager = new AccountsManager(TypicalAccounts.ACCOUNT_LIST, Index.fromOneBased(1));
    private Model model;
    @Test
    public void execute_validKeyword_findSuccessful() {
        List<String> keywords = new ArrayList<>();
        keywords.add("trip");
        NameHasKeywordsPredicate nameHasKeywordsPredicate = new NameHasKeywordsPredicate(keywords);

        CommandResult commandResult = new AccountFindCommand(nameHasKeywordsPredicate).execute(model);

        assertEquals("1 account(s) listed! ", commandResult.getFeedbackToUser());
    }




}
