package budgetbuddy.logic.script;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import budgetbuddy.logic.script.exceptions.ScriptException;
import budgetbuddy.model.Model;
import budgetbuddy.model.ModelManager;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.attributes.Category;
import budgetbuddy.model.attributes.Direction;
import budgetbuddy.model.transaction.Transaction;
import budgetbuddy.testutil.TypicalIndexes;

/**
 * Integration tests for {@link ScriptModelBinding}.
 */
class ScriptModelBindingTest {
    private Model modelManager;
    private ScriptEngine se;

    @BeforeEach
    void setUp() {
        modelManager = new ModelManager();
        se = new ScriptEngine(new ScriptModelBinding(modelManager));
    }

    @Test
    void testAccountBindings() throws ScriptException {
        run("addAccount('name', 'desc')");
        Account acc = modelManager.getAccountsManager().getAccount(TypicalIndexes.INDEX_SECOND_ITEM);
        assertEquals("name", acc.getName().toString());
        assertEquals("desc", acc.getDescription().getDescription());

        assertEquals(acc, run("getAccount(1)"));
        assertEquals(acc, run("getAccount('name')"));

        assertThrows(ScriptException.class, () -> run("getAccount(0.5)"));

        assertEquals(modelManager.getAccountsManager().getActiveAccount(), run("getActiveAccount()"));
        assertEquals(modelManager.getAccountsManager().getAccounts(), run("getAccounts()"));
        run("setActiveAccount(1)");
        assertEquals(acc, modelManager.getAccountsManager().getActiveAccount());
        assertEquals(modelManager.getAccountsManager().getActiveAccount(), run("getActiveAccount()"));

        se.setVariable("acc", acc);
        Account morphedAcc = (Account) run("morphAccount(acc, { name: 'newname' })");
        assertEquals("newname", morphedAcc.getName().toString());
        assertEquals(acc.getDescription(), morphedAcc.getDescription());

        morphedAcc = (Account) run("morphAccount(acc, { name: 'newname', description: 'newdesc' })");
        assertEquals("newname", morphedAcc.getName().toString());
        assertEquals("newdesc", morphedAcc.getDescription().getDescription());

        run("editAccount(1, { name: 'newname' })");
        acc = modelManager.getAccountsManager().getAccount(TypicalIndexes.INDEX_SECOND_ITEM);
        assertEquals("newname", acc.getName().toString());

        run("deleteAccount(1)");
        assertEquals(1, modelManager.getAccountsManager().size());

        assertEquals("name", run("accountName(acc)"));
        assertEquals("desc", run("accountDescription(acc)"));
        assertEquals(0L, run("accountBalance(acc)"));
        assertEquals(Collections.emptyList(), run("accountTxns(acc)"));
    }

    @Test
    void testTxnBindings() throws ScriptException {
        Account acc = modelManager.getAccountsManager().getAccount(TypicalIndexes.INDEX_FIRST_ITEM);
        Transaction addTxnRet = (Transaction) run("addTxn(1000, 'in', 'hello')");
        Transaction txn = acc.getTransaction(TypicalIndexes.INDEX_FIRST_ITEM);
        assertEquals(addTxnRet, txn);
        assertEquals(1000, txn.getAmount().toLong());
        assertEquals(Direction.IN, txn.getDirection());
        assertEquals("hello", txn.getDescription().getDescription());
        assertEquals(Collections.emptySet(), txn.getCategories());

        se.setVariable("txn", txn);
        assertEquals(txn.getAmount().toLong(), run("txnAmount(txn)"));
        assertEquals(txn.getDescription().getDescription(), run("txnDescription(txn)"));
        assertEquals(txn.getLocalDate(), run("txnDate(txn)"));
        assertEquals(txn.getDirection().toString(), run("txnDirection(txn)"));
        assertArrayEquals(new String[0], (Object[]) run("txnCategories(txn)"));

        txn = (Transaction) run(
                "morphTxn(txn, { date: '1/1/2019', amount: 1, direction: 'out', categories: ['a', 'b'] })");
        assertEquals(1, txn.getAmount().toLong());
        assertEquals(Direction.OUT, txn.getDirection());
        assertEquals(LocalDate.of(2019, 1, 1), txn.getLocalDate());
        assertEquals(Set.of(new Category("a"), new Category("b")), txn.getCategories());
        assertEquals("hello", txn.getDescription().getDescription());

        txn = (Transaction) run("morphTxn(txn, { description: 'hello2' })");
        assertEquals("hello2", txn.getDescription().getDescription());

        se.setVariable("acc", acc);
        run("editTxn(acc, txn, { date: '1/1/2019', amount: 1, direction: 'out', categories: ['a', 'b'] })");
        txn = acc.getTransaction(TypicalIndexes.INDEX_FIRST_ITEM);
        assertEquals(1, txn.getAmount().toLong());
        assertEquals(Direction.OUT, txn.getDirection());
        assertEquals(LocalDate.of(2019, 1, 1), txn.getLocalDate());
        assertEquals(Set.of(new Category("a"), new Category("b")), txn.getCategories());
        assertEquals("hello", txn.getDescription().getDescription());

        se.setVariable("txn", txn);
        run("deleteTxn(acc, txn)");
        assertEquals(0, acc.getTransactionList().getTransactionsCount());

        txn = (Transaction) run("addTxn(1000, 'in', 'hello')");
        run("refreshTxnView()");
        assertEquals(txn, run("getShownTxn(0)"));

        run("editShownTxn(0, { direction: 'out' })");
        run("refreshTxnView()");
        txn = acc.getTransaction(TypicalIndexes.INDEX_FIRST_ITEM);
        assertEquals(Direction.OUT, txn.getDirection());

        assertEquals(Collections.singletonList(txn), run("getShownTxns()"));

        run("deleteShownTxn(0)");
        run("refreshTxnView()");
        assertEquals(0, acc.getTransactionList().getTransactionsCount());
        assertEquals(Collections.emptyList(), run("getShownTxns()"));
    }

    @Test
    void testDateHelpers() throws ScriptException {
        assertEquals(LocalDate.of(2020, 1, 1), run("parseDate('1/1/2020')"));
        assertEquals(LocalDate.of(2020, 1, 1), run("makeDate(2020, 1, 1)"));
    }

    private Object run(String script) throws ScriptException {
        return se.evaluateScript(script);
    }
}
