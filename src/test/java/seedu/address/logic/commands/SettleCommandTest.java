package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Context;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Amount;
import seedu.address.model.activity.Expense;
import seedu.address.testutil.ActivityBuilder;
import seedu.address.testutil.TypicalPersons;

public class SettleCommandTest {
    private static final List<String> persons = new ArrayList<>();
    private static final List<Expense> expenses = new ArrayList<>();
    private static final Amount amount = new Amount(10);
    private static final String emptyString = "";
    private static SettleCommand command;

    @BeforeEach
    public void setLists() {
        expenses.clear();
        persons.clear();
        persons.add("Pauline"); // alice
        persons.add("Benson"); // yes benson
        // alice tries to pay benson 10 bucks
        command = new SettleCommand(persons, amount);
    }

    @Test
    public void constructor_nullParams_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new SettleCommand(null, new Amount(1)));
        assertThrows(NullPointerException.class, () ->
                new SettleCommand(new ArrayList<>(), null));
    }

    @Test
    public void execute_activityViewContextParticipantsPresent_addSuccessful() throws Exception {
        Activity validActivity = new ActivityBuilder()
                .addPerson(TypicalPersons.ALICE)
                .addPerson(TypicalPersons.BENSON)
                .build();
        Model model = new ModelManager();
        model.addPerson(TypicalPersons.ALICE);
        model.addPerson(TypicalPersons.BENSON);
        model.addActivity(validActivity);
        model.setContext(new Context(validActivity));
        Expense expense = new Expense(TypicalPersons.BENSON.getPrimaryKey(),
                    new Amount(10000),
                    emptyString);
        expenses.add(expense);
        validActivity.addExpense(expense);

        CommandResult commandResult = command.execute(model);

        assertEquals(String.format(SettleCommand.MESSAGE_SUCCESS,
                TypicalPersons.ALICE.getNameStr(),
                TypicalPersons.BENSON.getNameStr(),
                amount.value),
                commandResult.getFeedbackToUser());

        Expense settlement = new Expense(TypicalPersons.ALICE.getPrimaryKey(),
                    amount,
                    String.format(SettleCommand.EXPENSE_DESCRIPTION,
                        TypicalPersons.ALICE.getName(),
                        TypicalPersons.BENSON.getName()),
                    true,
                    TypicalPersons.BENSON.getPrimaryKey());
        expenses.add(settlement);
        assertEquals(expenses, model.getActivityBook().getActivityList().get(0).getExpenses());
    }

    @Test
    public void execute_samePerson_throwsCommandException() throws Exception {
        Activity validActivity = new ActivityBuilder()
                .addPerson(TypicalPersons.GEORGE)
                .addPerson(TypicalPersons.GEORGE_FIRSTNAME)
                .build();
        Model model = new ModelManager();
        model.addPerson(TypicalPersons.GEORGE);
        model.addPerson(TypicalPersons.GEORGE_FIRSTNAME);
        model.addActivity(validActivity);
        model.setContext(new Context(validActivity));

        ArrayList<String> search = new ArrayList<>();
        SettleCommand settlecommand = new SettleCommand(search, amount);
        search.add("George");
        search.add("George");
        assertThrows(CommandException.class, () -> settlecommand.execute(model));
    }

    @Test
    public void execute_invalidAmount_throwCommandException() throws Exception {
        Activity validActivity = new ActivityBuilder()
                .addPerson(TypicalPersons.ALICE)
                .addPerson(TypicalPersons.BENSON)
                .addPerson(TypicalPersons.GEORGE)
                .addPerson(TypicalPersons.BOB)
                .build();

        // now benson owes alice $5
        validActivity.addExpense(new Expense(TypicalPersons.ALICE.getPrimaryKey(),
                amount,
                emptyString,
                TypicalPersons.BENSON.getPrimaryKey()));

        Model model = new ModelManager();
        model.addPerson(TypicalPersons.ALICE);
        model.addPerson(TypicalPersons.BENSON);
        model.addPerson(TypicalPersons.GEORGE);
        model.addPerson(TypicalPersons.BOB);
        model.addPerson(TypicalPersons.HOON); // what a name
        model.addActivity(validActivity);
        model.setContext(new Context(validActivity));

        // alice tries to give benson $10, making debt worse!
        assertThrows(CommandException.class, () -> command.execute(model));

        persons.set(0, "Benson");
        persons.set(1, "alice");
        command = new SettleCommand(persons, amount);

        // Benson tries to give alice $10, that's too much!
        assertThrows(CommandException.class, () -> command.execute(model));

        command = new SettleCommand(persons, new Amount(0)); //pay full sum
        command.execute(model); // now nobody owes anything
        // Nothing to pay!
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_automaticallySettleZeroAmount_success() throws Exception {
        Activity validActivity = new ActivityBuilder()
                .addPerson(TypicalPersons.ALICE)
                .addPerson(TypicalPersons.BENSON)
                .addPerson(TypicalPersons.GEORGE)
                .addPerson(TypicalPersons.BOB)
                .build();

        // now alice owes benson $5
        Expense expense = new Expense(TypicalPersons.BENSON.getPrimaryKey(),
                new Amount(10),
                emptyString,
                TypicalPersons.ALICE.getPrimaryKey());
        validActivity.addExpense(expense);

        Model model = new ModelManager();
        model.addPerson(TypicalPersons.ALICE);
        model.addPerson(TypicalPersons.BENSON);
        model.addPerson(TypicalPersons.GEORGE);
        model.addPerson(TypicalPersons.BOB);
        model.addPerson(TypicalPersons.HOON); // what a name
        model.addActivity(validActivity);
        model.setContext(new Context(validActivity));

        // automagically calculate alice's debt with benson and return all money
        command = new SettleCommand(persons, new Amount(0));

        Activity expectedActivity = new ActivityBuilder()
                .addPerson(TypicalPersons.ALICE)
                .addPerson(TypicalPersons.BENSON)
                .addPerson(TypicalPersons.GEORGE)
                .addPerson(TypicalPersons.BOB)
                .build();

        // now alice owes benson $5
        expectedActivity.addExpense(expense);

        // execute command to get this
        Amount transferAmount = new Amount(5);
        Expense settle = new Expense(TypicalPersons.ALICE.getPrimaryKey(),
                transferAmount,
                String.format(SettleCommand.EXPENSE_DESCRIPTION,
                    TypicalPersons.ALICE.getName(),
                    TypicalPersons.BENSON.getName()),
                true,
                TypicalPersons.BENSON.getPrimaryKey());
        expectedActivity.addExpense(settle);

        Model expectedModel = new ModelManager();
        expectedModel.addPerson(TypicalPersons.ALICE);
        expectedModel.addPerson(TypicalPersons.BENSON);
        expectedModel.addPerson(TypicalPersons.GEORGE);
        expectedModel.addPerson(TypicalPersons.BOB);
        expectedModel.addPerson(TypicalPersons.HOON); // what a name
        expectedModel.addActivity(expectedActivity);
        expectedModel.setContext(new Context(expectedActivity));

        String expectedMessage = String.format(SettleCommand.MESSAGE_SUCCESS,
                TypicalPersons.ALICE.getName(),
                TypicalPersons.BENSON.getName(),
                transferAmount.value);
        assertCommandSuccess(command, model, expectedMessage, expectedModel, expectedModel.getContext());
    }

    @Test
    public void execute_notActivityView_throwsCommandException() throws Exception {
        Model model = new ModelManager();
        assertThrows(CommandException.class, () -> command.execute(model));

        model.addPerson(TypicalPersons.ALICE);
        assertThrows(CommandException.class, () -> command.execute(model));

        model.addPerson(TypicalPersons.BENSON);
        assertThrows(CommandException.class, () -> command.execute(model));

        SettleCommand command = new SettleCommand(persons, amount);
        assertThrows(CommandException.class, () -> command.execute(model));
    }
}
