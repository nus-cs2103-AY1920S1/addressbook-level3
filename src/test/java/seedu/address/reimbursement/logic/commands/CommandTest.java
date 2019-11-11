package seedu.address.reimbursement.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
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

import seedu.address.person.model.ModelManager;
import seedu.address.reimbursement.model.Model;
import seedu.address.reimbursement.model.Reimbursement;
import seedu.address.reimbursement.model.ReimbursementList;
import seedu.address.testutil.TransactionBuilder;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalReimbursements;
import seedu.address.transaction.model.transaction.Transaction;
import seedu.address.util.CommandResult;


public class CommandTest {
    public CommandTest() {
        TypicalReimbursements typicalReimbursements = new TypicalReimbursements();
        typicalReimbursements.resetReimbursements();
    }

    @Test
    public void execute_backCommand_success() {
        Model reimbursementModel = new seedu.address.reimbursement.model.ModelManager(new ReimbursementList());
        seedu.address.person.model.Model personModel = new ModelManager();

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
    public void execute_exitCommand_success() {
        Model reimbursementModel = new seedu.address.reimbursement.model.ModelManager(new ReimbursementList());
        seedu.address.person.model.Model personModel = new ModelManager();

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
    public void execute_sortAmountCommand_success() {
        Model reimbursementModel = new seedu.address.reimbursement.model.ModelManager(new ReimbursementList());
        seedu.address.person.model.Model personModel = new ModelManager();

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
    public void execute_sortDeadlineCommand_success() {
        Model reimbursementModel = new seedu.address.reimbursement.model.ModelManager(new ReimbursementList());
        seedu.address.person.model.Model personModel = new ModelManager();

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
    public void execute_sortNameCommand_success() {
        Model reimbursementModel = new seedu.address.reimbursement.model.ModelManager(new ReimbursementList());
        seedu.address.person.model.Model personModel = new ModelManager();

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
    public void execute_deadlineCommand_success() {
        Transaction transactionAlice = new TransactionBuilder(TypicalPersons.ALICE).withAmount(-10).build();
        Reimbursement reimbursementAlice = new Reimbursement(transactionAlice);
        ArrayList<Reimbursement> arrList = new ArrayList<>(Arrays.asList(reimbursementAlice));

        Model reimbursementModel = new seedu.address.reimbursement.model.ModelManager(new ReimbursementList(arrList));
        seedu.address.person.model.Model personModel = new ModelManager();

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
    public void execute_doneCommand_success() {

        Transaction transactionAlice = new TransactionBuilder(TypicalPersons.ALICE).withAmount(-10).build();
        Reimbursement reimbursementAlice = new Reimbursement(transactionAlice);
        ArrayList<Reimbursement> arrList = new ArrayList<>(Arrays.asList(reimbursementAlice));

        Model reimbursementModel = new seedu.address.reimbursement.model.ModelManager(new ReimbursementList(arrList));
        seedu.address.person.model.Model personModel = new ModelManager();

        Command doneCommand = new DoneCommand(TypicalPersons.ALICE);

        CommandResult commandResult = null;
        try {
            commandResult = doneCommand.execute(reimbursementModel, personModel);
        } catch (Exception e) {
            fail();
        }
        CommandResult expectedResult =
                new CommandResult(String.format(MESSAGE_DONE_REIMBURSEMENT, reimbursementAlice.toString()));

        assertEquals(expectedResult, commandResult);
    }

    @Test
    public void execute_findCommand_success() {
        Transaction transactionAlice = new TransactionBuilder(TypicalPersons.ALICE).withAmount(-10).build();
        Reimbursement reimbursementAlice = new Reimbursement(transactionAlice);
        ArrayList<Reimbursement> arrList = new ArrayList<>(Arrays.asList(reimbursementAlice));

        Model reimbursementModel = new seedu.address.reimbursement.model.ModelManager(new ReimbursementList(arrList));
        seedu.address.person.model.Model personModel = new ModelManager();

        Command findCommand = new FindCommand(TypicalPersons.ALICE);

        CommandResult commandResult = null;
        try {
            commandResult = findCommand.execute(reimbursementModel, personModel);
        } catch (Exception e) {
            fail();
        }
        CommandResult expectedResult =
                new CommandResult(String.format(MESSAGE_FIND_REIMBURSEMENT,
                        reimbursementAlice.getPerson().getName().toString(), reimbursementAlice.toString()));
        assertEquals(expectedResult, commandResult);
    }
}
