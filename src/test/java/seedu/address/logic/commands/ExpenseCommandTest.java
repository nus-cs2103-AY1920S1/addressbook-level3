package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_WARNING;
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

public class ExpenseCommandTest {
    private static final List<String> persons = new ArrayList<>();
    private static final Amount amount = new Amount(10);
    private static final List<Expense> expenses = new ArrayList<>();
    private static ExpenseCommand commandMultipleNames;
    private static ExpenseCommand commandOneName;
    private static final String emptyString = "";
    private static final String notEmptyString = "ayy";

    @BeforeEach
    public void setLists() {
        persons.clear();
        persons.add("Pauline"); // alice actually
        persons.add("Benson"); // yes he is benson
        commandMultipleNames = new ExpenseCommand(persons, amount, emptyString);
        commandOneName = new ExpenseCommand(persons.subList(0, 1), amount, emptyString);
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

        CommandResult commandResult = commandOneName.execute(model);

        assertEquals(String.format(ExpenseCommand.MESSAGE_SUCCESS,
                amount, TypicalPersons.ALICE.getName(), emptyString,
                "\t\t" + TypicalPersons.BENSON.getName() + "\n"),
                commandResult.getFeedbackToUser());

        expenses.add(new Expense(
                TypicalPersons.ALICE.getPrimaryKey(),
                amount,
                emptyString,
                TypicalPersons.BENSON.getPrimaryKey()));
        assertEquals(expenses, model.getActivityBook().getActivityList().get(0).getExpenses());
    }

    @Test
    public void execute_activityViewContextNameSubstring_addSuccessful() throws Exception {
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
        search.add("George");
        ExpenseCommand cmd = new ExpenseCommand(search, amount, emptyString);

        CommandResult commandResult = cmd.execute(model);

        assertEquals(String.format(ExpenseCommand.MESSAGE_SUCCESS,
                amount, TypicalPersons.GEORGE_FIRSTNAME.getName(),
                emptyString, "\t\t" + TypicalPersons.GEORGE.getName() + "\n"),
                commandResult.getFeedbackToUser());

        expenses.clear();
        expenses.add(new Expense(
                TypicalPersons.GEORGE_FIRSTNAME.getPrimaryKey(),
                amount,
                emptyString,
                TypicalPersons.GEORGE.getPrimaryKey()));
        assertEquals(expenses, model.getActivityBook().getActivityList().get(0).getExpenses());
    }

    @Test
    public void execute_activityViewContextSpecifyPeopleInvolved_success() throws Exception {
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

        CommandResult commandResult = commandMultipleNames.execute(model);

        assertEquals(String.format(ExpenseCommand.MESSAGE_SUCCESS,
                amount, TypicalPersons.ALICE.getName(), emptyString,
                "\t\t" + TypicalPersons.BENSON.getName() + "\n"),
                commandResult.getFeedbackToUser());

        expenses.clear();
        expenses.add(new Expense(TypicalPersons.ALICE.getPrimaryKey(), amount,
                    emptyString, TypicalPersons.BENSON.getPrimaryKey()));
        assertEquals(expenses, model.getActivityBook().getActivityList().get(0).getExpenses());
    }

    @Test
    public void execute_notActivityViewContextMissingPeopleOrDescription_throwsCommandException() {
        Model model = new ModelManager();
        assertThrows(CommandException.class, () -> commandMultipleNames.execute(model));

        model.addPerson(TypicalPersons.ALICE);
        assertThrows(CommandException.class, () -> commandMultipleNames.execute(model));

        model.addPerson(TypicalPersons.BENSON);
        assertThrows(CommandException.class, () -> commandMultipleNames.execute(model));
    }

    @Test
    public void execute_notActivityViewContextPeoplePresent_addSuccessful() throws Exception {
        Model model = new ModelManager();
        model.addPerson(TypicalPersons.ALICE);
        model.addPerson(TypicalPersons.BENSON);

        ExpenseCommand command = new ExpenseCommand(persons, amount, notEmptyString);
        CommandResult commandResult = command.execute(model);

        assertEquals(String.format(ExpenseCommand.MESSAGE_SUCCESS,
                amount, TypicalPersons.ALICE.getName(), notEmptyString,
                "\t\t" + TypicalPersons.BENSON.getName() + "\n"),
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

    @Test
    public void execute_duplicateNamesAdded_addSuccessfulWithWarning() throws Exception {
        Model model = new ModelManager();
        model.addPerson(TypicalPersons.ALICE);
        model.addPerson(TypicalPersons.BENSON);

        ArrayList<String> personsDuplicate = new ArrayList<>(persons);
        personsDuplicate.addAll(persons);
        ExpenseCommand command = new ExpenseCommand(personsDuplicate, amount, notEmptyString);
        CommandResult commandResult = command.execute(model);

        assertEquals(String.format(ExpenseCommand.MESSAGE_SUCCESS + MESSAGE_WARNING,
                amount, TypicalPersons.ALICE.getName(), notEmptyString,
                "\t\t" + TypicalPersons.BENSON.getName() + "\n",
                String.format(ExpenseCommand.WARNING_DUPLICATE_PERSON, TypicalPersons.ALICE.getName())
                + String.format(ExpenseCommand.WARNING_DUPLICATE_PERSON, TypicalPersons.BENSON.getName())),
                commandResult.getFeedbackToUser());

        Expense expense = new Expense(TypicalPersons.ALICE.getPrimaryKey(), amount,
                notEmptyString, TypicalPersons.BENSON.getPrimaryKey());

        assertEquals(new ActivityBuilder()
                        .withTitle(notEmptyString)
                        .addPerson(TypicalPersons.ALICE).addPerson(TypicalPersons.BENSON)
                        .addExpense(expense)
                        .build(),
                model.getActivityBook().getActivityList().get(0));
    }
}
