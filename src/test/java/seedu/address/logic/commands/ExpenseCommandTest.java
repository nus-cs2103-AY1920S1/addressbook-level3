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
import seedu.address.testutil.TypicalActivities;
import seedu.address.testutil.TypicalPersons;

public class ExpenseCommandTest {
    private static final List<String> persons = new ArrayList<>();
    private static final List<Amount> amounts = new ArrayList<>();
    private static final List<Expense> expenses = new ArrayList<>();
    private static ExpenseCommand command;
    private static final String emptyString = "";
    private static final String notEmptyString = "ayy";

    @BeforeAll
    public static void setLists() {
        persons.add("Pauline");
        persons.add("Benson");
        amounts.add(new Amount(10));
        amounts.add(new Amount(20));
        expenses.add(new Expense(TypicalPersons.ALICE.getPrimaryKey(), amounts.get(0), emptyString));
        expenses.add(new Expense(TypicalPersons.BENSON.getPrimaryKey(), amounts.get(1), emptyString));
        command = new ExpenseCommand(persons, amounts, emptyString);
    }

    @Test
    public void constructor_nullParams_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new ExpenseCommand(null, new ArrayList<>(), emptyString));
        assertThrows(NullPointerException.class, () ->
                new ExpenseCommand(new ArrayList<>(), null, emptyString));
        assertThrows(NullPointerException.class, () ->
                new ExpenseCommand(new ArrayList<>(), new ArrayList<>(), null));
    }

    @Test
    public void execute_activityViewContextMissingParticipants_throwsCommandException() {
        Activity validActivity = new ActivityBuilder().build();
        Model model = new ModelManager();
        model.addPerson(TypicalPersons.ALICE);
        model.addPerson(TypicalPersons.BENSON);
        model.addActivity(validActivity);
        model.setContext(new Context(validActivity));

        assertThrows(CommandException.class, () -> command.execute(model));

        model.addActivity(TypicalActivities.BREAKFAST);
        model.setContext(new Context(TypicalActivities.BREAKFAST));
        assertThrows(CommandException.class, () -> command.execute(model));
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
                        amounts.get(0),
                        emptyString)
                + String.format(ExpenseCommand.MESSAGE_EXPENSE,
                        TypicalPersons.BENSON.getName(),
                        amounts.get(1),
                        emptyString)
                ),
                commandResult.getFeedbackToUser());

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

        ExpenseCommand command = new ExpenseCommand(persons, amounts, notEmptyString);
        CommandResult commandResult = command.execute(model);

        assertEquals(String.format(ExpenseCommand.MESSAGE_SUCCESS, persons.size(),
                String.format(ExpenseCommand.MESSAGE_EXPENSE,
                        TypicalPersons.ALICE.getName(),
                        amounts.get(0),
                        notEmptyString)
                        + String.format(ExpenseCommand.MESSAGE_EXPENSE,
                        TypicalPersons.BENSON.getName(),
                        amounts.get(1),
                        notEmptyString)
                ),
                commandResult.getFeedbackToUser());

        List<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense(TypicalPersons.ALICE.getPrimaryKey(), amounts.get(0), notEmptyString));
        expenses.add(new Expense(TypicalPersons.BENSON.getPrimaryKey(), amounts.get(1), notEmptyString));

        assertEquals(new ActivityBuilder()
                        .withTitle(notEmptyString)
                        .addPerson(TypicalPersons.ALICE).addPerson(TypicalPersons.BENSON)
                        .addExpense(expenses.get(0)).addExpense(expenses.get(1))
                        .build(),
                model.getActivityBook().getActivityList().get(0));
    }
}
