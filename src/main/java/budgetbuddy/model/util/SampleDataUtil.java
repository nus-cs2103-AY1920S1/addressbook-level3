package budgetbuddy.model.util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import budgetbuddy.model.LoansManager;
import budgetbuddy.model.RuleManager;
import budgetbuddy.model.attributes.Amount;
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

    public static LoansManager getSampleLoansManager() {
        return new LoansManager(getSampleLoans());
    }

    public static RuleManager getSampleRuleManager() {
        return new RuleManager(getSampleRules());
    }
}
