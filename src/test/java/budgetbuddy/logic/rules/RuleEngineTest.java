package budgetbuddy.logic.rules;

import static budgetbuddy.logic.rules.RuleEngine.TYPE_AMOUNT;
import static budgetbuddy.logic.rules.RuleEngine.TYPE_BLANK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.parser.exceptions.ParseException;
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
import budgetbuddy.model.attributes.Amount;
import budgetbuddy.model.attributes.Category;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Direction;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.rule.Rule;
import budgetbuddy.model.rule.expression.Attribute;
import budgetbuddy.model.rule.expression.Value;
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
        account = new AccountMock();
        accountsManager.addAccount(account);

        // set up test rule
        RuleManager ruleManager = model.getRuleManager();
        Rule rule = TypicalRules.FOOD_DESC_FOOD;
        ruleManager.addRule(rule);
    }

    @Test
    public void executeRules_ruleAppliesToTxn_transactionEdited() {
        Transaction txn = new TransactionMock("food for lunch");
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
        Transaction txn = new TransactionMock("bought necessities");
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

        account.addTransaction(new TransactionMock("daily necessities"));
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
    void convertValue_parsableValue() throws ParseException {
        assertEquals(new Amount(20000), RuleEngine.convertValue(TYPE_AMOUNT, new Value("200")));
    }

    @Test
    void convertValue_blankValue() throws ParseException {
        assertNull(RuleEngine.convertValue(TYPE_BLANK, new Value("200")));
    }

    @Test
    void extractAttribute_descAttribute_sameDescAttributeExtracted() {
        String desc = "test";
        Transaction txn = new TransactionMock(desc);
        account.addTransaction(txn);
        Index txnIndex = TypicalIndexes.INDEX_FIRST_ITEM;

        assertEquals(new Description(desc), RuleEngine.extractAttribute(Attribute.DESCRIPTION, txnIndex, account));
    }

    @Test
    void extractAttribute_inAmtAttribute_inAmtAttributeExtracted() {
        Transaction txn = new TransactionMock("test");
        account.addTransaction(txn);
        Index txnIndex = TypicalIndexes.INDEX_FIRST_ITEM;

        assertNull(RuleEngine.extractAttribute(Attribute.IN_AMOUNT, txnIndex, account));
    }

    @Test
    void extractAttribute_outAmtAttribute_outAmtAttributeExtracted() {
        Transaction txn = new TransactionMock("test");
        account.addTransaction(txn);
        Index txnIndex = TypicalIndexes.INDEX_FIRST_ITEM;

        assertEquals(new Amount(10000), RuleEngine.extractAttribute(Attribute.OUT_AMOUNT, txnIndex, account));
    }

    /**
     * Represents a stub Account.
     */
    public class AccountMock extends Account {

        public AccountMock() {
            super(new Name("TestName"), new Description("TestDesc"), new TransactionList(), 0);
        }
    }

    /**
     * Represents a stub Transaction.
     */
    public class TransactionMock extends Transaction {

        public TransactionMock(String desc) {
            super(LocalDate.now(), new Amount(10000), Direction.OUT, new Description(desc), new HashSet<>());
        }
    }
}
