package budgetbuddy.model.util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.model.AccountsManager;
import budgetbuddy.model.LoansManager;
import budgetbuddy.model.RuleManager;
import budgetbuddy.model.ScriptLibraryManager;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.attributes.Amount;
import budgetbuddy.model.attributes.Category;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Direction;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.loan.Status;
import budgetbuddy.model.person.Person;
import budgetbuddy.model.rule.Rule;
import budgetbuddy.model.rule.expression.ActionExpression;
import budgetbuddy.model.rule.expression.Attribute;
import budgetbuddy.model.rule.expression.Operator;
import budgetbuddy.model.rule.expression.PredicateExpression;
import budgetbuddy.model.rule.expression.Value;
import budgetbuddy.model.script.Script;
import budgetbuddy.model.script.ScriptName;
import budgetbuddy.model.transaction.Transaction;
import budgetbuddy.model.transaction.TransactionList;

/**
 * Contains utility methods for populating {@code BudgetBuddy} with sample data.
 */
public class SampleDataUtil {

    public static List<Loan> getSampleLoans() {
        return Arrays.asList(
                new Loan(new Person(new Name("Example Person 1")),
                        Direction.OUT, new Amount(420), LocalDate.now(),
                        new Description("An example of a loan where you lent money to someone."), Status.UNPAID),
                new Loan(new Person(new Name("Example Person 2")),
                        Direction.IN, new Amount(1000), LocalDate.now(),
                        new Description("An example of a loan where you borrowed money from someone."), Status.PAID));
    }

    public static List<Rule> getSampleRules() {
        return Arrays.asList(
                new Rule(new PredicateExpression(Attribute.DESCRIPTION, Operator.CONTAINS, new Value("food")),
                        new ActionExpression(Operator.SET_CATEGORY, new Value("Food")))
        );
    }

    /**
     * Returns a list of sample scripts.
     */
    public static List<Script> getSampleScripts() {
        return Collections.singletonList(new Script(new ScriptName("category-sum"),
                new Description("Calculates the sum of transactions in categories."),
                "var txns = getShownTxns();\n"
                        + "var sums = { \"No Category\": 0 };\n"
                        + "for (var i = 0; i < txns.length; ++i) {\n"
                        + "    var txn = txns[i];\n"
                        + "    var amount = (txnDirection(txn) === \"IN\" ? 1 : -1) * txnAmount(txn);\n"
                        + "    var cats = txnCategories(txn);\n"
                        + "    for (var j = 0; j < cats.length; ++j) {\n"
                        + "        var cat = cats[j];\n"
                        + "        if (sums[cat] === undefined) {\n"
                        + "            sums[cat] = 0;\n"
                        + "        }\n"
                        + "        sums[cat] += amount;\n"
                        + "    }\n"
                        + "    if (cats.length === 0) {\n"
                        + "        sums[\"No Category\"] += amount;\n"
                        + "    }\n"
                        + "}\n"
                        + "var result = \"\";\n"
                        + "for (var cat in sums) {\n"
                        + "    result += cat + \": $\" + (sums[cat] / 100) + \"\\n\";\n"
                        + "}\n"
                        + "result\n"));
    }

    public static LoansManager getSampleLoansManager() {
        return new LoansManager(getSampleLoans());
    }

    public static RuleManager getSampleRuleManager() {
        return new RuleManager(getSampleRules());
    }

    public static ScriptLibraryManager getSampleScriptLibraryManager() {
        return new ScriptLibraryManager(getSampleScripts());
    }

    public static List<Account> getSampleAccounts() {
        TransactionList firstTransactionList = new TransactionList();
        firstTransactionList.add(new Transaction(
                LocalDate.now(),
                new Amount(5000),
                Direction.IN,
                new Description("Pocket Money"),
                new Category("Income")));
        firstTransactionList.add(new Transaction(
                LocalDate.now(),
                new Amount(5000),
                Direction.OUT,
                new Description("Books"),
                new Category("Studies")));

        Account first = new Account(
                new Name("School"),
                new Description("Transactions from school"),
                firstTransactionList);

        TransactionList secondTransactionList = new TransactionList();
        secondTransactionList.add(new Transaction(
                LocalDate.now(),
                new Amount(4000),
                Direction.IN,
                new Description("Birthday money"),
                new Category("birthday")));
        secondTransactionList.add(new Transaction(
                LocalDate.now(),
                new Amount(4000),
                Direction.OUT,
                new Description("Bought a cake"),
                new Category("food")));

        Account second = new Account(
                new Name("Home"),
                new Description("About family stuff"),
                secondTransactionList);

        return Arrays.asList(first, second);
    }

    public static AccountsManager getSampleAccountsManager() {
        return new AccountsManager(getSampleAccounts(), Index.fromZeroBased(0));
    }
}
