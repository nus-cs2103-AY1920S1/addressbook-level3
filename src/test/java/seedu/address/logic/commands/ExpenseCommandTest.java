package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
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

public class ExpenseCommandTest {
    private static final List<String> persons = new ArrayList<>();
    private static final Amount amount = new Amount(10);
    private static final List<Expense> expenses = new ArrayList<>();
    private static ExpenseCommand command;
    private static final String emptyString = "";
    private static final String notEmptyString = "ayy";

    @BeforeAll
    public static void setLists() {
        persons.add("Pauline"); // alice actually
        persons.add("Benson"); // yes he is benson
        command = new ExpenseCommand(persons, amount, emptyString);
        expenses.clear();
    }

    @Test
    public void constructor_nullParams_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new ExpenseCommand(null, new Amount(1), emptyString));
        assertThrows(NullPointerException.class, () ->
                new ExpenseCommand(new ArrayList<>(), null, emptyString));
        assertThrows(NullPointerException.class, () ->
                new ExpenseCommand(new ArrayList<>(), new Amount(1), null));
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

        CommandResult commandResult = command.execute(model);

        assertEquals(String.format(ExpenseCommand.MESSAGE_SUCCESS, persons.size(),
                String.format(ExpenseCommand.MESSAGE_EXPENSE,
                        TypicalPersons.ALICE.getName(),
                        amount,
                        emptyString)
                + String.format(ExpenseCommand.MESSAGE_EXPENSE,
                        TypicalPersons.BENSON.getName(),
                        amount,
                        emptyString)
                ),
                commandResult.getFeedbackToUser());
        expenses.clear(); // for some odd reason @BeforeAll doesn't do this properly?
        expenses.add(new Expense(TypicalPersons.ALICE.getPrimaryKey(), amount, emptyString));
        assertEquals(expenses, model.getActivityBook().getActivityList().get(0).getExpenses());
    }

    @Test
    public void execute_specifyPeopleInvolved_success() throws Exception {
        Activity validActivity = new ActivityBuilder()
                .addPerson(TypicalPersons.ALICE)
                .addPerson(TypicalPersons.BENSON)
                .addPerson(TypicalPersons.GEORGE)
                .addPerson(TypicalPersons.BOB)
                .build();

        Model model = new ModelManager();
        model.addPerson(TypicalPersons.ALICE);
        model.addPerson(TypicalPersons.BENSON);
        model.addPerson(TypicalPersons.GEORGE);
        model.addPerson(TypicalPersons.BOB);
        model.addPerson(TypicalPersons.HOON); // what a name
        model.addActivity(validActivity);
        model.setContext(new Context(validActivity));

        CommandResult commandResult = command.execute(model);

        assertEquals(String.format(ExpenseCommand.MESSAGE_SUCCESS, persons.size(),
                String.format(ExpenseCommand.MESSAGE_EXPENSE,
                        TypicalPersons.ALICE.getName(),
                        amount,
                        emptyString)
                + String.format(ExpenseCommand.MESSAGE_EXPENSE,
                        TypicalPersons.BENSON.getName(),
                        amount,
                        emptyString)
                ),
                commandResult.getFeedbackToUser());

        expenses.add(new Expense(TypicalPersons.ALICE.getPrimaryKey(), amount,
                    emptyString, TypicalPersons.BENSON.getPrimaryKey()));
        assertEquals(expenses, model.getActivityBook().getActivityList().get(0).getExpenses());
    }

    @Test
    public void execute_notActivityViewContextMissingPeopleOrDescription_throwsCommandException() {
        Model model = new ModelManager();
        assertThrows(CommandException.class, () -> command.execute(model));

        model.addPerson(TypicalPersons.ALICE);
        assertThrows(CommandException.class, () -> command.execute(model));

        model.addPerson(TypicalPersons.BENSON);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_notActivityViewContextPeoplePresent_addSuccessful() throws Exception {
        Model model = new ModelManager();
        model.addPerson(TypicalPersons.ALICE);
        model.addPerson(TypicalPersons.BENSON);

        ExpenseCommand command = new ExpenseCommand(persons, amount, notEmptyString);
        CommandResult commandResult = command.execute(model);

        assertEquals(String.format(ExpenseCommand.MESSAGE_SUCCESS, persons.size(),
                String.format(ExpenseCommand.MESSAGE_EXPENSE,
                        TypicalPersons.ALICE.getName(),
                        amount,
                        notEmptyString)
                        + String.format(ExpenseCommand.MESSAGE_EXPENSE,
                        TypicalPersons.BENSON.getName(),
                        amount,
                        notEmptyString)
                ),
                commandResult.getFeedbackToUser());

        // It should add a whole activity wrapping all the contacts, and then
        // create an expense as per usual. In this case the parsing takes the first
        // person to be the payer and the rest to owe him. It is odd but cannot
        // be helped since the user enters in a sequence of persons as well, and
        // there is no special flag to differentiate them.
        Expense expense = new Expense(TypicalPersons.ALICE.getPrimaryKey(), amount, notEmptyString);

        assertEquals(new ActivityBuilder()
                        .withTitle(notEmptyString)
                        .addPerson(TypicalPersons.ALICE).addPerson(TypicalPersons.BENSON)
                        .addExpense(expense)
                        .build(),
                model.getActivityBook().getActivityList().get(0));
    }
}
