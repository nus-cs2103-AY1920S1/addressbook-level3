package budgetbuddy.logic.commands.transactioncommands;

import static budgetbuddy.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import budgetbuddy.model.Model;
import budgetbuddy.model.ModelManager;
import budgetbuddy.model.attributes.Amount;
import budgetbuddy.model.attributes.Category;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Direction;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.transaction.Transaction;

public class TransactionAddCommandTest {
    private Model model;
    private Transaction testTransaction;

    @BeforeEach
    public void initialize() {
        model = new ModelManager();
        testTransaction = new Transaction(
                LocalDate.now(),
                new Amount(5000),
                Direction.IN,
                new Description("Pocket Money"),
                new Category("Income"));
    }



    @Test
    public void constructor_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TransactionAddCommand(null, new Name("Default")));
    }

}
