package seedu.address.reimbursement.logic.commands;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.reimbursement.ui.ReimbursementMessages.BACK_COMMAND;
import static seedu.address.reimbursement.ui.ReimbursementMessages.MESSAGE_ADD_DEADLINE;
import static seedu.address.reimbursement.ui.ReimbursementMessages.MESSAGE_DONE_REIMBURSEMENT;
import static seedu.address.reimbursement.ui.ReimbursementMessages.MESSAGE_EXIT_ACKNOWLEDGEMENT;
import static seedu.address.reimbursement.ui.ReimbursementMessages.MESSAGE_FIND_REIMBURSEMENT;
import static seedu.address.reimbursement.ui.ReimbursementMessages.SORT_BY_AMOUNT;
import static seedu.address.reimbursement.ui.ReimbursementMessages.SORT_BY_DEADLINE;
import static seedu.address.reimbursement.ui.ReimbursementMessages.SORT_BY_NAME;
import static seedu.address.testutil.TypicalDeadlines.DATE_TIME_FORMATTER;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.reimbursement.model.Model;
import seedu.address.reimbursement.model.ModelManager;
import seedu.address.reimbursement.model.Reimbursement;
import seedu.address.reimbursement.model.ReimbursementList;
import seedu.address.testutil.TransactionBuilder;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalReimbursements;
import seedu.address.testutil.TypicalTransactions;
import seedu.address.transaction.model.Transaction;

public class CommandTest {

    @Test
    public void back_command_test() {
        Model reimbursementModel = new ModelManager(new ReimbursementList());
        seedu.address.person.model.Model personModel = new seedu.address.person.model.ModelManager();

        Command backCommand = new BackCommand();

        CommandResult commandResult = null;
        try {
            commandResult = backCommand.execute(reimbursementModel, personModel);
        } catch (Exception e) {
            fail();
        }
        assertEquals(new CommandResult(BACK_COMMAND), commandResult);
    }

    @Test
    public void exit_command_test() {
        Model reimbursementModel = new ModelManager(new ReimbursementList());
        seedu.address.person.model.Model personModel = new seedu.address.person.model.ModelManager();

        Command exitCommand = new ExitCommand();

        CommandResult commandResult = null;
        try {
            commandResult = exitCommand.execute(reimbursementModel, personModel);
        } catch (Exception e) {
            fail();
        }
        assertEquals(new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, true), commandResult);
    }

    @Test
    public void sort_amount_command_test() {
        Model reimbursementModel = new ModelManager(new ReimbursementList());
        seedu.address.person.model.Model personModel = new seedu.address.person.model.ModelManager();

        Command sortAmountCommand = new SortAmountCommand();

        CommandResult commandResult = null;
        try {
            commandResult = sortAmountCommand.execute(reimbursementModel, personModel);
        } catch (Exception e) {
            fail();
        }
        assertEquals(new CommandResult(SORT_BY_AMOUNT), commandResult);
    }

    @Test
    public void sort_deadline_command_test() {
        Model reimbursementModel = new ModelManager(new ReimbursementList());
        seedu.address.person.model.Model personModel = new seedu.address.person.model.ModelManager();

        Command sortDeadlineCommand = new SortDeadlineCommand();

        CommandResult commandResult = null;
        try {
            commandResult = sortDeadlineCommand.execute(reimbursementModel, personModel);
        } catch (Exception e) {
            fail();
        }
        assertEquals(new CommandResult(SORT_BY_DEADLINE), commandResult);
    }

    @Test
    public void sort_name_command_test() {
        Model reimbursementModel = new ModelManager(new ReimbursementList());
        seedu.address.person.model.Model personModel = new seedu.address.person.model.ModelManager();

        Command sortNameCommand = new SortNameCommand();

        CommandResult commandResult = null;
        try {
            commandResult = sortNameCommand.execute(reimbursementModel, personModel);
        } catch (Exception e) {
            fail();
        }
        assertEquals(new CommandResult(SORT_BY_NAME), commandResult);
    }


    @Test
    public void deadline_command_test() {
        Transaction transactionAlice = new TransactionBuilder(TypicalPersons.ALICE).withAmount(-10).build();
        Reimbursement reimbursementAlice = new Reimbursement(transactionAlice);
        ArrayList<Reimbursement> arrList = new ArrayList<>(Arrays.asList(reimbursementAlice));

        Model reimbursementModel = new ModelManager(new ReimbursementList(arrList));
        seedu.address.person.model.Model personModel = new seedu.address.person.model.ModelManager();

        Command deadlineCommand = new DeadlineCommand(TypicalPersons.ALICE, "03-Jan-2020");

        CommandResult commandResult = null;
        try {
            commandResult = deadlineCommand.execute(reimbursementModel, personModel);
        } catch (Exception e) {
            fail();
        }
        CommandResult expectedResult = new CommandResult(String.format(MESSAGE_ADD_DEADLINE,
                reimbursementAlice.getDeadline().format(DATE_TIME_FORMATTER),
                reimbursementAlice.toStringNoDeadline()));

        assertEquals(expectedResult, commandResult);
    }

    @Test
    public void done_command_test() {

        Transaction transactionAlice = new TransactionBuilder(TypicalPersons.ALICE).withAmount(-10).build();
        Reimbursement reimbursementAlice = new Reimbursement(transactionAlice);
        ArrayList<Reimbursement> arrList = new ArrayList<>(Arrays.asList(reimbursementAlice));

        Model reimbursementModel = new ModelManager(new ReimbursementList(arrList));
        seedu.address.person.model.Model personModel = new seedu.address.person.model.ModelManager();

        Command doneCommand = new DoneCommand(TypicalPersons.ALICE);

        CommandResult commandResult = null;
        try {
            commandResult = doneCommand.execute(reimbursementModel, personModel);
        } catch (Exception e) {
            fail();
        }
        CommandResult expectedResult
                = new CommandResult(String.format(MESSAGE_DONE_REIMBURSEMENT, reimbursementAlice.toString()));

        assertEquals(expectedResult, commandResult);
    }

    @Test
    public void find_command_test() {
        Transaction transactionAlice = new TransactionBuilder(TypicalPersons.ALICE).withAmount(-10).build();
        Reimbursement reimbursementAlice = new Reimbursement(transactionAlice);
        ArrayList<Reimbursement> arrList = new ArrayList<>(Arrays.asList(reimbursementAlice));

        Model reimbursementModel = new ModelManager(new ReimbursementList(arrList));
        seedu.address.person.model.Model personModel = new seedu.address.person.model.ModelManager();

        Command findCommand = new FindCommand(TypicalPersons.ALICE);

        CommandResult commandResult = null;
        try {
            commandResult = findCommand.execute(reimbursementModel, personModel);
        } catch (Exception e) {
            fail();
        }
        CommandResult expectedResult
                = new CommandResult(String.format(MESSAGE_FIND_REIMBURSEMENT,
                reimbursementAlice.getPerson().getName().toString(), reimbursementAlice.toString()));
        assertEquals(expectedResult, commandResult);
    }
}