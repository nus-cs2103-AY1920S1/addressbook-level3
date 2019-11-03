package budgetbuddy.logic.rules;

import static budgetbuddy.logic.rules.RuleEngine.TYPE_AMOUNT;
import static budgetbuddy.logic.rules.RuleEngine.TYPE_BLANK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.rules.performable.Performable;
import budgetbuddy.logic.rules.testable.ContainsExpression;
import budgetbuddy.logic.rules.testable.Testable;
import budgetbuddy.logic.script.ScriptEngine;
import budgetbuddy.model.AccountsManager;
import budgetbuddy.model.Model;
import budgetbuddy.model.ModelManager;
import budgetbuddy.model.RuleManager;
import budgetbuddy.model.UserPrefs;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.attributes.Category;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Direction;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.rule.Rule;
import budgetbuddy.model.rule.expression.Attribute;
import budgetbuddy.model.rule.expression.Value;
import budgetbuddy.model.transaction.Amount;
import budgetbuddy.model.transaction.Transaction;
import budgetbuddy.model.transaction.TransactionList;
import budgetbuddy.testutil.TestUtil;
import budgetbuddy.testutil.TypicalIndexes;
import budgetbuddy.testutil.ruleutil.TypicalActions;
import budgetbuddy.testutil.ruleutil.TypicalPredicates;
import budgetbuddy.testutil.ruleutil.TypicalRules;

class RuleEngineTest {

    private Model model = new ModelManager();
    private ScriptEngine scriptEngine = new ScriptEngine(engine -> engine.setVariable("bb", model));
    private Account account;

    @BeforeEach
    public void setUp() {
        // set up script path
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setScriptsPath(TestUtil.getFilePathInSandboxFolder("scripts"));
        model.setUserPrefs(userPrefs);

        // set up test account
        AccountsManager accountsManager = model.getAccountsManager();
        account = new AccountStub();
        accountsManager.addAccount(account);

        // set up test rule
        RuleManager ruleManager = model.getRuleManager();
        Rule rule = TypicalRules.FOOD_DESC_FOOD;
        ruleManager.addRule(rule);
    }

    @Test
    public void executeRules_ruleAppliesToTxn_transactionEdited() {
        Transaction txn = new TransactionStub("food for lunch");
        account.addTransaction(txn);
        Index txnIndex = TypicalIndexes.INDEX_FIRST_ITEM;
        RuleEngine.executeRules(model, scriptEngine, txnIndex, account);

        // assert only 1 transaction in account
        assertEquals(1, account.getTransactionList().getTransactionsCount());

        // assert category added into transaction
        Category expectedCategory = new Category("Food");
        assertTrue(account.getTransaction(txnIndex).getCategories().contains(expectedCategory));

        // assert same transaction other than category
        assertEquals(txn.getDescription(), account.getTransaction(txnIndex).getDescription());
    }

    @Test
    public void executeRules_ruleDoesNotApplyToTxn_nothingIsDone() {
        Transaction txn = new TransactionStub("bought necessities");
        account.addTransaction(txn);
        Index txnIndex = TypicalIndexes.INDEX_FIRST_ITEM;
        RuleEngine.executeRules(model, scriptEngine, txnIndex, account);

        // assert only 1 transaction in account
        assertEquals(1, account.getTransactionList().getTransactionsCount());

        // assert same transaction
        assertEquals(txn, account.getTransaction(txnIndex));
    }

    @Test
    public void executeRules_overlappingRules_rulesExecutedInAddedOrder() {
        RuleManager ruleManager = model.getRuleManager();
        ruleManager.addRule(TypicalRules.DAILY_DESC_PREP_DAILY);
        ruleManager.addRule(TypicalRules.MOREEQUAL100_OUTAMT_HIDDEN);

        account.addTransaction(new TransactionStub("daily necessities"));
        Index txnIndex = TypicalIndexes.INDEX_FIRST_ITEM;

        RuleEngine.executeRules(model, scriptEngine, txnIndex, account);

        // assert only 1 transaction in account
        assertEquals(1, account.getTransactionList().getTransactionsCount());

        // assert transaction description modified by both rules but overwritten by second rule
        assertEquals(new Description("hidden"), account.getTransaction(txnIndex).getDescription());
    }

    @Test
    public void executeRules_nullModel_throwsNullPointerException() {
        Index txnIndex = TypicalIndexes.INDEX_FIRST_ITEM;
        assertThrows(NullPointerException.class, () -> RuleEngine.executeRules(null, scriptEngine, txnIndex, account));
    }

    @Test
    public void executeRules_nullScriptEngine_throwsNullPointerException() {
        Index txnIndex = TypicalIndexes.INDEX_FIRST_ITEM;
        assertThrows(NullPointerException.class, () -> RuleEngine.executeRules(model, null, txnIndex, account));
    }

    @Test
    public void executeRules_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> RuleEngine.executeRules(model, scriptEngine, null, account));
    }

    @Test
    public void executeRules_nullAccount_throwsNullPointerException() {
        Index txnIndex = TypicalIndexes.INDEX_FIRST_ITEM;
        assertThrows(NullPointerException.class, () -> RuleEngine.executeRules(model, scriptEngine, txnIndex, null));
    }

    @Test
    void generateTestable_validPredicateExpression_returnsExpectedTestable() {
        Testable expectedTestable = new ContainsExpression(Attribute.DESCRIPTION, new Value("food"));
        Testable actualTestable = RuleEngine.generateTestable(TypicalPredicates.DESC_CONTAINS_FOOD,
                model.getScriptLibrary(), scriptEngine);

        assertEquals(expectedTestable, actualTestable);
    }

    @Test
    void generateTestable_validPredicateScript_success() {
        Testable testable = RuleEngine.generateTestable(TypicalPredicates.TEST_SCRIPT,
                model.getScriptLibrary(), scriptEngine);

        assertNotNull(testable);
    }

    @Test
    void generatePerformable_validActionScript_success() {
        Performable performable = RuleEngine.generatePerformable(TypicalActions.TEST_SCRIPT,
                model.getScriptLibrary(), scriptEngine);

        assertNotNull(performable);
    }

    @Test
    void isValueParsable_parsableValue_returnsTrue() {
        assertTrue(RuleEngine.isValueParsable(TYPE_AMOUNT, new Value("200")));
    }

    @Test
    void isValueParsable_notParsableValue_returnsFalse() {
        assertFalse(RuleEngine.isValueParsable(TYPE_BLANK, new Value("200")));
    }

    @Test
    void extractAttribute_descAttribute_sameDescAttributeExtracted() {
        String desc = "test";
        Transaction txn = new TransactionStub(desc);
        account.addTransaction(txn);
        Index txnIndex = TypicalIndexes.INDEX_FIRST_ITEM;

        assertEquals(new Description(desc), RuleEngine.extractAttribute(Attribute.DESCRIPTION, txnIndex, account));
    }

    @Test
    void extractAttribute_inAmtAttribute_inAmtAttributeExtracted() {
        Transaction txn = new TransactionStub("test");
        account.addTransaction(txn);
        Index txnIndex = TypicalIndexes.INDEX_FIRST_ITEM;

        assertEquals(-100.00, RuleEngine.extractAttribute(Attribute.IN_AMOUNT, txnIndex, account));
    }

    @Test
    void extractAttribute_outAmtAttribute_outAmtAttributeExtracted() {
        Transaction txn = new TransactionStub("test");
        account.addTransaction(txn);
        Index txnIndex = TypicalIndexes.INDEX_FIRST_ITEM;

        assertEquals(100.00, RuleEngine.extractAttribute(Attribute.OUT_AMOUNT, txnIndex, account));
    }

    /**
     * Represents a stub Account.
     */
    public class AccountStub extends Account {

        public AccountStub() {
            super(new Name("TestName"), new Description("TestDesc"), new TransactionList());
        }
    }

    /**
     * Represents a stub Transaction.
     */
    public class TransactionStub extends Transaction {

        public TransactionStub(String desc) {
            super(new Date(), new Amount(10000), Direction.OUT, new Description(desc), new HashSet<>());
        }
    }
}
