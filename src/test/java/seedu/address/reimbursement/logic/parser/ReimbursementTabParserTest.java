package seedu.address.reimbursement.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import seedu.address.person.model.Model;
import seedu.address.person.model.ModelManager;
import seedu.address.reimbursement.logic.commands.BackCommand;
import seedu.address.reimbursement.logic.commands.Command;
import seedu.address.reimbursement.logic.commands.DeadlineCommand;
import seedu.address.reimbursement.logic.commands.DoneCommand;
import seedu.address.reimbursement.logic.commands.ExitCommand;
import seedu.address.reimbursement.logic.commands.FindCommand;
import seedu.address.reimbursement.logic.commands.SortAmountCommand;
import seedu.address.reimbursement.logic.commands.SortDeadlineCommand;
import seedu.address.reimbursement.logic.commands.SortNameCommand;
import seedu.address.testutil.TypicalPersons;

public class ReimbursementTabParserTest {
    private static final ReimbursementTabParser parser = new ReimbursementTabParser();
    private Model personModel = new ModelManager();

    @Test
    public void parser() {
        assertThrows(Exception.class, () -> parser.parseCommand("command", personModel));
    }

    @Test
    public void parser_backCommand() {
        Command command = null;

        try {
            command = parser.parseCommand("back", personModel);
        } catch (Exception e) {
            fail();
        }
        assertTrue(command instanceof BackCommand);
    }

    @Test
    public void parser_deadlineCommand() {
        Command command = null;

        assertThrows(Exception.class, () -> parser.parseCommand("deadline", personModel));
        assertThrows(Exception.class, () -> parser.parseCommand("deadline p/Bob", personModel));


        personModel.addPerson(TypicalPersons.ALICE);
        try {
            command = parser.parseCommand("deadline p/Alice Pauline dt/20-Sep-2020", personModel);
        } catch (Exception e) {
            fail();
        }
        assertTrue(command instanceof DeadlineCommand);
    }

    @Test
    public void parser_doneCommand() {
        Command command = null;

        assertThrows(Exception.class, () -> parser.parseCommand("done", personModel));
        assertThrows(Exception.class, () -> parser.parseCommand("done p/Bob", personModel));

        personModel.addPerson(TypicalPersons.ALICE);

        try {
            command = parser.parseCommand("done p/Alice Pauline", personModel);
        } catch (Exception e) {
            fail();
        }
        assertTrue(command instanceof DoneCommand);
    }

    @Test
    public void parser_exitCommand() {
        Command command = null;

        assertThrows(Exception.class, () -> parser.parseCommand("exit p/name", personModel));

        try {
            command = parser.parseCommand("exit", personModel);
        } catch (Exception e) {
            fail();
        }
        assertTrue(command instanceof ExitCommand);
    }

    @Test
    public void parser_findCommand() {
        Command command = null;

        assertThrows(Exception.class, () -> parser.parseCommand("find", personModel));
        assertThrows(Exception.class, () -> parser.parseCommand("find p/Bob", personModel));

        personModel.addPerson(TypicalPersons.ALICE);

        try {
            command = parser.parseCommand("find p/Alice Pauline", personModel);
        } catch (Exception e) {
            fail();
        }
        assertTrue(command instanceof FindCommand);
    }

    @Test
    public void parser_sortCommand() {
        Command command = null;

        assertThrows(Exception.class, () -> parser.parseCommand("sort", personModel));

        try {
            command = parser.parseCommand("sort name", personModel);
        } catch (Exception e) {
            fail();
        }
        assertTrue(command instanceof SortNameCommand);

        try {
            command = parser.parseCommand("sort amount", personModel);
        } catch (Exception e) {
            fail();
        }
        assertTrue(command instanceof SortAmountCommand);

        try {
            command = parser.parseCommand("sort date", personModel);
        } catch (Exception e) {
            fail();
        }
        assertTrue(command instanceof SortDeadlineCommand);
    }

}
